package com.github.wmarkow.klondiklon;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.ImageResolver.DirectImageResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ObjectMap;

public class KKTmxMapLoader extends TmxMapLoader
{
    public KKTmxMapLoader() {
        super();
    }

    public KKTmxMapLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    public TiledMap load(String fileName, TmxMapLoader.Parameters parameter)
    {
        FileHandle tmxFile = resolve(fileName);
        ObjectMap<String, Texture> textures = new ObjectMap<String, Texture>();

        // here load the map with libtiled library

        return loadTiledMap(tmxFile, parameter, new DirectImageResolver(textures));
    }

    protected TiledMap loadTiledMap(FileHandle tmxFile, TmxMapLoader.Parameters parameter, ImageResolver imageResolver)
    {
        return new TiledMap();
    }
}
