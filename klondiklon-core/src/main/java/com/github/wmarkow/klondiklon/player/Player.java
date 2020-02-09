package com.github.wmarkow.klondiklon.player;

import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.event.events.PlayerEnergyChangedEvent;

public class Player
{
    private final static int MAX_RESTORABLE_ENERGY = 100;
    private int energy = 100;
    private EventBus eventBus;

    public Player(EventBus eventBus) {
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
