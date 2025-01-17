package com.mycompany.angrybirds;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private int currentLevel;
    private List<List<Bird>> levelsBirds;
    private List<List<Pig>> levelsPigs;
    private List<List<WoodBlock>> levelsStructures;

    public LevelManager() {
        this.currentLevel = 1;
        levelsBirds = new ArrayList<>();
        levelsPigs = new ArrayList<>();
        levelsStructures = new ArrayList<>();

        // Initialize levels
        initLevels();
    }

    private void initLevels() {
        // Example Level 1
        List<Bird> level1Birds = List.of(new RedBird(70, PhysicsEngine.GROUND_LEVEL),
            new BlueBird(150, PhysicsEngine.GROUND_LEVEL),
            new BlackBird(350, PhysicsEngine.GROUND_LEVEL));

        List<Pig> level1Pigs = List.of(new Pig(850, PhysicsEngine.GROUND_LEVEL + 10, 50, 50));

        List<WoodBlock> level1Structures = List.of(new WoodBlock(800, PhysicsEngine.GROUND_LEVEL + 50),
            new WoodBlock(830, PhysicsEngine.GROUND_LEVEL + 100));

        levelsBirds.add(level1Birds);
        levelsPigs.add(level1Pigs);
        levelsStructures.add(level1Structures);

        // Add more levels similarly
    }

    public List<Bird> getBirdsForLevel(int level) {
        return levelsBirds.get(level - 1);
    }

    public List<Pig> getPigsForLevel(int level) {
        return levelsPigs.get(level - 1);
    }

    public List<WoodBlock> getStructuresForLevel(int level) {
        return levelsStructures.get(level - 1);
    }

    public boolean hasNextLevel() {
        return currentLevel < levelsBirds.size();
    }

    public void nextLevel() {
        if (hasNextLevel()) {
            currentLevel++;
        }
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}
