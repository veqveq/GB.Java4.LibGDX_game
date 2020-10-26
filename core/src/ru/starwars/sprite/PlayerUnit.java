package ru.starwars.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.Sprite;
import ru.starwars.math.Rect;

public class PlayerUnit extends Sprite {

    private final float MERGED = 0.15f;
    private final float SPEED = 0.01f;


    private final Vector2 v;
    private Vector2 touch;
    private final Vector2 tmp;
    private final Vector2 resistance;
    private final Vector2 acceleration;

    private Rect worldBounds;

    public PlayerUnit(TextureAtlas atlas) {
        super(new TextureRegion[]{
                new TextureRegion(atlas.findRegion("X-Wing"), 0, 0, atlas.findRegion("X-Wing").getRegionWidth() / 2, atlas.findRegion("X-Wing").getRegionHeight()),
                new TextureRegion(atlas.findRegion("X-Wing"), atlas.findRegion("X-Wing").getRegionWidth() / 2, 0, atlas.findRegion("X-Wing").getRegionWidth() / 2, atlas.findRegion("X-Wing").getRegionHeight()),
                new TextureRegion(atlas.findRegion("X-Wing-turn"), 0, 0, atlas.findRegion("X-Wing-turn").getRegionWidth() / 2, atlas.findRegion("X-Wing-turn").getRegionHeight()),
                new TextureRegion(atlas.findRegion("X-Wing-turn"), atlas.findRegion("X-Wing-turn").getRegionWidth() / 2, 0, atlas.findRegion("X-Wing-turn").getRegionWidth() / 2, atlas.findRegion("X-Wing-turn").getRegionHeight())
        });

        v = new Vector2();
        resistance = new Vector2();
        acceleration = new Vector2();

        touch = new Vector2();
        tmp = new Vector2();
    }

    private void acceleratedMove() {
        v.add(acceleration).add(resistance);
        if (tmp.x * v.x < 0) {
            resistance.setZero();
            v.setZero();
        }
        if (checkPosition()) {
            pos.add(v);
        } else {
            resistance.setZero();
        }
    }

    public void forwardMove() {
        tmp.set(touch);
        if (tmp.sub(pos).len() <= SPEED) {
            pos.set(touch);
            frame = 0;
        } else {
            if (checkPosition()) pos.add(v);
        }
    }

    @Override
    public void update(float delta) {
        if (acceleration.len() != 0 || resistance.len() != 0) {
            acceleratedMove();
        } else {
            forwardMove();
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        pos.set(0f, worldBounds.getBottom() + MERGED);
        setHeightProportion(0.2f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        resistance.setZero();
        tmp.setZero();
        this.touch = new Vector2(touch.x, pos.y);
        if (touch.x < pos.x) {
            turnLeft();
        } else {
            turnRight();
        }
        return false;
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                acceleration.set(-0.0005f, 0);
                resistance.set(0.0001f, 0);
                frame = 2;
                break;
            case Input.Keys.RIGHT:
                acceleration.set(0.0005f, 0);
                resistance.set(-0.0001f, 0);
                frame = 3;
                break;
        }
        tmp.setZero();
        v.setZero();
    }

    public void keyUp() {
        acceleration.setZero();
        frame = 0;
        acceleration.setZero();
        tmp.set(v);
    }

    private void turnLeft() {
        if (touch.x >= worldBounds.getLeft() + getHalfWidth()) frame = 2;
        v.set(-SPEED, 0);
    }

    private void turnRight() {
        if (touch.x <= worldBounds.getRight() - getHalfWidth()) frame = 3;
        v.set(SPEED, 0);
    }

    private boolean checkPosition() {
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            v.setZero();
            frame = 0;
            return false;
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            v.setZero();
            frame = 0;
            return false;
        }
        return true;
    }
}
