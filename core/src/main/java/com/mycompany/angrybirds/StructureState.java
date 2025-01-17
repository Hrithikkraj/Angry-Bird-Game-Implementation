package com.mycompany.angrybirds;

import java.io.Serializable;

public class StructureState implements Serializable {
    private static final long serialVersionUID = 1L;

    private float x, y; // Position
    private int hitPoints; // Remaining hit points of the structure

    public StructureState(float x, float y, int hitPoints) {
        this.x = x;
        this.y = y;
        this.hitPoints = hitPoints;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getHitPoints() {
        return hitPoints;
    }
}
