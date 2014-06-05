package game.object;

import tools.Globals;
import main.SavePart;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Object {

	// object save file
	public SavePart save;
	
	public int x;				// tile location
	public int y;				// tile location
	public boolean blocked;		// does this object stop movement
	public boolean immune;		// can the object take damage?
	
	public boolean delete = false;		// flag for whether the item is marked for deletion
	
	public float transX;
	public float transY;
	public float transStep = 2.0f;	// movement speed
	
	// object padding
	protected int paddingX = 0;
	protected int paddingY = 0;
	
	public Texture image;			// the image to render
	
	
	/**
	 * load the actor from the save file
	 * @param save
	 */
	public Object(SavePart objectSave) {
		save = objectSave;
		
		x = Integer.parseInt(save.get("xPos", "1"));
		y = Integer.parseInt(save.get("yPos", "1"));
		
		transX = Float.parseFloat(save.get("transX", "0"));
		transY = Float.parseFloat(save.get("transY", "0"));
		
		blocked = Boolean.parseBoolean(save.get("blocked", "false"));
		immune = Boolean.parseBoolean(save.get("immune", "false"));
		
		//attributes = new AttributeManager(save.getSubset("attributes"));
	}
	
	/**
	 * create actor at the given map coordinates
	 * @param startX
	 * @param startY
	 */
	public Object(int startX, int startY) {
		save = new SavePart();
		create(startX, startY);
	}
	
	public void create(int startX, int startY) {
		x = startX;
		y = startY;
		
		//createAttributes();
	}
	
	public SavePart save() {
		save.clear();
		
		save.put("class", this.getClass().getName());
		
		save.put("xPos", x);
		save.put("yPos", y);
		
		save.put("transX", transX);
		save.put("transY", transY);
		
		save.put("blocked", blocked);
		save.put("immune", immune);
		
		//save.putSubset("attributes", attributes.save());
		
		return save;
	}
	
	/**
	 * renders actor in the screen location
	 * @param g
	 * @param map
	 */
	public void render(SpriteBatch batch) {		
		if(image == null) {
			image = getImage();
		}
		
		float rx = ((x * Globals.tileWidth) + paddingX + (transX * Globals.tileWidth));
		float ry = ((y * Globals.tileHeight) + paddingY + (transY * Globals.tileHeight));
		
		batch.draw(
				image,
    			rx,
    			ry
    	);
	}
	
	private void smoothScrolling(float delta) {
		// calculate translation for smooth scrolling for map
    	if(transX != 0.00f) {
    		if(transX > 0f) {
    			transX -= transStep * delta;
    			if(transX < 0.01f) {
    				transX = 0.0f;
    			}
    		}
    		if(transX < 0f) {
    			transX += transStep * delta;
    			if(transX > -0.01f) {
    				transX = 0.0f;
    			}
    		}
    	}
    	if(transY != 0.00f) {
    		if(transY > 0f) {
    			transY -= (transStep * delta);
    			if(transY < 0.01f) {
    				transY = 0.0f;
    			}
    		}
    		if(transY < 0f) {
    			transY += transStep * delta;
    			if(transY > -0.01f) {
    				transY = 0.0f;
    			}
    		}
    	}
	}
	
	public void setTransX(float x) {
		transX = x;
	}
	public void setTransY(float y) {
		transY = y;
	}
	
	public void update(float delta) {
		smoothScrolling(delta);
	}
	
	public Texture texture;
	public Texture getImage() {
		if(texture == null) {
			texture = getImageFromFile();
		}
		
		return texture;
	}
	abstract public Texture getImageFromFile();
	
	abstract public void interact();			// called when the object is interacted with
	abstract public void damage(int amount);	// called when the object receives damage
	abstract public void destroy();			// called when the object is destroyed
	abstract public void remove();				// called when the object is removed from the level

	public boolean delete() {
		return delete;
	}
	public boolean isImmune() {
		return immune;
	}
	
}
