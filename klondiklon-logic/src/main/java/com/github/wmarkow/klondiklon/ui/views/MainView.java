package com.github.wmarkow.klondiklon.ui.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.github.wmarkow.klondiklon.Klondiklon;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.events.WarehouseButtonBackpackClickedEvent;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;
import com.github.wmarkow.klondiklon.player.Player;
import com.github.wmarkow.klondiklon.resources.graphics.SkinsManager;
import com.github.wmarkow.klondiklon.ui.widgets.EnergyWidget;

public class MainView extends Table
{
    private static Logger LOGGER = LoggerFactory.getLogger(MainView.class);

    private EnergyWidget energyWidget = null;
    private ImageButton imageButton = null;

    public MainView() {
        create();
    }

    public ImageButton getBackpackWidget()
    {
        return imageButton;
    }

    private void create()
    {
        row();
        add().expandX();
        add(getEnergyWidget()).center();
        add().expandX();
        row();
        add().expand();
        row();
        add().expandX();
        add().expandX();
        add(getBackpackButton()).center();
    }

    private EnergyWidget getEnergyWidget()
    {
        if (energyWidget == null)
        {
            SkinsManager skinsManager = ServiceRegistry.getInstance().getSkinsManager();

            Skin skin = skinsManager.getSkin(SkinsManager.GLASSY);
            EventBus eventBus = ServiceRegistry.getInstance().getEventBus();
            Player player = Klondiklon.gameplayService.getPlayer();

            energyWidget = new EnergyWidget(skin, eventBus);
            energyWidget.setEnergy(player.getEnergy(), player.getMaxRestorableEnergy());
        }

        return energyWidget;
    }

    private ImageButton getBackpackButton()
    {
        if (imageButton == null)
        {
            ImageButtonStyle style = new ImageButtonStyle();
            style.up = new TextureRegionDrawable(
                    ServiceRegistry.getInstance().getTexturesManager().getTexture(TexturesRegistrar.BACKPACK));
            imageButton = new ImageButton(style);
            imageButton.addListener(new ClickListener()
            {
                public void clicked(InputEvent event, float x, float y)
                {
                    event.cancel();
                    LOGGER.info("Backpack button clicked");
                    ServiceRegistry.getInstance().getEventBus().publish(new WarehouseButtonBackpackClickedEvent());
                }
            });
        }

        return imageButton;
    }
}
