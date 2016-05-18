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
	
	//Booleans

	//Integers
	
	//Windows

	//Buttons
	private Sprite button1;
	private Sprite button2;
	private Sprite buttonSquare;
	private Sprite buttonTriangle;
	
	//Rectangles
	
	//Counters
	
	//Pools
	
	//Explosions

	//Constants
	private final static int GRAVITY_Y = -20;
	
	private final static int PLATFORM_WIDTH = 128;
	private final static int PLATFORM_HEIGHT = 128;
	
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
	}
	
	private void createHUD() {
		gameHud = new HUD();
		
		button1 = new Sprite(screenWidth/2 - 200, screenHeight/2 - 250, resourcesManager.game_button_1_region, vbom){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					player.jump();
				}
				return false;
			}
		};
		button2 = new Sprite(screenWidth/2 - 200, screenHeight/2 - 500, resourcesManager.game_button_2_region, vbom);
		buttonSquare = new Sprite(screenWidth/2 + 200, screenHeight/2 - 250, resourcesManager.game_button_square_region, vbom);
		buttonTriangle = new Sprite(screenWidth/2 + 200, screenHeight/2 - 500, resourcesManager.game_button_triangle_region, vbom);
		
		gameHud.attachChild(button1);
		gameHud.attachChild(button2);
		gameHud.attachChild(buttonSquare);
		gameHud.attachChild(buttonTriangle);
		
		gameHud.registerTouchArea(button1);
		
		camera.setHUD(gameHud);
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
