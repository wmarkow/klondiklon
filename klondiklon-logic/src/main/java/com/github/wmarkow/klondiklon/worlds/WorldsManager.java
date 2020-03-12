package com.github.wmarkow.klondiklon.worlds;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.mapeditor.core.Map;
import org.mapeditor.io.TMXMapReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.badlogic.gdx.Gdx;
import com.github.wmarkow.klondiklon.map.KKMap;
import com.github.wmarkow.klondiklon.map.objects.MapObjectsFactory;

public class WorldsManager
{
    private static Logger LOGGER = LoggerFactory.getLogger(WorldsManager.class);

    private final static String WORLDS_DIR_NAME = "worlds";

    public void copyHomeWorldFromClasspathToInternal(boolean overrrideTmx)
    {
        ClassLoader classLoader = MethodHandles.lookup().getClass().getClassLoader();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
        File rootDstDirectory = new File(Gdx.files.getLocalStoragePath());

        try
        {
            Resource[] resources = resolver.getResources("classpath:" + WORLDS_DIR_NAME + "/**");
            for (Resource resource : resources)
            {
                if (resource.getFilename().toLowerCase().endsWith(".xcf"))
                {
                    continue;
                }

                if (resource.exists() & resource.isReadable() && resource.contentLength() > 0)
                {
                    URL url = resource.getURL();
                    String urlString = url.toExternalForm();
                    String targetName = urlString.substring(urlString.indexOf(WORLDS_DIR_NAME));
                    File destination = new File(rootDstDirectory, targetName);

                    if (url.getPath().toLowerCase().endsWith("tmx"))
                    {
                        if (destination.exists() && !overrrideTmx)
                        {
                            continue;
                        }
                    }
                    FileUtils.copyURLToFile(url, destination);
                    LOGGER.info("Copied " + url + " to " + destination.getAbsolutePath());
                }
            }

        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public KKMap readHomeWorld()
    {
        File file = new File(Gdx.files.getLocalStoragePath() + WORLDS_DIR_NAME + "\\home\\home.tmx");
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
