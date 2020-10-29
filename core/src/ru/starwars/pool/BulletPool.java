package ru.starwars.pool;

import ru.starwars.base.SpritesPool;
import ru.starwars.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}