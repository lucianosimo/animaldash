package com.lucianosimo.animaldash.object;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lucianosimo.animaldash.manager.ResourcesManager;

public abstract class Player extends Sprite{

	private Body body;
	private FixtureDef fixture;
	
	public abstract void onDie();
	
	public Player(float pX, float pY, VertexBufferObjectManager vbom, Camera camera, PhysicsWorld physicsWorld) {
		super(pX, pY, ResourcesManager.getInstance().game_player_region, vbom);
		createPhysics(camera, physicsWorld);
		Rectangle rectangle = new Rectangle(450, 50, 10, 10, vbom);
		rectangle.setAlpha(0);
		this.attachChild(rectangle);
		camera.setChaseEntity(rectangle);
	}
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld) {
		fixture = PhysicsFactory.createFixtureDef(0, 0, 0);
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.DynamicBody, fixture);
		
		this.setUserData("player");
		body.setUserData("player");
		
		body.setFixedRotation(true);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false) {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				super.onUpdate(pSecondsElapsed);
				camera.onUpdate(0.1f);
				body.setLinearVelocity(15, body.getLinearVelocity().y);
			}
		});
	}
	
	public Body getPlayerBody() {
		return body;
	}	
}
