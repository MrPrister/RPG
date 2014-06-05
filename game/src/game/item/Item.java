package game.item;

import main.SavePart;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import tools.Globals;

public abstract class Item {

	public SavePart save;
	
	// flags
	private boolean deleted;
	private boolean inInventory;
	private boolean canCollect;
	
	private int x;
	private int y;
	
	protected int paddingX;
	protected int paddingY;
	
	private Texture image;
	protected abstract Texture itemImage();
	
	private String name;
	protected abstract String itemName();
	
	private int maxStack;
	protected abstract int maxStack();
	
	public abstract void use();					// code to run when an item is used
	public abstract void pickup();				// code to run when an item is picked up
	public abstract void drop();				// code to run when an item is dropped
	public abstract void destroy();				// code to run when an item is destroyed
	
	public boolean canUse = true;				// can the item be used
	public boolean canDrop = true;				// can the item be dropped
	public boolean canDestory = true;			// can the item be destroyed
	
	//public String useOption = "Use";			// name of the use option in the menu
	public String getUseOption() { return "Use"; }
	//public String dropOption = "Drop";			// name of the drop option in the menu
	public String getDropOption() { return "Drop"; }
	//public String destroyOption = "Destroy";	// name of the destroy option in the menu
	public String getDestroyOption() { return "Destroy"; }
	
	/**
	 * load an item from a save part
	 * @param data
	 */
	public Item(SavePart data) {
		save = data;
		
		x = Integer.parseInt(data.get("xPos", "0"));
		y = Integer.parseInt(data.get("yPos", "0"));
		
		canCollect = Boolean.parseBoolean(data.get("canCollect", "true"));
		inInventory = Boolean.parseBoolean(data.get("inInventory", "false"));
		deleted = Boolean.parseBoolean(data.get("deleted", "false"));
		
		this.image = itemImage();
		this.name = itemName();
		this.maxStack = maxStack();
		
		paddingX = (int) ((Globals.tileWidth - image.getWidth()) / 2);
		paddingY = (int) ((Globals.tileHeight - image.getHeight()) / 2);

	}
	
	/**
	 * create a new item in the inventory
	 */
	public Item() {
		save = new SavePart();
		
		x = 0;
		y = 0;
		canCollect = true;
		inInventory = true;
		deleted = false;
		
		this.image = itemImage();
		this.name = itemName();
		this.maxStack = maxStack();
		
		paddingX = (int) ((Globals.tileWidth - image.getWidth()) / 2);
		paddingY = (int) ((Globals.tileHeight - image.getHeight()) / 2);
	}
	
	/**
	 * create a new item on the map
	 * @param x
	 * @param y
	 */
	public Item(int x, int y) {
		save = new SavePart();
		
		this.x = x;
		this.y = y;
		canCollect = true;
		inInventory = false;
		deleted = false;
		
		this.image = itemImage();
		this.name = itemName();
		this.maxStack = maxStack();
		
		paddingX = (int) ((Globals.tileWidth - image.getWidth()) / 2);
		paddingY = (int) ((Globals.tileHeight - image.getHeight()) / 2);
	}
	
	/**
	 * save this items variables in the save part
	 */
	public SavePart save() {
		save.clear();
		
		// save.put("class", this.getClass().getSimpleName());
		save.put("class", this.getClass().getName());
		
		save.put("xPos", x);
		save.put("yPos", y);
		
		save.put("canCollect", canCollect);
		save.put("inInventory", inInventory);
		save.put("deleted", deleted);
		
		return save;
	}
	
	/**
	 * get the save part for this item
	 * @return
	 */
	public SavePart getSavePart() {
		return save;
	}

	public boolean deleted() {
		return deleted;
	}

	public void update(float delta) {
		
	}

	public void render(SpriteBatch batch) {
		if(!inInventory) {
			batch.draw(
					image,
					(x * Globals.tileWidth) + paddingX,
					(y * Globals.tileHeight) + paddingY
			);
		}
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setLocation(Vector2 location) {
		this.x = (int) location.x;
		this.y = (int) location.y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Vector2 getLocation() {
		return new Vector2(x, y);
	}

	public void setInInventory(boolean b) {
		this.inInventory = b;
	}
	public boolean inInventory() {
		return this.inInventory;
	}

	public String getName() {
		return name;
	}
	
	public Texture getImage() {
		return image;
	}

	public int getMaxStack() {
		return maxStack;
	}

	public boolean canCollect() {
		return canCollect;
	}

	public void setCanCollect(boolean canCollect) {
		this.canCollect = canCollect;
	}


}
