package com.github.wmarkow.klondiklon.event.events;

import com.github.wmarkow.klondiklon.event.Event;

public class TouchLongDownEvent extends Event
{
    private int screenX;
    private int screenY;

    public TouchLongDownEvent(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
    }

    public int getScreenX()
    {
        return screenX;
    }

    public int getScreenY()
    {
        return screenY;
    }
}
