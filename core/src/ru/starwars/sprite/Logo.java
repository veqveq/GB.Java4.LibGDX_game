package ru.starwars.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.BaseScreen;
import ru.starwars.base.Sprite;
import ru.starwars.math.Rect;

public class Logo extends Sprite implements InputProcessor {

    protected Vector2 touch = new Vector2();
    private Vector2 v = new Vector2();
    private Vector2 tmp = new Vector2();
    private float SPEED = 0.03f;

    public Logo(TextureRegion region) {
        super(region);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight() * 0.2f);
        pos.set(0f, -0.35f);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        tmp.set(touch);
        if (tmp.sub(pos).len() <=v.len()) {
            pos.set(touch);
        }else{
            pos.add(v);
        }
        super.draw(batch);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown screenX = " + screenX + " screenY = " + screenY);
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(BaseScreen.getScreenToWorld());
        v=touch.cpy().sub(pos).setLength(SPEED);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
