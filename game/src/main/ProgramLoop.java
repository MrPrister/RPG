package main;

import game.Game;
import game.actor.animation.AnimationManager;
import main.state.StateManager;
import tools.DelayManager;
import tools.Fonts;
import tools.Globals;
import tools.UserInput;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class ProgramLoop implements ApplicationListener {
	
	@Override
	public void create() {		
		// allows images that aren't powers of 2 to be rendered correctly
		Texture.setEnforcePotImages(false);
		
		resetInputProcessor();
		
		// handles game and input delays
		Globals.animations = new AnimationManager();
		Globals.delayManager = new DelayManager();
		Globals.fonts = new Fonts();
		
		// display the game
		Globals.stateManager = new StateManager("StateSplash");
		Globals.stateManager.start();

		// make the saves folder if it doesn't exist
		FileHandle savesFolder = Gdx.files.local("saves");
		Globals.savesFolder = savesFolder;
		savesFolder.mkdirs();
	}
	
	public static void resetInputProcessor() {
		UserInput inputProcessor = new UserInput();
		Gdx.input.setInputProcessor(inputProcessor);
		Globals.input = inputProcessor;
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void render() {		
		Globals.stateManager.run();
		Globals.input.clear();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
}
