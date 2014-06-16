package game.item.interfaces;

public interface Equipable {
	
	public abstract void equipped();		// called when the item has been equipped to an active slot
	public abstract void unequipped();		// called when the item has been unequipped

}
