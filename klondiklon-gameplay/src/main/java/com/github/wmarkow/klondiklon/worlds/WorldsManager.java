package com.github.wmarkow.klondiklon.worlds;

import java.io.File;

import org.mapeditor.core.Map;
import org.mapeditor.io.TMXMapReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.github.wmarkow.klondiklon.GameplayService;
import com.github.wmarkow.klondiklon.HomeWorldRegistrar;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.map.KKMap;
import com.github.wmarkow.klondiklon.map.objects.MapObjectsFactory;

public class WorldsManager
{
    private static Logger LOGGER = LoggerFactory.getLogger(WorldsManager.class);

    public KKMap loadWorld()
    {
        HomeWorldRegistrar hwr = new HomeWorldRegistrar();
        hwr.copyResourcesToInternal();
        hwr.registerFonts(ServiceRegistry.getInstance().getFontsManager());
        hwr.registerMusics(ServiceRegistry.getInstance().getMusicManager());
        hwr.registerSounds(ServiceRegistry.getInstance().getSoundManager());
        hwr.registerTextures(ServiceRegistry.getInstance().getTexturesManager());
        hwr.registerStorageItemDescriptors(GameplayService.getInstance().getStorageItemDescriptorsManager());
        hwr.registerObjectTypeDescriptors(GameplayService.getInstance().getObjectTypeDescriptorsManager());

        File file = new File(Gdx.files.getLocalStoragePath() + WorldRegistrar.WORLDS_DIR_NAME + "\\home\\home.tmx");
        TMXMapReader tmxMapReader = new TMXMapReader();
        Map tmxMap = null;
        try
        {
            tmxMap = tmxMapReader.readMap(file.getAbsolutePath());
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        KKMap libGdxMap = new KKMap(tmxMap, new MapObjectsFactory());

        return libGdxMap;
    }
}
