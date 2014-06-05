package game.level;

import tools.Globals;
import main.SavePart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * describes a tile
 * @author mattadams
 *
 */
public class Tile {

	public SavePart save;
	
	public Texture texture;
	
	public int x;					// x location of this tile
	public int y;					// y location of this tile
	public boolean blocked;			// can the player walk into the tile
	public boolean free;			// flag for whether the tile can have objects etc added to it
	
	public int rotation;			// int describing the rotation of the tile texture
	
	public Tile() {
		save = new SavePart();
	}
	
	// for cloning
	public Tile(Tile tile) {
		save = new SavePart();
		
		this.x = tile.x;
		this.y = tile.y;
		this.blocked = tile.blocked;
		this.free = tile.free;
		this.rotation = tile.rotation;
		
		if(tile.texture != null) {
			this.texture = tile.texture;
		} else {
			this.texture = tile.getImage();
		}
	}
	
	public Tile(int x, int y, boolean blocked, boolean free) {
		save = new SavePart();
		
		this.x = x;
		this.y = y;
		this.blocked = blocked;
		this.free = free;
	}
	
	public Tile(SavePart data) {
		save = data;
		
		x = save.getInt("x");
		y = save.getInt("y");
		blocked = save.getBoolean("blocked");
		free = save.getBoolean("free");
		
		rotation = save.getInt("rotation");
	}
	
	/**
	 * return a string for the save file describing this tile
	 * @return
	 */
	public SavePart save() {
		save.clear();
		
		save.put("class", this.getClass().getName());
		
		save.put("x", x);
		save.put("y", y);
		save.put("blocked", blocked);
		save.put("free", free);
		save.put("rotation", rotation);
		
		return save;
	}
	
	/**
	 * render the tile
	 * @param batch
	 */
	public void render(SpriteBatch batch) {
		batch.draw(
			getImage(),
			(float) (x * Globals.tileWidth),
			(float) (y * Globals.tileHeight),
			Globals.tileWidth,
			Globals.tileHeight
		);
	}
	
	/**
	 * update the tile
	 * @param delta
	 */
	public void update(float delta) {
		
	}
	
	public Texture getImage() {
		if(texture == null) {
			texture = getImageFromFile();
		}
		return texture;
	}
	
	public Texture getImageFromFile() {
		return new Texture(Gdx.files.internal("404.png"));
	}
	
	/**
	 * is this tile marked as blocked?
	 * @return
	 */
	public boolean isBlocked() {
		return blocked ? true : false;
	}
	/**
	 * are objects allowed to be placed in this tile
	 * @return
	 */
	public boolean isFree() {
		return free ? true : false;
	}
	
}
