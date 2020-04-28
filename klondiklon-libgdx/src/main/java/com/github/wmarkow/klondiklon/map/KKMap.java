package com.github.wmarkow.klondiklon.map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.github.wmarkow.klondiklon.map.coordinates.CoordinateCalculator;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.tmx.TmxIsoCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.tmx.TmxOrthoCoordinates;
import com.github.wmarkow.klondiklon.map.objects.KKMapObject;
import com.github.wmarkow.klondiklon.map.objects.KKMapObjectIf;
import com.github.wmarkow.klondiklon.tiled.TileInfo;
import com.github.wmarkow.klondiklon.tiled.TmxLayer;
import com.github.wmarkow.klondiklon.tiled.TmxObjectGroupLayer;
import com.github.wmarkow.klondiklon.tiled.TmxTileLayer;
import com.github.wmarkow.klondiklon.tiled.TmxTiledMap;

public class KKMap extends TiledMap implements KKMapIf
{
    private static Logger LOGGER = LoggerFactory.getLogger(KKMap.class);

    private TmxTiledMap tiledMap;
    private Map<TextureKey, TextureRegion> texturesCache = new HashMap<TextureKey, TextureRegion>();
    private boolean flipY = true;
    private KKMapObjectsFactoryIf objectsFactory = new KKMapObjectsFactory();

    public KKMap(TmxTiledMap tiledMap) {
        this.tiledMap = tiledMap;

        wrap();
    }

    public KKMap(TmxTiledMap tiledMap, KKMapObjectsFactoryIf objectsFactory) {
        this.tiledMap = tiledMap;
        this.objectsFactory = objectsFactory;

        wrap();
    }

    @Override
    public int getHeightInTiles()
    {
        return (int) getProperties().get("height");
    }

    @Override
    public int getTileHeightInPixels()
    {
        return (int) getProperties().get("tileheight");
    }

    @Override
    public KKMapObjectIf[] getObjects()
    {
        KKObjectsLayer objectsLayer = getObjectsLayer();

        if (objectsLayer == null)
        {
            return new KKMapObjectIf[]
            {};
        }

        return objectsLayer.getMapObjects();
    }

    @Override
    public KKMapObjectIf getObject(int id)
    {
        for (KKMapObjectIf mapObject : getObjects())
        {
            if (mapObject.getId() == id)
            {
                return mapObject;
            }
        }

        return null;
    }

    @Override
    public void removeObject(KKMapObjectIf object)
    {
        // TODO: for now not supported yet

        // KKObjectsLayer objectsLayer = getObjectsLayer();
        //
        // if (objectsLayer == null)
        // {
        // return;
        // }
        //
        // objectsLayer.removeObject(object);
        //
        // // delete the object also from TMX map
        // final int id = object.getId();
        // for (org.mapeditor.core.MapLayer tiledLayer : tiledMap.getLayers())
        // {
        // if (tiledLayer instanceof org.mapeditor.core.ObjectGroup)
        // {
        // List<org.mapeditor.core.MapObject> objectsToRemove = new
        // ArrayList<org.mapeditor.core.MapObject>();
        //
        // org.mapeditor.core.ObjectGroup objectGroup = (org.mapeditor.core.ObjectGroup)
        // tiledLayer;
        // for (org.mapeditor.core.MapObject mapObject : objectGroup.getObjects())
        // {
        // if (mapObject.getId().equals(id))
        // {
        // objectsToRemove.add(mapObject);
        // }
        // }
        //
        // for (org.mapeditor.core.MapObject objectToRemove : objectsToRemove)
        // {
        // objectGroup.removeObject(objectToRemove);
        // }
        // }
        // }
    }

    @Override
    public void setObjectCoordinates(KKMapObjectIf object, GdxWorldOrthoCoordinates newCoordinates)
    {
        // TODO: for now not supported yet

        // KKMapObject gdxMapObject = (KKMapObject) object;
        // gdxMapObject.setX(newCoordinates.x);
        // gdxMapObject.setY(newCoordinates.y);
        //
        // // save the new coordinates also in TMX map
        // final int id = object.getId();
        //
        // org.mapeditor.core.MapObject tiledMapObject = getTiledObjectById(tiledMap,
        // id);
        // // in TMX objects coordinates are in TMX Isometric, need to convert
        // CoordinateCalculator coordinateCalculator = new CoordinateCalculator();
        // final int tileMapHeightInTiles = tiledMap.getHeight();
        // final int tileHeightInPixels = tiledMap.getTileHeight();
        //
        // TmxIsoCoordinates tmxIsoCoordinates =
        // coordinateCalculator.world2TmxIso(tileMapHeightInTiles,
        // tileHeightInPixels, newCoordinates);
        //
        // tiledMapObject.setX(tmxIsoCoordinates.x);
        // tiledMapObject.setY(tmxIsoCoordinates.y);
    }

