package game.item.consumable;

import tools.Globals;
import main.SavePart;
import game.actor.attribute.Attribute;
import game.camera.ScreenText;
import game.item.Item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class RedBook extends Item {

	public RedBook(int x, int y) {
		super(x, y);
	}

	public RedBook(SavePart data) {
		super(data);
	}
	
	public RedBook() {
		
	}
	
	@Override
	public Texture itemImage() {
		Texture img = new Texture(Gdx.files.internal("item/book_red.png"));
		return img;
	}
	
	@Override
	public String itemName() {
		return "Red Book";
	}

	@Override
	public void use() {
		Attribute strength = Globals.player.attributes.getAttribute("strength");
		strength.addToBase(1);
		Globals.screenText.add(new ScreenText("+1 strength", new Vector2(Globals.player.x, Globals.player.y), Color.GREEN));
		
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/page_turn.mp3"));
		sound.play(0.6f, 1, 0);
	}

	@Override
	public int maxStack() {
		return 1;
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
