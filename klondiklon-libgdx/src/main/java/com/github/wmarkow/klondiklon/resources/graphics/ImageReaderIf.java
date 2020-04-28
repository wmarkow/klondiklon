package com.github.wmarkow.klondiklon.resources.graphics;

import com.badlogic.gdx.graphics.Pixmap;

public interface ImageReaderIf
{
    Pixmap readImage(String path, int startX, int startY, int width, int height);
}
