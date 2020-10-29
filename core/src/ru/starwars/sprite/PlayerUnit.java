package ru.starwars.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.Sprite;
import ru.starwars.math.Rect;
import ru.starwars.math.TextureSpliter;

public class PlayerUnit extends Sprite {

    private final float MERGED = 0.02f;
    private final float ACCELERATION = 0.0005f;
    private final float RESISTANCE = 0.0002f;

    private final Vector2 v;
    private final Vector2 a;
    private final Vector2 r;
    private final Vector2 a0;
    private final Vector2 r0;

    private boolean left;
    private boolean right;

    private int leftPoint;
    private int rightPoint;

    private Rect worldBounds;

    public PlayerUnit(TextureAtlas atlas) {
        super(TextureSpliter.split(atlas.findRegion("X-Wing"), 4, 1, 4));

        v = new Vector2();
        a = new Vector2();
        r = new Vector2();
        a0 = new Vector2(ACCELERATION, 0);
        r0 = new Vector2(RESISTANCE, 0);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.2f);
        setBottom(worldBounds.getBottom() + MERGED);
    }

    private void moveLeft() {
        a.set(a0.cpy().rotate(180));
        r.set(r0);
        frame = 2;
    }

    private void moveRight() {
        a.set(a0);
        r.set(r0.cpy().rotate(180));
        frame = 3;
    }

    private void stop() {
        v.setZero();
        a.setZero();
        r.setZero();
        frame = 0;
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                left = true;
                if (!right) {
                    moveLeft();
                }
                break;
            case Input.Keys.RIGHT:
                right = true;
                if (!left) {
                    moveRight();
                }
                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                left = false;
                if (right) {
                    moveRight();
                } else {
                    a.setZero();
                    frame = 0;
                }
                break;
            case Input.Keys.RIGHT:
                right = false;
                if (left) {
                    moveLeft();
                } else {
                    a.setZero();
                    frame = 0;
                }
                break;
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < pos.x) {
            leftPoint = pointer;
            left = true;
            moveLeft();
        } else {
            rightPoint = pointer;
            right = true;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPoint){
            left = false;
            if (right) {
                moveRight();
            } else {
                a.setZero();
                frame = 0;
            }
        }
        if (pointer == rightPoint){
            right = false;
            if (left) {
                moveLeft();
            } else {
                a.setZero();
                frame = 0;
            }
        }
        a.setZero();
        frame = 0;
        return false;
    }

    @Override
    public void update(float delta) {
        v.add(a).add(r);
        pos.add(v);
        checkPos();
    }

    private void checkPos() {
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
            if (right) {
                moveRight();
            }
        }
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
            if (left) {
                moveLeft();
            }
            ;
        }
        if (v.cpy().add(r).len() < r.len() && !right && !left) {
            stop();
        }
    }
}
