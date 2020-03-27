package com.github.wmarkow.klondiklon.map.coordinates.gdx;

public class GdxScreenBounds
{
    private int x;
    private int y;
    private int width;
    private int height;

    public GdxScreenBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public boolean containsPoint(GdxScreenCoordinates point)
    {
        if (point.getX() < x)
        {
            return false;
        }

        if (point.getX() > x + width)
        {
            return false;
        }

        if (point.getY() < y)
        {
            return false;
        }

        if (point.getY() > y + height)
        {
            return false;
        }

        return true;
    }
}
