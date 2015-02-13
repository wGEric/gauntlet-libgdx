package com.ericfaerber.gauntletgdk.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraComponent extends Component {
    public Entity target;
    public OrthographicCamera camera;
    public int tileSize = 15;
    public float maxWidth = 0;
    public float maxHeight = 0;
}
