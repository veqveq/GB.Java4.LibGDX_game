package ru.starwars.dto;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.starwars.utils.TextureSpliter;

public class MediumEnemySettingsDto extends EnemySettingsDto{

    private final float HEIGHT = 0.4f;
    private final float HORIZONTAL_ACCELERATION = 0.0002f;
    private final float VERTICAL_SPEED = 0;
    private final float BULLET_HEIGHT = 0.2f;
    private final float BULLET_SPEED = -1f;
    private final float RELOAD_TIME = 0.5f;
    private final int SCORE = 20;
    private final int DAMAGE = 10;
    private final int HP = 100;
    private final boolean TURN_AROUND = false;


    public MediumEnemySettingsDto(TextureAtlas atlas, Sound bulletSound) {
        TextureRegion enemyRegion = atlas.findRegion("cruiser");
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
