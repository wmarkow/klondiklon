package com.github.wmarkow.klondiklon.tiled;

import java.io.File;

import com.github.wmarkow.klondiklon.tiled.tsx.PropertyElement;
import com.github.wmarkow.klondiklon.tiled.tsx.TilesetElement;

/***
 * A wrapper class around {@link TilesetElement} .
 * 
 *
 */
public class Tileset
{
    private int firstGid;
    private String tsxPath;
    private TilesetElement tilesetElement;

    /***
     * 
     * @param source
     *            TSX file path
     * @param firstGid
     *            first gid in this tileset, comes from TMX file
     * @param tilesetElement
     *            comes from TSX file
     */
    public Tileset(String tsxPath, int firstGid, TilesetElement tilesetElement) {
        this.tsxPath = tsxPath;
        this.firstGid = firstGid;
        this.tilesetElement = tilesetElement;
    }

    /***
     * Gets the TileInfo based on the tile gid (global id).
     * 
     * @param tileGid
     *            global id of the tile
     * @return TileInfo if the tile belongs to this TileSet or null otherwise
     */
    public TileInfo getTileInfo(int tileGid)
    {
        if (tileGid < getFirstGid())
        {
            return null;
        }
        if (tileGid > getLastGid())
        {
            return null;
        }

        File tsxFile = new File(tsxPath);
        File tsxDir = tsxFile.getParentFile();
        File imageFile = new File(tsxDir, tilesetElement.getImage().getSource());

        if (tileGid == getFirstGid() && tileGid == getLastGid())
        {
            // only one tile in the tileset
            int width = tilesetElement.getImage().getWidth();
            int height = tilesetElement.getImage().getHeight();

            TileInfo tileInfo = new TileInfo(imageFile.getAbsolutePath(), tileGid, 0, 0, width, height);
            copyProperties(tilesetElement, tileInfo);

            return tileInfo;
        }

        int tileNumber = tileGid - getFirstGid();
        int row = tileNumber / tilesetElement.getColumns();
        int column = tileNumber % tilesetElement.getColumns();
        int width = tilesetElement.getTilewidth();
        int height = tilesetElement.getTileheight();
        int startX = column * width;
        int startY = row * height;

        TileInfo tileInfo = new TileInfo(imageFile.getAbsolutePath(), tileGid, startX, startY, width, height);
        copyProperties(tilesetElement, tileInfo);

        return tileInfo;
    }

    public int getFirstGid()
    {
        return firstGid;
    }

    public int getLastGid()
    {
        return firstGid + tilesetElement.getTilecount() - 1;
    }
    
    private void copyProperties(TilesetElement tilesetElement, TileInfo tileInfo)
    {
        if(tilesetElement.getTile() == null)
        {
            return;
        }
        
        for (PropertyElement propertyElement : tilesetElement.getTile().getProperties())
        {
            tileInfo.addProperty(propertyElement.getName(), propertyElement.getValue());
        }
    }
}
