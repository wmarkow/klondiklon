package com.github.wmarkow.klondiklon.worlds;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.badlogic.gdx.Gdx;

public class WorldsManager
{
    private static Logger LOGGER = LoggerFactory.getLogger(WorldsManager.class);

    private final static String WORLDS_DIR_NAME = "worlds";

    public void copyHomeWorldFromClasspathToInternal()
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
                    FileUtils.copyURLToFile(url, destination);
                    LOGGER.info("Copied " + url + " to " + destination.getAbsolutePath());
                }
            }

        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
