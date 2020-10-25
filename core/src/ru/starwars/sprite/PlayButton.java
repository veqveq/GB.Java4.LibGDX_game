package ru.starwars.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.starwars.base.MenuButton;
import ru.starwars.math.Rect;
import ru.starwars.screen.GameScreen;

public class PlayButton extends MenuButton {

    private Game game;

    public PlayButton(TextureRegion region, Game game) {
        super(region);
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight() * 0.1f);
        pos.set(-0.15f, 0.3f);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.setScreen(new GameScreen());
        return false;
    }
}
