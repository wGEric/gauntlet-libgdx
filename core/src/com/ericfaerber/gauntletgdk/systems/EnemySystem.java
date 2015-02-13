package com.ericfaerber.gauntletgdk.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.ericfaerber.gauntletgdk.components.CharacterComponent;
import com.ericfaerber.gauntletgdk.components.EnemyComponent;
import com.ericfaerber.gauntletgdk.components.MovementComponent;
import com.ericfaerber.gauntletgdk.components.PlayerComponent;
import com.ericfaerber.gauntletgdk.components.TransformComponent;
import com.ericfaerber.gauntletgdk.components.ViewableComponent;

public class EnemySystem extends IteratingSystem {
    private final ComponentMapper<PlayerComponent> pm;
    private final ComponentMapper<EnemyComponent> em;
    private final ComponentMapper<MovementComponent> mm;
    private final ComponentMapper<CharacterComponent> cm;
    private final ComponentMapper<TransformComponent> tm;
    private final ComponentMapper<ViewableComponent> vm;
    private final Family playerFamily;
    private final Engine engine;
    
    public EnemySystem(Engine engine) {
        super(Family.getFor(EnemyComponent.class,
                MovementComponent.class, 
                TransformComponent.class,
                CharacterComponent.class));

        this.engine = engine;
        
        pm = ComponentMapper.getFor(PlayerComponent.class);
        em = ComponentMapper.getFor(EnemyComponent.class);
        mm = ComponentMapper.getFor(MovementComponent.class);
        cm = ComponentMapper.getFor(CharacterComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
        vm = ComponentMapper.getFor(ViewableComponent.class);
        
        playerFamily = Family.getFor(PlayerComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float timeDelta) {
        MovementComponent movement = mm.get(entity);
        CharacterComponent character = cm.get(entity);
        TransformComponent position = tm.get(entity);
        ViewableComponent viewable = vm.get(entity);
        
        // reset to 0
        movement.velocityX = 0;
        movement.velocityY = 0;

        
        if (viewable == null || viewable.isViewable) {
            Entity playerEntity = engine.getEntitiesFor(playerFamily).first();
            TransformComponent playerPosition = tm.get(playerEntity);

            if (playerPosition.pos.x > position.pos.x) {
                movement.velocityX = character.speed;
            } else if (playerPosition.pos.x < position.pos.x) {
                movement.velocityX = -1 * character.speed;
            }

            if (playerPosition.pos.y > position.pos.y) {
                movement.velocityY = character.speed;
            } else if (playerPosition.pos.y < position.pos.y) {
                movement.velocityY = -1 * character.speed;
            }
        }
    }
}
