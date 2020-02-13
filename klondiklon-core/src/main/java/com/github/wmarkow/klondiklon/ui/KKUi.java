package com.github.wmarkow.klondiklon.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.wmarkow.klondiklon.Klondiklon;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.player.Player;
import com.github.wmarkow.klondiklon.ui.widgets.EnergyWidget;

public class KKUi
{
    private static Logger LOGGER = LoggerFactory.getLogger(KKUi.class);

    private Stage stage;
    private Skin mySkin;
    private Table tableLayout;
    private Player player;
    private EventBus eventBus;

    public KKUi(Player player, EventBus eventBus) {
        this.player = player;
        this.eventBus = eventBus;

        create();
    }

    public Stage getStage()
    {
        return stage;
    }

    private void create()
    {
        stage = new Stage(new ScreenViewport());
        mySkin = Klondiklon.skinsManager.GLASSY;

        tableLayout = new Table();
        tableLayout.setFillParent(true);
        // tableLayout.setDebug(true);
        stage.addActor(tableLayout);

        EnergyWidget energyWidget = new EnergyWidget(mySkin, eventBus);
        energyWidget.setEnergy(player.getEnergy(), player.getMaxRestorableEnergy());

        tableLayout.row();
        tableLayout.add().expandX();
        tableLayout.add(energyWidget).center();
        tableLayout.add().expandX();
        tableLayout.row();
        tableLayout.add().expand();
    }
}
