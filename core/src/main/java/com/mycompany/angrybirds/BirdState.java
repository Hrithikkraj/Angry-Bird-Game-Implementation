package com.mycompany.angrybirds;

import java.io.Serializable;

public class BirdState implements Serializable {
    private static final long serialVersionUID = 1L;

    private float x, y; // Position
    private float velocityX, velocityY; // Velocity
    private String type; // Type of bird (e.g., "Red", "Blue", "Black")
    private boolean isInFlight;

    public BirdState(float x, float y, float velocityX, float velocityY, String type, boolean isInFlight) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.type = type;
        this.isInFlight = isInFlight;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public String getType() {
        return type;
    }

    public boolean isInFlight() {
        return isInFlight;
    }
}
