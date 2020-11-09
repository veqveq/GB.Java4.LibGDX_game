package ru.starwars.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.Sprite;
import ru.starwars.math.Rect;

public class Background extends Sprite {

    private Vector2 playerV;
    private Vector2 sumV = new Vector2();

    public Background(TextureRegion region) {
        super(region);
    }

    public Background(TextureRegion region, Vector2 playerV) {
        super(region);
        this.playerV = playerV;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }

    @Override
    public void update(float delta) {
        if (getLeft() > -getHalfHeight()-getHalfWidth() && getRight() < getHalfHeight()+getHalfWidth()) {
            try {
                sumV.setZero().mulAdd(playerV, 10f).rotate(180);
                pos.mulAdd(sumV, delta);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
