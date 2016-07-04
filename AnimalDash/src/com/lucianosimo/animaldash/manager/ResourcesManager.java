package com.lucianosimo.animaldash.manager;

import java.util.Random;

import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;

import com.lucianosimo.animaldash.GameActivity;

public class ResourcesManager {

	private static final ResourcesManager INSTANCE = new ResourcesManager();
	
	public Engine engine;
	public BoundCamera camera;
	public GameActivity activity;
	public VertexBufferObjectManager vbom;
	
	//Splash items
	public ITextureRegion splash_region;
	private BitmapTextureAtlas splashTextureAtlas;
		
	//Game audio
	
	//Game fonts
	public Font fruitsScoreFont;
	public Font enemiesScoreFont;
	
	//Game HUD
	public ITextureRegion game_hud_background_region;
	public ITextureRegion game_hud_powerup_background_region;
	
	//Players
	//public ITextureRegion game_player_region;
	public ITextureRegion game_player_beaver_region;
	public ITextureRegion game_player_bird_2_region;
	public ITextureRegion game_player_bird_region;
	public ITextureRegion game_player_bunny_region;
	public ITextureRegion game_player_elephant_region;
	public ITextureRegion game_player_giraffe_region;
	public ITextureRegion game_player_hippo_region;
	public ITextureRegion game_player_monkey_region;
	public ITextureRegion game_player_panda_region;
	public ITextureRegion game_player_penguin_region;
	public ITextureRegion game_player_pig_region;
	public ITextureRegion game_player_snake_region;
	
	public ITextureRegion game_player_icon_beaver_region;
	public ITextureRegion game_player_icon_bird_2_region;
	public ITextureRegion game_player_icon_bird_region;
	public ITextureRegion game_player_icon_bunny_region;
	public ITextureRegion game_player_icon_elephant_region;
	public ITextureRegion game_player_icon_giraffe_region;
	public ITextureRegion game_player_icon_hippo_region;
	public ITextureRegion game_player_icon_monkey_region;
	public ITextureRegion game_player_icon_panda_region;
	public ITextureRegion game_player_icon_penguin_region;
	public ITextureRegion game_player_icon_pig_region;
	public ITextureRegion game_player_icon_snake_region;
	
	//public ITextureRegion game_player_icon_region;
	
	public ITiledTextureRegion game_enemy_1_region;
	public ITiledTextureRegion game_enemy_2_region;
	public ITiledTextureRegion game_enemy_3_region;
	public ITiledTextureRegion game_enemy_4_region;
	
	public ITextureRegion game_enemy_1_sign_region;
	public ITextureRegion game_enemy_2_sign_region;
	public ITextureRegion game_enemy_3_sign_region;
	public ITextureRegion game_enemy_4_sign_region;
	
	//Fruits
	public ITextureRegion game_fruit_1_region;
	public ITextureRegion game_fruit_2_region;
	public ITextureRegion game_fruit_3_region;
	public ITextureRegion game_fruit_4_region;
	
	//Platforms
	public ITextureRegion game_platform_region;
	
	//Backgrounds
	public ITextureRegion game_background_layer_1_region;
	public ITextureRegion game_background_layer_2_region;
	public ITextureRegion game_background_layer_3_region;
	public ITextureRegion game_background_layer_4_region;
	public ITextureRegion game_background_layer_5_region;
	public ITextureRegion game_background_layer_6_region;
	
	//Animated
	public ITiledTextureRegion game_jump_landing_effect_region;
	
	//Menu
	public ITextureRegion game_menu_title_region;
	public ITextureRegion game_menu_play_button_region;
	
	//Windows
	public ITextureRegion game_over_window_region;
	public ITextureRegion game_over_fruits_icon_region;
	public ITextureRegion game_over_enemies_icon_region;
	public ITextureRegion game_over_x_icon_region;

	//Buttons
	public ITextureRegion game_player_select_left_button_region;
	public ITextureRegion game_player_select_right_button_region;
	
	public ITextureRegion game_button_1_region;
	public ITextureRegion game_button_2_region;
	public ITextureRegion game_button_3_region;
	public ITextureRegion game_button_4_region;

	public ITextureRegion game_button_1_disabled_region;
	public ITextureRegion game_button_2_disabled_region;
	public ITextureRegion game_button_3_disabled_region;
	public ITextureRegion game_button_4_disabled_region;
	
