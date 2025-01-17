package com.mycompany.angrybirds;

import com.badlogic.gdx.Game;

public class MyAngryBirdGame extends Game {
    @Override
    public void create() {
        setScreen(new HomeScreen(this));
    }
}