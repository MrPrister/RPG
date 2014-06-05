package game.actor;

import main.SavePart;
import game.item.consumable.RedBook;
import game.item.consumable.YellowBook;
import game.item.ingredient.Brain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import tools.Globals;
import tools.random.Random;

public class Zombie extends Actor {

	public Zombie(int startX, int startY) {
		super(startX, startY);
		
		paddingX = 4;
		paddingY = 4;
		
		// set the default animation
		current = idleDown;
	}
	
	public Zombie(SavePart data) {
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
		return new Texture("actor/zombie.png");
	}

	@Override
	void death() {
		int random = Random.number(0, 10);
		if(random > 7) {
			Globals.items.add(new Brain(this.x, this.y));
		} else if(random > 5) {
			Globals.items.add(new RedBook(this.x, this.y));
		} else {
			Globals.items.add(new YellowBook(this.x, this.y));
		}
		
		int randomScream = Random.number(1, 1);
		
		if(randomScream == 1) {
			Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/wilhelm_scream.mp3"));
			sound.play(0.6f, 1, 0);
		}
	}

	@Override
	void attack() {
		
	}

}
