package com.github.wmarkow.klondiklon.desktop;

import java.io.File;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.github.wmarkow.klondiklon.GameEntryPoint;

public class DesktopLauncher
{
    public static void main(String[] arg)
    {
        LwjglFiles.localPath = System.getProperty("user.home") + File.separator + ".klondiklon" + File.separator;

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = true;
        config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
        config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
        // config.width = 1024;
        // config.height = 768;

        new LwjglApplication(new GameEntryPoint(), config);
    }
}
