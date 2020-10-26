package ru.starwars.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.starwars.base.BaseButton;
import ru.starwars.math.Rect;
import ru.starwars.screen.GameScreen;

public class PlayButton extends BaseButton {

    private Game game;

    public PlayButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight() * 0.1f);
        pos.set(-0.15f, 0.3f);
    }

    @Override
    protected void action() {
        game.setScreen(new GameScreen());
    }
}
