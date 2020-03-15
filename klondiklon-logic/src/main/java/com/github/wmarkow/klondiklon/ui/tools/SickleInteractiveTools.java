package com.github.wmarkow.klondiklon.ui.tools;

import java.util.List;
import java.util.Set;

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
import com.github.wmarkow.klondiklon.objects.GrubbingProfit;
import com.github.wmarkow.klondiklon.ui.views.SickleView;
import com.github.wmarkow.klondiklon.warehouse.Warehouse;

public class SickleInteractiveTools implements EventSubscriber
{
    private static Logger LOGGER = LoggerFactory.getLogger(SickleInteractiveTools.class);

    private EventBus eventBus;
    private KKMapIf map;
    private Camera camera;

    private CoordinateCalculator coordinateCalculator = new CoordinateCalculator();

    private GardenCellObject gardenCell = null;
    private SickleView sickleView = null;
    private boolean sickleTouchedDown = false;

    public SickleInteractiveTools(EventBus eventBus, KKMapIf map, Camera camera) {
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
        LOGGER.info(String.format("Event %s received x=%s, y=%s.", event.getClass().getSimpleName(),
                event.getGdxTouchCoordinates().getX(), event.getGdxTouchCoordinates().getY()));

        reset();

        GdxWorldOrthoCoordinates gdxWorldCoordinates = coordinateCalculator.touch2World(camera,
                event.getGdxTouchCoordinates());

        gardenCell = findGardenReadyToSickle(gdxWorldCoordinates);
        if (gardenCell == null)
        {
            return;
        }

        // we can use sickle tool on this object
        gardenCell.setSelectedTrueGreenColor();

        sickleView = Klondiklon.ui.showSickleView();
        sickleView.setSickleCoordinates(coordinateCalculator.touch2Screen(event.getGdxTouchCoordinates()));
        ServiceRegistry.getInstance().cameraController.setLockCameraWhileDragging(true);
    }

    private void processTouchUpEvent(TouchUpEvent event)
    {
        reset();
    }

    private void processTouchDownEvent(TouchDownEvent event)
    {
        if (sickleView == null)
        {
            reset();
        }

        GdxScreenCoordinates point = coordinateCalculator.touch2Screen(event.getGdxTouchCoordinates());
        if (!sickleView.getSickleImageBounds().containsPoint(point))
        {
            reset();
            return;
        }

        sickleTouchedDown = true;
    }

    private void processTouchDraggedEvent(TouchDraggedEvent event)
    {
        if (gardenCell == null)
        {
            return;
        }

        if (sickleTouchedDown == false)
        {
            reset();

            return;
        }

        GdxScreenCoordinates screenCoordinates = coordinateCalculator.touch2Screen(event.getGdxTouchCoordinates());
        sickleView.setSickleCoordinates(screenCoordinates);

        GdxWorldOrthoCoordinates gdxWorldCoordinates = coordinateCalculator.touch2World(camera,
                event.getGdxTouchCoordinates());

        GardenCellObject gardenToSickle = findGardenReadyToSickle(gdxWorldCoordinates);
        if (gardenToSickle == null)
        {
            return;
        }

        Set<GrubbingProfit> profit = gardenToSickle.sickleIt();
        addSickleProfit(Klondiklon.warehouse, profit);

        Klondiklon.ui.showProfitView(profit, screenCoordinates);
    }

    private GardenCellObject findGardenReadyToSickle(GdxWorldOrthoCoordinates gdxWorldCoordinates)
    {
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

            return (GardenCellObject) mapObject;
        }

        return null;
    }

    private void addSickleProfit(Warehouse warehouse, Set<GrubbingProfit> grubbingProfits)
    {
        for (GrubbingProfit grubbingProfit : grubbingProfits)
        {
            warehouse.addItemQuantity(grubbingProfit.getStorageItemDescriptor(), grubbingProfit.getAmount());
        }
    }

    private void reset()
    {
        ServiceRegistry.getInstance().cameraController.setLockCameraWhileDragging(false);

        if (gardenCell != null)
        {
            gardenCell.setSelected(false);
        }

        Klondiklon.ui.hideSickleView();
        gardenCell = null;
        sickleView = null;
        sickleTouchedDown = false;
    }
}
