package com.lucianosimo.animaldash.object;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lucianosimo.animaldash.manager.ResourcesManager;

public class Enemy3 extends Sprite {

	private Body body;
	private FixtureDef fixture;

	public Enemy3(float pX, float pY, VertexBufferObjectManager vbom, PhysicsWorld physicsWorld) {
		super(pX, pY, ResourcesManager.getInstance().game_enemy_3_region, vbom);
		createPhysics(physicsWorld);
	}
	
	private void createPhysics(PhysicsWorld physicsWorld) {
		fixture = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixture.filter.groupIndex = -1;
		
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.StaticBody, fixture);
		
		this.setUserData("enemy3");
		body.setUserData("enemy3");
		
		body.setFixedRotation(true);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false) {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				super.onUpdate(pSecondsElapsed);
			}
		});
	}
	
	public Body getEnemy3Body() {
		return body;
	}
	
	public String getEnemy3UserData() {
		return this.getUserData().toString();
	}

}
