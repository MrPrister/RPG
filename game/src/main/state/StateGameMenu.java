package main.state;

import tools.Fonts;
import tools.Globals;
import tools.UserInput;
import tools.ui.Label;
import tools.ui.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StateGameMenu extends AbstractState {

	
	public StateGameMenu() {}
	
	private Menu menu;
	private BitmapFont font;
	
	@Override
	void init() {		
		font = Fonts.font32;
		
		menu = new Menu();
		menu.addOption(new Label("Resume", font), 0);
		menu.addOption(new Label("Options", font), 1);
		menu.addOption(new Label("Exit", font), 2);
		
		menu.setX(100);
		menu.setY(500);
		menu.setFont(font);
		menu.setInline(false);
		menu.setOptionSpacing(60);
		menu.setFocusXDelta(10);
		
		
		menu.setKeyboardInput(true);
		menu.setMouseInput(true);
		menu.setScrollInput(true);
	}

	@Override
	void open(int previousStateID) {
		
	}
	
	@Override
	void input(UserInput input) {
		menu.input(input);
	}

	@Override
	void update(float delta) {
		if(menu.hasSelected()) {
			switch (menu.getSelected()) {
			case 0:
				// resume
				Globals.stateManager.setState("StateGame");
				break;
			
			case 1:
				// options
				break;
				
			case 2:
				// exit
				Globals.stateManager.setState("StateMainMenu");
				break;

			default:
				break;
			}
			
			menu.clearSelected();
		}
		
		if(menu.escaped()) {
			Globals.stateManager.setState("StateGame");
			menu.clearEscaped();
		}
	}

	@Override
	void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// render the game - call the render method from the game state
		AbstractState gameState = Globals.stateManager.getStateByName("StateGame");
		gameState.render();
		
		// render the menu
		SpriteBatch batch = new SpriteBatch();
		batch.begin();
			Texture overlay = new Texture(Gdx.files.internal("trans-70-black.png"));
			batch.draw(overlay, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			
			// render the menu
			menu.render(batch);
			
		batch.end();
	}

	@Override
	void close(int nextStateID) {
		if(nextStateID == Globals.stateManager.getStateID("StateMainMenu")) {
			Globals.game.close();
			Globals.game = null;
			
			Globals.player = null;
			Globals.map = null;
			Globals.tileHeight = 0;
			Globals.tileWidth = 0;
			
			Globals.actors = null;
			Globals.items = null;
			Globals.animations = null;
			Globals.screenText = null;
		}
	}

	@Override
	void dispose() {
		// TODO Auto-generated method stub

	}

}
