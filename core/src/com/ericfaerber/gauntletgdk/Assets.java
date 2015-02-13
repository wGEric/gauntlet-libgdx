package com.ericfaerber.gauntletgdk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.ericfaerber.gauntletgdk.components.CharacterComponent;

public class Assets {
    
    private static final String[] LOAD_TEXTURES = {
        "images/entities_32x32.png"
    };
    private static final String[] LOAD_MUSIC = {};
    private static final String[] LOAD_SOUNDS = {};
    
    static TextureRegion[][] entities;
    
    static Animation[] characterNorth = new Animation[5];
    static Animation[] characterNorthEast = new Animation[5];
    static Animation[] characterEast = new Animation[5];
    static Animation[] characterSouthEast = new Animation[5];
    static Animation[] characterSouth = new Animation[5];
    static Animation[] characterSouthWest = new Animation[5];
    static Animation[] characterWest = new Animation[5];
    static Animation[] characterNorthWest = new Animation[5];
    static Animation[] characterBullet = new Animation[5];
    
    final AssetManager manager;
    
    public Assets(final AssetManager manager) {
        this.manager = manager;
 
        // Allow loading tmx files
        this.manager.setLoader(TiledMap.class, new TmxMapLoader());
    }
    
    public <T> T get(String asset) {
        return manager.get(asset);
    }
    
    public TiledMap getLevel(int level) {
        String map = getMapFileName(level);
        return get(map);
    }
    
    public void load() {
        loadTextures();
        loadMusic();
        loadSounds();
    }
    
    public void init() {
        Texture allEntities = get("images/entities_32x32.png");
        entities = TextureRegion.split(allEntities, 32, 32);
        
        initCharacters();
    }
    
    public void loadMap(int level) {
        loadMap(level, false);
    }
    
    public void loadMap(int level, boolean blocking) {
        String map = getMapFileName(level);
                
        Gdx.app.debug(getClass().getSimpleName(), "Loading map " + map);
        
        manager.load(map, TiledMap.class);
        
        if (blocking) {
            manager.finishLoading();
        }
    }

    private String getMapFileName(int level) {
        String map = "maps/level" + level + ".tmx";
        return map;
    }
    
    private void loadTextures() {
        for (String texture : LOAD_TEXTURES) {
            Gdx.app.debug(getClass().getSimpleName(), "Loading texture: " + texture);
            manager.load(texture, Texture.class);
        }
    }
    
    private void loadMusic() {
        for (String music : LOAD_MUSIC) {
            Gdx.app.debug(getClass().getSimpleName(), "Loading music: " + music);
            manager.load(music, Music.class);
        }
    }
    
    private void loadSounds() {
        for (String sound : LOAD_SOUNDS) {
            Gdx.app.debug(getClass().getSimpleName(), "Loading sound: " + sound);
            manager.load(sound, Sound.class);
        }
    }
    
    private void initCharacters() {
        setCharacterAnimations(CharacterComponent.TYPE_WARRIOR, 0);
        setCharacterAnimations(CharacterComponent.TYPE_VALKYIRE, 1);
        setCharacterAnimations(CharacterComponent.TYPE_WIZARD, 2);
        setCharacterAnimations(CharacterComponent.TYPE_ELF, 3);
        setCharacterAnimations(CharacterComponent.TYPE_GHOST, 4);
    }

    private void setCharacterAnimations(int type, int row) {
        characterNorth[type] = new Animation(0.2f, getCharacterRegions(row, 0), Animation.PlayMode.LOOP);
        characterNorthEast[type] = new Animation(0.2f, getCharacterRegions(row, 1), Animation.PlayMode.LOOP);
        characterEast[type] = new Animation(0.2f, getCharacterRegions(row, 2), Animation.PlayMode.LOOP);
        characterSouthEast[type] = new Animation(0.2f, getCharacterRegions(row, 3), Animation.PlayMode.LOOP);
        characterSouth[type] = new Animation(0.2f, getCharacterRegions(row, 4), Animation.PlayMode.LOOP);
        characterSouthWest[type] = new Animation(0.2f, getCharacterRegions(row, 5), Animation.PlayMode.LOOP);
        characterWest[type] = new Animation(0.2f, getCharacterRegions(row, 6), Animation.PlayMode.LOOP);
        characterNorthWest[type] = new Animation(0.2f, getCharacterRegions(row, 7), Animation.PlayMode.LOOP);
        
        // bullet/attack
        Array<TextureRegion> bulletRegions = new Array(TextureRegion.class);
        for(int i = 0; i <= 8; i++) {
            bulletRegions.add(entities[row][i + 25]);
        }
        characterBullet[type] = new Animation(0.2f, bulletRegions, Animation.PlayMode.LOOP);
    }
    
    private Array<TextureRegion> getCharacterRegions(int row, int col) {
        Array<TextureRegion> regions = new Array(TextureRegion.class);
        regions.addAll(new TextureRegion[]{ entities[row][col], entities[row][col + 8], entities[row][col], entities[row][col + 16] });
        return regions;
    }
}
