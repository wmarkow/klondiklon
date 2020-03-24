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
    private Image grassImage = null;
    private Image cornImage = null;

    public SeedView(GdxScreenCoordinates screenCoordinates) {
        createWheatImage();
        createBeanImage();
        createGrassImage();
        createCornImage();

        int deltaX = 125;
        int startX = -200;
        int startY = 0;
        int deltaY = 70;

        beanImage.setX(screenCoordinates.getX() + startX);
        beanImage.setY(screenCoordinates.getY() + startY);

        wheatImage.setX(screenCoordinates.getX() + startX + deltaX);
        wheatImage.setY(screenCoordinates.getY() + startY + deltaY);

        cornImage.setX(screenCoordinates.getX() + startX + 2 * deltaX);
        cornImage.setY(screenCoordinates.getY() + startY + 2 * deltaY);

        grassImage.setX(screenCoordinates.getX() - 185);
        grassImage.setY(screenCoordinates.getY() + 100);
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

        if (getBounds(grassImage).containsPoint(screenPoint))
        {
            return Klondiklon.storageItemDescriptorsManager.getByType(StorageItemTypes.GRASS);
        }

        if (getBounds(cornImage).containsPoint(screenPoint))
        {
            return Klondiklon.storageItemDescriptorsManager.getByType(StorageItemTypes.CORN);
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
        } else if (StorageItemTypes.GRASS.equals(seedItemDescriptor.getStorageItemType()))
        {
            image = grassImage;
        } else if (StorageItemTypes.CORN.equals(seedItemDescriptor.getStorageItemType()))
        {
            image = cornImage;
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

    private void createGrassImage()
    {
        grassImage = new Image(
                ServiceRegistry.getInstance().getTexturesManager().getTexture(TexturesRegistrar.STORAGE_ITEM_GRASS));
        grassImage.setX(0);
        grassImage.setY(0);

        addActor(grassImage);
    }

    private void createCornImage()
    {
        cornImage = new Image(
                ServiceRegistry.getInstance().getTexturesManager().getTexture(TexturesRegistrar.STORAGE_ITEM_CORN));
        cornImage.setX(0);
        cornImage.setY(0);

        addActor(cornImage);
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
