package com.ericfaerber.gauntletgdk.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.ericfaerber.gauntletgdk.components.CharacterComponent;
import com.ericfaerber.gauntletgdk.components.TransformComponent;
import com.ericfaerber.gauntletgdk.components.ViewableComponent;

public class ViewableSystem extends IteratingSystem {
    private final ComponentMapper<ViewableComponent> vm;
    private final ComponentMapper<TransformComponent> tm;
    
    public ViewableSystem() {
        super(Family.getFor(ViewableComponent.class, TransformComponent.class));

        vm = ComponentMapper.getFor(ViewableComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }
    
    @Override
    protected void processEntity(Entity entity, float timeDelta) {
        ViewableComponent viewable = vm.get(entity);
        TransformComponent position = tm.get(entity);
        BoundingBox bounds = new BoundingBox(position.pos, new Vector3(position.pos.x + CharacterComponent.CHARACTER_WIDTH, position.pos.y + CharacterComponent.CHARACTER_HEIGHT, 0));

        viewable.isViewable = viewable.camera.frustum.boundsInFrustum(bounds);
    }
    
}
