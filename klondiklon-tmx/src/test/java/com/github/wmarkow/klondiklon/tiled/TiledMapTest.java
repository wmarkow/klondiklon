package com.github.wmarkow.klondiklon.tiled;

import org.junit.Test;

public class TiledMapTest
{

    @Test
    public void testReadForNoException() throws Exception
    {
        TiledMap tiledMap = TiledMap.readFromTmx("src/test/resources/home.tmx");
    }
}
