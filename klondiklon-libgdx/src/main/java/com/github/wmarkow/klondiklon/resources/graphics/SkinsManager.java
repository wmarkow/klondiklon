package com.github.wmarkow.klondiklon.resources.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.github.wmarkow.klondiklon.resources.ResourcesManager;

public class SkinsManager extends ResourcesManager<Skin>
{
    public final static String GLASSY = "GLASSY";

    public void init()
    {
        registerResource(GLASSY, new Skin(Gdx.files.classpath("skins/glassy/skin/glassy-ui.json")));
    }
}
