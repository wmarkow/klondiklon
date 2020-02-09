package com.github.wmarkow.klondiklon.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;

public class EnergyWidget extends Stack
{
    private Skin skin;

    private Image lightningImage;
    private ProgressBar progressBar;
    private Label text;

    public EnergyWidget(Skin skin) {
        this.skin = skin;

        Table t1 = new Table();
        t1.add(getProgressBar()).padLeft(0.3f * getLightningImage().getPrefWidth()).center();
        addActor(t1);

        Table t2 = new Table();
        t2.add(getText()).expand().padLeft(0.3f * getLightningImage().getPrefWidth()).center();
        addActor(t2);

        Table t3 = new Table();
        t3.add(getLightningImage()).expand().center().left();
        addActor(t3);
    }

    public Image getLightningImage()
    {
        if (lightningImage == null)
        {
            Texture texture = new Texture(Gdx.files.internal("images/lightning.png"));
            lightningImage = new ImageWidget(texture);
            lightningImage.setScale(0.65f);
        }
        return lightningImage;
    }

    public ProgressBar getProgressBar()
    {
        if (progressBar == null)
        {
            progressBar = new ProgressBar(0, 100, 1, false, skin);
            progressBar.setValue(78);
        }
        return progressBar;
    }

    public Label getText()
    {
        if (text == null)
        {
            text = new Label("78/100", skin);
        }
        return text;
    }

}
