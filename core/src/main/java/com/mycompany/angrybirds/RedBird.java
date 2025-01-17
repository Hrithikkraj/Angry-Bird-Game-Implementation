package com.mycompany.angrybirds;

public class RedBird extends Bird {
    public RedBird(float x, float y) {
        super("redbird.png", x, y, 60, 60);
    }

    @Override
    public void specialAbility() {
        // Increase speed significantly
        this.velocity.mulAdd(velocity, 2.0f);
    }
}
