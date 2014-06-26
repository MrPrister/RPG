package tools.ui;

import java.util.HashMap;
import java.util.Map.Entry;

import tools.Tools;
import tools.UserInput;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * TODO: needs a complete rework, might even try to remove entirely in favour of using a UIContianer
 * deals with the updating and display of menu items
 * @author mattadams
 *
 */
public class Menu {
	
	// public ArrayList<String> options;		// a list of all the menu options
	public HashMap<Integer, Label> options;
	
	private int focus;						// the current option in focus
	private int selected = -1;				// the option that has been selected
	private boolean escaped = false;		// has the menu been escaped
	
	// flags for input
	private boolean keyboardInput = true;	// accept keyboard input
	private boolean mouseInput = true;		// accept mouse input
	private boolean scrollInput = true;		// accept scroll input
	private boolean loop = true;
	
	// input keys
	private int UP = Keys.UP;
	private int DOWN = Keys.DOWN;
	private int SELECT = Keys.ENTER;
	private int ESCAPE = Keys.ESCAPE;
	
	// flags and variables for rendering
	private String name;					// menu name
	private boolean renderName = false;		// should we start by rendering the menu name
	
	private float x = 0;					// x position on screen to start rendering the menu 
	private float y = 0;					// y position on screen to start rendering the menu
	
	private boolean inline = false;			// render the menu horizontally instead of vertically
	
	private boolean outline = false;		// should an outline be rendered round the menu
	
	private float focusXDelta = 0;			// change in the x position to render the option in focus
	private float focusYDelta = 0;			// change in the y position to render the option in focus
	
	private float optionSpacing = 0;		// the space between 2 options
	
	private BitmapFont font;
	
	private Color backgroundColor = null;
	private Color lineColor = null;
	
	private float width;
	private float height;
	
	/**
	 * create a new menu
	 * @param name the name of the menu
	 */
	public Menu() {
		//options = new ArrayList<String>();
		options = new HashMap<Integer, Label>();
		focus = -1;
	}
	
	/**
	 * set whether the menu accepts keyboard input
	 * @param acceptInput
	 */
	public void setKeyboardInput(boolean acceptInput) {
		keyboardInput = acceptInput;
	}
	
	/**
	 * return whether the menu accepts keyboard input
	 * @return
	 */
	public boolean acceptKeyboardInput() {
		return keyboardInput;
	}
	
	/**
	 * set the key used for moving up the menu options
	 * @param keyCode
	 */
	public void setUpKey(int keyCode) {
		UP = keyCode;
	}
	
	/**
	 * return the keycode of the key used as the menu up key
	 * @return
	 */
	public int getUpKey() {
		return UP;
	}
	
	/**
	 * set the key used for moving down the menu options
	 * @param keyCode
	 */
	public void setDownKey(int keyCode) {
		DOWN = keyCode;
	}
	
	/**
	 * return the keycode of the key used as the menu down key
	 * @return
	 */
	public int getDownKey() {
		return DOWN;
	}
	
	/**
	 * set the key for selecting the focused option
	 * @param keyCode
	 */
	public void setSelectKey(int keyCode) {
		SELECT = keyCode;
	}
	
	/**
	 * return the keycode of the key used as the menu select key
	 * @return
	 */
	public int getSelectKey() {
		return SELECT;
	}
	
	/**
	 * set the key for escaping the menu
	 * @param keyCode
	 */
	public void setEscapeKey(int keyCode) {
		ESCAPE = keyCode;
	}
	
	/**
	 * return the keycode of the key used as the menu escape key
	 * @return
	 */
	public int getEscapeKey() {
		return ESCAPE;
	}
	
	/**
	 * set whether the menu accepts mouse input
	 * @param acceptInput
	 */
	public void setMouseInput(boolean acceptInput) {
		mouseInput = acceptInput;
	}
	
	/**
	 * return whether the menu accepts mouse input
	 * @return
	 */
	public boolean acceptMouseInput() {
		return mouseInput;
	}
	
	/**
	 * set whether the menu accepts scroll input
	 * @param acceptInput
	 */
	public void setScrollInput(boolean acceptInput) {
		scrollInput = acceptInput;
	}
	
	/**
	 * return whether the menu accepts scroll input
	 * @return
	 */
	public boolean acceptScrollInput() {
		return scrollInput;
	}
	
