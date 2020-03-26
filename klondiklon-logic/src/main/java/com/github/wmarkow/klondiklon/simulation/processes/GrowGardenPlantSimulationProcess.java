package com.github.wmarkow.klondiklon.simulation.processes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.map.objects.GardenCellObject;
import com.github.wmarkow.klondiklon.objects.StorageItemTypes;
import com.github.wmarkow.klondiklon.simulation.Simulable;

public class GrowGardenPlantSimulationProcess implements Simulable
{
    private static Logger LOGGER = LoggerFactory.getLogger(GrowGardenPlantSimulationProcess.class);

    @JsonProperty("secondsCounter")
    private int secondsCounter;
    @JsonProperty("gardenObjectId")
    private int gardenObjectId;
    @JsonProperty("storageItemType")
    private String storageItemType;
    private KKMapIf map;

    GrowGardenPlantSimulationProcess() {
        // default package protected constructor for deserialisation
    }

    public GrowGardenPlantSimulationProcess(int gardenObjectId, String storageItemType, KKMapIf map) {
        this.gardenObjectId = gardenObjectId;
        this.secondsCounter = 0;
        this.storageItemType = storageItemType;
        this.map = map;
    }

    public void setMap(KKMapIf map)
    {
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

        if (StorageItemTypes.BEAN.equals(storageItemType))
        {
            stepEverySecondForBean();
        } else if (StorageItemTypes.WHEAT.equals(storageItemType))
        {
            stepEverySecondForWheat();
        } else if (StorageItemTypes.GRASS.equals(storageItemType))
        {
            stepEverySecondForGrass();
        } else if (StorageItemTypes.CORN.equals(storageItemType))
        {
            stepEverySecondForCorn();
        } else if (StorageItemTypes.STRAWBERRY.equals(storageItemType))
        {
            stepEverySecondForStrawberry();
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
            getGardenCell().setBeanPhase1();
        } else if (secondsCounter <= 10)
        {
            // growing phase 2
            getGardenCell().setBeanPhase2();
        } else
        {
            // growing phase 3
            getGardenCell().setBeanPhase3();
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

    private void stepEverySecondForGrass()
    {
        if (secondsCounter <= 5)
        {
            // growing phase 1
            getGardenCell().setGrassPhase1();
        } else if (secondsCounter <= 10)
        {
            // growing phase 2
            getGardenCell().setGrassPhase2();
        } else
        {
            // growing phase 3
            getGardenCell().setGrassPhase3();
        }
    }

    private void stepEverySecondForCorn()
    {
        if (secondsCounter <= 5)
        {
            // growing phase 1
            getGardenCell().setCornPhase1();
        } else if (secondsCounter <= 10)
        {
            // growing phase 2
            getGardenCell().setCornPhase2();
        } else
        {
            // growing phase 3
            getGardenCell().setCornPhase3();
        }
    }

    private void stepEverySecondForStrawberry()
    {
        if (secondsCounter <= 5)
        {
            // growing phase 1
            getGardenCell().setStrawberryPhase1();
        } else if (secondsCounter <= 10)
        {
            // growing phase 2
            getGardenCell().setStrawberryPhase2();
        } else
        {
            // growing phase 3
            getGardenCell().setStrawberryPhase3();
        }
    }

}
