package ee.taltech.iti0301.hydra.entity.old;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ee.taltech.iti0301.hydra.Hydra;

public class Bullet extends Entity{

    public static final int WIDTH = 1;
    public static final int HEIGHT = 2;
    public float angle;

    float x, y;
    Vector2 vector2;
    public Sprite sprite;
    public boolean remove = false;

    public Bullet (float x, float y, Vector2 vector2) {
        super(x, y);
        SPEED = 30;
        texture = new Texture("bullet.png");
        this.vector2 = vector2;
        this.rotation = calculateAngle(vector2);
        this.sprite = new Sprite(texture);
    }

    public static float calculateAngle(Vector2 vector2) {
        if (vector2.x > vector2.y) {
            vector2.y /= vector2.x;
            vector2.x = 1;
        } else {
            vector2.x /= vector2.y;
            vector2.y = 1;
        }
        return (float)Math.atan2(vector2.y, vector2.x) * MathUtils.radiansToDegrees;
    }

    public void update (float deltaTime) {
        y += SPEED * deltaTime;
        if (y > Hydra.HEIGHT)
            remove = true;
    }

    public void draw (SpriteBatch batch) {
        batch.draw(sprite, x, y, 1, 1, WIDTH, HEIGHT, 0.5f, 0.5f, 0);
    }

}