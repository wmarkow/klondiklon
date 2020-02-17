package com.github.wmarkow.klondiklon.graphics;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;

public class TexturesManager
{
    private Map<String, Texture> textures = new HashMap<String, Texture>();

    public void registerTexture(String name, Texture texture)
    {
        textures.put(name, texture);
    }

    public Texture getTexture(String name)
    {
        return textures.get(name);
    }

    public void init()
    {
    }
}
