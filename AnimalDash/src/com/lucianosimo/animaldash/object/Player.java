package com.lucianosimo.animaldash.object;

import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.lucianosimo.animaldash.manager.ResourcesManager;

public abstract class Player extends Sprite{

	private final static int PLAYER_INITIAL_SPEED = 7;
	private final static int PLAYER_JUMP_SPEED_X = 15;
	private final static int PLAYER_JUMP_SPEED_Y = 24;
	
	private final static float BEAVER_ANCHOR_CENTER_X = 0.5f;
	private final static float BEAVER_ANCHOR_CENTER_Y = 0.5f;
	private final static float BEAVER_ANCHOR_BODY_RADIUS = 1.8f;
	
	private final static float BIRD_2_ANCHOR_CENTER_X = 0.5f;
	private final static float BIRD_2_ANCHOR_CENTER_Y = 0.55f;
	private final static float BIRD_2_ANCHOR_BODY_RADIUS = 1.75f;
	
	private final static float BIRD_ANCHOR_CENTER_X = 0.5f;
	private final static float BIRD_ANCHOR_CENTER_Y = 0.6f;
	private final static float BIRD_ANCHOR_BODY_RADIUS = 1.85f;
	
	private final static float BUNNY_ANCHOR_CENTER_X = 0.5f;
	private final static float BUNNY_ANCHOR_CENTER_Y = 0.35f;
	private final static float BUNNY_ANCHOR_BODY_RADIUS = 1.35f;
	
	private final static float ELEPHANT_ANCHOR_CENTER_X = 0.5f;
	private final static float ELEPHANT_ANCHOR_CENTER_Y = 0.59f;
	private final static float ELEPHANT_ANCHOR_BODY_RADIUS = 1.35f;
	
	private final static float GIRAFFE_ANCHOR_CENTER_X = 0.5f;
	private final static float GIRAFFE_ANCHOR_CENTER_Y = 0.37f;
	private final static float GIRAFFE_ANCHOR_BODY_RADIUS = 1.22f;
	
	private final static float HIPPO_ANCHOR_CENTER_X = 0.5f;
	private final static float HIPPO_ANCHOR_CENTER_Y = 0.42f;
	private final static float HIPPO_ANCHOR_BODY_RADIUS = 1.58f;
	
	private final static float MONKEY_ANCHOR_CENTER_X = 0.5f;
	private final static float MONKEY_ANCHOR_CENTER_Y = 0.5f;
	private final static float MONKEY_ANCHOR_BODY_RADIUS = 1.8f;
	
	private final static float PANDA_ANCHOR_CENTER_X = 0.5f;
	private final static float PANDA_ANCHOR_CENTER_Y = 0.45f;
	private final static float PANDA_ANCHOR_BODY_RADIUS = 1.6f;

	private final static float PENGUIN_ANCHOR_CENTER_X = 0.5f;
	private final static float PENGUIN_ANCHOR_CENTER_Y = 0.5f;
	private final static float PENGUIN_ANCHOR_BODY_RADIUS = 1.95f;
	
	private final static float PIG_ANCHOR_CENTER_X = 0.5f;
	private final static float PIG_ANCHOR_CENTER_Y = 0.45f;
	private final static float PIG_ANCHOR_BODY_RADIUS = 1.84f;
	
	private final static float SNAKE_ANCHOR_CENTER_X = 0.5f;
	private final static float SNAKE_ANCHOR_CENTER_Y = 0.6f;
	private final static float SNAKE_ANCHOR_BODY_RADIUS = 1.5f;
	
	private final static float PLAYER_JUMP_ROTATION_DURATION = 2.3f;
	private final static float PLAYER_KILL_JUMP_ROTATION_DURATION = 3f;
	
	private final static int CAMERA_CHASE_RECTANGLE_WIDTH = 128;
	private final static int CAMERA_CHASE_RECTANGLE_HEIGHT = 128;
	private final static int CAMERA_CHASE_PLAYER_DISTANCE = 225;
	
	private final static float SCALE_FACTOR = 1.85f;
	private final static int ROTATION_DEGREES = 90;
	private final static int ROTATION_FACTOR_MAX = 7;
	private final static int ROTATION_FACTOR_MIN = 5;
	
	private float jumpScaleCycleDuration = 0.1f;
	private float fromScaleHorizontal = 1;
	private float toScaleHorizontal = 0.8f;
	private float fromScaleVertical = 1;
	private float toScaleVertical = 1.2f;
	
	private boolean inAir = false;
	
	private Body playerBody;
	private FixtureDef playerFixture;
	
	private Body cameraChaseBody;
	private FixtureDef cameraChaseFixture;
	private Rectangle cameraChaseRectangle;
	
