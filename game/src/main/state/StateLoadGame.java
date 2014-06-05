package main.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import tools.Fonts;
import tools.Globals;
import tools.UserInput;
import tools.ui.Label;
import tools.ui.Menu;

public class StateLoadGame extends AbstractState {

	public static String newline = System.getProperty("line.separator");
	
	public StateLoadGame() {}

	@Override
	void init() {

	}

	private Menu loadMenu;
	private BitmapFont font;
	private FileHandle[] saveFiles;
	
	@Override
	void open(int previousStateID) {
		// build a list of all the save game files
		FileHandle saves = Gdx.files.local("saves");
		saveFiles = saves.list();
		
		font = Fonts.font36;
		
		loadMenu = new Menu();
		
		for (int i = 0; i < saveFiles.length; i++) {
			loadMenu.addOption(new Label(saveFiles[i].nameWithoutExtension(), font), i);
		}
		
		loadMenu.setX(100);
		loadMenu.setY(800);
		loadMenu.setFont(font);
		loadMenu.setKeyboardInput(true);
		loadMenu.setMouseInput(true);
		loadMenu.setScrollInput(true);
		
		loadMenu.setOptionSpacing(40);
		loadMenu.setFocusXDelta(15);
	}

	@Override
	void input(UserInput input) {
		loadMenu.input(input);
		
		// accept user input for selecting and deleting game saves
	}

	@Override
	void update(float delta) {
		if(loadMenu.hasSelected()) {
			Globals.gameToLoad = loadMenu.getOptionByIndex(loadMenu.getSelected()).getText();
			Globals.stateManager.setState("StateGame");
		}
	}

	@Override
	void render() {
		// display list of game saves
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		SpriteBatch batch = new SpriteBatch();
		batch.begin();
			loadMenu.render(batch);
		batch.end();
	}

	@Override
	void close(int nextStateID) {
		
	}

	@Override
	void dispose() {
		
	}

}
