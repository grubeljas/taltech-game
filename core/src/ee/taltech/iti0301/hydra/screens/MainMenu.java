package ee.taltech.iti0301.hydra.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import ee.taltech.iti0301.hydra.Hydra;
import jdk.tools.jmod.Main;

public class MainMenu implements Screen {

    Hydra game;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture playButtonActive;
    Texture playButtonInactive;

    private static final int EXIT_BUTTON_WIDTH = 150;
    private static final int EXIT_BUTTON_HEIGHT = 75;
    private static final int EXIT_START_Y = 50;
    private static final int EXIT_END_Y = EXIT_START_Y + EXIT_BUTTON_HEIGHT;
    private static final int EXIT_START_X = (Hydra.WIDTH - EXIT_BUTTON_WIDTH) / 2;
    private static final int EXIT_END_X = EXIT_START_X + EXIT_BUTTON_WIDTH;


    public MainMenu(Hydra game) {
        this.game = game;
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
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();


        if (Gdx.input.getX() > EXIT_START_X && Gdx.input.getX() < EXIT_END_X &&
        Hydra.HEIGHT - Gdx.input.getY() > EXIT_START_Y && Hydra.HEIGHT - Gdx.input.getY() < EXIT_END_Y) {
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
