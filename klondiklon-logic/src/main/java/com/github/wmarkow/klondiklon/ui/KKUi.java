package com.github.wmarkow.klondiklon.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.event.Event;
import com.github.wmarkow.klondiklon.event.EventBus;
import com.github.wmarkow.klondiklon.event.EventSubscriber;
import com.github.wmarkow.klondiklon.events.WarehouseButtonBackpackClickedEvent;
import com.github.wmarkow.klondiklon.graphics.TexturesRegistrar;
import com.github.wmarkow.klondiklon.player.Player;
import com.github.wmarkow.klondiklon.ui.views.MoveObjectView;
import com.github.wmarkow.klondiklon.ui.views.WarehouseView;
import com.github.wmarkow.klondiklon.ui.widgets.EnergyWidget;

public class KKUi implements EventSubscriber
{
    private static Logger LOGGER = LoggerFactory.getLogger(KKUi.class);

    private Stage stage;
    private Skin mySkin;
    private Stack stack;
    private Table tableLayout;
    private WarehouseView warehouseWidget = null;
    private MoveObjectView moveObjectView = null;
    private Player player;
    private EventBus eventBus;

    private EnergyWidget energyWidget = null;
    private ImageButton imageButton = null;

    public KKUi(Player player, EventBus eventBus) {
        this.player = player;
        this.eventBus = eventBus;

        create();

        ServiceRegistry.getInstance().getEventBus().subscribe(WarehouseButtonBackpackClickedEvent.class, this);
    }

    public Stage getStage()
    {
        return stage;
    }

    public ImageButton getBackpackWidget()
    {
        return imageButton;
    }

    public void showMoveObjectView()
    {
        moveObjectView = new MoveObjectView();
        stack.addActor(moveObjectView);
    }

    public void hideMoveObjectView()
    {
        if (moveObjectView == null)
        {
            return;
        }
        stack.removeActor(moveObjectView);
        moveObjectView = null;
    }

    @Override
    public void onEvent(Event event)
    {
        if (event instanceof WarehouseButtonBackpackClickedEvent)
        {
            if (warehouseWidget == null)
            {
                showWarehouseWidget();
            } else
            {
                hideWarehouseWidget();
            }
        }
    }

    private void create()
    {
        stage = new Stage(new ScreenViewport());
        mySkin = ServiceRegistry.getInstance().getSkinsManager().GLASSY;

        stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);

        tableLayout = new Table();
        tableLayout.setFillParent(true);
        // tableLayout.setDebug(true);
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

    private void showWarehouseWidget()
    {
        if (warehouseWidget != null)
        {
            return;
        }

        warehouseWidget = new WarehouseView();
        stack.addActor(warehouseWidget);
    }

    private void hideWarehouseWidget()
    {
        if (warehouseWidget == null)
        {
            return;
        }

        stack.removeActor(warehouseWidget);
        warehouseWidget = null;
    }
}
