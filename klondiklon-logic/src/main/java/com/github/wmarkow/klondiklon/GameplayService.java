package com.github.wmarkow.klondiklon;

import java.io.IOException;

import org.mapeditor.io.TMXMapWriter;

import com.badlogic.gdx.Gdx;
import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.worlds.WorldsManager;

public class GameplayService
{
    private WorldsManager worldsManager = new WorldsManager();
    private KKMapIf currentWorldMap;

    public KKMapIf loadHomeWorld()
    {
        worldsManager.copyHomeWorldFromClasspathToInternal(false);
        currentWorldMap = worldsManager.readHomeWorld();

        return currentWorldMap;
    }

    public KKMapIf getCurrentWorldMap()
    {
        return currentWorldMap;
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
