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
import com.lucianosimo.animaldash.manager.ResourcesManager;

public abstract class Player extends Sprite{

	private final static int PLAYER_INITIAL_SPEED = 7;
	private final static int PLAYER_JUMP_SPEED_X = 15;
	//private final static int PLAYER_JUMP_SPEED_Y = 25;
	private final static int PLAYER_JUMP_SPEED_Y = 24;
	
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
		playerBody.getFixtureList().get(0).getShape().setRadius(1.8f);
		this.setAnchorCenter(0.5f, 0.5f);
		
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
			playerBody.getFixtureList().get(0).getShape().setRadius(1.8f);
			this.setAnchorCenter(0.5f, 0.5f);
			break;
		//Bird2
		case 1:
			playerBody.getFixtureList().get(0).getShape().setRadius(1.75f);
			this.setAnchorCenter(0.5f, 0.55f);
			break;
		//Bird
		case 2:
			playerBody.getFixtureList().get(0).getShape().setRadius(1.85f);
			this.setAnchorCenter(0.5f, 0.6f);
			break;
		//Bunny
		case 3:
			playerBody.getFixtureList().get(0).getShape().setRadius(1.35f);
			this.setAnchorCenter(0.5f, 0.35f);		
			break;
		//Elephant
		case 4:
			playerBody.getFixtureList().get(0).getShape().setRadius(1.35f);
			this.setAnchorCenter(0.5f, 0.59f);
			break;
		//Giraffe
		case 5:
			playerBody.getFixtureList().get(0).getShape().setRadius(1.22f);
			this.setAnchorCenter(0.5f, 0.37f);
			break;
		//Hippo
		case 6:
			playerBody.getFixtureList().get(0).getShape().setRadius(1.58f);
			this.setAnchorCenter(0.5f, 0.42f);
			break;
		//Monkey
		case 7:
			playerBody.getFixtureList().get(0).getShape().setRadius(1.8f);
			this.setAnchorCenter(0.5f, 0.5f);
			break;
		//Panda
		case 8:
			playerBody.getFixtureList().get(0).getShape().setRadius(1.6f);
			this.setAnchorCenter(0.5f, 0.45f);
			break;
		//Penguin
		case 9:
			playerBody.getFixtureList().get(0).getShape().setRadius(1.95f);
			this.setAnchorCenter(0.5f, 0.5f);
			break;
		//Pig
		case 10:
			playerBody.getFixtureList().get(0).getShape().setRadius(1.84f);
			this.setAnchorCenter(0.5f, 0.45f);
			break;
		//Snake
		case 11:		
			playerBody.getFixtureList().get(0).getShape().setRadius(1.5f);
			this.setAnchorCenter(0.5f, 0.6f);
			break;
		default:
			playerBody.getFixtureList().get(0).getShape().setRadius(1.8f);
			this.setAnchorCenter(0.5f, 0.5f);
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
				if (((int)this.getRotation() / 90 & 1) == 0 || this.getRotation() == 0) {
					this.registerEntityModifier(
							new ScaleModifier(
									jumpScaleCycleDuration, fromScaleHorizontal, toScaleHorizontal, fromScaleVertical, toScaleVertical) {
						@Override
						protected void onModifierFinished(IEntity pItem) {
							super.onModifierFinished(pItem);
							Player.this.registerEntityModifier(
									new ScaleModifier(
											jumpScaleCycleDuration, toScaleHorizontal, fromScaleHorizontal , toScaleVertical, fromScaleVertical));
						}
					});
				} else {
					this.registerEntityModifier(
							new ScaleModifier(
									jumpScaleCycleDuration, fromScaleVertical, toScaleVertical, fromScaleHorizontal, toScaleHorizontal) {
						@Override
						protected void onModifierFinished(IEntity pItem) {
							super.onModifierFinished(pItem);
							Player.this.registerEntityModifier(
									new ScaleModifier(
											jumpScaleCycleDuration, toScaleVertical, fromScaleVertical, toScaleHorizontal, fromScaleHorizontal));
						}
					});
				}
			}
			
			this.registerEntityModifier(new RotationModifier(PLAYER_JUMP_ROTATION_DURATION, savedRotation, savedRotation + jumpRotation * ROTATION_DEGREES) {
				@Override
				protected void onModifierFinished(IEntity pItem) {
					super.onModifierFinished(pItem);
					savedRotation = Player.this.getRotation();
				}
			});
			
			setInAir(true);
		}
	}
	
	public boolean isInAir() {
		return inAir;
	}
	
	public void setInAir(boolean inAir) {
		this.inAir = inAir;
		
		if (!this.inAir && !this.isInPowerUpMode) {
			
			if (((int)this.getRotation() / 90 & 1) == 0) {
				this.registerEntityModifier(
					new ScaleModifier(
							jumpScaleCycleDuration, fromScaleVertical, toScaleVertical, fromScaleHorizontal, toScaleHorizontal) {
						@Override
						protected void onModifierFinished(IEntity pItem) {
							super.onModifierFinished(pItem);
							Player.this.registerEntityModifier(
									new ScaleModifier(
											jumpScaleCycleDuration, toScaleVertical, fromScaleVertical, toScaleHorizontal, fromScaleHorizontal));
						}
					});
			} else {
				this.registerEntityModifier(
					new ScaleModifier(
							jumpScaleCycleDuration, fromScaleHorizontal, toScaleHorizontal, fromScaleVertical, toScaleVertical) {
						@Override
						protected void onModifierFinished(IEntity pItem) {
							super.onModifierFinished(pItem);
							Player.this.registerEntityModifier(
									new ScaleModifier(
											jumpScaleCycleDuration, toScaleHorizontal, fromScaleHorizontal , toScaleVertical, fromScaleVertical));
						}
					});
			}
			
			
		}
	}
	
	public void setPowerUp() {
		isInPowerUpMode = true;
		this.setScale(SCALE_FACTOR);
		playerBody.getFixtureList().get(0).getShape().setRadius(playerBody.getFixtureList().get(0).getShape().getRadius() * SCALE_FACTOR);
	}
	
	public void unsetPowerUp() {
		isInPowerUpMode = false;
		this.setScale(1);
		playerBody.getFixtureList().get(0).getShape().setRadius(playerBody.getFixtureList().get(0).getShape().getRadius() * 1/SCALE_FACTOR);
	}
	
	public boolean isPlayerAlive() {
		return isAlive;
	}
	
	public boolean isPlayerInPowerUpMode() {
		return isInPowerUpMode;
	}
	
	public void killPlayer() {
		speedIncrement = -PLAYER_INITIAL_SPEED;
		isAlive = false;
		
		playerBody.setLinearVelocity(new Vector2(playerBody.getLinearVelocity().x, PLAYER_JUMP_SPEED_Y));
		playerBody.getFixtureList().get(0).setSensor(true);
		
		this.registerEntityModifier(new RotationModifier(PLAYER_KILL_JUMP_ROTATION_DURATION, savedRotation, savedRotation + 12 * ROTATION_DEGREES) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.onModifierFinished(pItem);
				savedRotation = Player.this.getRotation();
			}
		});
	}
}
