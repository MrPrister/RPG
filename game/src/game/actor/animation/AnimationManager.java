package game.actor.animation;

import java.util.ArrayList;
import java.util.Iterator;

public class AnimationManager {

	public ArrayList<Animation> animations;
	
	public AnimationManager() {
		animations = new ArrayList<Animation>();
	}
	
	public void add(Animation a) {
		animations.add(a);
	}
	
	public void remove(Animation a) {
		animations.remove(a);
	}
	
	public void update(float delta) {
		for (Iterator<Animation> iterator = animations.iterator(); iterator.hasNext();) {
			Animation a = (Animation) iterator.next();
			
			a.update(delta);
		}
	}

	public void clear() {
		animations.clear();
	}
}
