package ee.taltech.iti0301.hydra.entity.tank;

import com.badlogic.gdx.graphics.Texture;
import ee.taltech.iti0301.hydra.entity.Entity;
import ee.taltech.iti0301.hydra.entity.MovableEntity;

public class TankTurret extends Entity implements MovableEntity {

    private static final float WIDTH = 3f;
    private static final float HEIGHT = 3f;
    private static final Texture TURRET_TEXTURE = new Texture("turret.png");

    public TankTurret(int id, float x, float y, float angle) {
        super(id, x, y, angle, TURRET_TEXTURE, WIDTH, HEIGHT);
    }

    @Override
    public void updatePosition(float deltaTime) {
        updateSpritePosition();
    }
}
