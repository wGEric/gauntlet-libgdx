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
import com.ericfaerber.gauntletgdk.components.CharacterComponent;
import com.ericfaerber.gauntletgdk.components.MovementComponent;
import com.ericfaerber.gauntletgdk.components.StateComponent;
import com.ericfaerber.gauntletgdk.components.TransformComponent;

public class MovementSystem extends IteratingSystem {
    private final ComponentMapper<TransformComponent> pm;
    private final ComponentMapper<MovementComponent> mm;
    private final ComponentMapper<StateComponent> sm;

    public MovementSystem () {
        super(Family.getFor(TransformComponent.class, MovementComponent.class));
        
        pm = ComponentMapper.getFor(TransformComponent.class);
        mm = ComponentMapper.getFor(MovementComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {
        TransformComponent position = pm.get(entity);
        MovementComponent movement = mm.get(entity);
        StateComponent state = sm.get(entity);

        position.pos.x += movement.velocityX * deltaTime;
        position.pos.y += movement.velocityY * deltaTime;
        
        if (state != null) {
            updateState(movement, state);
        }
    }
    
    private void updateState(MovementComponent movement, StateComponent state) {
        int newState = -1;
        
        if (movement.velocityX > 0) {
            if (movement.velocityY > 0) {
                newState = CharacterComponent.STATE_NORTH_EAST;
            } else if (movement.velocityY == 0) {
                newState = CharacterComponent.STATE_EAST;
            } else if (movement.velocityY < 0) {
                newState = CharacterComponent.STATE_SOUTH_EAST;
            }
        } else if (movement.velocityX < 0) {
            if (movement.velocityY > 0) {
               newState = CharacterComponent.STATE_NORTH_WEST;
            } else if (movement.velocityY == 0) {
                newState = CharacterComponent.STATE_WEST;
            } else if (movement.velocityY < 0) {
                newState = CharacterComponent.STATE_SOUTH_WEST;
            }
        } else if (movement.velocityY > 0) {
            newState = CharacterComponent.STATE_NORTH;
        } else if (movement.velocityY < 0) {
            newState = CharacterComponent.STATE_SOUTH;
        }
        
        if (newState != -1 && newState != state.get()) {
            state.set(newState);
        }
    }
}