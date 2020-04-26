package com.github.wmarkow.klondiklon.worlds;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.github.wmarkow.klondiklon.GameplayService;
import com.github.wmarkow.klondiklon.HomeWorldRegistrar;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.map.KKMap;
import com.github.wmarkow.klondiklon.map.objects.MapObjectsFactory;
import com.github.wmarkow.klondiklon.tiled.TmxTiledMap;

public class WorldsManager
{
    private static Logger LOGGER = LoggerFactory.getLogger(WorldsManager.class);

    public KKMap loadWorld()
    {
        HomeWorldRegistrar hwr = new HomeWorldRegistrar();
        hwr.copyAssetsToLocalStorage();
        hwr.registerFonts(ServiceRegistry.getInstance().getFontsManager());
        hwr.registerMusics(ServiceRegistry.getInstance().getMusicManager());
        hwr.registerSounds(ServiceRegistry.getInstance().getSoundManager());
        hwr.registerTextures(ServiceRegistry.getInstance().getTexturesManager());
        hwr.registerStorageItemDescriptors(GameplayService.getInstance().getStorageItemDescriptorsManager());
        hwr.registerObjectTypeDescriptors(GameplayService.getInstance().getObjectTypeDescriptorsManager());

        File file = new File(Gdx.files.getLocalStoragePath() + WorldRegistrar.WORLDS_DIR_NAME + File.separator + "home"
                + File.separator + "home.tmx");

        try
        {
            TmxTiledMap tmxTiledMap = TmxTiledMap.readFromTmx(file.getAbsolutePath());

            return new KKMap(tmxTiledMap, new MapObjectsFactory());
            // return new KKMap(new Map());
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
