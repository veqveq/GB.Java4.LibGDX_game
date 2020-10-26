package ru.starwars.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.Sprite;
import ru.starwars.math.Rect;
import ru.starwars.math.Rnd;

public class Star extends Sprite {

    private Rect worldBounds;
    private Vector2 v;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        setHeightProportion(Rnd.nextFloat(0.005f,0.015f));
        v = new Vector2(Rnd.nextFloat(-0.005f,+0.005f),getHeight()*-15f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v,delta);
        if (getLeft()>worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getRight()<worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getTop()<worldBounds.getBottom()) setBottom(worldBounds.getTop());
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        pos.set(Rnd.nextFloat(worldBounds.getLeft(),worldBounds.getRight()),Rnd.nextFloat(worldBounds.getBottom(),worldBounds.getTop()));
    }
}
