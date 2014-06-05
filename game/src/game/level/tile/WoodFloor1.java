package game.level.tile;

import main.SavePart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import game.level.Tile;

public class WoodFloor1 extends Tile {

	public Texture texture;
	
	public WoodFloor1() {
		super();
	}
	
	public WoodFloor1(int x, int y, boolean blocked, boolean free) {
		super(x, y, blocked, free);
	}

	public WoodFloor1(SavePart data) {
		super(data);
	}

	public Texture getImageFromFile() {
		return new Texture(Gdx.files.internal("tile/wood-horz-1.png"));
	}

}
