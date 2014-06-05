package game.actor.player;

import main.SavePart;
import game.actor.AbstractPlayer;
import game.actor.attribute.Attribute;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PlayerRanger extends AbstractPlayer {
	
	public PlayerRanger(SavePart playerData) {
		super(playerData);
	}
	
	public PlayerRanger(int startX, int startY) {
		super(startX, startY);
		
		attributes.add(new Attribute("strength", 10, 1, 3));
		attributes.add(new Attribute("agility", 10, 1, 7));
		attributes.add(new Attribute("intellect", 10, 1, 6));
		attributes.add(new Attribute("luck", 10, 1, 3));
	}

	@Override
	public Texture spriteSheet() {
		spriteWidth = 32;
		spriteHeight = 32;
		return new Texture(Gdx.files.internal("actor/ranger.png"));
	}
	
	@Override
	public int health() {
		return 100;
	}
	
	@Override
	public int food() {
		return 80;
	}
	
}
