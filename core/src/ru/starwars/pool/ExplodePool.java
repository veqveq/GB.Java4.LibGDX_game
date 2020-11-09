package ru.starwars.pool;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.starwars.base.AnimatedSprite;
import ru.starwars.base.SpritesPool;
import ru.starwars.sprite.Explode;

public class ExplodePool extends SpritesPool<Explode> {

    private TextureAtlas atlas;

    public ExplodePool(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    @Override
    protected Explode newObject() {
        return new Explode(atlas.findRegion("explode"),9,9,74);
    }

    @Override
    public void drawActiveSprites(SpriteBatch batch) {
        for (AnimatedSprite sprite: activeObjects){
            if (!sprite.isDestroyed()) sprite.fadingAnimation(batch,Explode.ANIMATE_INTERVAL*100);
        }
    }
}
