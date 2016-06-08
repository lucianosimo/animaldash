package com.lucianosimo.animaldash.object;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lucianosimo.animaldash.manager.ResourcesManager;

public abstract class Player extends Sprite{

	private final static int PLAYER_INITIAL_SPEED = 5;
	private final static int CAMERA_CHASE_RECTANGLE_WIDTH = 128;
	private final static int CAMERA_CHASE_RECTANGLE_HEIGHT = 128;
	private final static int CAMERA_CHASE_PLAYER_DISTANCE = 450;
	
	private Body playerBody;
	private FixtureDef playerFixture;
	
	private Body cameraChaseBody;
	private FixtureDef cameraChaseFixture;
	private Rectangle cameraChaseRectangle;
	private int speedIncrement = 0;
	private int speedIncrementLimit = 5;
	
	public abstract void onDie();
	
	public Player(float pX, float pY, VertexBufferObjectManager vbom, Camera camera, PhysicsWorld physicsWorld) {
		super(pX, pY, ResourcesManager.getInstance().game_player_region, vbom);
		
		createPlayerPhysics(physicsWorld);
		createCameraChaseRectanglePhysics(pX, pY, camera, physicsWorld, vbom);
		
		camera.setChaseEntity(cameraChaseRectangle);
	}
	
	private void createPlayerPhysics(PhysicsWorld physicsWorld) {
		playerFixture = PhysicsFactory.createFixtureDef(0, 0, 0);
		playerBody = PhysicsFactory.createCircleBody(physicsWorld, this, BodyType.DynamicBody, playerFixture);
		playerFixture.filter.groupIndex = -1;
		
		this.setUserData("player");
		playerBody.setUserData("player");
		
		playerBody.setFixedRotation(true);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, playerBody, true, false) {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				super.onUpdate(pSecondsElapsed);
				playerBody.setLinearVelocity(PLAYER_INITIAL_SPEED + speedIncrement, playerBody.getLinearVelocity().y);
			}
		});
	}
	
	private void createCameraChaseRectanglePhysics(float pX, float pY, final Camera camera, PhysicsWorld physicsWorld, VertexBufferObjectManager vbom) {
		cameraChaseRectangle = new Rectangle(pX + CAMERA_CHASE_PLAYER_DISTANCE, pY, CAMERA_CHASE_RECTANGLE_WIDTH, CAMERA_CHASE_RECTANGLE_HEIGHT, vbom);
		cameraChaseRectangle.setAlpha(0);
		
		cameraChaseFixture = PhysicsFactory.createFixtureDef(0, 0, 0);
		cameraChaseFixture.filter.groupIndex = -1;
		
		cameraChaseBody = PhysicsFactory.createCircleBody(physicsWorld, cameraChaseRectangle, BodyType.DynamicBody, cameraChaseFixture);
		
		cameraChaseRectangle.setUserData("cameraChaseRectangle");
		cameraChaseBody.setUserData("cameraChaseRectangle");
		cameraChaseBody.setFixedRotation(true);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(cameraChaseRectangle, cameraChaseBody, true, false) {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				super.onUpdate(pSecondsElapsed);
				camera.onUpdate(0.1f);
				cameraChaseBody.setLinearVelocity(playerBody.getLinearVelocity().x, cameraChaseBody.getLinearVelocity().y);
			}
		});
	}
	
	public Body getPlayerBody() {
		return playerBody;
	}
	
	public Rectangle getCameraChaseRectangle() {
		return cameraChaseRectangle;
	}
	
	public Body getCameraChaseBody() {
		return cameraChaseBody;
	}
	
	public void incrementSpeed() {
		if (speedIncrement <= speedIncrementLimit) {
			speedIncrement += 1;
		}
	}
	
	public void jump(int jumpFactor) {
		playerBody.setLinearVelocity(new Vector2(playerBody.getLinearVelocity().x, jumpFactor * 10));
		this.registerEntityModifier(new RotationModifier(jumpFactor, 0, jumpFactor * 180));
	}
}
