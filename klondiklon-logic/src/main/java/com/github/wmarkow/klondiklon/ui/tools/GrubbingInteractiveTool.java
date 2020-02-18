package com.github.wmarkow.klondiklon.ui.tools;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.wmarkow.klondiklon.Klondiklon;
import com.github.wmarkow.klondiklon.KlondiklonCore;
import com.github.wmarkow.klondiklon.event.Event;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.event.EventSubscriber;
import com.github.wmarkow.klondiklon.event.events.TouchLongDownEvent;
import com.github.wmarkow.klondiklon.event.events.TouchTapEvent;
import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.map.coordinates.CoordinateCalculator;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;
import com.github.wmarkow.klondiklon.map.objects.KKMapObjectIf;
import com.github.wmarkow.klondiklon.objects.ObjectTypeDescriptor;
import com.github.wmarkow.klondiklon.objects.ObjectTypeDescriptorsManager;
import com.github.wmarkow.klondiklon.player.Player;
import com.github.wmarkow.klondiklon.sound.SoundPlayerListener;
import com.github.wmarkow.klondiklon.ui.widgets.GrubbingProfitActor;

public class GrubbingInteractiveTool implements EventSubscriber
{
    private static Logger LOGGER = LoggerFactory.getLogger(GrubbingInteractiveTool.class);

    private final static long GRUBBING_MAX_TIME_IN_MILLIS = 6000;

    private EventBus eventBus;
    private KKMapIf map;
    private Camera camera;

    private List<KKMapObjectIf> firstTapSelectedObjects = new ArrayList<KKMapObjectIf>();
    private KKMapObjectIf objectToGrubb = null;
    private Long lastGrubbingTimestamp = null;
    private Player player;
    private ObjectTypeDescriptorsManager objectTypesManager;

    public GrubbingInteractiveTool(EventBus eventBus, KKMapIf map, Camera camera, Player player,
            ObjectTypeDescriptorsManager objectTypesManager) {
        this.eventBus = eventBus;
        this.map = map;
        this.camera = camera;
        this.player = player;
        this.objectTypesManager = objectTypesManager;

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

        for (KKMapObjectIf mapObject : map.getObjects())
        {
            mapObject.setSelected(false);

            ObjectTypeDescriptor descriptor = objectTypesManager.getByObjectType(mapObject.getObjectType());
            if (descriptor == null)
            {
                continue;
            }

            if (descriptor.getGrubbingType() == null)
            {
                continue;
            }

            if (mapObject.containsPoint(gdxWorldCoordinates))
            {
                int energyToGrub = descriptor.getEnergyToGrubb();
                String name = descriptor.getName();
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

        for (KKMapObjectIf mapObject : map.getObjects())
        {
            mapObject.setSelected(false);

            ObjectTypeDescriptor descriptor = objectTypesManager.getByObjectType(mapObject.getObjectType());
            if (descriptor == null)
            {
                continue;
            }

            if (descriptor.getGrubbingType() == null)
            {
                continue;
            }

            if (mapObject.containsPoint(gdxWorldCoordinates))
            {
                int energyToGrub = descriptor.getEnergyToGrubb();
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

        ObjectTypeDescriptor descriptor = objectTypesManager.getByObjectType(objectToGrubb.getObjectType());
        if (descriptor == null)
        {
            resetGrubbing();
            return;
        }

        int energyToGrub = descriptor.getEnergyToGrubb();
        if (player.getEnergy() < energyToGrub)
        {
            // to low energy, can not grub
            resetGrubbing();
            return;
        }

        player.removeEnergy(energyToGrub);

        LOGGER.info(String.format("Need to grubb the object %s", objectToGrubb));
        firstTapSelectedObjects.clear();

        // now it depends on the grubbing type
        switch (descriptor.getGrubbingType())
        {
            case DIGGING:
                KlondiklonCore.soundManager.play(KlondiklonCore.soundManager.GRUBBING_DIGGING, 1.0f, 1,
                        new GrubbingSoundPlayerListener());
                break;
            case CHOPPING:
                // must be played three times
                KlondiklonCore.soundManager.play(KlondiklonCore.soundManager.GRUBBING_CHOPPING, 1.0f, 3,
                        new GrubbingSoundPlayerListener());
                break;
            case MINING:
                KlondiklonCore.soundManager.play(KlondiklonCore.soundManager.GRUBBING_MINING, 2.5f, 1,
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
            map.removeObject(objectToGrubb);

            // TODO: add reward to the backpack
            ObjectTypeDescriptor descriptor = objectTypesManager.getByObjectType(objectToGrubb.getObjectType());
            Stage stage = Klondiklon.ui.getStage();
            CoordinateCalculator cc = new CoordinateCalculator();
            GdxScreenCoordinates start = cc.world2Screen(camera,
                    new GdxWorldOrthoCoordinates(objectToGrubb.getX(), objectToGrubb.getY(), 0));

            GrubbingProfitActor gpa = new GrubbingProfitActor(descriptor.getGrubbingProfits(), start);
            stage.addActor(gpa);
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
