package com.github.wmarkow.klondiklon.player;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.event.events.PlayerEnergyChangedEvent;

public class Player
{
    private final static int MAX_RESTORABLE_ENERGY = 1000;
    @JsonProperty("energy")
    private int energy = 1000;
    private EventBus eventBus;

    Player() {
        // default package protected constructor for deserialisation
    }

    public Player(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void setEventBus(EventBus eventBus)
    {
        this.eventBus = eventBus;
    }

    public int getEnergy()
    {
        return energy;
    }

    public int getMaxRestorableEnergy()
    {
        return MAX_RESTORABLE_ENERGY;
    }

    public void addEnergy(int delta)
    {
        energy += delta;

        eventBus.publish(new PlayerEnergyChangedEvent(energy, MAX_RESTORABLE_ENERGY));
    }

    public void removeEnergy(int delta)
    {
        energy -= delta;

        if (energy < 0)
        {
            energy = 0;
        }

        eventBus.publish(new PlayerEnergyChangedEvent(energy, MAX_RESTORABLE_ENERGY));
    }
}
