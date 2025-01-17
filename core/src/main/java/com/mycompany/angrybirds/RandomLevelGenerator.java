package com.mycompany.angrybirds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomLevelGenerator {
    private Random random = new Random();

    public List<Bird> generateRandomBirds() {
        List<Bird> birds = new ArrayList<>();
        int count = random.nextInt(3) + 3; // Random count of birds between 3 and 5
        for (int i = 0; i < count; i++) {
            int type = random.nextInt(3);
            if (type == 0) birds.add(new RedBird(70 + i * 50, PhysicsEngine.GROUND_LEVEL));
            else if (type == 1) birds.add(new BlueBird(70 + i * 50, PhysicsEngine.GROUND_LEVEL));
            else birds.add(new BlackBird(70 + i * 50, PhysicsEngine.GROUND_LEVEL));
        }
        return birds;
    }

    public List<Pig> generateRandomPigs() {
        List<Pig> pigs = new ArrayList<>();
        int count = random.nextInt(4) + 2; // Random count of pigs between 2 and 5
        for (int i = 0; i < count; i++) {
            pigs.add(new Pig(800 + random.nextInt(200), PhysicsEngine.GROUND_LEVEL + 10, 50, 50));
        }
        return pigs;
    }

    public List<WoodBlock> generateRandomStructures() {
        List<WoodBlock> structures = new ArrayList<>();
        int count = random.nextInt(5) + 3; // Random count of structures between 3 and 7
        for (int i = 0; i < count; i++) {
            structures.add(new WoodBlock(800 + random.nextInt(200), PhysicsEngine.GROUND_LEVEL + 50 * i));
        }
        return structures;
    }
}
