package com.github.wmarkow.klondiklon.tiled;

public class Tile
{
    private TileInfo tileInfo;
    private TileFrameInfo[] tileFrameInfos;

    private Tile(TileInfo tileInfo) {
        this.tileInfo = tileInfo;
    }

    private Tile(TileFrameInfo[] tileFrameInfos) {
        this.tileFrameInfos = tileFrameInfos;
    }

    public TileInfo getTileInfo()
    {
        return tileInfo;
    }

    public TileFrameInfo[] getTileFrameInfos()
    {
        return tileFrameInfos;
    }

    final static Tile valueOfStaticTile(TileInfo tileInfo)
    {
        return new Tile(tileInfo);
    }

    final static Tile valueOfAnimatedTile(TileFrameInfo[] tileFrameInfos)
    {
        return new Tile(tileFrameInfos);
    }
}
