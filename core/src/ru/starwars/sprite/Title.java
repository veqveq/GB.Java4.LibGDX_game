package ru.starwars.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.starwars.base.Sprite;

public class Title extends Sprite {

    public Title(TextureRegion region) {
        super(region);
    }

    public void resize(float height, float x, float y) {
        setHeightProportion(height);
        pos.set(x, y);
    }
}
