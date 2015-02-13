package com.ericfaerber.gauntletgdk.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ericfaerber.gauntletgdk.components.CameraComponent;
import com.ericfaerber.gauntletgdk.components.TiledMapComponent;

public class MapSystem extends IteratingSystem {

    private final ComponentMapper<TiledMapComponent> tm;
    private final ComponentMapper<CameraComponent> cm;
    private final SpriteBatch batch;
    
    public MapSystem(SpriteBatch batch) {
        super(Family.getFor(TiledMapComponent.class));
        
        this.batch = batch;
        
        tm = ComponentMapper.getFor(TiledMapComponent.class);
        cm = ComponentMapper.getFor(CameraComponent.class);
    }
    
    @Override
    protected void processEntity(Entity entity, float f) {        
        TiledMapComponent tiledMap = tm.get(entity);
        CameraComponent cam = cm.get(entity);
        
        if (tiledMap.map == null || tiledMap.renderer == null) {
            return;
        }
        
        batch.setProjectionMatrix(cam.camera.combined);
        
        tiledMap.renderer.setView(cam.camera);
        tiledMap.renderer.render();
    }
    
}
