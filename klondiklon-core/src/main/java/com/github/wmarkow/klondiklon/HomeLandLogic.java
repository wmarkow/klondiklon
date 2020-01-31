package com.github.wmarkow.klondiklon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.github.wmarkow.klondiklon.event.Event;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.event.EventSubscriber;
import com.github.wmarkow.klondiklon.event.events.TouchUpEvent;
import com.github.wmarkow.klondiklon.map.KKTiledMap;
import com.github.wmarkow.klondiklon.map.coordinates.CoordinateCalculator;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxWorldOrthoCoordinates;

public class HomeLandLogic implements EventSubscriber
{
    private static Logger LOGGER = LoggerFactory.getLogger(HomeLandLogic.class);

    private EventBus eventBus;
    private KKTiledMap map;
    private Camera camera;

    public HomeLandLogic(EventBus eventBus, KKTiledMap map, Camera camera) {
        this.eventBus = eventBus;
        this.map = map;
        this.camera = camera;

        this.eventBus.subscribe(TouchUpEvent.class, this);
    }

    @Override
    public void onEvent(Event event)
    {
        if (event instanceof TouchUpEvent)
        {
            processTouchUpEvent((TouchUpEvent) event);
        }
    }

    private void processTouchUpEvent(TouchUpEvent event)
    {
        LOGGER.info(String.format("Event %s received x=%s, y=%s.", event.getClass().getSimpleName(), event.getScreenX(),
                event.getScreenY()));

        CoordinateCalculator coordinateCalculator = new CoordinateCalculator();

        GdxScreenCoordinates screenCoordinates = new GdxScreenCoordinates(event.getScreenX(), event.getScreenY());
        GdxWorldOrthoCoordinates gdxWorldCoordinates = coordinateCalculator.screen2World(camera, screenCoordinates);

        int count = 0;
        for (MapObject mapObject : map.getObjects())
        {
            if (!(mapObject instanceof TextureMapObject))
            {
                continue;
            }
            TextureMapObject textureObject = (TextureMapObject) mapObject;

            float boundsX = textureObject.getX();
            float boundsY = textureObject.getY();
            float boundsWidth = textureObject.getTextureRegion().getRegionWidth();
            float boundsHeight = textureObject.getTextureRegion().getRegionHeight();

            if (isPointInBounds(gdxWorldCoordinates, boundsX, boundsY, boundsWidth, boundsHeight))
            {
                LOGGER.info(String.format("Object clicked anchor(x,y)=(%s,%s), (width,height)=(%s,%s)", textureObject.getX(), textureObject.getY(), boundsWidth,
                        boundsHeight));
                count++;
            }
        }
        if (count == 0)
        {
            LOGGER.info(String.format("No object selected", event.getClass().getSimpleName(), event.getScreenX(),
                    event.getScreenY()));
        }
    }

    /***
     * Remember that the anchor of the object is a BOTTOM_MIDLE
     * @param point
     * @param boundsX
     * @param boundsY
     * @param boundsWidth
     * @param boundsHeight
     * @return
     */
    private boolean isPointInBounds(GdxWorldOrthoCoordinates point, float boundsX, float boundsY, float boundsWidth,
            float boundsHeight)
    {
        float pointX = point.x;
        float pointY = point.y;

        if (pointX < boundsX - boundsWidth / 2.0)
        {
            return false;
        }
        if (pointX > boundsX + boundsWidth / 2.0)
        {
            return false;
        }

        if (pointY < boundsY)
        {
            return false;
        }
        if (pointY > boundsY + boundsHeight)
        {
            return false;
        }

        return true;
    }
}
