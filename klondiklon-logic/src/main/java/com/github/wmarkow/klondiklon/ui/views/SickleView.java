package com.github.wmarkow.klondiklon.ui.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;
import com.github.wmarkow.klondiklon.map.coordinates.CoordinateCalculator;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenBounds;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxTouchCoordinates;

public class SickleView extends Group
{
    private CoordinateCalculator coordinateCalculator = new CoordinateCalculator();
    private Image sickleImage = null;

    public SickleView() {
        create();
    }

    private void create()
    {

    }

    public void setSickleCoordinates(GdxScreenCoordinates screenCoordinates)
    {
        Image sickle = getSickleImage();

        sickle.setX(screenCoordinates.getX());
        sickle.setY(screenCoordinates.getY());
    }

    public GdxScreenBounds getSickleImageBounds()
    {
        int x = (int) sickleImage.getX();
        int y = (int) sickleImage.getY();
        int width = (int) sickleImage.getImageWidth();
        int height = (int) sickleImage.getImageHeight();

        return new GdxScreenBounds(x, y, width, height);
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
