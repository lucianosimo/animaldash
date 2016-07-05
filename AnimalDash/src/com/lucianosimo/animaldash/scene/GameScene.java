package com.lucianosimo.animaldash.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseElasticInOut;
import org.andengine.util.modifier.ease.EaseElasticOut;
import org.andengine.util.modifier.ease.IEaseFunction;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.lucianosimo.animaldash.base.BaseScene;
import com.lucianosimo.animaldash.manager.SceneManager;
import com.lucianosimo.animaldash.manager.SceneManager.SceneType;
import com.lucianosimo.animaldash.object.Enemy1;
import com.lucianosimo.animaldash.object.Enemy2;
import com.lucianosimo.animaldash.object.Enemy3;
import com.lucianosimo.animaldash.object.Enemy4;
import com.lucianosimo.animaldash.object.Platform;
import com.lucianosimo.animaldash.object.Player;

public class GameScene extends BaseScene implements IOnSceneTouchListener {
	
	//Scene indicators
	private HUD gameHud;
	
	//Physics world variable
	private PhysicsWorld physicsWorld;
	
	//Menu
	private Sprite menu_title;
	private Sprite menu_play_button;
	
	//HUD sprites
	private Sprite game_hud_background;
	private Rectangle game_hud_powerup_empty_background;
	private Rectangle game_hud_powerup_bar;
	private Sprite game_hud_powerup_background;
	private Sprite game_hud_small_player;
	private Sprite game_hud_big_player;
	
	private final static int HUD_POWERUP_EMPTY_BACKGROUND_WIDTH = 365;
	private final static int HUD_POWERUP_EMPTY_BACKGROUND_HEIGHT = 75;
	
	//Constants	
	private float screenWidth;
	private float screenHeight;
	
	//Instances
	private Player player;
	
	private Enemy1[] enemy1;
	private Enemy2[] enemy2;
	private Enemy3[] enemy3;
	private Enemy4[] enemy4;
	
	private ArrayList<ITextureRegion> playersRegions;
	private ArrayList<ITextureRegion> playersIconsRegions;
	
	private int selectedPlayerIndex = 0;
	
	//Fruits
	private Sprite[] fruits;
	private int fruitsCounter;
	private DelayModifier powerUpBarDecreaseModifier;
	private boolean decreasingPowerUpBar;
	//private float increaseBarCounter = 0;
	//private IUpdateHandler increaseBarUpdateHandler;
	
	private final static int FRUITS_INITIAL_X = 2000;
	private final static int FRUITS_BETWEEN_DISTANCE = 2000;
	private final static int FRUITS_CENTER_SCREEN_OFFSET_Y = 350;
	private final static int FRUITS_QUANTITY = 4;
	
	private final static int POWER_UP_REQUIRED_FRUITS = 4;
	private final static float POWER_UP_DURATION = 15;
	//private final static int POWER_UP_BAR_INCREASE_DURATION = 1;
	
	private Platform[] platforms;
	
	//Booleans
	private boolean gameStarted = false;

	//Integers
	
	//Windows
	private Sprite game_over_window;
	private Sprite game_over_fruits_icon;
	private Sprite game_over_enemies_icon;
	private Sprite[] game_over_x_icon;
	private Text fruitsScoreText;
	private Text enemiesScoreText;

	//Buttons
	private Sprite buttonPlayerSelectLeft;
	private Sprite buttonPlayerSelectRight;
	private Sprite gamePlayerIcon;
	
	private Sprite button1;
	private Sprite button2;
	private Sprite button3;
	private Sprite button4;
	
	private Sprite button1Disabled;
	private Sprite button2Disabled;
	private Sprite button3Disabled;
	private Sprite button4Disabled;
	
	//private Sprite buttonSquare;
	//private Sprite buttonTriangle;
	
	private Sprite buttonReplay;
	private Sprite buttonQuit;
	
	//Rectangles
	
	//Counters
	private int pressedButtonCounter;
	private int enemiesCounter = 0;
	private int fruitsGlobalCounter = 0;
	
	//Background
	private AutoParallaxBackground autoParallaxBackground;
	private ParallaxEntity backgroundLayer1;
	private ParallaxEntity backgroundLayer2;
	private ParallaxEntity backgroundLayer3;
	private ParallaxEntity backgroundLayer4;
	private ParallaxEntity backgroundLayer5;
	private ParallaxEntity backgroundLayer6;
	private int parallaxChangeForSecond;

	//Constants
	private final static int GRAVITY_Y = -20;
	
	private final static int DISTANCE_ENABLING_BUTTON = 400;
	
	//Enemies
	private final static int ENEMY_INITIAL_X = 2000;
	private final static int ENEMIES_BETWEEN_DISTANCE = 2000;
	private final static int ENEMIES_QUANTITY = 16;
	private final static int ENEMY_1_QUANTITY = ENEMIES_QUANTITY / 4;
	private final static int ENEMY_2_QUANTITY = ENEMIES_QUANTITY / 4;
	private final static int ENEMY_3_QUANTITY = ENEMIES_QUANTITY / 4;
	private final static int ENEMY_4_QUANTITY = ENEMIES_QUANTITY / 4;
	private final static int ENEMY_CENTER_OFFSET_Y = 0;
	
	//Platforms
	private final static int PLATFORMS_QUANTITY = 10;
	private final static int PLATFORM_WIDTH = 128;
	//private final static int PLATFORM_HEIGHT = 128;
	
	//Buttons
	private final static int BUTTON_PLAYER_SELECT_LEFT_CENTER_OFFSET_X = -225;
	private final static int BUTTON_PLAYER_SELECT_LEFT_CENTER_OFFSET_Y = -390;
	
	private final static int BUTTON_PLAYER_SELECT_RIGHT_CENTER_OFFSET_X = 225;
	private final static int BUTTON_PLAYER_SELECT_RIGHT_CENTER_OFFSET_Y = -390;
	
	private final static int BUTTON_PLAYER_ICON_CENTER_OFFSET_Y = -390;
	
	private final static int BUTTON_PLAYER_SELECT_MOVE_MODIFIER_Y = -375;
	
	private final static int BUTTON_1_OFFSET_X = -175;
	private final static int BUTTON_1_MOVE_MODIFIER_X = -275;
	private final static int BUTTON_1_OFFSET_Y = -275;
	
