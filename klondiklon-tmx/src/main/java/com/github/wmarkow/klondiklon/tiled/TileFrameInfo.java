package com.github.wmarkow.klondiklon.tiled;

public class TileFrameInfo
{
    private TileInfo tileInfo;
    private int durationInMillis;

    TileFrameInfo(TileInfo tileInfo, int durationInMillis) {
        this.tileInfo = tileInfo;
        this.durationInMillis = durationInMillis;
    }

    public TileInfo getTileInfo()
    {
        return tileInfo;
    }

    public int getDurationInMillis()
    {
        return durationInMillis;
    }
}
