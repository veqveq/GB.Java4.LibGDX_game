package ru.starwars.button;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.starwars.base.BaseButton;
import ru.starwars.math.Rect;
import ru.starwars.screen.MenuScreen;

public class NewGameButton extends BaseButton {

    private Game game;

    public NewGameButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("newgame"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        pos.set(0,0);
    }

    @Override
    protected void action() {
        game.setScreen(new MenuScreen(game));
    }
}
