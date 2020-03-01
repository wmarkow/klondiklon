package com.github.wmarkow.klondiklon.ui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.graphics.FontsRegistrar;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;
import com.github.wmarkow.klondiklon.warehouse.WarehouseItemQuantity;

public class WarehouseItemWidget extends Table
{
    private WarehouseItemQuantity itemQuantity;

    public WarehouseItemWidget(WarehouseItemQuantity itemQuantity) {
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

        final String textureName = itemQuantity.getStorageItemDescriptor().getTextureName();
        Image image = new Image(ServiceRegistry.getInstance().getTexturesManager().getTexture(textureName));
        add(image);

        row();

        LabelStyle style = new LabelStyle();
        style.font = ServiceRegistry.getInstance().getFontsManager().getFont(FontsRegistrar.GRUBBING_FONT_NAME);
        style.fontColor = Color.WHITE;
        Label label = new Label(String.valueOf(itemQuantity.getQuantity()), style);
        add(label).align(Align.center);
    }
}
