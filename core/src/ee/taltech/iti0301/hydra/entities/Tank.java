package ee.taltech.iti0301.hydra.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tank {

    public float x;
    public float y;
    public float rotation;
    public boolean tankMoved = false;
    public static final Texture tempTankTexture = new Texture("prototank.png");
    public static final float SPEED = 20;
    public static final float SPEEDROTATION = 80;
    TextureRegion tankTexture;


    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
        tankTexture = new TextureRegion(tempTankTexture);
        rotation = 0;
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            rotation -= SPEEDROTATION * Gdx.graphics.getDeltaTime();
            tankMoved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= SPEED * Math.sin(Math.toRadians(rotation)) * Gdx.graphics.getDeltaTime();
            x -= SPEED * Math.cos(Math.toRadians(rotation)) * Gdx.graphics.getDeltaTime();
            tankMoved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            rotation += SPEEDROTATION * Gdx.graphics.getDeltaTime();
            tankMoved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += SPEED * Math.sin(Math.toRadians(rotation)) * Gdx.graphics.getDeltaTime();
            x += SPEED * Math.cos(Math.toRadians(rotation)) * Gdx.graphics.getDeltaTime();
            tankMoved = true;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(tankTexture, x - 1.5f, y - 1.5f, 3, 3, 6, 6,
                1, 1, rotation - 90);
    }
}
