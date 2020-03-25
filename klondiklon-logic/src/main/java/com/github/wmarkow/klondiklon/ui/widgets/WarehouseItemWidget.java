package com.github.wmarkow.klondiklon.ui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.graphics.FontsRegistrar;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptor;

public class WarehouseItemWidget extends Table
{
    private StorageItemDescriptor storageItemDescriptor;
    private int itemQuantity;

    public WarehouseItemWidget(StorageItemDescriptor storageItemDescriptor, int itemQuantity) {
        this.storageItemDescriptor = storageItemDescriptor;
        this.itemQuantity = itemQuantity;

        create();
    }

    private void create()
    {
        NinePatch ninePatch = new NinePatch(ServiceRegistry.getInstance().getTexturesManager()
                .getTexture(TexturesRegistrar.WAREHOUSE_ITEM_BACKGROUND), 15, 15, 15, 15);
        setBackground(new NinePatchDrawable(ninePatch));

        setFillParent(false);
        row();

        final String textureName = storageItemDescriptor.getTextureName();
        Image image = new Image(ServiceRegistry.getInstance().getTexturesManager().getTexture(textureName));
        add(image);

        row();

        LabelStyle style = new LabelStyle();
        style.font = ServiceRegistry.getInstance().getFontsManager().getFont(FontsRegistrar.GRUBBING_FONT_NAME);
        style.fontColor = Color.WHITE;
        Label label = new Label(String.valueOf(itemQuantity), style);
        add(label).align(Align.center);
    }
}
