package com.github.wmarkow.klondiklon.tiled;

import org.junit.Test;

public class TmxTiledMapTest
{

    @Test
    public void testReadForNoException() throws Exception
    {
        TmxTiledMap tiledMap = TmxTiledMap.readFromTmx("src/test/resources/home.tmx");
    }
}
