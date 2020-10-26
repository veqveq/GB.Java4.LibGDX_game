package ru.starwars.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.starwars.math.MatrixUtils;
import ru.starwars.math.Rect;

public class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;
    private Rect screenBounds;
    private Rect worldBounds;
    private Rect glBounds;

    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    private Vector2 touch;

    @Override
    public void show() {
        System.out.println("show");
        batch = new SpriteBatch();
        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0, 0, 1f, 1f);
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
        touch = new Vector2();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void update (float delta){

    }

    public void draw(){

    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize width = " + width + " height = " + height);
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        resize(worldBounds);
    }

    public void resize(Rect worldBounds) {
        System.out.println("resize width = " + worldBounds.getWidth() + " height = " + worldBounds.getHeight());
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX,Gdx.graphics.getHeight()-screenY).mul(screenToWorld);
        touchDown(touch,pointer,button);
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX,Gdx.graphics.getHeight()-screenY).mul(screenToWorld);
        touchUp(touch,pointer,button);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX,Gdx.graphics.getHeight()-screenY).mul(screenToWorld);
        touchDragged(touch,pointer);
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        touch.set(screenX,Gdx.graphics.getHeight()-screenY).mul(screenToWorld);
        mouseMoved(touch);
        return false;
    }

    public boolean mouseMoved(Vector2 touch) {
        return false;
    }


    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
