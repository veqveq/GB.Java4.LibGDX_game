package ru.starwars.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.starwars.base.BaseScreen;
import ru.starwars.math.Rect;
import ru.starwars.pool.BulletPool;
import ru.starwars.pool.EnemyShipPool;
import ru.starwars.pool.ExplodePool;
import ru.starwars.sprite.Background;
import ru.starwars.sprite.Bullet;
import ru.starwars.sprite.EnemyShip;
import ru.starwars.sprite.PlayerShip;
import ru.starwars.sprite.Star;
import ru.starwars.tools.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private final int STARS_COUNT = 64;

    private Texture bg;
    private Game game;

    private Background background;
    private PlayerShip player;
    private Star[] stars;
    private TextureAtlas atlas;
    private BulletPool playerBulletPool;
    private BulletPool enemyBulletPool;
    private EnemyShipPool enemyShipPool;
    private ExplodePool explodePool;
    private EnemyEmitter enemyEmitter;
    private Music music;
    private Sound enemyBulletSound;
    private Sound playerExplodeSound;
    private Sound enemyExplodeSound;
    private boolean sounds;
    private boolean changeScreen;

    public GameScreen(Boolean sounds, Game game) {
        this.sounds = sounds;
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures\\game.pack");
        bg = new Texture("textures\\background.jpg");
        background = new Background(new TextureRegion(bg));
        enemyBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds\\TIE-fire.wav"));
        enemyExplodeSound = Gdx.audio.newSound(Gdx.files.internal("sounds\\TIE-explode.wav"));
        playerExplodeSound = Gdx.audio.newSound(Gdx.files.internal("sounds\\XWing-explode.wav"));
        playerBulletPool = new BulletPool();
        enemyBulletPool = new BulletPool();
        explodePool = new ExplodePool(atlas);
        player = new PlayerShip(atlas, playerBulletPool, explodePool, playerExplodeSound, sounds);
        enemyShipPool = new EnemyShipPool(enemyBulletPool, explodePool, enemyExplodeSound, sounds, player, worldBounds);
        enemyEmitter = new EnemyEmitter(worldBounds, enemyShipPool, atlas, enemyBulletSound);
        stars = new Star[STARS_COUNT];

        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }

        if (sounds) {
            music = Gdx.audio.newMusic(Gdx.files.internal("musics\\MainTheme.mp3"));
            music.setLooping(true);
            music.setVolume(0.3f);
            music.play();
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        update(delta);
        checkCollision();
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
        playerBulletPool.dispose();
        enemyBulletPool.dispose();
        enemyShipPool.dispose();
        enemyBulletSound.dispose();
        explodePool.dispose();
        enemyExplodeSound.dispose();
        playerExplodeSound.dispose();
//        if (sounds) {
//            music.stop();
//            music.dispose();
//        }
        super.dispose();
    }

    public void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        playerBulletPool.updateActiveSprites(delta);
        enemyBulletPool.updateActiveSprites(delta);
        explodePool.updateActiveSprites(delta);
        player.update(delta);
        enemyEmitter.generate(delta);
        enemyShipPool.updateActiveSprites(delta);
    }

    private void checkCollision() {
        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        List<Bullet> playerBulletList = playerBulletPool.getActiveObjects();
        List<Bullet> enemyBulletList = enemyBulletPool.getActiveObjects();
        for (Bullet bullet : playerBulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            for (EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isDestroyed()) {
                    continue;
                }
                if (enemyShip.isBulletCollision(bullet) && enemyShip.getScale() == 1) {
                    enemyShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }
        for (Bullet bullet : enemyBulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (player.isBulletCollision(bullet)) {
                player.damage(bullet.getDamage());
                bullet.destroy();
            }
        }
    }

    private void freeAllDestroyed() {
        playerBulletPool.freeAllDestroyedActiveSprites();
        enemyBulletPool.freeAllDestroyedActiveSprites();
        enemyShipPool.freeAllDestroyedActiveSprites();
        explodePool.freeAllDestroyedActiveSprites();
    }

    public void draw() {
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        enemyShipPool.drawActiveSprites(batch);
        explodePool.drawActiveSprites(batch);
        enemyBulletPool.drawActiveSprites(batch);
        playerBulletPool.drawActiveSprites(batch);
        if (!player.isDestroyed()) {
            player.draw(batch);
        } else {
            gameOver();
        }
    }

    private void gameOver() {
        enemyEmitter.stopGenerate();
        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) {
                continue;
            } else {
                enemyShip.goAway();
            }
        }
        if (explodePool.getActiveObjects().size() == 0
                && enemyShipPool.getActiveObjects().size() == 0) {
            game.setScreen(new FinishScreen(game, stars, atlas, music));
        }
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
        player.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        player.touchDown(touch, pointer, button);
        return false;
    }
}
