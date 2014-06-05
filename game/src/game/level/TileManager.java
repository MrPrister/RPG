package game.level;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import main.SavePart;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * holds the tiles for the given level, used to render and update them (can be used for input if needed)
 * @author mattadams
 *
 */
public class TileManager {

	// hashmap, the tile's location is used as the key and the tile the value
	public ArrayList<Tile> tiles;
	public SavePart save;
	
	public TileManager() {
		save = new SavePart();
		tiles = new ArrayList<Tile>();
	}
	
	public TileManager(SavePart data) {
		save = data;
		tiles = new ArrayList<Tile>();
		
		if(save != null && !save.empty()) {
			HashMap<String, SavePart> tileList = save.getSubsets();
			for (Entry<String, SavePart> tilePair : tileList.entrySet()) {
				SavePart tile = tilePair.getValue();
				
				try {
					String className = tile.get("class");
					@SuppressWarnings("rawtypes")
					Class cl = Class.forName(className);
					@SuppressWarnings({ "rawtypes", "unchecked" })
					Constructor con = cl.getConstructor(SavePart.class);
					Tile aTile = (Tile) con.newInstance(tile);
					add(aTile);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("can't load tile [" + tile.get("class") + "]");
				}
			}
		}
	}
	
	public SavePart save() {
		save.clear();
		
		for (int i = 0; i < tiles.size(); i++) {
			Tile tile = (Tile) tiles.get(i);
			save.putSubset("tile" + i, tile.save());
		}
		
		return save;
	}
	
	public void render(SpriteBatch batch) {
		// render each tile
		for (int i = 0; i < tiles.size(); i++) {
			Tile tile = tiles.get(i);
			
			tile.render(batch);
		}
	}
	
	public void update(float delta) {
		// render each tile
		for (int i = 0; i < tiles.size(); i++) {
			Tile tile = tiles.get(i);
			
			tile.update(delta);
		}
	}
	
	public void add(Tile tile) {
		tiles.add(tile);
	}
	
	/**
	 * return the first tile with the co-ordinates of x and y
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile getTile(int x, int y) {
		for (int i = 0; i < tiles.size(); i++) {
			Tile tile = tiles.get(i);
			
			if(tile.x == x && tile.y == y) {
				return tile;
			}
		}
		
		// no tile was found
		return null;
	}

	public boolean isTile(int checkX, int checkY) {
		
		return false;
	}

}
