package com.mycompany.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class WoodBlock implements Disposable {
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private float width;
    private float height;
    private float rotation;
    private float rotationSpeed;
    private boolean isFalling;
    private static final float GRAVITY = -9.8f;
    public static final float GROUND_LEVEL_2 = 100; // Example ground level for level 2

    public WoodBlock(float x, float y) {
        this.texture = new Texture("wood_block.png"); // Adjust texture path accordingly
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.width = 25f;
        this.height = 170f;
        this.rotation = 0f;
        this.rotationSpeed = 0f;
        this.isFalling = false;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, width / 2, height / 2, width, height,
            1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public void update(float delta) {
        if (isFalling) {
            velocity.y += GRAVITY * delta; // Apply gravity
            position.add(velocity.x * delta, velocity.y * delta); // Update position
            rotation += rotationSpeed * delta; // Update rotation

            // Stop when it hits the ground level 2
            if (position.y < GROUND_LEVEL_2) {
                position.y = GROUND_LEVEL_2;
                isFalling = false;
                velocity.y = 0;
                velocity.x = 0; // Reset horizontal velocity when hitting the ground
                rotationSpeed = 0; // Stop rotation after hitting the ground
                rotation = 0; // Ensure the block is horizontally flat
            }
        }
    }

    public void startFalling() {
        isFalling = true;
    }

    public void setVelocity(float vx, float vy) {
        this.velocity.set(vx, vy);
        this.isFalling = true;
    }

    public void setVelocity(Vector2 impactVelocity) {
        this.velocity.set(impactVelocity);
        this.isFalling = true;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, width, height);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
