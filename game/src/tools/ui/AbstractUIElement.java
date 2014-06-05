package tools.ui;

import tools.Globals;
import tools.Tools;
import tools.UserInput;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractUIElement {

	// flags
	protected boolean enabled = true;
	protected boolean hover = false;		// is the mouse currently over this element
	protected boolean focus = false;		// is the element in focus
	protected boolean click = false;		// has the element been clicked on
	
	// input flags
	protected boolean acceptMouseInput = true;
	protected boolean acceptKeyboardInput = true;
	protected boolean acceptScrollInput = true;
	
	// positioning & size
	// x and y co-ords only used if no co-ords are passed with render, input or update functions
	protected float x = 0;
	protected float y = 0;
	protected float renderX;
	protected float renderY;
	protected float width = -1;	// use -1 for auto
	protected float height = -1;	// use -1 for auto
	
	// background
	protected Color backgroundColor = null;
	protected Texture backgroundImage = null;
	
	// border
	protected Color borderColor = null;
	protected Color hoverBorderColor = null;
	protected Color activeBorderColor = null;
	
	// font
	protected BitmapFont font;
	protected BitmapFont hoverFont = null;
	protected BitmapFont activeFont = null;
	
	// conditional movement
	protected float hoverDeltaX = 0;
	protected float hoverDeltaY = 0;
	protected float activeDeltaX = 0;
	protected float activeDeltaY = 0;
	
	// padding
	protected float paddingTop = 0;
	protected float paddingRight = 0;
	protected float paddingBottom = 0;
	protected float paddingLeft = 0;
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * respond to user input - override for more complex interactions
	 * @param input
	 */
	public void input(UserInput input) {
		if(acceptMouseInput) {
			if(mouseInBounds(input.mouseX(), input.mouseY())) {
				hover = true;
			} else {
				hover = false;
			}
			
			if(input.mouseClicked() && hover) {
				click = true;
			} else {
				click = false;
			}
		}
		
		if(acceptKeyboardInput) {
			//
		}
		
		if(acceptScrollInput) {
			//
		}
	}
	
	/**
	 * update the element flags based on user interaction etc
	 * @param delta
	 */
	public void update(float delta) {
		if(click) {
			System.out.println("calling click");
			clicked(Globals.input);
			click = false;
		}
	}
	
	/**
	 * basic render method
	 */
	public void render(SpriteBatch batch) {
		render(batch, getX(), getY());
	}
	
	/**
	 * render the elemtent with a starting position of x, y using the sprite batch
	 * @param batch
	 * @param x
	 * @param y
	 */
	abstract void render(SpriteBatch batch, float x, float y);
	
	// action methods
	abstract void hover(UserInput input);
	abstract void inFocus(UserInput input);
	abstract void clicked(UserInput input);
	abstract void keyPressed(UserInput input);
	
	public boolean mouseInBounds(float pointX, float pointY) {
		//System.out.println("checking if " + pointX + "," + pointY + " is with the bounds of " + x + "," + y + " & " + (x + width) + "," + (y - height));
		return Tools.inBounds(x, y, width, height, pointX, pointY);
	}
	
	// getters and setters
	
	public boolean getHover() {
		return hover;
	}
	public void setHover(boolean hover) {
		this.hover = hover;
	}
	public boolean getFocus() {
		return focus;
	}
	public void setFocus(boolean focus) {
		this.focus = focus;
	}
	public boolean getClicked() {
		return click;
	}
	
	public boolean getAcceptMouseInput() {
		return acceptMouseInput;
	}

	public void setAcceptMouseInput(boolean acceptMouseInput) {
		this.acceptMouseInput = acceptMouseInput;
	}

	public boolean getAcceptKeyboardInput() {
		return acceptKeyboardInput;
	}

	public void setAcceptKeyboardInput(boolean acceptKeyboardInput) {
		this.acceptKeyboardInput = acceptKeyboardInput;
	}

	public boolean getAcceptScrollInput() {
		return acceptScrollInput;
	}

	public void setAcceptScrollInput(boolean acceptScrollInput) {
		this.acceptScrollInput = acceptScrollInput;
	}

	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public Texture getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(Texture backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	public Color getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(Color color) {
		borderColor = color;
	}
	public Color getHoverBorderColor() {
		return hoverBorderColor;
	}
	public void setHoverBorderColor(Color color) {
		hoverBorderColor = color;
	}
	public Color getActiveBorderColor() {
		return activeBorderColor;
	}
	public void setActiveBorderColor(Color color) {
		activeBorderColor = color;
	}
	public BitmapFont getFont() {
		return font;
	}
	public void setFont(BitmapFont font) {
		this.font = font;
	}
	public BitmapFont getHoverFont() {
		return hoverFont;
	}
	public void setHoverFont(BitmapFont hoverFont) {
		this.hoverFont = hoverFont;
	}
	public BitmapFont getActiveFont() {
		return activeFont;
	}
	public void setActiveFont(BitmapFont activeFont) {
		this.activeFont = activeFont;
	}
	public float getHoverDeltaX() {
		return hoverDeltaX;
	}
	public void setHoverDeltaX(float hoverDeltaX) {
		this.hoverDeltaX = hoverDeltaX;
	}
	public float getHoverDeltaY() {
		return hoverDeltaY;
	}
	public void setHoverDeltaY(float hoverDeltaY) {
		this.hoverDeltaY = hoverDeltaY;
	}
	public float getActiveDeltaX() {
		return activeDeltaX;
	}
	public void setActiveDeltaX(float activeDeltaX) {
		this.activeDeltaX = activeDeltaX;
	}
	public float getActiveDeltaY() {
		return activeDeltaY;
	}
	public void setActiveDeltaY(float activeDeltaY) {
		this.activeDeltaY = activeDeltaY;
	}
	public float getPaddingTop() {
		return paddingTop;
	}
	public void setPaddingTop(float paddingTop) {
		this.paddingTop = paddingTop;
	}
	public float getPaddingRight() {
		return paddingRight;
	}
	public void setPaddingRight(float paddingRight) {
		this.paddingRight = paddingRight;
	}
	public float getPaddingBottom() {
		return paddingBottom;
	}
	public void setPaddingBottom(float paddingBottom) {
		this.paddingBottom = paddingBottom;
	}
	public float getPaddingLeft() {
		return paddingLeft;
	}
	public void setPaddingLeft(float paddingLeft) {
		this.paddingLeft = paddingLeft;
	}
	public void setPadding(float top, float right, float bottom, float left) {
		setPaddingTop(top);
		setPaddingRight(right);
		setPaddingBottom(bottom);
		setPaddingLeft(left);
	}
	

}
