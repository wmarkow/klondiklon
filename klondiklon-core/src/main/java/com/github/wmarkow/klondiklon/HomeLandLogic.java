package com.github.wmarkow.klondiklon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wmarkow.klondiklon.event.Event;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.event.EventSubscriber;
import com.github.wmarkow.klondiklon.event.events.TouchUpEvent;

public class HomeLandLogic implements EventSubscriber
{
    private static Logger LOGGER = LoggerFactory.getLogger(HomeLandLogic.class);

    private EventBus eventBus;

    public HomeLandLogic(EventBus eventBus) {
        this.eventBus = eventBus;

        this.eventBus.subscribe(TouchUpEvent.class, this);
    }

    @Override
    public void onEvent(Event event)
    {
        if (event instanceof TouchUpEvent)
        {
            TouchUpEvent touchUpEvent = (TouchUpEvent) event;
            LOGGER.info(String.format("Event %s received x=%s, y=%s.", touchUpEvent.getClass().getSimpleName(),
                    touchUpEvent.getScreenX(), touchUpEvent.getScreenY()));
        }
    }

}
