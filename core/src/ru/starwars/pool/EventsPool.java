package ru.starwars.pool;

import com.badlogic.gdx.audio.Sound;

import ru.starwars.base.SpritesPool;
import ru.starwars.sprite.RandomEvent;

public class EventsPool extends SpritesPool<RandomEvent> {

    private ExplodePool explodePool;
    private Sound soundExplode;

    public EventsPool(ExplodePool explodePool, Sound soundExplode) {
        this.explodePool = explodePool;
        this.soundExplode = soundExplode;
    }

    @Override
    protected RandomEvent newObject() {
        return new RandomEvent(explodePool, soundExplode);
    }
}
