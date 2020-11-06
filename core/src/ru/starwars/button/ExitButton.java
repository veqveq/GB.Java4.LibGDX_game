package ru.starwars.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.BaseButton;
import ru.starwars.math.Rect;

public class ExitButton extends BaseButton {

    public ExitButton(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    public ExitButton(TextureRegion texture) {
        super(texture);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight() * 0.1f);
        pos.set(0.15f, 0.3f);
    }

    @Override
    protected void action() {
        scale += 2f;
        if (scale > 50f) Gdx.app.exit();
    }
}
