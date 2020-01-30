package com.github.wmarkow.klondiklon.event.events;

import com.github.wmarkow.klondiklon.event.Event;

public class TouchUpEvent extends Event
{
    final public static String EVENT_TOUCH_UP = "EVENT_TOUCH_UP";

    private int screenX;
    private int screenY;

    public TouchUpEvent(int screenX, int screenY) {
        super(EVENT_TOUCH_UP);

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
