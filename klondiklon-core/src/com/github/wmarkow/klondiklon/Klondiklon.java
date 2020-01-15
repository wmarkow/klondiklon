package com.github.wmarkow.klondiklon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class Klondiklon extends ApplicationAdapter implements InputProcessor
{
    Texture texture;
    OrthographicCamera cam;
    SpriteBatch batch;
    final Sprite[][] sprites = new Sprite[10][10];
    final Matrix4 matrix = new Matrix4();

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
        for(int z = 0; z < 10; z++) {
                for(int x = 0; x < 10; x++) {
                        sprites[x][z].draw(batch);
                }
        }
        batch.end();
        
//        checkTileTouched();
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        return false;
    }
}
