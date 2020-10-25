package ru.starwars.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.BaseScreen;
import ru.starwars.math.Rect;
import ru.starwars.sprite.Background;
import ru.starwars.sprite.ExitButton;
import ru.starwars.sprite.Logo;
import ru.starwars.sprite.PlayButton;

public class MenuScreen extends BaseScreen {

    private Texture bg, ex, pl;
    private Background background;
    private ExitButton exit;
    private PlayButton play;
    private Game game;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures\\background.jpg");
        ex = new Texture("textures\\exit.png");
        pl = new Texture("textures\\play.png");
        background = new Background(new TextureRegion(bg));
        play = new PlayButton(new TextureRegion(pl),game);
        exit = new ExitButton(new TextureRegion(ex));
        addProcessor(exit);
        addProcessor(play);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        play.draw(batch);
        exit.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        exit.resize(worldBounds);
        play.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        ex.dispose();
        pl.dispose();
        super.dispose();
    }
}
