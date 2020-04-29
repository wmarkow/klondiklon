package com.github.wmarkow.klondiklon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.github.wmarkow.klondiklon.resources.graphics.ImageReaderIf;

import com.github.wmarkow.klondiklon.resources.graphics.ImageReaderIf;

public class AndroidImageReader implements ImageReaderIf {

    @Override
    public Pixmap readImage(String path, int startX, int startY, int width, int height) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);

        return toPixmap(bitmap, startX, startY, width, height);
    }

    private Pixmap toPixmap(Bitmap bitmap, int startX, int startY, int width, int height)
    {
        final int imageWidth = width;
        final int imageHeight = height;

        Pixmap pixmap = new Pixmap(imageWidth, imageHeight, Pixmap.Format.RGBA8888);

        for (int xx = startX; xx < startX + width; xx++)
        {
            for (int yy = startY; yy < startY + height; yy++)
            {
                final int  pixel = bitmap.getPixel(xx, yy);
                final float r = android.graphics.Color.red(pixel);
                final float g = android.graphics.Color.green(pixel);
                final float b = android.graphics.Color.blue(pixel);
                final float a = android.graphics.Color.alpha(pixel);
                final int rgba = Color.rgba8888(r / 255f, g / 255f, b / 255f, a / 255f);

                pixmap.drawPixel(xx - startX, yy - startY, rgba);
            }
        }

        return pixmap;
    }
}
