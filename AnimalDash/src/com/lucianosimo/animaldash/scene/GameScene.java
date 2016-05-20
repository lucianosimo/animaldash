package com.lucianosimo.animaldash.scene;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.lucianosimo.animaldash.base.BaseScene;
import com.lucianosimo.animaldash.manager.SceneManager.SceneType;
import com.lucianosimo.animaldash.object.Enemy1;
import com.lucianosimo.animaldash.object.Enemy2;
import com.lucianosimo.animaldash.object.Enemy3;
import com.lucianosimo.animaldash.object.Enemy4;
import com.lucianosimo.animaldash.object.Platform;
import com.lucianosimo.animaldash.object.Player;

public class GameScene extends BaseScene  implements IOnSceneTouchListener {
	
	//Scene indicators
	private HUD gameHud;
	
	//Physics world variable
	private PhysicsWorld physicsWorld;
	
	//HUD sprites
	
	//Constants	
	private float screenWidth;
	private float screenHeight;
	
	//Instances
	private Player player;
	
	private Enemy1 enemy1;
	private Enemy2 enemy2;
	private Enemy3 enemy3;
	private Enemy4 enemy4;
	
	private Platform[] platforms;
	
	//Booleans

	//Integers
	
	//Windows

	//Buttons
	private Sprite button1;
	private Sprite button2;
	private Sprite button3;
	private Sprite button4;
	//private Sprite buttonSquare;
	//private Sprite buttonTriangle;
	
	//Rectangles
	
	//Counters
	
	//Pools
	
	//Explosions

	//Constants
	private final static int GRAVITY_Y = -20;
	
	private final static int DISTANCE_ENABLING_BUTTON = 400;
	
	//Enemies
	private final static int ENEMY_INITIAL_X = 1500;
	private final static int ENEMIES_BETWEEN_DISTANCE = 1500;
	private final static int ENEMIES_QUANTITY = 4;
	
	//Platforms
	private final static int PLATFORMS_QUANTITY = 10;
	private final static int PLATFORM_WIDTH = 128;
	//private final static int PLATFORM_HEIGHT = 128;
	
	//Buttons
	private final static int BUTTON_1_OFFSET_X = -200;
	private final static int BUTTON_1_OFFSET_Y = -250;
	private final static int BUTTON_2_OFFSET_X = -200;
	private final static int BUTTON_2_OFFSET_Y = -500;
	private final static int BUTTON_3_OFFSET_X = 200;
	private final static int BUTTON_3_OFFSET_Y = -250;
	private final static int BUTTON_4_OFFSET_X = 200;
	private final static int BUTTON_4_OFFSET_Y = -500;
	
	//Camera
	private final static int CAMERA_BOUND_X_MIN = 0;
	private final static int CAMERA_BOUND_Y_MIN = 0;
	private final static int CAMERA_BOUND_X_MAX = 100000000;
	private final static int CAMERA_BOUND_Y_MAX = 1280;
	
	//If negative, never collides between groups, if positive yes
	//private static final int GROUP_ENEMY = -1;

	@Override
	public void createScene() {
		screenWidth = resourcesManager.camera.getWidth();
		screenHeight = resourcesManager.camera.getHeight();
		createBackground();
		createPhysics();
		createPlatform();
		createPlayer();
		createNumericalEnemies();
		createHUD();
		setCameraProperties();
		//DebugRenderer debug = new DebugRenderer(physicsWorld, vbom);
        //GameScene.this.attachChild(debug);
	}
	
