package ru.starwars.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.math.Rect;
import ru.starwars.utils.TextureSpliter;

public abstract class Sprite extends Rect {

    protected float angle;
    protected float scale = 1;
    protected TextureRegion[] regions;
    protected int frame;
    protected boolean destroyed;

    public Sprite(){}

    public Sprite(TextureRegion region) {
        this.regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite(TextureRegion region, int cols, int rows, int frames) {
        this.regions = TextureSpliter.split(region, cols, rows, frames);
    }

    public Sprite(TextureRegion[] regions) {
        this.regions = regions;
    }

    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

    public float getScale() {
        return scale;
    }

    public float getAngle() {
        return angle;
    }

    public void update(float delta) {

    }

    public void resize(Rect worldBounds) {

    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean mouseMoved(Vector2 cursorPosition) {
        return false;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy(){
        destroyed =true;
    }

    public void flushDestroy(){
        destroyed = false;
    }
}
