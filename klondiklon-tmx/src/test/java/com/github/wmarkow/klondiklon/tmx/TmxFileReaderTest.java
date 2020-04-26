package com.github.wmarkow.klondiklon.tmx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.github.wmarkow.klondiklon.tiled.tmx.LayerDataElement;
import com.github.wmarkow.klondiklon.tiled.tmx.LayerElement;
import com.github.wmarkow.klondiklon.tiled.tmx.MapElement;
import com.github.wmarkow.klondiklon.tiled.tmx.ObjectElement;
import com.github.wmarkow.klondiklon.tiled.tmx.TilesetElement;
import com.github.wmarkow.klondiklon.tiled.tmx.TmxFileReader;

public class TmxFileReaderTest
{
    private TmxFileReader tmxReader = new TmxFileReader();

    @Test
    public void testReadForAttributes() throws Exception
    {
        MapElement mapElement = tmxReader.readTmx("src/test/resources/home.tmx");

        assertEquals(0, mapElement.getCompressionlevel());
        assertEquals(40, mapElement.getHeight());
        assertEquals(0, mapElement.getInfinite());
        assertEquals(9, mapElement.getNextlayerid());
        assertEquals(219, mapElement.getNextobjectid());
        assertEquals("isometric", mapElement.getOrientation());
        assertEquals("right-down", mapElement.getRenderorder());
        assertEquals("1.3.1", mapElement.getTiledVersion());
        assertEquals(80, mapElement.getTileheight());
        assertEquals(160, mapElement.getTilewidth());
        assertEquals("1.2", mapElement.getVersion());
        assertEquals(40, mapElement.getWidth());
    }

    @Test
    public void testReadForTilesets() throws Exception
    {
        MapElement mapElement = tmxReader.readTmx("src/test/resources/home.tmx");

        assertEquals(21, mapElement.getTilesets().size());

        TilesetElement firstTileset = mapElement.getTilesets().get(0);
        assertEquals(1, firstTileset.getFirstgid());
        assertEquals("object_garden_cell.tsx", firstTileset.getSource());

        TilesetElement lastTileset = mapElement.getTilesets().get(20);
        assertEquals(26, lastTileset.getFirstgid());
        assertEquals("object_windmill.tsx", lastTileset.getSource());
    }

    @Test
    public void testReadForLayers() throws Exception
    {
        MapElement mapElement = tmxReader.readTmx("src/test/resources/home.tmx");

        assertEquals(2, mapElement.getLayers().size());

        LayerElement firstLayer = mapElement.getLayers().get(0);
        assertEquals(40, firstLayer.getHeight());
        assertEquals(1, firstLayer.getId());
        assertEquals("terrain", firstLayer.getName());
        assertEquals(40, firstLayer.getWidth());

        LayerDataElement layerDataElement = firstLayer.getData();
        assertEquals("csv", layerDataElement.getEncoding());

        String dataAsString = layerDataElement.getData();
        String[] rows = dataAsString.split("\n");
        assertEquals(41, rows.length);
        assertTrue(rows[0].isEmpty());

        for (int q = 1; q < 40; q++)
        {
            String[] columns = rows[q].split(",");

            assertEquals(40, columns.length);
        }
    }

    @Test
    public void testReadForObjectGroup() throws Exception
    {
        MapElement mapElement = tmxReader.readTmx("src/test/resources/home.tmx");

        assertEquals(2, mapElement.getObjectGroup().getId());
        assertEquals("objects", mapElement.getObjectGroup().getName());

        List<ObjectElement> objects = mapElement.getObjectGroup().getObjects();
        assertEquals(202, objects.size());

        ObjectElement firstObject = objects.get(0);
        assertEquals(1, firstObject.getGid());
        assertEquals(Double.doubleToLongBits(99.0), Double.doubleToLongBits(firstObject.getHeight()));
        assertEquals(1, firstObject.getId());
        assertEquals(Double.doubleToLongBits(152.0), Double.doubleToLongBits(firstObject.getWidth()));
        assertEquals(Double.doubleToLongBits(2380), Double.doubleToLongBits(firstObject.getX()));
        assertEquals(Double.doubleToLongBits(2460), Double.doubleToLongBits(firstObject.getY()));
    }
}
