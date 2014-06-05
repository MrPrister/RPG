package game.item;

import java.util.HashMap;
import java.util.Map.Entry;

import main.SavePart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

import tools.Fonts;
import tools.Globals;
import tools.Tools;

/**
 * class for holding and managing the player inventory
 * @author mattadams
 *
 */
public class Inventory {

	protected SavePart save;
	//protected ArrayList<InventorySlot> slots;
	protected HashMap<Integer, InventorySlot> slots;
	protected int width;
	
	public Inventory(int num) {
		//slots = new ArrayList<InventorySlot>();
		slots = new HashMap<Integer, InventorySlot>();
		setSlots(num);
		
		save = new SavePart();
	}
	
	public Inventory(SavePart data) {
		save = data;
		
		//slots = new ArrayList<InventorySlot>();
		slots = new HashMap<Integer, InventorySlot>();
		
		SavePart slots = data.getSubset("slots");
		
		if(slots != null) {
			// add each of the items from the save data to the inventory
			HashMap<String, SavePart> slotList = slots.getSubsets();
			
			System.out.println("loading " + slotList.size() + " slots");
			
			for (Entry<String, SavePart> slotPair : slotList.entrySet()) {
				// item loading handled in InventorySlot
				SavePart slot = slotPair.getValue();
				this.slots.put(Integer.parseInt(slotPair.getKey()), new InventorySlot(slot));
			}
		}
	}
	
	public SavePart save() {
		// clear all inventory items
		save.clear();
		
		//save.put("num", slots.size());
		
		SavePart invSlots = new SavePart();
		
		for (Entry<Integer, InventorySlot> slotPair : slots.entrySet()) {
			invSlots.putSubset(String.valueOf(slotPair.getKey()), slotPair.getValue().save());
		}
		
		save.putSubset("slots", invSlots);
		
		return save;
	}

	/**
	 * return an arraylist of all the slots in the inventory
	 * @return
	 */
	public HashMap<Integer, InventorySlot> getSlots() {
		return slots;
	}
	
	/**
	 * return how many slots there are in the inventory
	 * @return
	 */
	public int numSlots() {
		return slots.size();
	}
	
	/**
	 * add empty slots to the inventory
	 * @param number
	 */
	public void addSlots(int number) {
		number = number + slots.size();
		
		for (int i = slots.size(); i < number; i++) {
			//slots.add(new InventorySlot());
			slots.put(i, new InventorySlot());
		}
	}
	
	/**
	 * remove slots from the inventory and drop items to the floor
	 * @param number
	 */
	public void removeSlots(int number) {
		for (int i = 0; i < number; i++) {
			InventorySlot slot = slots.get(slots.size() - 1);
			
			// we need to drop any items that occupy the slots were removing
			if(slot.isOccupied()) {
				// drop the item to the floor
				Item item = slot.getItem();
				item.setLocation(Globals.player.getLocation());
				item.setInInventory(false);
			}
			slots.remove(slots.size() - 1);
			
		}	
	}
	
	/**
	 * set the number of slots in the inventory to the given number, adding or removing slots if required
	 * @param number
	 */
	public void setSlots(int number) {
		int current = slots.size();
		
		if(current < number) {
			// add slots
			addSlots(number - current);
		} else if(current > number) {
			//remove slots
			removeSlots(current - number);
		}
	}
	
	/**
	 * add the given item to the first available space
	 * @param item
	 * @return if the item was added
	 */
	public boolean add(Item item) {
		// look for items that are the same and are not full first
		for (Entry<Integer, InventorySlot> slotPair : slots.entrySet()) {
			InventorySlot slot = slotPair.getValue(); 
			Item slotItem = slot.getItem();
			
			if(slotItem != null && slotItem.getClass() == item.getClass() && slot.getQty() < item.maxStack()) {
				slot.fill(item);
				return true;
			}
		}
		
		// if we've made it here there are no slots to increase the qty on so try and fill a blank slot
		for (Entry<Integer, InventorySlot> slotPair : slots.entrySet()) {
			InventorySlot slot = slotPair.getValue(); 
			
			if(!slot.isOccupied()) {
				slot.fill(item);
				return true;
			}
		}
		
		// if we've made it here then there are no slots the item could be added to
		return false;
	}
	
	public void remove(Item item) {
		
	}
	
	BitmapFont font = Fonts.font16;
	private int slotsWide = 5;					// how many columns of slots to display
	
	public int slotWidth = 50;
	public int slotHeight = 50;
	
	public int slotXPadding = 5;
	public int slotYPadding = 5;
	
