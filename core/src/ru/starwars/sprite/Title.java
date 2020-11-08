package ru.starwars.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.starwars.base.AnimatedSprite;

public class Title extends AnimatedSprite {

    public Title(TextureRegion region) {
        super(region);
    }

    public void resize(float height, float x, float y) {
        setHeightProportion(height);
        pos.set(x, y);
    }
}
