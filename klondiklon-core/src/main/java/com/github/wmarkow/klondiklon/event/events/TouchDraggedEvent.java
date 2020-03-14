package com.github.wmarkow.klondiklon.event.events;

import com.github.wmarkow.klondiklon.event.Event;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxTouchCoordinates;

public class TouchDraggedEvent extends Event
{
    private GdxTouchCoordinates coordinates;

    public TouchDraggedEvent(GdxTouchCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public GdxTouchCoordinates getGdxTouchCoordinates()
    {
        return coordinates;
    }
}
