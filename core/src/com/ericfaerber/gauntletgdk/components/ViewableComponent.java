package com.ericfaerber.gauntletgdk.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class ViewableComponent extends Component {
    public boolean isViewable = false;
    public OrthographicCamera camera;
    
    public ViewableComponent(OrthographicCamera camera) {
        this.camera = camera;
    }
}
