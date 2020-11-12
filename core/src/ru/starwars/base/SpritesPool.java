package ru.starwars.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool <T extends Sprite>{

    protected final List<T> activeObjects = new ArrayList<>();
    protected final List<T> freeObjects = new ArrayList<>();

    protected abstract T newObject();

    public T obtain(){
        System.out.println(activeObjects.size() +" / " + freeObjects.size());
        T object;
        if (freeObjects.isEmpty()){
           object = newObject();
        }else{
            object = freeObjects.remove(freeObjects.size()-1);
        }
        activeObjects.add(object);
        System.out.printf("Pool <%s>: %d/%d \n",activeObjects.get(0).getClass().getCanonicalName(), activeObjects.size(),freeObjects.size());
        return object;
    }

    public void freeAllActiveObjects(){
        freeObjects.addAll(activeObjects);
        activeObjects.clear();
    }

    public void updateActiveSprites(float delta){
        for (Sprite sprite: activeObjects){
            if (!sprite.isDestroyed()) sprite.update(delta);
        }
    }

    public void drawActiveSprites (SpriteBatch batch){
        for (Sprite sprite: activeObjects){
            if (!sprite.isDestroyed()) sprite.draw(batch);
        }
    }

    public void freeAllDestroyedActiveSprites(){
        for (int i = 0; i < activeObjects.size(); i++) {
            T sprite = activeObjects.get(i);
            if (sprite.isDestroyed()){
                free(sprite);
                i--;
                sprite.flushDestroy();
            }
        }
    }

    public void free (T object){
        if (activeObjects.remove(object)){
            freeObjects.add(object);
        }
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public void dispose(){
        activeObjects.clear();
        freeObjects.clear();
    }
}
