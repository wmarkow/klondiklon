package com.github.wmarkow.klondiklon.events;

import com.github.wmarkow.klondiklon.event.Event;

public class PlayerEnergyChangedEvent extends Event
{
    private int currentEnergy;
    private int maxRestorableEnergy;

    public PlayerEnergyChangedEvent(int currentEnergy, int maxRestorableEnergy) {
        this.currentEnergy = currentEnergy;
        this.maxRestorableEnergy = maxRestorableEnergy;
    }

    public int getCurrentEnergy()
    {
        return currentEnergy;
    }

    public int getMaxRestorableEnergy()
    {
        return maxRestorableEnergy;
    }
}
