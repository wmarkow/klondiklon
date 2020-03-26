package com.github.wmarkow.klondiklon.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBus
{
    private static Logger LOGGER = LoggerFactory.getLogger(EventBus.class);

    private Map<Class<? extends Event>, Set<EventSubscriber>> subscribers = new HashMap<Class<? extends Event>, Set<EventSubscriber>>();

    public void subscribe(Class<? extends Event> eventClass, EventSubscriber subscriber)
    {
        if (!subscribers.containsKey(eventClass))
        {
            subscribers.put(eventClass, new HashSet<>());
        }

        subscribers.get(eventClass).add(subscriber);
    }

    public void publish(Event asd)
    {
        if (!subscribers.containsKey(asd.getClass()))
        {
            return;
        }

        for (EventSubscriber subscriber : subscribers.get(asd.getClass()))
        {
            try
            {
                subscriber.onEvent(asd);
            } catch (Exception ex)
            {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }
}
