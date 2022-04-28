package ee.taltech.iti0301.hydra.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import ee.taltech.iti0301.hydra.Hydra;
import ee.taltech.iti0301.hydra.networking.NetworkingGame;
import ee.taltech.iti0301.hydra.networking.NetworkingMain;
import ee.taltech.iti0301.hydra.session.GameSession;

import java.io.IOException;

public class ActiveLobbyScreen implements Screen {

    Hydra game;
    Client lobbyClient;
    Client gameClient;
    boolean playerReady = false;

    int sessionId;
    GameSession session;
    volatile boolean connectToGameServer = false;
    volatile boolean startGame = false;

    OrthographicCamera camera;

    Texture playButtonActive = new Texture("play_button_active.png");
    Texture playButtonInactive = new Texture("play_button_inactive.png");
    private static final int PLAY_BUTTON_WIDTH = 20;
    private static final int PLAY_BUTTON_HEIGHT = 10;
    private static final int PLAY_START_Y = 20;
    private static final int PLAY_END_Y = PLAY_START_Y + PLAY_BUTTON_HEIGHT;
    private static final int PLAY_START_X = 41;
    private static final int PLAY_END_X = PLAY_START_X + PLAY_BUTTON_WIDTH;

    public ActiveLobbyScreen(Hydra game, Client lobbyClient, Client gameClient) {
        this.game = game;
        this.lobbyClient = lobbyClient;
        this.gameClient = gameClient;

        session = new GameSession(gameClient);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 100f, 100f * (height / width));

        lobbyClient.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof NetworkingMain.StartGame) {
                    System.out.println("Game has started");
                    sessionId = ((NetworkingMain.StartGame) object).gameId;
                    connectToGameServer = true;
                }
            }
        });

        gameClient.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof NetworkingGame.UpdateEntity) {

                }
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();

        Vector3 mouse_position = new Vector3(mouseX, mouseY, 0);
        camera.unproject(mouse_position);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        if (mouse_position.x > PLAY_START_X && mouse_position.x < PLAY_END_X &&
                mouse_position.y > PLAY_START_Y && mouse_position.y < PLAY_END_Y) {
            game.batch.draw(playButtonActive, PLAY_START_X, PLAY_START_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if (Gdx.input.justTouched()) {
                playerReady = !playerReady;
                NetworkingMain.ReadyStatus status = new NetworkingMain.ReadyStatus();
                status.ready = playerReady;
                lobbyClient.sendTCP(status);
            }
        } else {
            game.batch.draw(playButtonInactive, PLAY_START_X, PLAY_START_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }
        game.batch.end();

        if (connectToGameServer) {
            try {
                session.start(sessionId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 100f;
        camera.viewportHeight = 100f * height/width;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
