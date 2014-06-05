package game.camera;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenTextManager {

ArrayList<ScreenText> text;
	
	public ScreenTextManager() {
		text = new ArrayList<ScreenText>();
	}
	
	public void add(ScreenText screenText) {
		text.add(screenText);
	}
	
	/**
	 * run the render function on all actors in the actor list
	 * @param g
	 */
	public void render(SpriteBatch batch) {
		for (Iterator<ScreenText> textIterator = text.iterator(); textIterator.hasNext();) {
			ScreenText screenText = (ScreenText) textIterator.next();
			screenText.render(batch);
		}
	}
	
	/**
	 * run the update function on all actors in the actor list
	 * @param delta
	 */
	public void update(float delta) {
		ArrayList<ScreenText> toDelete = new ArrayList<ScreenText>();
		
		for (Iterator<ScreenText> textIterator = text.iterator(); textIterator.hasNext();) {
			ScreenText screenText = (ScreenText) textIterator.next();
			if(screenText.delete()) {
				toDelete.add(screenText);
			} else {
				screenText.update(delta);
			}
		}
		
		if(toDelete.size() != 0) {
			for (int i = 0; i < toDelete.size(); i++) {
				ScreenText screenText = toDelete.get(i);
				text.remove(screenText);
			}
		}
	}

	public void clear() {
		text.clear();
	}
	
}
