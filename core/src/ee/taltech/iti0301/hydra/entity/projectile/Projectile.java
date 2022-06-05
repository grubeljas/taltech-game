package ee.taltech.iti0301.hydra.entity.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import ee.taltech.iti0301.hydra.entity.Entity;
import ee.taltech.iti0301.hydra.entity.MovableEntity;
import ee.taltech.iti0301.hydra.entity.FakeEntity;

public class Projectile extends Entity implements MovableEntity {

    private static final float WIDTH = 0.5f;
    private static final float HEIGHT = 0.5f;
    private static final int SPEED = 50;
    private float live = 10;
    private static final Texture BULLET_TEXTURE = new Texture("bullet.png");
    
    private Rectangle projectileRectangle;

    public Projectile(int id, float x, float y, float rotation) {
        super(id, x, y, rotation, BULLET_TEXTURE, WIDTH, HEIGHT);
        projectileRectangle = new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
    public Projectile(FakeEntity fakeEntity) {
        super(fakeEntity.getId(), fakeEntity.getX(), fakeEntity.getY(), fakeEntity.getRotation(),
                BULLET_TEXTURE, WIDTH, HEIGHT);
        projectileRectangle = new Rectangle(fakeEntity.getX(), fakeEntity.getY(), WIDTH, HEIGHT);
    }

    @Override
    public void updatePosition(float deltaTime) {
        live -= deltaTime;
        y += SPEED * Math.sin(Math.toRadians(angle + 90)) * deltaTime;
        x += SPEED * Math.cos(Math.toRadians(angle + 90)) * deltaTime;
        updateSpritePosition();
    }
    
    public float getLive() {
        return live;
    }
    
    // Not the perfect solution to disposing this stuff, but will work for now.
    // AssetManager needs to be added as a better way.
    public static void dispose() {
        BULLET_TEXTURE.dispose();
    }
}
