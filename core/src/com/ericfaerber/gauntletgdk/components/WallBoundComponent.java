package com.ericfaerber.gauntletgdk.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class WallBoundComponent extends Component {
    public TiledMapTileLayer collisionLayer;
    
    public WallBoundComponent(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }
}