	private int speedIncrement = 0;
	private int speedIncrementLimit = 5;
	private float savedRotation = 0;
	
	private boolean isAlive = true;
	private boolean isInPowerUpMode = false;
	
	public Player(float pX, float pY, VertexBufferObjectManager vbom, Camera camera, PhysicsWorld physicsWorld) {		
		super(pX, pY, ResourcesManager.getInstance().game_player_beaver_region, vbom);
		
		createCameraChaseRectanglePhysics(pX, pY, camera, physicsWorld, vbom);
		createPlayerPhysics(physicsWorld);
		
		camera.setChaseEntity(cameraChaseRectangle);
	}
	
	private void createPlayerPhysics(PhysicsWorld physicsWorld) {
		playerFixture = PhysicsFactory.createFixtureDef(0, 0, 0);
		
		playerBody = PhysicsFactory.createCircleBody(physicsWorld, this, BodyType.DynamicBody, playerFixture);
		playerBody.getFixtureList().get(0).getShape().setRadius(BEAVER_ANCHOR_BODY_RADIUS);
		this.setAnchorCenter(BEAVER_ANCHOR_CENTER_X,BEAVER_ANCHOR_CENTER_Y);
		
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
	
	public void setTexture(ITextureRegion textureRegion, int playerIndex) {
		this.setTextureRegion(textureRegion);
		
		switch (playerIndex) {
		//Beaver
		case 0:
			playerBody.getFixtureList().get(0).getShape().setRadius(BEAVER_ANCHOR_BODY_RADIUS);
			this.setAnchorCenter(BEAVER_ANCHOR_CENTER_X, BEAVER_ANCHOR_CENTER_Y);
			break;
		//Bird2
		case 1:
			playerBody.getFixtureList().get(0).getShape().setRadius(BIRD_2_ANCHOR_BODY_RADIUS);
			this.setAnchorCenter(BIRD_2_ANCHOR_CENTER_X, BIRD_2_ANCHOR_CENTER_Y);
			break;
		//Bird
		case 2:
			playerBody.getFixtureList().get(0).getShape().setRadius(BIRD_ANCHOR_BODY_RADIUS);
			this.setAnchorCenter(BIRD_ANCHOR_CENTER_X, BIRD_ANCHOR_CENTER_Y);
			break;
		//Bunny
		case 3:
			playerBody.getFixtureList().get(0).getShape().setRadius(BUNNY_ANCHOR_BODY_RADIUS);
			this.setAnchorCenter(BUNNY_ANCHOR_CENTER_X, BUNNY_ANCHOR_CENTER_Y);		
			break;
		//Elephant
		case 4:
			playerBody.getFixtureList().get(0).getShape().setRadius(ELEPHANT_ANCHOR_BODY_RADIUS);
			this.setAnchorCenter(ELEPHANT_ANCHOR_CENTER_X, ELEPHANT_ANCHOR_CENTER_Y);
			break;
		//Giraffe
		case 5:
			playerBody.getFixtureList().get(0).getShape().setRadius(GIRAFFE_ANCHOR_BODY_RADIUS);
			this.setAnchorCenter(GIRAFFE_ANCHOR_CENTER_X, GIRAFFE_ANCHOR_CENTER_Y);
			break;
		//Hippo
		case 6:
			playerBody.getFixtureList().get(0).getShape().setRadius(HIPPO_ANCHOR_BODY_RADIUS);
			this.setAnchorCenter(HIPPO_ANCHOR_CENTER_X, HIPPO_ANCHOR_CENTER_Y);
			break;
		//Monkey
		case 7:
			playerBody.getFixtureList().get(0).getShape().setRadius(MONKEY_ANCHOR_BODY_RADIUS);
			this.setAnchorCenter(MONKEY_ANCHOR_CENTER_X, MONKEY_ANCHOR_CENTER_Y);
			break;
		//Panda
		case 8:
			playerBody.getFixtureList().get(0).getShape().setRadius(PANDA_ANCHOR_BODY_RADIUS);
			this.setAnchorCenter(PANDA_ANCHOR_CENTER_X, PANDA_ANCHOR_CENTER_Y);
			break;
		//Penguin
		case 9:
			playerBody.getFixtureList().get(0).getShape().setRadius(PENGUIN_ANCHOR_BODY_RADIUS);
			this.setAnchorCenter(PENGUIN_ANCHOR_CENTER_X, PENGUIN_ANCHOR_CENTER_Y);
			break;
		//Pig
		case 10:
			playerBody.getFixtureList().get(0).getShape().setRadius(PIG_ANCHOR_BODY_RADIUS);
			this.setAnchorCenter(PIG_ANCHOR_CENTER_X, PIG_ANCHOR_CENTER_Y);
			break;
		//Snake
		case 11:		
			playerBody.getFixtureList().get(0).getShape().setRadius(SNAKE_ANCHOR_BODY_RADIUS);
			this.setAnchorCenter(SNAKE_ANCHOR_CENTER_X, SNAKE_ANCHOR_CENTER_Y);
			break;
		default:
			playerBody.getFixtureList().get(0).getShape().setRadius(BEAVER_ANCHOR_BODY_RADIUS);
			this.setAnchorCenter(BEAVER_ANCHOR_CENTER_X, BEAVER_ANCHOR_CENTER_Y);
			break;
		}
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
	
	public void jump() {
		if(!this.inAir) {
			Random rand = new Random();
			int jumpRotation = rand.nextInt(ROTATION_FACTOR_MAX - ROTATION_FACTOR_MIN + 1) + ROTATION_FACTOR_MIN;
			
			playerBody.setLinearVelocity(new Vector2(PLAYER_JUMP_SPEED_X, PLAYER_JUMP_SPEED_Y));
			
			if (!this.isInPowerUpMode) {
				if (isPlayerRotated() || this.getRotation() == 0) {
					setSquashAndStretchEffect(fromScaleHorizontal, toScaleHorizontal, fromScaleVertical, toScaleVertical);
				} else {
					setSquashAndStretchEffect(fromScaleVertical, toScaleVertical, fromScaleHorizontal, toScaleHorizontal);
				}
			}
			
			setPlayerRotation(PLAYER_JUMP_ROTATION_DURATION, jumpRotation);
			
			setInAir(true);
		}
	}
	
	public boolean isInAir() {
		return inAir;
	}
	
	public void setInAir(boolean inAir) {
		this.inAir = inAir;
		
		if (!this.inAir && !this.isInPowerUpMode) {
			
			if (isPlayerRotated()) {
				setSquashAndStretchEffect(fromScaleVertical, toScaleVertical, fromScaleHorizontal, toScaleHorizontal);
			} else {
				setSquashAndStretchEffect(fromScaleHorizontal, toScaleHorizontal, fromScaleVertical, toScaleVertical);
			}
			
			
		}
	}
	
	public void setPowerUp() {
		Shape playerShape = playerBody.getFixtureList().get(0).getShape();
		
		isInPowerUpMode = true;
		this.setScale(SCALE_FACTOR);
		playerShape.setRadius(playerShape.getRadius() * SCALE_FACTOR);
	}
	
	public void unsetPowerUp() {
		Shape playerShape = playerBody.getFixtureList().get(0).getShape();
		
		isInPowerUpMode = false;
		this.setScale(1);
		playerShape.setRadius(playerShape.getRadius() * 1/SCALE_FACTOR);
	}
	
	public boolean isPlayerAlive() {
		return isAlive;
	}
	
	public boolean isPlayerInPowerUpMode() {
		return isInPowerUpMode;
	}
	
	public void setPlayerSensor(boolean isSensor) {
		playerBody.getFixtureList().get(0).setSensor(isSensor);
	}
	
	public void killPlayer() {
		int jumpRotation = 12;
		
		speedIncrement = -PLAYER_INITIAL_SPEED;
		isAlive = false;
		
		playerBody.setLinearVelocity(
				new Vector2(playerBody.getLinearVelocity().x, PLAYER_JUMP_SPEED_Y));
		setPlayerSensor(true);
		
		setPlayerRotation(PLAYER_KILL_JUMP_ROTATION_DURATION, jumpRotation);
	}
	
	private boolean isPlayerRotated() {
		return ((int)this.getRotation() / 90 & 1) == 0;
	}
	
	private void setPlayerRotation(float jumpDuration, int jumpRotation) {
		this.registerEntityModifier(
				new RotationModifier(jumpDuration, savedRotation, savedRotation + jumpRotation * ROTATION_DEGREES) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.onModifierFinished(pItem);
				savedRotation = Player.this.getRotation();
			}
		});
	}
	
	private void setSquashAndStretchEffect(final float fromScaleX,final float toScaleX,final float fromScaleY,final float toScaleY) {
		this.registerEntityModifier(
				new ScaleModifier(
						jumpScaleCycleDuration, fromScaleX, toScaleX, fromScaleY, toScaleY) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.onModifierFinished(pItem);
				Player.this.registerEntityModifier(
						new ScaleModifier(
								jumpScaleCycleDuration, toScaleX, fromScaleX , toScaleY, fromScaleY));
			}
		});
	}
}
