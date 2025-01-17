package com.mycompany.angrybirds;

import java.io.Serializable;

public class PigState implements Serializable {
    private static final long serialVersionUID = 1L;

    private float x, y; // Position
    private boolean isDead;

    public PigState(float x, float y, boolean isDead) {
        this.x = x;
        this.y = y;
        this.isDead = isDead;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isDead() {
        return isDead;
    }
}