	/**
	 * set whether scrolling or keyboard input should loop back to the start/end if scrolled from the end/start
	 * @param loop
	 */
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	/**
	 * return whether menu looping is active
	 * @return
	 */
	public boolean getLoop() {
		return loop;
	}
	
	/**
	 * return the index of the selected option
	 * @return the index or -1 if no index is selected
	 */
	public int getSelected() {
		return selected;
	}
	
	public boolean hasSelected() {
		if(selected != -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * clear the selected option
	 */
	public void clearSelected() {
		selected = -1;
	}
	
	/**
	 * return whether the menu has been escaped
	 * @return
	 */
	public boolean escaped() {
		return escaped;
	}
	
	/**
	 * clear the escaped flag
	 */
	public void clearEscaped() {
		escaped = false;
	}
	
	/**
	 * set the x position of where to start rendering the menu
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * return the x position of where to start rendering the menu
	 * @return
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * set the y position of where to start rendering the menu
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * return the y position of where to start rendering the menu
	 * @return
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * set whether we should outline the menu
	 * @param outline
	 */
	public void setDrawOutline(boolean outline) {
		this.outline = outline;
	}
	
	/**
	 * the color of the outline
	 * @param color
	 */
	public void setOutlineColor(Color color) {
		this.lineColor = color;
	}
	
	/**
	 * return whether the menu outline will be drawn
	 * @return
	 */
	public boolean getDrawOutline() {
		return outline;
	}
	
	/**
	 * return the color of the menu outline
	 * @return
	 */
	public Color getOutlineColor() {
		return lineColor;
	}
	
	public void setBackgroundColor(Color color) {
		this.backgroundColor = color;
	}
	
	public Color getBackgroundColor() {
		return this.backgroundColor;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	public float getWidth() {
		return this.width;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getHeight() {
		return height;
	}
	
	/**
	 * set the font of the menu
	 * @param font
	 */
	public void setFont(BitmapFont font) {
		this.font = font;
	}
	
	/**
	 * get the font of the menu
	 * @return
	 */
	public BitmapFont getFont() {
		return font;
	}
	
	/**
	 * set the menu name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * return the name of the menu
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * set whether to render the menu name
	 * @param render
	 */
	public void setRenderName(boolean render) {
		renderName = render;
	}
	
	/**
	 * return whether to render the menu name
	 * @return
	 */
	public boolean getRenderName() {
		return renderName;
	}
	
	/**
	 * set the spacing (in pixels) between menu options
	 * @param spacing
	 */
	public void setOptionSpacing(float spacing) {
		optionSpacing = spacing;
	}
	
	/**
	 * return the space (in pixels) between menu items
	 * @return
	 */
	public float getOptionSpacing() {
		return optionSpacing;
	}
	
	/**
	 * set the difference in x positioning of a menu option when in focus
	 * @param xDelta
	 */
	public void setFocusXDelta(float xDelta) {
		focusXDelta = xDelta;
	}
	
	/**
	 * return the difference in y positioning of a menu option when in focus
	 * @return
	 */
	public float getFocusXDelta() {
		return focusXDelta;
	}
	
	/**
	 * set the difference in y positioning of a menu option when in focus
	 * @param yDelta
	 */
	public void setFocusYDelta(float yDelta) {
		focusYDelta = yDelta;
	}
	
	/**
	 * return the difference in y positioning of a menu option when in focus
	 * @return
	 */
	public float getFocusYDelta() {
		return focusYDelta;
	}
	
	/**
	 * set whether to render the options horizontally
	 * @param inline
	 */
	public void setInline(boolean inline) {
		this.inline = inline;
	}
	
	/**
	 * return whether the menu options will be rendered horizontally
	 * @return
	 */
	public boolean getInline() {
		return inline;
	}
	
	
	/**
	 * add an option to the menu
	 * @param label
	 */
	public void addOption(Label label, int index) {
		// set the focus to the first element added
		if(focus == -1) {
			focus = index;
		}
		
		options.put(index, label);
	}
	
	/**
	 * set focus to the next menu item
	 * @param loop loop back to the start if scrolling down on the last element
	 */
	public void scrollDown() {
		if((options.size() - 1) > focus) {
			focus++;
		} else if(loop) {
			focus = 0;
		}
	}
	
	/**
	 * set focus to the previous menu item
	 * @param loop loop back to the end if scrolling up on the first element
	 */
	public void scrollUp() {
		if(focus > 0) {
			focus--;
		} else if(loop) {
			focus = (options.size() - 1);
		}
	}
	
	/**
	 * set the focus on the given menu option if it exists
	 * @param index
	 */
	public void setFocus(int index) {
		if(index >= 0 && index <= (options.size() - 1)) {
			focus = index;
		}
	}
	
	/**
	 * return the index of the currently focused menu option
	 * @return
	 */
	public int getFocusIndex() {
		return focus;
	}
	
	/**
	 * return the label currently in focus
	 * @return
	 */
	public Label getFocus() {
		return options.get(focus);
	}
	
	/**
	 * return the label with the given index
	 * @param index
	 * @return
	 */
	public Label getOptionByIndex(int index) {
		return options.get(index);
	}
	/**
	 * get the index of the option
	 * @param option
	 * @return
	 */
	public int getIndexByOption(Label option) {
		for (Entry<Integer, Label> keyValuePair : options.entrySet()) {
			if(keyValuePair.getValue() == option) {
				return keyValuePair.getKey();
			}
		}
		
		return -1;
	}
	
	/**
	 * handle user input
	 * @param input
	 */
	public void input(UserInput input) {
		if(keyboardInput) {
			if(input.keyReleased(UP)) {
				scrollUp();
			}
			if(input.keyReleased(DOWN)) {
				scrollDown();
			}
		}
		
		if(mouseInput) {
			if(input.mouseMoved() || input.mouseClicked()) {
				float mx = getX();
				float my = getY();
				
				for (Entry<Integer, Label> keyValuePair : options.entrySet()) {
					Label option = keyValuePair.getValue();
					
					if(Tools.inBounds(mx, my, option.getWidth(), option.getHeight(), input.mouseX(), input.mouseY())) {
						setFocus(getIndexByOption(option));
						option.setFocus(true);
						option.setHover(true);
						if(input.mouseClicked()) {
							selected = getFocusIndex();
						}
					} else {
						option.setFocus(false);
						option.setHover(false);
					}
					
					if(inline) {
						mx += option.getWidth() + optionSpacing;
					} else {
						my -= option.getHeight() + optionSpacing;
					}
				}
			}
		}
		
		if(scrollInput) {
			if(input.hasScrolled()) {
				switch (input.scrollDirection()) {
				case 1:
					scrollUp();
					break;

				case -1:
					scrollDown();
					break;
					
				default:
					break;
				}
			}
		}
		 
		if(keyboardInput) {
			if(input.keyReleased(SELECT)) {
				selected = getFocusIndex();
			}
			
			if(input.keyReleased(ESCAPE)) {
				this.escaped = true;
			}
		}
	}
	
	/**
	 * render the menu
	 * @param batch
	 */
	public void render(SpriteBatch batch) {
		float renderX = x;
		float renderY = y;
		
		// renderer to draw rectangles
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
    	shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
		
		batch.end();
    	
		if(backgroundColor != null && height != 0 && width != 0) {
	    	shapeRenderer.begin(ShapeType.Filled);
				shapeRenderer.setColor(backgroundColor);
				shapeRenderer.rect(x, y, width, -height);
			shapeRenderer.end();
		}
		if(outline && lineColor != null && height != 0 && width != 0) {
			shapeRenderer.begin(ShapeType.Line);
	    		shapeRenderer.setColor(lineColor);
	    		shapeRenderer.rect(x, y, width, -height);
	    	shapeRenderer.end();
		}
        
        batch.begin();
		
		for (Entry<Integer, Label> keyValuePair : options.entrySet()) {
			Label option = keyValuePair.getValue();
			
			if(getFocusIndex() == getIndexByOption(option)) {
				//option.render(batch, renderX + focusXDelta, renderY + focusYDelta);
				option.render(batch);
			} else {
				//option.render(batch, renderX, renderY);
				option.render(batch);
			}
			
			if(inline) {
				renderX += option.getWidth() + optionSpacing;
			} else {
				renderY -= option.getHeight() + optionSpacing;
			}
		}
	}
}
