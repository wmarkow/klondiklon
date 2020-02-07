package com.github.wmarkow.klondiklon.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.wmarkow.klondiklon.ui.widgets.EnergyWidget;

public class KKUi
{
    private static Logger LOGGER = LoggerFactory.getLogger(KKUi.class);

    private Stage stage;
    private Skin mySkin;
    private Table tableLayout;

    public KKUi() {
        create();
    }

    public Stage getStage()
    {
        return stage;
    }

    private void create()
    {
        stage = new Stage(new ScreenViewport());
        mySkin = new Skin(Gdx.files.internal("skins/glassy/skin/glassy-ui.json"));

        tableLayout = new Table();
        tableLayout.setFillParent(true);
        tableLayout.setDebug(true);
        stage.addActor(tableLayout);

        EnergyWidget energyWidget = new EnergyWidget(mySkin);

        tableLayout.row();
        tableLayout.add().expandX();
        tableLayout.add(energyWidget).center();
        tableLayout.add().expandX();
        tableLayout.row();
        tableLayout.add().expand();
    }
}
