package tools.ui;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import tools.UserInput;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * containers can hold other containers and AbstractUIElements
 * padding used to position items within containers
 * 
 * @author mattadams
 *
 */

public class UIContainer extends AbstractUIElement {

	public LinkedHashMap<Integer, AbstractUIElement> elements;
	
	public void render(SpriteBatch batch) {
		for (Entry<Integer, AbstractUIElement> elementEntry : elements.entrySet()) {
			AbstractUIElement element = elementEntry.getValue();
			element.render(batch);
		}
	}
	
	public void input(UserInput input) {
		for (Entry<Integer, AbstractUIElement> elementEntry : elements.entrySet()) {
			AbstractUIElement element = elementEntry.getValue();
			element.input(input);
		}
	}

	public void update(float delta) {
		for (Entry<Integer, AbstractUIElement> elementEntry : elements.entrySet()) {
			AbstractUIElement element = elementEntry.getValue();
			element.update(delta);
		}
	}

	
	// TODO: check whether to pass the command to the sub element 
	
	@Override
	void hover(UserInput input) {
		for (Entry<Integer, AbstractUIElement> elementEntry : elements.entrySet()) {
			AbstractUIElement element = elementEntry.getValue();
			element.hover(input);
		}
	}

	@Override
	void inFocus(UserInput input) {
		for (Entry<Integer, AbstractUIElement> elementEntry : elements.entrySet()) {
			AbstractUIElement element = elementEntry.getValue();
			element.inFocus(input);
		}
	}

	@Override
	void clicked(UserInput input) {
		for (Entry<Integer, AbstractUIElement> elementEntry : elements.entrySet()) {
			AbstractUIElement element = elementEntry.getValue();
			element.clicked(input);
		}
	}

	@Override
	void keyPressed(UserInput input) {
		for (Entry<Integer, AbstractUIElement> elementEntry : elements.entrySet()) {
			AbstractUIElement element = elementEntry.getValue();
			element.keyPressed(input);
		}
	}
	
}
