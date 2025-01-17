package com.mycompany.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class HomeScreen implements Screen {
    private final MyAngryBirdGame game;
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private Texture playTexture;
    private Texture exitTexture;
    private Texture birdsTexture;  // New texture for birds.png
    private ImageButton playButton;
    private ImageButton exitButton;

    private float birdsX, birdsY, birdsWidth, birdsHeight; // Variables to store birds.png position and size

    public HomeScreen(MyAngryBirdGame game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture("bg001.png");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        playTexture = new Texture("exit_button copy.png");
        exitTexture = new Texture("exit_button copy1.png");
        birdsTexture = new Texture("birds.png");  // Load birds.png

        playButton = new ImageButton(new TextureRegionDrawable(playTexture));
        exitButton = new ImageButton(new TextureRegionDrawable(exitTexture));

        // Set button sizes (adjust the width and height as needed for your images)
        playButton.setSize(400, 300);
        exitButton.setSize(400, 300);

        // Set button positions (centered horizontally and vertically with offsets)
        playButton.setPosition((Gdx.graphics.getWidth() - playButton.getWidth()) / 2, (Gdx.graphics.getHeight() / 2) - 200);
        exitButton.setPosition((Gdx.graphics.getWidth() - exitButton.getWidth()) / 2, (Gdx.graphics.getHeight() / 2) - 300);

        // Add listeners for button actions
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Add buttons to stage
        stage.addActor(playButton);
        stage.addActor(exitButton);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();

        // Draw the background to fill the screen (this is now scaled in resize)
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw the birds.png image with updated size and position
        batch.draw(birdsTexture, birdsX, birdsY, birdsWidth, birdsHeight);

        batch.end();

        // Render stage with buttons
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

    // Adjust birds.png size and position
    birdsWidth = width / 2;  // Scale birds to half the screen width
    birdsHeight = birdsWidth * (birdsTexture.getHeight() / (float) birdsTexture.getWidth());  // Maintain aspect ratio
    birdsX = (width - birdsWidth) / 2;
    birdsY = (height - birdsHeight) / 2 + 100;  // Adjust Y position to move it above the buttons

    // Adjust button positions based on screen size
    float buttonWidth = playButton.getWidth();
    float buttonHeight = playButton.getHeight();
    float buttonsY = birdsY - buttonHeight - 20; // Position buttons just below the birds texture with a 20px gap

    playButton.setPosition((width / 2) - buttonWidth - 10, buttonsY); // Position play button to the left
    exitButton.setPosition((width / 2) + 10, buttonsY); // Position exit button to the right
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
        playTexture.dispose();
        exitTexture.dispose();
        birdsTexture.dispose();
    }
}
