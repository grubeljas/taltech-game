package ee.taltech.iti0301.hydra.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import ee.taltech.iti0301.hydra.Hydra;
import ee.taltech.iti0301.hydra.entity.Entity;
import ee.taltech.iti0301.hydra.entity.MovableEntity;
import ee.taltech.iti0301.hydra.entity.old.Bullet;
import ee.taltech.iti0301.hydra.entity.projectile.Projectile;
import ee.taltech.iti0301.hydra.entity.tank.TankBody;
import ee.taltech.iti0301.hydra.networking.Client;

import java.awt.TextField;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen implements Screen {
    
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera camera;

    List<Bullet> bullets = new ArrayList<>();
    BitmapFont font;
    TextField textField;

    boolean mousePressed = false;

    Hydra hydra;
    
    private Client client;

    float mouseX, mouseY;
    Vector3 mouseVector;

    public GameScreen(Hydra hydra, Client client) {
        this.hydra = hydra;

        
        
        font = new BitmapFont();
        textField = new TextField();

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 50, 50 * (height / width));

        Random r = new Random();

        tiledMap = new TmxMapLoader().load("Map_assets/SecondMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1/16f);
    
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    client = new Client(new URI("ws://193.40.255.17:5001")); // 193.40.255.17
                    client.connectBlocking();
                    client.setGameToClient(game);
                } catch (URISyntaxException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
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

        TankBody.Direction movementDirection = TankBody.Direction.NONE;
        TankBody.Direction rotationDirection = TankBody.Direction.NONE;

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            movementDirection = TankBody.Direction.BACKWARD;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            movementDirection = TankBody.Direction.FORWARD;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            rotationDirection = TankBody.Direction.LEFT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            rotationDirection = TankBody.Direction.RIGHT;
        }

        mouseX = Gdx.input.getX();
        mouseY = Gdx.input.getY();
        mouseVector = new Vector3(mouseX, mouseY, 0);
        camera.unproject(mouseVector);

        

        /**if (mousePressed) {
            Projectile bullet = new Projectile(5,
                    gameSession.getPlayerTank().getX(),
                    gameSession.getPlayerTank().getY(),
                    gameSession.getPlayerTank().getTurret().getRotation());

            gameSession.getMovableEntities().add(bullet);
            gameSession.getEntities().add(bullet);
            System.out.println(gameSession.getMovableEntities());
            mousePressed = false;
        }
         **/
    }

    @Override
    public void show () {
    }

    @Override
    public void render (float delta) {

        handleInput();
        // Update positions for all our movable entities
        for (MovableEntity entity : gameSession.getMovableEntities()) {
            entity.updatePosition(delta);
        }
        camera.position.x = gameSession.getPlayerTank().getX();
        camera.position.y = gameSession.getPlayerTank().getY();
        camera.update();

        Gdx.gl.glClearColor(0.3f, 0.35f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render();

        hydra.batch.setProjectionMatrix(camera.combined);
        hydra.batch.begin();

        font.draw(hydra.batch, String.format("%.2f %.2f %.2f", gameSession.getPlayerTank().getRotation(),
                        gameSession.getPlayerTank().getX(), gameSession.getPlayerTank().getY()),
                10, 10);
        for (Bullet bullet: bullets) {
            bullet.update(delta);
            bullet.draw(hydra.batch);
        }

        // Draw all our entities
        for (Entity entity : gameSession.getEntities()) {
            entity.draw(hydra.batch);
        }
        hydra.batch.end();
    }
    
    public List<Bullet> getBullets() {
        return bullets;
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
        Projectile.dispose();
        TankBody.dispose();
    }
}
