package com.mycompany.angrybirds;

import java.io.*;

public class SaveLoadManager {
    private static final String SAVE_FILE = "game_save.ser";

    public static void saveGame(GameState gameState) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(gameState);
        }
    }

    public static GameState loadGame() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (GameState) ois.readObject();
        }
    }
}
