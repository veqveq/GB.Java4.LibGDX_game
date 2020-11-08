package ru.starwars.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.starwars.base.BaseShip;
import ru.starwars.dto.EnemySettingsDto;
import ru.starwars.math.Rect;
import ru.starwars.pool.ExplodePool;
import ru.starwars.tools.TextureSpliter;
import ru.starwars.pool.BulletPool;

public class EnemyShip extends BaseShip {

    PlayerShip playerShip;
    BulletPool playerBulletPool;
    boolean turn;
    boolean finishGame;

    public EnemyShip(BulletPool bulletPool, ExplodePool explodePool, Sound soundExplode, boolean sound, PlayerShip playerShip, Rect worldBounds) {
        super();
        this.bulletPool = bulletPool;
        this.explodePool = explodePool;
        this.sound = sound;
        this.playerShip = playerShip;
        this.worldBounds = worldBounds;
        this.soundExplode = soundExplode;
    }

    public void set(EnemySettingsDto settings) {
        this.regions = settings.getRegions();
        setHeightProportion(settings.getHeight());
        this.bulletRegion = settings.getBulletRegion();
        this.bulletHeight = settings.getBulletSize();
        this.soundShot = settings.getBulletSound();
        this.BULLET_SPEED = settings.getBulletSpeed();
        this.HORIZONTAL_ACCELERATION = settings.getHorizontalAcceleration();
        this.VERTICAL_SPEED = settings.getVerticalSpeed();
        this.RELOAD_TIME = settings.getReloadTime();
        this.hp = settings.getHp();
        this.damage = settings.getDamage();
        this.turn = settings.isTurned();
        this.playerBulletPool = playerShip.getBulletPool();
        v.set(0, -VERTICAL_SPEED);
        a0.set(HORIZONTAL_ACCELERATION, 0);
        bulletV.set(0, BULLET_SPEED);
        reload = RELOAD_TIME;
        this.soundExplode = settings.getSoundExplode();
    }

    @Override
    protected void autoShooting(float delta) {
        reload -= delta;
        if ((getLeft() >= playerShip.getLeft() && getLeft() <= playerShip.getRight() ||
                getRight() >= playerShip.getLeft() && getRight() <= playerShip.getRight()) &&
                scale >= 0.95f &&
                getTop() <= worldBounds.getTop()) {
            if (reload <= 0) {
                shot();
                reload = RELOAD_TIME;
            }
        }
    }

    @Override
    protected void shot() {
        if (sound) soundShot.play();
        Bullet bullet = bulletPool.obtain();
        bulletPos.set(pos.x, getBottom() - MERGED);
        bullet.set(this, bulletRegion, bulletPos, bulletV, worldBounds, damage, bulletHeight);
    }

    @Override
    protected void checkPos() {
        if (!finishGame){
            rebound();
            turnAround();
            super.checkPos();
            shortenDistance();
            dodgeTheBullets();
            startBattle();
        }else{
            if (worldBounds.isOutside(this)) {
                destroyed = true;
            }
        }
    }

    private void rebound() {
        if (getBottom() > 0 && getBottom() < 0.1f && v.y < 0) {
            if (turn) {
                if ((float) Math.random() < 0.1f) {
                    v.y = VERTICAL_SPEED;
                } else {
                    v.y = Math.max(-VERTICAL_SPEED * 2f, v.y - 0.0006f);
                }
            } else {
                v.y = VERTICAL_SPEED;
            }
        }
    }

    private void startBattle() {
        if (getTop() > worldBounds.getTop()) {
            v.set(0, Math.min(-VERTICAL_SPEED*2,-0.01f));
        }
    }

    private void shortenDistance() {
        if (getTop() < -0.1 && angle == 0) {
            if (playerShip.getRight() < getRight()) {
                moveRight();
            } else if (playerShip.getLeft() >= getLeft()) {
                moveLeft();
            }
        } else {
            if (playerShip.pos.x > pos.x) {
                moveRight();
            } else {
                moveLeft();
            }
        }
    }

    private void dodgeTheBullets() {
        if (playerBulletPool.getActiveObjects().size() != 0) {
            for (int i = playerBulletPool.getActiveObjects().size() - 1; i > 0; i -= 2) {
                if (playerBulletPool.getActiveObjects().get(i).getTop() <= getBottom()) {
                    Bullet leftBullet = playerBulletPool.getActiveObjects().get(i - 1);
                    Bullet rightBullet = playerBulletPool.getActiveObjects().get(i);
                    if (pos.x >= leftBullet.getLeft() - 2 * MERGED && pos.x <= leftBullet.getRight() + 2 * MERGED)
                        moveLeft();
                    if (pos.x >= rightBullet.getLeft() - 2 * MERGED && pos.x <= rightBullet.getRight() + 2 * MERGED)
                        moveRight();
                }
            }
        }
    }

    private void turnAround() {
        if (getTop() < -0.1f) {
            setAngle(Math.min(180, angle + 3.6f));
            setScale(Math.max(0.7f, scale - 0.005f));
        }
        if (angle == 180) {
            if (scale < 1) {
                v.y = 2f * VERTICAL_SPEED;
            } else {
                v.y = VERTICAL_SPEED;
            }
        }
        if (getBottom() > 0.1f && angle >= 180) {
            setAngle(Math.min(360, angle + 4));
            setScale(Math.min(1f, scale + 0.01f));
        }
        if (angle == 360 || getTop() + MERGED >= worldBounds.getTop()) {
            angle = 0;
            scale = 1f;
            v.y = -VERTICAL_SPEED;
        }
    }

    public boolean isBulletCollision(Rect bullet){
        return !(
                bullet.getRight() < getLeft()+MERGED
                        || bullet.getLeft() > getRight()-MERGED
                        || bullet.getBottom() > getTop()
                        ||bullet.getTop() < pos.y
        );
    }

    public void goAway(){
        finishGame = true;
        r.setZero();
        r0.setZero();
    }
}
