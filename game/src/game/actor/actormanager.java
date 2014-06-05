package game.actor;

import game.actor.pathfind.AStar;
import game.camera.ScreenText;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import main.SavePart;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import tools.Globals;

/**
 * calls the relevant functions for all actors
 * @author Matt-SSD
 *
 */
public class ActorManager {

	SavePart save;
	ArrayList<Actor> actors;
	
	public ActorManager() {
		save = new SavePart();
		actors = new ArrayList<Actor>();
	}
	
	public ActorManager(SavePart data) {
		save = data;
		actors = new ArrayList<Actor>();
		
		load(data);
	}
	
	/**
	 * clear the current save part, populate a new one and return it
	 * @return
	 */
	public SavePart save() {
		save.clear();
		
		int i = 0;
		
		for (Iterator<Actor> iterator = actors.iterator(); iterator.hasNext();) {
			Actor actor = (Actor) iterator.next();
			
			if(!actor.isDead()) {
				save.putSubset("actor" + i, actor.save());
				i++;
			}
		}
		
		return save;
	}
	
	/**
	 * add the actor to the actor list
	 * @param actor
	 */
	public void add(Actor actor) {
		actors.add(actor);
	}
	
	/**
	 * remove the actor from the actor list
	 * @param actor
	 */
	public void remove(Actor actor) {
		actors.remove(actor);
	}
	
	/**
	 * run the render function on all actors in the actor list
	 * @param g
	 */
	public void render(SpriteBatch batch) {
		for (Iterator<Actor> actorIterator = actors.iterator(); actorIterator.hasNext();) {
			Actor actor = (Actor) actorIterator.next();
			actor.render(batch);
		}
	}
	
	/**
	 * run the update function on all actors in the actor list
	 * @param delta
	 */
	public void update(float delta) {
		ArrayList<Actor> toDelete = new ArrayList<Actor>();
		
		for (Iterator<Actor> actorIterator = actors.iterator(); actorIterator.hasNext();) {
			Actor actor = (Actor) actorIterator.next();
			if(actor.isDead()) {
				actor.death();
				toDelete.add(actor);
			} else {
				actor.update(delta);
			}
		}
		
		if(toDelete.size() != 0) {
			for (int i = 0; i < toDelete.size(); i++) {
				Actor actor = toDelete.get(i);
				actors.remove(actor);
			}
		}
	}

	/**
	 * does an actor exist in the given tile
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isOccupied(int x, int y) {
		for (Iterator<Actor> actorIterator = actors.iterator(); actorIterator.hasNext();) {
			Actor actor = (Actor) actorIterator.next();
			if(actor.x == x && actor.y == y) {
				return true;
			}
		}
		
		//if(Globals.player.x == x && Globals.player.y == y) {
		//	return true;
		//}
		
		return false;
	}
	
	public boolean isOccupied(float x, float y) {
		return isOccupied((int) x, (int) y);
	}
	
	public void hurtActorInTile(Vector2 tile, int amount) {
		for (Iterator<Actor> actorIterator = actors.iterator(); actorIterator.hasNext();) {
			Actor actor = (Actor) actorIterator.next();
			if(actor.x == tile.x && actor.y == tile.y) {
				actor.hurt(amount);
				Globals.screenText.add(new ScreenText("-" + amount, tile, Color.RED));
				return;
			}
		}
	}
	
	// testing
	public void buildPaths() {
		// build the path to the player for each actor
		for (Iterator<Actor> actorIterator = actors.iterator(); actorIterator.hasNext();) {
			Actor actor = (Actor) actorIterator.next();
			AStar pathfind = new AStar();
			
			System.out.println("building path for " + actor.toString());
			actor.setPath(pathfind.path(actor.getLocation(), Globals.player.getLocation(), false));
		}
	}

	public void clear() {
		actors.clear();
	}

	public void load(SavePart data) {		
		// it's possible that no actors are on the level so check if the save part is empty
		if(data != null && !data.empty()) {
			HashMap<String, SavePart> actorList = data.getSubsets();
			for (Entry<String, SavePart> actorPair : actorList.entrySet()) {
				// need to work out which actor we need to make using some fancy code here
				
				SavePart actor = actorPair.getValue();
				
				try {
					String className = actor.get("class");
					@SuppressWarnings("rawtypes")
					Class cl = Class.forName(className);
					@SuppressWarnings({ "rawtypes", "unchecked" })
					Constructor con = cl.getConstructor(SavePart.class);
					Actor anActor = (Actor) con.newInstance(actor);
					add(anActor);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("can't load actor [" + actor.get("class") + "]");
				}
			}
		}
	}

}
