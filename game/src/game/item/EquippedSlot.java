package game.item;

import main.SavePart;

public class EquippedSlot extends InventorySlot {

	public EquippedSlot(SavePart data) {
		super(data);
	}
	
	public EquippedSlot() {
		super();
	}

	public EquippedSlot(InventorySlot slot) {
		this.contents = slot.contents;
		this.qty = slot.qty;
	}

}
