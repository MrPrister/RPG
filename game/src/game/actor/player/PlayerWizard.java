package game.actor.player;

import main.SavePart;
import game.actor.AbstractPlayer;
import game.actor.attribute.Attribute;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PlayerWizard extends AbstractPlayer {
	
	public PlayerWizard(SavePart playerData) {
		super(playerData);
	}
	
	public PlayerWizard(int startX, int startY) {
		super(startX, startY);
		
		attributes.add(new Attribute("strength", 10, 1, 2));
		attributes.add(new Attribute("agility", 10, 1, 3));
		attributes.add(new Attribute("intellect", 10, 1, 8));
		attributes.add(new Attribute("luck", 10, 1, 6));
	}

	@Override
	public Texture spriteSheet() {
		spriteWidth = 32;
		spriteHeight = 32;
		return new Texture(Gdx.files.internal("actor/wizard.png"));
	}
	
	@Override
	public int health() {
		return 80;
	}
	
	@Override
	public int food() {
		return 100;
	}
	
}
