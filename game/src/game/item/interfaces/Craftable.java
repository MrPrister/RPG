package game.item.interfaces;

public interface Craftable {

	public abstract void craft();		// called when the item has been used in a recipe
	public abstract void crafted();		// called when the item has been crafted from a recipe
	
}
