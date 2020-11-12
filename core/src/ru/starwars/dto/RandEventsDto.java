package ru.starwars.dto;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RandEventsDto {
    private TextureRegion [] region;
    private float speed;
    private float size;
    private float animationSpeed;

    public float getAnimationSpeed() {
        return animationSpeed;
    }

    public void setAnimationSpeed(float animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    private int hp;
    private int damage;

    public TextureRegion[] getRegion() {
        return region;
    }

    public void setRegion(TextureRegion[] region) {
        this.region = region;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
