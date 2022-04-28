package ee.taltech.iti0301.hydra.entity.tank;

import com.badlogic.gdx.graphics.Texture;
import ee.taltech.iti0301.hydra.entity.Entity;
import ee.taltech.iti0301.hydra.entity.MovableEntity;

public class TankBody extends Entity implements MovableEntity {

    private static final float WIDTH = 6f;
    private static final float HEIGHT = 6f;
    private static final Texture TANK_TEXTURE = new Texture("prototank.png");
    private static final int MOVEMENT_SPEED = 20;
    private static final int ROTATION_SPEED = 80;

    public enum Direction {
        RIGHT, LEFT, FORWARD, BACKWARD,NONE
    }

    private Direction rotationDirection = Direction.NONE;
    private Direction movementDirection = Direction.NONE;
    private boolean blockedHorizontal = false;
    private boolean blockedVertical = false;

    public TankBody(int id, float x, float y, float angle) {
        super(id, x, y, angle, TANK_TEXTURE, WIDTH, HEIGHT);
    }

    public void setMovementDirection(Direction movementDirection) {
        this.movementDirection = movementDirection;
    }

    public void setRotationDirection(Direction rotationDirection) {
        this.rotationDirection = rotationDirection;
    }

    @Override
    public void updatePosition(float deltaTime) {
        if (movementDirection == Direction.FORWARD) {
            y += MOVEMENT_SPEED * Math.sin(Math.toRadians(angle + 90)) * deltaTime;
            x += MOVEMENT_SPEED * Math.cos(Math.toRadians(angle + 90)) * deltaTime;
        }
        if (movementDirection == Direction.BACKWARD) {
            y -= MOVEMENT_SPEED * Math.sin(Math.toRadians(angle + 90)) * deltaTime;
            x -= MOVEMENT_SPEED * Math.cos(Math.toRadians(angle + 90)) * deltaTime;
        }
        if (rotationDirection == Direction.LEFT) {
            angle += ROTATION_SPEED * deltaTime;
        }
        if (rotationDirection == Direction.RIGHT) {
            angle -= ROTATION_SPEED * deltaTime;
        }
        updateSpritePosition();
    }

    // Not the perfect solution to disposing this stuff, but will work for now.
    // AssetManager needs to be added as a better way.
    public static void dispose() {
        TANK_TEXTURE.dispose();
    }

    public Direction getRotationDirection() {
        return rotationDirection;
    }

    public Direction getMovementDirection() {
        return movementDirection;
    }

    public boolean isBlockedHorizontal() {
        return blockedHorizontal;
    }

    public void setBlockedHorizontal(boolean blockedHorizontal) {
        this.blockedHorizontal = blockedHorizontal;
    }

    public boolean isBlockedVertical() {
        return blockedVertical;
    }

    public void setBlockedVertical(boolean blockedVertical) {
        this.blockedVertical = blockedVertical;
    }
}
