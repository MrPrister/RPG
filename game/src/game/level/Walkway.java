package game.level;

import com.badlogic.gdx.graphics.Texture;

/**
 * creates a set of tiles for walkways between rooms
 * @author mattadams
 *
 */
public class Walkway {

	public int x;
	public int y;
	
	public int length;
	public int width;
	public int direction;
	
	public static int HORIZONTAL = 0;
	public static int VERTICAL = 1;
	
	public Texture floorTexturePack;
	public Texture wallTexturePack;
	
}
