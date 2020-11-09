package ru.starwars.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.Sprite;
import ru.starwars.math.Rect;
import ru.starwars.math.Rnd;

public class Star extends Sprite {

    private Rect worldBounds;
    private Vector2 v;
    private Vector2 playerV;
    private Vector2 sumV;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        setHeightProportion(Rnd.nextFloat(0.005f,0.015f));
        v = new Vector2(Rnd.nextFloat(-0.005f,+0.005f),getHeight()*-15f);
    }

    public Star(TextureAtlas atlas, Vector2 playerV) {
        super(atlas.findRegion("star"));
        setHeightProportion(Rnd.nextFloat(0.005f,0.015f));
        v = new Vector2(Rnd.nextFloat(-0.005f,+0.005f),getHeight()*-15f);
        this.playerV = playerV;
        this.sumV = new Vector2();
    }

    @Override
    public void update(float delta) {
        try{
            sumV.setZero().mulAdd(playerV,20f).rotate(180).add(v);
            pos.mulAdd(sumV,delta);
        }catch (NullPointerException e){
            pos.mulAdd(v,delta);
        }
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
