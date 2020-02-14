package com.github.wmarkow.klondiklon.ui.tools;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.graphics.Camera;
import com.github.wmarkow.klondiklon.HomeLandLogic;
import com.github.wmarkow.klondiklon.Klondiklon;
import com.github.wmarkow.klondiklon.event.Event;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.event.EventSubscriber;
import com.github.wmarkow.klondiklon.event.events.TouchLongDownEvent;
import com.github.wmarkow.klondiklon.event.events.TouchTapEvent;
import com.github.wmarkow.klondiklon.map.KKMapObjectIf;
import com.github.wmarkow.klondiklon.map.KKTiledMap;
import com.github.wmarkow.klondiklon.map.coordinates.CoordinateCalculator;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;
import com.github.wmarkow.klondiklon.player.Player;
import com.github.wmarkow.klondiklon.sound.SoundManager;
import com.github.wmarkow.klondiklon.sound.SoundPlayerListener;

public class GrubbingInteractiveTool implements EventSubscriber
{
    private static Logger LOGGER = LoggerFactory.getLogger(GrubbingInteractiveTool.class);

    private final static long GRUBBING_MAX_TIME_IN_MILLIS = 6000;

    private HomeLandLogic homeLandLogic = new HomeLandLogic();

    private EventBus eventBus;
    private KKTiledMap map;
    private Camera camera;

    private List<KKMapObjectIf> firstTapSelectedObjects = new ArrayList<KKMapObjectIf>();
    private KKMapObjectIf objectToGrubb = null;
    private Long lastGrubbingTimestamp = null;
    private Player player;

    public GrubbingInteractiveTool(EventBus eventBus, KKTiledMap map, Camera camera, Player player) {
        this.eventBus = eventBus;
        this.map = map;
        this.camera = camera;
        this.player = player;

        this.eventBus.subscribe(TouchTapEvent.class, this);
        this.eventBus.subscribe(TouchLongDownEvent.class, this);
    }

    @Override
    public void onEvent(Event event)
    {
        if (objectToGrubb != null)
        {
            // actually in grubbing, can't process more events right now
            if (lastGrubbingTimestamp != null
                    && System.currentTimeMillis() - lastGrubbingTimestamp < GRUBBING_MAX_TIME_IN_MILLIS)
            {
                return;
            } else
            {
                // something wrong with grubbing
                resetGrubbing();
            }
        }

        if (event instanceof TouchTapEvent)
        {
            processTouchTapEvent((TouchTapEvent) event);

            return;
        }
        if (event instanceof TouchLongDownEvent)
        {
            processTouchLongDownEvent((TouchLongDownEvent) event);

            return;
        }
    }

    private void processTouchTapEvent(TouchTapEvent event)
    {
        LOGGER.info(String.format("Event %s received x=%s, y=%s.", event.getClass().getSimpleName(), event.getScreenX(),
                event.getScreenY()));

        if (firstTapSelectedObjects.size() == 0)
        {
            processFirstTap(event);
        } else
        {
            processSecondTap(event);
        }
    }

    private void processTouchLongDownEvent(TouchLongDownEvent event)
    {
        resetGrubbing();

        CoordinateCalculator coordinateCalculator = new CoordinateCalculator();

        GdxScreenCoordinates screenCoordinates = new GdxScreenCoordinates(event.getScreenX(), event.getScreenY());
        GdxWorldOrthoCoordinates gdxWorldCoordinates = coordinateCalculator.screen2World(camera, screenCoordinates);

        for (KKMapObjectIf mapObject : map.getObjectsLayer().getMapObjects())
        {
            mapObject.setSelected(false);

            GrubbingType grubbingType = homeLandLogic.getGrubbingType(mapObject);

            if (GrubbingType.NONE.equals(grubbingType))
            {
                continue;
            }

            if (mapObject.containsPoint(gdxWorldCoordinates))
            {
                int energyToGrub = homeLandLogic.energyToGrub(mapObject.getObjectType());
                String name = homeLandLogic.getName(mapObject.getObjectType());
                String tooltip = String.format("%s \n Wytrzymałość: %s", name, energyToGrub);

                mapObject.setSelectedTrue(tooltip);
            }
        }
    }

