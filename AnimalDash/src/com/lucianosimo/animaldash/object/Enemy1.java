package com.lucianosimo.animaldash.object;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lucianosimo.animaldash.manager.ResourcesManager;

public class Enemy1 extends AnimatedSprite {

	private Body body;
	private FixtureDef fixture;
	private Sprite sign1;
	
	private final static int SIGN_X = 62;
	private final static int SIGN_Y = 128;
	
	public Enemy1(float pX, float pY, VertexBufferObjectManager vbom, PhysicsWorld physicsWorld) {
		super(pX, pY, ResourcesManager.getInstance().game_enemy_1_region, vbom);
		createPhysics(physicsWorld);
		setIdleState();
		createSign(vbom);
	}
	
	private void createPhysics(PhysicsWorld physicsWorld) {
		fixture = PhysicsFactory.createFixtureDef(0, 0, 0);
		fixture.filter.groupIndex = -1;
		
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.StaticBody, fixture);
		
		this.setUserData("enemy1");
		body.setUserData("enemy1");
		
		body.setFixedRotation(true);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false) {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				super.onUpdate(pSecondsElapsed);
			}
		});
	}
	
	public Body getEnemy1Body() {
		return body;
	}
	
	public String getEnemy1UserData() {
		return this.getUserData().toString();
	}
	
	private void setIdleState() {
		final long[] ENEMY_ANIMATE = new long[] {100, 100, 100, 100};
		animate(ENEMY_ANIMATE, 0, 3, true);
	}
	
	private void createSign(VertexBufferObjectManager vbom) {
		sign1 = new Sprite(SIGN_X, SIGN_Y, ResourcesManager.getInstance().game_enemy_1_sign_region, vbom);
		this.attachChild(sign1);
		sign1.setZIndex(-1);
		sortChildren();
	}

}
