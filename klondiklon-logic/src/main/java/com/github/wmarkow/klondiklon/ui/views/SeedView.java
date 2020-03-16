package com.github.wmarkow.klondiklon.ui.views;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.wmarkow.klondiklon.Klondiklon;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenBounds;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptor;
import com.github.wmarkow.klondiklon.objects.StorageItemTypes;

public class SeedView extends Group
{
    private Image wheatImage = null;

    public SeedView(GdxScreenCoordinates screenCoordinates) {
        createWheatImage();

        wheatImage.setX(screenCoordinates.getX() - 200);
        wheatImage.setY(screenCoordinates.getY());
    }

    public StorageItemDescriptor getSeedItemDescriptor(GdxScreenCoordinates screenPoint)
    {
        GdxScreenBounds wheatBounds = getBounds(wheatImage);
        if (wheatBounds.containsPoint(screenPoint))
        {
            return Klondiklon.storageItemDescriptorsManager.getByType(StorageItemTypes.WHEAT);
        }

        return null;
    }

    public void setSeedItemCoordinates(StorageItemDescriptor seedItemDescriptor, GdxScreenCoordinates screenPoint)
    {
        if (StorageItemTypes.WHEAT.equals(seedItemDescriptor.getStorageItemType()))
        {
            wheatImage.setX(screenPoint.getX());
            wheatImage.setY(screenPoint.getY());
        }
    }

    private void createWheatImage()
    {
        wheatImage = new Image(
                ServiceRegistry.getInstance().getTexturesManager().getTexture(TexturesRegistrar.STORAGE_ITEM_WHEAT));
        wheatImage.setX(0);
        wheatImage.setY(0);

        addActor(wheatImage);
    }

    private GdxScreenBounds getBounds(Image image)
    {
        int x = (int) image.getX();
        int y = (int) image.getY();
        int width = (int) image.getImageWidth();
        int height = (int) image.getImageHeight();

        return new GdxScreenBounds(x, y, width, height);
    }
}
