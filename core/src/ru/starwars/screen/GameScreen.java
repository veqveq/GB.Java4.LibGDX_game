package ru.starwars.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.starwars.base.BaseScreen;
import ru.starwars.button.ExitButton;
import ru.starwars.button.NewGameButton;
import ru.starwars.math.Rect;
import ru.starwars.pool.BulletPool;
import ru.starwars.pool.EnemyShipPool;
import ru.starwars.pool.ExplodePool;
import ru.starwars.sprite.Background;
import ru.starwars.sprite.Bullet;
import ru.starwars.sprite.EnemyShip;
import ru.starwars.sprite.PlayerShip;
import ru.starwars.sprite.Star;
import ru.starwars.sprite.Title;
import ru.starwars.utils.AnimatedFont;
import ru.starwars.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private final int STARS_COUNT = 64;
    private final float MERGED = 0.02f;

    private enum State {PLAYING, GAME_OVER}

    private Texture bg;
    private State state;

    private NewGameButton newGameButton;
    private ExitButton exitButton;
    private Title gameOver;

    private final int ANIMATION_SPEED = 5;
    private final String HP = "hp: ";
    private final String SCORE = "score: ";

    private StringBuilder hpPrinter = new StringBuilder();
    private StringBuilder scorePrinter = new StringBuilder();

    private int scoreCounter;

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
    private AnimatedFont fontScore;
    private AnimatedFont fontHp;


    public GameScreen(Boolean sounds) {
        this.sounds = sounds;
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures\\game.pack");
        bg = new Texture("textures\\background.jpg");
        enemyBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds\\TIE-fire.wav"));
        enemyExplodeSound = Gdx.audio.newSound(Gdx.files.internal("sounds\\TIE-explode.wav"));
        playerExplodeSound = Gdx.audio.newSound(Gdx.files.internal("sounds\\XWing-explode.wav"));
        fontScore = new AnimatedFont("font\\starwars.fnt", "font\\starwars.png");
        fontHp = new AnimatedFont("font\\starwars.fnt", "font\\starwars.png");
        fontScore.setSize(0.03f);
        fontHp.setSize(0.03f);
        fontScore.setColor(Color.YELLOW);
        fontHp.setColor(Color.YELLOW);

        playerBulletPool = new BulletPool();
        enemyBulletPool = new BulletPool();
        explodePool = new ExplodePool(atlas);

        player = new PlayerShip(atlas, playerBulletPool, explodePool, playerExplodeSound, sounds);
        background = new Background(new TextureRegion(bg), player.getV());
        stars = new Star[STARS_COUNT];

        enemyShipPool = new EnemyShipPool(enemyBulletPool, explodePool, enemyExplodeSound, sounds, player, worldBounds);
        enemyEmitter = new EnemyEmitter(worldBounds, enemyShipPool, atlas, enemyBulletSound);

        newGameButton = new NewGameButton(atlas, this);
        exitButton = new ExitButton(atlas.findRegion("exit"));
        gameOver = new Title(atlas.findRegion("gameover"));

        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas, player.getV());
        }
        if (sounds) {
            music = Gdx.audio.newMusic(Gdx.files.internal("musics\\MainTheme.mp3"));
            music.setLooping(true);
            music.setVolume(0.3f);
            music.play();
        }

        state = State.PLAYING;
    }

    public void restartGame() {
        player.restartGame(worldBounds);
        state = State.PLAYING;

        explodePool.freeAllActiveObjects();
        playerBulletPool.freeAllActiveObjects();

        exitButton.resetAnimation();
        gameOver.resetAnimation();
        newGameButton.resetAnimation();

        fontScore.resetAnimation();
        fontScore.setSize(0.03f*fontScore.getScaleY());
        fontHp.resetAnimation();
        scoreCounter = 0;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        update(delta);
        checkCollision();
        freeAllDestroyed();
        draw();
        drawInterface();
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        player.resize(worldBounds);
        newGameButton.resize(0.05f, 0, 0);
        exitButton.resize(0.1f, 0, -0.1f);
        gameOver.resize(0.1f, 0.01f, 0.1f);
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
        if (sounds) music.dispose();
        super.dispose();
    }

    public void update(float delta) {
        background.update(delta);
        for (Star star : stars) {
            star.update(delta);
        }
        explodePool.updateActiveSprites(delta);
        enemyShipPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            playerBulletPool.updateActiveSprites(delta);
            enemyBulletPool.updateActiveSprites(delta);
            player.update(delta);
            enemyEmitter.generate(delta);
        } else {
            newGameButton.update(delta);
            exitButton.update(delta);
        }
    }

    private void checkCollision() {
        if (state == State.GAME_OVER) return;
        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        List<Bullet> playerBulletList = playerBulletPool.getActiveObjects();
        List<Bullet> enemyBulletList = enemyBulletPool.getActiveObjects();
        for (Bullet bullet : playerBulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            for (EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isDestroyed()) {
                    scoreCounter += enemyShip.getScore();
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
        explodePool.drawActiveSprites(batch);
        enemyShipPool.drawActiveSprites(batch);
        if (state == State.PLAYING) {
            enemyBulletPool.drawActiveSprites(batch);
            playerBulletPool.drawActiveSprites(batch);
        }
        if (!player.isDestroyed()) {
            player.draw(batch);
        } else {
            gameOver();
            state = State.GAME_OVER;
        }
        if (state == State.GAME_OVER) {
            gameOver.zoomAnimation(0, 1, batch, ANIMATION_SPEED);
            gameOver.movedAnimation(batch, new Vector2(0, 0), new Vector2(0.01f, 0.1f), ANIMATION_SPEED);
            newGameButton.zoomAnimation(0, 1, batch, ANIMATION_SPEED);
            exitButton.zoomAnimation(0, 1, batch, ANIMATION_SPEED);
            exitButton.movedAnimation(batch, new Vector2(0, 0), new Vector2(0, -0.1f), ANIMATION_SPEED);
        }
    }

    private void gameOver() {
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
            enemyBulletPool.freeAllActiveObjects();
            enemyShipPool.dispose();
        }
    }

    private void drawInterface() {
        if (state == State.PLAYING) {
            scorePrinter.setLength(0);
            fontScore.draw(batch, scorePrinter.append(SCORE).append(scoreCounter), worldBounds.getLeft() + 0.02f, worldBounds.getBottom() + MERGED + fontScore.getCapHeight(), Align.left);
            hpPrinter.setLength(0);
            fontHp.draw(batch, hpPrinter.append(HP).append(player.getHp()), worldBounds.getRight() - 0.02f, worldBounds.getBottom() + MERGED + fontHp.getCapHeight(), Align.right);
        } else {
            scorePrinter.setLength(0);
            fontScore.zoomAnimation(0.03f, 0.07f, ANIMATION_SPEED);
            fontScore.movedAnimation(
                    batch,
                    new Vector2(worldBounds.getLeft() + 0.02f, worldBounds.getBottom() + MERGED + fontScore.getCapHeight()),
                    new Vector2(-0.25f, 0.25f),
                    ANIMATION_SPEED+2f, scorePrinter.append(SCORE).append(scoreCounter),
                    Align.left);
            hpPrinter.setLength(0);
            fontHp.fadingAnimation(ANIMATION_SPEED*0.5f);
            fontHp.draw(batch, hpPrinter.append(HP).append(player.getHp()), worldBounds.getRight() - 0.02f, worldBounds.getBottom() + MERGED + fontHp.getCapHeight(), Align.right);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            player.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            player.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            player.touchUp(touch, pointer, button);
        } else {
            newGameButton.touchUp(touch, pointer, button);
            exitButton.touchUp(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            player.touchDown(touch, pointer, button);
        } else {
            newGameButton.touchDown(touch, pointer, button);
            exitButton.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(Vector2 touch) {
        if (state == State.GAME_OVER) {
            newGameButton.mouseMoved(touch);
            exitButton.mouseMoved(touch);
        }
        return false;
    }
}
