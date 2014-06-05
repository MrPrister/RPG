package game.object;

import game.object.Object;
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
public class ObjectManager {

	SavePart save;
	ArrayList<Object> objects;
	
	public ObjectManager() {
		save = new SavePart();
		objects = new ArrayList<Object>();
	}
	
	public ObjectManager(SavePart data) {
		save = data;
		objects = new ArrayList<Object>();
		
		load(data);
	}
	
	/**
	 * clear the current save part, populate a new one and return it
	 * @return
	 */
	public SavePart save() {
		save.clear();
		
		int i = 0;
		
		for (Iterator<Object> iterator = objects.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();

			save.putSubset("object" + i, object.save());
			i++;
		}
		
		return save;
	}
	
	/**
	 * add the actor to the actor list
	 * @param actor
	 */
	public void add(Object object) {
		objects.add(object);
	}
	
	/**
	 * remove the actor from the actor list
	 * @param actor
	 */
	public void remove(Object object) {
		objects.remove(object);
	}
	
	/**
	 * run the render function on all actors in the actor list
	 * @param g
	 */
	public void render(SpriteBatch batch) {
		for (Iterator<Object> iterator = objects.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			object.render(batch);
		}
	}
	
	/**
	 * run the update function on all actors in the actor list
	 * @param delta
	 */
	public void update(float delta) {
		ArrayList<Object> toDelete = new ArrayList<Object>();
		
		for (Iterator<Object> iterator = objects.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if(object.delete()) {
				object.remove();
				toDelete.add(object);
			} else {
				object.update(delta);
			}
		}
		
		if(toDelete.size() != 0) {
			for (int i = 0; i < toDelete.size(); i++) {
				Object object = toDelete.get(i);
				objects.remove(object);
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
		for (Iterator<Object> iterator = objects.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if(object.x == x && object.y == y) {
				return true;
			}
		}
		
		//if(Globals.player.x == x && Globals.player.y == y) {
		//	return true;
		//}
		
		return false;
	}
	
	public boolean isOccupied(Vector2 tile) {
		return isOccupied((int) tile.x, (int) tile.y);
	}
	
	public boolean isOccupied(float x, float y) {
		return isOccupied((int) x, (int) y);
	}
	
	public Object getObjectInTile(float x, float y) {
		for (Iterator<Object> iterator = objects.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if(object.x == x && object.y == y) {
				return object;
			}
		}
		
		// no object was found
		return null;
	}
	
	public void damageObjectInTile(Vector2 tile, int amount) {
		for (Iterator<Object> iterator = objects.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if(object.x == tile.x && object.y == tile.y && !object.isImmune()) {
				object.damage(amount);
				Globals.screenText.add(new ScreenText("-" + amount, tile, Color.RED));
				return;
			}
		}
	}
	
	public void interact(Vector2 tile) {
		interact(tile.x, tile.y);
	}

	/**
	 * call the interact method on the object in tile x, y
	 * @param x
	 * @param y
	 */
	public void interact(float x, float y) {
		Object object = getObjectInTile(x,y);
		
		if(object != null) {
			object.interact();
		}
	}
	
	

	public void clear() {
		objects.clear();
	}

	public void load(SavePart data) {		
		// it's possible that no actors are on the level so check if the save part is empty
		if(data != null && !data.empty()) {
			HashMap<String, SavePart> actorList = data.getSubsets();
			for (Entry<String, SavePart> objectPair : actorList.entrySet()) {
				// need to work out which actor we need to make using some fancy code here
				
				SavePart object = objectPair.getValue();
				
				try {
					String className = object.get("class");
					@SuppressWarnings("rawtypes")
					Class cl = Class.forName(className);
					@SuppressWarnings({ "rawtypes", "unchecked" })
					Constructor con = cl.getConstructor(SavePart.class);
					Object anObject = (Object) con.newInstance(object);
					add(anObject);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("can't load object [" + object.get("class") + "]");
				}
			}
		}
	}

}
