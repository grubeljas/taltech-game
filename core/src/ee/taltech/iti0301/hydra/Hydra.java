package ee.taltech.iti0301.hydra;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ee.taltech.iti0301.hydra.screens.MainMenu;

public class Hydra extends Game {

	public SpriteBatch batch;
	public static final int WIDTH = 720;
	public static final int HEIGHT = 480;
	
	@Override
	public void create () {

		Texture tankBody = new Texture("tankbody.png");
		batch = new SpriteBatch();
		this.setScreen(new MainMenu(this));
		//this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
