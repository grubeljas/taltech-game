package ee.taltech.iti0301.hydra.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import ee.taltech.iti0301.hydra.Hydra;

public class GameScreen implements Screen {

    public static final float SPEED = 140;
    Texture img;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;

    float x;
    float y;

    Hydra hydra;

    public GameScreen(Hydra hydra) {
        this.hydra = hydra;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 50, 50 * (8f / 12f));
    }

    @Override
    public void show () {
        img = new Texture("badlogic.jpg");
        tiledMap = new TmxMapLoader().load("Map_assets/FirstMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap, 1/32f);
    }

    @Override
    public void render (float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += SPEED * Gdx.graphics.getDeltaTime();
        }
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();

        hydra.batch.begin();
        hydra.batch.draw(img, x, y);
        hydra.batch.end();
    }

    @Override
    public void resize (int width, int height) {

    }

    @Override
    public void pause () {

    }

    @Override
    public void resume () {

    }

    @Override
    public void hide () {

    }

    @Override
    public void dispose () {
        tiledMap.dispose();
    }
}
