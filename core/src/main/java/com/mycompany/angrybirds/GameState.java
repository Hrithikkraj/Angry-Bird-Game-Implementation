package com.mycompany.angrybirds;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L; // Add serialVersionUID for versioning

    private List<BirdState> birds; // Store bird states instead of full objects
    private List<PigState> pigs; // Store pig states
    private List<StructureState> structures; // Store structure states
    private int currentLevel;

    public GameState(List<BirdState> birds, List<PigState> pigs, List<StructureState> structures, int currentLevel) {
        this.birds = birds;
        this.pigs = pigs;
        this.structures = structures;
        this.currentLevel = currentLevel;
    }

    public List<BirdState> getBirds() {
        return birds;
    }

    public List<PigState> getPigs() {
        return pigs;
    }

    public List<StructureState> getStructures() {
        return structures;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}
