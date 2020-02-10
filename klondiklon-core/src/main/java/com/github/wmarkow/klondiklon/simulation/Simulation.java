package com.github.wmarkow.klondiklon.simulation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Simulation
{
    private static Logger LOGGER = LoggerFactory.getLogger(Simulation.class);
    private final static long SIMULATION_RESOLUTION_IN_MILLIS = 1000;

    private List<Simulable> simulables = new ArrayList<Simulable>();
    private long lastSimulationTimeInMillis;

    public Simulation(long lastSimulationTimeInMillis) {
        this.lastSimulationTimeInMillis = lastSimulationTimeInMillis;
    }

    public void addSimulable(Simulable simulable)
    {
        simulables.add(simulable);
    }

    public synchronized void simulateStep(long currentSimulationTimeInMillis)
    {
        if (currentSimulationTimeInMillis < lastSimulationTimeInMillis)
        {
            LOGGER.warn(String.format("Current simulation time is lower than previous simulation time: %s < %s",
                    currentSimulationTimeInMillis, lastSimulationTimeInMillis));
            return;
        }

        if (currentSimulationTimeInMillis - lastSimulationTimeInMillis < SIMULATION_RESOLUTION_IN_MILLIS)
        {
            return;
        }

        for (Simulable simulable : simulables)
        {
            simulable.stepEverySecond();
        }

        lastSimulationTimeInMillis = currentSimulationTimeInMillis;
    }
}
