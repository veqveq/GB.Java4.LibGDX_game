package ru.starwars.pool;

import com.badlogic.gdx.audio.Sound;

import ru.starwars.base.SpritesPool;
import ru.starwars.math.Rect;
import ru.starwars.sprite.EnemyShip;
import ru.starwars.sprite.PlayerShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private ExplodePool explodePool;
    private Sound soundExplode;
    private boolean sounds;
    private PlayerShip playerShip;
    private Rect worldBounds;

    public EnemyShipPool(BulletPool bulletPool, ExplodePool explodePool, Sound soundExplode, boolean sound, PlayerShip playerShip, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.sounds = sound;
        this.playerShip = playerShip;
        this.worldBounds = worldBounds;
        this.explodePool = explodePool;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool,explodePool, soundExplode,sounds,playerShip, worldBounds);
    }
}
