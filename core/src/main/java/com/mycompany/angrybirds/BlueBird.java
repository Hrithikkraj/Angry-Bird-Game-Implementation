package com.mycompany.angrybirds;

public class BlueBird extends Bird {
    public BlueBird(float x, float y) {
        super("bluebird.png", x, y, 50, 50);
    }

    @Override
    public void specialAbility() {
        // Split into three smaller birds (This example won't actually create new birds,
        // but represents where you would handle this logic.)
        System.out.println("Splitting into three!");
    }
}
