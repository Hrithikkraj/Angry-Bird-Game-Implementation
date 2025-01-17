package com.mycompany.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mycompany.angrybirds.MyAngryBirdGame;

public class LevelScreen implements Screen {
    private final MyAngryBirdGame game;
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private TextButton level1Button;
    private TextButton level2Button;
    private TextButton level3Button;
    private TextButton backButton;
    private Label level1Label;
    private Skin skin;

    public LevelScreen(MyAngryBirdGame game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture("lvl.png");  // Full screen background image
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load the skin for LibGDX buttons and labels
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Create TextButtons using the skin
        level1Button = new TextButton("Level 1", skin);
        level1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to the game screen for Level 1
                game.setScreen(new GameScreen(game));
            }
        });

        level2Button = new TextButton("Level 2", skin);
        level2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to the game screen for Level 2
                game.setScreen(new GameScreenLevel2(game));
            }
        });

        level3Button = new TextButton("Level 3", skin);
        level3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to the game screen for Level 3
                game.setScreen(new GameScreenLevel3(game));
            }
        });

        backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Go back to the home screen
                game.setScreen(new HomeScreen(game));
            }
        });

        // Set button sizes
        float buttonWidth = 400f;
        float buttonHeight = 100f;  // Make it more appropriate for text buttons
        level1Button.setSize(buttonWidth, buttonHeight);
        level2Button.setSize(buttonWidth, buttonHeight);
        level3Button.setSize(buttonWidth, buttonHeight);
        backButton.setSize(buttonWidth, buttonHeight);

        // Create a label above the Level 1 button (optional, but you may not need this anymore)
        level1Label = new Label("Select a Level", skin);

        // Add buttons and label to the stage
        stage.addActor(level1Button);
        stage.addActor(level2Button);
        stage.addActor(level3Button);
        stage.addActor(backButton);
        stage.addActor(level1Label);  // Add label to the stage
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();

        // Draw the background to fill the screen
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        // Center the buttons relative to the screen size
        float buttonWidth = level1Button.getWidth();
        float buttonHeight = level1Button.getHeight();

        // Dynamically calculate the center positions
        float centerX = (width - buttonWidth) / 2;
        float centerY = height / 2;

        // Set positions for the buttons (centered vertically and spaced apart)
        level1Button.setPosition(centerX, centerY + buttonHeight + 5);  // Reduced space above the center
        level2Button.setPosition(centerX, centerY);                     // Button at the center
        level3Button.setPosition(centerX, centerY - buttonHeight - 5);  // Reduced space below the center
        backButton.setPosition(centerX, centerY - 2 * (buttonHeight + 5)); // Reduced space for back button

        // Set position for the label above the level1Button
        level1Label.setPosition(centerX + (buttonWidth / 2 - level1Label.getWidth() / 2), level1Button.getY() + buttonHeight + 20);  // Position it above the Level 1 button
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        stage.dispose();
        skin.dispose();
    }
}
