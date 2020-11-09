package ru.starwars.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseButton extends AnimatedSprite {

    protected boolean doAction;
    private boolean pressed;
    protected Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds\\btClick.wav"));


    public BaseButton(TextureRegion texture) {
        super(texture);
    }

    public BaseButton(TextureRegion[] texture) {
        super(texture);
    }

    @Override
    public boolean mouseMoved(Vector2 cursorPosition) {
        if (!doAction && !playingAnimation) {
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
        if (!pressed && isMe(touch) && !playingAnimation) {
            pressed = true;
            scale = 1f;
        }
        clickSound.play();
        return false;
    }

    public void resize(float height, float x, float y) {
        setHeightProportion(height);
        pos.set(x, y);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (!playingAnimation)
            if (pressed && isMe(touch)) {
                scale = 1.2f;
                doAction = true;
                pressed = false;
            } else {
                scale = 1f;
            }
        return false;
    }

    @Override
    public void update(float delta) {
        if (doAction) {
            doAction = false;
            action();
        }
    }

    public void dispose(){
        clickSound.dispose();
    }

    protected abstract void action();
}
