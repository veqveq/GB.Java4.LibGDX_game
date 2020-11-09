package ru.starwars.dto;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.starwars.utils.TextureSpliter;

public class SmallEnemySettingsDto extends EnemySettingsDto{
    private final float HEIGHT = 0.1f;
    private final float HORIZONTAL_ACCELERATION = 0.0003f;
    private final float VERTICAL_SPEED = 0.003f;
    private final float BULLET_HEIGHT = 0.1f;
    private final float BULLET_SPEED = -1f;
    private final float RELOAD_TIME = 0.25f;
    private final int DAMAGE = 3;
    private final int HP = 30;
    private final int SCORE = 5;
    private final boolean TURN_AROUND = true;

    public SmallEnemySettingsDto(TextureAtlas atlas, Sound bulletSound) {
        TextureRegion enemyRegion = atlas.findRegion("tie-striker");
        TextureRegion bulletRegion = atlas.findRegion("fire");
        setRegions(TextureSpliter.split(enemyRegion,4,1,4));
        setBulletRegion(TextureSpliter.split(bulletRegion,2,1,2)[1]);
        setHeight(HEIGHT);
        setHorizontalAcceleration(HORIZONTAL_ACCELERATION);
        setVerticalSpeed(VERTICAL_SPEED);
        setBulletSize(BULLET_HEIGHT);
        setBulletSpeed(BULLET_SPEED);
        setBulletSound(bulletSound);
        setReloadTime(RELOAD_TIME);
        setDamage(DAMAGE);
        setHp(HP);
        setTurned(TURN_AROUND);
        setScore(SCORE);
    }
}
