package game;

import game.actor.AbstractPlayer;
import game.actor.animation.AnimationManager;
import game.actor.player.*;
import game.camera.Camera;
import game.camera.Hud;
import game.camera.ScreenTextManager;
import game.level.LevelManager;
import main.SaveFile;
import main.SavePart;
import tools.Globals;

import com.badlogic.gdx.files.FileHandle;

/**
 * setting up the game environment prior to running StateGame
 * @author mattadams
 *
 */
public class Game {
	
	// loads:
	// the level / map
	// the actors in the level
	// the items in the level
	// the objects in the level
	// the player
	
	public SaveFile save;
	public AbstractPlayer player;
	public LevelManager levels;
	
	public Game(FileHandle saveFile, String playerClass) {
		Globals.saveFile = saveFile;
		save = new SaveFile(saveFile);
		open();
		
		// we need to load the levels first, select the level that player is in and then build the player
		boolean fromLoad = false;
		if(load(saveFile)) {
			fromLoad = true;
		} else {
			fromLoad = false;
			save = new SaveFile(saveFile);
			
			// set up the required environment for a new game
			save.create();
			//save.load();
		}
		
		if(fromLoad && Globals.gameSave.existsSubset("levels") && !Globals.gameSave.getSubset("levels").empty()) {
			levels = new LevelManager(Globals.gameSave.getSubset("levels"));
		} else {
			levels = new LevelManager();
		}
		
		if(fromLoad) {
			loadPlayer(Globals.playerSave.get("class", "PlayerRanger"), Globals.playerSave);
		} else {
			newPlayer(playerClass);
		}
		
		// set level to 1 for testing, this is where we would set it to the current player level
		levels.changeLevel(1);
	}
	
	/**
	 * sets up managers required to run the game
	 */
	public void open() {
		Globals.animations = new AnimationManager();
		Globals.screenText = new ScreenTextManager();
		Globals.camera = new Camera();
		Globals.hud = new Hud();
	}
	
	/**
	 * called when the game is exited
	 */
	public void close() {
		save();
		
		Globals.player = null;
		
		Globals.map = null;
		Globals.tileHeight = 0;
		Globals.tileWidth = 0;
		
		Globals.tiles = null;
		Globals.actors = null;
		Globals.items = null;
		Globals.objects = null;
		Globals.screenText = null;
		
		Globals.animations = null;
		Globals.hud = null;
		
		player = null;
		levels = null;
		save = null;
	}
	
	public void save() {
		Globals.gameSave.putSubset("player", player.save());
		Globals.gameSave.putSubset("levels", levels.save());
		
		save.save(Globals.saveFile);
	}
	
	/**
	 * load the save file if it exists otherwise create it
	 */
	public boolean load(FileHandle saveFile) {
		if(saveFile.exists()) {
			Globals.saveFile = saveFile;
			save = new SaveFile(saveFile);
			return save.load();
		} else {
			return false;
		}
	}
	
	
	/**
	 * decide which class the player is and create the player object for that class
	 */
	public void loadPlayer(String playerClass, SavePart playerData) {
		
		System.out.println("loading player [" + playerClass + "]");
		
		if(playerClass.equals("PlayerChampion")) {
			player = new PlayerChampion(playerData);
		} else if(playerClass.equals("PlayerKnight")) {
			player = new PlayerKnight(playerData);
		} else if(playerClass.equals("PlayerRanger")) {
			player = new PlayerRanger(playerData);
		} else if(playerClass.equals("PlayerWizard")) {
			player = new PlayerWizard(playerData);
		} else {
			System.out.println("can't load the player class '" + playerClass + "'");
		}
		
		Globals.player = player;
	}
	
	public void newPlayer(String playerClass) {
		// create the first level, with actors, items, objects etc
		// decide where the player will start in the level
		int startX = 1;
		int startY = 1;
		
		if(playerClass.equals("PlayerChampion")) {
			player = new PlayerChampion(startX, startY);
		} else if(playerClass.equals("PlayerKnight")) {
			player = new PlayerKnight(startX, startY);
		} else if(playerClass.equals("PlayerRanger")) {
			player = new PlayerRanger(startX, startY);
		} else if(playerClass.equals("PlayerWizard")) {
			player = new PlayerWizard(startX, startY);
		} else {
			System.out.println("can't load the player class '" + playerClass + "'");
		}
		
		Globals.player = player;
	}

	
	

}