    /***
     * The purpose of this method is to find a candidates for grubbing.
     * 
     * @param event
     */
    private void processFirstTap(TouchTapEvent event)
    {
        firstTapSelectedObjects.clear();

        CoordinateCalculator coordinateCalculator = new CoordinateCalculator();

        GdxScreenCoordinates screenCoordinates = new GdxScreenCoordinates(event.getScreenX(), event.getScreenY());
        GdxWorldOrthoCoordinates gdxWorldCoordinates = coordinateCalculator.screen2World(camera, screenCoordinates);

        for (KKMapObjectIf mapObject : map.getObjectsLayer().getMapObjects())
        {
            mapObject.setSelected(false);

            GrubbingType grubbingType = homeLandLogic.getGrubbingType(mapObject);

            if (GrubbingType.NONE.equals(grubbingType))
            {
                continue;
            }

            if (mapObject.containsPoint(gdxWorldCoordinates))
            {
                int energyToGrub = homeLandLogic.energyToGrub(mapObject.getObjectType());
                mapObject.setSelectedTrue(String.valueOf(energyToGrub));
                firstTapSelectedObjects.add(mapObject);

                LOGGER.info(String.format("Object clicked anchor(x,y)=(%s,%s), (width,height)=(%s,%s)",
                        mapObject.getX(), mapObject.getY(), mapObject.getWidth(), mapObject.getHeight()));
            }
        }
    }

    /***
     * The purpose of this method is to perform a grubbing on the selected object.
     * 
     * @param event
     */
    private void processSecondTap(TouchTapEvent event)
    {
        CoordinateCalculator coordinateCalculator = new CoordinateCalculator();

        GdxScreenCoordinates screenCoordinates = new GdxScreenCoordinates(event.getScreenX(), event.getScreenY());
        GdxWorldOrthoCoordinates gdxWorldCoordinates = coordinateCalculator.screen2World(camera, screenCoordinates);

        List<KKMapObjectIf> secondTapObjects = new ArrayList<KKMapObjectIf>();
        for (KKMapObjectIf mapObject : firstTapSelectedObjects)
        {
            if (mapObject.containsPoint(gdxWorldCoordinates))
            {
                secondTapObjects.add(mapObject);

                LOGGER.info(String.format("Object clicked anchor(x,y)=(%s,%s), (width,height)=(%s,%s)",
                        mapObject.getX(), mapObject.getY(), mapObject.getWidth(), mapObject.getHeight()));
            } else
            {
                mapObject.setSelected(false);
            }
        }

        if (secondTapObjects.size() == 0)
        {
            firstTapSelectedObjects.clear();

            return;
        }

        if (secondTapObjects.size() > 1)
        {
            return;
        }

        // do the grubbing
        objectToGrubb = secondTapObjects.get(0);
        lastGrubbingTimestamp = System.currentTimeMillis();

        int energyToGrub = homeLandLogic.energyToGrub(objectToGrubb.getObjectType());
        if (player.getEnergy() < energyToGrub)
        {
            // to low energy, can not grub
            resetGrubbing();
            return;
        }

        player.removeEnergy(energyToGrub);

        LOGGER.info(String.format("Need to grubb the object %s", objectToGrubb));
        firstTapSelectedObjects.clear();

        // now it depends on the grubbing type, it depends on the object type itself
        GrubbingType grubbingType = homeLandLogic.getGrubbingType(objectToGrubb);
        switch (grubbingType)
        {
            case DIGGING:
                Klondiklon.soundManager.play(Klondiklon.soundManager.GRUBBING_DIGGING, 1.0f, 1,
                        new GrubbingSoundPlayerListener());
                break;
            case CHOPPING:
                // must be played three times
                Klondiklon.soundManager.play(Klondiklon.soundManager.GRUBBING_CHOPPING, 1.0f, 3,
                        new GrubbingSoundPlayerListener());
                break;
            case MINING:
                Klondiklon.soundManager.play(Klondiklon.soundManager.GRUBBING_MINING, 2.5f, 1,
                        new GrubbingSoundPlayerListener());
                break;
            default:
                break;
        }

        // remove the object from map
        // add reward
        // this will be done when sound finishes to play
    }

    private void finishGrubbing()
    {
        if (objectToGrubb != null)
        {
            // remove object from map
            // add reward
        }

        resetGrubbing();
    }

    private void resetGrubbing()
    {
        if (objectToGrubb != null)
        {
            objectToGrubb.setSelected(false);
        }
        objectToGrubb = null;
        lastGrubbingTimestamp = null;
        firstTapSelectedObjects.clear();
    }

    private class GrubbingSoundPlayerListener implements SoundPlayerListener
    {
        @Override
        public void playSoundFinished()
        {
            finishGrubbing();
        }
    }
}
