package com.github.wmarkow.klondiklon.worlds;

import com.github.wmarkow.klondiklon.objects.ObjectTypeDescriptorsManager;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptorsManager;
import com.github.wmarkow.klondiklon.resources.graphics.FontsManager;
import com.github.wmarkow.klondiklon.resources.graphics.TexturesManager;
import com.github.wmarkow.klondiklon.resources.music.MusicManager;
import com.github.wmarkow.klondiklon.resources.sound.SoundManager;

public interface WorldRegistrar
{
    public final static String WORLDS_DIR_NAME = "worlds";

    void registerFonts(FontsManager fontsManager);

    void registerTextures(TexturesManager texturesManager);

    void registerMusics(MusicManager musicManager);

    void registerSounds(SoundManager soundManager);

    void registerStorageItemDescriptors(StorageItemDescriptorsManager manager);
    
    void registerObjectTypeDescriptors(ObjectTypeDescriptorsManager manager);
    
    void copyResourcesToInternal();
}