    @Override
    public TmxTiledMap getTmxTiledMap()
    {
        return tiledMap;
    }

    @Override
    public void dispose()
    {
    }

    private KKObjectsLayer getObjectsLayer()
    {
        for (MapLayer mapLayer : getLayers())
        {
            if (mapLayer instanceof KKObjectsLayer)
            {
                return (KKObjectsLayer) mapLayer;
            }
        }

        return null;
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

        for (TmxLayer tiledLayer : tiledMap.getLayers())
        {
            MapLayer libGdxMapLayer = toLibGdxMapLayer(tiledLayer);
            if (libGdxMapLayer != null)
            {
                getLayers().add(libGdxMapLayer);
            }
        }
    }

    private MapLayer toLibGdxMapLayer(TmxLayer tiledMapLayer)
    {
        MapLayer libGdxMapLayer = null;

        if (tiledMapLayer instanceof TmxTileLayer)
        {
            TmxTileLayer tmxTileLayer = (TmxTileLayer) tiledMapLayer;
            int width = tmxTileLayer.getWidth();
            int height = tmxTileLayer.getHeight();
            int tileWidth = tmxTileLayer.getTileWidth();
            int tileHeight = tmxTileLayer.getTileHeight();

            libGdxMapLayer = new TiledMapTileLayer(width, height, tileWidth, tileHeight);

            handleTileLayer((TiledMapTileLayer) libGdxMapLayer, (TmxTileLayer) tiledMapLayer);
        } else if (tiledMapLayer instanceof TmxObjectGroupLayer)
        {
            libGdxMapLayer = new KKObjectsLayer();

            handleObjectGroupLayer((KKObjectsLayer) libGdxMapLayer, (TmxObjectGroupLayer) tiledMapLayer);
        } else
        {
            // not supported map layer
            LOGGER.warn(String.format("Not supported %s map layer. It will be not imported to libGdx.",
                    tiledMapLayer.getClass().getName()));
            return null;
        }

        libGdxMapLayer.setName(tiledMapLayer.getName());
        libGdxMapLayer.setVisible(true);

        return libGdxMapLayer;
    }

