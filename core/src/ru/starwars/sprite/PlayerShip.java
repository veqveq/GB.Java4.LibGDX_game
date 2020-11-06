package ru.starwars.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.BaseShip;
import ru.starwars.math.Rect;
import ru.starwars.pool.ExplodePool;
import ru.starwars.tools.TextureSpliter;
import ru.starwars.pool.BulletPool;

public class PlayerShip extends BaseShip {

    private int leftPoint;
    private int rightPoint;

    public PlayerShip(TextureAtlas atlas, BulletPool bulletPool, ExplodePool explodePool, Sound soundExplode, boolean sounds) {
        super(atlas.findRegion("X-Wing"), 4, 1, 4, bulletPool, soundExplode, sounds);
        this.bulletRegion = TextureSpliter.split(atlas.findRegion("fire"), 2, 1, 2)[0];
        this.soundExplode = soundExplode;
        this.explodePool = explodePool;
        soundShot = Gdx.audio.newSound(Gdx.files.internal("sounds\\XWing-fire.wav"));
        HORIZONTAL_ACCELERATION = 0.0005f;
        RELOAD_TIME = 0.11f;
        BULLET_SPEED = 1.2f;
        a0.set(HORIZONTAL_ACCELERATION, 0);
        bulletV.set(0, BULLET_SPEED);
        reload = RELOAD_TIME;
        this.damage = 1;
        this.hp = 1;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.2f);
        setBottom(worldBounds.getBottom() + MERGED);
    }

    @Override
    protected void shot() {
        if (sound) soundShot.play();
        Bullet bulletLeft = bulletPool.obtain();
        Bullet bulletRight = bulletPool.obtain();
        bulletPos.set(getLeft() + MERGED, getTop() - MERGED);
        bulletLeft.set(this, bulletRegion, bulletPos, bulletV, worldBounds, 3, 0.1f);
        bulletPos.set(getRight() - MERGED, getTop() - MERGED);
        bulletRight.set(this, bulletRegion, bulletPos, bulletV, worldBounds, 3, 0.1f);
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
            case Input.Keys.SPACE:
                autoShot = true;
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
            case Input.Keys.SPACE:
                autoShot = false;
                break;
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < pos.x) {
            leftPoint = pointer;
            left = true;
            if (!right) {
                moveLeft();
            }
        }
        if (touch.x >= pos.x) {
            rightPoint = pointer;
            right = true;
            if (!left) {
                moveRight();
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPoint) {
            left = false;
            leftPoint = -1;
            if (right) {
                moveRight();
            } else {
                a.setZero();
                frame = 0;
            }
        }
        if (pointer == rightPoint) {
            right = false;
            rightPoint = -1;
            if (left) {
                moveLeft();
            } else {
                a.setZero();
                frame = 0;
            }
        }
        return false;
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(
                bullet.getRight() < getLeft()
                        || bullet.getLeft() > getRight()
                        || bullet.getBottom() > pos.y
                        || bullet.getTop() < getBottom()
        );
    }

    @Override
    public void destroy() {
        super.destroy();
        setBottom(worldBounds.getTop());
    }
}
