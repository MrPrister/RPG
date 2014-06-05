package game.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import main.SavePart;

public class LevelManager {

	private SavePart save;
	private ArrayList<Level> levels;
	private Level currentLevel;
	
	public LevelManager() {
		save = new SavePart();
		levels = new ArrayList<Level>();
		
		levels.add(new Level(1));
	}
	
	public LevelManager(SavePart data) {
		save = data;
		levels = new ArrayList<Level>();
		
		HashMap<String, SavePart> levelList = data.getSubsets();
		for (Entry<String, SavePart> levelPair : levelList.entrySet()) {
			SavePart level = levelPair.getValue();
			
			levels.add(new Level(level));
		}
	}
	
	/**
	 * clear the current save part, populate a new one and return it
	 * @return
	 */
	public SavePart save() {
		save.clear();
		
		for (Iterator<Level> iterator = levels.iterator(); iterator.hasNext();) {
			Level level = (Level) iterator.next();
			
			save.putSubset("level" + level.getLevelNumber(), level.save());
		}
		
		return save;
	}
	
	public boolean changeLevel(int levelNumber) {
		// change the current level to the level with the given level number
		
		for (Iterator<Level> levelList = levels.iterator(); levelList.hasNext();) {
			Level level = (Level) levelList.next();
			
			if(level.getLevelNumber() == levelNumber) {
				currentLevel = level;
				level.updateGlobals();
				return true;
			}
		}
		
		return false;
	}
	
	
}
