package com.mycompany.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class NewCage {
    private Texture horizontalTexture; // Texture for horizontal walls
    private Texture verticalTexture;   // Texture for vertical walls
    private Rectangle bounds;          // Bounds of the cage (outer square)
    private static final float SCALE_FACTOR = 0.3f; // Scale factor for wall texture

    private List<ShatterPiece> shatterPieces; // List to store individual shattered pieces
    private boolean shattered = false; // Track if the cage is shattered

    public NewCage(float x, float y, float size) {
        // Load the textures for the horizontal and vertical walls
        this.horizontalTexture = new Texture("wall_horizontal.png");
        this.verticalTexture = new Texture("wall_vertical.png");

        // Initialize the bounds of the square cage
        this.bounds = new Rectangle(x, y, size, size);

        // Initialize shatter pieces
        this.shatterPieces = new ArrayList<>();
    }

    // Render the NewCage or its shattered pieces
    public void render(SpriteBatch batch) {
        if (!shattered) {
            // Render the cage normally
            float x = bounds.x;
            float y = bounds.y;
            float size = bounds.width; // Total square size
            float cellSize = size / 3; // Divide the square into 3x3 smaller cells

            // Scale wall textures
            float horizontalTileWidth = horizontalTexture.getWidth() * SCALE_FACTOR;
            float horizontalTileHeight = horizontalTexture.getHeight() * SCALE_FACTOR;
            float verticalTileWidth = verticalTexture.getWidth() * SCALE_FACTOR;
            float verticalTileHeight = verticalTexture.getHeight() * SCALE_FACTOR;

            // Draw the outer horizontal walls (top and bottom)
            for (float i = x; i < x + size; i += horizontalTileWidth) {
                batch.draw(horizontalTexture, i, y + size - horizontalTileHeight, horizontalTileWidth, horizontalTileHeight); // Top wall
                batch.draw(horizontalTexture, i, y, horizontalTileWidth, horizontalTileHeight); // Bottom wall
            }

            // Draw the outer vertical walls (left and right)
            for (float i = y; i < y + size; i += verticalTileHeight) {
                batch.draw(verticalTexture, x, i, verticalTileWidth, verticalTileHeight); // Left wall
                batch.draw(verticalTexture, x + size - verticalTileWidth, i, verticalTileWidth, verticalTileHeight); // Right wall
            }

            // Draw the inner horizontal grid lines
            for (int row = 1; row < 3; row++) {
                float lineY = y + row * cellSize; // Position of the inner horizontal line
                for (float i = x; i < x + size; i += horizontalTileWidth) {
                    batch.draw(horizontalTexture, i, lineY - horizontalTileHeight / 2, horizontalTileWidth, horizontalTileHeight);
                }
            }

            // Draw the inner vertical grid lines
            for (int col = 1; col < 3; col++) {
                float lineX = x + col * cellSize; // Position of the inner vertical line
                for (float i = y; i < y + size; i += verticalTileHeight) {
                    batch.draw(verticalTexture, lineX - verticalTileWidth / 2, i, verticalTileWidth, verticalTileHeight);
                }
            }
        } else {
            // Render shattered pieces
            for (ShatterPiece piece : shatterPieces) {
                piece.render(batch);
            }
        }
    }

    // Logic to scatter and fall pieces after collision
    public void scatterBlocks(Bird bird) {
        if (!shattered) {
            shattered = true;

            float size = bounds.width;
            float horizontalTileWidth = horizontalTexture.getWidth() * SCALE_FACTOR;
            float horizontalTileHeight = horizontalTexture.getHeight() * SCALE_FACTOR;
            float verticalTileWidth = verticalTexture.getWidth() * SCALE_FACTOR;
            float verticalTileHeight = verticalTexture.getHeight() * SCALE_FACTOR;

            // Create horizontal pieces
            for (float i = bounds.x; i < bounds.x + size; i += horizontalTileWidth) {
                shatterPieces.add(new ShatterPiece(horizontalTexture, i, bounds.y, horizontalTileWidth, horizontalTileHeight, new Vector2((float) Math.random() * 2 - 1, -2)));
                shatterPieces.add(new ShatterPiece(horizontalTexture, i, bounds.y + size - horizontalTileHeight, horizontalTileWidth, horizontalTileHeight, new Vector2((float) Math.random() * 2 - 1, -2)));
            }

            // Create vertical pieces
            for (float i = bounds.y; i < bounds.y + size; i += verticalTileHeight) {
                shatterPieces.add(new ShatterPiece(verticalTexture, bounds.x, i, verticalTileWidth, verticalTileHeight, new Vector2(-1, (float) Math.random() * -2)));
                shatterPieces.add(new ShatterPiece(verticalTexture, bounds.x + size - verticalTileWidth, i, verticalTileWidth, verticalTileHeight, new Vector2(1, (float) Math.random() * -2)));
            }
        }
    }

    // Update logic for shattered pieces
    public void update(float delta) {
        if (shattered) {
            for (ShatterPiece piece : shatterPieces) {
                piece.update(delta);
            }
        }
    }

    // Check for collision with a bird
    public boolean checkCollision(Bird bird) {
        return bounds.overlaps(bird.getBounds());
    }

    // Dispose resources
    public void dispose() {
        horizontalTexture.dispose();
        verticalTexture.dispose();
        for (ShatterPiece piece : shatterPieces) {
            piece.dispose();
        }
    }

    // Inner class for individual shatter pieces
    private class ShatterPiece {
        private Texture texture;
        private float x, y, width, height;
        private Vector2 velocity;

        public ShatterPiece(Texture texture, float x, float y, float width, float height, Vector2 velocity) {
            this.texture = texture;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.velocity = velocity;
        }

        public void update(float delta) {
            x += velocity.x * delta * 100;
            y += velocity.y * delta * 100;

            // Simulate gravity
            velocity.y -= 9.8f * delta;
        }

        public void render(SpriteBatch batch) {
            batch.draw(texture, x, y, width, height);
        }

        public void dispose() {
            texture.dispose();
        }
    }
}
