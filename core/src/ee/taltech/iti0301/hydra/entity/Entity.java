package ee.taltech.iti0301.hydra.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {

    protected float x;
    protected float y;
    protected float rotation;
    Sprite sprite;

    public Entity(float x, float y, float rotation, Texture texture, float width, float height) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;

        sprite = new Sprite(texture);
        sprite.setSize(width, height);
        sprite.setOriginCenter();
        updateSpritePosition();
    }

    protected void updateSpritePosition() {
        sprite.setPosition(x, y);
        sprite.setRotation(rotation);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

}
