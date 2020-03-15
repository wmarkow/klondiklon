package com.github.wmarkow.klondiklon.ui.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.github.wmarkow.klondiklon.Klondiklon;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.event.Event;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.event.EventSubscriber;
import com.github.wmarkow.klondiklon.event.events.TouchDraggedEvent;
import com.github.wmarkow.klondiklon.event.events.TouchTapEvent;
import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.map.coordinates.CoordinateCalculator;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxTouchCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;
import com.github.wmarkow.klondiklon.map.objects.GardenCellObject;
import com.github.wmarkow.klondiklon.map.objects.KKMapObjectIf;
import com.github.wmarkow.klondiklon.ui.views.SickleView;

public class SickleInteractiveTools implements EventSubscriber
{
    private static Logger LOGGER = LoggerFactory.getLogger(SickleInteractiveTools.class);

    private EventBus eventBus;
    private KKMapIf map;
    private Camera camera;

    private CoordinateCalculator coordinateCalculator = new CoordinateCalculator();

    private GardenCellObject gardenCell = null;
    private SickleView sickleView = null;

    public SickleInteractiveTools(EventBus eventBus, KKMapIf map, Camera camera) {
        this.eventBus = eventBus;
        this.map = map;
        this.camera = camera;

        this.eventBus.subscribe(TouchTapEvent.class, this);
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
        if (event instanceof TouchDraggedEvent)
        {
            processTouchDraggedEvent((TouchDraggedEvent) event);

            return;
        }
    }

    private void processTouchTapEvent(TouchTapEvent event)
    {
        LOGGER.info(String.format("Event %s received x=%s, y=%s.", event.getClass().getSimpleName(),
                event.getGdxTouchCoordinates().getX(), event.getGdxTouchCoordinates().getY()));

        reset();

        GdxWorldOrthoCoordinates gdxWorldCoordinates = coordinateCalculator.touch2World(camera,
                event.getGdxTouchCoordinates());

        for (KKMapObjectIf mapObject : map.getObjects())
        {
            if (mapObject instanceof GardenCellObject == false)
            {
                continue;
            }

            mapObject.setSelected(false);

            GardenCellObject gardenCellObject = (GardenCellObject) mapObject;

            if (!gardenCellObject.isReadyForSickle())
            {
                continue;
            }

            if (!mapObject.containsPoint(gdxWorldCoordinates))
            {
                continue;
            }

            // we can use sickle tool on this object
            gardenCell = (GardenCellObject) mapObject;
            gardenCell.setSelectedTrueGreenColor();

            sickleView = Klondiklon.ui.showSickleView();
            sickleView.setSickleCoordinates(coordinateCalculator.touch2Screen(event.getGdxTouchCoordinates()));
            ServiceRegistry.getInstance().cameraController.setLockCameraWhileDragging(true);

            return;

        }
    }

    private void processTouchDraggedEvent(TouchDraggedEvent event)
    {
        // to sa touch coordinates
        if (gardenCell == null)
        {
            return;
        }

        sickleView.setSickleCoordinates(coordinateCalculator.touch2Screen(event.getGdxTouchCoordinates()));
    }

    private void reset()
    {
        ServiceRegistry.getInstance().cameraController.setLockCameraWhileDragging(false);

        if (gardenCell != null)
        {
            gardenCell.setSelected(false);
            Klondiklon.ui.hideSickleView();

            gardenCell = null;
            sickleView = null;
        }
    }
}
