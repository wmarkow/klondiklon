package com.github.wmarkow.klondiklon.ui;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.event.Event;
import com.github.wmarkow.klondiklon.event.EventSubscriber;
import com.github.wmarkow.klondiklon.events.WarehouseButtonBackpackClickedEvent;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;
import com.github.wmarkow.klondiklon.objects.GrubbingProfit;
import com.github.wmarkow.klondiklon.ui.views.MainView;
import com.github.wmarkow.klondiklon.ui.views.MoveObjectView;
import com.github.wmarkow.klondiklon.ui.views.ProfitView;
import com.github.wmarkow.klondiklon.ui.views.SeedView;
import com.github.wmarkow.klondiklon.ui.views.HarvestView;
import com.github.wmarkow.klondiklon.ui.views.WarehouseView;

public class KKUi implements EventSubscriber
{
    private static Logger LOGGER = LoggerFactory.getLogger(KKUi.class);

    private Stage stage;
    private Stack stack;

    private MainView mainView = null;
    private WarehouseView warehouseWidget = null;
    private MoveObjectView moveObjectView = null;
    private HarvestView sickleView = null;
    private SeedView seedView = null;

    public KKUi() {
        create();

        ServiceRegistry.getInstance().getEventBus().subscribe(WarehouseButtonBackpackClickedEvent.class, this);
    }

    public Stage getStage()
    {
        return stage;
    }

    public MainView getMainView()
    {
        return mainView;
    }

    public void showMoveObjectView()
    {
        moveObjectView = new MoveObjectView();
        stack.addActor(moveObjectView);
        mainView.setVisible(false);
    }

    public void hideMoveObjectView()
    {
        mainView.setVisible(true);

        if (moveObjectView == null)
        {
            return;
        }
        stack.removeActor(moveObjectView);
        moveObjectView = null;
    }

    public HarvestView showSickleView()
    {
        sickleView = new HarvestView();
        stack.addActor(sickleView);

        return sickleView;
    }

    public void hideSickleView()
    {
        if (sickleView == null)
        {
            return;
        }
        stack.removeActor(sickleView);
        sickleView = null;
    }

    public SeedView showSeedView(GdxScreenCoordinates gdxScreenCoordinates)
    {
        seedView = new SeedView(gdxScreenCoordinates);
        stack.addActor(seedView);

        return seedView;
    }

    public void hideSeedView()
    {
        if (seedView == null)
        {
            return;
        }
        stack.removeActor(seedView);
        seedView = null;
    }

    public void showProfitView(Set<GrubbingProfit> grubbingProfits, GdxScreenCoordinates startPoint)
    {
        ProfitView profitView = new ProfitView(grubbingProfits, startPoint);
        stack.addActor(profitView);
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

        stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);

        mainView = new MainView();
        stack.addActor(mainView);
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
