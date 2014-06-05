package game.object.container;

import main.SavePart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class WoodenChest extends Container {

	public WoodenChest(int startX, int startY) {
		super(startX, startY);
	}
	public WoodenChest(SavePart data) {
		super(data);
	}

	@Override
	public Texture getImageFromFile() {
		if(looted) {
			return new Texture(Gdx.files.internal("object/wood-chest-1.png"));
		} else {
			return new Texture(Gdx.files.internal("object/wood-chest-1.png"));
		}
	}

}
