package ru.starwars.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.Sprite;
import ru.starwars.math.Rect;
import ru.starwars.math.TextureSpliter;
import ru.starwars.pool.BulletPool;

public class PlayerUnit extends Sprite {

    private final float MERGED = 0.02f;
    private final float ACCELERATION = 0.0005f;
    private final float RESISTANCE = 0.0001f;
    private final float BULLET_SPEED = 1.2f;

    private final Vector2 v;
    private final Vector2 a;
    private final Vector2 r;
    private final Vector2 a0;
    private final Vector2 r0;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletPos;
    private Vector2 bulletV;
    private Sound soundShot;
    private boolean sound;

    private boolean left;
    private boolean right;

    private int leftPoint;
    private int rightPoint;

    private Rect worldBounds;

    public PlayerUnit(TextureAtlas atlas, BulletPool bulletPool, boolean sound) {
        super(TextureSpliter.split(atlas.findRegion("X-Wing"), 4, 1, 4));
        this.bulletRegion = TextureSpliter.split(atlas.findRegion("fire"), 2, 1, 2)[0];

        soundShot = Gdx.audio.newSound(Gdx.files.internal("sounds\\XWing-fire.wav"));
        this.bulletPool = bulletPool;
        this.sound = sound;

        v = new Vector2();
        a = new Vector2();
        r = new Vector2();
        bulletPos = new Vector2();
        bulletV = new Vector2(0, BULLET_SPEED);
        a0 = new Vector2(ACCELERATION, 0);
        r0 = new Vector2(RESISTANCE, 0);
    }

    public void autoShooting() {
        if (bulletPool.getActiveObjects().size() != 0) {
            Bullet lastBullet = bulletPool.getActiveObjects().get(bulletPool.getActiveObjects().size() - 1);
            if (lastBullet.getTop() >= 0) {
                shot();
            }
        } else {
            shot();
        }
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

    private void shot() {
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
//            case Input.Keys.SPACE:
//                shot();
//                break;
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
        if (pointer == leftPoint) {
            left = false;
            if (right) {
                moveRight();
            } else {
                a.setZero();
                frame = 0;
            }
        }
        if (pointer == rightPoint) {
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
        autoShooting();
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
        }
        if (v.cpy().add(r).len() < r.len() && !right && !left) {
            stop();
        }
    }
}
