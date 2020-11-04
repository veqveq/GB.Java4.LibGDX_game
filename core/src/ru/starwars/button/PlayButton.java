package ru.starwars.button;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.starwars.base.BaseButton;
import ru.starwars.math.Rect;
import ru.starwars.screen.GameScreen;

public class PlayButton extends BaseButton {

    private Game game;
    private boolean sounds;

    public PlayButton(TextureAtlas atlas, Sound clickSound, Game game) {
        super(atlas.findRegion("btPlay"), clickSound);
        this.game = game;
        this.sounds = true;

    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight() * 0.1f);
        pos.set(-0.15f, 0.3f);
    }

    public void setSounds(boolean sounds) {
        this.sounds = sounds;
    }

    @Override
    protected void action() {
        game.setScreen(new GameScreen(sounds));
    }
}
