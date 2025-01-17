package com.mycompany.angrybirds;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PhysicsEngine {
    public static final float GRAVITY = -9.8f;
    public static final float GROUND_LEVEL = 130f;

    public static void updateBird(Bird bird, float delta) {
        if (bird.isInFlight()) {
            Vector2 velocity = bird.getVelocity();
            velocity.add(0, GRAVITY * delta);
            bird.setVelocity(velocity);

            Vector2 position = bird.getPosition();
            position.add(velocity.x * delta, velocity.y * delta);
            bird.setPosition(position);

            // Check for ground contact
            if (position.y < GROUND_LEVEL) {
                position.y = GROUND_LEVEL;
                bird.setInFlight(false); // Stop the bird when it hits the ground
                bird.setVelocity(new Vector2(0, 0)); // Stop vertical movement
            }
        }
    }

    public static void updatePig(Pig pig, float delta) {
        if (!pig.isDead()) {
            Vector2 velocity = pig.getVelocity();
            velocity.add(0, GRAVITY * delta);
            pig.setVelocity(velocity);

            Vector2 position = pig.getPosition();
            position.add(velocity.x * delta, velocity.y * delta);
            pig.setPosition(position);

            // Check for ground contact
            if (position.y < GROUND_LEVEL) {
                position.y = GROUND_LEVEL;
                pig.setVelocity(new Vector2(0, 0)); // Stop vertical movement
            }
        }
    }

    public static void handleCollisions(Bird bird, Pig pig) {
        Rectangle birdBounds = bird.getBounds();
        Rectangle pigBounds = pig.getBounds();

        if (birdBounds.overlaps(pigBounds) && !pig.isDead()) {
            pig.die();
            bird.setVelocity(new Vector2(0, 0)); // Assuming the bird would stop on collision
            bird.setInFlight(false);
        }
    }
}
