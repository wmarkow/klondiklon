package com.github.wmarkow.klondiklon.map.coordinates.gdx;

import com.badlogic.gdx.math.Vector3;

/***
 * LibGdx Touch Coordinates
 * <ul>
 * <li>Units: pixels</li>
 * <li>System: y-down</li>
 * <li>Type: integer, can't be fractional</li>
 * <li>Range: (0,0) (upper left corner) to (Gdx.graphics.getWidth()-1,
 * Gdx.graphics.getHeight()-1) (lower right corner)</li>
 * <li>Usage: touch/mouse coordinates</li>
 * <li>Dependence: device specific</li>
 * </ul>
 * 
 * @see <a href="https://github.com/libgdx/libgdx/wiki/Coordinate-systems#touch-coordinates">LibGdx Touch Coordinates</a>
 * @author wmarkowski
 *
 */
public class GdxTouchCoordinates
{
    private int x;
    private int y;

    public GdxTouchCoordinates(int x, int y) {
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
