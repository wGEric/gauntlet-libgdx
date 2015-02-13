package com.ericfaerber.gauntletgdk.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledMapComponent extends Component {
    public TiledMap map;
    public OrthogonalTiledMapRenderer renderer;
    public int level;
    
    public TiledMapComponent(TiledMap map, int level) {
        this.map = map;
        this.level = level;
        this.renderer = new OrthogonalTiledMapRenderer(map, 1f / 32f);
    }
}
