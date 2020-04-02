package com.github.wmarkow.klondiklon.simulation.processes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.wmarkow.klondiklon.HomeWorldRegistrar;
import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.map.objects.GardenCellObject;
import com.github.wmarkow.klondiklon.map.objects.GrowPlantInfo;
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

    private GrowPlantInfo growBeanInfo;
    private GrowPlantInfo growWheatInfo;
    private GrowPlantInfo growGrassInfo;
    private GrowPlantInfo growCornInfo;
    private GrowPlantInfo growStrawberryInfo;

    GrowGardenPlantSimulationProcess() {
        // default package protected constructor for deserialisation
        initGrowPlantInfos();
    }

    public GrowGardenPlantSimulationProcess(int gardenObjectId, String storageItemType, KKMapIf map) {
        this.gardenObjectId = gardenObjectId;
        this.secondsCounter = 0;
        this.storageItemType = storageItemType;
        this.map = map;

        initGrowPlantInfos();
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
            getGardenCell().setGrowPlantPhase(growBeanInfo, 0);
        } else if (secondsCounter <= 10)
        {
            // growing phase 2
            getGardenCell().setGrowPlantPhase(growBeanInfo, 1);
        } else
        {
            // growing phase 3
            getGardenCell().setGrowPlantPhase(growBeanInfo, 2);
        }
    }

    private void stepEverySecondForWheat()
    {
        if (secondsCounter <= 5)
        {
            // growing phase 1
            getGardenCell().setGrowPlantPhase(growWheatInfo, 0);
        } else if (secondsCounter <= 10)
        {
            // growing phase 2
            getGardenCell().setGrowPlantPhase(growWheatInfo, 1);
        } else if (secondsCounter <= 15)
        {
            // growing phase 3
            getGardenCell().setGrowPlantPhase(growWheatInfo, 2);
        } else
        {
            // growing phase 4
            getGardenCell().setGrowPlantPhase(growWheatInfo, 3);
        }
    }

    private void stepEverySecondForGrass()
    {
        if (secondsCounter <= 5)
        {
            // growing phase 1
            getGardenCell().setGrowPlantPhase(growGrassInfo, 0);
        } else if (secondsCounter <= 10)
        {
            // growing phase 2
            getGardenCell().setGrowPlantPhase(growGrassInfo, 1);
        } else
        {
            // growing phase 3
            getGardenCell().setGrowPlantPhase(growGrassInfo, 2);
        }
    }

    private void stepEverySecondForCorn()
    {
        if (secondsCounter <= 5)
        {
            // growing phase 1
            getGardenCell().setGrowPlantPhase(growCornInfo, 0);
        } else if (secondsCounter <= 10)
        {
            // growing phase 2
            getGardenCell().setGrowPlantPhase(growCornInfo, 1);
        } else
        {
            // growing phase 3
            getGardenCell().setGrowPlantPhase(growCornInfo, 2);
        }
    }

    private void stepEverySecondForStrawberry()
    {
        if (secondsCounter <= 5)
        {
            // growing phase 1
            getGardenCell().setGrowPlantPhase(growStrawberryInfo, 0);
        } else if (secondsCounter <= 10)
        {
            // growing phase 2
            getGardenCell().setGrowPlantPhase(growStrawberryInfo, 1);
        } else
        {
            // growing phase 3
            getGardenCell().setGrowPlantPhase(growStrawberryInfo, 2);
        }
    }

    private void initGrowPlantInfos()
    {
        growBeanInfo = new GrowPlantInfo(StorageItemTypes.BEAN, 15);
        growBeanInfo.addgGrowPhaseTexture(HomeWorldRegistrar.OBJECT_BEAN_GARDEN_1);
        growBeanInfo.addgGrowPhaseTexture(HomeWorldRegistrar.OBJECT_BEAN_GARDEN_2);
        growBeanInfo.addgGrowPhaseTexture(HomeWorldRegistrar.OBJECT_BEAN_GARDEN_3);

        growWheatInfo = new GrowPlantInfo(StorageItemTypes.WHEAT, 20);
        growWheatInfo.addgGrowPhaseTexture(HomeWorldRegistrar.OBJECT_WHEAT_GARDEN_1);
        growWheatInfo.addgGrowPhaseTexture(HomeWorldRegistrar.OBJECT_WHEAT_GARDEN_2);
        growWheatInfo.addgGrowPhaseTexture(HomeWorldRegistrar.OBJECT_WHEAT_GARDEN_3);
        growWheatInfo.addgGrowPhaseTexture(HomeWorldRegistrar.OBJECT_WHEAT_GARDEN_4);

        growGrassInfo = new GrowPlantInfo(StorageItemTypes.GRASS, 15);
        growGrassInfo.addgGrowPhaseTexture(HomeWorldRegistrar.OBJECT_GRASS_GARDEN_1);
        growGrassInfo.addgGrowPhaseTexture(HomeWorldRegistrar.OBJECT_GRASS_GARDEN_2);
        growGrassInfo.addgGrowPhaseTexture(HomeWorldRegistrar.OBJECT_GRASS_GARDEN_3);

        growCornInfo = new GrowPlantInfo(StorageItemTypes.CORN, 15);
        growCornInfo.addgGrowPhaseTexture(HomeWorldRegistrar.OBJECT_CORN_GARDEN_1);
        growCornInfo.addgGrowPhaseTexture(HomeWorldRegistrar.OBJECT_CORN_GARDEN_2);
        growCornInfo.addgGrowPhaseTexture(HomeWorldRegistrar.OBJECT_CORN_GARDEN_3);

        growStrawberryInfo = new GrowPlantInfo(StorageItemTypes.STRAWBERRY, 15);
        growStrawberryInfo.addgGrowPhaseTexture(HomeWorldRegistrar.OBJECT_STRAWBERRY_GARDEN_1);
        growStrawberryInfo.addgGrowPhaseTexture(HomeWorldRegistrar.OBJECT_STRAWBERRY_GARDEN_2);
        growStrawberryInfo.addgGrowPhaseTexture(HomeWorldRegistrar.OBJECT_STRAWBERRY_GARDEN_3);
    }
}
