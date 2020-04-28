package com.github.wmarkow.klondiklon.tiled;

/***
 * All necessary information related to the tile.
 *
 */
public class TileInfo
{
    private String absoluteImagePath;
    private int gid;
    private int startX;
    private int startY;
    private int width;
    private int height;

    public TileInfo(String absoluteImagePath, int gid, int startX, int startY, int width, int height) {
        this.gid = gid;
        this.absoluteImagePath = absoluteImagePath;
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
    }

    /***
     * Gets the absolute path to the tile image. Remark: it is possible that the
     * image contains many tiles inside.
     * 
     * @return
     */
    public String getAbsoluteImagePath()
    {
        return absoluteImagePath;
    }

    public int getGid()
    {
        return gid;
    }

    /***
     * Gets the X coordinate in the image, where the specific tile image begins.
     * 
     * @return coordinate in pixels in x-right system
     */
    public int getStartX()
    {
        return startX;
    }

    /***
     * Gets the Y coordinate in the image, where the specific tile image begins.
     * 
     * @return coordinate in pixels in y-down system
     */
    public int getStartY()
    {
        return startY;
    }

    /***
     * Gets the width of the tile.
     * 
     * @return width in pixels
     */
    public int getWidth()
    {
        return width;
    }

    /***
     * Gets the height of the tile.
     * 
     * @return height in pixels
     */
    public int getHeight()
    {
        return height;
    }
}
