package ru.starwars.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.math.Rect;
import ru.starwars.pool.BulletPool;
import ru.starwars.pool.ExplodePool;
import ru.starwars.sprite.Explode;

public abstract class BaseShip extends Sprite {

    private final float DAMAGE_ANIMATE_INTERVAL = 0.1f;
    private final float RESISTANCE = 0.0001f;
    protected final float MERGED = 0.02f;
    protected float HORIZONTAL_ACCELERATION;
    protected float VERTICAL_SPEED;
    protected float RELOAD_TIME;
    protected float BULLET_SPEED;

    protected final Vector2 v;
    protected final Vector2 a;
    protected final Vector2 r;
    protected final Vector2 a0;
    protected final Vector2 r0;
    protected Rect worldBounds;

    protected BulletPool bulletPool;
    protected ExplodePool explodePool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected Sound soundShot;
    protected Sound soundExplode;
    protected boolean sound;
    protected int hp;
    protected int damage;
    protected float bulletHeight;
    private float damageAnimateTimer;

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

    public BaseShip(TextureRegion region, int cols, int rows, int frames, BulletPool bulletPool, Sound soundExplode, boolean sounds) {
        super(region, cols, rows, frames);
        this.v = new Vector2();
        this.a = new Vector2();
        this.r = new Vector2();
        bulletPos = new Vector2();
        bulletV = new Vector2();
        a0 = new Vector2();
        r0 = new Vector2(RESISTANCE, 0);
        this.bulletPool = bulletPool;
        this.sound = sounds;
    }

    protected void moveLeft() {
        a.set(a0.cpy().rotate(180));
        r.set(r0);
    }

    protected void moveRight() {
        a.set(a0);
        r.set(r0.cpy().rotate(180));
    }

    protected void stop() {
        v.set(0, -VERTICAL_SPEED);
        a.setZero();
        r.setZero();
    }

    @Override
    public void update(float delta) {
        v.add(a).add(r);
        pos.add(v);
        checkPos();
        autoShooting(delta);
        damageAnimate(delta);
    }

    public Vector2 getV() {
        return v;
    }

    public int getHp() {
        return hp;
    }

    private void damageAnimate(float delta) {
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            if (a.x < 0) {
                frame = 2;
            } else if (a.x > 0) {
                frame = 3;
            } else {
                frame = 0;
            }
        }
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

    @Override
    public void destroy() {
        boom();
        super.destroy();
    }

    private void boom() {
        soundExplode.play();
        Explode explode = explodePool.obtain();
        explode.set(getHeight(), pos);
    }

    protected abstract void shot();

    public void damage(int damage) {
        frame = 1;
        damageAnimateTimer = 0;
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
    }
}
