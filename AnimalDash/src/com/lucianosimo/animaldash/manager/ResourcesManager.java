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
	public ITextureRegion game_background_region;
	
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
	private BuildableBitmapTextureAtlas gameBackgroundTextureAtlas;
	
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
		
		gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1000, 1000, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		gameBackgroundTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 720, 1280, TextureOptions.BILINEAR);
		
		game_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundTextureAtlas, activity, "game_background.png");
		
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
			this.gameBackgroundTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameTextureAtlas.load();
			this.gameBackgroundTextureAtlas.load();
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
		this.gameBackgroundTextureAtlas.unload();
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


