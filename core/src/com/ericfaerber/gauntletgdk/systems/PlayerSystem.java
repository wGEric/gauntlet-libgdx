package com.ericfaerber.gauntletgdk.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.ericfaerber.gauntletgdk.components.CharacterComponent;
import com.ericfaerber.gauntletgdk.components.MovementComponent;
import com.ericfaerber.gauntletgdk.components.PlayerComponent;

public class PlayerSystem extends IteratingSystem {
    private final ComponentMapper<PlayerComponent> pm;
    private final ComponentMapper<MovementComponent> mm;
    private final ComponentMapper<CharacterComponent> cm;
    
    public PlayerSystem() {
        super(Family.getFor(PlayerComponent.class, MovementComponent.class, CharacterComponent.class));

        pm = ComponentMapper.getFor(PlayerComponent.class);
        mm = ComponentMapper.getFor(MovementComponent.class);
        cm = ComponentMapper.getFor(CharacterComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movement = mm.get(entity);
        CharacterComponent character = cm.get(entity);
        
        // reset to 0, nothing pressed
        movement.velocityX = 0;
        movement.velocityY = 0;
        
        float touchX = 0;
        float touchY = 0;
        if (Gdx.input.isTouched()) {
            touchX = Gdx.input.getX();
            touchY = Gdx.input.getY();
        } 
      
        // check if keys have been pressed
        if (Gdx.input.isKeyPressed(Keys.UP) || (touchY < 125 && touchY > 0)) {
            movement.velocityY = character.speed;
        }
        
        if (Gdx.input.isKeyPressed(Keys.DOWN) || touchY > 550) {
            movement.velocityY = -1 * character.speed;
        }
        
        if (Gdx.input.isKeyPressed(Keys.LEFT) || (touchX < 125 && touchX > 0)) {
            movement.velocityX = -1 * character.speed;
        }
        
        if (Gdx.input.isKeyPressed(Keys.RIGHT) || touchX > 1000) {
            movement.velocityX = character.speed;
        }
    }
}
