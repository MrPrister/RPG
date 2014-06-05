package game.level;

import java.util.ArrayList;

import tools.Globals;
import tools.random.Random;

import game.level.Map;
import game.level.tile.*;
import game.actor.ActorManager;
import game.actor.Goblin;
import game.actor.Zombie;
import game.item.ItemManager;
import game.item.consumable.Apple;
import game.object.Entrance;
import game.object.Exit;
import game.object.ObjectManager;
import game.object.container.WoodenChest;
import game.object.container.WoodenCrate;

//import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import main.SavePart;

public class Level {

	public SavePart save;
	
	public int levelNumber;
	
	public Map map;
	public int tileWidth;
	public int tileHeight;
	
	public TileManager tiles;
	public ActorManager actors;
	public ItemManager items;
	public ObjectManager objects;
	
	public float size;
	public float difficulty;
	
	/**
	 * load the level from the save data
	 * @param data
	 */
	public Level(SavePart data) {
		save = data;
		
		// load the map
	    Globals.tileWidth = 48;
	    Globals.tileHeight = 48;
		
	    if(save.existsSubset("tiles")) {
	    	tiles = new TileManager(save.getSubset("tiles"));
	    } else {
	    	tiles = new TileManager();
	    }
	    
	    if(save.existsSubset("actors")) {
	    	actors = new ActorManager(save.getSubset("actors"));
	    } else {
	    	actors = new ActorManager();
	    }
	    
	    if(save.existsSubset("items")) {
	    	items = new ItemManager(save.getSubset("items"));
	    } else {
	    	items = new ItemManager();
	    }
	    
	    if(save.existsSubset("objects")) {
	    	objects = new ObjectManager(save.getSubset("objects"));
	    } else {
	    	objects = new ObjectManager();
	    }
		
		levelNumber = Integer.valueOf(save.get("levelNumber", "1"));
	}
	
	/**
	 * create a new level
	 */
	public Level(int levelNum) {
		save = new SavePart();
		
		levelNumber = levelNum;
		
		// build the map
		//map = new Map(new TmxMapLoader().load("data/map1.tmx"), 1f);
		
	    //tileWidth = (int) map.getTileWidth();
	    //tileHeight = (int) map.getTileHeight();
	    //Globals.tileWidth = tileWidth;
	    //Globals.tileHeight = tileHeight;
	    
	    Globals.tileWidth = 48;
	    Globals.tileHeight = 48;
	    
	    tiles = new TileManager();
	    actors = new ActorManager();
	    items = new ItemManager();
	    objects = new ObjectManager();
		
	    // build the new level
		create();
	}
	
	public void updateGlobals() {
		System.out.println("updating globals");
		
		//Globals.map = map;
		Globals.tileWidth = 48;
		Globals.tileHeight = 48;
		
		Globals.tiles = tiles;
		Globals.actors = actors;
		Globals.items = items;
		Globals.objects = objects;
	}
	
	public SavePart save() {
		save.clear();

		save.put("levelNumber", levelNumber);
		
		save.putSubset("tiles", tiles.save());
		save.putSubset("actors", actors.save());
		save.putSubset("items", items.save());
		save.putSubset("objects", objects.save());
		
		return save;
	}
	
	public SavePart getSavePart() {
		return save;
	}

	/**
	 * create the level 
	 */
	public void create() {
		//for (int i = 0; i < 20; i++) {
		//	for (int j = 0; j < 20; j++) {
		//		tiles.add(new WoodFloor1(i, j, false, true));
		//	}
		//}
		Room room = new Room();
		
		room.floorTile = new WoodFloor1();
		room.doorTile = new WoodFloor1();
		
		room.leftWallTile = new StoneWall1();
		room.rightWallTile = new StoneWall1();
		room.topWallTile = new StoneWall1();
		room.bottomWallTile = new StoneWall1();
		room.bottomLeftWallTile = new StoneWall1();
		room.bottomRightWallTile = new StoneWall1();
		room.topLeftWallTile = new StoneWall1();
		room.topRightWallTile = new StoneWall1();
		
		ArrayList<Tile> tileList = room.generateRoom(Random.number(4, 20), Random.number(4, 20));
		for (int i = 0; i < tileList.size(); i++) {
			tiles.add(tileList.get(i));
		}
		
		actors.add(new Zombie(4,4));
		actors.add(new Zombie(8,4));
		actors.add(new Zombie(11,7));
		actors.add(new Zombie(3,9));
		actors.add(new Goblin(6,6));
		actors.add(new Goblin(2,8));
		
		items.add(new Apple(7,6));
		items.add(new Apple(8,6));
		items.add(new Apple(9,6));
		items.add(new Apple(6,7));
		items.add(new Apple(6,8));
		items.add(new Apple(7,7));
		items.add(new Apple(7,8));
		items.add(new Apple(8,8));
		
		objects.add(new Entrance(1,1));
		objects.add(new Exit(11,15));
		objects.add(new WoodenCrate(3,3));
	}

	public int getLevelNumber() {
		return levelNumber;
	}
}