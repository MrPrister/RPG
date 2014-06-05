package game.level.tile;

import main.SavePart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import game.level.Tile;

public class WoodFloor2 extends Tile {

	public Texture texture;
	
	public WoodFloor2() {
		super();
	}
	
	public WoodFloor2(int x, int y, boolean blocked, boolean free) {
		super(x, y, blocked, free);
	}

	public WoodFloor2(SavePart data) {
		super(data);
	}

	@Override
	public Texture getImageFromFile() {
		return new Texture(Gdx.files.internal("tile/wood-vert-1.png"));
	}

}
