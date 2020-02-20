package com.github.wmarkow.klondiklon.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.wmarkow.klondiklon.Klondiklon;
import com.github.wmarkow.klondiklon.KlondiklonCore;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;
import com.github.wmarkow.klondiklon.warehouse.WarehouseItemQuantity;

public class WarehouseWidget extends Table
{

    public WarehouseWidget() {
        setFillParent(false);
        setDebug(true);

        create();

        addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                WarehouseWidget.this.remove();
            }
        });
    }

    private void create()
    {
        int index = 0;
        for (WarehouseItemQuantity itemQuantity : Klondiklon.warehouse.getWarehouseItemQuantities())
        {
            if (index % 5 == 0)
            {
                row();
            }
            final String textureName = itemQuantity.getStorageItemDescriptor().getTextureName();
            Image image = new Image(KlondiklonCore.texturesManager.getTexture(textureName));
            add(image);

            index++;
        }
    }
}
