package com.lucianosimo.animaldash.manager;

import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
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
	
	//Game HUD
	public ITextureRegion game_hud_background_region;
	
	//Players
	public ITextureRegion game_player_region;
	public ITextureRegion game_enemy_1_region;
	public ITextureRegion game_enemy_2_region;
	public ITextureRegion game_enemy_3_region;
	public ITextureRegion game_enemy_4_region;
	
	//Fruits
	public ITextureRegion game_fruit_1_region;
	public ITextureRegion game_fruit_2_region;
	public ITextureRegion game_fruit_3_region;
	public ITextureRegion game_fruit_4_region;
	
	//Platforms
	public ITextureRegion game_platform_region;
	
	//Backgrounds
	public ITextureRegion game_background_1_layer_1_region;
	public ITextureRegion game_background_1_layer_2_region;
	public ITextureRegion game_background_1_layer_3_region;
	public ITextureRegion game_background_1_layer_4_region;
	public ITextureRegion game_background_1_layer_5_region;
	public ITextureRegion game_background_1_layer_6_region;
	
	//Animated
	
	//Menu
	public ITextureRegion game_menu_title_region;
	public ITextureRegion game_menu_play_button_region;
	
	//Windows

	//Buttons
	public ITextureRegion game_button_1_region;
	public ITextureRegion game_button_2_region;
	public ITextureRegion game_button_3_region;
	public ITextureRegion game_button_4_region;
	public ITextureRegion game_button_square_region;
	public ITextureRegion game_button_triangle_region;
	public ITextureRegion game_button_circle_region;
	public ITextureRegion game_button_rectangle_region;

	//Game Textures
	private BuildableBitmapTextureAtlas gameTextureAtlas;
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
		gameBackgroundLayer1TextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1600, 1280, TextureOptions.BILINEAR);
		gameBackgroundLayer2TextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1600, 1280, TextureOptions.BILINEAR);
		gameBackgroundLayer3TextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1600, 1280, TextureOptions.BILINEAR);
		gameBackgroundLayer4TextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1600, 1280, TextureOptions.BILINEAR);
		gameBackgroundLayer5TextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1600, 1280, TextureOptions.BILINEAR);
		gameBackgroundLayer6TextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1600, 1280, TextureOptions.BILINEAR);
		
		game_background_1_layer_1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer1TextureAtlas, activity, "game_background_1_layer_1.png");
		game_background_1_layer_2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer2TextureAtlas, activity, "game_background_1_layer_2.png");
		game_background_1_layer_3_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer3TextureAtlas, activity, "game_background_1_layer_3.png");
		game_background_1_layer_4_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer4TextureAtlas, activity, "game_background_1_layer_4.png");
		game_background_1_layer_5_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer5TextureAtlas, activity, "game_background_1_layer_5.png");
		game_background_1_layer_6_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundLayer6TextureAtlas, activity, "game_background_1_layer_6.png");
		
		game_hud_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_hud_background.png");
		
		game_menu_title_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_menu_title.png");
		game_menu_play_button_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_menu_play_button.png");
		
		game_platform_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_platform.png");
		game_player_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_player.png");
		game_enemy_1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_enemy_1.png");
		game_enemy_2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_enemy_2.png");
		game_enemy_3_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_enemy_3.png");
		game_enemy_4_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_enemy_4.png");
		
		game_fruit_1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_fruit_1.png");
		game_fruit_2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_fruit_2.png");
		game_fruit_3_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_fruit_3.png");
		game_fruit_4_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_fruit_4.png");
		
		game_button_1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_1.png");
		game_button_2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_2.png");
		game_button_3_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_3.png");
		game_button_4_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_4.png");
		game_button_square_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_square.png");
		game_button_triangle_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_triangle.png");
		game_button_circle_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_circle.png");
		game_button_rectangle_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game_button_rectangle.png");
		
		try {
			this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameBackgroundLayer1TextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameBackgroundLayer2TextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameBackgroundLayer3TextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameBackgroundLayer4TextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameBackgroundLayer5TextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameBackgroundLayer6TextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameTextureAtlas.load();
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
		FontFactory.setAssetBasePath("fonts/game/");
	}
	
	private void unloadGameTextures() {
		this.gameTextureAtlas.unload();
		this.gameBackgroundLayer1TextureAtlas.unload();
		this.gameBackgroundLayer2TextureAtlas.unload();
		this.gameBackgroundLayer3TextureAtlas.unload();
		this.gameBackgroundLayer4TextureAtlas.unload();
		this.gameBackgroundLayer5TextureAtlas.unload();
		this.gameBackgroundLayer6TextureAtlas.unload();
	}
	
	private void unloadGameFonts() {
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


