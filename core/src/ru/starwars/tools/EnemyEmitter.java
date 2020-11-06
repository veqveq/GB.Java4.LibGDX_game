package ru.starwars.tools;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.starwars.dto.MediumEnemySettingsDto;
import ru.starwars.dto.SmallEnemySettingsDto;
import ru.starwars.math.Rect;
import ru.starwars.math.Rnd;
import ru.starwars.pool.EnemyShipPool;
import ru.starwars.sprite.EnemyShip;
import ru.starwars.sprite.PlayerShip;

public class EnemyEmitter {

    private final float GENERATE_INTERVAL = 4f;

    private Rect worldBounds;
    private EnemyShipPool enemyShipPool;
    private float generateTimer;

    private SmallEnemySettingsDto smallEnemySettingsDto;
    private MediumEnemySettingsDto mediumEnemySettingsDto;


    public EnemyEmitter(Rect worldBounds, EnemyShipPool enemyShipPool, TextureAtlas atlas, Sound bulletSound) {
        this.worldBounds = worldBounds;
        this.enemyShipPool = enemyShipPool;
        this.smallEnemySettingsDto = new SmallEnemySettingsDto(atlas,bulletSound);
        this.mediumEnemySettingsDto = new MediumEnemySettingsDto(atlas,bulletSound);
    }

    public void generate(float delta){
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL){
            generateTimer = 0;
            EnemyShip enemyShip = enemyShipPool.obtain();
            float type = (float) Math.random();
            if (type <= 0.95f){
                enemyShip.set(smallEnemySettingsDto);
            }else{
                enemyShip.set(mediumEnemySettingsDto);
            }
            enemyShip.pos.x = Rnd.nextFloat(worldBounds.getLeft()+enemyShip.getHalfWidth(),worldBounds.getRight()-enemyShip.getHalfWidth());
            enemyShip.setBottom(worldBounds.getTop());
        }
    }
}
