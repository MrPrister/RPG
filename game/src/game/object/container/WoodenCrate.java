package game.object.container;

import main.SavePart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class WoodenCrate extends Container {

	public WoodenCrate(int startX, int startY) {
		super(startX, startY);
	}
	public WoodenCrate(SavePart data) {
		super(data);
	}

	public Texture getImageFromFile() {
		if(looted) {
			return new Texture(Gdx.files.internal("object/crate-1-open.png"));
		} else {
			return new Texture(Gdx.files.internal("object/crate-1-closed.png"));
		}
	}

}
