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
import ee.taltech.iti0301.hydra.entity.projectile.Projectile;
import ee.taltech.iti0301.hydra.entity.tank.TankBody;
import ee.taltech.iti0301.hydra.networking.Client;
import ee.taltech.iti0301.hydra.networking.ClientGame;
import ee.taltech.iti0301.hydra.networking.ServerGame;
import java.awt.TextField;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameScreen implements Screen {
    
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera camera;
    
    BitmapFont font;
    TextField textField;

    boolean mousePressed = false;

    Hydra hydra;
    Client client;
    ClientGame clientGame;
    ServerGame serverGame;
    List<Projectile> bullets = new LinkedList<>();
    TankBody myTank;
    List<TankBody> othersTanks = new LinkedList<>();
    List<TankBody> allTanks = new LinkedList<>();
    
    float mouseX, mouseY;
    Vector3 mouseVector;

    public GameScreen(final Hydra hydra, Client client) {
        this.hydra = hydra;
        this.client = client;
        
        font = new BitmapFont();
        textField = new TextField();

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 50, 50 * (height / width));

        Random r = new Random();

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
            if (camera.zoom < 0.1) {
                camera.zoom = 0.1f;
            }
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

        movePlayerTank(movementDirection, rotationDirection, mouseVector);

        if (mousePressed) {
            Projectile bullet = new Projectile(5,
                    myTank.getX(),
                    myTank.getY(),
                    myTank.getTurret().getRotation());

            bullets.add(bullet);
            mousePressed = false;
        }
    }
    
    public void updatePlayerInfo() {
        for (Projectile projectile: serverGame.getBullets()) {
            if (projectile != null) {
                bullets.add(projectile);
            }
        }
        for (TankBody tankBody: serverGame.getTanks()) {
            if (tankBody != null) {
                allTanks.add(tankBody);
            }
        }
    }
    
    public void update(float dt) {
        updatePlayerInfo();
        
        //TODO gametime
        
        if (myTank == null) {
            setMyTank();
        }
        
        //TODO remove old projectiles
        
        handleInput();
        
        myTank.updatePosition(dt);
    
        if (clientGame != null) {
            clientGame.addProjectiles();
            clientGame.addTankBody();
        }
        
        allTanks.clear();
        
        client.update(dt);
    }
    
    public void movePlayerTank(TankBody.Direction movementDirection, TankBody.Direction rotationDirection, Vector3 mouseLocation) {
        myTank.setMovementDirection(movementDirection);
        myTank.setRotationDirection(rotationDirection);
        myTank.setTurretAngle(mouseLocation);
    }
    
    public void setMyTank() {
        for (TankBody tankBody: allTanks) {
            if (tankBody.getId() == Integer.getInteger(this.client.getName())) {
                myTank = tankBody;
            } else {
                this.othersTanks.add(tankBody);
            }
        }
    }

    @Override
    public void show () {
    }

    @Override
    public void render (float delta) {

        update(delta);
        
        // Update positions for all our movable entities
        camera.position.x = myTank.getX();
        camera.position.y = myTank.getY();
        camera.update();

        Gdx.gl.glClearColor(0.3f, 0.35f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render();

        hydra.batch.setProjectionMatrix(camera.combined);
        hydra.batch.begin();

        font.draw(hydra.batch, String.format("%.2f %.2f %.2f", myTank.getRotation(),
                        myTank.getX(), myTank.getY()),
                10, 10);
        for (Projectile bullet: bullets) {
            bullet.updatePosition(delta);
            bullet.draw(hydra.batch);
        }

        // Draw all our entities
        for (TankBody tankBody: allTanks) {
            tankBody.draw(hydra.batch);
        }
        hydra.batch.end();
    }
    
    public List<Projectile> getProjectiles() {
        return bullets;
    }
    
    public TankBody getMyTank() {
        return myTank;
    }
    
    @Override
    public void resize (int width, int height) {
        camera.viewportWidth = 50f;
        camera.viewportHeight = 50f * height/width;
        camera.update();
    }
    
    public void setServerGame(ServerGame serverGame) {
        this.serverGame = serverGame;
    }
    
    public void setClientGame(ClientGame clientGame) {
        this.clientGame = clientGame;
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
