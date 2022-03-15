package ee.taltech.iti0301.hydra.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {

    public float x;
    public float y;
    public static int SPEED;
    public static Texture texture;
    public float rotation;

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    abstract public void update (float deltaTime);

    abstract public void draw(SpriteBatch batch);
}
