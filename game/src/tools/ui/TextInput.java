package tools.ui;

import java.util.ArrayList;
import java.util.Iterator;

import tools.Globals;
import tools.UserInput;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

// field that accepts user input for constructing text strings

public class TextInput extends AbstractUIElement {

	private float internalPaddingX = 3;
	private float internalPaddingY = 5;
	
	public String text = "";
	public String defaultText = "";
	public BitmapFont defaultFont = null;
	public int claret = 0;
	public float claretBlinkRate = 0.3f;
	
	public TextInput(String text) {
		this.text = text;
	}
	
	@Override
	public void input(UserInput input) {
		super.input(input);
		
		if(enabled && focus) {
			if(input.anyKeyReleased()) {
				ArrayList<Integer> keys = input.getKeysUp();
				ArrayList<Integer> modifiers = input.getKeysDown();
				
				for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext();) {
					Integer keyCode = (Integer) iterator.next();
					
					String t = "";
					
					if(modifiers.contains(Keys.SHIFT_LEFT) || modifiers.contains(Keys.SHIFT_RIGHT)) {
						t = UserInput.getStringFromKey(keyCode, true);
					} else {
						t = UserInput.getStringFromKey(keyCode, false);
					}
					
					if(t == "") {
						switch(keyCode) {					
							case Keys.RIGHT:
								if(claret < text.length()) {
									claret++;
								}
								break;
							case Keys.LEFT:
								if(claret > 0) {
									claret--;
								}
								break;
							case Keys.BACKSPACE:
								if(text.length() > 0 && claret > 0) {
									String temp = text;
									text = text.substring(0, claret - 1);
									text = text.concat(temp.substring(claret));
									claret--;
								}
								break;
							case Keys.FORWARD_DEL:
								if(text.length() > 0 && claret < text.length()) {
									String temp = text;
									text = text.substring(0, claret);
									text = text.concat(temp.substring(claret + 1));
								}
								break;
							case Keys.HOME:
								claret = 0;
								break;
							case Keys.END:
								claret = text.length();
								break;
						}
					} else {
						// use has type a letter, number or symbol - insert at claret
						
						String temp = text;
						text = text.substring(0, claret);
						text = text.concat(t).concat(temp.substring(claret));
						claret++;
					}
				}
			}
		}
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		if(click) {
			focus = true;
		}
		
		// TODO: redo using skin swapping in the update function
		/*if(hover && !focus) {
			if(hoverDeltaX != 0) {
				renderX += hoverDeltaX;
			}
			if(hoverDeltaY != 0) {
				renderY += hoverDeltaY;
			}
		} else if(focus) {
			if(activeDeltaX != 0) {
				renderX += activeDeltaX;
			}
			if(activeDeltaY != 0) {
				renderY += activeDeltaY;
			}
		}*/
	}
	
	@Override
	public void render(SpriteBatch batch) {
		float renderX = x;
		float renderY = y;
		
		renderBackground(batch);
		renderBorder(batch);
		
		// we need to only show up to width worth of text, if the text exceeds width then we hide some of it, depending on claret position
		
		BitmapFont fontToRender = skin.getFont();
		String textToRender = text;
		
		if(!focus && text == "") {
			textToRender = defaultText;
			if(defaultFont != null) {
				fontToRender = defaultFont;
			}
		}
		
		/*
		if(hover && hoverFont != null) {
			fontToRender = hoverFont;
		}
		if(focus && activeFont != null) {
			fontToRender = activeFont;
		}
		*/
		
		fontToRender.draw(batch, textToRender, renderX + internalPaddingX, renderY - internalPaddingY);
		
		if(focus) {
			// draw the claret
			if(Globals.delayManager.isDelayFinished("ClaretOff")) {
				TextBounds bounds = skin.getFont().getBounds(text.substring(0, claret));
				float claretRenderX =  renderX + bounds.width;
				fontToRender.draw(batch, "|", claretRenderX + internalPaddingX, renderY - internalPaddingY);
				
				if(Globals.delayManager.isDelayFinished("ClaretOn")) {
					Globals.delayManager.add("ClaretOff", claretBlinkRate);
				}
			} else {
				Globals.delayManager.add("ClaretOn", claretBlinkRate);
			}
		}
		
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
		for (int i = 0; i < text.length(); i++) {
			TextBounds bounds = skin.getFont().getBounds(text.substring(0, i));
			if(input.mouseX() > (getRenderX() + bounds.width)) {
				claret = i;
			} else {
				return;
			}
		}
	}

	@Override
	void keyPressed(UserInput input) {
		// TODO Auto-generated method stub
		
	}
	
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}
	public void setClaret(int position) {
		if(position > text.length()) {
			claret = text.length();
		} else {
			claret = position;
		}
	}
	public int getClaret() {
		return claret;
	}
	public void setClaretBlinkRate(float blinkRate) {
		claretBlinkRate = blinkRate;
	}
	public float getClaretBlinkRate() {
		return claretBlinkRate;
	}
	
	public void setDefaultText(String text) {
		defaultText = text;
	}
	public String getDefaultText() {
		return defaultText;
	}
	public void setDefaultFont(BitmapFont font) {
		defaultFont = font;
	}
	public BitmapFont getDefaultFont() {
		return defaultFont;
	}

}
