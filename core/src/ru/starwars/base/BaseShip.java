package ru.starwars.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.math.Rect;
import ru.starwars.pool.BulletPool;

public abstract class BaseShip extends Sprite {

    private final float RESISTANCE = 0.0001f;
    protected final float MERGED = 0.02f;
    protected float HORIZONTAL_ACCELERATION = 0;
    protected float VERTICAL_SPEED = 0;
    protected float RELOAD_TIME = 0;
    protected float BULLET_SPEED = 0;

    protected final Vector2 v;
    protected final Vector2 a;
    protected final Vector2 r;
    protected final Vector2 a0;
    protected final Vector2 r0;
    protected Rect worldBounds;

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected Sound soundShot;
    protected boolean sound;
    protected int hp;
    protected int damage;
    protected float bulletHeight;

    protected boolean left;
    protected boolean right;
    protected boolean autoShot;

    protected float reload;


    public BaseShip() {
        this.v = new Vector2();
        this.a = new Vector2();
        this.r = new Vector2();
        bulletPos = new Vector2();
        a0 = new Vector2();
        r0 = new Vector2(RESISTANCE, 0);
        bulletV = new Vector2();
    }

    public BaseShip(TextureRegion region, int cols, int rows, int frames, BulletPool bulletPool, boolean sound) {
        super(region, cols, rows, frames);
        setConst();
        this.v = new Vector2(0,-VERTICAL_SPEED);
        this.a = new Vector2();
        this.r = new Vector2();
        a0 = new Vector2(HORIZONTAL_ACCELERATION, 0);
        r0 = new Vector2(RESISTANCE, 0);
        bulletV = new Vector2(0, BULLET_SPEED);
        bulletPos = new Vector2();
        this.reload = RELOAD_TIME;
        this.bulletPool = bulletPool;
        this.sound = sound;
    }

    protected void moveLeft() {
        a.set(a0.cpy().rotate(180));
        r.set(r0);
        frame = 2;
    }

    protected void moveRight() {
        a.set(a0);
        r.set(r0.cpy().rotate(180));
        frame = 3;
    }

    protected void stop() {
        v.set(0,-VERTICAL_SPEED);
        a.setZero();
        r.setZero();
        frame = 0;
    }

    @Override
    public void update(float delta) {
        v.add(a).add(r);
        pos.add(v);
        checkPos();
        autoShooting(delta);
    }

    protected void checkPos() {
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
        if (v.cpy().add(r).len() <= r.len() && !right && !left) {
            stop();
        }
        if (v.cpy().dot(r) > 0) r.rotate(180);
    }

    protected void autoShooting(float delta) {
        if (reload - delta < 0 && autoShot) {
            shot();
            reload = RELOAD_TIME;
        } else {
            reload -= delta;
        }
    }

    public BulletPool getBulletPool() {
        return bulletPool;
    }

    protected abstract void shot();
    protected abstract void setConst();
}
