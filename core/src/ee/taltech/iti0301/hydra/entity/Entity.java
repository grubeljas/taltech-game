package ee.taltech.iti0301.hydra.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {

    protected int id;
    protected float x;
    protected float y;
    protected float angle;
    Sprite sprite;

    public Entity(int id, float x, float y, float angle, Texture texture, float width, float height) {

        this.id = id;

        // Entity coordinates (x, y), represent it's center, and angle (rotation)
        this.x = x;
        this.y = y;
        this.angle = angle;

        sprite = new Sprite(texture);       // Creating sprite from texture provided
        sprite.setSize(width, height);      // Setting sprite size
        sprite.setOriginCenter();           // Setting the rotation point to the sprite center
        updateSpritePosition();             // Updating sprite position
    }

    protected void updateSpritePosition() {
        sprite.setCenter(x, y);             // Setting sprite center to be on the coordinates
        sprite.setRotation(angle);       // Setting sprite angle (0 - UP, increases counterclockwise)
    }

    // Method used by render in GameScreen to draw the sprite
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    // GETTERS for coordinates and angle (rotation)

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRotation() {
        return angle;
    }

    public int getId() {
        return id;
    }
    
    public Sprite getSprite() {
        return sprite;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
