package com.github.wmarkow.klondiklon.desktop;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.github.wmarkow.klondiklon.resources.graphics.ImageReaderIf;

public class DesktopImageReader implements ImageReaderIf
{
    @Override
    public Pixmap readImage(String path, int startX, int startY, int width, int height)
    {
        BufferedImage image;
        try
        {
            image = ImageIO.read(new File(path));
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        BufferedImage buffered = new BufferedImage(image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        buffered.getGraphics().drawImage(image, 0, 0, null);
        
        return toPixmap(buffered, startX, startY, width, height);
    }

    private Pixmap toPixmap(BufferedImage bi, int startX, int startY, int width, int height)
    {
        final int imageWidth = width;
        final int imageHeight = height;

        Pixmap pixmap = new Pixmap(imageWidth, imageHeight, Format.RGBA8888);

        for (int xx = startX; xx < startX + width; xx++)
        {
            for (int yy = startY; yy < startY + height; yy++)
            {
                final int rgb = bi.getRGB(xx, yy);
                final float r = bi.getColorModel().getRed(rgb);
                final float g = bi.getColorModel().getGreen(rgb);
                final float b = bi.getColorModel().getBlue(rgb);
                final float a = bi.getColorModel().getAlpha(rgb);
                final int rgba = Color.rgba8888(r / 255f, g / 255f, b / 255f, a / 255f);

                pixmap.drawPixel(xx - startX, yy - startY, rgba);
            }
        }

        return pixmap;
    }
}
