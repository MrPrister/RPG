package game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import main.SavePart;

public class Entrance extends Object {
	
	public Entrance(SavePart objectSave) {
		super(objectSave);
	}

	public Entrance(int startX, int startY) {
		super(startX, startY);
	}

	@Override
	public Texture getImageFromFile() {
		return new Texture(Gdx.files.internal("object/entrance.png"));
	}
	
	@Override
	public void interact() {
		System.out.println("entrance interact called");
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
