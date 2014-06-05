package game.level;

import java.util.ArrayList;

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
	
	// bottom left tile of the room
	public int x = 0;
	public int y = 0;		
	
	
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
		
		// 1 = north, 2 = east, 3 = south, 4 = west
		int doorDirection = Random.number(1, 4);
		int doorLoc = 0;
		
		if(doorDirection == 1 || doorDirection == 3) {
			doorLoc = Random.number(1, height - 2);
		} else {
			doorLoc = Random.number(1, width - 2);
		}
		
		// i = width
		for (int i = 0; i < width; i++) {
			// j = height
			for (int j = 0; j < height; j++) {
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
				} else if(doorDirection == 1 && j == (height - 1) && doorLoc == i) {
					// top wall door
					tiles.add(updateTile(new Tile(doorTile), i, j, false, false));
				} else if(doorDirection == 2 && i == (width - 1) && doorLoc == j) {
					// right wall door
					tiles.add(updateTile(new Tile(doorTile), i, j, false, false));
				} else if(doorDirection == 3 && j == 0 && doorLoc == i) {
					// bottom wall door
					tiles.add(updateTile(new Tile(doorTile), i, j, false, false));
				} else if(doorDirection == 4 && i == 0 && doorLoc == j) {
					// left wall door
					tiles.add(updateTile(new Tile(doorTile), i, j, false, false));
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
		
		return tiles;
	}
	
}
