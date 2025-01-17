package com.mycompany.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Bird {
    protected Texture texture;
    protected Vector2 position;
    protected Vector2 velocity;
    private Vector2 originalPosition; // Save the original position for reset
    protected float width;
    protected float height;
    private boolean isInFlight = false; // Tracks if the bird is currently in flight
    private static final float GRAVITY = -9.8f; // Gravity effect

    public Bird(String texturePath, float x, float y, float width, float height) {
        this.texture = new Texture(texturePath);
        this.position = new Vector2(x, y);
        this.originalPosition = new Vector2(x, y); // Save the original position
        this.width = width;
        this.height = height;
        this.velocity = new Vector2(0, 0);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, width, height);
    }

    public void update(float delta, float groundLevel) {
        if (isInFlight) {
            velocity.add(0, GRAVITY * delta); // Apply gravity only if in flight
            position.add(velocity.x * delta, velocity.y * delta);

            // Check for ground contact
            if (position.y < groundLevel) {
                position.y = groundLevel;
                isInFlight = false; // Stop the bird when it hits the ground
                velocity.y = 0; // Stop vertical movement
            }
        }
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, width, height);
    }

    public void dispose() {
        texture.dispose();
    }

    public abstract void specialAbility();

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void saveOriginalPosition() {
        originalPosition.set(position);
    }

    public void resetPosition() {
        position.set(originalPosition);
        resetFlight(); // Ensure the flight is reset upon resetting the position
    }

    public void resetFlight() {
        velocity.set(0, 0);
        isInFlight = false; // Reset the flight status
    }

    public void setInFlight(boolean isInFlight) {
        this.isInFlight = isInFlight; // Set the flight status
    }

    public boolean isInFlight() {
        return isInFlight;
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public void setVelocity(float v, float v1) {
        this.velocity.set(v, v1);
    }

    public void fallToGround() {
        isInFlight = true; // Set in flight
        velocity.set(0, GRAVITY); // Use gravity to simulate falling
    }

    // Add any other required methods for the Bird subclass implementations
}
