package main;

import tools.Globals;
import tools.Tools;

import com.badlogic.gdx.files.FileHandle;

public class SaveFile {

	/*
	 key, value pair 			- 			key::value
	 key, value pairs 			- 			key::value~key::value~key::value
	 key, value subset	 		- 			key::|key::value~key::value|
	 
	 1 save file per game, main sections (player, levels, actors, items, objects, etc) separated with a key::|subset|
	 eg:
	 lastPlayed::01-01-01 00:00:00
	 ~
	 levels::|
	 	level1::|
	 		actors::|
	 			actor1::|
	 				xPos::1~
	 				yPos::1~
	 				health::10~
	 				food::100~
	 				facing::3~
	 				dead::false~
	 				moving::false~
	 				path::|
	 					x1::1~
	 					y1::2~
	 					x2::1~
	 					y2::1
	 				|
	 			|
	 		|~
	 		items::|
	 			item1::|
	 				type::brain~
	 				xPos::3~
	 				yPos::2~
	 				inInventory::false~
	 			|
	 		|~
	 		objects::|
	 			object1::|
	 				type::chest~
	 				xPos::6~
	 				yPos::3~
	 				inventory::|
	 					slot1::|
	 						type::goldCoin~
	 						qty::2~
	 					|
	 				|
	 			|
	 		|
	 	|
	 |
	 player::|
	 	currentLevel::2~
	 	health::100~
	 	food::100~
	 	facing::1~
	 	locked::false~
	 	dead::false~
	 	moving::false~
	 	inventory::|
	 		slot1::|
	 			item::|
	 				type::apple~
	 				qty::1~
	 			|
	 		|
	 	|
	 |
	 */
	
	private FileHandle saveLocation;
	
	public SaveFile(FileHandle saveFile) {
		saveLocation = saveFile;
	}
	
	/**
	 * try to load a saved game from a save file
	 * @param file
	 * @return
	 */
	public boolean load() {
		String rawContents = "";
		
		try {
			rawContents = saveLocation.readString();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		Globals.gameSave = new SavePart(rawContents);
		
		Globals.levelsSave = Globals.gameSave.getSubset("levels");
		Globals.playerSave = Globals.gameSave.getSubset("player");
		
		Globals.playerInventorySave = Globals.playerSave.getSubset("inventory");
		
		// we let another function decide which level is the current level and load
		// the necessary handles for that as it will need to be called multiple times
		
		return true;
	}
	
	/**
	 * save the current file to the default location
	 * @return
	 */
	public boolean save() {
		return save(saveLocation);
	}
	
	/**
	 * save the current game to the save file
	 * @param saveFile
	 */
	public boolean save(FileHandle saveFile) {
		String content = Globals.gameSave.compile();
		
	    try {
	    	saveFile.writeString(content, false);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return false;
	    }
		
		return true;
	}
	
	/**
	 * create the basics of a new save file and save it
	 */
	public boolean create() {
		String content =
				"lastPlayed" + Globals.valueDelimiter + Tools.getDate()
				+ Globals.itemDelimiter + "levels" + Globals.valueDelimiter + Globals.subSetStart + Globals.subSetEnd
				+ Globals.itemDelimiter + "player" + Globals.valueDelimiter + Globals.subSetStart
					+ "xPos" + Globals.valueDelimiter + "1"
					+ Globals.itemDelimiter
					+ "yPos" + Globals.valueDelimiter + "1"
					+ Globals.itemDelimiter
					+ "inventory" + Globals.valueDelimiter + Globals.subSetStart
						+ "slots" + Globals.valueDelimiter + Globals.subSetStart + Globals.subSetEnd
					+ Globals.subSetEnd
				+ Globals.subSetEnd
		;
		
		try {
	    	saveLocation.writeString("", false);
	    } catch (Exception e) {
	    	return false;
	    }
		
		Globals.gameSave = new SavePart("");
		
		return true;
	}

}
