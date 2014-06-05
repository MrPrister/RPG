package main.state;

import tools.Fonts;
import tools.Globals;
import tools.Tools;
import tools.UserInput;
import tools.random.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class StateGameOver extends AbstractState {
	
	private String randomText;
	
	public StateGameOver() {}
	
	@Override
	void init() {
		
	}

	@Override
	void open(int previousStateID) {
		randomText = (String) Random.object(new String[] {"BIATCH", "NOOOOOOB", "LOOSER"});
	}
	
	@Override
	void input(UserInput input) {
		if(input.anyKeyReleased()) {
			Globals.stateManager.setState("StateMainMenu");
		}	
	}

	@Override
	void update(float delta) {
		
	}

	@Override
	void render() {
		SpriteBatch batch = new SpriteBatch();
		
		Gdx.gl.glClearColor(0.6f, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
			BitmapFont font = Fonts.font56;
			String toDraw = "YOU DEAD, " + randomText + "!";
			TextBounds bounds = font.getBounds(toDraw);
			
			font.draw(
					batch,
					toDraw,
					Tools.centreX(Gdx.graphics.getWidth(), bounds.width),
					Tools.centreY(Gdx.graphics.getHeight(), bounds.height)
			);
			
			font = Fonts.font24;
			TextBounds bounds2 = font.getBounds("press any key to continue");
			
			font.draw(
					batch,
					"press any key to continue",
					Tools.centreX(Gdx.graphics.getWidth(), bounds2.width),
					(Tools.centreY(Gdx.graphics.getHeight(), bounds.height) - bounds.height - 40)
					);
		batch.end();
	}

	@Override
	void close(int nextStateID) {

	}

	@Override
	void dispose() {
		// TODO Auto-generated method stub

	}

}
