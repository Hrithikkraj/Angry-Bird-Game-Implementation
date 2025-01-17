package com.mycompany.angrybirds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class StoneCage {
    private StoneBlock[] stoneBlocks;
    private boolean isBroken;
    private static final float LEVEL3_GROUND_LEVEL = 130f; // Assuming GROUND_LEVEL as the bottom y-position of the bottom row blocks

    public StoneCage(float x, float y) {
        stoneBlocks = new StoneBlock[6];

        // Decrease the initial y position of the stone blocks
        stoneBlocks[0] = new StoneBlock(x, LEVEL3_GROUND_LEVEL);           // Bottom Left
        stoneBlocks[1] = new StoneBlock(x + 50f, LEVEL3_GROUND_LEVEL);     // Bottom Right
        stoneBlocks[2] = new StoneBlock(x, y + 150f);                   // Middle Left (Lowered from 210f)
        stoneBlocks[3] = new StoneBlock(x + 50f, y + 150f);             // Middle Right (Lowered from 210f)
        stoneBlocks[4] = new StoneBlock(x, y + 300f);                   // Top Left (Lowered from 350f)
        stoneBlocks[5] = new StoneBlock(x + 50f, y + 300f);             // Top Right (Lowered from 350f)

        this.isBroken = false;
    }

    public void render(SpriteBatch batch) {
        for (StoneBlock block : stoneBlocks) {
            if (block != null) {
                block.render(batch);
            }
        }
    }

    public void update(float delta) {
        if (isBroken) {
            applyGravity(delta);
            for (StoneBlock block : stoneBlocks) {
                if (block != null) {
                    block.update(delta);
                }
            }
        }
    }

    private void applyGravity(float delta) {
        for (StoneBlock block : stoneBlocks) {
            if (block != null && block.isFalling()) {
                Vector2 position = block.getPosition();
                if (position.y > StoneBlock.LEVEL3_GROUND_LEVEL) {
                    Vector2 velocity = block.getVelocity();
                    velocity.y += StoneBlock.GRAVITY * delta;
                    block.setVelocity(velocity);
                } else {
                    // Make sure blocks don't go below ground level
                    position.y = StoneBlock.LEVEL3_GROUND_LEVEL;
                    block.setVelocity(new Vector2(0, 0));
                    block.stopFalling();
                }
            }
        }
    }

    public boolean checkCollision(Bird bird) {
        Rectangle birdBounds = bird.getBounds();
        for (StoneBlock block : stoneBlocks) {
            if (block != null && block.getBounds().overlaps(birdBounds)) {
                tumbleBlocks();
                return true;
            }
        }
        return false;
    }

    public Rectangle getBounds() {
        float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE, maxY = Float.MIN_VALUE;
        for (StoneBlock block : stoneBlocks) {
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

    public void tumbleBlocks() {
        if (!isBroken) {
            isBroken = true;
            for (StoneBlock block : stoneBlocks) {
                if (block != null) {
                    Vector2 tumbleVelocity = getTumbleVelocity(block);
                    block.setVelocity(tumbleVelocity);
                    block.startFalling();
                }
            }
        }
    }

    private Vector2 getTumbleVelocity(StoneBlock block) {
        Vector2 tumbleVelocity = new Vector2();
        float speedFactor = MathUtils.random(0.5f, 1.0f);

        float angle = MathUtils.random(MathUtils.PI / 4, 3 * MathUtils.PI / 4); // Randomize direction for a more staggered effect
        tumbleVelocity.x = speedFactor * MathUtils.cos(angle);
        tumbleVelocity.y = speedFactor * MathUtils.sin(angle);

        return tumbleVelocity;
    }

    public void dispose() {
        for (StoneBlock block : stoneBlocks) {
            if (block != null) {
                block.dispose();
            }
        }
    }

    public void setPosition(float x, float y) {
        float blockWidth = stoneBlocks[0].getWidth();
        float blockHeight = stoneBlocks[0].getHeight();

        stoneBlocks[0].setPosition(x, LEVEL3_GROUND_LEVEL);      // Bottom Left
        stoneBlocks[1].setPosition(x + 50f, LEVEL3_GROUND_LEVEL); // Bottom Right
        stoneBlocks[2].setPosition(x, y + 170f);              // Middle Left (Lowered from 210f)
        stoneBlocks[3].setPosition(x + 50f, y + 170f);        // Middle Right (Lowered from 210f)
        stoneBlocks[4].setPosition(x, y + 310f);              // Top Left (Lowered from 350f)
        stoneBlocks[5].setPosition(x + 50f, y + 310f);        // Top Right (Lowered from 350f)
    }
}
