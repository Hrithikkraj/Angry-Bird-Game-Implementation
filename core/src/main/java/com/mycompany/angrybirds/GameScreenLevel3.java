package com.mycompany.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreenLevel3 implements Screen {
    protected final MyAngryBirdGame game;
    protected SpriteBatch batch;
    protected ShapeRenderer shapeRenderer;
    protected Stage stage;
    protected Texture background;
    protected boolean isPaused;
    protected boolean birdDragged;
    protected Catapult catapult;
    protected StoneCage cage;
    protected RedBird redBird;
    protected BlueBird blueBird;
    protected BlackBird blackBird;
    protected Bird selectedBird;
    protected Vector2 dragStart;
    protected Vector2 dragEnd;
    private boolean showPopup = false; // To track if the popup is shown

    private static final float LEVEL3_GROUND_LEVEL = 130f;

    public GameScreenLevel3(MyAngryBirdGame game) {
        this.game = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        stage = new Stage(new ScreenViewport());
        background = new Texture("bg003.png");
        isPaused = false;
        birdDragged = false;

        setupGameObjects();
        setupPauseButton();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector2 touchPos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
                handleBirdSelection(touchPos);
                dragStart = touchPos;
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (selectedBird != null) {
                    birdDragged = true;
                    dragEnd = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
                    selectedBird.setPosition(dragEnd.x, dragEnd.y);
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (birdDragged) {
                    launchBird();
                    birdDragged = false;
                }
                return true;
            }
        });
    }

    private void handleBirdSelection(Vector2 touchPos) {
        if (redBird.getBounds().contains(touchPos)) {
            selectBird(redBird);
        } else if (blueBird.getBounds().contains(touchPos)) {
            selectBird(blueBird);
        } else if (blackBird.getBounds().contains(touchPos)) {
            selectBird(blackBird);
        }
    }

    private void selectBird(Bird bird) {
        if (selectedBird != null && selectedBird != bird) {
            selectedBird.setInFlight(false);
        }
        selectedBird = bird;
        loadBirdOnCatapult(selectedBird);
    }

    private void loadBirdOnCatapult(Bird bird) {
        Vector2 catapultTop = new Vector2(
            catapult.getPosition().x + catapult.getWidth() / 2 - bird.getWidth() / 2,
            catapult.getPosition().y + catapult.getHeight() - bird.getHeight() / 2
        );
        bird.setPosition(catapultTop.x, catapultTop.y);
        bird.setInFlight(false);
    }

    private void launchBird() {
        if (selectedBird != null && dragStart != null && dragEnd != null) {
            Vector2 launchVector = new Vector2(dragStart.x - dragEnd.x, dragStart.y - dragEnd.y);
            selectedBird.setVelocity(launchVector.x * 2, launchVector.y * 2);
            selectedBird.setInFlight(true);
        }
    }

    protected void setupGameObjects() {
        catapult = new Catapult(400, LEVEL3_GROUND_LEVEL, 70, 130);

        float birdGap = 10;
        float birdWidth = 70;
        float birdHeight = 70;

        redBird = new RedBird(100, LEVEL3_GROUND_LEVEL);
        blueBird = new BlueBird(190, LEVEL3_GROUND_LEVEL);
        blackBird = new BlackBird(280, LEVEL3_GROUND_LEVEL);

        setBirdPositionAndSize(redBird, 100, LEVEL3_GROUND_LEVEL, birdWidth, birdHeight);
        setBirdPositionAndSize(blueBird, 190, LEVEL3_GROUND_LEVEL, birdWidth, birdHeight);
        setBirdPositionAndSize(blackBird, 280, LEVEL3_GROUND_LEVEL, birdWidth, birdHeight + 20);

        cage = new StoneCage(900, LEVEL3_GROUND_LEVEL);
    }

    protected void setBirdPositionAndSize(Bird bird, float x, float y, float width, float height) {
        bird.setPosition(x, y);
        bird.setSize(width, height);
    }

    private void setupPauseButton() {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        TextButton pauseButton = new TextButton("Pause", skin);
        pauseButton.setSize(150f, 50f);
        pauseButton.setPosition(Gdx.graphics.getWidth() - 160, Gdx.graphics.getHeight() - 60);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = !isPaused;
                if (isPaused) {
                    game.setScreen(new PauseScreen(game));
                } else {
                    game.setScreen(GameScreenLevel3.this);
                }
            }
        });
        stage.addActor(pauseButton);
    }

    private void showWinPopup() {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // "YOU WON" Label
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.fontColor = Color.BLACK;
        Label winLabel = new Label("YOU WON", labelStyle);
        winLabel.setFontScale(4f);
        winLabel.setPosition(
            Gdx.graphics.getWidth() / 2f - winLabel.getPrefWidth() * 2f,
            Gdx.graphics.getHeight() / 2f + 50
        );

        // "HOME" Button
        TextButton homeButton = new TextButton("HOME", skin);
        homeButton.setSize(200f, 50f);
        homeButton.setPosition(
            Gdx.graphics.getWidth() / 2f - homeButton.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - 50
        );
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomeScreen(game)); // Transition to HomeScreen
            }
        });

        stage.addActor(winLabel);
        stage.addActor(homeButton);

        // Ensure the stage processes input for the popup
        Gdx.input.setInputProcessor(stage);
    }

    private void checkCollisionWithCage(Bird bird, StoneCage cage) {
        if (cage.checkCollision(bird)) {
            bird.setInFlight(false);
            bird.setVelocity(0, 0);
            cage.tumbleBlocks();

            if (!showPopup) {
                showPopup = true;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        showWinPopup();
                    }
                }, 1); // Show popup after 1 second
            }
        }
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.2f, 0.3f, 1f);

        if (!isPaused) {
            updateGameObjects(delta);
        }

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderGameObjects();
        batch.end();

        renderElasticBands();

        stage.act(delta);
        stage.draw();
    }

    private void updateGameObjects(float delta) {
        if (selectedBird != null && selectedBird.isInFlight()) {
            selectedBird.update(delta, LEVEL3_GROUND_LEVEL);
            checkCollisionWithCage(selectedBird, cage);
        }
        redBird.update(delta, LEVEL3_GROUND_LEVEL);
        blueBird.update(delta, LEVEL3_GROUND_LEVEL);
        blackBird.update(delta, LEVEL3_GROUND_LEVEL);

        cage.update(delta);
    }

    private void renderGameObjects() {
        catapult.render(batch);
        cage.render(batch);
        redBird.render(batch);
        blueBird.render(batch);
        blackBird.render(batch);
    }

    private void renderElasticBands() {
        if (selectedBird != null && birdDragged) {
            Vector2 catapultTop = new Vector2(
                catapult.getPosition().x + catapult.getWidth() / 2,
                catapult.getPosition().y + catapult.getHeight()
            );

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0.55f, 0.27f, 0.07f, 1f);
            shapeRenderer.rectLine(catapultTop, selectedBird.getPosition(), 5);
            shapeRenderer.end();
        }
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
        shapeRenderer.dispose();
        stage.dispose();
        background.dispose();
        catapult.dispose();
        cage.dispose();
        redBird.dispose();
        blueBird.dispose();
        blackBird.dispose();
    }
}
