package tools.ui;

import tools.Globals;
import tools.Tools;
import tools.UserInput;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public abstract class AbstractUIElement {

	// ------------------------------------------------------------
	//		FLAGS
	// ------------------------------------------------------------
	
	protected boolean enabled = true;		// if false all inputs are ignored
	protected boolean hover = false;		// is the mouse currently over this element
	protected boolean focus = false;		// is the element in focus
	protected boolean click = false;		// has the element been clicked on
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
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
	
	// ------------------------------------------------------------
	//		ACCEPTED INPUT FLAGS
	// ------------------------------------------------------------
	
	protected boolean acceptMouseInput = true;
	protected boolean acceptKeyboardInput = true;
	protected boolean acceptScrollInput = true;
	
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
	
	// ------------------------------------------------------------
	//		POSITIONING & SIZE
	// ------------------------------------------------------------
	protected float x = 0;
	protected float y = 0;
	protected float width = -1;		// use -1 for auto
	protected float height = -1;	// use -1 for auto
	
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
	
	// ------------------------------------------------------------
	//		SKINS
	// ------------------------------------------------------------
	private UISkin defaultSkin = new UISkin();			// default skin
	public UISkin getDefaultSkin() {
		return defaultSkin;
	}
	public void setDefaultSkin(UISkin defaultSkin) {
		this.defaultSkin = defaultSkin;
	}
	
	private UISkin hoverSkin = defaultSkin;				// hover skin
	public UISkin getHoverSkin() {
		return hoverSkin;
	}
	public void setHoverSkin(UISkin hoverSkin) {
		this.hoverSkin = hoverSkin;
	}
	
	private UISkin clickSkin = defaultSkin;				// click skin
	public UISkin getClickSkin() {
		return clickSkin;
	}
	public void setClickSkin(UISkin clickSkin) {
		this.clickSkin = clickSkin;
	}
	
	protected UISkin skin = defaultSkin;				// current skin
	
	public void setSkin(UISkin skin) {
		this.skin = skin;
	}
	public UISkin getSkin() {
		return skin;
	}
	
	
	// ------------------------------------------------------------
	//		USED INTERNALLY
	// ------------------------------------------------------------
	
		
	/**
	 * respond to user input - override for more complex interactions
	 * @param input
	 */
	public void input(UserInput input) {
		if(acceptMouseInput) {
			if(mouseInBounds(input.mouseX(), input.mouseY())) {
				hover = true;
				skin = hoverSkin;
			} else {
				hover = false;
				skin = defaultSkin;
			}
			
			if(input.mouseClicked() && hover) {
				click = true;
				skin = clickSkin;
			} else {
				click = false;
				if(!hover) {
					skin = defaultSkin;
				}
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
	 * render method
	 */
	public void render(SpriteBatch batch) {
		renderBackground(batch);
		renderBorder(batch);
	};
	
	public void renderBackground(SpriteBatch batch) {
		if(skin.getBackgroundColor() != null) {
			ShapeRenderer shapeRenderer = new ShapeRenderer();
			shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
	    	shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
	    	
	    	batch.end();
	    	
			shapeRenderer.begin(ShapeType.Filled);
				shapeRenderer.setColor(skin.getBackgroundColor());
				shapeRenderer.rect((getX() + skin.getMarginLeft()), (getY() + skin.getMarginBottom()), width, -height);
			shapeRenderer.end();
			
			batch.begin();
		}
	}
	
	public void renderBorder(SpriteBatch batch) {
		
		if(skin.getBorderColor() != null) {
			ShapeRenderer shapeRenderer = new ShapeRenderer();
			shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
	    	shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
			
			if(skin.isBorderTop() && skin.isBorderRight() && skin.isBorderBottom() && skin.isBorderLeft()) {
		    	batch.end();
		    	
				shapeRenderer.begin(ShapeType.Line);
					shapeRenderer.setColor(skin.getBorderColor());
					shapeRenderer.rect((getX() + skin.getMarginLeft()), (getY() + skin.getMarginBottom()), width, -height);
				shapeRenderer.end();
				
				batch.begin();
				
			} else {
				// TODO: render each line of the border separately
				if(skin.isBorderTop()) {
					
				}
				if(skin.isBorderRight()) {
									
				}
				if(skin.isBorderBottom()) {
					
				}
				if(skin.isBorderLeft()) {
					
				}
			}
		}
	}
	
	// action methods
	abstract void hover(UserInput input);
	abstract void inFocus(UserInput input);
	abstract void clicked(UserInput input);
	abstract void keyPressed(UserInput input);
	
	/**
	 * return the point at which the content can start being drawn (after the margin, border and padding)
	 * @return
	 */
	public float getRenderX() {
		return getX() + skin.getMarginLeft() + (skin.isBorderLeft() ? skin.getBorderWidth() : 0) + skin.getPaddingLeft();
	}
	
	/**
	 * return the point at which the content can start being drawn (after the margin, border and padding)
	 * @return
	 */
	public float getRenderY() {
		return getY() + skin.getMarginBottom() + (skin.isBorderBottom() ? skin.getBorderWidth() : 0) + skin.getPaddingBottom();
	}
	
	
	public boolean mouseInBounds(float pointX, float pointY) {
		return Tools.inBounds(x, y, width, height, pointX, pointY);
	}
	
}
