package game.item;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import main.SavePart;
import game.item.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ItemManager {

	private SavePart save;
	ArrayList<Item> items;
	
	public ItemManager(SavePart data) {
		save = data;
		items = new ArrayList<Item>();
		
		load(data);
	}
	
	/**
	 * create a new item manager with a blank list of items
	 */
	public ItemManager() {
		save = new SavePart();
		items = new ArrayList<Item>();
	}
	
	/**
	 * clear the current save part, populate a new one and return it
	 * @return
	 */
	public SavePart save() {
		save.clear();
		
		int i = 0;
		
		for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
			Item item = (Item) iterator.next();
			
			if(item.inInventory() == false && item.deleted() == false) {
				save.putSubset("item" + i, item.save());
				i++;
			}
		}
		
		return save;
	}
	
	public void add(Item item) {
		items.add(item);
	}
	
	/**
	 * run the render function on all actors in the actor list
	 * @param batch
	 */
	public void render(SpriteBatch batch) {
		for (Iterator<Item> textIterator = items.iterator(); textIterator.hasNext();) {
			Item item = (Item) textIterator.next();
			item.render(batch);
		}
	}
	
	/**
	 * run the update function on all actors in the actor list
	 * @param delta
	 */
	public void update(float delta) {
		ArrayList<Item> toDelete = new ArrayList<Item>();
		
		for (Iterator<Item> itemIterator = items.iterator(); itemIterator.hasNext();) {
			Item item = (Item) itemIterator.next();
			if(item.deleted()) {
				toDelete.add(item);
			} else {
				item.update(delta);
			}
		}
		
		if(toDelete.size() != 0) {
			for (int i = 0; i < toDelete.size(); i++) {
				Item item = toDelete.get(i);
				items.remove(item);
			}
		}
	}
	
	public Item getItemInTile(Vector2 location) {
		for (Iterator<Item> itemIterator = items.iterator(); itemIterator.hasNext();) {
			Item item = (Item) itemIterator.next();
			
			if(item.getLocation().equals(location) && !item.inInventory()) {
				return item;
			}
		}
		return null;
	}

	public void clear() {
		items.clear();
	}
	
	public void load(SavePart data) {		
		if(data != null && !data.empty()) {
			// add each of the items from the save data to the inventory
			HashMap<String, SavePart> itemList = data.getSubsets();
			for (Entry<String, SavePart> itemPair : itemList.entrySet()) {
				// need to work out which item we need to make using some fancy code here
				
				SavePart item = itemPair.getValue();
				
				try {
					String className = item.get("class");
					@SuppressWarnings("rawtypes")
					Class cl = Class.forName(className);
					@SuppressWarnings({ "rawtypes", "unchecked" })
					Constructor con = cl.getConstructor(SavePart.class);
					Item invItem = (Item) con.newInstance(item);
					add(invItem);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("can't load item [" + item.get("class") + "]");
				}
			}
		}
	}
	
	/**
	 * return an array list of all items that are not in an inventory and not deleted
	 * @return
	 */
	public ArrayList<Item> getLevelItems() {
		
		ArrayList<Item> levelItems = new ArrayList<Item>();
		
		for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
			Item item = (Item) iterator.next();
			
			if(!item.inInventory() && !item.deleted()) {
				levelItems.add(item);
			}
		}
		
		return levelItems;
	}
	
	/**
	 * return an array list of all items marked as inInventory 
	 * @return
	 */
	public ArrayList<Item> getInventoryItems() {
		ArrayList<Item> levelItems = new ArrayList<Item>();
		
		for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
			Item item = (Item) iterator.next();
			
			if(item.inInventory() && !item.deleted()) {
				levelItems.add(item);
			}
		}
		
		return levelItems;
	}

	public boolean remove(Item item) {
		return items.remove(item);
	}
}
