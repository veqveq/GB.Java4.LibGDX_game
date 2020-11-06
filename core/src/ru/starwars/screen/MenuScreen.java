package ru.starwars.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.BaseScreen;
import ru.starwars.button.MusicButton;
import ru.starwars.math.Rect;
import ru.starwars.sprite.Background;
import ru.starwars.button.ExitButton;
import ru.starwars.button.PlayButton;
import ru.starwars.sprite.PlayerShip;
import ru.starwars.sprite.Star;

public class MenuScreen extends BaseScreen {

    private final int STARS_COUNT = 256;

    private Game game;
    private Texture bg;
    private TextureAtlas atlas;

    private Background background;

    private ExitButton exitBt;
    private PlayButton playBt;
    private MusicButton musicBt;

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
        music = Gdx.audio.newMusic(Gdx.files.internal("musics\\MenuTheme.mp3"));

        background = new Background(new TextureRegion(bg));
        playBt = new PlayButton(atlas, game);
        musicBt = new MusicButton(atlas);
        exitBt = new ExitButton(atlas);


        stars = new Star[STARS_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }

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
        playBt.resize(worldBounds);
        musicBt.resize(worldBounds);
        exitBt.resize(worldBounds);

        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        playBt.dispose();
        exitBt.dispose();
        musicBt.dispose();
        atlas.dispose();
        music.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        exitBt.touchDown(touch, pointer, button);
        playBt.touchDown(touch, pointer, button);
        musicBt.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        exitBt.touchUp(touch, pointer, button);
        playBt.touchUp(touch, pointer, button);
        musicBt.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean mouseMoved(Vector2 touch) {
        exitBt.mouseMoved(touch);
        playBt.mouseMoved(touch);
        musicBt.mouseMoved(touch);
        return false;
    }

    public void update(float delta) {
        if (!musicBt.isPlayMusic()) {
            music.stop();
            playBt.setSounds(false);
        } else {
            music.play();
            playBt.setSounds(true);
        }
        exitBt.update(delta);
        musicBt.update(delta);
        playBt.update(delta);
        for (Star star : stars) {
            star.update(delta);
        }
    }

    public void draw() {
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        playBt.draw(batch);
        musicBt.draw(batch);
        exitBt.draw(batch);
    }
}
