package com.github.wmarkow.klondiklon;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import org.mapeditor.io.TMXMapWriter;

import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.map.objects.KKMapObjectIf;
import com.github.wmarkow.klondiklon.objects.ObjectTypes;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptor;
import com.github.wmarkow.klondiklon.player.Player;
import com.github.wmarkow.klondiklon.simulation.Simulable;
import com.github.wmarkow.klondiklon.simulation.Simulation;
import com.github.wmarkow.klondiklon.simulation.processes.GrowGardenPlantSimulationProcess;
import com.github.wmarkow.klondiklon.simulation.processes.RestoreEnergySimulationProcess;
import com.github.wmarkow.klondiklon.worlds.WorldsManager;

public class GameplayService
{
    private WorldsManager worldsManager = new WorldsManager();
    private Player player;
    private Simulation simulation;
    private KKMapIf currentWorldMap;

    public KKMapIf getCurrentWorldMap()
    {
        return currentWorldMap;

    }

    public Player getPlayer()
    {
        return player;
    }

    public boolean simulateStep(long currentSimulationTimeInMillis)
    {
        return simulation.simulateStep(currentSimulationTimeInMillis);
    }

    public void loadGameContext()
    {
        // load world
        worldsManager.copyHomeWorldFromClasspathToInternal(false);
        currentWorldMap = worldsManager.readHomeWorld();

        // load player
        player = loadPlayer();

        // load simulation
        loadSimulation();
    }

    public void saveGameContext()
    {
        // save world
        TMXMapWriter tmxMapWriter = new TMXMapWriter();
        tmxMapWriter.settings.compressLayerData = false;
        tmxMapWriter.settings.layerEncodingMethod = TMXMapWriter.Settings.LAYER_ENCODING_METHOD_CSV;

        String tmxFilePath = Gdx.files.getLocalStoragePath() + "/worlds/home/home.tmx";
        try
        {
            tmxMapWriter.writeMap(currentWorldMap.getTmxMap(), tmxFilePath);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        // save player
        savePlayer(player);

        // save simulation
    }

    public void addGardenSimulation(int gardenId, StorageItemDescriptor seedItemDescriptor)
    {
        simulation.addSimulable(new GrowGardenPlantSimulationProcess(gardenId, seedItemDescriptor, currentWorldMap));
    }

    public void stopGardenSimulation(int gardenId)
    {
        for (Simulable simulable : simulation.getSimulables())
        {
            if (simulable instanceof GrowGardenPlantSimulationProcess == false)
            {
                continue;
            }

            GrowGardenPlantSimulationProcess ggpsp = (GrowGardenPlantSimulationProcess) simulable;
            if (ggpsp.getGardenObjectId() == gardenId)
            {
                simulation.removeSimulable(simulable);

                return;
            }
        }
    }

    private void loadSimulation()
    {
        long epochMilli = Instant.now().toEpochMilli();
        simulation = new Simulation(epochMilli);
        simulation.addSimulable(new RestoreEnergySimulationProcess(player));
    }

    private void savePlayer(Player player)
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.AUTO_DETECT_CREATORS, MapperFeature.AUTO_DETECT_FIELDS,
                MapperFeature.AUTO_DETECT_GETTERS, MapperFeature.AUTO_DETECT_IS_GETTERS);
        // // if you want to prevent an exception when classes have no annotated
        // properties
        // mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        String filePath = Gdx.files.getLocalStoragePath() + "/player.json";
        try
        {
            mapper.writeValue(new File(filePath), player);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Player loadPlayer()
    {
        ObjectMapper mapper = new ObjectMapper();

        String filePath = Gdx.files.getLocalStoragePath() + "/player.json";

        try
        {
            Player player = mapper.readValue(new File(filePath), Player.class);
            player.setEventBus(ServiceRegistry.getInstance().getEventBus());

            return player;
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
