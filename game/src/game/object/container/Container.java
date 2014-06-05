package game.object.container;

import tools.Globals;
import tools.random.Random;
import main.SavePart;

import com.badlogic.gdx.graphics.Texture;

import game.item.consumable.Apple;
import game.object.Object;

public abstract class Container extends Object {

	public boolean looted = false;
	
	public float containerLevel = 1f;	// the level of items inside this container
	public float luckMultiplier = 1f;	// how much luck influences the content
	
	public Container(SavePart objectSave) {
		super(objectSave);
		looted = objectSave.getBoolean("looted");
	}

	public Container(int startX, int startY) {
		super(startX, startY);
	}
	
	public SavePart save() {
		super.save();
		save.put("looted", looted);
		
		return save;
	}

	public abstract Texture getImageFromFile();

	public void interact() {
		if(!looted) {
			int luck = Globals.player.attributes.getValue("luck");
			int items = Random.numberLuck(1, Random.numberLuck(1, 4, luck), luck);
			
			for (int i = 0; i < items; i++) {
				Globals.items.add(new Apple(this.x, this.y));
			}
			
			looted = true;
			image = texture = getImageFromFile();
		}
	}

	public void damage(int amount) {

	}

	public void destroy() {

	}

	public void remove() {

	}

}
