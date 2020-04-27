package com.github.wmarkow.klondiklon.tiled;

/***
 * All necessary information related to the tile.
 *
 */
public class TileInfo
{
    private String imagePath;
    private int gid;
    private int startX;
    private int startY;
    private int width;
    private int height;

    public TileInfo(String imagePath, int gid, int startX, int startY, int width, int height) {
        this.gid = gid;
        this.imagePath = imagePath;
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
    }

    /***
     * Gets the path to the tile image. Path is relative to the TMX file. Remark: it
     * is possible that the image contains many tiles inside.
     * 
     * @return
     */
    public String getImagePath()
    {
        return imagePath;
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
