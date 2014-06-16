package game.item.consumable;

import main.SavePart;
import game.camera.ScreenText;
import game.item.Item;
import tools.Globals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Apple extends Item {

	@Override
	public String getUseOption() { return "Eat"; }
	
	public Apple(int x, int y) {
		super(x, y);
	}
	
	public Apple(SavePart data) {
		super(data);
	}

	public Apple() {
		// do nothing, used for getting defaults and such
	}

	@Override
	public Texture itemImage() {
		Texture img = new Texture(Gdx.files.internal("item/apple.png"));
		return img;
	}
	
	@Override
	public String itemName() {
		return "Apple";
	}

	@Override
	public void use() {
		Globals.player.addFood(20);
		Globals.screenText.add(new ScreenText("+20 food", new Vector2(Globals.player.x, Globals.player.y), Color.GREEN));
		
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/apple-crunch.mp3"));
		sound.play(0.6f, 1, 0);
	}

	@Override
	public int maxStack() {
		return 5;
	}

	@Override
	public void pickup() {
		
	}

	@Override
	public void drop() {
		
	}

	@Override
	public void destroy() {
		
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
