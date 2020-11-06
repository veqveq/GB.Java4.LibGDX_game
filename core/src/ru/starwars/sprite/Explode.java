package ru.starwars.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.Sprite;

public class Explode extends Sprite {

    private float ANIMATE_INTERVAL = 0.04f;

    private float animateTimer;

    public Explode(TextureRegion region, int cols, int rows, int frames) {
        super(region, cols, rows, frames);
    }

    public void set(float height, Vector2 pos){
        setHeightProportion(height*2);
        this.pos.set(pos);
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL){
            animateTimer = 0;
            if (++frame == regions.length){
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
