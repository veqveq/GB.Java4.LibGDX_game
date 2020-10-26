package ru.starwars.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.BaseScreen;
import ru.starwars.math.Rect;
import ru.starwars.sprite.Background;
import ru.starwars.sprite.Star;

public class GameScreen extends BaseScreen {

    private final int STARS_COUNT = 64;

    private Texture bg;
    private Background background;
    private Star[] stars;
    private TextureAtlas atlas;

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures\\menu.pack");
        bg = new Texture("textures\\background.jpg");
        background = new Background(new TextureRegion(bg));
        stars = new Star[STARS_COUNT];

        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        update(delta);
        checkColision();
        draw();
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public void dispose() {
        bg.dispose();
        super.dispose();
    }

    public void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
    }

    public void checkColision() {

    }

    public void draw() {
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
    }


}
