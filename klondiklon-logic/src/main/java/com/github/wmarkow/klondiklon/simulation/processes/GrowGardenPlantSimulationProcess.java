package com.github.wmarkow.klondiklon.simulation.processes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.map.objects.GardenCellObject;
import com.github.wmarkow.klondiklon.simulation.Simulable;

public class GrowGardenPlantSimulationProcess implements Simulable
{
    private static Logger LOGGER = LoggerFactory.getLogger(GrowGardenPlantSimulationProcess.class);

    private int secondsCounter;
    private int gardenObjectId;
    private KKMapIf map;

    public GrowGardenPlantSimulationProcess(int gardenObjectId, KKMapIf map) {
        this.gardenObjectId = gardenObjectId;
        this.secondsCounter = 0;
        this.map = map;
    }

    @Override
    public void stepEverySecond()
    {
        secondsCounter++;

        if (secondsCounter < 5)
        {
            // growing phase 1
            getGardenCell().setWheatPhase1();
        } else if (secondsCounter < 10)
        {
            // growing phase 2
            getGardenCell().setWheatPhase2();
        } else if (secondsCounter < 15)
        {
            // growing phase 3
            getGardenCell().setWheatPhase3();
        } else if (secondsCounter >= 20)
        {
            // growing phase 4
            getGardenCell().setWheatPhase4();
        }
    }

    public int getGardenObjectId()
    {
        return gardenObjectId;
    }

    private GardenCellObject getGardenCell()
    {
        return (GardenCellObject) map.getObject(gardenObjectId);
    }
}
