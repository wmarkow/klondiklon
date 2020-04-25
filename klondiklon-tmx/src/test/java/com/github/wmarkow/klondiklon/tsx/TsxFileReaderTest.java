package com.github.wmarkow.klondiklon.tsx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TsxFileReaderTest
{
    private TsxFileReader tsxReader = new TsxFileReader();

    @Test
    public void testReadForHome() throws Exception
    {
        TilesetElement tilesetElement = tsxReader.readTsx("src/test/resources/home.tsx");

        assertEquals(3, tilesetElement.getColumns());
        assertEquals("home", tilesetElement.getName());
        assertEquals(6, tilesetElement.getTilecount());
        assertEquals("1.3.1", tilesetElement.getTiledversion());
        assertEquals(95, tilesetElement.getTileheight());
        assertEquals(160, tilesetElement.getTilewidth());
        assertEquals("1.2", tilesetElement.getVersion());

        ImageElement imageElement = tilesetElement.getImage();
        assertEquals(190, imageElement.getHeight());
        assertEquals(480, imageElement.getWidth());
        assertEquals("home.png", imageElement.getSource());

        assertNull(tilesetElement.getTile());
    }

    @Test
    public void testReadForBarn() throws Exception
    {
        TilesetElement tilesetElement = tsxReader.readTsx("src/test/resources/barn.tsx");

        assertEquals(1, tilesetElement.getColumns());
        assertEquals("barn", tilesetElement.getName());
        assertEquals(1, tilesetElement.getTilecount());
        assertEquals("1.3.1", tilesetElement.getTiledversion());
        assertEquals(425, tilesetElement.getTileheight());
        assertEquals(353, tilesetElement.getTilewidth());
        assertEquals("1.2", tilesetElement.getVersion());

        ImageElement imageElement = tilesetElement.getImage();
        assertEquals(425, imageElement.getHeight());
        assertEquals(353, imageElement.getWidth());
        assertEquals("barn.png", imageElement.getSource());

        TileElement tileElement = tilesetElement.getTile();
        assertEquals(0, tileElement.getId());
        assertEquals(1, tileElement.getProperties().size());
        assertEquals("TYPE", tileElement.getProperties().get(0).getName());
        assertEquals("BARN", tileElement.getProperties().get(0).getValue());
    }
}
