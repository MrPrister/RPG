package tools.ui;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import tools.UserInput;

public class UI {

	// containing class for all UI elements, basically a UIContainer but at top level
	
	public LinkedHashMap<Integer, AbstractUIElement> elements;
	
	/**
	 * initialise a new UI
	 */
	public UI() {
		
	}
	
	/**
	 * called to render the UI, command propagates to all sub elements
	 */
	public void render(SpriteBatch batch) {
		for (Entry<Integer, AbstractUIElement> elementEntry : elements.entrySet()) {
			AbstractUIElement element = elementEntry.getValue();
			element.render(batch);
		}
	}
	
	/**
	 * called to pass input to the UI, command propagates to all sub elements
	 * @param input
	 */
	public void input(UserInput input) {
		for (Entry<Integer, AbstractUIElement> elementEntry : elements.entrySet()) {
			AbstractUIElement element = elementEntry.getValue();
			element.input(input);
		}
	}
	
	/**
	 * called to update the UI, command propagates to all sub elements
	 * @param delta
	 */
	public void update(float delta) {
		for (Entry<Integer, AbstractUIElement> elementEntry : elements.entrySet()) {
			AbstractUIElement element = elementEntry.getValue();
			element.update(delta);
		}
	}

}
