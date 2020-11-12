package ru.starwars.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.Sprite;
import ru.starwars.dto.RandEventsDto;
import ru.starwars.math.Rect;
import ru.starwars.pool.ExplodePool;

public class RandomEvent extends Sprite {

    private float animationTimer;

    protected final float MERGED = 0.01f;

    private Vector2 v;
    private int hp;
    private int damage;
    private float animationSpeed;
    private ExplodePool explodePool;
    private Sound soundExplode;

    public RandomEvent(ExplodePool explodePool, Sound soundExplode) {
        v = new Vector2();
        this.explodePool = explodePool;
        this.soundExplode = soundExplode;
    }

    public void set(RandEventsDto settings){
        this.regions = settings.getRegion();
        setHeightProportion(settings.getSize());
        this.v.set(0,-settings.getSpeed());
        this.damage = settings.getDamage();
        this.hp = settings.getHp();
        this.animationSpeed =settings.getAnimationSpeed();
    }

    @Override
    public void update(float delta) {
        if (animationSpeed > 0) {
            animationTimer += delta;
            if (animationTimer >= animationSpeed){
                animationTimer = 0;
                if (frame == regions.length-1){
                    frame = 0;
                }else{
                    frame++;
                }
            }
        }
        pos.add(v);
    }

    @Override
    public void destroy() {
        boom();
        super.destroy();
    }

    public void delete(){
        super.destroy();
    }

    private void boom() {
        soundExplode.play();
        Explode explode = explodePool.obtain();
        explode.set(getHeight(), pos);
    }

    public int getDamage() {
        return damage;
    }

    public boolean isBulletCollision(Rect bullet){
        return !(
                bullet.getRight() < getLeft()+MERGED
                        || bullet.getLeft() > getRight()-MERGED
                        || bullet.getBottom() > getTop()
                        ||bullet.getTop() < pos.y
        );
    }

    public void damage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
    }

}
