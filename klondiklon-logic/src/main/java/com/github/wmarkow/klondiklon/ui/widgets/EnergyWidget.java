package com.github.wmarkow.klondiklon.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.wmarkow.klondiklon.Klondiklon;
import com.github.wmarkow.klondiklon.KlondiklonCore;
import com.github.wmarkow.klondiklon.event.Event;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.event.EventSubscriber;
import com.github.wmarkow.klondiklon.event.events.PlayerEnergyChangedEvent;

public class EnergyWidget extends Stack
{
    private Skin skin;

    private Image lightningImage;
    private ProgressBar progressBar;
    private Label text;
    private EventBus eventBus;

    public EnergyWidget(Skin skin, EventBus eventBus) {
        this.skin = skin;
        this.eventBus = eventBus;

        Table t1 = new Table();
        t1.add(getProgressBar()).padLeft(0.3f * getLightningImage().getPrefWidth()).center();
        addActor(t1);

        Table t2 = new Table();
        t2.add(getText()).expand().padLeft(0.3f * getLightningImage().getPrefWidth()).center();
        addActor(t2);

        Table t3 = new Table();
        t3.add(getLightningImage()).expand().center().left();
        addActor(t3);

        eventBus.subscribe(PlayerEnergyChangedEvent.class, new EventSubscriber()
        {

            @Override
            public void onEvent(Event event)
            {
                if (event instanceof PlayerEnergyChangedEvent)
                {
                    onPlayerEnergyChanged((PlayerEnergyChangedEvent) event);
                }
            }
        });
    }

    public void setEnergy(int energy, int maxRestorableEnergy)
    {
        getText().setText(String.format("%s/%s", energy, maxRestorableEnergy));

        float valueInPercent = 100f * energy / maxRestorableEnergy;
        getProgressBar().setValue(valueInPercent);
    }

    public Image getLightningImage()
    {
        if (lightningImage == null)
        {
            Texture texture = KlondiklonCore.texturesManager.LIGHTNING;
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
            progressBar.setAnimateDuration(1.0f);
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

    private void onPlayerEnergyChanged(PlayerEnergyChangedEvent event)
    {
        setEnergy(event.getCurrentEnergy(), event.getMaxRestorableEnergy());
    }
}
