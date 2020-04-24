package com.github.wmarkow.klondiklon.desktop;

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
import com.github.wmarkow.klondiklon.AssetsCopyServiceIf;
import com.github.wmarkow.klondiklon.worlds.WorldRegistrar;

public class DesktopAssetsCopyService implements AssetsCopyServiceIf
{
    private static Logger LOGGER = LoggerFactory.getLogger(DesktopAssetsCopyService.class);

    @Override
    public void copyAssetsToLocalStorage()
    {
        boolean overrrideTmx = false;

        ClassLoader classLoader = MethodHandles.lookup().getClass().getClassLoader();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
        File rootDstDirectory = new File(Gdx.files.getLocalStoragePath());

        try
        {
            Resource[] resources = resolver.getResources("classpath:assets/" + WorldRegistrar.WORLDS_DIR_NAME + "/**");
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
                    String targetName = urlString.substring(urlString.indexOf(WorldRegistrar.WORLDS_DIR_NAME));
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

}
