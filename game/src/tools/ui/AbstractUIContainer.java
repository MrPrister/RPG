package tools.ui;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * containers can hold other containers and AbstractUIElements
 * padding used to position items within containers
 * 
 * @author mattadams
 *
 */

public abstract class AbstractUIContainer extends AbstractUIElement {

	public LinkedHashMap<Integer, AbstractUIElement> elements;
	
	public void render(SpriteBatch batch, float x, float y) {
		// call the render method with base x and y for all contents
		for (Entry<Integer, AbstractUIElement> elementEntry : elements.entrySet()) {
			// item loading handled in InventorySlot
			AbstractUIElement element = elementEntry.getValue();
			
			element.render(batch, x, y);
		}
	}
	
}
