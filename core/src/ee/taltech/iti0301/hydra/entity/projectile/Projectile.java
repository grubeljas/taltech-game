package ee.taltech.iti0301.hydra.entity.projectile;

import com.badlogic.gdx.graphics.Texture;
import ee.taltech.iti0301.hydra.entity.Entity;
import ee.taltech.iti0301.hydra.entity.MovableEntity;

public class Projectile extends Entity implements MovableEntity {

    private static final float WIDTH = 2f;
    private static final float HEIGHT = 2f;
    private static final int SPEED = 10;
    private static final Texture BULLET_TEXTURE = new Texture("bullet.png");


    public Projectile(float x, float y, float rotation) {
        super(x, y, rotation, BULLET_TEXTURE, WIDTH, HEIGHT);
    }

    @Override
    public void updatePosition(float deltaTime) {
        y += SPEED * Math.sin(Math.toRadians(rotation + 90)) * deltaTime;
        x += SPEED * Math.cos(Math.toRadians(rotation + 90)) * deltaTime;
        updateSpritePosition();
    }

    // Not the perfect solution to disposing this stuff, but will work for now.
    // AssetManager needs to be added as a better way.
    public static void dispose() {
        BULLET_TEXTURE.dispose();
    }
}
