package ee.taltech.iti0301.hydra.entity.tank;

import com.badlogic.gdx.graphics.Texture;
import ee.taltech.iti0301.hydra.entity.Entity;

public class TankBody extends Entity {

    private static final float WIDTH = 2f;
    private static final float HEIGHT = 2f;
    private static final Texture TANK_TEXTURE = new Texture("prototank.png");

    public TankBody(float x, float y, float angle) {
        super(x, y, angle, TANK_TEXTURE, WIDTH, HEIGHT);
    }

    // Not the perfect solution to disposing this stuff, but will work for now.
    // AssetManager needs to be added as a better way.
    public static void dispose() {
        TANK_TEXTURE.dispose();
    }
}
