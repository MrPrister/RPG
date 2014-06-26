package tools.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import tools.UserInput;

// simple text label

public class Label extends AbstractUIElement {

	private String text;
	
	public Label(String text) {
		setText(text);
	}

	public Label(String string, BitmapFont font) {
		setText(string);
		skin.setFont(font);
		
		setHeight(skin.getFont().getBounds(getText()).height);
		setWidth(skin.getFont().getBounds(getText()).width);
	}
	
	/**
	 * render the label using the sprite batch
	 * @param batch
	 */
	public void render(SpriteBatch batch) {
		float renderX = getRenderX();
		float renderY = getRenderY();
		
		// TODO: move to update and use skin swapping
		/*if(hover) {
			if(hoverDeltaX != 0) {
				renderX += hoverDeltaX;
			}
			if(hoverDeltaY != 0) {
				renderY += hoverDeltaY;
			}
		}*/
		
		skin.getFont().draw(batch, text, renderX, renderY);
	}

	@Override
	void hover(UserInput input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void inFocus(UserInput input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void clicked(UserInput input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void keyPressed(UserInput input) {
		// TODO Auto-generated method stub
		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
