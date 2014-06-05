package game.actor.player;

import main.SavePart;
import game.actor.AbstractPlayer;
import game.actor.attribute.Attribute;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PlayerChampion extends AbstractPlayer {
	
	public PlayerChampion(SavePart playerData) {
		super(playerData);
	}
	
	public PlayerChampion(int startX, int startY) {
		super(startX, startY);
		
		attributes.add(new Attribute("strength", 10, 1, 5));
		attributes.add(new Attribute("agility", 10, 1, 5));
		attributes.add(new Attribute("intellect", 10, 1, 5));
		attributes.add(new Attribute("luck", 10, 1, 3));
	}

	@Override
	public Texture spriteSheet() {
		spriteWidth = 32;
		spriteHeight = 32;
		return new Texture(Gdx.files.internal("actor/champion.png"));
	}
	
	@Override
	public int health() {
		return 100;
	}
	
	@Override
	public int food() {
		return 100;
	}
	
}