	private void createBackground() {
		ParallaxBackground background = new ParallaxBackground(0, 0, 0);
		background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(screenWidth/2, screenHeight/2, resourcesManager.game_background_region, vbom)));
		this.setBackground(background);
	}

	private void createPhysics() {
		physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, GRAVITY_Y), false);
		physicsWorld.setContactListener(contactListener());
		registerUpdateHandler(physicsWorld);
	}
	
	private void createPlatform() {
		platforms = new Platform[PLATFORMS_QUANTITY];
		
		for (int i = 0; i < PLATFORMS_QUANTITY; i++) {
			platforms[i] = new Platform(PLATFORM_WIDTH * (i + 1), screenHeight/2 - 100, vbom, camera, physicsWorld) {
				@Override
				protected void onManagedUpdate(float pSecondsElapsed) {
					super.onManagedUpdate(pSecondsElapsed);
					if ((player.getX() - this.getX()) > 200) {
						
						this.getPlatformBody().setTransform((this.getX() + PLATFORM_WIDTH * PLATFORMS_QUANTITY) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
								this.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
								this.getPlatformBody().getAngle());
						
						this.setPosition((this.getX() + PLATFORM_WIDTH * PLATFORMS_QUANTITY), this.getY());
					}
				}
			};
			GameScene.this.attachChild(platforms[i]);
		}
	}
	
	private void createPlayer() {
		player = new Player(screenWidth/2, screenHeight/2 + 200, vbom, camera, physicsWorld) {
			
			@Override
			public void onDie() {
				
			}
		};
		
		GameScene.this.attachChild(player);
		GameScene.this.attachChild(player.getCameraChaseRectangle());
	}
	
	private void createNumericalEnemies() {
		enemy1 = new Enemy1(ENEMY_INITIAL_X,  screenHeight/2, vbom, physicsWorld) {
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if ((player.getX() - this.getX()) > 1000) {
					
					this.getEnemy1Body().setTransform((this.getX() + ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
							this.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
							this.getEnemy1Body().getAngle());
					
					this.setPosition((this.getX() + ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY), this.getY());
				}
			}
		};
		enemy2 = new Enemy2(ENEMY_INITIAL_X * 2,  screenHeight/2, vbom, physicsWorld){
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if ((player.getX() - this.getX()) > 1000) {
					
					this.getEnemy2Body().setTransform((this.getX() + ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
							this.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
							this.getEnemy2Body().getAngle());
					
					this.setPosition((this.getX() + ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY), this.getY());
				}
			}
		};
		enemy3 = new Enemy3(ENEMY_INITIAL_X * 3,  screenHeight/2, vbom, physicsWorld){
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if ((player.getX() - this.getX()) > 1000) {
					
					this.getEnemy3Body().setTransform((this.getX() + ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
							this.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
							this.getEnemy3Body().getAngle());
					
					this.setPosition((this.getX() + ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY), this.getY());
				}
			}
		};
		enemy4 = new Enemy4(ENEMY_INITIAL_X * 4,  screenHeight/2, vbom, physicsWorld){
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if ((player.getX() - this.getX()) > 1000) {
					
					this.getEnemy4Body().setTransform((this.getX() + ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
							this.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
							this.getEnemy4Body().getAngle());
					
					this.setPosition((this.getX() + ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY), this.getY());
				}
			}
		};
		
		GameScene.this.attachChild(enemy1);
		GameScene.this.attachChild(enemy2);
		GameScene.this.attachChild(enemy3);
		GameScene.this.attachChild(enemy4);
	}
	
	private void createHUD() {
		gameHud = new HUD();
		
		createNumericalButtons();
		
		camera.setHUD(gameHud);
	}
	
	private void createNumericalButtons() {
		button1 = new Sprite(screenWidth/2 + BUTTON_1_OFFSET_X, screenHeight/2 + BUTTON_1_OFFSET_Y, resourcesManager.game_button_1_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown() && enableButton(enemy1.getX(), player.getX())) {
					player.jump(1);
				}
				return false;
			}
		};
		button2 = new Sprite(screenWidth/2 + BUTTON_2_OFFSET_X, screenHeight/2 + BUTTON_2_OFFSET_Y, resourcesManager.game_button_2_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown() && enableButton(enemy2.getX(), player.getX())) {
					player.jump(2);
				}
				return false;
			}
		};
		button3 = new Sprite(screenWidth/2 + BUTTON_3_OFFSET_X, screenHeight/2 + BUTTON_3_OFFSET_Y, resourcesManager.game_button_3_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown() && enableButton(enemy3.getX(), player.getX())) {
					player.jump(3);
				}
				return false;
			}
		};
		button4 = new Sprite(screenWidth/2 + BUTTON_4_OFFSET_X, screenHeight/2 + BUTTON_4_OFFSET_Y, resourcesManager.game_button_4_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown() && enableButton(enemy4.getX(), player.getX())) {
					player.jump(4);
				}
				return false;
			}
		};
		
		gameHud.attachChild(button1);
		gameHud.attachChild(button2);
		gameHud.attachChild(button3);
		gameHud.attachChild(button4);
		
		gameHud.registerTouchArea(button1);
		gameHud.registerTouchArea(button2);
		gameHud.registerTouchArea(button3);
		gameHud.registerTouchArea(button4);
	}
	
	private boolean enableButton(float enemyX, float playerX) {
		return (enemyX - playerX < DISTANCE_ENABLING_BUTTON) && (enemyX - playerX > 0);
	}
	
	private void setCameraProperties() {
		camera.setBoundsEnabled(true);
		camera.setBounds(CAMERA_BOUND_X_MIN, CAMERA_BOUND_Y_MIN, CAMERA_BOUND_X_MAX, CAMERA_BOUND_Y_MAX);
	}
	
	private ContactListener contactListener() {
		ContactListener contactListener = new ContactListener() {
			
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}
			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
	
			}
			
			@Override
			public void endContact(Contact contact) {
				
			}
			
			@Override
			public void beginContact(Contact contact) {
				//final Fixture x1 = contact.getFixtureA();
				//final Fixture x2 = contact.getFixtureB();
			}
		};
		return contactListener;
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		
	}
	
	/*private void myGarbageCollection() {
		Iterator<Body> allMyBodies = physicsWorld.getBodies();
        while(allMyBodies.hasNext()) {
        	try {
        		final Body myCurrentBody = allMyBodies.next();
                	physicsWorld.destroyBody(myCurrentBody);                
            } catch (Exception e) {
            	Debug.e(e);
            }
        }
               
        this.clearChildScene();
        this.detachChildren();
        this.reset();
        this.detachSelf();
        physicsWorld.clearForces();
        physicsWorld.clearPhysicsConnectors();
        physicsWorld.reset();
 
        System.gc();
	}*/
	
	@Override
	public void handleOnPause() {
		
	}
	
	@Override
	public void onBackKeyPressed() {

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		return false;
	}
	
}
