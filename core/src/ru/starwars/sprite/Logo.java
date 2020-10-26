package ru.starwars.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.Sprite;
import ru.starwars.math.Rect;

public class Logo extends Sprite {

    protected Vector2 touch = new Vector2();
    private Vector2 v = new Vector2();
    private Vector2 tmp = new Vector2();
    private float SPEED = 0.03f;

    public Logo(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        pos.set(0f, -0.35f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        tmp.set(touch);
        if (tmp.sub(pos).len() <=SPEED) {
            pos.set(touch);
        }else{
            pos.add(v);
        }
        super.draw(batch);
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        System.out.println("touchDown screenX = " + touch.x + " screenY = " + touch.y);
        this.touch.set(touch);
        v=touch.cpy().sub(pos).setLength(SPEED);
        return false;
    }
}
