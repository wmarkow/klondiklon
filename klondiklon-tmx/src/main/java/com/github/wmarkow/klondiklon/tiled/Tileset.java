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
     *            first gid in thise tileset, comes from TMX file
     * @param tilesetElement
     *            comes from TSX file
     */
    public Tileset(String source, int firstGid, TilesetElement tilesetElement)
    {
        this.source = source;
        this.firstGid = firstGid;
        this.tilesetElement = tilesetElement;
    }

}