	public ITextureRegion game_button_square_region;
	public ITextureRegion game_button_triangle_region;
	public ITextureRegion game_button_circle_region;
	public ITextureRegion game_button_rectangle_region;
	
	public ITextureRegion game_button_replay_region;
	public ITextureRegion game_button_quit_region;

	//Game Textures
	private BuildableBitmapTextureAtlas gameTextureAtlas;
	private BuildableBitmapTextureAtlas gamePlayerTextureAtlas;
	private BuildableBitmapTextureAtlas gamePlayerIconsTextureAtlas;
	private BuildableBitmapTextureAtlas gameHudTextureAtlas;
	private BuildableBitmapTextureAtlas animatedTextureAtlas;
	private BuildableBitmapTextureAtlas gameBackgroundLayer1TextureAtlas;
	private BuildableBitmapTextureAtlas gameBackgroundLayer2TextureAtlas;
	private BuildableBitmapTextureAtlas gameBackgroundLayer3TextureAtlas;
	private BuildableBitmapTextureAtlas gameBackgroundLayer4TextureAtlas;
	private BuildableBitmapTextureAtlas gameBackgroundLayer5TextureAtlas;
	private BuildableBitmapTextureAtlas gameBackgroundLayer6TextureAtlas;
	
	//Splash Methods
	public void loadSplashScreen() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 720, 1280, TextureOptions.BILINEAR);
		splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
		splashTextureAtlas.load();
	}
	
	public void unloadSplashScreen() {
		splashTextureAtlas.unload();
		splash_region = null;
	}
	
	//Game Methods
	public void loadGameResources() {
		loadGameGraphics();
		loadGameAudio();
		loadGameFonts();
	}
	
	public void unloadGameResources() {
		unloadGameTextures();
		unloadGameFonts();	
		unloadGameAudio();
	}
	
	private void loadGameGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
		
		gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1500, 1500, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		gamePlayerTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1200, 400, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		gamePlayerIconsTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1500, 500, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		gameHudTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1500, 1500, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		animatedTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1500, 500, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		gameBackgroundLayer1TextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1600, 1280, TextureOptions.BILINEAR);
		gameBackgroundLayer2TextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1600, 1280, TextureOptions.BILINEAR);
		gameBackgroundLayer3TextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1600, 1280, TextureOptions.BILINEAR);
		gameBackgroundLayer4TextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1600, 1280, TextureOptions.BILINEAR);
		gameBackgroundLayer5TextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1600, 1280, TextureOptions.BILINEAR);
		gameBackgroundLayer6TextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1600, 1280, TextureOptions.BILINEAR);
		
		//Randomize backgrounds
		Random rand = new Random();
		int selectedBackground = rand.nextInt(4 - 1 + 1) + 1;
		
		switch (selectedBackground) {
		case 1:
			game_background_layer_1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer1TextureAtlas, activity, "game_background_1_layer_1.png");
			game_background_layer_2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer2TextureAtlas, activity, "game_background_1_layer_2.png");
			game_background_layer_3_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer3TextureAtlas, activity, "game_background_1_layer_3.png");
			game_background_layer_4_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer4TextureAtlas, activity, "game_background_1_layer_4.png");
			game_background_layer_5_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer5TextureAtlas, activity, "game_background_1_layer_5.png");
			game_background_layer_6_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer6TextureAtlas, activity, "game_background_1_layer_6.png");
			break;
			
		case 2:
			game_background_layer_1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer1TextureAtlas, activity, "game_background_2_layer_1.png");
			game_background_layer_2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer2TextureAtlas, activity, "game_background_2_layer_2.png");
			game_background_layer_3_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer3TextureAtlas, activity, "game_background_2_layer_3.png");
			game_background_layer_4_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer4TextureAtlas, activity, "game_background_2_layer_4.png");
			game_background_layer_5_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer5TextureAtlas, activity, "game_background_2_layer_5.png");
			game_background_layer_6_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer6TextureAtlas, activity, "game_background_2_layer_6.png");
			break;
			
		case 3:
			game_background_layer_1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer1TextureAtlas, activity, "game_background_3_layer_1.png");
			game_background_layer_2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer2TextureAtlas, activity, "game_background_3_layer_2.png");
			game_background_layer_3_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer3TextureAtlas, activity, "game_background_3_layer_3.png");
			game_background_layer_4_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer4TextureAtlas, activity, "game_background_3_layer_4.png");
			game_background_layer_5_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer5TextureAtlas, activity, "game_background_3_layer_5.png");
			game_background_layer_6_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer6TextureAtlas, activity, "game_background_3_layer_6.png");
			break;

		case 4:
			game_background_layer_1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer1TextureAtlas, activity, "game_background_4_layer_1.png");
			game_background_layer_2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer2TextureAtlas, activity, "game_background_4_layer_2.png");
			game_background_layer_3_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer3TextureAtlas, activity, "game_background_4_layer_3.png");
			game_background_layer_4_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer4TextureAtlas, activity, "game_background_4_layer_4.png");
			game_background_layer_5_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer5TextureAtlas, activity, "game_background_4_layer_5.png");
			game_background_layer_6_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer6TextureAtlas, activity, "game_background_4_layer_6.png");
			break;

		default:
			game_background_layer_1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer1TextureAtlas, activity, "game_background_1_layer_1.png");
			game_background_layer_2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer2TextureAtlas, activity, "game_background_1_layer_2.png");
			game_background_layer_3_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer3TextureAtlas, activity, "game_background_1_layer_3.png");
			game_background_layer_4_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer4TextureAtlas, activity, "game_background_1_layer_4.png");
			game_background_layer_5_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer5TextureAtlas, activity, "game_background_1_layer_5.png");
			game_background_layer_6_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer6TextureAtlas, activity, "game_background_1_layer_6.png");
			break;
		}
		
		game_player_beaver_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerTextureAtlas, activity, "game_player_beaver.png");
		game_player_bird_2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerTextureAtlas, activity, "game_player_bird_2.png");
		game_player_bird_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerTextureAtlas, activity, "game_player_bird.png");
		game_player_bunny_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerTextureAtlas, activity, "game_player_bunny.png");
		game_player_elephant_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerTextureAtlas, activity, "game_player_elephant.png");
		game_player_giraffe_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerTextureAtlas, activity, "game_player_giraffe.png");
		game_player_hippo_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerTextureAtlas, activity, "game_player_hippo.png");
		game_player_monkey_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerTextureAtlas, activity, "game_player_monkey.png");
		game_player_panda_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerTextureAtlas, activity, "game_player_panda.png");
		game_player_penguin_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerTextureAtlas, activity, "game_player_penguin.png");
		game_player_pig_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerTextureAtlas, activity, "game_player_pig.png");
		game_player_snake_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerTextureAtlas, activity, "game_player_snake.png");
		
		game_player_icon_beaver_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerIconsTextureAtlas, activity, "game_player_icon_beaver.png");
		game_player_icon_bird_2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerIconsTextureAtlas, activity, "game_player_icon_bird_2.png");
		game_player_icon_bird_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerIconsTextureAtlas, activity, "game_player_icon_bird.png");
		game_player_icon_bunny_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerIconsTextureAtlas, activity, "game_player_icon_bunny.png");
		game_player_icon_elephant_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerIconsTextureAtlas, activity, "game_player_icon_elephant.png");
		game_player_icon_giraffe_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerIconsTextureAtlas, activity, "game_player_icon_giraffe.png");
		game_player_icon_hippo_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerIconsTextureAtlas, activity, "game_player_icon_hippo.png");
		game_player_icon_monkey_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerIconsTextureAtlas, activity, "game_player_icon_monkey.png");
		game_player_icon_panda_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerIconsTextureAtlas, activity, "game_player_icon_panda.png");
		game_player_icon_penguin_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerIconsTextureAtlas, activity, "game_player_icon_penguin.png");
		game_player_icon_pig_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerIconsTextureAtlas, activity, "game_player_icon_pig.png");
		game_player_icon_snake_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gamePlayerIconsTextureAtlas, activity, "game_player_icon_snake.png");
		
		
		game_hud_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameHudTextureAtlas, activity, "game_hud_background.png");
		game_hud_powerup_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameHudTextureAtlas, activity, "game_hud_powerup_background.png");
		
		game_menu_title_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_menu_title.png");
		game_menu_play_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_menu_play_button.png");
		
		game_over_window_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_over_window.png");
		game_over_fruits_icon_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_fruits_icon.png");
		game_over_enemies_icon_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_enemies_icon.png");
		game_over_x_icon_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_x_icon.png");
		
		game_platform_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_platform.png");
		
		game_jump_landing_effect_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "game_jump_landing_effect.png", 5, 1);

		game_enemy_1_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "game_enemy_1.png", 4, 1);
		game_enemy_2_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "game_enemy_2.png", 4, 1);
		game_enemy_3_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "game_enemy_3.png", 4, 1);
		game_enemy_4_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animatedTextureAtlas, activity, "game_enemy_4.png", 4, 1);
		
		game_enemy_1_sign_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_enemy_1_sign.png");
		game_enemy_2_sign_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_enemy_2_sign.png");
		game_enemy_3_sign_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_enemy_3_sign.png");
		game_enemy_4_sign_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_enemy_4_sign.png");
		
		game_fruit_1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_fruit_1.png");
		game_fruit_2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_fruit_2.png");
		game_fruit_3_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_fruit_3.png");
		game_fruit_4_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_fruit_4.png");
		
		game_player_select_left_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_player_select_left_button.png");
		game_player_select_right_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_player_select_right_button.png");
		
		game_button_1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_1.png");
		game_button_2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_2.png");
		game_button_3_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_3.png");
		game_button_4_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_4.png");
		
		game_button_1_disabled_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_1_disabled.png");
		game_button_2_disabled_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_2_disabled.png");
		game_button_3_disabled_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_3_disabled.png");
		game_button_4_disabled_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_4_disabled.png");
		
		game_button_square_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_square.png");
		game_button_triangle_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_triangle.png");
		game_button_circle_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_circle.png");
		game_button_rectangle_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_rectangle.png");
		
		game_button_replay_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_replay.png");
		game_button_quit_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_quit.png");
		
		try {
			this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gamePlayerTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gamePlayerIconsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameHudTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.animatedTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameBackgroundLayer1TextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameBackgroundLayer2TextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameBackgroundLayer3TextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameBackgroundLayer4TextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameBackgroundLayer5TextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameBackgroundLayer6TextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			
			this.gameTextureAtlas.load();
			this.gamePlayerTextureAtlas.load();
			this.gamePlayerIconsTextureAtlas.load();
			this.gameHudTextureAtlas.load();
			this.animatedTextureAtlas.load();
			this.gameBackgroundLayer1TextureAtlas.load();
			this.gameBackgroundLayer2TextureAtlas.load();
			this.gameBackgroundLayer3TextureAtlas.load();
			this.gameBackgroundLayer4TextureAtlas.load();
			this.gameBackgroundLayer5TextureAtlas.load();
			this.gameBackgroundLayer6TextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	private void loadGameAudio() {
		MusicFactory.setAssetBasePath("music/game/");
	}
	
	public void unloadGameAudio() {

		System.gc();
	}
	
	private void loadGameFonts() {
		FontFactory.setAssetBasePath("fonts/");
		final ITexture fruitsScoreTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		final ITexture enemiesScoreTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		fruitsScoreFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), fruitsScoreTexture, activity.getAssets(), "dimbo.ttf", 85, true, Color.WHITE_ARGB_PACKED_INT, 3f, Color.BLACK_ARGB_PACKED_INT);
		enemiesScoreFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), enemiesScoreTexture, activity.getAssets(), "dimbo.ttf", 85, true, Color.WHITE_ARGB_PACKED_INT, 3f, Color.BLACK_ARGB_PACKED_INT);

		fruitsScoreFont.load();
		enemiesScoreFont.load();
	}
	
	private void unloadGameTextures() {
		this.gameTextureAtlas.unload();
		this.gamePlayerTextureAtlas.unload();
		this.gamePlayerIconsTextureAtlas.unload();
		this.gameHudTextureAtlas.unload();
		this.animatedTextureAtlas.unload();
		this.gameBackgroundLayer1TextureAtlas.unload();
		this.gameBackgroundLayer2TextureAtlas.unload();
		this.gameBackgroundLayer3TextureAtlas.unload();
		this.gameBackgroundLayer4TextureAtlas.unload();
		this.gameBackgroundLayer5TextureAtlas.unload();
		this.gameBackgroundLayer6TextureAtlas.unload();
	}
	
	private void unloadGameFonts() {
		fruitsScoreFont.unload();
		enemiesScoreFont.unload();
	}
	
	
	//Manager Methods
	public static void prepareManager(Engine engine, GameActivity activity, BoundCamera camera, VertexBufferObjectManager vbom) {
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;		
	}
	
	public static ResourcesManager getInstance() {
		return INSTANCE;
	}

}


