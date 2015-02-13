/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericfaerber.gauntletgdk.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.ericfaerber.gauntletgdk.components.CharacterComponent;
import com.ericfaerber.gauntletgdk.components.MovementComponent;
import com.ericfaerber.gauntletgdk.components.TransformComponent;
import com.ericfaerber.gauntletgdk.components.WallBoundComponent;

/**
 *
 * @author eric
 */
public class WallBoundSystem extends IteratingSystem {
    
    private final ComponentMapper<WallBoundComponent> wbm;
    private final ComponentMapper<TransformComponent> tm;
    private final ComponentMapper<MovementComponent> mm;
    
    private final Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject () {
            return new Rectangle();
        }
    };

    public WallBoundSystem () {
        super(Family.getFor(WallBoundComponent.class, MovementComponent.class, TransformComponent.class));
        
        wbm = ComponentMapper.getFor(WallBoundComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
        mm = ComponentMapper.getFor(MovementComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent position = tm.get(entity);
        MovementComponent movement = mm.get(entity);
        WallBoundComponent wallbounds = wbm.get(entity);
        
        float startX = position.pos.x + movement.velocityX * deltaTime;
        float startY = position.pos.y + movement.velocityY * deltaTime;
        int endX = (int) Math.ceil(startX + CharacterComponent.CHARACTER_WIDTH);
        int endY = (int) Math.ceil(startY + CharacterComponent.CHARACTER_HEIGHT);
        Rectangle characterRect = rectPool.obtain();
        Array<Rectangle> tiles = getTiles(wallbounds.collisionLayer, (int) startX, (int) startY, endX, endY);
        
        // check x
        characterRect.set(startX + 0.1f,  position.pos.y, CharacterComponent.CHARACTER_WIDTH - 0.2f, CharacterComponent.CHARACTER_HEIGHT - 0.3f);
        for(Rectangle tile : tiles) {
            if (characterRect.overlaps(tile)) {
                movement.velocityX = movement.velocityX % 1; // put character next to wall
            }
        }
        
        // check y
        characterRect.set(position.pos.x + 0.1f,  startY, CharacterComponent.CHARACTER_WIDTH - 0.2f, CharacterComponent.CHARACTER_HEIGHT - 0.3f);
        for(Rectangle tile : tiles) {
            if (characterRect.overlaps(tile)) {
                movement.velocityY = movement.velocityY % 1; // put character next to wall
            }
        }
    }
    
    private Array<Rectangle> getTiles(TiledMapTileLayer collisionLayer, int startX, int startY, int endX, int endY) {
        Array<Rectangle> tiles = new Array<Rectangle>();
        
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                Cell cell = collisionLayer.getCell(x, y);
                if (cell != null) {
                    TiledMapTile tile = cell.getTile();
                    MapProperties tileProperties = tile.getProperties();
                    
                    if (tileProperties.containsKey("solid")) {
                        Rectangle rect = rectPool.obtain();
                        rect.set(x, y, 1, 1);
                        tiles.add(rect);
                    }
                }
            }
        }
        
        return tiles;
    }
}
