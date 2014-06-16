package game.item.weapon;

import main.SavePart;
import game.item.Item;
import game.item.interfaces.Weapon;

public abstract class AbstractMeleeWeapon extends Item  implements Weapon {
	
	public AbstractMeleeWeapon() {
		super();
	}
	public AbstractMeleeWeapon(int x, int y) {
		super(x, y);
	}
	public AbstractMeleeWeapon(SavePart save) {
		super(save);
	}

	@Override
	public boolean canEquip() {
		return true;
	}
	@Override
	public int equipSlot() {
		return 2;
	}
	@Override
	public boolean canUse() {
		return false;
	}
	@Override
	public boolean canDrop() {
		return true;
	}
	@Override
	public boolean canDestory() {
		return true;
	}

}
