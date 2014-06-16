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

public class YellowBook extends Item {

	public YellowBook(int x, int y) {
		super(x, y);
	}

	public YellowBook(SavePart data) {
		super(data);
	}
	
	public YellowBook() {
		
	}
	
	@Override
	public Texture itemImage() {
		Texture img = new Texture(Gdx.files.internal("item/book_yellow.png"));
		return img;
	}
	
	@Override
	public String itemName() {
		return "Yellow Book";
	}

	@Override
	public void use() {
		Attribute intellect = Globals.player.attributes.getAttribute("intellect");
		intellect.addToBase(1);
		Globals.screenText.add(new ScreenText("+1 intellect", new Vector2(Globals.player.x, Globals.player.y), Color.GREEN));
		
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
