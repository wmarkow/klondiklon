package com.github.wmarkow.klondiklon.map.coordinates.gdx;

import com.badlogic.gdx.math.Vector3;

/***
 * LibGdx Touch Coordinates
 * <ul>
 * <li>Units: pixels</li>
 * <li>System: y-up</li>
 * <li>Type: integer, can't be fractional</li>
 * <li>Range: (0,0) (lower left corner) to (Gdx.graphics.getWidth()-1,
 * Gdx.graphics.getHeight()-1) (upper right corner)</li>
 * <li>Usage: viewport, scissors and pixmap</li>
 * <li>Dependence: device/resource/asset specific</li>
 * </ul>
 * 
 * @see <a href=
 *      "https://github.com/libgdx/libgdx/wiki/Coordinate-systems#screen-or-image-coordinates">LibGdx
 *      Screen Coordinates</a>
 * @author wmarkowski
 *
 */
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
