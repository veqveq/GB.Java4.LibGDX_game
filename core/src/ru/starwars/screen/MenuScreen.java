package ru.starwars.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.BaseScreen;
import ru.starwars.math.Rect;
import ru.starwars.sprite.Background;
import ru.starwars.sprite.ExitButton;
import ru.starwars.sprite.PlayButton;
import ru.starwars.sprite.Star;

public class MenuScreen extends BaseScreen {

    private final int STARS_COUNT = 256;

    private Game game;
    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    private ExitButton exit;
    private PlayButton play;
    private Star[] stars;
    private Music music;


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

        stars = new Star[STARS_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }

        music = Gdx.audio.newMusic(Gdx.files.internal("musics\\MenuTheme.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();

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
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        music.dispose();
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
        for (Star star : stars) {
            star.update(delta);
        }
    }

    public void draw() {
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        play.draw(batch);
        exit.draw(batch);
    }
}
