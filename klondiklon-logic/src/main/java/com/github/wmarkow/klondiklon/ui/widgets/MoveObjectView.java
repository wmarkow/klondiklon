package com.github.wmarkow.klondiklon.ui.widgets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.events.MoveObjectButtonCancelClickedEvent;
import com.github.wmarkow.klondiklon.events.MoveObjectButtonOkClickedEvent;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;

public class MoveObjectView extends Table
{
    private static Logger LOGGER = LoggerFactory.getLogger(MoveObjectView.class);

    private ImageButton buttonOk;
    private ImageButton buttonCancel;

    public MoveObjectView() {
        create();
    }

    private void create()
    {
        setFillParent(true);
        // setDebug(true);

        row().expandY();
        add().expandX();
        add(getOkButton()).align(Align.bottom).pad(20.0f);
        add(getCancelButton()).align(Align.bottom).pad(20.0f);
        add().expandX();
    }

    private ImageButton getOkButton()
    {
        if (buttonOk != null)
        {
            return buttonOk;
        }

        ImageButtonStyle style = new ImageButtonStyle();
        style.up = new TextureRegionDrawable(
                ServiceRegistry.getInstance().getTexturesManager().getTexture(TexturesRegistrar.BUTTON_OK));
        buttonOk = new ImageButton(style);
        buttonOk.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                LOGGER.info("Button OK clicked");
                event.cancel();
                ServiceRegistry.getInstance().getEventBus().publish(new MoveObjectButtonOkClickedEvent());
            }
        });

        return buttonOk;
    }

    private ImageButton getCancelButton()
    {
        if (buttonCancel != null)
        {
            return buttonCancel;
        }

        ImageButtonStyle style = new ImageButtonStyle();
        style.up = new TextureRegionDrawable(
                ServiceRegistry.getInstance().getTexturesManager().getTexture(TexturesRegistrar.BUTTON_CANCEL));
        buttonCancel = new ImageButton(style);
        buttonCancel.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                LOGGER.info("Button CANCEL clicked");
                event.cancel();
                ServiceRegistry.getInstance().getEventBus().publish(new MoveObjectButtonCancelClickedEvent());
            }
        });

        return buttonCancel;
    }
}
