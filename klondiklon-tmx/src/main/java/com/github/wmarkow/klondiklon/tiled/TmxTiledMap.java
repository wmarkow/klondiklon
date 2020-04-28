package com.github.wmarkow.klondiklon.tiled;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.wmarkow.klondiklon.tiled.tmx.LayerElement;
import com.github.wmarkow.klondiklon.tiled.tmx.MapElement;
import com.github.wmarkow.klondiklon.tiled.tmx.ObjectGroupElement;
import com.github.wmarkow.klondiklon.tiled.tmx.TmxFileReader;
import com.github.wmarkow.klondiklon.tiled.tsx.TilesetElement;
import com.github.wmarkow.klondiklon.tiled.tsx.TsxFileReader;

public class TmxTiledMap
{
    private MapElement tmxMapElement;
    private List<Tileset> tsxTilesets = new ArrayList<Tileset>();

    private TmxTiledMap() {

    }

    public String getOrientation()
    {
        return tmxMapElement.getOrientation();
    }

    public int getWidth()
    {
        return tmxMapElement.getWidth();
    }

    public int getHeight()
    {
        return tmxMapElement.getHeight();
    }

    public int getTileWidth()
    {
        return tmxMapElement.getTilewidth();
    }

    public int getTileHeight()
    {
        return tmxMapElement.getTileheight();
    }

    public List<TmxLayer> getLayers()
    {
        List<TmxLayer> layers = new ArrayList<TmxLayer>();

        // first tile layers...
        int tileWidth = getTileWidth();
        int tileHeight = getTileHeight();

        for (LayerElement layerElement : tmxMapElement.getLayers())
        {
            layers.add(new TmxTileLayer(tileWidth, tileHeight, this, layerElement));
        }

        // ...then add object group layer at the end
        ObjectGroupElement objectGroupElement = tmxMapElement.getObjectGroup();
        layers.add(new TmxObjectGroupLayer(objectGroupElement));

        return layers;
    }

    public List<Tileset> getTilests()
    {
        return tsxTilesets;
    }

    public static TmxTiledMap readFromTmx(String tmxPath) throws Exception
    {
        TmxTiledMap result = new TmxTiledMap();

        // read TMX
        TmxFileReader tmxFileReader = new TmxFileReader();
        result.tmxMapElement = tmxFileReader.readTmx(tmxPath);

        // read TSXs
        File tmxFile = new File(tmxPath);
        File tmxDir = tmxFile.getParentFile();
        for (com.github.wmarkow.klondiklon.tiled.tmx.TilesetElement tmxTileset : result.tmxMapElement.getTilesets())
        {
            TsxFileReader tsxFileReader = new TsxFileReader();
            File tsxFile = new File(tmxDir, tmxTileset.getSource());
            TilesetElement tsxTileset = tsxFileReader.readTsx(tsxFile.getAbsolutePath());

            result.tsxTilesets.add(new Tileset(tsxFile.getAbsolutePath(), tmxTileset.getFirstgid(), tsxTileset));
        }

        return result;
    }
}