	/**
	 * render the inventory to the screen
	 */
	public void render(SpriteBatch batch) {
		HashMap<Integer, InventorySlot> slots = getSlots();
		
		// renderer to draw rectangles
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
    	shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
		
		// overall inventory width and height
		float inventoryWidth = getInventoryWidth();
		float inventoryHeight = getInventoryHeight();
		
		// work out the top left corner where we need to start drawing from
		float invX = getInvX();
		float invY = getInvY();
		
		shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(Color.BLACK);
			shapeRenderer.rect(invX - 5, (invY - inventoryHeight) - 5, inventoryWidth + 10, inventoryHeight + 10);
		shapeRenderer.end();
		shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.WHITE);
			shapeRenderer.rect(invX - 5, (invY - inventoryHeight) - 5, inventoryWidth + 10, inventoryHeight + 10);
		shapeRenderer.end();
		
		for (int i = 0; i < numSlots(); i++) {
			InventorySlot slot = slots.get(i);
			
			Vector2 position = getPos(i);
			
			float slotX = position.x;
			float slotY = position.y;
			
			batch.end();
        	
        	shapeRenderer.begin(ShapeType.Filled);
    			shapeRenderer.setColor(Color.BLACK);
    			shapeRenderer.rect(slotX, slotY, slotWidth, slotHeight);
    		shapeRenderer.end();
			shapeRenderer.begin(ShapeType.Line);
        		shapeRenderer.setColor(Color.BLUE);
        		shapeRenderer.rect(slotX, slotY, slotWidth, slotHeight);
        	shapeRenderer.end();
            
            batch.begin();
            
            // now draw the item if it exists
            if(slot.isOccupied()) {
            	Item item = slot.getItem();
            	Texture image = item.getImage();
            	
            	// draw the image
            	batch.draw(
    					image,
    					slotX + ((slotWidth - image.getWidth()) / 2),
    					slotY + ((slotHeight - image.getHeight()) / 2)
    			);
            	
            	// only draw the quantity if there is more than 1
            	if(slot.getQty() > 1) {
	            	// draw the quantity
	            	String toDraw = " x " + slot.getQty();
	            	
	            	TextBounds bounds = font.getBounds(toDraw);
	            	float textX = bounds.width;
	            	float textY = bounds.height;
	            	
	            	float textXPadding = 4;
	            	float textYPadding = 5;
	            	
	            	font.draw(
							batch,
							toDraw,
							(float) slotX + (slotWidth - textX) - textXPadding,
							(float) slotY + textY + textYPadding
					);
            	}
            }
		}
	}
	
	public int getSlotsHigh() {
		return (int) Math.ceil((double) numSlots() / slotsWide);
	}
	
	public float getInvX() {
		return Tools.centreX(Gdx.graphics.getWidth(), getInventoryWidth());
	}
	
	public float getInvY() {
		return Tools.centreY(Gdx.graphics.getHeight(), getInventoryHeight());
	}
	
	public float getInventoryWidth() {
		return (slotsWide * slotWidth) + ((slotsWide - 1) * slotXPadding);
	}
	
	public float getInventoryHeight() {
		int slotsHigh = getSlotsHigh();
		return (slotsHigh * slotHeight) + ((slotsHigh - 1) * slotYPadding);
	}
	
	/**
	 * return the index of the slot at the given x,y co-ords
	 * @param x
	 * @param y
	 * @return slot index, -1 if not slot at location
	 */
	public int getSlotIndex(float x, float y) {
		
		// loop through each slot and test whether the point x,y is within its bounds
		for (int i = 0; i < numSlots(); i++) {
			Vector2 position = getPos(i);
			
			if(Tools.inBounds(position.x, (position.y + slotHeight), slotWidth, slotHeight, x, y)) {
				return i;
			}
		}
		
		return -1;
	}
	
	public InventorySlot getSlot(int index) {
		if(index == -1) {
			return null;
		}
		return slots.get(index);
	}
	
	public InventorySlot getSlot(float x, float y) {
		return getSlot(getSlotIndex(x,y));
	}
	
	/**
	 * given the index of the slot return the x,y co-ords of the bottom left corner
	 * @param index
	 * @return
	 */
	public Vector2 getPos(int index) {
		int currentColumn = 1 + (index % slotsWide);
		int currentRow = 1 + (int)(index / slotsWide);
		
		float invX = getInvX();
		float invY = getInvY();
		
		// calculate the top left position of the slot on screen
		float slotX = invX + (((currentColumn - 1) * slotWidth) + ((currentColumn - 1) * slotXPadding));
		float slotY = invY - ((currentRow * slotHeight) + ((currentRow - 1) * slotYPadding));
		
		return new Vector2(slotX, slotY);
	}
	
}
