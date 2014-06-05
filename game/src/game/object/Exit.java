package game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import main.SavePart;

public class Exit extends Object {
	
	public Exit(SavePart objectSave) {
		super(objectSave);
	}

	public Exit(int startX, int startY) {
		super(startX, startY);
	}

	@Override
	public Texture getImageFromFile() {
		return new Texture(Gdx.files.internal("object/exit.png"));
	}
	
	@Override
	public void interact() {
		System.out.println("exit interact called");
	}

	@Override
	public void damage(int amount) {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void remove() {

	}

}
