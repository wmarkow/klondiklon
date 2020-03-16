package com.github.wmarkow.klondiklon.ui.views;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenBounds;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;

public class SickleView extends Group
{
    private Image sickleImage = null;

    public SickleView() {
        createSickleImage();
    }

    public void setSickleCoordinates(GdxScreenCoordinates screenCoordinates)
    {
        int width = (int)sickleImage.getImageWidth();
        int height = (int) sickleImage.getImageHeight();
        
        sickleImage.setX(screenCoordinates.getX() - width / 2);
        sickleImage.setY(screenCoordinates.getY() - height / 2);
    }

    public GdxScreenBounds getSickleImageBounds()
    {
        int x = (int) sickleImage.getX();
        int y = (int) sickleImage.getY();
        int width = (int) sickleImage.getImageWidth();
        int height = (int) sickleImage.getImageHeight();

        return new GdxScreenBounds(x, y, width, height);
    }

    private Image createSickleImage()
    {
        sickleImage = new Image(
                ServiceRegistry.getInstance().getTexturesManager().getTexture(TexturesRegistrar.SICKLE));
        sickleImage.setX(0);
        sickleImage.setY(0);

        addActor(sickleImage);

        return sickleImage;
    }
}
