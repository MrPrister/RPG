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
		setFont(font);
		
		setHeight(getFont().getBounds(getText()).height);
		setWidth(getFont().getBounds(getText()).width);
	}

	@Override
	public void render(SpriteBatch batch) {
		render(batch, getX(), getY());
	}
	
	/**
	 * render the label with a starting position of x, y using the sprite batch
	 * @param batch
	 * @param x
	 * @param y
	 */
	public void render(SpriteBatch batch, float x, float y) {
		float renderX = x;
		float renderY = y;
		
		if(hover) {
			if(hoverDeltaX != 0) {
				renderX += hoverDeltaX;
			}
			if(hoverDeltaY != 0) {
				renderY += hoverDeltaY;
			}
		}
		
		font.draw(batch, text, renderX, renderY);
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
