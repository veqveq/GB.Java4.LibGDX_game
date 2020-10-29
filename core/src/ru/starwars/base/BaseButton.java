package ru.starwars.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseButton extends Sprite {

    protected boolean playAnimation = true;
    private int pointer;
    private boolean pressed;


    public BaseButton(TextureRegion texture) {
        super(texture);
    }

    public BaseButton(TextureRegion[] texture) {
        super(texture);
    }

    public boolean mouseMoved(Vector2 cursorPosition) {
        if (playAnimation) {
            if (isMe(cursorPosition)) {
                scale = 1.2f;
            } else {
                scale = 1f;
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (!pressed && isMe(touch)){
            playAnimation = false;
            this.pointer = pointer;
            pressed = true;
            scale = 1f;
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pressed && isMe(touch)){
            scale = 1.2f;
            action();
            pressed = false;
        }
        scale = 1f;
        playAnimation = true;
        return false;
    }

    protected abstract void action();
}
