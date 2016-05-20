package com.lucianosimo.animaldash.scene;

import java.util.Iterator;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.debug.Debug;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
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
	
	private final static int PLATFORM_WIDTH = 128;
	private final static int PLATFORM_HEIGHT = 128;
	
	private final static int DISTANCE_ENABLING_BUTTON = 400;
	
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
		createEnemies();
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
		for (int i = 0; i < 100; i++) {
			Platform platform = new Platform(PLATFORM_WIDTH / 2 + i * PLATFORM_WIDTH, 
					screenHeight/2 - 100, vbom, camera, physicsWorld);	
			
			GameScene.this.attachChild(platform);
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
	
	private void createEnemies() {
		enemy1 = new Enemy1(1500,  screenHeight/2, vbom, physicsWorld);
		enemy2 = new Enemy2(3000,  screenHeight/2, vbom, physicsWorld);
		enemy3 = new Enemy3(4500,  screenHeight/2, vbom, physicsWorld);
		enemy4 = new Enemy4(6000,  screenHeight/2, vbom, physicsWorld);
		
		GameScene.this.attachChild(enemy1);
		GameScene.this.attachChild(enemy2);
		GameScene.this.attachChild(enemy3);
		GameScene.this.attachChild(enemy4);
	}
	
	private void createHUD() {
		gameHud = new HUD();
		
		button1 = new Sprite(screenWidth/2 - 200, screenHeight/2 - 250, resourcesManager.game_button_1_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown() && enableButton(enemy1.getX(), player.getX())) {
					player.jump(1);
				}
				return false;
			}
		};
		button2 = new Sprite(screenWidth/2 - 200, screenHeight/2 - 500, resourcesManager.game_button_2_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown() && enableButton(enemy2.getX(), player.getX())) {
					player.jump(2);
				}
				return false;
			}
		};
		button3 = new Sprite(screenWidth/2 + 200, screenHeight/2 - 250, resourcesManager.game_button_3_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown() && enableButton(enemy3.getX(), player.getX())) {
					player.jump(3);
				}
				return false;
			}
		};
		button4 = new Sprite(screenWidth/2 + 200, screenHeight/2 - 500, resourcesManager.game_button_4_region, vbom) {
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
		
		camera.setHUD(gameHud);
	}
	
	private boolean enableButton(float enemyX, float playerX) {
		return (enemyX - playerX < DISTANCE_ENABLING_BUTTON) && (enemyX - playerX > 0);
	}
	
	private void setCameraProperties() {
		camera.setBoundsEnabled(true);
		camera.setBounds(0, 0, 1000000, 1280);
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
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();
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
	
	private void myGarbageCollection() {
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
	}
	
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
