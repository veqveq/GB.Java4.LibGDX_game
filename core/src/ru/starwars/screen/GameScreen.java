package ru.starwars.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.base.BaseScreen;
import ru.starwars.math.Rect;
import ru.starwars.sprite.Background;
import ru.starwars.sprite.Logo;

public class GameScreen extends BaseScreen {

    private Texture bg, lg;
    private Background background;
    private Logo logo;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures\\background.jpg");
        lg = new Texture("textures\\logo.jpg");
        background = new Background(new TextureRegion(bg));
        logo = new Logo(new TextureRegion(lg));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        logo.touchDown(touch,pointer,button);
        return false;
    }

    @Override
    public void dispose() {
        bg.dispose();
        lg.dispose();
        super.dispose();
    }


}
