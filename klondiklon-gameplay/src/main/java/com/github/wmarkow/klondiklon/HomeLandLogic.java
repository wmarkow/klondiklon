package com.github.wmarkow.klondiklon;

import com.github.wmarkow.klondiklon.graphics.FontsRegistrar;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;
import com.github.wmarkow.klondiklon.music.MusicsRegistrar;
import com.github.wmarkow.klondiklon.resources.graphics.FontsManager;
import com.github.wmarkow.klondiklon.resources.graphics.TexturesManager;
import com.github.wmarkow.klondiklon.resources.music.MusicManager;
import com.github.wmarkow.klondiklon.resources.sound.SoundManager;
import com.github.wmarkow.klondiklon.sounds.SoundsRegistrar;

public class HomeLandLogic
{
    public void initFonts(FontsManager fontsManager)
    {
        FontsRegistrar registrar = new FontsRegistrar();
        registrar.register(fontsManager);
    }

    public void initTextures(TexturesManager texturesManager)
    {
        TexturesRegistrar registrar = new TexturesRegistrar();
        registrar.register(texturesManager);
    }

    public void initMusics(MusicManager musicManager)
    {
        MusicsRegistrar registrar = new MusicsRegistrar();
        registrar.register(musicManager);
    }

    public void initSounds(SoundManager soundManager)
    {
        SoundsRegistrar registrar = new SoundsRegistrar();
        registrar.register(soundManager);
    }
}
