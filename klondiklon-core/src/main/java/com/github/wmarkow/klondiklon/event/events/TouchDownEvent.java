package com.github.wmarkow.klondiklon.event.events;

import com.github.wmarkow.klondiklon.event.Event;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxTouchCoordinates;

public class TouchDownEvent extends Event
{
    private GdxTouchCoordinates coordinates;

    public TouchDownEvent(GdxTouchCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public GdxTouchCoordinates getGdxTouchCoordinates()
    {
        return coordinates;
    }
}
