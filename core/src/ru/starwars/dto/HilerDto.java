package ru.starwars.dto;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.starwars.utils.TextureSpliter;

public class HilerDto extends RandEventsDto {

    private final int HP = 30;
    private final int DAMAGE = -1;
    private final float SPEED = 0.005f;
    private final float SIZE = 0.1f;
    private final float ANIMATION_SPEED = -1f;



    public HilerDto(TextureAtlas atlas) {
        TextureRegion [] texture = TextureSpliter.split(atlas.findRegion("hiler"),1,1,1);
        setRegion(texture);
        setDamage(DAMAGE);
        setHp(HP);
        setSpeed(SPEED);
        setSize(SIZE);
        setAnimationSpeed(ANIMATION_SPEED);
    }
}
