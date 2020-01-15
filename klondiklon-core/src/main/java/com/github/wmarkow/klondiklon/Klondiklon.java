package com.github.wmarkow.klondiklon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

/***
 * https://www.badlogicgames.com/wordpress/?p=2032
 * https://github.com/libgdx/libgdx/blob/master/tests/gdx-tests/src/com/badlogic/gdx/tests/IsometricTileTest.java
 * @author wmarkowski
 *
 */
public class Klondiklon extends ApplicationAdapter implements InputProcessor
{
    Texture texture;
    OrthographicCamera cam;
    SpriteBatch batch;
    final Sprite[][] sprites = new Sprite[10][10];
    final Matrix4 matrix = new Matrix4();
    final Plane xzPlane = new Plane(new Vector3(0, 1, 0), 0);
    final Vector3 intersection = new Vector3();
    Sprite lastSelectedTile = null;
    final Vector3 curr = new Vector3();
    final Vector3 last = new Vector3(-1, -1, -1);
    final Vector3 delta = new Vector3();

    @Override
    public void create()
    {
        texture = new Texture("badlogic.jpg");
        cam = new OrthographicCamera(10, 10 * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        cam.position.set(5, 5, 10);
        cam.direction.set(-1, -1, -1);
        cam.near = 1;
        cam.far = 100;
        matrix.setToRotation(new Vector3(1, 0, 0), 90);

        for (int z = 0; z < 10; z++)
        {
            for (int x = 0; x < 10; x++)
            {
                sprites[x][z] = new Sprite(texture);
                sprites[x][z].setPosition(x, z);
                sprites[x][z].setSize(1, 1);
            }
        }

        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render()
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();

        batch.setProjectionMatrix(cam.combined);
        batch.setTransformMatrix(matrix);
        batch.begin();
        for (int z = 0; z < 10; z++)
        {
            for (int x = 0; x < 10; x++)
            {
                sprites[x][z].draw(batch);
            }
        }
        batch.end();

        checkTileTouched();
    }

    @Override
    public void dispose()
    {
        batch.dispose();
        texture.dispose();
    }

    @Override
    public boolean keyDown(int keycode)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        last.set(-1, -1, -1);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        Ray pickRay = cam.getPickRay(screenX, screenY);
        Intersector.intersectRayPlane(pickRay, xzPlane, curr);
        
        if(!(last.x == -1 && last.y == -1 && last.z == -1)) {
                pickRay = cam.getPickRay(last.x, last.y);
                Intersector.intersectRayPlane(pickRay, xzPlane, delta);                 
                delta.sub(curr);
                cam.position.add(delta.x, delta.y, delta.z);
        }
        last.set(screenX, screenY, 0);
        
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        if(amount > 0)
        {
            // zoom out
            cam.zoom += 0.1;
            
            return false;
        }
        
        if(amount < 0)
        {
            // zoom in
            cam.zoom += -0.1;
            if(cam.zoom < 0.1)
            {
                cam.zoom = 0.1f;
            }
            
            return false;
        }
        
        
        return false;
    }

    private void checkTileTouched()
    {
        if (Gdx.input.justTouched())
        {
            Ray pickRay = cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());
            Intersector.intersectRayPlane(pickRay, xzPlane, intersection);
            int x = (int) intersection.x;
            int z = (int) intersection.z;
            if (x >= 0 && x < 10 && z >= 0 && z < 10)
            {
                if (lastSelectedTile != null)
                    lastSelectedTile.setColor(1, 1, 1, 1);
                Sprite sprite = sprites[x][z];
                sprite.setColor(1, 0, 0, 1);
                lastSelectedTile = sprite;
            }
        }
    }
}
