package com.mycompany.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class Pig {
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private float width; // Assuming width is required
    private float height; // Assuming height is required
    private boolean isDead;

    public Pig(float x, float y, float width, float height) {
        this.texture = new Texture("pig.png");  // Replace with actual pig texture path
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.width = width;
        this.height = height;
        this.isDead = false;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, width, height);
    }

    public void update(float delta) {
        if (!isDead) {
            velocity.add(0, PhysicsEngine.GRAVITY * delta);
            position.add(velocity.x * delta, velocity.y * delta);

            // Check for ground contact
            if (position.y < PhysicsEngine.GROUND_LEVEL) {
                position.y = PhysicsEngine.GROUND_LEVEL;
                velocity.y = 0; // Stop vertical movement
            }
        }
    }

    public void dispose() {
        texture.dispose();
    }

    public void die() {
        isDead = true;
        // Change texture or any other relevant state changes for pig death
    }

    public boolean isDead() {
        return isDead;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, width, height);
    }
}
