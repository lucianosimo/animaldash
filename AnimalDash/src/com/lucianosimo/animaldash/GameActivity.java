package com.lucianosimo.animaldash;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import com.lucianosimo.animaldash.manager.ResourcesManager;
import com.lucianosimo.animaldash.manager.SceneManager;

import android.os.Bundle;
import android.view.KeyEvent;

public class GameActivity extends BaseGameActivity {

	private BoundCamera camera;
	
	private final static float SPLASH_DURATION = 5f;
	
	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);
	}
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new BoundCamera(0, 0, 720, 1280);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), this.camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.getRenderOptions().setDithering(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		SceneManager.getInstance().getCurrentScene().handleOnPause();
		mEngine.getSoundManager().setMasterVolume(0);
		mEngine.getMusicManager().setMasterVolume(0);
	}
	
	@Override
	protected synchronized void onResume() {
		super.onResume();
		/*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		int soundEnabled = sharedPreferences.getInt("soundEnabled", 0);
		int musicEnabled = sharedPreferences.getInt("musicEnabled", 0);
		if (soundEnabled == 1) {
			enableSound(false);
		} else if (soundEnabled == 0) {
			enableSound(true);
		}
		if (musicEnabled == 1) {
			enableMusic(false);
		} else if (musicEnabled == 0) {
			enableMusic(true);
		}*/
	}
	
	/*public void enableSound(boolean enable) {
		if (enable) {
			mEngine.getSoundManager().setMasterVolume(1);
		} else {
			mEngine.getSoundManager().setMasterVolume(0);
		}
	}
	
	public void enableMusic(boolean enable) {
		if (enable) {
			mEngine.getMusicManager().setMasterVolume(1);
		} else {
			mEngine.getMusicManager().setMasterVolume(0);
		}
	}*/

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback)	throws IOException {
		ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)	throws IOException {
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);		
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
		mEngine.registerUpdateHandler(new TimerHandler(SPLASH_DURATION, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				SceneManager.getInstance().createGameScene();
			}
		}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new LimitedFPSEngine(pEngineOptions, 60);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			 SceneManager.getInstance().getCurrentScene().onBackKeyPressed(); 
		 }
		return false;
	}

    @Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}	
}
