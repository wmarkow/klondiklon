package com.github.wmarkow.klondiklon.tiled;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.wmarkow.klondiklon.tiled.tsx.FrameElement;
import com.github.wmarkow.klondiklon.tiled.tsx.PropertyElement;
import com.github.wmarkow.klondiklon.tiled.tsx.TileElement;
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
    Tileset(String tsxPath, int firstGid, TilesetElement tilesetElement) {
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
    public Tile getTile(int tileGid)
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

            return Tile.valueOfStaticTile(tileInfo);
        }

        TileElement tileElement = tilesetElement.getTile();
        if (tileElement != null)
        {
            List<FrameElement> animationFrames = tileElement.getAnimationFrames();
            if (animationFrames.size() > 1)
            {
                // we have an animation
                List<TileFrameInfo> tfi = new ArrayList<TileFrameInfo>();
                for (FrameElement frameElement : animationFrames)
                {
                    int tileNumber = frameElement.getTileid();
                    int row = tileNumber / tilesetElement.getColumns();
                    int column = tileNumber % tilesetElement.getColumns();
                    int width = tilesetElement.getTilewidth();
                    int height = tilesetElement.getTileheight();
                    int startX = column * width;
                    int startY = row * height;

                    TileInfo tileInfo = new TileInfo(imageFile.getAbsolutePath(), tileGid + tileNumber, startX, startY,
                            width, height);
                    copyProperties(tilesetElement, tileInfo);

                    tfi.add(new TileFrameInfo(tileInfo, frameElement.getDuration()));
                }

                TileFrameInfo[] tileFrameInfos = tfi.toArray(new TileFrameInfo[]
                {});
                return Tile.valueOfAnimatedTile(tileFrameInfos);
            }
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

        return Tile.valueOfStaticTile(tileInfo);
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
        if (tilesetElement.getTile() == null)
        {
            return;
        }

        for (PropertyElement propertyElement : tilesetElement.getTile().getProperties())
        {
            tileInfo.addProperty(propertyElement.getName(), propertyElement.getValue());
        }
    }
}
