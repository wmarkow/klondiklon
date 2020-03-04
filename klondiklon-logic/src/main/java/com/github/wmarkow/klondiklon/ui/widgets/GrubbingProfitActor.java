package com.github.wmarkow.klondiklon.ui.widgets;

import java.util.Random;
import java.util.Set;

import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.github.wmarkow.klondiklon.Klondiklon;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.map.coordinates.gdx.GdxScreenCoordinates;
import com.github.wmarkow.klondiklon.objects.GrubbingProfit;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptor;

/***
 * Performs a nice animation on the screen of grubbed items. This actor removes
 * itself from the stage after animation is finished.
 * 
 */
public class GrubbingProfitActor extends Group
{
    private final static float ANIMATION_DURATION_IN_SECONDS = 2.5f;
    private Array<Path<Vector2>> paths = new Array<Path<Vector2>>();
    private float t = 0;

    public GrubbingProfitActor(Set<GrubbingProfit> grubbingProfits, GdxScreenCoordinates start) {
        createImages(grubbingProfits, start);
    }

    public void act(float delta)
    {
        super.act(delta);

        t += delta;

        if (t >= ANIMATION_DURATION_IN_SECONDS)
        {
            // end of animation
            getChildren().clear();
            getStage().getActors().removeValue(this, true);
            return;
        }

        float normalisedT = t / ANIMATION_DURATION_IN_SECONDS;
        float scale = 1.0f;
        if (normalisedT > 0.9)
        {
            scale = (1.0f - normalisedT) * 5f + 0.5f;
        }

        for (int q = 0; q < paths.size; q++)
        {
            Path<Vector2> path = paths.get(q);
            Vector2 current = new Vector2();
            path.valueAt(current, normalisedT);

            Actor actor = getChild(q);
            actor.setX(current.x);
            actor.setY(current.y);
            actor.setScale(scale);
        }
    }

    private void createImages(Set<GrubbingProfit> grubbingProfits, GdxScreenCoordinates start)
    {
        for (GrubbingProfit gp : grubbingProfits)
        {
            StorageItemDescriptor storageItemDescriptor = gp.getStorageItemDescriptor();
            for (int q = 0; q < gp.getAmount(); q++)
            {
                Image image = new Image(ServiceRegistry.getInstance().getTexturesManager()
                        .getTexture(storageItemDescriptor.getTextureName()));
                image.setX(start.getX());
                image.setY(start.getY());
                addActor(image);
            }
        }

        Random random = new Random(System.currentTimeMillis());
        for (int q = 0; q < getChildren().size; q++)
        {
            Actor actor = getChildren().get(q);

            float newX = actor.getX() + random.nextFloat() * 400.0f - 200.0f;
            float newY = actor.getY() + random.nextFloat() * 400.0f - 200.0f;
            actor.setX(newX);
            actor.setY(newY);

            Actor backpackButton = Klondiklon.ui.getMainView().getBackpackWidget();

            paths.add(new Bezier<Vector2>(new Vector2(start.getX(), start.getY()), new Vector2(newX, newY),
                    new Vector2(newX, newY), new Vector2(backpackButton.getX(), backpackButton.getY())));
        }
    }
}
