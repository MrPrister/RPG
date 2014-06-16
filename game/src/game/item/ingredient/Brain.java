package game.item.ingredient;

import main.SavePart;
import game.item.Item;
import game.item.interfaces.Craftable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import tools.*;

public class Brain extends Item implements Craftable {

	public Brain(int x, int y) {
		super(x, y);
	}
	
	public Brain(SavePart data) {
		super(data);
	}
	
	public Brain() {
		
	}

	@Override
	public Texture itemImage() {
		Texture img = new Texture(Gdx.files.internal("item/ingredient_brain.png"));
		return img;
	}
	
	@Override
	public String itemName() {
		return "Brain";
	}

	@Override
	public void use() {
		Globals.player.removeFood(100);
	}

	@Override
	public int maxStack() {
		return 2;
	}

	@Override
	public void craft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void crafted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pickup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canEquip() {
		return false;
	}

	@Override
	public int equipSlot() {
		return 0;
	}

	@Override
	public boolean canUse() {
		return true;
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
