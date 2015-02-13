package com.ericfaerber.gauntletgdk;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.ericfaerber.gauntletgdk.components.AnimationComponent;
import com.ericfaerber.gauntletgdk.components.CameraComponent;
import com.ericfaerber.gauntletgdk.components.CharacterComponent;
import com.ericfaerber.gauntletgdk.components.EnemyComponent;
import com.ericfaerber.gauntletgdk.components.MovementComponent;
import com.ericfaerber.gauntletgdk.components.PlayerComponent;
import com.ericfaerber.gauntletgdk.components.TransformComponent;
import com.ericfaerber.gauntletgdk.components.StateComponent;
import com.ericfaerber.gauntletgdk.components.TextureComponent;
import com.ericfaerber.gauntletgdk.components.TiledMapComponent;
import com.ericfaerber.gauntletgdk.components.ViewableComponent;
import com.ericfaerber.gauntletgdk.components.WallBoundComponent;
import com.ericfaerber.gauntletgdk.systems.RenderingSystem;
import java.util.Iterator;

public class Level {
    final Gauntlet game;
    
    Engine engine;
    int number;
    TiledMapComponent tiledMap;
    
    public Level(final Gauntlet game, Engine engine, int number) {
        this.game = game;
        this.engine = engine;
        this.number = number;
        game.assets.loadMap(number, true);
    }
    
    public void create() {
        prepareMap();
        Entity player = createPlayer();
        createMap(player);
        createEnemies();
    }
    
    private void createMap(Entity target) {
        Entity map = new Entity();
  
        MapProperties mapProperties = tiledMap.map.getProperties();
        
        CameraComponent camera = new CameraComponent();
        camera.camera = engine.getSystem(RenderingSystem.class).getCamera();
        camera.target = target;
        camera.maxWidth = mapProperties.get("width", int.class) * 32 * RenderingSystem.PIXELS_TO_METERS;
        camera.maxHeight = mapProperties.get("height", int.class) * 32 * RenderingSystem.PIXELS_TO_METERS;

        map.add(camera);
        map.add(tiledMap);
        
        engine.addEntity(map);
    }
    
    private void prepareMap() {
        tiledMap = new TiledMapComponent(game.assets.getLevel(number), number);
        
        // hide the collision layer
        MapLayer collisionLayer = tiledMap.map.getLayers().get("collision");
        collisionLayer.setOpacity(0);
    }
    
    private Entity createPlayer() {
        Entity entity = new Entity();
        
        MapLayer objectsLayer = tiledMap.map.getLayers().get("objects");
        MapObject playerObject = objectsLayer.getObjects().get("player");
        MapProperties properties = playerObject.getProperties();
        
        AnimationComponent animation = new AnimationComponent();
        TransformComponent transform = new TransformComponent();
        CharacterComponent character = new CharacterComponent(CharacterComponent.TYPE_WARRIOR);
        PlayerComponent player = new PlayerComponent();
        MovementComponent movement = new MovementComponent();
        StateComponent state = new StateComponent();
        TextureComponent texture = new TextureComponent();
        WallBoundComponent wallBound = new WallBoundComponent((TiledMapTileLayer) tiledMap.map.getLayers().get("collision"));
        
        transform.pos.set(properties.get("x", float.class) * RenderingSystem.PIXELS_TO_METERS, properties.get("y", float.class) * RenderingSystem.PIXELS_TO_METERS, 0);
        
        animation.animations.put(CharacterComponent.STATE_NORTH, Assets.characterNorth[character.type]);
        animation.animations.put(CharacterComponent.STATE_NORTH_EAST, Assets.characterNorthEast[character.type]);
        animation.animations.put(CharacterComponent.STATE_EAST, Assets.characterEast[character.type]);
        animation.animations.put(CharacterComponent.STATE_SOUTH_EAST, Assets.characterSouthEast[character.type]);
        animation.animations.put(CharacterComponent.STATE_SOUTH, Assets.characterSouth[character.type]);
        animation.animations.put(CharacterComponent.STATE_SOUTH_WEST, Assets.characterSouthWest[character.type]);
        animation.animations.put(CharacterComponent.STATE_WEST, Assets.characterWest[character.type]);
        animation.animations.put(CharacterComponent.STATE_NORTH_WEST, Assets.characterNorthWest[character.type]);
        
        int initialState = new Integer(properties.get("state", "0", String.class));
        state.set(initialState);
        
        entity.add(animation);
        entity.add(transform);
        entity.add(character);
        entity.add(player);
        entity.add(movement);
        entity.add(state);
        entity.add(texture);
        entity.add(wallBound);
        
        engine.addEntity(entity);
        
        return entity;
    }
    
