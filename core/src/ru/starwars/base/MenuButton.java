package ru.starwars.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class MenuButton extends Sprite implements InputProcessor {

    protected boolean touchEnd;

    public MenuButton(TextureRegion region) {
        super(region);
        Gdx.input.setInputProcessor(this);
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
        if (!touchEnd) {
            Vector2 pos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY).mul(BaseScreen.getScreenToWorld());
            if (isMe(pos)) {
                scale = 1.2f;
            } else {
                scale = 1f;
            }
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    protected void animation(float pictureSize) {
        while (scale < pictureSize) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scale += 1f;
        }
    }
}
