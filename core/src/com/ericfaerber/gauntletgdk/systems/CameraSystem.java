package com.ericfaerber.gauntletgdk.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.ericfaerber.gauntletgdk.components.CameraComponent;
import com.ericfaerber.gauntletgdk.components.TransformComponent;

public class CameraSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> pm;
    private ComponentMapper<CameraComponent> cm;
    
    public CameraSystem() {
        super(Family.getFor(CameraComponent.class));
		
        pm = ComponentMapper.getFor(TransformComponent.class);
        cm = ComponentMapper.getFor(CameraComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CameraComponent cam = cm.get(entity);
		
        if (cam.target == null) {
            return;
        }

        TransformComponent target = pm.get(cam.target);

        if (target == null) {
            return;
        }
        
        cam.camera.position.x = MathUtils.clamp(target.pos.x, cam.camera.viewportWidth / 2f, cam.maxWidth - cam.camera.viewportWidth / 2f);
        cam.camera.position.y = MathUtils.clamp(target.pos.y, cam.camera.viewportHeight / 2f, cam.maxHeight - cam.camera.viewportHeight / 2f);
    }
    
}
