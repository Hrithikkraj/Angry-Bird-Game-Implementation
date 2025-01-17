package com.mycompany.angrybirds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Cage {
    private WoodBlock[] woodBlocks;
    private boolean isBroken;

    public Cage(float x, float y) {
        woodBlocks = new WoodBlock[6];

        // Initialize wood blocks
        woodBlocks[0] = new WoodBlock(x, y);                     // Bottom Left
        woodBlocks[1] = new WoodBlock(x + 50f, y);               // Bottom Right
        woodBlocks[2] = new WoodBlock(x, y + 160f);              // Middle Left
        woodBlocks[3] = new WoodBlock(x + 50f, y + 160f);        // Middle Right
        woodBlocks[4] = new WoodBlock(x, y + 320f);              // Top Left
        woodBlocks[5] = new WoodBlock(x + 50f, y + 320f);        // Top Right

        this.isBroken = false;
    }

    public void render(SpriteBatch batch) {
        for (WoodBlock block : woodBlocks) {
            if (block != null) {
                block.render(batch);
            }
        }
    }

    public void update(float delta) {
        if (isBroken) {
            for (WoodBlock block : woodBlocks) {
                if (block != null) {
                    block.update(delta);
                }
            }
        }
    }

    public boolean checkCollision(Bird bird) {
        Rectangle birdBounds = bird.getBounds();
        for (WoodBlock block : woodBlocks) {
            if (block != null && block.getBounds().overlaps(birdBounds)) {
                scatterBlocks(bird);
                return true;
            }
        }
        return false;
    }

    public Rectangle getBounds() {
        // Calculate bounds dynamically
        float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE, maxY = Float.MIN_VALUE;
        for (WoodBlock block : woodBlocks) {
            if (block != null) {
                Vector2 pos = block.getPosition();
                minX = Math.min(minX, pos.x);
                minY = Math.min(minY, pos.y);
                maxX = Math.max(maxX, pos.x + block.getWidth());
                maxY = Math.max(maxY, pos.y + block.getHeight());
            }
        }
        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    public void scatterBlocks(Bird bird) {
        if (!isBroken) {
            isBroken = true;

            // Simulate blast effect
            for (WoodBlock block : woodBlocks) {
                if (block != null) {
                    Vector2 impactVelocity = getImpactVelocity(bird, block);
                    block.setVelocity(impactVelocity);
                    block.startFalling();
                }
            }
        }
    }

    private Vector2 getImpactVelocity(Bird bird, WoodBlock block) {
        Vector2 impactVelocity = new Vector2();
        float speedFactor = MathUtils.random(1.5f, 2.5f); // Increase the speed factor for dramatic effect

        // Calculate direction based on the difference between bird and block positions
        float deltaX = block.getPosition().x - bird.getPosition().x;
        float deltaY = block.getPosition().y - bird.getPosition().y;

        float angle = MathUtils.atan2(deltaY, deltaX);
        impactVelocity.x = speedFactor * bird.getVelocity().len() * MathUtils.cos(angle);
        impactVelocity.y = speedFactor * bird.getVelocity().len() * MathUtils.sin(angle);

        return impactVelocity;
    }

    public void dispose() {
        for (WoodBlock block : woodBlocks) {
            if (block != null) {
                block.dispose();
            }
        }
    }

    public void setPosition(float x, float y) {
        // Adjust every wood block's position relative to the new cage position
        float blockWidth = woodBlocks[0].getWidth();
        float blockHeight = woodBlocks[0].getHeight();

        woodBlocks[0].setPosition(x, y);                         // Bottom Left
        woodBlocks[1].setPosition(x + 50f, y);                   // Bottom Right
        woodBlocks[2].setPosition(x, y + blockHeight);           // Middle Left
        woodBlocks[3].setPosition(x + 50f, y + blockHeight);     // Middle Right
        woodBlocks[4].setPosition(x, y + 2 * blockHeight);       // Top Left
        woodBlocks[5].setPosition(x + 50f, y + 2 * blockHeight); // Top Right
    }
}
