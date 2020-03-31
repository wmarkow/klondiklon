package com.github.wmarkow.klondiklon;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import org.mapeditor.io.TMXMapWriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.jackson.KKObjectMapper;
import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.music.MusicsRegistrar;
import com.github.wmarkow.klondiklon.objects.ObjectTypeDescriptorsManager;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptor;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptorsManager;
import com.github.wmarkow.klondiklon.player.Player;
import com.github.wmarkow.klondiklon.simulation.Simulable;
import com.github.wmarkow.klondiklon.simulation.Simulation;
import com.github.wmarkow.klondiklon.simulation.processes.GrowGardenPlantSimulationProcess;
import com.github.wmarkow.klondiklon.simulation.processes.RestoreEnergySimulationProcess;
import com.github.wmarkow.klondiklon.ui.KKUi;
import com.github.wmarkow.klondiklon.ui.tools.GrubbingInteractiveTool;
import com.github.wmarkow.klondiklon.ui.tools.HarvestInteractiveTools;
import com.github.wmarkow.klondiklon.ui.tools.MoveObjectInteractiveTool;
import com.github.wmarkow.klondiklon.ui.tools.SeedInteractiveTool;
import com.github.wmarkow.klondiklon.warehouse.Warehouse;
import com.github.wmarkow.klondiklon.worlds.WorldsManager;

public class GameplayService
{
    private static GameplayService instance = new GameplayService();

    private WorldsManager worldsManager;
    private ObjectTypeDescriptorsManager objectTypeDescriptorsManager;
    private StorageItemDescriptorsManager storageItemDescriptorsManager;
    private KKUi ui;

    private Player player;
    private Warehouse warehouse;
    private Simulation simulation;
    private KKMapIf currentWorldMap;

    private GrubbingInteractiveTool grubbingInteractiveTool;
    private MoveObjectInteractiveTool moveObjectInteractiveTool;
    private HarvestInteractiveTools sickleInteractiveTools;
    private SeedInteractiveTool sowInteractiveTools;

    public GameplayService() {
        init();
    }

    public static GameplayService getInstance()
    {
        return instance;
    }

    public void initUi()
    {
        ui = new KKUi();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(ui.getStage());
        multiplexer.addProcessor(ServiceRegistry.getInstance().getCameraController());
        Gdx.input.setInputProcessor(multiplexer);
    }

    public KKMapIf getCurrentWorldMap()
    {
        return currentWorldMap;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Warehouse getWarehouse()
    {
        return warehouse;
    }

    public ObjectTypeDescriptorsManager getObjectTypeDescriptorsManager()
    {
        return objectTypeDescriptorsManager;
    }

    public StorageItemDescriptorsManager getStorageItemDescriptorsManager()
    {
        return storageItemDescriptorsManager;
    }

    public KKUi getUi()
    {
        return ui;
    }

    public boolean simulateStep(long currentSimulationTimeInMillis)
    {
        return simulation.simulateStep(currentSimulationTimeInMillis);
    }

    public void loadGameContext()
    {
        currentWorldMap = worldsManager.loadWorld();
        player = loadPlayer();
        warehouse = loadWarehouse();
        simulation = loadSimulation();
        simulation.catchUp(Instant.now().toEpochMilli());

        initInteractiveTools(currentWorldMap);
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

        savePlayer(player);
        saveWarehouse(warehouse);
        saveSimulation(simulation);
    }

    public void addGardenSimulation(int gardenId, StorageItemDescriptor seedItemDescriptor)
    {
        simulation.addSimulable(new GrowGardenPlantSimulationProcess(gardenId, seedItemDescriptor.getStorageItemType(),
                currentWorldMap));
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

    public void playMainTheme()
    {
        Music mainTheme = ServiceRegistry.getInstance().getMusicManager().getResource(MusicsRegistrar.MAIN_THEME);
        mainTheme.play();
        mainTheme.setLooping(true);
    }

    private void savePlayer(Player player)
    {
        ObjectMapper mapper = new KKObjectMapper();
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
        ObjectMapper mapper = new KKObjectMapper();
        File file = new File(Gdx.files.getLocalStoragePath() + "/player.json");

        if (!file.exists())
        {
            return new Player(ServiceRegistry.getInstance().getEventBus());
        }

        try
        {
            return mapper.readValue(file, Player.class);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void saveWarehouse(Warehouse warehouse)
    {
        ObjectMapper mapper = new KKObjectMapper();
        String filePath = Gdx.files.getLocalStoragePath() + "/warehouse.json";

        try
        {
            mapper.writeValue(new File(filePath), warehouse);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Warehouse loadWarehouse()
    {
        ObjectMapper mapper = new KKObjectMapper();
        File file = new File(Gdx.files.getLocalStoragePath() + "/warehouse.json");

        if (!file.exists())
        {
            return new Warehouse();
        }

        try
        {
            return mapper.readValue(file, Warehouse.class);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void saveSimulation(Simulation simulation)
    {
        ObjectMapper mapper = new KKObjectMapper();

        String filePath = Gdx.files.getLocalStoragePath() + "/simulation.json";

        try
        {
            mapper.writeValue(new File(filePath), simulation);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Simulation loadSimulation()
    {
        ObjectMapper mapper = new KKObjectMapper();
        File file = new File(Gdx.files.getLocalStoragePath() + "/simulation.json");

        if (!file.exists())
        {
            Simulation simulation = new Simulation(Instant.now().toEpochMilli());
            simulation.addSimulable(new RestoreEnergySimulationProcess(player));

            return simulation;
        }

        try
        {
            return mapper.readValue(file, Simulation.class);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void init()
    {
        worldsManager = new WorldsManager();
        objectTypeDescriptorsManager = new ObjectTypeDescriptorsManager();
        storageItemDescriptorsManager = new StorageItemDescriptorsManager();
    }

    private void initInteractiveTools(KKMapIf kkMap)
    {
        Camera camera = ServiceRegistry.getInstance().getCamera();
        EventBus eventBus = ServiceRegistry.getInstance().getEventBus();

        grubbingInteractiveTool = new GrubbingInteractiveTool(eventBus, kkMap, camera,
                GameplayService.getInstance().getPlayer(),
                GameplayService.getInstance().getObjectTypeDescriptorsManager());
        moveObjectInteractiveTool = new MoveObjectInteractiveTool(eventBus, kkMap, camera,
                GameplayService.getInstance().getObjectTypeDescriptorsManager());
        sickleInteractiveTools = new HarvestInteractiveTools(eventBus, kkMap, camera);
        sowInteractiveTools = new SeedInteractiveTool(eventBus, kkMap, camera);
    }
}
