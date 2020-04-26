package com.github.wmarkow.klondiklon.tiled;

import org.junit.Test;

public class TiledMapTest
{

    @Test
    public void testRead() throws Exception
    {
        TiledMap tiledMap = TiledMap.readFromTmx("src/test/resources/home.tmx");
    }
}
