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

public class KKCameraController extends InputAdapter
{
    final OrthographicCamera camera;
    final Vector3 curr = new Vector3();
    final Vector3 last = new Vector3(-1, -1, -1);
    final Vector3 delta = new Vector3();

    public KKCameraController(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer)
    {
        camera.unproject(curr.set(x, y, 0));
        if (!(last.x == -1 && last.y == -1 && last.z == -1))
        {
            camera.unproject(delta.set(last.x, last.y, 0));
            delta.sub(curr);
            camera.position.add(delta.x, delta.y, 0);
        }
        last.set(x, y, 0);
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button)
    {
        last.set(-1, -1, -1);
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
