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
    float currentZoom = 0;

    private EventBus eventBus;

    private boolean touchDraggedDetected = false;
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
        return false;
    }

    @Override
    public boolean touchDown(int touchX, int touchY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int touchX, int touchY, int pointer, int button)
    {
        GdxTouchCoordinates touchCoordinates = new GdxTouchCoordinates((int) touchX, (int) touchY);
        eventBus.publish(new TouchUpEvent(touchCoordinates));

        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        if (amount > 0)
        {
            // zoom out
            setZoom(getZoom() + 20);
        }

        if (amount < 0)
        {
            // zoom in
            setZoom(getZoom() - 20);
        }

        return false;
    }

    @Override
    public boolean touchDown(float touchX, float touchY, int pointer, int button)
    {
        currentZoom = camera.zoom;
        touchDraggedDetected = false;

        GdxTouchCoordinates touchCoordinates = new GdxTouchCoordinates((int) touchX, (int) touchY);
        eventBus.publish(new TouchDownEvent(touchCoordinates));

        return false;
    }

    @Override
    public boolean tap(float touchX, float touchY, int count, int button)
    {
        LOGGER.info("tap(float , float , int , int )");

        GdxTouchCoordinates touchCoordinates = new GdxTouchCoordinates((int) touchX, (int) touchY);
        eventBus.publish(new TouchTapEvent(touchCoordinates));

        return false;
    }

    @Override
    public boolean longPress(float touchX, float touchY)
    {
        GdxTouchCoordinates touchCoordinates = new GdxTouchCoordinates((int) touchX, (int) touchY);
        eventBus.publish(new TouchLongDownEvent(touchCoordinates));

        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button)
    {
        LOGGER.info("fling(float , float , int )");

        return false;
    }

    /***
     * Called when user makes a pan gesture. The parameters are in
     * {@link GdxTouchCoordinates}
     * 
     * @param x
     * @param y
     * @param deltaX
     * @param deltaY
     */
    @Override
    public boolean pan(float touchX, float touchY, float deltaX, float deltaY)
    {
        LOGGER.info(String.format("pan(%s , %s , %s , %s )", touchX, touchY, deltaX, deltaY));

        GdxTouchCoordinates touchCoordinates = new GdxTouchCoordinates((int) touchX, (int) touchY);

        touchDraggedDetected = true;
        if (lockCameraWhileDragging == false)
        {
            final Vector3 currWorld = new Vector3();
            final Vector3 lastTouch = new Vector3(touchX - deltaX, touchY - deltaY, 0);
            final Vector3 delta = new Vector3();

            camera.unproject(currWorld.set(touchX, touchY, 0));
            camera.unproject(delta.set(lastTouch.x, lastTouch.y, 0));
            delta.sub(currWorld);
            camera.position.add(delta.x, delta.y, 0);
        }
        eventBus.publish(new TouchDraggedEvent(touchCoordinates));

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button)
    {
        LOGGER.info("panStop(float , float , int , int )");

        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance)
    {
        LOGGER.info("zoom(float , float )");

        float newZoom = (initialDistance / distance) * currentZoom;
        setZoom(newZoom);

        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
    {
        return false;
    }

    @Override
    public void pinchStop()
    {
    }

    private float getZoom()
    {
        return camera.zoom;
    }

    private void setZoom(float zoom)
    {
        float newZoom = zoom;
        if (newZoom < 70)
        {
            newZoom = 70;
        } else if (newZoom > 300)
        {
            newZoom = 300;
        }

        camera.zoom = newZoom;
        camera.update();
    }
}
