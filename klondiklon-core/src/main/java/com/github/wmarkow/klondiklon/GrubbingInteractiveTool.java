package com.github.wmarkow.klondiklon;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.graphics.Camera;
import com.github.wmarkow.klondiklon.event.Event;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.event.EventSubscriber;
import com.github.wmarkow.klondiklon.event.events.TouchTapEvent;
import com.github.wmarkow.klondiklon.map.KKMapObjectIf;
import com.github.wmarkow.klondiklon.map.KKTiledMap;
import com.github.wmarkow.klondiklon.map.coordinates.CoordinateCalculator;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;
import com.github.wmarkow.klondiklon.sound.SoundPlayer;
import com.github.wmarkow.klondiklon.sound.SoundPlayerListener;

public class GrubbingInteractiveTool implements EventSubscriber, SoundPlayerListener
{
    private static Logger LOGGER = LoggerFactory.getLogger(GrubbingInteractiveTool.class);

    private HomeLandLogic homeLandLogic = new HomeLandLogic();

    private EventBus eventBus;
    private KKTiledMap map;
    private Camera camera;
    private SoundPlayer soundPlayer = new SoundPlayer();

    private List<KKMapObjectIf> firstTapSelectedObjects = new ArrayList<KKMapObjectIf>();
    private KKMapObjectIf objectToGrubb = null;

    public GrubbingInteractiveTool(EventBus eventBus, KKTiledMap map, Camera camera) {
        this.eventBus = eventBus;
        this.map = map;
        this.camera = camera;

        this.eventBus.subscribe(TouchTapEvent.class, this);
    }

    @Override
    public void onEvent(Event event)
    {
        if (objectToGrubb != null)
        {
            // actually in grubbing, can't process more events right now
            return;
        }

        if (event instanceof TouchTapEvent)
        {
            processTouchTapEvent((TouchTapEvent) event);
        }
    }

    @Override
    public void playSoundFinished()
    {
        // remove the object from map
        // add reward
        
        objectToGrubb = null;
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
                mapObject.setSelected(true);
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
        LOGGER.info(String.format("Need to grubb the object %s", objectToGrubb));
        objectToGrubb.setSelected(false);
        firstTapSelectedObjects.clear();

        // now it depends on the grubbing type, it depends on the object type itself
        GrubbingType grubbingType = homeLandLogic.getGrubbingType(objectToGrubb);
        switch (grubbingType)
        {
            case DIGGING:
                HomeLand.GRUBBING_DIGGING.play();
                break;
            case CHOPPING:
                // must be played three times
                soundPlayer.play(HomeLand.GRUBBING_CHOPPING, 1.0f, 3, this);
                break;
            case MINING:
                HomeLand.GRUBBING_MINING.play();
                break;
            default:
                break;
        }

        // remove the object from map
        // add reward
        // this will be done when sound finishes to play
    }
}