	private final static int BUTTON_2_OFFSET_X = -175;
	private final static int BUTTON_2_MOVE_MODIFIER_X = -275;
	private final static int BUTTON_2_OFFSET_Y = -500;
	
	private final static int BUTTON_3_OFFSET_X = 175;
	private final static int BUTTON_3_MOVE_MODIFIER_X = 275;
	private final static int BUTTON_3_OFFSET_Y = -275;
	
	private final static int BUTTON_4_OFFSET_X = 175;
	private final static int BUTTON_4_MOVE_MODIFIER_X = 275;
	private final static int BUTTON_4_OFFSET_Y = -500;
	
	//Camera
	private final static int CAMERA_BOUND_X_MIN = 0;
	private final static int CAMERA_BOUND_Y_MIN = 0;
	private final static int CAMERA_BOUND_X_MAX = 100000000;
	private final static int CAMERA_BOUND_Y_MAX = 1280;
	
	private final static int PLATFORM_CENTER_OFFSET_Y = -105;
	private final static int MENU_TITLE_OFFSET_Y = -200;
	private final static int MENU_TITLE_MOVE_MODIFIER_Y = 375;
	private final static int MENU_PLAY_BUTTON_OFFSET_Y = 50;
	private final static int MENU_POWERUP_HUD_ITEMS_OFFSET_Y = 200;
	private final static int GAME_OVER_WINDOW_OFFSET_Y = 300;
	
	private final static int GAME_OVER_FRUITS_ICON_X = 175;
	private final static int GAME_OVER_FRUITS_ICON_Y = 325;
	private final static int GAME_OVER_ENEMIES_ICON_X = 175;
	private final static int GAME_OVER_ENEMIES_ICON_Y = 150;
	
	private final static int BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS = 200;
	
	private final static float JUMP_LANDING_EFFECT_SCALE_FACTOR = 1.85f;
	
	//Ease functions
	private static final IEaseFunction[][] EASEFUNCTIONS = new IEaseFunction[][] {
		new IEaseFunction[] { 
				EaseElasticInOut.getInstance(),
				EaseElasticOut.getInstance()},
	};
	
