package com.mycompany.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class StoneBlock {
    public static final float LEVEL3_GROUND_LEVEL = 130f; // Define the ground level for the stone blocks
    private Vector2 position;
    private Vector2 velocity;
    private float width;
    private float height;
    private float rotation;
    private boolean falling;
    private Texture texture;
    public static final float GRAVITY = -9.8f;

    public StoneBlock(float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2();
        width = 30f;
        height = 150f;  // Increased height from 20f to 40f
        rotation = 0f;
        falling = false;
        texture = new Texture("stone_block.png"); // Assume there's an appropriate texture
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture,
            position.x, position.y, // position
            width / 2, height / 2,  // origin
            width, height,          // dimensions
            1.0f, 1.0f,             // scale
            rotation,               // rotation
            0, 0, texture.getWidth(), texture.getHeight(), false, false // texture source
        );
    }

    public void update(float delta) {
        if (falling) {
            position.add(velocity.cpy().scl(delta));
            rotation += velocity.x * delta * 10; // Add rotation proportional to x-velocity

            // Check if the block has hit the ground level
            if (position.y <= LEVEL3_GROUND_LEVEL) {
                position.y = LEVEL3_GROUND_LEVEL; // Set the position to the ground level
                velocity.setZero(); // Stop the block's movement
                falling = false; // Set falling to false
                rotation = 0f; // Ensure the block ends up horizontal (corrected from 90f)
            }
        }
    }

    public void startFalling() {
        falling = true;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public void dispose() {
        texture.dispose();
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, width, height);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void stopFalling() {
        falling = false;
        velocity.setZero();
        rotation = 0f; // Ensure the block is horizontal when stopped (corrected from 90f)
    }
}
