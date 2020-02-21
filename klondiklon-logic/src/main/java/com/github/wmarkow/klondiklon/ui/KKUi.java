package com.github.wmarkow.klondiklon.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.wmarkow.klondiklon.KlondiklonCore;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;
import com.github.wmarkow.klondiklon.player.Player;
import com.github.wmarkow.klondiklon.ui.widgets.EnergyWidget;
import com.github.wmarkow.klondiklon.ui.widgets.WarehouseWidget;

public class KKUi
{
    private static Logger LOGGER = LoggerFactory.getLogger(KKUi.class);

    private Stage stage;
    private Skin mySkin;
    private Stack stack;
    private Table tableLayout;
    private WarehouseWidget warehouseWidget = null;
    private Player player;
    private EventBus eventBus;

    private EnergyWidget energyWidget = null;
    private ImageButton imageButton = null;

    public KKUi(Player player, EventBus eventBus) {
        this.player = player;
        this.eventBus = eventBus;

        create();
    }

    public Stage getStage()
    {
        return stage;
    }

    public ImageButton getBackpackWidget()
    {
        return imageButton;
    }

    private void create()
    {
        stage = new Stage(new ScreenViewport());
        mySkin = KlondiklonCore.skinsManager.GLASSY;

        stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);

        tableLayout = new Table();
        tableLayout.setFillParent(true);
//        tableLayout.setDebug(true);
        stack.addActor(tableLayout);

        tableLayout.row();
        tableLayout.add().expandX();
        tableLayout.add(getEnergyWidget()).center();
        tableLayout.add().expandX();
        tableLayout.row();
        tableLayout.add().expand();
        tableLayout.row();
        tableLayout.add().expandX();
        tableLayout.add().expandX();
        tableLayout.add(getBackpackButton()).center();
    }

    private EnergyWidget getEnergyWidget()
    {
        if (energyWidget == null)
        {
            energyWidget = new EnergyWidget(mySkin, eventBus);
            energyWidget.setEnergy(player.getEnergy(), player.getMaxRestorableEnergy());
        }

        return energyWidget;
    }

    private ImageButton getBackpackButton()
    {
        if (imageButton == null)
        {
            ImageButtonStyle style = new ImageButtonStyle();
            style.up = new TextureRegionDrawable(KlondiklonCore.texturesManager.getTexture(TexturesRegistrar.BACKPACK));
            imageButton = new ImageButton(style);
            imageButton.addListener(new ClickListener()
            {
                public void clicked(InputEvent event, float x, float y)
                {
                    event.cancel();
                    LOGGER.info("Backpack button clicked");
                    showWarehouseWidget();
                }
            });
        }

        return imageButton;
    }

    private boolean isWarehouseVisible()
    {
        if (warehouseWidget == null)
        {
            return false;
        }

        if (stack.getChildren().contains(warehouseWidget, true))
        {
            return true;
        }

        warehouseWidget = null;
        return false;
    }

    private void showWarehouseWidget()
    {
        if (isWarehouseVisible())
        {
            return;
        }

        warehouseWidget = new WarehouseWidget();
        stack.addActor(warehouseWidget);
    }
}
