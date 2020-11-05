package ru.starwars.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.BaseScreen;
import ru.starwars.math.Rect;
import ru.starwars.pool.BulletPool;
import ru.starwars.pool.EnemyShipPool;
import ru.starwars.sprite.Background;
import ru.starwars.sprite.EnemyShip;
import ru.starwars.sprite.PlayerShip;
import ru.starwars.sprite.Star;
import ru.starwars.tools.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private final int STARS_COUNT = 64;

    private Texture bg;

    private Background background;
    private PlayerShip player;
//    private EnemyShip enemy;
    private Star[] stars;
    private TextureAtlas atlas;
    private BulletPool playerBulletPool;
    private BulletPool enemyBulletPool;
    private EnemyShipPool enemyShipPool;
    private EnemyEmitter enemyEmitter;
    private Music music;
    private Sound enemyBulletSound;
    private boolean sounds;

    public GameScreen(Boolean sounds){
        this.sounds = sounds;
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures\\game.pack");
        bg = new Texture("textures\\background.jpg");
        enemyBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds\\TIE-fire.wav"));
        background = new Background(new TextureRegion(bg));
        playerBulletPool = new BulletPool();
        enemyBulletPool = new BulletPool();
        player = new PlayerShip(atlas, playerBulletPool,sounds);
//        enemy = new EnemyShip(atlas, enemyBulletPool,sounds,player);
        enemyShipPool = new EnemyShipPool(enemyBulletPool,sounds,player,worldBounds);
        enemyEmitter = new EnemyEmitter(worldBounds,enemyShipPool,atlas,enemyBulletSound);
        stars = new Star[STARS_COUNT];

        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }

        if (sounds) {
            music = Gdx.audio.newMusic(Gdx.files.internal("musics\\MainTheme.mp3"));
            music.setLooping(true);
            music.setVolume(0.5f);
            music.play();
        }
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
//        enemy.resize(worldBounds);
        player.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        playerBulletPool.dispose();
        enemyBulletPool.dispose();
        enemyShipPool.dispose();
        music.dispose();
        enemyBulletSound.dispose();
        super.dispose();
    }

    public void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        playerBulletPool.updateActiveSprites(delta);
        enemyBulletPool.updateActiveSprites(delta);
//        enemy.update(delta);
        player.update(delta);
        enemyEmitter.generate(delta);
        enemyShipPool.updateActiveSprites(delta);
    }

    private void checkColision() {

    }

    private void freeAllDestroyed(){
        playerBulletPool.freeAllDestroyedActiveSprites();
        enemyBulletPool.freeAllDestroyedActiveSprites();
        enemyShipPool.freeAllDestroyedActiveSprites();
    }

    public void draw() {
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        enemyBulletPool.drawActiveSprites(batch);
        enemyShipPool.drawActiveSprites(batch);
//        enemy.draw(batch);
        playerBulletPool.drawActiveSprites(batch);
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
