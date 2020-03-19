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
    private Image beanImage = null;

    public SeedView(GdxScreenCoordinates screenCoordinates) {
        createWheatImage();
        createBeanImage();

        beanImage.setX(screenCoordinates.getX() - 200);
        beanImage.setY(screenCoordinates.getY());

        wheatImage.setX(screenCoordinates.getX() - 100);
        wheatImage.setY(screenCoordinates.getY() + 100);
    }

    public StorageItemDescriptor getSeedItemDescriptor(GdxScreenCoordinates screenPoint)
    {
        if (getBounds(beanImage).containsPoint(screenPoint))
        {
            return Klondiklon.storageItemDescriptorsManager.getByType(StorageItemTypes.BEAN);
        }

        if (getBounds(wheatImage).containsPoint(screenPoint))
        {
            return Klondiklon.storageItemDescriptorsManager.getByType(StorageItemTypes.WHEAT);
        }

        return null;
    }

    public void setSeedItemCoordinates(StorageItemDescriptor seedItemDescriptor, GdxScreenCoordinates screenPoint)
    {
        Image image = null;

        if (StorageItemTypes.WHEAT.equals(seedItemDescriptor.getStorageItemType()))
        {
            image = wheatImage;
        } else if (StorageItemTypes.BEAN.equals(seedItemDescriptor.getStorageItemType()))
        {
            image = beanImage;
        }

        if (image == null)
        {
            return;
        }

        int width = (int) image.getImageWidth();
        int height = (int) image.getImageHeight();
        image.setX(screenPoint.getX() - width / 2);
        image.setY(screenPoint.getY() - height / 2);
    }

    private void createWheatImage()
    {
        wheatImage = new Image(
                ServiceRegistry.getInstance().getTexturesManager().getTexture(TexturesRegistrar.STORAGE_ITEM_WHEAT));
        wheatImage.setX(0);
        wheatImage.setY(0);

        addActor(wheatImage);
    }

    private void createBeanImage()
    {
        beanImage = new Image(
                ServiceRegistry.getInstance().getTexturesManager().getTexture(TexturesRegistrar.STORAGE_ITEM_BEAN));
        beanImage.setX(0);
        beanImage.setY(0);

        addActor(beanImage);
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
