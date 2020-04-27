package com.github.wmarkow.klondiklon.tiled;

import com.github.wmarkow.klondiklon.tiled.tsx.TilesetElement;

/***
 * A wrapper class around {@link TilesetElement} .
 * 
 *
 */
public class Tileset
{
    private int firstGid;
    private String source;
    private TilesetElement tilesetElement;

    /***
     * 
     * @param source
     *            TSX file path, comes from TMX file
     * @param firstGid
     *            first gid in this tileset, comes from TMX file
     * @param tilesetElement
     *            comes from TSX file
     */
    public Tileset(String source, int firstGid, TilesetElement tilesetElement) {
        this.source = source;
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

        if (tileGid == getFirstGid() && tileGid == getLastGid())
        {
            // only one tile in the tileset
            int width = tilesetElement.getImage().getWidth();
            int height = tilesetElement.getImage().getHeight();
            return new TileInfo(tilesetElement.getImage().getSource(), tileGid, 0, 0, width, height);
        }

        int tileNumber = tileGid - getFirstGid();
        int row = tileNumber / tilesetElement.getColumns();
        int column = tileNumber % tilesetElement.getColumns();
        int width = tilesetElement.getTilewidth();
        int height = tilesetElement.getTileheight();
        int startX = column * width;
        int startY = row * height;

        return new TileInfo(tilesetElement.getImage().getSource(), tileGid, startX, startY, width, height);
    }

    public int getFirstGid()
    {
        return firstGid;
    }

    public int getLastGid()
    {
        return firstGid + tilesetElement.getTilecount() - 1;
    }
}
