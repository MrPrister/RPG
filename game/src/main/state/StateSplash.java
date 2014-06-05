package main.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;

import tools.Globals;
import tools.UserInput;

public class StateSplash extends AbstractState {
	
	public StateSplash() {}

	@Override
	void init() {
		
	}

	@Override
	void open(int previousStateID) {
		Globals.delayManager.add("splashScreen", 5f);
	}

	@Override
	void input(UserInput input) {
		if(input.keyReleased(Keys.ESCAPE)) {
			Globals.delayManager.add("splashScreen", 0);
		}
	}

	@Override
	void update(float delta) {
		if(Globals.delayManager.isDelayFinished("splashScreen")) {
			Globals.stateManager.setState("StateMainMenu");
		}
	}

	@Override
	void render() {
		Gdx.gl.glClearColor(0, 0.6f, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}

	@Override
	void close(int nextStateID) {
		
	}

	@Override
	void dispose() {
		
	}

}
