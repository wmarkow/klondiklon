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

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.event.events.TouchLongDownEvent;
import com.github.wmarkow.klondiklon.event.events.TouchTapEvent;
import com.github.wmarkow.klondiklon.event.events.TouchUpEvent;

public class KKCameraController extends InputAdapter
{
    private final static float LONG_TOUCH_DOWN_SECONDS = 0.5f;

    final OrthographicCamera camera;
    final Vector3 curr = new Vector3();
    final Vector3 last = new Vector3(-1, -1, -1);
    final Vector3 delta = new Vector3();

    private EventBus eventBus;

    private boolean touchDraggedDetected = false;
    private boolean touchDownDetected = false;
    private Timer longTouchDownTimer;

    public KKCameraController(OrthographicCamera camera, EventBus eventBus) {
        this.camera = camera;
        this.eventBus = eventBus;
        
        longTouchDownTimer = new Timer();
        longTouchDownTimer.start();
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer)
    {
        touchDraggedDetected = true;
        camera.unproject(curr.set(x, y, 0));
        if (!(last.x == -1 && last.y == -1 && last.z == -1))
        {
            camera.unproject(delta.set(last.x, last.y, 0));
            delta.sub(curr);
            camera.position.add(delta.x, delta.y, 0);
        }
        last.set(x, y, 0);

        eventBus.publish(new TouchUpEvent(x, y));

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        touchDraggedDetected = false;
        touchDownDetected = true;

        longTouchDownTimer.scheduleTask(new Task()
        {
            @Override
            public void run()
            {
                if (touchDownDetected)
                {
                    eventBus.publish(new TouchLongDownEvent(screenX, screenY));
                }

            }
        }, LONG_TOUCH_DOWN_SECONDS);

        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button)
    {
        touchDownDetected = false;
        longTouchDownTimer.clear();

        last.set(-1, -1, -1);
        eventBus.publish(new TouchUpEvent(x, y));

        if (touchDraggedDetected == false)
        {
            eventBus.publish(new TouchTapEvent(x, y));
        }

        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
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
}
