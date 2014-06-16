package game.item.interfaces;

import game.item.Inventory;

public interface Weapon extends Equipable {

	public boolean canUse = false;		// weapons are used via keyboard controls not through the inventory
	public int equipSlot = Inventory.equipWeapon;

	public abstract float baseAttack();				// base attack stat
	public abstract float baseAttackRange();		// how far from the base the strike can be
	
	public abstract void attack();		// called when the player attacks with this weapon
	
}
