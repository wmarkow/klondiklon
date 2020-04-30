/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.github.wmarkow.klondiklon.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.event.events.TouchDownEvent;
import com.github.wmarkow.klondiklon.event.events.TouchDraggedEvent;
import com.github.wmarkow.klondiklon.event.events.TouchLongDownEvent;
import com.github.wmarkow.klondiklon.event.events.TouchTapEvent;
import com.github.wmarkow.klondiklon.event.events.TouchUpEvent;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxTouchCoordinates;

public class KKCameraController extends InputAdapter implements GestureListener
{
    private static Logger LOGGER = LoggerFactory.getLogger(KKCameraController.class);

    private final static float LONG_TOUCH_DOWN_SECONDS = 0.5f;

    final OrthographicCamera camera;
    final Vector3 curr = new Vector3();
    final Vector3 last = new Vector3(-1, -1, -1);
    final Vector3 delta = new Vector3();
    float currentZoom = 0;

    private EventBus eventBus;

    private boolean touchDraggedDetected = false;
    private Long touchDownMillis = null;
    private Timer longTouchDownTimer;
    private boolean lockCameraWhileDragging = false;

    public KKCameraController(OrthographicCamera camera, EventBus eventBus) {
        this.camera = camera;
        this.eventBus = eventBus;

        currentZoom = camera.zoom;

        longTouchDownTimer = new Timer();
        longTouchDownTimer.start();
    }

    public void setLockCameraWhileDragging(boolean lockCameraWhileDragging)
    {
        LOGGER.info(String.format("setLockCameraWhileDragging(%s)", lockCameraWhileDragging));

        this.lockCameraWhileDragging = lockCameraWhileDragging;
    }

    @Override
    public boolean touchDragged(int touchX, int touchY, int pointer)
    {
        LOGGER.info("touchDragged(int , int , int )");

        // GdxTouchCoordinates touchCoordinates = new GdxTouchCoordinates(touchX,
        // touchY);
        //
        // touchDraggedDetected = true;
        // if (lockCameraWhileDragging == false)
        // {
        // camera.unproject(curr.set(touchX, touchY, 0));
        // if (!(last.x == -1 && last.y == -1 && last.z == -1))
        // {
        // camera.unproject(delta.set(last.x, last.y, 0));
        // delta.sub(curr);
        // camera.position.add(delta.x, delta.y, 0);
        // }
        // last.set(touchX, touchY, 0);
        // }
        // eventBus.publish(new TouchDraggedEvent(touchCoordinates));

        return false;
    }

    @Override
    public boolean touchDown(int touchX, int touchY, int pointer, int button)
    {
        LOGGER.info("touchDown(int , int , int , int )");

        GdxTouchCoordinates touchCoordinates = new GdxTouchCoordinates(touchX, touchY);

        touchDraggedDetected = false;
        touchDownMillis = System.currentTimeMillis();

        eventBus.publish(new TouchDownEvent(touchCoordinates));

        longTouchDownTimer.scheduleTask(new Task()
        {
            @Override
            public void run()
            {
                if (touchDownMillis != null)
                {
                    eventBus.publish(new TouchLongDownEvent(touchCoordinates));
                }

            }
        }, LONG_TOUCH_DOWN_SECONDS);

        return false;
    }

    @Override
    public boolean touchUp(int touchX, int touchY, int pointer, int button)
    {
        LOGGER.info("touchUp(int , int , int , int )");

        GdxTouchCoordinates touchCoordinates = new GdxTouchCoordinates(touchX, touchY);

        Long localTouchDownInMillis = touchDownMillis;
        touchDownMillis = null;
        longTouchDownTimer.clear();

        last.set(-1, -1, -1);
        eventBus.publish(new TouchUpEvent(touchCoordinates));

        if (touchDraggedDetected)
        {
            return false;
        }

        if (localTouchDownInMillis != null
                && System.currentTimeMillis() - localTouchDownInMillis < 1000 * LONG_TOUCH_DOWN_SECONDS)
        {
            eventBus.publish(new TouchTapEvent(touchCoordinates));
        }

        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        LOGGER.info("scrolled(int )");

        if (amount > 0)
        {
            // zoom out
            camera.zoom += 20;

            return false;
        }

        if (amount < 0)
        {
            // zoom in
            camera.zoom += -20;
            if (camera.zoom < 20)
            {
                camera.zoom = 20f;
            }

            return false;
        }

        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button)
    {
        LOGGER.info("touchDown(float , float , int , int )");

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button)
    {
        LOGGER.info("tap(float , float , int , int )");

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean longPress(float x, float y)
    {
        LOGGER.info("longPress(float , float )");

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button)
    {
        LOGGER.info("fling(float , float , int )");

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY)
    {
        LOGGER.info("pan(float , float , float , float )");

        // camera.translate(-deltaX * currentZoom, deltaY * currentZoom);
        camera.translate(-deltaX * 5, deltaY * 5);
        camera.update();

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button)
    {
        LOGGER.info("panStop(float , float , int , int )");

        currentZoom = camera.zoom;
        
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance)
    {
        LOGGER.info("zoom(float , float )");

        float newZoom = (initialDistance / distance) * currentZoom;
        if (newZoom < 20)
        {
            newZoom = 20;
        }
        if (newZoom > 300)
        {
            newZoom = 300;
        }
        camera.zoom = newZoom;
        camera.update();

        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
    {
        LOGGER.info("pinch(Vector2 , Vector2 , Vector2 , Vector2 )");

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void pinchStop()
    {
        LOGGER.info("pinchStop()");

        // TODO Auto-generated method stub
    }

}
