package game.item;

import java.lang.reflect.Constructor;
import main.SavePart;

public class InventorySlot {

	private SavePart save;
	
	protected Item contents;						// the item in this slot
	protected int qty;							// the number of item in this slot
	
	public InventorySlot(SavePart data) {
		save = data;
		contents = null;

		qty = Integer.parseInt(data.get("qty", "0"));
		SavePart item = data.getSubset("item");

		if(item.get("class") != null) {
			try {
				System.out.println("trying to load item '"+item.get("class")+"'");
				
				String className = item.get("class");
				@SuppressWarnings("rawtypes")
				Class cl = Class.forName(className);
				@SuppressWarnings({ "rawtypes", "unchecked" })
				Constructor con = cl.getConstructor(SavePart.class);
				Item invItem = (Item) con.newInstance(item);
				
				System.out.println("adding item to inventory [" + invItem.getClass().getSimpleName() + "]");
				
				contents = invItem;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public InventorySlot() {
		save = new SavePart();
		contents = null;
		qty = 0;
	}
	
	public SavePart save() {
		save.clear();
		
		save.put("qty", qty);
		
		// make a save part for the item and save it
		if(contents != null) {
			contents.save();
			save.putSubset("item", contents.getSavePart());
		} else {
			save.putSubset("item", null);
		}
		
		return save;
	}

	public int getQty() {
		return qty;
	}
	
	public boolean increaseQty(int amount) {
		return increaseQty(amount, true);
	}
	
	public boolean increaseQty(int amount, boolean observeLimit) {
		if(contents != null) {
			qty += amount;
			
			if(observeLimit) {
				int limit = contents.getMaxStack();
				if(qty > limit) {
					qty = limit;
				}
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	public boolean decreaseQty(int amount) {
		return decreaseQty(amount, true, true);
	}
	
	public boolean decreaseQty(int amount, boolean noNegative, boolean deleteIfNone) {
		if(contents != null) {
			qty -= amount;
			
			if(noNegative) {
				if(qty < 0) {
					qty = 0;
				}
			}
			
			if(deleteIfNone && qty <= 0) {
				contents = null;
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * empty the slot
	 */
	public void empty() {
		contents = null;
		qty = 0;
	}
	
	/**
	 * does the slot hold an item or part of an item
	 * @return
	 */
	public boolean isOccupied() {
		if(qty > 0 && contents != null) {
			return true;
		}
		 return false;
	}

	/**
	 * fill the slot with an item
	 * @param item
	 */
	public boolean fill(Item item) {
		if(contents == null) {
			// slot was empty, set the contents to this item and remove it from the game world
			contents = item;
			contents.setX(-1);
			contents.setY(-1);
			contents.setInInventory(true);
			qty++;
			return true;
		} else if(item.getClass() == contents.getClass()) {
			// slot contains item of this type, increase the qty of the current item and set the picked up item to delete
			if(contents.maxStack() >= (qty + 1)) {
				item.setX(-1);
				item.setY(-1);
				item.setInInventory(true);
				qty++;
				return true;
			} else {
				return false;
			}
		} else {
			// slot contains a different item and this item cannot be stacked with it
			return false;
		}
	}
	
	public Item getItem() {
		return contents;
	}
	
	/**
	 * copy the contents of another inventory slot to this one
	 * @param alreadyEquipped
	 */
	public void copy(InventorySlot alreadyEquipped) {
		this.contents = alreadyEquipped.getItem();
		this.qty = alreadyEquipped.getQty();
	}

}
