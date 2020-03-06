package com.github.wmarkow.klondiklon.ui.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.graphics.Camera;
import com.github.wmarkow.klondiklon.Klondiklon;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.event.Event;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.event.EventSubscriber;
import com.github.wmarkow.klondiklon.event.events.TouchDraggedEvent;
import com.github.wmarkow.klondiklon.event.events.TouchLongDownEvent;
import com.github.wmarkow.klondiklon.event.events.TouchUpEvent;
import com.github.wmarkow.klondiklon.events.MoveObjectButtonCancelClickedEvent;
import com.github.wmarkow.klondiklon.events.MoveObjectButtonOkClickedEvent;
import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.map.coordinates.CoordinateCalculator;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;
import com.github.wmarkow.klondiklon.map.objects.KKMapObjectIf;
import com.github.wmarkow.klondiklon.objects.ObjectTypeDescriptor;
import com.github.wmarkow.klondiklon.objects.ObjectTypeDescriptorsManager;

public class MoveObjectInteractiveTool implements EventSubscriber
{
    private static Logger LOGGER = LoggerFactory.getLogger(MoveObjectInteractiveTool.class);

    private EventBus eventBus;
    private KKMapIf map;
    private Camera camera;
    private ObjectTypeDescriptorsManager objectTypesManager;
    private KKMapObjectIf objectToMove = null;
    private boolean objectTochedDown = false;

    public MoveObjectInteractiveTool(EventBus eventBus, KKMapIf map, Camera camera,
            ObjectTypeDescriptorsManager objectTypesManager) {
        this.eventBus = eventBus;
        this.map = map;
        this.camera = camera;
        this.objectTypesManager = objectTypesManager;

        this.eventBus.subscribe(TouchUpEvent.class, this);
        this.eventBus.subscribe(TouchLongDownEvent.class, this);
        this.eventBus.subscribe(TouchDraggedEvent.class, this);
        this.eventBus.subscribe(MoveObjectButtonOkClickedEvent.class, this);
        this.eventBus.subscribe(MoveObjectButtonCancelClickedEvent.class, this);
    }

    @Override
    public void onEvent(Event event)
    {
        if (event instanceof TouchLongDownEvent)
        {
            processTouchLongDownEvent((TouchLongDownEvent) event);

            return;
        }
        if (event instanceof TouchDraggedEvent)
        {
            processTouchDraggedEvent((TouchDraggedEvent) event);

            return;
        }
        if (event instanceof TouchUpEvent)
        {
            objectTochedDown = false;
            ServiceRegistry.getInstance().cameraController.setLockCameraWhileDragging(false);

            return;
        }

        if (event instanceof MoveObjectButtonOkClickedEvent)
        {
            reset();

            return;
        }
        if (event instanceof MoveObjectButtonCancelClickedEvent)
        {
            reset();

            return;
        }
    }

    private void processTouchLongDownEvent(TouchLongDownEvent event)
    {
        LOGGER.info(String.format("Long touch down event"));

        CoordinateCalculator coordinateCalculator = new CoordinateCalculator();

        GdxScreenCoordinates screenCoordinates = new GdxScreenCoordinates(event.getScreenX(), event.getScreenY());
        GdxWorldOrthoCoordinates gdxWorldCoordinates = coordinateCalculator.screen2World(camera, screenCoordinates);

        if (objectToMove != null && objectToMove.containsPoint(gdxWorldCoordinates))
        {
            // already moving this object
            ServiceRegistry.getInstance().cameraController.setLockCameraWhileDragging(true);
            return;
        }
        
        for (KKMapObjectIf mapObject : map.getObjects())
        {
            ObjectTypeDescriptor descriptor = objectTypesManager.getByObjectType(mapObject.getObjectType());
            if (descriptor == null)
            {
                continue;
            }

            if (descriptor.getGrubbingType() != null)
            {
                // what can be grubbed, can not be moved
                continue;
            }

            if (mapObject.containsPoint(gdxWorldCoordinates))
            {
                objectToMove = mapObject;
                objectTochedDown = true;
                objectToMove.setSelectedTrueGreenColor();
                Klondiklon.ui.showMoveObjectView();

                ServiceRegistry.getInstance().cameraController.setLockCameraWhileDragging(true);
                return;
            }
        }
    }

    private void processTouchDraggedEvent(TouchDraggedEvent event)
    {
        if (objectToMove == null)
        {
            return;
        }
        LOGGER.info(String.format("Touch dragged event received x=%s, y=%s", event.getScreenX(), event.getScreenY()));
    }

    private void reset()
    {
        if (objectToMove != null)
        {
            objectToMove.setSelected(false);
        }

        objectToMove = null;
        Klondiklon.ui.hideMoveObjectView();
        ServiceRegistry.getInstance().cameraController.setLockCameraWhileDragging(false);
    }
}
