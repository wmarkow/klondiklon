package com.github.wmarkow.klondiklon.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.github.wmarkow.klondiklon.resources.graphics.TexturesManager;

public class TexturesRegistrar
{
    public final static String LIGHTNING = "LIGHTNING";
    public final static String BALLOON = "BALLOON";
    public final static String BALLOON_BLUE = "BALLOON_BLUE";
    public final static String BACKPACK = "BACKPACK";
    public final static String WAREHOUSE_BACKGROUND = "WAREHOUSE_BACKGROUND";
    public final static String WAREHOUSE_ITEM_BACKGROUND = "WAREHOUSE_ITEM_BACKGROUND";
    public final static String BUTTON_OK = "BUTTON_OK";
    public final static String BUTTON_CANCEL = "BUTTON_CANCEL";
    public final static String SICKLE = "SICKLE";

    public void register(TexturesManager texturesManager)
    {
        texturesManager.registerTexture(LIGHTNING, new Texture(Gdx.files.classpath("ui/lightning.png")));
        texturesManager.registerTexture(BALLOON, new Texture(Gdx.files.classpath("ui/balloon.png")));
        texturesManager.registerTexture(BALLOON_BLUE, new Texture(Gdx.files.classpath("ui/balloon_blue.png")));
        texturesManager.registerTexture(BACKPACK, new Texture(Gdx.files.classpath("ui/backpack.png")));
        texturesManager.registerTexture(WAREHOUSE_BACKGROUND,
                new Texture(Gdx.files.classpath("ui/warehouse_background.png")));
        texturesManager.registerTexture(WAREHOUSE_ITEM_BACKGROUND,
                new Texture(Gdx.files.classpath("ui/warehouse_item_background.png")));
        texturesManager.registerTexture(BUTTON_OK, new Texture(Gdx.files.classpath("ui/button_ok.png")));
        texturesManager.registerTexture(BUTTON_CANCEL, new Texture(Gdx.files.classpath("ui/button_cancel.png")));
        texturesManager.registerTexture(SICKLE, new Texture(Gdx.files.classpath("ui/sickle.png")));
    }
}
