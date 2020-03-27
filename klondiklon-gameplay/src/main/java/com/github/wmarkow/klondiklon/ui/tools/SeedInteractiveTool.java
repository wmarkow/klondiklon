package com.github.wmarkow.klondiklon.ui.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.graphics.Camera;
import com.github.wmarkow.klondiklon.Klondiklon;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.event.Event;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.event.EventSubscriber;
import com.github.wmarkow.klondiklon.event.events.TouchDownEvent;
import com.github.wmarkow.klondiklon.event.events.TouchDraggedEvent;
import com.github.wmarkow.klondiklon.event.events.TouchTapEvent;
import com.github.wmarkow.klondiklon.event.events.TouchUpEvent;
import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.map.coordinates.CoordinateCalculator;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;
import com.github.wmarkow.klondiklon.map.objects.GardenCellObject;
import com.github.wmarkow.klondiklon.map.objects.KKMapObjectIf;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptor;
import com.github.wmarkow.klondiklon.sounds.SoundsRegistrar;
import com.github.wmarkow.klondiklon.ui.views.SeedView;

public class SeedInteractiveTool implements EventSubscriber
{
    private static Logger LOGGER = LoggerFactory.getLogger(SeedInteractiveTool.class);

    private CoordinateCalculator coordinateCalculator = new CoordinateCalculator();

    private EventBus eventBus;
    private KKMapIf map;
    private Camera camera;

    private SeedView seedView = null;
    private StorageItemDescriptor seedItemDescriptor = null;

    public SeedInteractiveTool(EventBus eventBus, KKMapIf map, Camera camera) {
        this.eventBus = eventBus;
        this.map = map;
        this.camera = camera;

        this.eventBus.subscribe(TouchTapEvent.class, this);
        this.eventBus.subscribe(TouchUpEvent.class, this);
        this.eventBus.subscribe(TouchDownEvent.class, this);
        this.eventBus.subscribe(TouchDraggedEvent.class, this);
    }

    @Override
    public void onEvent(Event event)
    {
        if (event instanceof TouchTapEvent)
        {
            processTouchTapEvent((TouchTapEvent) event);

            return;
        }
        if (event instanceof TouchUpEvent)
        {
            processTouchUpEvent((TouchUpEvent) event);

            return;
        }
        if (event instanceof TouchDownEvent)
        {
            processTouchDownEvent((TouchDownEvent) event);

            return;
        }
        if (event instanceof TouchDraggedEvent)
        {
            processTouchDraggedEvent((TouchDraggedEvent) event);

            return;
        }
    }

    private void processTouchTapEvent(TouchTapEvent event)
    {
        GdxWorldOrthoCoordinates gdxWorldCoordinates = coordinateCalculator.touch2World(camera,
                event.getGdxTouchCoordinates());

        if (findGardenToSeed(gdxWorldCoordinates) == null)
        {
            return;
        }

        seedView = Klondiklon.ui.showSeedView(coordinateCalculator.touch2Screen(event.getGdxTouchCoordinates()));
    }

    private void processTouchUpEvent(TouchUpEvent event)
    {
        if (seedItemDescriptor == null)
        {
            return;
        }

        ServiceRegistry.getInstance().cameraController.setLockCameraWhileDragging(false);
        Klondiklon.ui.hideSeedView();
        seedView = null;
        seedItemDescriptor = null;
    }

    private void processTouchDownEvent(TouchDownEvent event)
    {
        if (seedView == null)
        {
            return;
        }

        GdxScreenCoordinates screenCoordinates = coordinateCalculator.touch2Screen(event.getGdxTouchCoordinates());
        seedItemDescriptor = seedView.getSeedItemDescriptor(screenCoordinates);
        if (seedItemDescriptor == null)
        {
            Klondiklon.ui.hideSeedView();
            seedView = null;
            seedItemDescriptor = null;
            return;
        }

        ServiceRegistry.getInstance().cameraController.setLockCameraWhileDragging(true);
    }

    private void processTouchDraggedEvent(TouchDraggedEvent event)
    {
        if (seedItemDescriptor == null)
        {
            return;
        }

        GdxScreenCoordinates screenCoordinates = coordinateCalculator.touch2Screen(event.getGdxTouchCoordinates());
        seedView.setSeedItemCoordinates(seedItemDescriptor, screenCoordinates);

        GdxWorldOrthoCoordinates gdxWorldCoordinates = coordinateCalculator.touch2World(camera,
                event.getGdxTouchCoordinates());

        GardenCellObject gardenToSeed = findGardenToSeed(gdxWorldCoordinates);
        if (gardenToSeed == null)
        {
            return;
        }

        // seed the plant
        int gardenId = gardenToSeed.getId();
        Klondiklon.gameplayService.addGardenSimulation(gardenId, seedItemDescriptor);
        ServiceRegistry.getInstance().getSoundManager().getResource(SoundsRegistrar.GARDEN_SEED).play();
    }

    private GardenCellObject findGardenToSeed(GdxWorldOrthoCoordinates gdxWorldCoordinates)
    {
        for (KKMapObjectIf mapObject : map.getObjects())
        {
            if (mapObject instanceof GardenCellObject == false)
            {
                continue;
            }

            GardenCellObject gardenCellObject = (GardenCellObject) mapObject;

            if (!gardenCellObject.isReadyForSeed())
            {
                continue;
            }

            if (!mapObject.containsPoint(gdxWorldCoordinates))
            {
                continue;
            }

            return (GardenCellObject) mapObject;
        }

        return null;
    }
}
