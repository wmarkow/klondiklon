package com.github.wmarkow.klondiklon.ui.tools;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.github.wmarkow.klondiklon.Klondiklon;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.event.Event;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.event.EventSubscriber;
import com.github.wmarkow.klondiklon.event.events.TouchDownEvent;
import com.github.wmarkow.klondiklon.event.events.TouchDraggedEvent;
import com.github.wmarkow.klondiklon.event.events.TouchUpEvent;
import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.map.coordinates.CoordinateCalculator;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;
import com.github.wmarkow.klondiklon.map.objects.GardenCellObject;
import com.github.wmarkow.klondiklon.map.objects.KKMapObjectIf;
import com.github.wmarkow.klondiklon.objects.GrubbingProfit;
import com.github.wmarkow.klondiklon.sounds.SoundsRegistrar;
import com.github.wmarkow.klondiklon.ui.views.HarvestView;
import com.github.wmarkow.klondiklon.warehouse.Warehouse;

public class HarvestInteractiveTools implements EventSubscriber
{
    private static Logger LOGGER = LoggerFactory.getLogger(HarvestInteractiveTools.class);

    private EventBus eventBus;
    private KKMapIf map;
    private Camera camera;

    private CoordinateCalculator coordinateCalculator = new CoordinateCalculator();

    private GardenCellObject gardenCell = null;
    private HarvestView harvestView = null;
    private boolean sickleTouchedDown = false;

    public HarvestInteractiveTools(EventBus eventBus, KKMapIf map, Camera camera) {
        this.eventBus = eventBus;
        this.map = map;
        this.camera = camera;

        this.eventBus.subscribe(TouchUpEvent.class, this);
        this.eventBus.subscribe(TouchDownEvent.class, this);
        this.eventBus.subscribe(TouchDraggedEvent.class, this);
    }

    @Override
    public void onEvent(Event event)
    {
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

    private void processTouchUpEvent(TouchUpEvent event)
    {
        LOGGER.info(String.format("Event %s received x=%s, y=%s.", event.getClass().getSimpleName(),
                event.getGdxTouchCoordinates().getX(), event.getGdxTouchCoordinates().getY()));

        GdxWorldOrthoCoordinates gdxWorldCoordinates = coordinateCalculator.touch2World(camera,
                event.getGdxTouchCoordinates());

        if (gardenCell != null)
        {
            reset();
        }

        gardenCell = findGardenReadyToSickle(gdxWorldCoordinates);
        if (gardenCell == null)
        {
            return;
        }

        // we can use harvest tool on this object
        harvestView = Klondiklon.ui.showHarvestView();
        harvestView.setSickleCoordinates(coordinateCalculator.touch2Screen(event.getGdxTouchCoordinates()));
        ServiceRegistry.getInstance().cameraController.setLockCameraWhileDragging(true);
    }

    private void processTouchDownEvent(TouchDownEvent event)
    {
        if (harvestView == null)
        {
            return;
        }

        GdxScreenCoordinates point = coordinateCalculator.touch2Screen(event.getGdxTouchCoordinates());
        if (!harvestView.getSickleImageBounds().containsPoint(point))
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
            return;
        }

        GdxScreenCoordinates screenCoordinates = coordinateCalculator.touch2Screen(event.getGdxTouchCoordinates());
        harvestView.setSickleCoordinates(screenCoordinates);

        GdxWorldOrthoCoordinates gdxWorldCoordinates = coordinateCalculator.touch2World(camera,
                event.getGdxTouchCoordinates());

        GardenCellObject gardenToSickle = findGardenReadyToSickle(gdxWorldCoordinates);
        if (gardenToSickle == null)
        {
            return;
        }

        Set<GrubbingProfit> profit = gardenToSickle.sickleIt();
        Klondiklon.gameplayService.stopGardenSimulation(gardenToSickle.getId());
        addSickleProfit(Klondiklon.gameplayService.getWarehouse(), profit);

        Klondiklon.ui.showProfitView(profit, screenCoordinates);
        ServiceRegistry.getInstance().getSoundManager().getResource(SoundsRegistrar.GARDEN_HARVEST).play();
    }

    private GardenCellObject findGardenReadyToSickle(GdxWorldOrthoCoordinates gdxWorldCoordinates)
    {
        for (KKMapObjectIf mapObject : map.getObjects())
        {
            if (mapObject instanceof GardenCellObject == false)
            {
                continue;
            }

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

        Klondiklon.ui.hideHarvestView();
        gardenCell = null;
        harvestView = null;
        sickleTouchedDown = false;
    }
}
