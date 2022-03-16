package ee.taltech.iti0301.hydra.entity.tank;

import com.badlogic.gdx.graphics.Texture;
import ee.taltech.iti0301.hydra.entity.Entity;
import ee.taltech.iti0301.hydra.entity.MovableEntity;

public class TankBody extends Entity implements MovableEntity {

    private static final float WIDTH = 2f;
    private static final float HEIGHT = 2f;
    private static final Texture TANK_TEXTURE = new Texture("prototank.png");
    private static final int MOVEMENT_SPEED = 20;
    private static final int ROTATION_SPEED = 80;

    public enum Direction {
        RIGHT, LEFT, FORWARD, BACKWARD,NONE
    }

    private Direction rotationDirection = Direction.NONE;
    private Direction movementDirection = Direction.NONE;

    public TankBody(float x, float y, float angle) {
        super(x, y, angle, TANK_TEXTURE, WIDTH, HEIGHT);
    }

    @Override
    public void updatePosition(float deltaTime) {
        if (movementDirection == Direction.FORWARD) {
            y += MOVEMENT_SPEED * Math.sin(Math.toRadians(rotation - 90)) * deltaTime;
            x += MOVEMENT_SPEED * Math.cos(Math.toRadians(rotation - 90)) * deltaTime;
        }
        if (movementDirection == Direction.BACKWARD) {
            y -= MOVEMENT_SPEED * Math.sin(Math.toRadians(rotation - 90)) * deltaTime;
            x -= MOVEMENT_SPEED * Math.cos(Math.toRadians(rotation - 90)) * deltaTime;
        }
        if (rotationDirection == Direction.LEFT) {
            rotation += ROTATION_SPEED * deltaTime;
        }
        if (rotationDirection == Direction.RIGHT) {
            rotation -= ROTATION_SPEED * deltaTime;
        }
    }

    // Not the perfect solution to disposing this stuff, but will work for now.
    // AssetManager needs to be added as a better way.
    public static void dispose() {
        TANK_TEXTURE.dispose();
    }
}
