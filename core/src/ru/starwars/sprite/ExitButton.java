package ru.starwars.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.starwars.base.BaseButton;
import ru.starwars.math.Rect;

public class ExitButton extends BaseButton {

    public ExitButton(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight() * 0.1f);
        pos.set(0.15f, 0.3f);
    }

    @Override
    protected void action() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                animation();
                Gdx.app.exit();
            }
        }).start();
    }

    private void animation(){
        playAnimation = false;
        while (scale <= 50f){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scale+=0.2f;
        }
    }
}
