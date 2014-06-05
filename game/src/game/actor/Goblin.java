package game.actor;

import main.SavePart;

import com.badlogic.gdx.graphics.Texture;
import tools.Globals;
import tools.random.Random;
import game.item.consumable.Apple;
import game.item.consumable.GreenBook;

public class Goblin extends Actor {

	public Goblin(int startX, int startY) {
		super(startX, startY);
		
		paddingX = 4;
		paddingY = 4;
		
		current = idleDown;
	}
	
	public Goblin(SavePart data) {
		super(data);
	}
	
	@Override
	int health() {
		return 10;
	}
	
	@Override
	public int moves() {
		return 2;
	}

	@Override
	Texture spriteSheet() {
		spriteWidth = 32;
		spriteHeight = 32;
		return new Texture("actor/goblin.png");
	}

	@Override
	void death() {
		// drop an apple
		int random = Random.number(0, 10);
		if(random > 7) {
			Globals.items.add(new GreenBook(this.x, this.y));
		} else {
			Globals.items.add(new Apple(this.x, this.y));
		}
	}

	@Override
	void attack() {
		// TODO Auto-generated method stub
		
	}

}
