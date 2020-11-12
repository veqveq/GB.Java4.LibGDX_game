package ru.starwars.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.starwars.dto.BolidDto;
import ru.starwars.dto.HilerDto;
import ru.starwars.math.Rect;
import ru.starwars.math.Rnd;
import ru.starwars.pool.EventsPool;
import ru.starwars.sprite.RandomEvent;

public class EventEmitter {

    private float GENERATE_INTERVAL = (float) Math.random() * 5f;
    private float HEALTH_INTERVAL = 10f;

    private Rect worldBounds;
    private EventsPool eventsPool;
    private float bolidTimer;
    private float healthTimer;

    private HilerDto hilerDto;
    private BolidDto bolidDto;

    public EventEmitter(Rect worldBounds, EventsPool eventsPool, TextureAtlas atlas) {
        this.worldBounds = worldBounds;
        this.eventsPool = eventsPool;
        this.hilerDto = new HilerDto(atlas);
        this.bolidDto = new BolidDto(atlas);
    }

    public void generate(float delta) {
        spawnBolid(delta);
        spawnHiler(delta);
    }

    private void spawnBolid(float delta) {
        bolidTimer += delta;
        if (bolidTimer >= GENERATE_INTERVAL) {
            bolidTimer = 0;
            GENERATE_INTERVAL = (float) Math.random() * 3f;
            float type = (float) Math.random();
            if (type <= 0.5f) {
                RandomEvent event = eventsPool.obtain();
                event.set(bolidDto);
                event.pos.x = Rnd.nextFloat(worldBounds.getLeft() + event.getHalfWidth(), worldBounds.getRight() - event.getHalfWidth());
                event.setBottom(worldBounds.getTop());
            }
        }
    }

    private void spawnHiler(float delta) {
        healthTimer += delta;
        if (healthTimer >= HEALTH_INTERVAL) {
            healthTimer = 0;
            float type = (float) Math.random();
            if (type <= 0.5f) {
                RandomEvent event = eventsPool.obtain();
                event.set(hilerDto);
                event.pos.x = Rnd.nextFloat(worldBounds.getLeft() + event.getHalfWidth(), worldBounds.getRight() - event.getHalfWidth());
                event.setBottom(worldBounds.getTop());
            }
        }
    }
}
