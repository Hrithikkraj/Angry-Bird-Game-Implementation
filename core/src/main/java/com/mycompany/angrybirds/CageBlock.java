package com.mycompany.angrybirds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CageBlock {
    private Texture texture;
    private float x, y;

    public CageBlock(float x, float y) {
        this.texture = new Texture("INGAME_BLOCKS_STONE_1.png");  // Replace with actual cage block texture path
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void dispose() {
        texture.dispose();
    }
}
