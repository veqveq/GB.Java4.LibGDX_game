package ru.starwars.button;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.starwars.base.BaseButton;
import ru.starwars.math.Rect;
import ru.starwars.tools.TextureSpliter;

public class MusicButton extends BaseButton {

    private boolean playMusic = true;

    public MusicButton(TextureAtlas atlas) {
        super(TextureSpliter.split(atlas.findRegion("btMusic"), 2, 1, 2));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.1f);
        pos.set(0f, 0.15f);
    }

    @Override
    protected void action() {
        if (frame == 0) {
            frame = 1;
            playMusic = false;
        } else {
            frame = 0;
            playMusic = true;
        }
        doAction = false;
    }

    public boolean isPlayMusic() {
        return playMusic;
    }
}
