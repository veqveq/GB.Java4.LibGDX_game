package ru.starwars.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseButton extends Sprite {

    protected boolean doAction;
    private boolean pressed;
    protected Sound clickSound;


    public BaseButton(TextureRegion texture, Sound clickSound) {
        super(texture);
        this.clickSound = clickSound;
    }

    public BaseButton(TextureRegion[] texture, Sound clickSound) {
        super(texture);
        this.clickSound = clickSound;
    }

    public boolean mouseMoved(Vector2 cursorPosition) {
        if (!doAction) {
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
        if (!pressed && isMe(touch)) {
            pressed = true;
            scale = 1f;
            clickSound.play();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pressed && isMe(touch)) {
            scale = 1.2f;
            doAction = true;
            pressed = false;
        }
        scale = 1f;
        return false;
    }

    @Override
    public void update(float delta) {
        if (doAction) action();
    }

    protected abstract void action();
}
