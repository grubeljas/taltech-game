package ee.taltech.iti0301.hydra.entity.tank;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import ee.taltech.iti0301.hydra.entity.Entity;
import ee.taltech.iti0301.hydra.entity.MovableEntity;
import ee.taltech.iti0301.hydra.entity.fakeEntity;

public class TankBody extends Entity implements MovableEntity {

    private static final float WIDTH = 6f;
    private static final float HEIGHT = 6f;
    private static final Texture TANK_TEXTURE = new Texture("tankbody.png");
    private static final int MOVEMENT_SPEED = 20;
    private static final int ROTATION_SPEED = 80;

    private final TankTurret turret;

    public enum Direction {
        RIGHT, LEFT, FORWARD, BACKWARD,NONE
    }

    private Direction rotationDirection = Direction.NONE;
    private Direction movementDirection = Direction.NONE;

    public TankBody(int id, float x, float y, float angle) {
        super(id, x, y, angle, TANK_TEXTURE, WIDTH, HEIGHT);
        turret = new TankTurret(id, x, y, angle);
    }

    public TankBody(fakeEntity fakeTank, fakeEntity fakeEntity) {
        super(fakeTank.getId(), fakeTank.getX(), fakeTank.getY(), fakeTank.getRotation(),
                TANK_TEXTURE, WIDTH, HEIGHT);
        System.out.println("CREATE NEW TURRET "+fakeEntity.getId() +" "+ fakeTank.getX()+" "+ fakeTank.getY()+" "+ fakeEntity.getRotation());
        this.turret = new TankTurret(fakeEntity.getId(), fakeEntity.getX(), fakeEntity.getY(), fakeEntity.getRotation());
    }

    public void setMovementDirection(Direction movementDirection) {
        this.movementDirection = movementDirection;
    }

    public void setRotationDirection(Direction rotationDirection) {
        this.rotationDirection = rotationDirection;
    }

    public void setTurretAngle(Vector3 mouseLocation) {
        mouseLocation = mouseLocation.sub(x, y, 0);
        float angleTurret = (float) (Math.atan2(mouseLocation.x, mouseLocation.y)*180/Math.PI);
        turret.setAngle(-angleTurret);
    }

    @Override
    public void updatePosition(float deltaTime) {
        if (movementDirection == Direction.FORWARD) {
            y += MOVEMENT_SPEED * Math.sin(Math.toRadians(angle + 90)) * deltaTime;
            x += MOVEMENT_SPEED * Math.cos(Math.toRadians(angle + 90)) * deltaTime;
            turret.setX(x);
            turret.setY(y);
        }
        else if (movementDirection == Direction.BACKWARD) {
            y -= MOVEMENT_SPEED * Math.sin(Math.toRadians(angle + 90)) * deltaTime;
            x -= MOVEMENT_SPEED * Math.cos(Math.toRadians(angle + 90)) * deltaTime;
            turret.setX(x);
            turret.setY(y);
        }
        if (rotationDirection == Direction.LEFT) {
            angle += ROTATION_SPEED * deltaTime;
        }
        if (rotationDirection == Direction.RIGHT) {
            angle -= ROTATION_SPEED * deltaTime;
        }
        updateSpritePosition();
        turret.updatePosition(deltaTime);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        turret.draw(batch);
    }

    public TankTurret getTurret() {
        return turret;
    }

    // Not the perfect solution to disposing this stuff, but will work for now.
    // AssetManager needs to be added as a better way.
    public static void dispose() {
        TANK_TEXTURE.dispose();
    }
}
