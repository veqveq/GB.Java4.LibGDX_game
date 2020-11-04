package ru.starwars.pool;

import ru.starwars.base.SpritesPool;
import ru.starwars.sprite.EnemyShip;
import ru.starwars.sprite.PlayerShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private boolean sound;
    private PlayerShip playerShip;

    public EnemyShipPool(BulletPool bulletPool, boolean sound, PlayerShip playerShip) {
        this.bulletPool = bulletPool;
        this.sound = sound;
        this.playerShip = playerShip;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool,sound,playerShip);
    }
}