    private void createEnemies() {
        MapLayer objectsLayer = tiledMap.map.getLayers().get("objects");
        MapObjects objects = objectsLayer.getObjects();
        Iterator<MapObject> iterator = objects.iterator();
        
        while(iterator.hasNext()) {
            MapObject object = iterator.next();
            if (object.getName().equals("enemy")) {
                createEnemy(object);
            }
        }
    }
    
    private void createEnemy(MapObject enemyObject) {
        Entity entity = new Entity();
        
        MapProperties properties = enemyObject.getProperties();
        
        AnimationComponent animation = new AnimationComponent();
        TransformComponent transform = new TransformComponent();
        EnemyComponent enemy = new EnemyComponent();
        ViewableComponent viewable = new ViewableComponent(engine.getSystem(RenderingSystem.class).getCamera());
        MovementComponent movement = new MovementComponent();
        StateComponent state = new StateComponent();
        TextureComponent texture = new TextureComponent();
        WallBoundComponent wallBound = new WallBoundComponent((TiledMapTileLayer) tiledMap.map.getLayers().get("collision"));
        
        int type = CharacterComponent.TYPE_GHOST;
        String objectType = properties.get("type", String.class);
        
        if (objectType.equals("ghost")) {
            type = CharacterComponent.TYPE_GHOST;
        } else if (objectType.equals("grunt")) {
            type = CharacterComponent.TYPE_GRUNT;
        } else if (objectType.equals("demon")) {
            type = CharacterComponent.TYPE_GRUNT;
        } else if (objectType.equals("sorcerer")) {
            type = CharacterComponent.TYPE_SORCERER;
        } else if (objectType.equals("death")) {
            type = CharacterComponent.TYPE_DEATH;
        }
        
        CharacterComponent character = new CharacterComponent(type);
        
        transform.pos.set(properties.get("x", float.class) * RenderingSystem.PIXELS_TO_METERS, properties.get("y", float.class) * RenderingSystem.PIXELS_TO_METERS, 0);
        
        animation.animations.put(CharacterComponent.STATE_NORTH, Assets.characterNorth[character.type]);
        animation.animations.put(CharacterComponent.STATE_NORTH_EAST, Assets.characterNorthEast[character.type]);
        animation.animations.put(CharacterComponent.STATE_EAST, Assets.characterEast[character.type]);
        animation.animations.put(CharacterComponent.STATE_SOUTH_EAST, Assets.characterSouthEast[character.type]);
        animation.animations.put(CharacterComponent.STATE_SOUTH, Assets.characterSouth[character.type]);
        animation.animations.put(CharacterComponent.STATE_SOUTH_WEST, Assets.characterSouthWest[character.type]);
        animation.animations.put(CharacterComponent.STATE_WEST, Assets.characterWest[character.type]);
        animation.animations.put(CharacterComponent.STATE_NORTH_WEST, Assets.characterNorthWest[character.type]);
        
        int initialState = new Integer(properties.get("state", "0", String.class));
        state.set(initialState);
        
        entity.add(animation);
        entity.add(transform);
        entity.add(character);
        entity.add(enemy);
        entity.add(viewable);
        entity.add(movement);
        entity.add(state);
        entity.add(texture);
        entity.add(wallBound);
        
        engine.addEntity(entity);
    }
}
