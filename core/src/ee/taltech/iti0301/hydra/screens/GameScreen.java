package ee.taltech.iti0301.hydra.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import ee.taltech.iti0301.hydra.Hydra;
import ee.taltech.iti0301.hydra.entity.FakeEntity;
import ee.taltech.iti0301.hydra.entity.projectile.Projectile;
import ee.taltech.iti0301.hydra.entity.tank.TankBody;
import ee.taltech.iti0301.hydra.networking.Client;
import ee.taltech.iti0301.hydra.networking.ClientGame;
import ee.taltech.iti0301.hydra.networking.ServerGame;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameScreen implements Screen {
    
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera camera;
    
    BitmapFont font;

    boolean mousePressed = false;

    Hydra hydra;
    Client client;
    ClientGame clientGame;
    ServerGame serverGame;
    List<Projectile> bullets = new LinkedList<>();
    List<Projectile> toRemoveBullets = new LinkedList<>();
    List<Projectile> newBullets = new LinkedList<>();
    TankBody myTank;
    int enemyLifes = 10;
    boolean enemyIsDead = false;
    boolean myTankIsDead = false;
    List<TankBody> othersTanks = new LinkedList<>();
    Random random = new Random();
    
    float mouseX, mouseY;
    Vector3 mouseVector;

    public GameScreen(final Hydra hydra, Client client) {
        this.hydra = hydra;
        this.client = client;
        
        font = new BitmapFont();

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
            if (camera.zoom < 0.2) {
                camera.zoom = 0.2f;
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
            Projectile bullet = new Projectile(
                    random.nextInt(100),
                    myTank.getX(),
                    myTank.getY(),
                    myTank.getTurret().getRotation());

            newBullets.add(bullet);
            bullets.add(bullet);
            mousePressed = false;
        }
    }
    
    public void updatePlayerInfo() {
        for (FakeEntity projectile: serverGame.getBullets()) {
            if (projectile != null) {
                bullets.add(new Projectile(projectile));
            }
        }
        for (Integer integer: serverGame.getTanks().keySet()) {
            try {
                if (integer != Integer.parseInt(this.client.getName())) {
                    othersTanks.add(new TankBody(
                            serverGame.getTanks().get(integer),
                            serverGame.getTurrets().get(integer)));
                }
            } catch (NumberFormatException e) {
                Gdx.app.exit();
            }
        }
    }
    
    public void update(float dt) {
        updatePlayerInfo();
        if (myTank == null) {
            setMyTank();
        }
    
        handleInput();
        myTank.updatePosition(dt);
        
        Rectangle recTank = myTank.getSprite().getBoundingRectangle();
        Rectangle enemyTank = othersTanks.get(0).getSprite().getBoundingRectangle();
        for (Projectile bullet: bullets) {
            bullet.updatePosition(dt);
            if (bullet.getLive() <= 0) {
                toRemoveBullets.add(bullet);
            }
            if ((recTank.overlaps(bullet.getSprite().getBoundingRectangle())
                    || enemyTank.overlaps(bullet.getSprite().getBoundingRectangle()))
                    && bullet.getLive() < 9.7) {
                toRemoveBullets.add(bullet);
                if (recTank.overlaps(bullet.getSprite().getBoundingRectangle())) {
                    myTank.health--;
                } else {
                    enemyLifes--;
                }
                System.out.println(myTank.health);
                if (myTank.health == 0) {
                    myTankIsDead = true;
                }
                if (enemyLifes == 0) {
                    enemyIsDead = true;
                }
                System.out.println("HIT HIT HIT HIT");
            }
        }

        
        if (clientGame != null) {
            
            clientGame.addProjectiles();
            clientGame.addTankBody();
        }
        
        client.setClientGame(clientGame);

        
        this.client.update(dt);
        
        newBullets.clear();
    }
    
    public void movePlayerTank(TankBody.Direction movementDirection, TankBody.Direction rotationDirection, Vector3 mouseLocation) {
        myTank.setMovementDirection(movementDirection);
        myTank.setRotationDirection(rotationDirection);
        myTank.setTurretAngle(mouseLocation);
    }
    
    public void setMyTank() {
        for (Integer i: serverGame.getTanks().keySet()) {
            if (i == Integer.parseInt(this.client.getName())) {
                myTank = new TankBody(serverGame.getTanks().get(i), serverGame.getTurrets().get(i));
            } else {
                this.othersTanks.add(new TankBody(serverGame.getTanks().get(i), serverGame.getTurrets().get(i)));
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
        
        bullets.removeAll(toRemoveBullets);
        toRemoveBullets.clear();
        
        hydra.batch.begin();
    
        if (myTankIsDead || enemyIsDead) {
            String title;
            if (myTankIsDead) title = "LOSE";
            else title = "WIN";
            font.draw(hydra.batch, title, myTank.getX() - 10, myTank.getY());
            if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
                Gdx.app.exit();
            }
        }

        if(!myTankIsDead) {
            myTank.draw(hydra.batch);
        }

        for (Projectile bullet : bullets) {
            bullet.draw(hydra.batch);
        }
        
        // Draw all our entities
        for (TankBody tankBody : othersTanks) {
            if (!enemyIsDead) {
                tankBody.draw(hydra.batch);
            }
        }
        
        hydra.batch.end();
        othersTanks.clear();
        
    }
    
    public List<Projectile> getProjectiles() {
        return newBullets;
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
    
    public void killEnemy() {
        enemyIsDead = true;
    }
}
