package ru.starwars.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.BaseScreen;
import ru.starwars.button.ExitButton;
import ru.starwars.button.NewGameButton;
import ru.starwars.math.Rect;
import ru.starwars.sprite.Background;
import ru.starwars.sprite.Title;
import ru.starwars.sprite.Star;

public class FinishScreen extends BaseScreen {

    private Game game;
    private Texture bg;
    private Background background;
    private Star[] stars;
    private TextureAtlas atlas;
    private Music music;

    private NewGameButton newGameButton;
    private ExitButton exitButton;
    private Title gameOver;

    private final int ANIMATION_SPEED = 5;


    public FinishScreen(Game game, Star[] stars, TextureAtlas atlas, Music music) {
        this.game = game;
        this.stars = stars;
        this.atlas = atlas;
        this.music = music;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures\\background.jpg");
        background = new Background(new TextureRegion(bg));
        newGameButton = new NewGameButton(atlas, game);
        exitButton = new ExitButton(atlas.findRegion("exit"));
        gameOver = new Title(atlas.findRegion("gameover"));
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
    public void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        newGameButton.update(delta);
        exitButton.update(delta);
    }

    @Override
    public void draw() {
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        gameOver.zoomAnimation(0, 1, batch, ANIMATION_SPEED);
        gameOver.movedAnimation(batch,new Vector2(0,0),new Vector2(0.01f,0.1f),ANIMATION_SPEED);
        newGameButton.zoomAnimation(0, 1, batch, ANIMATION_SPEED);
        exitButton.zoomAnimation(0, 1, batch, ANIMATION_SPEED);
        exitButton.movedAnimation(batch,new Vector2(0,0),new Vector2(0,-0.1f),ANIMATION_SPEED);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        newGameButton.resize(0.05f, 0, 0);
        exitButton.resize(0.1f, 0, -0.1f);
        gameOver.resize(0.1f, 0.01f, 0.1f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        newGameButton.touchDown(touch, pointer, button);
        exitButton.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        newGameButton.touchUp(touch, pointer, button);
        exitButton.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean mouseMoved(Vector2 touch) {
        newGameButton.mouseMoved(touch);
        exitButton.mouseMoved(touch);
        return false;
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        if (music != null) {
            music.dispose();
        }
        super.dispose();
        newGameButton.dispose();
        exitButton.dispose();
    }
}
