package main.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import tools.Fonts;
import tools.Globals;
import tools.UserInput;
import tools.ui.Label;
import tools.ui.Menu;

public class StateMainMenu extends AbstractState {

	public StateMainMenu() {}

	private Menu menu;
	private BitmapFont font;
	
	@Override
	void init() {
		font = Fonts.font42;
		
		menu = new Menu();
		
		// don't change this for giggles sake - only show if there is a saved game
		menu.addOption(new Label("Contine", font), 0);
		menu.addOption(new Label("New Game", font), 1);
		menu.addOption(new Label("Load Game", font), 2);
		menu.addOption(new Label("Options", font), 3);
		menu.addOption(new Label("Credits", font), 4);
		menu.addOption(new Label("Exit", font), 5);
		menu.addOption(new Label("Test", font), 6);
		
		menu.setX(100);
		menu.setY(500);
		menu.setFont(font);
		menu.setKeyboardInput(true);
		menu.setMouseInput(true);
		menu.setScrollInput(true);
		
		menu.setOptionSpacing(40);
		menu.setFocusXDelta(15);
	}

	@Override
	void open(int previousStateID) {
		// TODO: we need to clear old globals here
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
				// continue
				// set this to the game with the latest last played 
				Globals.gameToLoad = "newGame";
				Globals.stateManager.setState("StateGame");
				break;
			
			case 1:
				// new game
				Globals.stateManager.setState("StateNewGame");
				break;
				
			case 2:
				// load game
				Globals.stateManager.setState("StateLoadGame");
				break;
				
			case 3:
				// options
				break;
				
			case 4:
				// credits
				Globals.stateManager.setState("StateCredits");
				break;
			
			case 5:
				// exit
				Gdx.app.exit();
				break;

			case 6:
				// exit
				Globals.stateManager.setState("StateTest");
				break;	
				
			default:
				break;
			}
			
			menu.clearSelected();
		}
		
		if(menu.escaped()) {
			// do nothing
			menu.clearEscaped();
		}
	}

	@Override
	void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		SpriteBatch batch = new SpriteBatch();
		batch.begin();
			menu.render(batch);
		batch.end();
	}

	@Override
	void close(int nextStateID) {
		
	}

	@Override
	void dispose() {
		
	}

}
