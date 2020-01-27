package com.github.wmarkow.klondiklon.map.coordinates.gdx;

import com.badlogic.gdx.math.Vector3;

public class GdxScreenCoordinates
{
    private int x;
    private int y;

    public GdxScreenCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Vector3 toVector3()
    {
        return new Vector3(x, y, 0);
    }
}