	//Effects
	private AnimatedSprite landingEffect;
	private final static long[] LANDING_EFFECT_ANIMATE = new long[] {75, 75, 75, 75, 100};
	
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
		createMenu();
		setCameraProperties();
		//DebugRenderer debug = new DebugRenderer(physicsWorld, vbom);
        //GameScene.this.attachChild(debug);
	}
	
	private void createBackground() {
		parallaxChangeForSecond = 5;
		
		autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, parallaxChangeForSecond);
		
		backgroundLayer1 = new ParallaxEntity(0.0f, new Sprite(screenWidth / 2, screenHeight / 2, screenWidth, screenHeight, resourcesManager.game_background_layer_1_region, vbom));
		backgroundLayer2 = new ParallaxEntity(-5.0f, new Sprite(screenWidth / 2, screenHeight / 2, screenWidth, screenHeight, resourcesManager.game_background_layer_2_region, vbom));
		backgroundLayer3 = new ParallaxEntity(-7.5f, new Sprite(screenWidth / 2, screenHeight / 2, screenWidth, screenHeight, resourcesManager.game_background_layer_3_region, vbom));
		backgroundLayer4 = new ParallaxEntity(-10.0f, new Sprite(screenWidth / 2, screenHeight / 2, screenWidth, screenHeight, resourcesManager.game_background_layer_4_region, vbom));
		backgroundLayer5 = new ParallaxEntity(-12.5f, new Sprite(screenWidth / 2, screenHeight / 2, screenWidth, screenHeight, resourcesManager.game_background_layer_5_region, vbom));
		backgroundLayer6 = new ParallaxEntity(-15.0f, new Sprite(screenWidth / 2, screenHeight / 2, screenWidth, screenHeight, resourcesManager.game_background_layer_6_region, vbom));
		
		autoParallaxBackground.attachParallaxEntity(backgroundLayer1);
		autoParallaxBackground.attachParallaxEntity(backgroundLayer2);
		autoParallaxBackground.attachParallaxEntity(backgroundLayer3);
		autoParallaxBackground.attachParallaxEntity(backgroundLayer4);
		autoParallaxBackground.attachParallaxEntity(backgroundLayer5);
		autoParallaxBackground.attachParallaxEntity(backgroundLayer6);
		
	    this.setBackground(autoParallaxBackground);
	}

	private void createPhysics() {
		physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, GRAVITY_Y), false);
		physicsWorld.setContactListener(contactListener());
		registerUpdateHandler(physicsWorld);
	}
	
	private void createPlatform() {
		platforms = new Platform[PLATFORMS_QUANTITY];
		
		for (int i = 0; i < PLATFORMS_QUANTITY; i++) {
			platforms[i] = new Platform(PLATFORM_WIDTH * (i + 1), screenHeight/2 + PLATFORM_CENTER_OFFSET_Y, vbom, camera, physicsWorld) {
				@Override
				protected void onManagedUpdate(float pSecondsElapsed) {
					super.onManagedUpdate(pSecondsElapsed);
					if ((player.getX() - this.getX()) > 300) {
						
						this.getPlatformBody().setTransform((this.getX() + PLATFORM_WIDTH * PLATFORMS_QUANTITY) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
								this.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
								this.getPlatformBody().getAngle());
						
						this.setPosition((this.getX() + PLATFORM_WIDTH * PLATFORMS_QUANTITY), this.getY());
					}
				}
			};
			GameScene.this.attachChild(platforms[i]);
			platforms[i].setCullingEnabled(true);
		}
	}
	
	private void createPlayer() {
		decreasingPowerUpBar = false;
		
		landingEffect = new AnimatedSprite(0, 0, resourcesManager.game_jump_landing_effect_region, vbom);
		player = new Player(screenWidth/2, screenHeight/2 + 200, vbom, camera, physicsWorld) {
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				
				if (pressedButtonCounter == 4) {
					pressedButtonCounter = 0;
					player.incrementSpeed();
					
					if (parallaxChangeForSecond <= 10) {
						parallaxChangeForSecond++;
						autoParallaxBackground.setParallaxChangePerSecond(parallaxChangeForSecond);
					}
					
				}
				
				if (this.getY() < 0 && !gameStarted) {
					this.getPlayerBody().setTransform(this.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
							this.getY() + 1500 / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
							this.getPlayerBody().getAngle());
					this.setPosition(this.getX(), this.getY() + 1500);
					this.setPlayerSensor(false);
					this.setTexture(playersRegions.get(selectedPlayerIndex), selectedPlayerIndex);
				}
				
				if (player.isPlayerInPowerUpMode() && !decreasingPowerUpBar) {
					decreasingPowerUpBar = true;
					
					powerUpBarDecreaseModifier = new DelayModifier(POWER_UP_DURATION / POWER_UP_REQUIRED_FRUITS , new IEntityModifierListener() {
						
						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
							
						}
						
						public void onModifierFinished(org.andengine.util.modifier.IModifier<IEntity> pModifier, IEntity pItem) {
							fruitsCounter--;
							resizePowerUpBar();
							decreasingPowerUpBar = false;
							
							if (fruitsCounter > 0) {
								player.registerEntityModifier(powerUpBarDecreaseModifier);
							}
						};
					});
					
					if (fruitsCounter > 0) {
						player.registerEntityModifier(powerUpBarDecreaseModifier);
					}
					
				}
				
			}
		};
		
		player.setZIndex(2);
		landingEffect.setZIndex(3);
		
		GameScene.this.attachChild(player);
		GameScene.this.attachChild(player.getCameraChaseRectangle());
		
		GameScene.this.attachChild(landingEffect);
	}
	
	private void createMenu() {
		gameHud = new HUD();
		
		game_over_x_icon = new Sprite[2];
		
		loadPlayersAndIconsRegions();
		
		menu_title = new Sprite(screenWidth / 2, screenHeight + MENU_TITLE_OFFSET_Y, resourcesManager.game_menu_title_region, vbom);
		
		buttonPlayerSelectLeft = new Sprite(screenWidth / 2 + BUTTON_PLAYER_SELECT_LEFT_CENTER_OFFSET_X, screenHeight / 2 + BUTTON_PLAYER_SELECT_LEFT_CENTER_OFFSET_Y, resourcesManager.game_player_select_left_button_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown() && !gameStarted) {
					//final float playerOriginalX = player.getX();
					//final float playerOriginalY = player.getY();
					
					selectedPlayerIndex--;
					if (selectedPlayerIndex == -1) {
						selectedPlayerIndex = 11;
					}
					
					player.setPlayerSensor(true);

					/*player.registerEntityModifier(
						new MoveModifier(1f, playerOriginalX, playerOriginalY, playerOriginalX - 300, playerOriginalY) {
							protected void onModifierFinished(IEntity pItem) {
								player.setTexture(playersRegions.get(selectedPlayerIndex), selectedPlayerIndex);
								player.registerEntityModifier(
										new MoveModifier(1f, player.getX(), player.getY(), playerOriginalX, playerOriginalY));
							};
						});*/
					
					
					
					gamePlayerIcon.setTextureRegion(playersIconsRegions.get(selectedPlayerIndex));
					game_hud_small_player.setTextureRegion(playersRegions.get(selectedPlayerIndex));
					game_hud_big_player.setTextureRegion(playersRegions.get(selectedPlayerIndex));
				}
				return true;
			}
		};
		buttonPlayerSelectRight = new Sprite(screenWidth / 2 + BUTTON_PLAYER_SELECT_RIGHT_CENTER_OFFSET_X, screenHeight / 2 + BUTTON_PLAYER_SELECT_RIGHT_CENTER_OFFSET_Y, resourcesManager.game_player_select_right_button_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown() && !gameStarted) {
					//final float playerOriginalX = player.getX();
					//final float playerOriginalY = player.getY();
					
					selectedPlayerIndex++;
					if (selectedPlayerIndex == 12) {
						selectedPlayerIndex = 0;
					}
					
					player.setPlayerSensor(true);

					/*player.registerEntityModifier(
						new MoveModifier(1f, playerOriginalX, playerOriginalY, playerOriginalX - 300, playerOriginalY) {
							protected void onModifierFinished(IEntity pItem) {
								player.setTexture(playersRegions.get(selectedPlayerIndex), selectedPlayerIndex);
								player.registerEntityModifier(
										new MoveModifier(1f, player.getX(), player.getY(), playerOriginalX, playerOriginalY));
							};
						});*/
					
					gamePlayerIcon.setTextureRegion(playersIconsRegions.get(selectedPlayerIndex));
					game_hud_small_player.setTextureRegion(playersRegions.get(selectedPlayerIndex));
					game_hud_big_player.setTextureRegion(playersRegions.get(selectedPlayerIndex));
				}
				return true;
			}
		};
		
		gamePlayerIcon =  new Sprite(screenWidth / 2, screenHeight / 2 + BUTTON_PLAYER_ICON_CENTER_OFFSET_Y, resourcesManager.game_player_icon_beaver_region, vbom);
		
		game_hud_small_player = new Sprite(screenWidth / 2 - 300, screenHeight + MENU_POWERUP_HUD_ITEMS_OFFSET_Y, resourcesManager.game_player_beaver_region, vbom);
		game_hud_big_player = new Sprite(screenWidth / 2 + 300, screenHeight + MENU_POWERUP_HUD_ITEMS_OFFSET_Y, resourcesManager.game_player_beaver_region, vbom);
		game_hud_powerup_empty_background = new Rectangle(screenWidth / 2 + 7, screenHeight + MENU_POWERUP_HUD_ITEMS_OFFSET_Y, HUD_POWERUP_EMPTY_BACKGROUND_WIDTH, HUD_POWERUP_EMPTY_BACKGROUND_HEIGHT, vbom);
		game_hud_powerup_bar = new Rectangle(screenWidth / 2, screenHeight + MENU_POWERUP_HUD_ITEMS_OFFSET_Y, 0, HUD_POWERUP_EMPTY_BACKGROUND_HEIGHT, vbom);
		game_hud_powerup_background = new Sprite(screenWidth / 2, screenHeight + MENU_POWERUP_HUD_ITEMS_OFFSET_Y, resourcesManager.game_hud_powerup_background_region, vbom);
		
		game_over_window = new Sprite(screenWidth / 2, screenHeight + GAME_OVER_WINDOW_OFFSET_Y, resourcesManager.game_over_window_region, vbom);
		game_over_fruits_icon = new Sprite(GAME_OVER_FRUITS_ICON_X, GAME_OVER_FRUITS_ICON_Y, resourcesManager.game_over_fruits_icon_region, vbom);
		game_over_enemies_icon = new Sprite(GAME_OVER_ENEMIES_ICON_X, GAME_OVER_ENEMIES_ICON_Y, resourcesManager.game_over_enemies_icon_region, vbom);
		game_over_x_icon[0] = new Sprite(GAME_OVER_FRUITS_ICON_X + 150, GAME_OVER_FRUITS_ICON_Y, resourcesManager.game_over_x_icon_region, vbom);
		game_over_x_icon[1] = new Sprite(GAME_OVER_ENEMIES_ICON_X + 150, GAME_OVER_ENEMIES_ICON_Y, resourcesManager.game_over_x_icon_region, vbom);
		fruitsScoreText = new Text(GAME_OVER_FRUITS_ICON_X + 300, GAME_OVER_FRUITS_ICON_Y, resourcesManager.fruitsScoreFont, "0123456789", vbom);
		enemiesScoreText = new Text(GAME_OVER_ENEMIES_ICON_X + 300, GAME_OVER_ENEMIES_ICON_Y, resourcesManager.enemiesScoreFont, "0123456789", vbom);
		
		buttonReplay = new Sprite(450, -50, resourcesManager.game_button_replay_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					SceneManager.getInstance().createGameScene(2);
				}
				return true;
			}
		};
		
		buttonQuit = new Sprite(150, -50, resourcesManager.game_button_quit_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					System.exit(0);
				}
				return true;
			}
		};
		
		game_hud_background = new Sprite(screenWidth / 2, screenHeight / 2 - 400, resourcesManager.game_hud_background_region, vbom);
		
		game_hud_small_player.setScale(0.5f);
		game_hud_big_player.setScale(0.75f);
		game_hud_powerup_bar.setColor(0.275f, 0.647f, 0.012f);
		
		menu_play_button = new Sprite(screenWidth / 2, screenHeight / 2 + MENU_PLAY_BUTTON_OFFSET_Y, resourcesManager.game_menu_play_button_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown() && !gameStarted) {
					
					createNumericalEnemies();
					createNumericalButtons();
					createFruits();
					
					engine.runOnUpdateThread(new Runnable() {
						
						@Override
						public void run() {
							hideMenu();
						}
					});
					gameStarted = true;
					
				}
				return false;
			}
		};
		
		gameHud.attachChild(menu_title);
		gameHud.attachChild(menu_play_button);
		gameHud.attachChild(game_hud_background);
		gameHud.registerTouchArea(menu_play_button);
		
		gameHud.attachChild(game_hud_powerup_empty_background);
		gameHud.attachChild(game_hud_powerup_bar);
		gameHud.attachChild(game_hud_powerup_background);
		gameHud.attachChild(game_hud_small_player);
		gameHud.attachChild(game_hud_big_player);
		
		gameHud.attachChild(game_over_window);
		
		gameHud.attachChild(buttonPlayerSelectLeft);
		gameHud.attachChild(buttonPlayerSelectRight);
		gameHud.attachChild(gamePlayerIcon);
		
		game_over_window.attachChild(buttonReplay);
		game_over_window.attachChild(buttonQuit);
		game_over_window.attachChild(game_over_fruits_icon);
		game_over_window.attachChild(game_over_enemies_icon);
		game_over_window.attachChild(game_over_x_icon[0]);
		game_over_window.attachChild(game_over_x_icon[1]);
		game_over_window.attachChild(fruitsScoreText);
		game_over_window.attachChild(enemiesScoreText);
		
		gameHud.registerTouchArea(buttonPlayerSelectLeft);
		gameHud.registerTouchArea(buttonPlayerSelectRight);
		gameHud.registerTouchArea(buttonReplay);
		gameHud.registerTouchArea(buttonQuit);
		
		buttonReplay.setVisible(false);
		buttonQuit.setVisible(false);
		
		createNumericalButtons();
		
		camera.setHUD(gameHud);
		
	}
	
	private void loadPlayersAndIconsRegions() {
		playersRegions = new ArrayList<ITextureRegion>();
		playersIconsRegions = new ArrayList<ITextureRegion>();
		
		playersRegions.add(resourcesManager.game_player_beaver_region);
		playersRegions.add(resourcesManager.game_player_bird_2_region);
		playersRegions.add(resourcesManager.game_player_bird_region);
		playersRegions.add(resourcesManager.game_player_bunny_region);
		playersRegions.add(resourcesManager.game_player_elephant_region);
		playersRegions.add(resourcesManager.game_player_giraffe_region);
		playersRegions.add(resourcesManager.game_player_hippo_region);
		playersRegions.add(resourcesManager.game_player_monkey_region);
		playersRegions.add(resourcesManager.game_player_panda_region);
		playersRegions.add(resourcesManager.game_player_penguin_region);
		playersRegions.add(resourcesManager.game_player_pig_region);
		playersRegions.add(resourcesManager.game_player_snake_region);
		
		playersIconsRegions.add(resourcesManager.game_player_icon_beaver_region);
		playersIconsRegions.add(resourcesManager.game_player_icon_bird_2_region);
		playersIconsRegions.add(resourcesManager.game_player_icon_bird_region);
		playersIconsRegions.add(resourcesManager.game_player_icon_bunny_region);
		playersIconsRegions.add(resourcesManager.game_player_icon_elephant_region);
		playersIconsRegions.add(resourcesManager.game_player_icon_giraffe_region);
		playersIconsRegions.add(resourcesManager.game_player_icon_hippo_region);
		playersIconsRegions.add(resourcesManager.game_player_icon_monkey_region);
		playersIconsRegions.add(resourcesManager.game_player_icon_panda_region);
		playersIconsRegions.add(resourcesManager.game_player_icon_penguin_region);
		playersIconsRegions.add(resourcesManager.game_player_icon_pig_region);
		playersIconsRegions.add(resourcesManager.game_player_icon_snake_region);
	}
	
	private void createNumericalEnemies() {
		enemy1 = new Enemy1[ENEMY_1_QUANTITY];
		enemy2 = new Enemy2[ENEMY_2_QUANTITY];
		enemy3 = new Enemy3[ENEMY_3_QUANTITY];
		enemy4 = new Enemy4[ENEMY_4_QUANTITY];
		
		ArrayList<Integer> enemiesPositions = new ArrayList<Integer>();
		
		enemiesPositions.add((int)player.getX() + ENEMY_INITIAL_X);
		enemiesPositions.add((int)player.getX() + ENEMY_INITIAL_X * 2);
		enemiesPositions.add((int)player.getX() + ENEMY_INITIAL_X * 3);
		enemiesPositions.add((int)player.getX() + ENEMY_INITIAL_X * 4);
		
		for (int i = 0; i < ENEMY_1_QUANTITY; i++) {
			
			long seed = System.nanoTime();
			Collections.shuffle(enemiesPositions, new Random(seed));
			
			enemy1[i] = new Enemy1(enemiesPositions.get(0) + i * ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY / ENEMY_1_QUANTITY,  screenHeight/2 + ENEMY_CENTER_OFFSET_Y, vbom, physicsWorld) {
				@Override
				protected void onManagedUpdate(float pSecondsElapsed) {
					super.onManagedUpdate(pSecondsElapsed);
					if ((player.getX() - this.getX()) > 1000) {
						
						this.getEnemy1Body().setTransform((this.getX() + ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
								this.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
								this.getEnemy1Body().getAngle());
						
						this.setPosition((this.getX() + ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY), this.getY());
						
						enemiesCounter++;
					}
				}
			};
			enemy2[i] = new Enemy2(enemiesPositions.get(1) + i * ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY / ENEMY_1_QUANTITY,  screenHeight/2 + ENEMY_CENTER_OFFSET_Y, vbom, physicsWorld){
				@Override
				protected void onManagedUpdate(float pSecondsElapsed) {
					super.onManagedUpdate(pSecondsElapsed);
					if ((player.getX() - this.getX()) > 1000) {
						
						this.getEnemy2Body().setTransform((this.getX() + ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
								this.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
								this.getEnemy2Body().getAngle());
						
						this.setPosition((this.getX() + ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY), this.getY());
						
						enemiesCounter++;
					}
				}
			};
			enemy3[i] = new Enemy3(enemiesPositions.get(2) + i * ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY / ENEMY_1_QUANTITY,  screenHeight/2 + ENEMY_CENTER_OFFSET_Y, vbom, physicsWorld){
				@Override
				protected void onManagedUpdate(float pSecondsElapsed) {
					super.onManagedUpdate(pSecondsElapsed);
					if ((player.getX() - this.getX()) > 1000) {
						
						this.getEnemy3Body().setTransform((this.getX() + ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
								this.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
								this.getEnemy3Body().getAngle());
						
						this.setPosition((this.getX() + ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY), this.getY());
						
						enemiesCounter++;
					}
				}
			};
			enemy4[i] = new Enemy4(enemiesPositions.get(3) + i * ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY / ENEMY_1_QUANTITY,  screenHeight/2 + ENEMY_CENTER_OFFSET_Y, vbom, physicsWorld){
				@Override
				protected void onManagedUpdate(float pSecondsElapsed) {
					super.onManagedUpdate(pSecondsElapsed);
					if ((player.getX() - this.getX()) > 1000) {
						
						this.getEnemy4Body().setTransform((this.getX() + ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
								this.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
								this.getEnemy4Body().getAngle());
						
						this.setPosition((this.getX() + ENEMIES_BETWEEN_DISTANCE * ENEMIES_QUANTITY), this.getY());
						
						enemiesCounter++;
					}
				}
			};
			
			enemy1[i].setZIndex(1);
			enemy2[i].setZIndex(1);
			enemy3[i].setZIndex(1);
			enemy4[i].setZIndex(1);
			
			enemy1[i].setCullingEnabled(true);
			enemy2[i].setCullingEnabled(true);
			enemy3[i].setCullingEnabled(true);
			enemy4[i].setCullingEnabled(true);
			
			platforms[i].setCullingEnabled(true);
			
			GameScene.this.attachChild(enemy1[i]);
			GameScene.this.attachChild(enemy2[i]);
			GameScene.this.attachChild(enemy3[i]);
			GameScene.this.attachChild(enemy4[i]);
			
		}
		
		GameScene.this.sortChildren();

	}
	
	private void createNumericalButtons() {
		button1Disabled = new Sprite(screenWidth/2 + BUTTON_1_OFFSET_X + BUTTON_1_MOVE_MODIFIER_X, screenHeight/2 + BUTTON_1_OFFSET_Y, resourcesManager.game_button_1_disabled_region, vbom);
		button2Disabled = new Sprite(screenWidth/2 + BUTTON_2_OFFSET_X + BUTTON_2_MOVE_MODIFIER_X, screenHeight/2 + BUTTON_2_OFFSET_Y, resourcesManager.game_button_2_disabled_region, vbom);
		button3Disabled = new Sprite(screenWidth/2 + BUTTON_3_OFFSET_X + BUTTON_3_MOVE_MODIFIER_X, screenHeight/2 + BUTTON_3_OFFSET_Y, resourcesManager.game_button_3_disabled_region, vbom);
		button4Disabled = new Sprite(screenWidth/2 + BUTTON_4_OFFSET_X + BUTTON_4_MOVE_MODIFIER_X, screenHeight/2 + BUTTON_4_OFFSET_Y, resourcesManager.game_button_4_disabled_region, vbom);
		
		button1 = new Sprite(screenWidth/2 + BUTTON_1_OFFSET_X + BUTTON_1_MOVE_MODIFIER_X, screenHeight/2 + BUTTON_1_OFFSET_Y, resourcesManager.game_button_1_region, vbom) {
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if (gameStarted) {
					if (enableButton(enemy1[0].getX(), player.getX())
							|| enableButton(enemy1[1].getX(), player.getX())
							|| enableButton(enemy1[2].getX(), player.getX())
							|| enableButton(enemy1[3].getX(), player.getX())) {
				
						this.setVisible(true);
					} else {
						this.setVisible(false);
					}
				}
				
			}
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown() && 
						(enableButton(enemy1[0].getX(), player.getX())
								|| enableButton(enemy1[1].getX(), player.getX())
								|| enableButton(enemy1[2].getX(), player.getX())
								|| enableButton(enemy1[3].getX(), player.getX()))) {
					
					pressedButtonCounter++;
					player.jump();
					
				}
				return false;
			}
		};
		button2 = new Sprite(screenWidth/2 + BUTTON_2_OFFSET_X + BUTTON_2_MOVE_MODIFIER_X, screenHeight/2 + BUTTON_2_OFFSET_Y, resourcesManager.game_button_2_region, vbom) {
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if (gameStarted) {
					if (enableButton(enemy2[0].getX(), player.getX())
							|| enableButton(enemy2[1].getX(), player.getX())
							|| enableButton(enemy2[2].getX(), player.getX())
							|| enableButton(enemy2[3].getX(), player.getX())) {
				
						this.setVisible(true);
					} else {
						this.setVisible(false);
					}
				}
				
			}
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown() && 
						(enableButton(enemy2[0].getX(), player.getX())
								|| enableButton(enemy2[1].getX(), player.getX())
								|| enableButton(enemy2[2].getX(), player.getX())
								|| enableButton(enemy2[3].getX(), player.getX()))) {
					
					pressedButtonCounter++;
					player.jump();
					
				}
				return false;
			}
		};
		button3 = new Sprite(screenWidth/2 + BUTTON_3_OFFSET_X + BUTTON_3_MOVE_MODIFIER_X, screenHeight/2 + BUTTON_3_OFFSET_Y, resourcesManager.game_button_3_region, vbom) {
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if (gameStarted) {
					if (enableButton(enemy3[0].getX(), player.getX())
							|| enableButton(enemy3[1].getX(), player.getX())
							|| enableButton(enemy3[2].getX(), player.getX())
							|| enableButton(enemy3[3].getX(), player.getX())) {
				
						this.setVisible(true);
					} else {
						this.setVisible(false);
					}
				}
				
			}
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown() && 
						(enableButton(enemy3[0].getX(), player.getX())
								|| enableButton(enemy3[1].getX(), player.getX())
								|| enableButton(enemy3[2].getX(), player.getX())
								|| enableButton(enemy3[3].getX(), player.getX()))) {
					
					pressedButtonCounter++;
					player.jump();
					
				}
				return false;
			}
		};
		button4 = new Sprite(screenWidth/2 + BUTTON_4_OFFSET_X + BUTTON_4_MOVE_MODIFIER_X, screenHeight/2 + BUTTON_4_OFFSET_Y, resourcesManager.game_button_4_region, vbom) {
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
				if (gameStarted) {
					if (enableButton(enemy4[0].getX(), player.getX())
							|| enableButton(enemy4[1].getX(), player.getX())
							|| enableButton(enemy4[2].getX(), player.getX())
							|| enableButton(enemy4[3].getX(), player.getX())) {
				
						this.setVisible(true);
					} else {
						this.setVisible(false);
					}
				}
			}
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown() && 
						(enableButton(enemy4[0].getX(), player.getX())
								|| enableButton(enemy4[1].getX(), player.getX())
								|| enableButton(enemy4[2].getX(), player.getX())
								|| enableButton(enemy4[3].getX(), player.getX()))) {
					
					pressedButtonCounter++;
					player.jump();
					
				}
				return false;
			}
		};
		
		gameHud.attachChild(button1Disabled);
		gameHud.attachChild(button2Disabled);
		gameHud.attachChild(button3Disabled);
		gameHud.attachChild(button4Disabled);
		
		gameHud.attachChild(button1);
		gameHud.attachChild(button2);
		gameHud.attachChild(button3);
		gameHud.attachChild(button4);
		
		button1.setVisible(false);
		button2.setVisible(false);
		button3.setVisible(false);
		button4.setVisible(false);
		
		gameHud.registerTouchArea(button1);
		gameHud.registerTouchArea(button2);
		gameHud.registerTouchArea(button3);
		gameHud.registerTouchArea(button4);
	}
	
	private void createFruits() {
		ArrayList<Integer> fruitsTextureOrder = new ArrayList<Integer>();
		int[] fruitsPositions = new int[FRUITS_QUANTITY];
		fruits = new Sprite[FRUITS_QUANTITY];
		ITextureRegion fruitRegion;
		long seed = System.nanoTime();
		
		for (int i = 0; i < FRUITS_QUANTITY; i++) {
			fruitsPositions[i] = (int)player.getX() + FRUITS_INITIAL_X * (i + 1);
			fruitsTextureOrder.add(i + 1);
		}
		
		Collections.shuffle(fruitsTextureOrder, new Random(seed));
		
		for (int i = 0; i < FRUITS_QUANTITY; i++) {
			
			switch (fruitsTextureOrder.get(i)) {
			case 1:
				fruitRegion = resourcesManager.game_fruit_1_region;
				break;
			case 2:
				fruitRegion = resourcesManager.game_fruit_2_region;
				break;
			case 3:
				fruitRegion = resourcesManager.game_fruit_3_region;
				break;
			case 4:
				fruitRegion = resourcesManager.game_fruit_4_region;
				break;
			default:
				fruitRegion = resourcesManager.game_fruit_1_region;
				break;
			}
			
			fruits[i] = new Sprite(fruitsPositions[i], screenHeight / 2 + FRUITS_CENTER_SCREEN_OFFSET_Y, 
					fruitRegion, vbom) {
				@Override
				protected void onManagedUpdate(float pSecondsElapsed) {
					super.onManagedUpdate(pSecondsElapsed);
					if (player.collidesWith(this) && player.isPlayerAlive()) {
						this.setX(this.getX() + FRUITS_BETWEEN_DISTANCE * FRUITS_QUANTITY);
						fruitsGlobalCounter++;
						
						if (!player.isPlayerInPowerUpMode()) {
							fruitsCounter++;
							resizePowerUpBar();
						}
						
						if (fruitsCounter == POWER_UP_REQUIRED_FRUITS) {
							player.registerEntityModifier(new DelayModifier(POWER_UP_DURATION, new IEntityModifierListener() {
								
								@Override
								public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
									player.setPowerUp();
									landingEffect.setScale(JUMP_LANDING_EFFECT_SCALE_FACTOR);
									for (int i = 0; i < ENEMIES_QUANTITY / 4; i++) {
										enemy1[i].getEnemy1Body().setActive(false);
										enemy2[i].getEnemy2Body().setActive(false);
										enemy3[i].getEnemy3Body().setActive(false);
										enemy4[i].getEnemy4Body().setActive(false);
									}
								}
								
								public void onModifierFinished(org.andengine.util.modifier.IModifier<IEntity> pModifier, IEntity pItem) {
									player.unsetPowerUp();
									landingEffect.setScale(1);
									for (int i = 0; i < ENEMIES_QUANTITY / 4; i++) {
										enemy1[i].getEnemy1Body().setActive(true);
										enemy2[i].getEnemy2Body().setActive(true);
										enemy3[i].getEnemy3Body().setActive(true);
										enemy4[i].getEnemy4Body().setActive(true);
									}
								};
							}));
						}
							
					}
					
					if ((player.getX() - this.getX()) > 1000) {
						this.setX(this.getX() + FRUITS_BETWEEN_DISTANCE * FRUITS_QUANTITY);
					}
				}
			};
			
			fruits[i].setCullingEnabled(true);
			GameScene.this.attachChild(fruits[i]);
		}
	}
	
	private void resizePowerUpBar() {
		game_hud_powerup_bar.setWidth(HUD_POWERUP_EMPTY_BACKGROUND_WIDTH / POWER_UP_REQUIRED_FRUITS * fruitsCounter);
		game_hud_powerup_bar.setPosition((game_hud_powerup_empty_background.getX() - HUD_POWERUP_EMPTY_BACKGROUND_WIDTH / 2) + game_hud_powerup_bar.getWidth() / 2, 
				game_hud_powerup_bar.getY());
	}
	
	private boolean enableButton(float enemyX, float playerX) {
		return (enemyX - playerX < DISTANCE_ENABLING_BUTTON) && (enemyX - playerX > 0);
	}
	
	private void disableButtons() {
		gameHud.unregisterTouchArea(button1);
		gameHud.unregisterTouchArea(button2);
		gameHud.unregisterTouchArea(button3);
		gameHud.unregisterTouchArea(button4);
	}
	
	private void hideMenu() {
		final IEaseFunction[] easeFunction = EASEFUNCTIONS[0];
		
		menu_title.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, menu_title.getX(), 
				menu_title.getY(), menu_title.getX(), menu_title.getY() + MENU_TITLE_MOVE_MODIFIER_Y, easeFunction[0]) {
			protected void onModifierFinished(IEntity pItem) {
				displayHUD();
				displayButtons();
			};
		});
		buttonPlayerSelectLeft.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, buttonPlayerSelectLeft.getX(), 
				buttonPlayerSelectLeft.getY(), buttonPlayerSelectLeft.getX(), buttonPlayerSelectLeft.getY() + BUTTON_PLAYER_SELECT_MOVE_MODIFIER_Y, easeFunction[0]));
		buttonPlayerSelectRight.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, buttonPlayerSelectRight.getX(), 
				buttonPlayerSelectRight.getY(), buttonPlayerSelectRight.getX(), buttonPlayerSelectRight.getY() + BUTTON_PLAYER_SELECT_MOVE_MODIFIER_Y, easeFunction[0]));
		gamePlayerIcon.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, gamePlayerIcon.getX(), 
				gamePlayerIcon.getY(), gamePlayerIcon.getX(), gamePlayerIcon.getY() + BUTTON_PLAYER_SELECT_MOVE_MODIFIER_Y, easeFunction[0]));

		gameHud.detachChild(menu_play_button);
		gameHud.unregisterTouchArea(menu_play_button);
	}
	
	private void displayHUD() {
		final IEaseFunction[] easeFunction = EASEFUNCTIONS[0];
		
		game_hud_powerup_empty_background.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, game_hud_powerup_empty_background.getX(), 
				game_hud_powerup_empty_background.getY(), game_hud_powerup_empty_background.getX(), game_hud_powerup_empty_background.getY() - 375, easeFunction[1]));
		game_hud_powerup_bar.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, game_hud_powerup_bar.getX(), 
				game_hud_powerup_bar.getY(), game_hud_powerup_bar.getX(), game_hud_powerup_bar.getY() - 375, easeFunction[1]));
		game_hud_powerup_background.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, game_hud_powerup_background.getX(), 
				game_hud_powerup_background.getY(), game_hud_powerup_background.getX(), game_hud_powerup_background.getY() - 375, easeFunction[1]));
		game_hud_small_player.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, game_hud_small_player.getX(), 
				game_hud_small_player.getY(), game_hud_small_player.getX(), game_hud_small_player.getY() - 375, easeFunction[1]));
		game_hud_big_player.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, game_hud_big_player.getX(), 
				game_hud_big_player.getY(), game_hud_big_player.getX(), game_hud_big_player.getY() - 375, easeFunction[1]));
	}
	
	private void displayButtons() {
		final IEaseFunction[] easeFunction = EASEFUNCTIONS[0];
		
		button1.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, button1.getX(), 
				button1.getY(), button1.getX() - BUTTON_1_MOVE_MODIFIER_X, button1.getY(), easeFunction[0]));
		button2.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, button2.getX(), 
				button2.getY(), button2.getX() - BUTTON_2_MOVE_MODIFIER_X, button2.getY(), easeFunction[0]));
		button3.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, button3.getX(), 
				button3.getY(), button3.getX() - BUTTON_3_MOVE_MODIFIER_X, button3.getY(), easeFunction[0]));
		button4.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, button4.getX(), 
				button4.getY(), button4.getX() - BUTTON_4_MOVE_MODIFIER_X, button4.getY(), easeFunction[0]));
		
		button1Disabled.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, button1Disabled.getX(), 
				button1Disabled.getY(), button1Disabled.getX() - BUTTON_1_MOVE_MODIFIER_X, button1Disabled.getY(), easeFunction[0]));
		button2Disabled.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, button2Disabled.getX(), 
				button2Disabled.getY(), button2Disabled.getX() - BUTTON_2_MOVE_MODIFIER_X, button2Disabled.getY(), easeFunction[0]));
		button3Disabled.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, button3Disabled.getX(), 
				button3Disabled.getY(), button3Disabled.getX() - BUTTON_3_MOVE_MODIFIER_X, button3Disabled.getY(), easeFunction[0]));
		button4Disabled.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, button4Disabled.getX(), 
				button4Disabled.getY(), button4Disabled.getX() - BUTTON_4_MOVE_MODIFIER_X, button4Disabled.getY(), easeFunction[0]));
	}
	
	private void hideButtons() {
		final IEaseFunction[] easeFunction = EASEFUNCTIONS[0];
		
		disableButtons();
		
		button1.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, button1.getX(), 
				button1.getY(), button1.getX() + BUTTON_1_MOVE_MODIFIER_X, button1.getY(), easeFunction[0]));
		button2.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, button2.getX(), 
				button2.getY(), button2.getX() + BUTTON_2_MOVE_MODIFIER_X, button2.getY(), easeFunction[0]));
		button3.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, button3.getX(), 
				button3.getY(), button3.getX() + BUTTON_3_MOVE_MODIFIER_X, button3.getY(), easeFunction[0]));
		button4.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, button4.getX(), 
				button4.getY(), button4.getX() + BUTTON_4_MOVE_MODIFIER_X, button4.getY(), easeFunction[0]));
		
		button1Disabled.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, button1Disabled.getX(), 
				button1Disabled.getY(), button1Disabled.getX() + BUTTON_1_MOVE_MODIFIER_X, button1Disabled.getY(), easeFunction[0]));
		button2Disabled.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, button2Disabled.getX(), 
				button2Disabled.getY(), button2Disabled.getX() + BUTTON_2_MOVE_MODIFIER_X, button2Disabled.getY(), easeFunction[0]));
		button3Disabled.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, button3Disabled.getX(), 
				button3Disabled.getY(), button3Disabled.getX() + BUTTON_3_MOVE_MODIFIER_X, button3Disabled.getY(), easeFunction[0]));
		button4Disabled.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, button4Disabled.getX(), 
				button4Disabled.getY(), button4Disabled.getX() + BUTTON_4_MOVE_MODIFIER_X, button4Disabled.getY(), easeFunction[0]));
	}
	
	private void hideHUD() {
		final IEaseFunction[] easeFunction = EASEFUNCTIONS[0];
		
		hideButtons();
		
		game_hud_powerup_empty_background.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, game_hud_powerup_empty_background.getX(), 
				game_hud_powerup_empty_background.getY(), game_hud_powerup_empty_background.getX(), game_hud_powerup_empty_background.getY() + 375, easeFunction[0]));
		game_hud_powerup_bar.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, game_hud_powerup_bar.getX(), 
				game_hud_powerup_bar.getY(), game_hud_powerup_bar.getX(), game_hud_powerup_bar.getY() + 375, easeFunction[0]));
		game_hud_powerup_background.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, game_hud_powerup_background.getX(), 
				game_hud_powerup_background.getY(), game_hud_powerup_background.getX(), game_hud_powerup_background.getY() + 375, easeFunction[0]));
		game_hud_small_player.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, game_hud_small_player.getX(), 
				game_hud_small_player.getY(), game_hud_small_player.getX(), game_hud_small_player.getY() + 375, easeFunction[0]));
		game_hud_big_player.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS, game_hud_big_player.getX(), 
				game_hud_big_player.getY(), game_hud_big_player.getX(), game_hud_big_player.getY() + 375, easeFunction[0]) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.onModifierFinished(pItem);
				displayGameOverWindow();
			}
		});
	}
	
	private void displayGameOverWindow() {
		final IEaseFunction[] easeFunction = EASEFUNCTIONS[0];
		
		buttonReplay.setVisible(true);
		buttonQuit.setVisible(true);

		fruitsScoreText.setText("" + fruitsGlobalCounter);
		enemiesScoreText.setText("" + enemiesCounter);
		
		game_over_window.registerEntityModifier(new MoveModifier(BUTTONS_MOVE_MODIFIER_DURATION_MILISECONDS + 50, game_over_window.getX(), 
				game_over_window.getY(), game_over_window.getX(), game_over_window.getY() - 600, easeFunction[1]));
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
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();
				
				if (x1.getBody().getUserData().equals("platform") && x2.getBody().getUserData().equals("player") && player.isPlayerAlive() && player.isInAir()) {
					player.setInAir(false);
					if (!player.isPlayerInPowerUpMode()) {
						landingEffect.setPosition(player.getX(), screenHeight/2 + ENEMY_CENTER_OFFSET_Y);
					} else {
						landingEffect.setPosition(player.getX(), screenHeight/2 + ENEMY_CENTER_OFFSET_Y + 40);
					}
					
					landingEffect.animate(LANDING_EFFECT_ANIMATE, 0, 4, false);
				}
				
				if (x1.getBody().getUserData().equals("enemy1") && x2.getBody().getUserData().equals("player") && player.isPlayerAlive() && !player.isPlayerInPowerUpMode()) {
					player.killPlayer();
					hideHUD();
					autoParallaxBackground.setParallaxChangePerSecond(0);
				}
				
				if (x1.getBody().getUserData().equals("enemy2") && x2.getBody().getUserData().equals("player") && player.isPlayerAlive() && !player.isPlayerInPowerUpMode()) {
					player.killPlayer();
					hideHUD();
					autoParallaxBackground.setParallaxChangePerSecond(0);
				}
				
				if (x1.getBody().getUserData().equals("enemy3") && x2.getBody().getUserData().equals("player") && player.isPlayerAlive() && !player.isPlayerInPowerUpMode()) {
					player.killPlayer();
					hideHUD();
					autoParallaxBackground.setParallaxChangePerSecond(0);
				}
				
				if (x1.getBody().getUserData().equals("enemy4") && x2.getBody().getUserData().equals("player") && player.isPlayerAlive() && !player.isPlayerInPowerUpMode()) {
					player.killPlayer();
					hideHUD();
					autoParallaxBackground.setParallaxChangePerSecond(0);
				}
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
