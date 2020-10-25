package ru.starwars.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.BaseScreen;
import ru.starwars.base.MenuButton;
import ru.starwars.math.Rect;

public class ExitButton extends MenuButton {

    public ExitButton(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight() * 0.1f);
        pos.set(0.15f, 0.3f);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (isMe(new Vector2(screenX, Gdx.graphics.getHeight() - screenY).mul(BaseScreen.getScreenToWorld()))) {
            touchEnd = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    animation(50);
                    System.exit(0);
                }
            }).start();
            return true;
        }
        return false;
    }
}
