package com.github.wmarkow.klondiklon.simulation.processes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wmarkow.klondiklon.player.Player;
import com.github.wmarkow.klondiklon.simulation.Simulable;

public class RestoreEnergySimulationProcess implements Simulable
{
    private static Logger LOGGER = LoggerFactory.getLogger(RestoreEnergySimulationProcess.class);

    private final static int SECONDS_PER_ENERGY_UPDATE = 5;
    private final static int ENERGY_UPDATE = 1;

    private Player player;
    private int secondsCounter = 0;

    public RestoreEnergySimulationProcess(Player player) {
        this.player = player;
    }

    @Override
    public void stepInit()
    {
        // nothing to do here
    }

    @Override
    public void stepEverySecond()
    {
        secondsCounter++;
        if (secondsCounter % SECONDS_PER_ENERGY_UPDATE != 0)
        {
            return;
        }

        secondsCounter = 0;

        if (player.getEnergy() >= player.getMaxRestorableEnergy())
        {
            LOGGER.debug("Energy is at maximum restorable level. Nothing to restore.");
            return;
        }
        final int energyUpdate = Math.min(ENERGY_UPDATE, player.getMaxRestorableEnergy() - player.getEnergy());

        LOGGER.debug(String.format("Restoring %s point(s) of energy", energyUpdate));
        player.addEnergy(energyUpdate);
    }
}
