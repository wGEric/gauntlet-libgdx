package com.ericfaerber.gauntletgdk.components;

import com.badlogic.ashley.core.Component;

public class MovementComponent extends Component {
    public float velocityX = 0;
    public float velocityY = 0;

    public MovementComponent(float velocityX, float velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
    
    public MovementComponent() {
        // do nothing
    }
}
