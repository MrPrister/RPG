package game.level.tile;

import main.SavePart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import game.level.Tile;

public class StoneWall1 extends Tile {

	public Texture texture;
	
	public StoneWall1() {
		super();
	}
	
	public StoneWall1(int x, int y, boolean blocked, boolean free) {
		super(x, y, blocked, free);
	}

	public StoneWall1(SavePart data) {
		super(data);
	}

	public Texture getImageFromFile() {
		return new Texture(Gdx.files.internal("tile/stone-wall-1.png"));
	}

}
