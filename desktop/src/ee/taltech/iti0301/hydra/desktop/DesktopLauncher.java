package ee.taltech.iti0301.hydra.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ee.taltech.iti0301.hydra.Hydra;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 60;
		config.width = Hydra.WIDTH;
		config.height = Hydra.HEIGHT;
//		config.resizable = false;
		new LwjglApplication(new Hydra(), config);
	}
}
