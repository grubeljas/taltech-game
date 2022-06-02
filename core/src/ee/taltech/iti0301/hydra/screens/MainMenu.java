package ee.taltech.iti0301.hydra.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import ee.taltech.iti0301.hydra.Hydra;
import ee.taltech.iti0301.hydra.networking.Client;
import java.net.URI;
import java.net.URISyntaxException;

public class MainMenu implements Screen {

    final Hydra game;
    Client client;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture playButtonActive;
    Texture playButtonInactive;

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


    public MainMenu(final Hydra game) {
        this.game = game;

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 100f, 100f * (height / width));

        this.exitButtonActive = new Texture("exit_button_active.png");
        this.exitButtonInactive = new Texture("exit_button_inactive.png");
        this.playButtonActive = new Texture("play_button_active.png");
        this.playButtonInactive = new Texture("play_button_inactive.png");

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    client = new Client(new URI("ws://10.192.244.9:5003")); // 193.40.255.17
                    client.connectBlocking();
                    client.setGameToClient(game);
                } catch (URISyntaxException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
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
                client.sendMessageToServerToStartGame();
                dispose();
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
