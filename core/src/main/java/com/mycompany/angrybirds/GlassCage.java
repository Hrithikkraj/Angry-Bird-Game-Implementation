package com.mycompany.angrybirds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GlassCage {
    private GlassBlock[] glassBlocks;
    private boolean isBroken;

    public GlassCage(float x, float y) {
        glassBlocks = new GlassBlock[6];

        // Initialize glass blocks
        glassBlocks[0] = new GlassBlock(x, y);                     // Bottom Left
        glassBlocks[1] = new GlassBlock(x + 50f, y);               // Bottom Right
        glassBlocks[2] = new GlassBlock(x, y + 160f);              // Middle Left
        glassBlocks[3] = new GlassBlock(x + 50f, y + 160f);        // Middle Right
        glassBlocks[4] = new GlassBlock(x, y + 320f);              // Top Left
        glassBlocks[5] = new GlassBlock(x + 50f, y + 320f);        // Top Right

        this.isBroken = false;
    }

    public void render(SpriteBatch batch) {
        for (GlassBlock block : glassBlocks) {
            if (block != null) {
                block.render(batch);
            }
        }
    }

    public void update(float delta) {
        if (isBroken) {
            for (GlassBlock block : glassBlocks) {
                if (block != null) {
                    block.update(delta);
                }
            }
        }
    }

    public boolean checkCollision(Bird bird) {
        Rectangle birdBounds = bird.getBounds();
        for (GlassBlock block : glassBlocks) {
            if (block != null && block.getBounds().overlaps(birdBounds)) {
                scatterBlocks(bird);
                return true;
            }
        }
        return false;
    }

    public void breakDown() {
        if (!isBroken) {
            isBroken = true;
        }
    }

    public void dispose() {
        for (GlassBlock block : glassBlocks) {
            if (block != null) {
                block.dispose();
            }
        }
    }

    public Rectangle getBounds() {
        // Calculate bounds dynamically
        float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE, maxY = Float.MIN_VALUE;
        for (GlassBlock block : glassBlocks) {
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
            for (GlassBlock block : glassBlocks) {
                if (block != null) {
                    Vector2 impactVelocity = getImpactVelocity(bird, block);
                    block.setVelocity(impactVelocity);
                    block.startFalling();
                }
            }
        }
    }

    private Vector2 getImpactVelocity(Bird bird, GlassBlock block) {
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

    public void setPosition(float x, float y) {
        float blockWidth = glassBlocks[0].getWidth();
        float blockHeight = glassBlocks[0].getHeight();

        glassBlocks[0].setPosition(x, y);                     // Bottom Left
        glassBlocks[1].setPosition(x + 50f, y);               // Bottom Right
        glassBlocks[2].setPosition(x, y + blockHeight);       // Middle Left
        glassBlocks[3].setPosition(x + 50f, y + blockHeight); // Middle Right
        glassBlocks[4].setPosition(x, y + 2 * blockHeight);   // Top Left
        glassBlocks[5].setPosition(x + 50f, y + 2 * blockHeight); // Top Right
    }
}
