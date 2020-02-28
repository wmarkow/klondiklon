package com.github.wmarkow.klondiklon;

import java.io.IOException;
import java.time.Instant;

import org.mapeditor.io.TMXMapWriter;

import com.badlogic.gdx.Gdx;
import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.player.Player;
import com.github.wmarkow.klondiklon.simulation.Simulation;
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
        player = new Player(KlondiklonCore.eventBus);

        // load simulation
        long epochMilli = Instant.now().toEpochMilli();
        simulation = new Simulation(epochMilli);
        simulation.addSimulable(new RestoreEnergySimulationProcess(player));
    }

    public void saveGameContext()
    {
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
    }
}
