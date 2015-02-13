package com.ericfaerber.gauntletgdk;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ericfaerber.gauntletgdk.screens.LoadingScreen;

public class Gauntlet extends Game {
    
    static final int LOG_LEVEL = Application.LOG_DEBUG;
    
    public BitmapFont font;
    public SpriteBatch batch;
    public SpriteBatch guiBatch;
    public AssetManager assetManager;
    public Assets assets;

    @Override
    public void create () {
        Gdx.app.setLogLevel(LOG_LEVEL);
        
        batch = new SpriteBatch();
        guiBatch = new SpriteBatch();
        font = new BitmapFont();
        assetManager = new AssetManager();
        assets = new Assets(assetManager);
                
        this.setScreen(new LoadingScreen(this));
    }

    @Override
    public void render () {
        super.render();
    }
    
    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
        assetManager.dispose();
    }
    
    public void clearScreen() {
        Gdx.graphics.getGL20().glClearColor(0, 0, 0, 0);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }
}
