package com.mycompany.angrybirds;

public class GameStateManager {
    private static GameState currentGameState;

    public static GameState getCurrentGameState() {
        return currentGameState;
    }

    public static void setCurrentGameState(GameState state) {
        currentGameState = state;
    }
}
