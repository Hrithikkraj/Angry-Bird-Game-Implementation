package com.mycompany.angrybirds;

public class BlackBird extends Bird {
    public BlackBird(float x, float y) {
        super("blackbird.png", x, y, 70, 70);
    }

    @Override
    public void specialAbility() {
        // Explode causing area damage
        System.out.println("Exploding!");
    }
}
