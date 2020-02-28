package com.github.wmarkow.klondiklon;

import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.worlds.WorldsManager;

public class GameplayService
{
    private WorldsManager worldsManager = new WorldsManager();
    private KKMapIf currentWorldMap;
    
    public KKMapIf loadHomeWorld()
    {
        worldsManager.copyHomeWorldFromClasspathToInternal();
        currentWorldMap = worldsManager.readHomeWorld();
        
        return currentWorldMap;
    }
    
    public KKMapIf getCurrentWorldMap()
    {
        return currentWorldMap;
    }
}
