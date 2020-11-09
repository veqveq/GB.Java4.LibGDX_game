package ru.starwars.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class AnimatedFont extends Font {

    private boolean startZooming = true;
    private boolean startMoving = true;
    protected boolean playingAnimation = false;

    private Vector2 v = new Vector2();
    private Vector2 pos = new Vector2();

    public AnimatedFont(String fontFile, String imageFile) {
        super(fontFile, imageFile);
    }

    public boolean zoomAnimation(float startScale, float finishScale, float speed) {
        if (startZooming) {
            setSize(startScale * getScaleY());
            startZooming = false;
            playingAnimation = true;
            return false;
        }
        if (getCapHeight() < finishScale) {
            System.out.println(getCapHeight());
            setSize((getCapHeight() + ((finishScale - startScale) / speed / 60))* getScaleY());
            System.out.println(getCapHeight());
            return false;
        }
        playingAnimation = false;
        return true;
    }

    public boolean emergenceAnimation(float speed) {
        if (getColor().a < 1) {
            getColor().a += 1 / speed / 60;
            return false;
        }
        getColor().a = 1;
        return true;
    }

    public boolean fadingAnimation(float speed) {
        if (getColor().a > 0) {
            getColor().a -= 1 / speed / 60;
            return false;
        } else {
            getColor().a = 0;
            return true;
        }
    }

    public boolean movedAnimation(SpriteBatch batch, Vector2 start, Vector2 finish, float speed, CharSequence str, int align) {
        try {
            if (startMoving) {
                pos.set(start);
                v.set(finish.cpy().sub(start).nor().scl(1 / speed / 60));
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
            super.draw(batch, str, pos.x, pos.y, align);
        }
    }

    public void resetAnimation() {
        startZooming = true;
        startMoving = true;
        playingAnimation = false;
        getColor().a = 1;
        v.setZero();
    }
}
