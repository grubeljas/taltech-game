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
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0301.hydra.Hydra;
import ee.taltech.iti0301.hydra.networking.Networking;

public class GameScreen implements Screen {

    Client gameClient;
    boolean isConnected;

    public static final float SPEED = 20;
    Texture tempTankTexture;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera camera;

    float tankPositionX;
    float tankPositionY;
    boolean tankMoved = false;

    Hydra hydra;

    public GameScreen(Hydra hydra, Client gameClient) {
        this.hydra = hydra;

        this.gameClient = gameClient;
        isConnected = gameClient.isConnected();

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 50, 50 * (height / width));

        tempTankTexture = new Texture("prototank.png");
        tiledMap = new TmxMapLoader().load("Map_assets/FirstMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/32f);
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed((Input.Keys.E))) {
            camera.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed((Input.Keys.Q))) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            tankPositionX += SPEED * Gdx.graphics.getDeltaTime();
            tankMoved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            tankPositionY -= SPEED * Gdx.graphics.getDeltaTime();
            tankMoved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            tankPositionX -= SPEED * Gdx.graphics.getDeltaTime();
            tankMoved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            tankPositionY += SPEED * Gdx.graphics.getDeltaTime();
            tankMoved = true;
        }

        if (tankMoved && isConnected) {
            System.out.println(tankPositionX + " " + tankPositionY);
            Networking.CurrentCoordinates coordinates = new Networking.CurrentCoordinates();
            coordinates.x = tankPositionX;
            coordinates.y = tankPositionY;
            gameClient.sendUDP(coordinates);
            tankMoved = false;
        }
    }

    @Override
    public void show () {
    }

    @Override
    public void render (float delta) {

        handleInput();
        camera.position.x = tankPositionX;
        camera.position.y = tankPositionY;
        camera.update();

        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render();

        hydra.batch.setProjectionMatrix(camera.combined);
        hydra.batch.begin();
        hydra.batch.draw(tempTankTexture, tankPositionX - 1, tankPositionY - 1, 3, 3);
        hydra.batch.end();
    }

    @Override
    public void resize (int width, int height) {
        camera.viewportWidth = 50f;
        camera.viewportHeight = 50f * height/width;
        camera.update();

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
