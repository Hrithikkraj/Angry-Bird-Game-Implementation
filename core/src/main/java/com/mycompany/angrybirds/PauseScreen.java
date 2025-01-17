package com.mycompany.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.IOException;

public class PauseScreen implements Screen {
    private final MyAngryBirdGame game;
    private SpriteBatch batch;
    private Stage stage;
    private Texture background;

    public PauseScreen(MyAngryBirdGame game) {
        this.game = game;
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        background = new Texture("bg001.png");

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Resume Button
        TextButton resumeButton = new TextButton("Resume", skin);
        setupButton(resumeButton, "Resume", 0, () -> game.setScreen(new GameScreen(game)));

        // Save Game Button
        TextButton saveButton = new TextButton("Save Game", skin);
        setupButton(saveButton, "Save Game", -120, this::saveGame);

        // Exit Button
        TextButton exitButton = new TextButton("Exit", skin);
        setupButton(exitButton, "Exit", -240, () -> game.setScreen(new LevelScreen(game)));

        stage.addActor(resumeButton);
        stage.addActor(saveButton);
        stage.addActor(exitButton);

        Gdx.input.setInputProcessor(stage);
    }

    private void setupButton(TextButton button, String label, float yOffset, Runnable action) {
        float buttonWidth = 300f;
        float buttonHeight = 100f;

        button.setSize(buttonWidth, buttonHeight);
        button.setPosition(
            Gdx.graphics.getWidth() / 2 - buttonWidth / 2,
            Gdx.graphics.getHeight() / 2 - buttonHeight / 2 + yOffset
        );

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action.run();
            }
        });

        stage.addActor(button);
    }

    private void saveGame() {
        try {
            // Get the current game state from the GameStateManager
            GameState currentGameState = GameStateManager.getCurrentGameState();
            if (currentGameState == null) {
                System.err.println("No current game state available to save.");
                return;
            }

            // Save the game state to a file using SaveLoadManager
            SaveLoadManager.saveGame(currentGameState);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());
        }
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        background.dispose();
    }
}
