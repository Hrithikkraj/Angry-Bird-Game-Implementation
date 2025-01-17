package com.mycompany.angrybirds;

public class Vector {
    private float magnitude;
    private float angle;

    public Vector() {
        this(0, 0);
    }

    public Vector(float magnitude, float angle) {
        this.magnitude = magnitude;
        this.angle = angle;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public static Vector addVectors(Vector v1, Vector v2) {
        float x = (float)(Math.sin(v1.getAngle()) * v1.getMagnitude() + Math.sin(v2.getAngle()) * v2.getMagnitude());
        float y = (float)(Math.cos(v1.getAngle()) * v1.getMagnitude() + Math.cos(v2.getAngle()) * v2.getMagnitude());

        float newAngle = (float)(0.5 * Math.PI - Math.atan2(y, x));
        float newMagnitude = (float)Math.hypot(x, y);

        return new Vector(newMagnitude, newAngle);
    }
}
