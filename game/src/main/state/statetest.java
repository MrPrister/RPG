package main.state;

import game.item.consumable.Apple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import tools.Fonts;
import tools.Globals;
import tools.UserInput;
import tools.random.Random;
import tools.ui.Image;
import tools.ui.Label;
import tools.ui.Menu;
import tools.ui.TextInput;

public class StateTest extends AbstractState {

	public StateTest() {}

	private Label label;
	private TextInput input;
	private Image image;
	
	@Override
	void init() {
		
	}

	public Menu menu;
	public boolean showMenu = false;
	
	@Override
	void open(int previousStateID) {
		for (int i = 1; i <= 10; i++) {
			float avg = 0;
			float avg2 = 0;
			float avg3 = 0;
			float avg4 = 0;
			
			for (int j = 1; j <= 100; j++) {
				avg += Random.numberLuck(0, 100, i);
				avg2 += Random.numberLuck(0, 100, i);
				avg3 += Random.numberLuck(0, 100, i);
				avg4 += Random.numberLuck(0, 100, i);
			}
			System.out.println("luck: " + i + " avg results 1) " + (avg / 100) + ", 2) " + (avg2 / 100) + ", 3) " + (avg3 / 100) + ", 4) " + (avg4 / 100));
		}
	}

	@Override
	void input(UserInput input) {
		if(input.keyReleased(Keys.ESCAPE)) {
			Globals.stateManager.setState("StateMainMenu");
		}
		
	}

	@Override
	void update(float delta) {
		//label.input(Globals.input);
		//label.update(delta);
		
		//input.input(Globals.input);
		//input.update(delta);
	}

	@Override
	void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		SpriteBatch batch = new SpriteBatch();
		batch.begin();
			
			
		
		batch.end();
	}

	@Override
	void close(int nextStateID) {
		Globals.delayManager.add("credits", 0);
	}

	@Override
	void dispose() {
		
	}

}
