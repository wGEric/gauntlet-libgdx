package com.ericfaerber.gauntletgdk.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.ericfaerber.gauntletgdk.Gauntlet;

public class TitleScreen  extends ScreenAdapter {

    final Gauntlet game;

    public TitleScreen(final Gauntlet game) {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.app.debug(getClass().getSimpleName(), "Show");
    }

    @Override
    public void render(float f) {
        handleInput();
        
        game.clearScreen();
        
        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Drop!!! ", 100, 150);
        game.font.draw(game.batch, "Push any key to begin!", 100, 100);
        game.batch.end();
    }
    
    private void handleInput() {
        if (Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
            game.setScreen(new PlayScreen(game));
        }
    }
}
