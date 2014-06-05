package tools;

import com.badlogic.gdx.files.FileHandle;

import game.Game;
import game.actor.AbstractPlayer;
import game.actor.ActorManager;
import game.actor.animation.AnimationManager;
import game.camera.Camera;
import game.camera.Hud;
import game.camera.ScreenTextManager;
import game.item.ItemManager;
import game.level.Map;
import game.level.TileManager;
import game.object.ObjectManager;

import main.SavePart;
import main.state.StateManager;


public class Globals {
	
	public static String newLine = "\n";
	public static String tab = "     ";
	
	public static String itemDelimiter = "~";			// separator used for key/value pairs in the save file 
	public static String valueDelimiter = "::";			// separator used for separating the key from the value
	// public static String subSetDelimiter = "|";			// used to identify the start of an array or other subset
	public static String subSetStart = "<";				// identities the start of a subset
	public static String subSetEnd = ">";				// identities the end of a subset
	
	public static FileHandle savesFolder;
	public static FileHandle saveFile;
	
	public static SavePart gameSave;
	public static SavePart levelsSave;
	public static SavePart playerSave;
	public static SavePart playerInventorySave;
	
	// these will change as the current level changes - are these neeeded?
	public static SavePart currentLevelSave;
	public static SavePart mapSave;
	public static SavePart actorsSave;
	public static SavePart itemsSave;
	public static SavePart objectsSave;
	

	public static StateManager stateManager;
	
	// tools
	public static DelayManager delayManager;
	public static Fonts fonts;

	public static Game game;
	public static AnimationManager animations;
	public static ScreenTextManager screenText;
	
	public static TileManager tiles;
	public static ObjectManager objects;
	public static ItemManager items;
	public static ActorManager actors;
	public static AbstractPlayer player;
	
	public static Camera camera;
	public static Map map;
	
	public static Hud hud;

	public static int tileWidth;
	public static int tileHeight;

	public static UserInput input;
	
	// variables for holding info on starting a new game
	public static String newGameName;
	public static String newGameClass;
	
	// holds the name of the game we want to load
	public static String gameToLoad;
	
}
