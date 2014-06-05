package main.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import tools.Fonts;
import tools.Globals;
import tools.Tools;
import tools.UserInput;

public class StateCredits extends AbstractState {

	public StateCredits() {}

	private BitmapFont font;
	private BitmapFont heading;
	
	@Override
	void init() {
		heading = Fonts.font24;
		font = Fonts.font36;
	}

	@Override
	void open(int previousStateID) {
		Globals.delayManager.add("credits", 10);
	}

	@Override
	void input(UserInput input) {
		if(input.keyReleased(Keys.ESCAPE)) {
			Globals.delayManager.add("credits", 0);
			// state will be changed when update is called next
		}
	}

	@Override
	void update(float delta) {
		if(Globals.delayManager.isDelayFinished("credits")) {
			Globals.stateManager.setState("StateMainMenu");
		}
	}

	@Override
	void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		SpriteBatch batch = new SpriteBatch();
		batch.begin();
			String toDraw;
			TextBounds bounds;
			
			toDraw = "Made By:";
			bounds = heading.getBounds(toDraw);
			
			heading.draw(
					batch,
					toDraw,
					Tools.centreX(Gdx.graphics.getWidth(), bounds.width),
					Tools.centreY(Gdx.graphics.getHeight(), (bounds.height + 40))
			);
			
			toDraw = "Matt Adams [MrPrister]";
			bounds = font.getBounds(toDraw);
			font.draw(
					batch,
					toDraw,
					Tools.centreX(Gdx.graphics.getWidth(), bounds.width),
					Tools.centreY(Gdx.graphics.getHeight(), (bounds.height - 40))
			);
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
