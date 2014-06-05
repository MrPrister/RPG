package game.item.interfaces;

public interface Usable {

	public boolean canUse = true;		// flag for if you can use the item
	
	public abstract void use();			// called when you use the item (from inventory menu)
	
}
