package ru.starwars.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.BaseScreen;
import ru.starwars.math.Rect;
import ru.starwars.pool.BulletPool;
import ru.starwars.sprite.Background;
import ru.starwars.sprite.PlayerUnit;
import ru.starwars.sprite.Star;

public class GameScreen extends BaseScreen {

    private final int STARS_COUNT = 64;

    private Texture bg;
    private Background background;
    private PlayerUnit player;
    private Star[] stars;
    private TextureAtlas atlas;
    private BulletPool bulletPool;
    private Music music;

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures\\game.pack");
        bg = new Texture("textures\\background.jpg");
        background = new Background(new TextureRegion(bg));
        bulletPool = new BulletPool();
        player = new PlayerUnit(atlas,bulletPool);
        stars = new Star[STARS_COUNT];

        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        music = Gdx.audio.newMusic(Gdx.files.internal("musics\\MainTheme.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        update(delta);
        checkColision();
        freeAllDestroyed();
        draw();
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        player.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        music.dispose();
        super.dispose();
    }

    public void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        player.update(delta);
    }

    private void checkColision() {

    }

    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyedActiveSprites();
    }

    public void draw() {
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        bulletPool.drawActiveSprites(batch);
        player.draw(batch);
    }

    @Override
    public boolean keyDown(int keycode) {
        player.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        player.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        player.touchUp(touch,pointer,button);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        player.touchDown(touch,pointer,button);
        return false;
    }
}
