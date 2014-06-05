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

public class GreenBook extends Item {

	public GreenBook(int x, int y) {
		super(x, y);
	}

	public GreenBook(SavePart data) {
		super(data);
	}
	
	public GreenBook() {
		
	}
	
	@Override
	public Texture itemImage() {
		Texture img = new Texture(Gdx.files.internal("item/book_green.png"));
		return img;
	}
	
	@Override
	public String itemName() {
		return "Green Book";
	}

	@Override
	public void use() {
		Attribute agility = Globals.player.attributes.getAttribute("agility");
		agility.addToBase(1);
		Globals.screenText.add(new ScreenText("+1 agility", new Vector2(Globals.player.x, Globals.player.y), Color.GREEN));
		
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

}
