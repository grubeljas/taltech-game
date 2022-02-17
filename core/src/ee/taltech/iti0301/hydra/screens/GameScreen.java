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

    public static final float SPEED = 20;
    Texture tempTankTexture;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera camera;

    float x;
    float y;

    Hydra hydra;

    public GameScreen(Hydra hydra) {
        this.hydra = hydra;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 50, 50 * (8f / 12f));

        tempTankTexture = new Texture("prototank.png");
        tiledMap = new TmxMapLoader().load("Map_assets/FirstMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/32f);
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += SPEED * Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public void show () {
    }

    @Override
    public void render (float delta) {

        handleInput();
        camera.position.x = x;
        camera.position.y = y;
        camera.update();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render();

        hydra.batch.setProjectionMatrix(camera.combined);
        hydra.batch.begin();
        hydra.batch.draw(tempTankTexture, x - 1, y - 1, 3, 3);
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
