package com.ericfaerber.gauntletgdk.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.ericfaerber.gauntletgdk.Gauntlet;

public class LoadingScreen  extends ScreenAdapter {
    
    final Gauntlet game;
    
    public LoadingScreen(final Gauntlet game) {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.app.debug(getClass().getSimpleName(), "Show");
        game.assets.load();
    }

    @Override
    public void render(float f) {
        game.clearScreen();
        
        if (game.assetManager.update()) {
            game.assets.init();
//            game.setScreen(new TitleScreen(game));
            game.setScreen(new PlayScreen(game));
        } else {
            float progress = game.assetManager.getProgress();
            game.batch.begin();
            game.font.draw(game.batch, "Loading ... " + (progress * 100) + "%", 100, 150);
            game.batch.end();
        }
    }    
}
