package com.github.wmarkow.klondiklon.simulation.processes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.map.objects.GardenCellObject;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptor;
import com.github.wmarkow.klondiklon.objects.StorageItemTypes;
import com.github.wmarkow.klondiklon.simulation.Simulable;

public class GrowGardenPlantSimulationProcess implements Simulable
{
    private static Logger LOGGER = LoggerFactory.getLogger(GrowGardenPlantSimulationProcess.class);

    private int secondsCounter;
    private int gardenObjectId;
    private StorageItemDescriptor seedItemDescriptor;
    private KKMapIf map;

    public GrowGardenPlantSimulationProcess(int gardenObjectId, StorageItemDescriptor seedItemDescriptor, KKMapIf map) {
        this.gardenObjectId = gardenObjectId;
        this.secondsCounter = 0;
        this.seedItemDescriptor = seedItemDescriptor;
        this.map = map;
    }

    @Override
    public void stepInit()
    {
        stepEverySecond();
    }

    @Override
    public void stepEverySecond()
    {
        secondsCounter++;

        if (StorageItemTypes.BEAN.equals(seedItemDescriptor.getStorageItemType()))
        {
            stepEverySecondForBean();
        } else if (StorageItemTypes.WHEAT.equals(seedItemDescriptor.getStorageItemType()))
        {
            stepEverySecondForWheat();
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

    private void stepEverySecondForBean()
    {
        if (secondsCounter <= 5)
        {
            // growing phase 1
            getGardenCell().setWheatPhase1();
        } else if (secondsCounter <= 10)
        {
            // growing phase 2
            getGardenCell().setWheatPhase2();
        } else
        {
            // growing phase 3
            getGardenCell().setWheatPhase4();
        }
    }

    private void stepEverySecondForWheat()
    {
        if (secondsCounter <= 5)
        {
            // growing phase 1
            getGardenCell().setWheatPhase1();
        } else if (secondsCounter <= 10)
        {
            // growing phase 2
            getGardenCell().setWheatPhase2();
        } else if (secondsCounter <= 15)
        {
            // growing phase 3
            getGardenCell().setWheatPhase3();
        } else
        {
            // growing phase 4
            getGardenCell().setWheatPhase4();
        }
    }
}
