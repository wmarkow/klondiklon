package com.github.wmarkow.klondiklon.ui.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxTouchCoordinates;

public class SickleView extends Group
{
    private Image sickleImage = null;

    public SickleView() {
        create();
    }

    private void create()
    {

    }

    public void setSickleCoordinates(GdxTouchCoordinates touchCoordinates)
    {
        Image sickle = getSickleImage();

        int screenY = Gdx.graphics.getHeight() - 1 - touchCoordinates.getY();
        sickle.setX(touchCoordinates.getX());
        sickle.setY(screenY);
    }

    private Image getSickleImage()
    {
        if (sickleImage == null)
        {
            sickleImage = new Image(
                    ServiceRegistry.getInstance().getTexturesManager().getTexture(TexturesRegistrar.SICKLE));
            sickleImage.setX(0);
            sickleImage.setY(0);

            addActor(sickleImage);
        }

        return sickleImage;
    }
}
