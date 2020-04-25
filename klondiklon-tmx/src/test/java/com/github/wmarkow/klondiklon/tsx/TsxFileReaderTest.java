package com.github.wmarkow.klondiklon.tsx;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TsxFileReaderTest
{
    private TsxFileReader tsxReader = new TsxFileReader();

    @Test
    public void testReadForHomes() throws Exception
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
    }
}
