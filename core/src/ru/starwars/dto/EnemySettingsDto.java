package ru.starwars.dto;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class EnemySettingsDto {

    protected TextureRegion [] regions;
    protected float horizontalAcceleration;
    protected float verticalSpeed;
    protected float reloadTime;
    protected TextureRegion bulletRegion;
    protected float bulletSpeed;
    protected float bulletSize;
    protected Sound bulletSound;
    protected int damage;
    protected float height;
    protected int hp;
    protected boolean turned;
    protected Sound soundExplode = Gdx.audio.newSound(Gdx.files.internal("sounds\\TIE-explode.wav"));

    public Sound getSoundExplode() {
        return soundExplode;
    }

    public boolean isTurned() {
        return turned;
    }

    public void setTurned(boolean turned) {
        this.turned = turned;
    }

    public TextureRegion getBulletRegion() {
        return bulletRegion;
    }

    public void setBulletRegion(TextureRegion bulletRegion) {
        this.bulletRegion = bulletRegion;
    }

    public TextureRegion[] getRegions() {
        return regions;
    }

    public void setRegions(TextureRegion[] regions) {
        this.regions = regions;
    }

    public float getHorizontalAcceleration() {
        return horizontalAcceleration;
    }

    public void setHorizontalAcceleration(float horizontalAcceleration) {
        this.horizontalAcceleration = horizontalAcceleration;
    }

    public float getVerticalSpeed() {
        return verticalSpeed;
    }

    public void setVerticalSpeed(float verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    public float getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(float reloadTime) {
        this.reloadTime = reloadTime;
    }

    public float getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(float bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public float getBulletSize() {
        return bulletSize;
    }

    public void setBulletSize(float bulletSize) {
        this.bulletSize = bulletSize;
    }

    public Sound getBulletSound() {
        return bulletSound;
    }

    public void setBulletSound(Sound bulletSound) {
        this.bulletSound = bulletSound;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
