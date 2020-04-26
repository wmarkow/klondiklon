package com.github.wmarkow.klondiklon.tiled;

import java.util.ArrayList;
import java.util.List;

import com.github.wmarkow.klondiklon.tiled.tmx.MapElement;
import com.github.wmarkow.klondiklon.tiled.tmx.TmxFileReader;
import com.github.wmarkow.klondiklon.tiled.tsx.TilesetElement;
import com.github.wmarkow.klondiklon.tiled.tsx.TsxFileReader;

public class TiledMap
{

    private MapElement tmxMapElement;
    private List<Tileset> tsxTilesets = new ArrayList<Tileset>();

    private TiledMap()
    {
        
    }
    
    public static TiledMap readFromTmx(String tmxPath) throws Exception
    {
        TiledMap result = new TiledMap();
        
        // read TMX
        TmxFileReader tmxFileReader = new TmxFileReader();
        result.tmxMapElement = tmxFileReader.readTmx(tmxPath);

        // read TSXs
        for (com.github.wmarkow.klondiklon.tiled.tmx.TilesetElement tmxTileset : result.tmxMapElement.getTilesets())
        {
            TsxFileReader tsxFileReader = new TsxFileReader();
            TilesetElement tsxTileset = tsxFileReader.readTsx(tmxTileset.getSource());

            result.tsxTilesets.add(new Tileset(tmxTileset.getSource(), tmxTileset.getFirstgid(), tsxTileset));
        }
        
        return result;
    }
}
