package ru.starwars.button;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.starwars.base.BaseButton;
import ru.starwars.math.Rect;
import ru.starwars.screen.GameScreen;

public class NewGameButton extends BaseButton {

    private GameScreen screen;

    public NewGameButton(TextureAtlas atlas, GameScreen screen) {
        super(atlas.findRegion("newgame"));
        this.screen = screen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        pos.set(0,0);
    }

    @Override
    protected void action() {
        screen.restartGame();
    }
}
