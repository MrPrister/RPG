package game.level;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

import tools.random.Random;

/**
 * creates a set of tiles for a room within a level
 * @author mattadams
 *
 */
public class Room {

	public ArrayList<Tile> floorTiles;
	public ArrayList<Tile> wallTiles;
	
	public Tile bottomLeftWallTile;
	public Tile bottomRightWallTile;
	public Tile topLeftWallTile;
	public Tile topRightWallTile;
	
	public Tile topWallTile;
	public Tile rightWallTile;
	public Tile bottomWallTile;
	public Tile leftWallTile;
	
	public Tile floorTile;
	
	public Tile doorTile;
	
	
	// record the position of the doors for easy access later
	public HashMap<Integer, Vector2> doors = new HashMap<Integer, Vector2>();
	
	// bottom left tile of the room
	public int x = 0;
	public int y = 0;	
	
	public int width = 0;
	public int height = 0;
	
	
	public Room() {
		floorTiles = new ArrayList<Tile>();
		wallTiles = new ArrayList<Tile>();
	}
	
	/**
	 * add a tile to be used for the flooring in the room
	 * @param tile
	 * @param frequency
	 */
	public void addFloorTile(Tile tile, float frequency) {
		
	}
	/**
	 * add a tile to be used for the walls
	 * @param tile
	 * @param frequency
	 */
	public void addWallTile(Tile tile, float frequency) {
		
	}
	
	public Tile updateTile(Tile tile, int i, int j, boolean blocked, boolean free) {
		tile.x = x + i;
		tile.y = y + j;
		tile.blocked = blocked;
		tile.free = free;
		return tile;
	}
	
	public ArrayList<Tile> generateRoom(int width, int height) {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		
		this.width = width;
		this.height = height;
		
		/*
		 * SUDO:
		 * create a tile list for the room the defined width and height
		 * create 2 doors on different walls and record there location
		 * update the tiles for the doors
		 */
		
		// 1 = north, 2 = east, 3 = south, 4 = west
		
		
		// decide on the location of the 2 doors
		for (int i = 0; i < 2; i++) {
			
			// get random direction for the door, only continue when one is found that hasn't already been used
			int doorDirection = Random.number(1, 4);
			while(doors.containsKey(doorDirection)) {
				doorDirection = Random.number(1, 4);
			}
			
			switch (doorDirection) {
				case 1:
					doors.put(doorDirection, new Vector2(Random.number(1, width - 2), y + height));
					break;
				case 2:
					doors.put(doorDirection, new Vector2(x + width, Random.number(1, width - 2)));		
					break;
				case 3:
					doors.put(doorDirection, new Vector2(Random.number(1, width - 2), y));
					break;
				case 4:
					doors.put(doorDirection, new Vector2(x, Random.number(1, width - 2)));
					break;
				default:
					break;
			}
		}
		
		// i = width
		for (int i = 0; i < width; i++) {
			// j = height
			for (int j = 0; j < height; j++) {
				
				// does this match a door tile
				if(doors.containsValue(new Vector2(i,j))) {
					tiles.add(updateTile(new Tile(doorTile), i, j, false, false));
				} else {
					if(i == 0 && j == 0) {
						// bottom left corner
						tiles.add(updateTile(new Tile(bottomLeftWallTile), i, j, true, false));
					} else if(i == 0 && j == (height - 1)) {
						// top left corner
						tiles.add(updateTile(new Tile(topLeftWallTile), i, j, true, false));
					} else if(i == (width - 1) && j == 0) {
						// bottom right corner
						tiles.add(updateTile(new Tile(bottomRightWallTile), i, j, true, false));
					} else if(i == (width - 1) && j == (height - 1)) {
						// top right corner
						tiles.add(updateTile(new Tile(topRightWallTile), i, j, true, false));
					} else if(i == 0) {
						// left wall
						tiles.add(updateTile(new Tile(leftWallTile), i, j, true, false));
					} else if(i == (width - 1)) {
						// right wall
						tiles.add(updateTile(new Tile(rightWallTile), i, j, true, false));
					} else if(j == 0) {
						// bottom wall
						tiles.add(updateTile(new Tile(bottomWallTile), i, j, true, false));
					} else if(j == (height - 1)) {
						// top wall
						tiles.add(updateTile(new Tile(topWallTile), i, j, true, false));
					} else {
						// floor tile
						tiles.add(updateTile(new Tile(floorTile), i, j, false, true));
					}
				}
			}
		}
		
		return tiles;
	}
	
}
