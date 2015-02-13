package com.ericfaerber.gauntletgdk.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.ericfaerber.gauntletgdk.Gauntlet;
import com.ericfaerber.gauntletgdk.Level;
import com.ericfaerber.gauntletgdk.systems.AnimationSystem;
import com.ericfaerber.gauntletgdk.systems.CameraSystem;
import com.ericfaerber.gauntletgdk.systems.EnemySystem;
import com.ericfaerber.gauntletgdk.systems.MapSystem;
import com.ericfaerber.gauntletgdk.systems.MovementSystem;
import com.ericfaerber.gauntletgdk.systems.PlayerSystem;
import com.ericfaerber.gauntletgdk.systems.RenderingSystem;
import com.ericfaerber.gauntletgdk.systems.ViewableSystem;
import com.ericfaerber.gauntletgdk.systems.WallBoundSystem;

public class PlayScreen extends ScreenAdapter {
    
    final Gauntlet game;
    private final Engine engine;
    
    public PlayScreen(final Gauntlet game) {
        this.game = game;
        engine = new Engine();
        
        engine.addSystem(new CameraSystem());
        engine.addSystem(new MapSystem(game.batch));
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new ViewableSystem());
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new EnemySystem(engine));
        engine.addSystem(new WallBoundSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderingSystem(game.batch));
        
        Level level = new Level(this.game, engine, 0);
        level.create();
    }

    @Override
    public void show() { 
        Gdx.app.debug(getClass().getSimpleName(), "Show");
    }
    
    public void update(float deltaTime) {
        game.clearScreen();
        engine.update(deltaTime);
    }
    
    @Override
    public void render(float delta) {
        update(delta);
    }
}