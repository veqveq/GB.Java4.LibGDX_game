package ru.starwars.dto;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.starwars.utils.TextureSpliter;

public class BolidDto extends RandEventsDto{

    private final int HP = 30;
    private final int DAMAGE = 20;
    private final float SPEED = 0.005f;
    private final float SIZE = 0.05f;
    private final float ANIMATION_SPEED = 0.1f;

    public BolidDto(TextureAtlas atlas) {
        TextureRegion[] texture = TextureSpliter.split(atlas.findRegion("bolid"),5,4,20);
        setRegion(texture);
        setDamage(DAMAGE);
        setHp(HP);
        setSpeed(SPEED);
        setSize(SIZE);
        setAnimationSpeed(ANIMATION_SPEED);
    }
}
