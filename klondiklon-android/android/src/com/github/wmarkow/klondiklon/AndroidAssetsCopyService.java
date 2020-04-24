package com.github.wmarkow.klondiklon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.wmarkow.klondiklon.worlds.WorldRegistrar;

public class AndroidAssetsCopyService implements AssetsCopyServiceIf {

    @Override
    public void copyAssetsToLocalStorage() {
        FileHandle srcDir = Gdx.files.internal(WorldRegistrar.WORLDS_DIR_NAME);
        FileHandle dstDir = Gdx.files.local("");

        // FIXME: do not copy xcf files
        srcDir.copyTo(dstDir);
    }
}
