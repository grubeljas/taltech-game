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

import static ee.taltech.iti0301.hydra.networking.NetworkingMain.*;

public class MainMenu implements Screen {

    Hydra game;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture playButtonActive;
    Texture playButtonInactive;

    Client lobbyClient;
    Client gameClient;
    GameSession gameSession;

    private static final int EXIT_BUTTON_WIDTH = 20;
    private static final int EXIT_BUTTON_HEIGHT = 10;
    private static final int EXIT_START_Y = 8;
    private static final int EXIT_END_Y = EXIT_START_Y + EXIT_BUTTON_HEIGHT;
    private static final int EXIT_START_X = 41;
    private static final int EXIT_END_X = EXIT_START_X + EXIT_BUTTON_WIDTH;

    private static final int PLAY_BUTTON_WIDTH = 20;
    private static final int PLAY_BUTTON_HEIGHT = 10;
    private static final int PLAY_START_Y = 20;
    private static final int PLAY_END_Y = PLAY_START_Y + PLAY_BUTTON_HEIGHT;
    private static final int PLAY_START_X = 41;
    private static final int PLAY_END_X = PLAY_START_X + PLAY_BUTTON_WIDTH;

    OrthographicCamera camera;


    public MainMenu(Hydra game) {
        this.game = game;

        lobbyClient = new Client();
        lobbyClient.start();
        gameClient = new Client();
        gameClient.start();
        NetworkingMain.register(lobbyClient);

        lobbyClient.addListener(new Listener() {

            public void connected (Connection connection) {
                System.out.println("CONNECTED");
                NetworkingMain.RegisterName registerName = new NetworkingMain.RegisterName();
                registerName.name = "UgaBuga";
                lobbyClient.sendTCP(registerName);
            }

            public void received (Connection connection, Object object) {
                if (object instanceof NetworkingMain.RegistrationResponse) {
                    NetworkingMain.RegistrationResponse response = (NetworkingMain.RegistrationResponse) object;
                    System.out.println(response.text);
                }

                if (object instanceof NetworkingMain.GameServerPorts) {
                    NetworkingMain.GameServerPorts ports = (NetworkingMain.GameServerPorts) object;
                    System.out.println(ports.tcp + " " + ports.udp);
                }
            }
        });

        try {
            lobbyClient.connect(60000, SERVER_ADDRESS, MAIN_TCP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }


        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 100f, 100f * (height / width));

        this.exitButtonActive = new Texture("exit_button_active.png");
        this.exitButtonInactive = new Texture("exit_button_inactive.png");
        this.playButtonActive = new Texture("play_button_active.png");
        this.playButtonInactive = new Texture("play_button_inactive.png");
    }

    @Override
    public void show () {

    }

    @Override
    public void render (float delta) {
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
            if (Gdx.input.isTouched()) {
                game.setScreen(new GameScreen(game, new GameSession(gameClient)));
            }
        } else {
            game.batch.draw(playButtonInactive, PLAY_START_X, PLAY_START_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        if (mouse_position.x > EXIT_START_X && mouse_position.x < EXIT_END_X &&
        mouse_position.y > EXIT_START_Y && mouse_position.y < EXIT_END_Y) {
            game.batch.draw(exitButtonActive, EXIT_START_X, EXIT_START_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitButtonInactive, EXIT_START_X, EXIT_START_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }
        game.batch.end();


    }

    @Override
    public void resize (int width, int height) {
        camera.viewportWidth = 100f;
        camera.viewportHeight = 100f * height/width;
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

    }
}
