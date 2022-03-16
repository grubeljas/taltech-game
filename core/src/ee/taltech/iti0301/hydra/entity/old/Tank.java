package ee.taltech.iti0301.hydra.entity.old;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tank extends Entity {

    public boolean tankMoved = false;
    public static final float SPEEDROTATION = 80;
    TextureRegion tankTexture;

    public Tank(int x, int y) {
        super(x, y);
        SPEED = 20;
        texture = new Texture("prototank.png");
        tankTexture = new TextureRegion(texture);
        rotation = 0;
    }

    public void update(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            rotation -= SPEEDROTATION * deltaTime;
            tankMoved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= SPEED * Math.sin(Math.toRadians(rotation)) * deltaTime;
            x -= SPEED * Math.cos(Math.toRadians(rotation)) * deltaTime;
            tankMoved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            rotation += SPEEDROTATION * deltaTime;
            tankMoved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += SPEED * Math.sin(Math.toRadians(rotation)) * deltaTime;
            x += SPEED * Math.cos(Math.toRadians(rotation)) * deltaTime;
            tankMoved = true;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(tankTexture, x - 1.5f, y - 1.5f, 3, 3, 6, 6,
                1, 1, rotation - 90);
    }
}
