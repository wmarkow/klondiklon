package com.github.wmarkow.klondiklon.ui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.github.wmarkow.klondiklon.Klondiklon;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.graphics.FontsRegistrar;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;
import com.github.wmarkow.klondiklon.warehouse.WarehouseItemQuantity;

public class WarehouseWidget extends Container<Table>
{
    private Table table;

    public WarehouseWidget() {
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
        NinePatch ninePatch = new NinePatch(
                ServiceRegistry.getInstance().getTexturesManager().getTexture(TexturesRegistrar.WAREHOUSE_BACKGROUND),
                15, 15, 15, 15);

        table = new Table();
        table.setFillParent(false);
        // table.setDebug(true);
        table.setBackground(new NinePatchDrawable(ninePatch));

        table.row();
        LabelStyle style = new LabelStyle();
        style.font = ServiceRegistry.getInstance().getFontsManager().getFont(FontsRegistrar.GRUBBING_FONT_NAME);
        style.fontColor = Color.WHITE;
        Label label = new Label("MAGAZYN", style);
        table.add(label).align(Align.top | Align.center);
        table.row();
        table.add(createItemsTable()).expand().align(Align.top | Align.center);

        setActor(table);
    }

    private Table createItemsTable()
    {
        Table itemsTable = new Table();
        itemsTable.setFillParent(false);

        int index = 0;
        for (WarehouseItemQuantity itemQuantity : Klondiklon.warehouse.getWarehouseItemQuantities())
        {
            if (index % 5 == 0)
            {
                itemsTable.row();
            }
            WarehouseItemWidget warehouseItemWidget = new WarehouseItemWidget(itemQuantity);
            itemsTable.add(warehouseItemWidget).pad(5.0f);

            index++;
        }

        return itemsTable;
    }
}
