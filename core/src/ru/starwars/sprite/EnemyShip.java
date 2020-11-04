package ru.starwars.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.starwars.base.BaseShip;
import ru.starwars.dto.EnemySettingsDto;
import ru.starwars.math.Rect;
import ru.starwars.math.TextureSpliter;
import ru.starwars.pool.BulletPool;

public class EnemyShip extends BaseShip {

    PlayerShip playerShip;
    BulletPool playerBulletPool;
    boolean turn;

    public EnemyShip(BulletPool bulletPool, boolean sound, PlayerShip playerShip){
        super();
        this.bulletPool = bulletPool;
        this.sound = sound;
        this.playerShip = playerShip;
    }

    public EnemyShip(TextureAtlas atlas, BulletPool bulletPool, boolean sound, PlayerShip playerShip) {
        super(atlas.findRegion("tie-striker"), 1, 1, 1, bulletPool, sound);
        this.playerShip = playerShip;
        this.playerBulletPool = playerShip.getBulletPool();
        this.bulletRegion = TextureSpliter.split(atlas.findRegion("fire"), 2, 1, 2)[1];
        soundShot = Gdx.audio.newSound(Gdx.files.internal("sounds\\TIE-fire.wav"));
        bulletHeight = 0.07f;
        turn = true;
    }

    public void set(EnemySettingsDto settings){
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


        v.set(0,-VERTICAL_SPEED);
        a0.set(HORIZONTAL_ACCELERATION, 0);
        bulletV.set(0, BULLET_SPEED);
        reload = RELOAD_TIME;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.1f);
        setTop(worldBounds.getTop() - MERGED);
    }

    @Override
    protected void autoShooting(float delta) {
        reload -= delta;
        if ((getLeft() >= playerShip.getLeft() && getLeft() <= playerShip.getRight() ||
                getRight() >= playerShip.getLeft() && getRight() <= playerShip.getRight()) &&
                scale >= 0.95f) {
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
    protected void setConst() {
        HORIZONTAL_ACCELERATION = 0.0003f;
        RELOAD_TIME = 0.25f;
        BULLET_SPEED = -1f;
        VERTICAL_SPEED = 0.003f;
    }

    @Override
    protected void checkPos() {
        shortenDistance();
            if (getBottom() > 0 && getBottom() < 0.1f && v.y < 0) {
                if (turn) {
                    if ((float) Math.random() < 0.1f) {
                        v.y = VERTICAL_SPEED;
                    } else {
                        v.y = Math.max(-VERTICAL_SPEED * 2f, v.y - 0.0006f);
                    }
                }else{
                    v.y = VERTICAL_SPEED;
                }
            }
        turnAround();
        dodgeTheBullets();
        super.checkPos();
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
}
