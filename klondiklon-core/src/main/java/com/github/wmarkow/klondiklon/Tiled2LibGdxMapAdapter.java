package com.github.wmarkow.klondiklon;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.mapeditor.core.Properties;
import org.mapeditor.core.Property;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class Tiled2LibGdxMapAdapter extends TiledMap
{
    private static Logger LOGGER = LoggerFactory.getLogger(Tiled2LibGdxMapAdapter.class);

    private org.mapeditor.core.Map tiledMap;
    private Map<TextureKey, TextureRegion> texturesCache = new HashMap<TextureKey, TextureRegion>();
    private boolean flipY = true;

    public Tiled2LibGdxMapAdapter(org.mapeditor.core.Map tiledMap) {
        this.tiledMap = tiledMap;

        wrap();
    }

    @Override
    public MapLayers getLayers()
    {
        return super.getLayers();
    }

    @Override
    public MapProperties getProperties()
    {
        return super.getProperties();
    }

    @Override
    public void dispose()
    {
    }

    private void wrap()
    {
        MapProperties mapProperties = getProperties();
        if (tiledMap.getOrientation() != null)
        {
            mapProperties.put("orientation", tiledMap.getOrientation().toString());
        }
        mapProperties.put("width", tiledMap.getWidth());
        mapProperties.put("height", tiledMap.getHeight());
        mapProperties.put("tilewidth", tiledMap.getTileWidth());
        mapProperties.put("tileheight", tiledMap.getTileHeight());
        mapProperties.put("hexsidelength", tiledMap.getHexSideLength());
        if (tiledMap.getStaggerAxis() != null)
        {
            mapProperties.put("staggeraxis", tiledMap.getStaggerAxis().toString());
        }
        if (tiledMap.getStaggerIndex() != null)
        {
            mapProperties.put("staggerindex", tiledMap.getStaggerIndex().toString());
        }
        if (tiledMap.getBackgroundcolor() != null)
        {
            mapProperties.put("backgroundcolor", tiledMap.getBackgroundcolor());
        }

        for (org.mapeditor.core.MapLayer tiledLayer : tiledMap.getLayers())
        {
            MapLayer libGdxMapLayer = toLibGdxMapLayer(tiledLayer);
            if (libGdxMapLayer != null)
            {
                getLayers().add(libGdxMapLayer);
            }
        }
    }

    private MapLayer toLibGdxMapLayer(org.mapeditor.core.MapLayer tiledMapLayer)
    {
        final int mapWidth = tiledMapLayer.getMap().getWidth();
        final int mapHeight = tiledMapLayer.getMap().getHeight();
        final int tileWidth = tiledMapLayer.getMap().getTileWidth();
        final int tileHeight = tiledMapLayer.getMap().getTileHeight();

        MapLayer libGdxMapLayer = null;

        if (tiledMapLayer instanceof TileLayer)
        {
            libGdxMapLayer = new TiledMapTileLayer(mapWidth, mapHeight, tileWidth, tileHeight);

            handleTileLayer((TiledMapTileLayer) libGdxMapLayer, (TileLayer) tiledMapLayer);
        } else
        {
            // not supported map
            LOGGER.warn(String.format("Not supported %s map layer. It will be not imported to libGdx.",
                    tiledMapLayer.getClass().getName()));
            return null;
        }

        libGdxMapLayer.setName(tiledMapLayer.getName());
        if (tiledMapLayer.getOffsetX() != null)
        {
            libGdxMapLayer.setOffsetX(tiledMapLayer.getOffsetX());
        }
        if (tiledMapLayer.getOffsetY() != null)
        {
            libGdxMapLayer.setOffsetY(tiledMapLayer.getOffsetY());
        }
        if (tiledMapLayer.getOpacity() != null)
        {
            libGdxMapLayer.setOpacity(tiledMapLayer.getOpacity());
        }
        // libGdxMapLayer.setParent();
        if (tiledMapLayer.isVisible() == null)
        {
            libGdxMapLayer.setVisible(true);
        } else
        {
            libGdxMapLayer.setVisible(tiledMapLayer.isVisible());
        }

        Properties properties = tiledMapLayer.getProperties();
        for (Property tiledProperty : properties.getProperties())
        {
            final String name = tiledProperty.getName();
            final String value = tiledProperty.getValue();

            libGdxMapLayer.getProperties().put(name, value);
        }

        return libGdxMapLayer;
    }

    private void handleTileLayer(TiledMapTileLayer libGdxMapLayer, TileLayer tiledMapLayer)
    {
        final int mapWidth = tiledMapLayer.getMap().getWidth();
        final int mapHeight = tiledMapLayer.getMap().getHeight();

        for (int y = 0; y < mapHeight; y++)
        {
            for (int x = 0; x < mapWidth; x++)
            {
                Tile tile = tiledMapLayer.getTileAt(x, y);
                if (tile == null)
                {
                    continue;
                }
                final String sourceFilePath = tile.getTileSet().getTilebmpFile();
                final int tileId = tile.getId();

                TextureKey textureKey = new TextureKey(sourceFilePath, tileId);

                if (!texturesCache.containsKey(textureKey))
                {
                    Pixmap pixmap = toPixmap(tile.getImage());
                    texturesCache.put(textureKey, new TextureRegion(new Texture(pixmap)));
                }

                TextureRegion textureRegion = texturesCache.get(textureKey);
                StaticTiledMapTile libGdxTile = new StaticTiledMapTile(textureRegion);
                Cell libGdxCell = new Cell();
                libGdxCell.setTile(libGdxTile);

                final int newY = flipY ? mapHeight - 1 - y : y;

                libGdxMapLayer.setCell(x, newY, libGdxCell);
            }
        }
    }

    private Pixmap toPixmap(BufferedImage bi)
    {
        final int imageWidth = bi.getWidth();
        final int imageHeight = bi.getHeight();

        Pixmap pixmap = new Pixmap(imageWidth, imageHeight, Format.RGBA8888);

        for (int xx = 0; xx < imageWidth; xx++)
        {
            for (int yy = 0; yy < imageHeight; yy++)
            {
                final int rgb = bi.getRGB(xx, yy);
                final float r = bi.getColorModel().getRed(rgb);
                final float g = bi.getColorModel().getGreen(rgb);
                final float b = bi.getColorModel().getBlue(rgb);
                final float a = bi.getColorModel().getAlpha(rgb);
                final int rgba = Color.rgba8888(r / 255f, g / 255f, b / 255f, a / 255f);

                pixmap.drawPixel(xx, yy, rgba);
            }
        }

        return pixmap;
    }

    private class TextureKey
    {
        private String sourceFilePath;
        private int id;

        public TextureKey(String sourceFilePath, int id) {
            this.sourceFilePath = sourceFilePath;
            this.id = id;
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + id;
            result = prime * result + ((sourceFilePath == null) ? 0 : sourceFilePath.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            TextureKey other = (TextureKey) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (id != other.id)
                return false;
            if (sourceFilePath == null)
            {
                if (other.sourceFilePath != null)
                    return false;
            } else if (!sourceFilePath.equals(other.sourceFilePath))
                return false;
            return true;
        }

        private Tiled2LibGdxMapAdapter getOuterType()
        {
            return Tiled2LibGdxMapAdapter.this;
        }
    }
}
