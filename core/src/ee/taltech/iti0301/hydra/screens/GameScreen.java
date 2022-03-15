package ee.taltech.iti0301.hydra.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import ee.taltech.iti0301.hydra.Hydra;
import ee.taltech.iti0301.hydra.entities.Bullet;
import ee.taltech.iti0301.hydra.entities.Tank;
import ee.taltech.iti0301.hydra.networking.NetworkingGame;
import ee.taltech.iti0301.hydra.networking.NetworkingMain;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen {

    Client gameClient;
    boolean isConnected;

    TiledMap tiledMap;
    OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera camera;

    Tank palyerTank;
    ArrayList<Bullet> bullets;
    BitmapFont font;
    TextField textField;

    boolean mousePressed = false;

    Hydra hydra;

    public GameScreen(Hydra hydra, Client gameClient) {
        this.hydra = hydra;
        font = new BitmapFont();
        textField = new TextField();
        this.gameClient = gameClient;
        isConnected = gameClient.isConnected();

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 50, 50 * (height / width));

        Random r = new Random();

        bullets = new ArrayList<>();
        palyerTank = new Tank(r.nextInt(10), r.nextInt(10));
        tiledMap = new TmxMapLoader().load("Map_assets/SecondMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/16f);
    }
//
    private void handleInput() {
        if (Gdx.input.isKeyPressed((Input.Keys.E))) {
            camera.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed((Input.Keys.Q))) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.justTouched()) {
            mousePressed = true;
        }

        if (palyerTank.tankMoved && isConnected) {
            System.out.println(palyerTank.x + " " + palyerTank.y);
            NetworkingGame.CurrentCoordinates coordinates = new NetworkingGame.CurrentCoordinates();
            coordinates.x = palyerTank.x;
            coordinates.y = palyerTank.y;
            gameClient.sendUDP(coordinates);
            palyerTank.tankMoved = false;
        }
    }

    @Override
    public void show () {
    }

    @Override
    public void render (float delta) {

        handleInput();
        palyerTank.update(delta);
        camera.position.x = palyerTank.x;
        camera.position.y = palyerTank.y;
        camera.update();

        if (mousePressed) {
            bullets.add(new Bullet(palyerTank.x + 0.1f, palyerTank.y, new Vector2(0, 0)));
            mousePressed = false;
        }

        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render();

        hydra.batch.setProjectionMatrix(camera.combined);
        hydra.batch.begin();
        font.draw(hydra.batch, palyerTank.rotation + " " + palyerTank.x + " " + palyerTank.y, 10, 10);
        for (Bullet bullet: bullets) {
            bullet.update(delta);
            bullet.draw(hydra.batch);
        }
        palyerTank.draw(hydra.batch);
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
