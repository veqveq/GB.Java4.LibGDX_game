package ru.starwars.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class AnimatedSprite extends Sprite {

    private boolean startZooming = true;
    private boolean startMoving = true;
    protected boolean playingAnimation = false;

    private float alfaFromEmergence = 0;
    private float alfaFromFanding = 1;
    private Vector2 v = new Vector2();

    public AnimatedSprite(TextureRegion region, int cols, int rows, int frames) {
        super(region, cols, rows, frames);
    }

    public AnimatedSprite(TextureRegion[] regions) {
        super(regions);
    }

    public AnimatedSprite(TextureRegion texture) {
        super(texture);
    }

    public boolean zoomAnimation(float startScale, float finishScale, SpriteBatch batch, float speed) {
        try {
            if (startZooming) {
                setScale(startScale);
                startZooming = false;
                playingAnimation = true;
                return false;
            }
            if (getScale() < finishScale) {
                setScale(getScale() + (finishScale - startScale) / speed / 60);
                return false;
            }
            playingAnimation = false;
            return true;
        } finally {
            draw(batch);
        }
    }

    public boolean emergenceAnimation(SpriteBatch batch, float speed) {
        if (alfaFromEmergence < 1) {
            playingAnimation = true;
            alfaFromEmergence += 1 / speed / 60;
            batch.setColor(255, 255, 255, alfaFromEmergence);
            draw(batch);
            batch.setColor(255, 255, 255, 1);
            return false;
        }
        draw(batch);
        playingAnimation = false;
        return true;
    }

    public boolean fadingAnimation(SpriteBatch batch, float speed) {
        if (alfaFromFanding > 0) {
            playingAnimation = true;
            alfaFromFanding -= 1 / speed / 60;
            batch.setColor(255, 255, 255, alfaFromFanding);
            draw(batch);
            batch.setColor(255, 255, 255, 1);
            return false;
        }
        draw(batch);
        playingAnimation = false;
        return true;
    }

    public boolean movedAnimation(SpriteBatch batch, Vector2 start, Vector2 finish, float speed) {
        try {
            if (startMoving) {
                pos.set(start);
                v.set(finish.cpy().sub(start).nor().scl(1 / speed / 600));
                startMoving = false;
                playingAnimation = true;
                return false;
            }
            if (finish.cpy().sub(pos).len() > v.len()) {
                pos.add(v);
                return false;
            }
            pos.set(finish);
            playingAnimation = false;
            return true;
        } finally {
            draw(batch);
        }
    }

    public void resetAnimation(){
        startZooming = true;
        startMoving = true;
        playingAnimation = false;
        alfaFromEmergence = 0;
        alfaFromFanding = 1;
        v.setZero();
    }

    @Override
    public void destroy() {
        resetAnimation();
        super.destroy();
    }
}