    private void handleTileLayer(TiledMapTileLayer libGdxMapLayer, TmxTileLayer tiledMapLayer)
    {
        final int mapWidth = tiledMapLayer.getWidth();
        final int mapHeight = tiledMapLayer.getHeight();

        for (int y = 0; y < mapHeight; y++)
        {
            for (int x = 0; x < mapWidth; x++)
            {
                TileInfo tileInfo = tiledMapLayer.getTileInfoAt(x, y);
                if (tileInfo == null)
                {
                    continue;
                }
                final String sourceFilePath = tileInfo.getImagePath();
                final int tileId = tileInfo.getGid();

                TextureKey textureKey = new TextureKey(sourceFilePath, tileId);
                if (!texturesCache.containsKey(textureKey))
                {
                    // TODO: read image as Pixmap here
                    // Pixmap pixmap = toPixmap(tile.getImage());
                    // texturesCache.put(textureKey, new TextureRegion(new Texture(pixmap)));
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

    private void handleObjectGroupLayer(KKObjectsLayer objectsMapLayer, TmxObjectGroupLayer tiledMapLayer)
    {
        // TODO: for now not supported yet

        // for (org.mapeditor.core.MapObject tiledMapObject :
        // tiledMapLayer.getObjects())
        // {
        // final org.mapeditor.core.Tile tile = tiledMapObject.getTile();
        // // derive object type from TMX tile
        // final String objectType =
        // tile.getProperties().getProperty(KKMapObjectIf.PROPERTY_TYPE_KEY);
        // loadTexturesFromTileSet(tile.getTileSet());
        //
        // final String sourceFilePath = tile.getTileSet().getTilebmpFile();
        // final int tileId = tile.getId();
        // final int objectId = tiledMapObject.getId();
        // TextureKey textureKey = new TextureKey(sourceFilePath, tileId);
        // TextureRegion textureRegion = texturesCache.get(textureKey);
        //
        // KKMapObject libGdxMapObject = null;
        // if (tile.getAnimation() == null)
        // {
        // StaticTiledMapTile libGdxTile = new StaticTiledMapTile(textureRegion);
        // libGdxMapObject = objectsFactory.create(libGdxTile, objectId, objectType);
        // } else
        // {
        // IntArray intervals = new IntArray();
        // Array<StaticTiledMapTile> frameTiles = new Array<StaticTiledMapTile>();
        // for (org.mapeditor.core.Frame frame : tile.getAnimation().getFrame())
        // {
        // intervals.add(frame.getDuration());
        // TextureKey frameTextureKey = new TextureKey(sourceFilePath,
        // frame.getTileid());
        // TextureRegion frameTextureRegion = texturesCache.get(frameTextureKey);
        // StaticTiledMapTile frameStaticTiledMapTile = new
        // StaticTiledMapTile(frameTextureRegion);
        // frameTiles.add(frameStaticTiledMapTile);
        // }
        //
        // AnimatedTiledMapTile libGdxTile = new AnimatedTiledMapTile(new
        // IntArray(intervals), frameTiles);
        // libGdxMapObject = objectsFactory.create(libGdxTile, objectId, objectType);
        // }
        //
        // CoordinateCalculator coordinateCalculator = new CoordinateCalculator();
        // final int tileMapHeightInTiles = tiledMapLayer.getMap().getHeight();
        // final int tileHeightInPixels = tiledMapLayer.getMap().getTileHeight();
        //
        // // in TMX objects coordinates are in TMX Isometric
        // TmxIsoCoordinates tmxIsoCoordinates = new TmxIsoCoordinates((float)
        // tiledMapObject.getX(),
        // (float) tiledMapObject.getY(), 0.0f);
        // TmxOrthoCoordinates tmxOrthogonal =
        // coordinateCalculator.tmxIso2tmxOrthogonal(tileMapHeightInTiles,
        // tileHeightInPixels, tmxIsoCoordinates);
        // GdxWorldOrthoCoordinates world =
        // coordinateCalculator.tmxOrthogonal2world(tileMapHeightInTiles,
        // tileHeightInPixels, tmxOrthogonal);
        //
        // // FIXME: I'm not sure but it would be good to handle an object anchor here
        // libGdxMapObject.setX(world.x);
        // libGdxMapObject.setY(world.y);
        //
        // libGdxMapObject.setName(tiledMapObject.getName());
        // if (tiledMapObject.isVisible() == null)
        // {
        // libGdxMapObject.setVisible(true);
        // } else
        // {
        // libGdxMapObject.setVisible(tiledMapObject.isVisible());
        // }
        //
        // objectsMapLayer.getObjects().add(libGdxMapObject);
        // }
    }

    private void loadTexturesFromTileSet(org.mapeditor.core.TileSet tileSet)
    {
        if (tileSet == null)
        {
            throw new IllegalArgumentException("TileSet must not be null");
        }
        if (tileSet.getTilecount() == null)
        {
            throw new IllegalArgumentException("tilecount in TileSet must not be null");
        }

        final String sourceFilePath = tileSet.getTilebmpFile();
        for (int q = 0; q < tileSet.getTilecount(); q++)
        {
            final org.mapeditor.core.Tile tile = tileSet.getTile(q);
            final int tileId = tile.getId();

            TextureKey textureKey = new TextureKey(sourceFilePath, tileId);
            if (!texturesCache.containsKey(textureKey))
            {
                Pixmap pixmap = toPixmap(tile.getImage());
                texturesCache.put(textureKey, new TextureRegion(new Texture(pixmap)));
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

    private org.mapeditor.core.MapObject getTiledObjectById(org.mapeditor.core.Map tiledMap, int id)
    {
        for (org.mapeditor.core.MapLayer tiledLayer : tiledMap.getLayers())
        {
            if (tiledLayer instanceof org.mapeditor.core.ObjectGroup)
            {
                org.mapeditor.core.ObjectGroup objectGroup = (org.mapeditor.core.ObjectGroup) tiledLayer;
                for (org.mapeditor.core.MapObject mapObject : objectGroup.getObjects())
                {
                    if (mapObject.getId().equals(id))
                    {
                        return mapObject;
                    }
                }
            }
        }

        return null;
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

        private KKMap getOuterType()
        {
            return KKMap.this;
        }
    }
}
