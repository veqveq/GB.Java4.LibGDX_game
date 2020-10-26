package ru.starwars.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.BaseScreen;
import ru.starwars.math.Rect;
import ru.starwars.sprite.Background;
import ru.starwars.sprite.ExitButton;
import ru.starwars.sprite.PlayButton;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private ExitButton exit;
    private PlayButton play;
    private Game game;
    private TextureAtlas atlas;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures\\menu.pack");
        bg = new Texture("textures\\background.jpg");

        background = new Background(new TextureRegion(bg));
        play = new PlayButton(atlas, game);
        exit = new ExitButton(atlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        update(delta);
        draw();
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
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        exit.touchDown(touch,pointer,button);
        play.touchDown(touch,pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        exit.touchUp(touch,pointer,button);
        play.touchUp(touch,pointer,button);
        return false;
    }

    @Override
    public boolean mouseMoved(Vector2 touch) {
        exit.mouseMoved(touch);
        play.mouseMoved(touch);
        return false;
    }

    public void update(float delta) {

    }

    public void draw() {
        background.draw(batch);
        play.draw(batch);
        exit.draw(batch);
    }
}
