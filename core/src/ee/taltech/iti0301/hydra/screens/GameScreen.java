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
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0301.hydra.Hydra;
import ee.taltech.iti0301.hydra.entities.Bullet;
import ee.taltech.iti0301.hydra.networking.NetworkingGame;
import ee.taltech.iti0301.hydra.networking.NetworkingMain;

import java.util.ArrayList;

public class GameScreen implements Screen {

    Client gameClient;
    boolean isConnected;

    public static final float SPEED = 20;
    Texture tempTankTexture;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera camera;

    ArrayList<Bullet> bullets;

    float tankPositionX;
    float tankPositionY;
    boolean tankMoved = false;
    boolean mousePressed = false;

    Hydra hydra;

    public GameScreen(Hydra hydra, Client gameClient) {
        this.hydra = hydra;

        this.gameClient = gameClient;
        isConnected = gameClient.isConnected();

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 50, 50 * (height / width));

        bullets = new ArrayList<>();
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
        if (Gdx.input.justTouched()) {
            mousePressed = true;
        }

        if (tankMoved && isConnected) {
            System.out.println(tankPositionX + " " + tankPositionY);
            NetworkingGame.CurrentCoordinates coordinates = new NetworkingGame.CurrentCoordinates();
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

        if (mousePressed) {
            bullets.add(new Bullet(tankPositionX + 0.1f, tankPositionY, new Vector2(0, 0)));
            mousePressed = false;
        }

        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render();

        hydra.batch.setProjectionMatrix(camera.combined);
        hydra.batch.begin();
        for (Bullet bullet: bullets) {
            bullet.update(Gdx.graphics.getDeltaTime());
            bullet.render(hydra.batch);
        }
        hydra.batch.draw(tempTankTexture, tankPositionX - 1.5f, tankPositionY - 1.5f, 5, 5);
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
