package tools;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class UserInput implements InputProcessor {

	private ArrayList<Integer> keysDown;			// keys that are currently pressed
	private ArrayList<Integer> keysUp;			// keys that have been released
	
	private float mouseX = 0;
	private float mouseY = 0;
	private boolean mouseMoved = false;
	
	private boolean mouseClick = false;
	
	private int scrollDirection = 0;
	
	
	public UserInput() {
		keysDown = new ArrayList<Integer>();
		keysUp = new ArrayList<Integer>();
	}

	@Override
	public boolean keyDown(int keycode) {
		keysDown.add(keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// the key has been released so it's no longer held down, remove from the key down list
		if(keysDown.contains(keycode)) {
			keysDown.remove(new Integer(keycode));
		}
		
		keysUp.add(keycode);
		
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		mouseClick = true;
		
		if(mouseX != screenX) {
			mouseMoved = true;
			mouseX = screenX;
		}
		
		if(mouseY != Gdx.graphics.getHeight() - screenY) {
			mouseMoved = true;
			mouseY = Gdx.graphics.getHeight() - screenY;
		}
		
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouseX = screenX;
		mouseY = Gdx.graphics.getHeight() - screenY;
		mouseMoved = true;
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		scrollDirection = amount;
		return true;
	}
	
	/**
	 * has the key been released since the last poll
	 * @param keyCode
	 * @return
	 */
	public boolean keyReleased(int keyCode) {
		if(keysUp.contains(keyCode)) {
			return true;
		}
		return false;
	}
	
	/**
	 * has any key been pressed since the last poll
	 * @return
	 */
	public boolean anyKeyReleased() {
		if(!keysUp.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * has a mouse button been clicked since the last poll
	 * @return
	 */
	public boolean mouseClicked() {
		return mouseClick;
	}
	
	/**
	 * has the mouse moved since the last poll
	 * @return
	 */
	public boolean mouseMoved() {
		return mouseMoved;
	}
	
	/**
	 * has the user scrolled the mouse wheel since the last poll
	 * @return
	 */
	public boolean hasScrolled() {
		if(scrollDirection != 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * which direction has the user scrolled the mouse
	 * @return
	 */
	public int scrollDirection() {
		return scrollDirection;
	}
	
	/**
	 * the mouse's x position
	 * @return
	 */
	public float mouseX() {
		return mouseX;
	}
	
	/**
	 * the mouse's y position
	 * @return
	 */
	public float mouseY() {
		return mouseY;
	}
	
	public ArrayList<Integer> getKeysUp() {
		return keysUp;
	}
	public ArrayList<Integer> getKeysDown() {
		return keysDown;
	}
	
	/**
	 * clear poll specific variables
	 */
	public void clear() {
		scrollDirection = 0;
		mouseClick = false;
		mouseMoved = false;
		
		keysUp.clear();
	}

	public static String getStringFromKey(int keyCode, boolean shift) {
		switch(keyCode) {
			// letters
			case Keys.A:
				if(shift) {return "A";} else {return "a";}
			case Keys.B:
				if(shift) {return "B";} else {return "b";}
			case Keys.C:
				if(shift) {return "C";} else {return "c";}
			case Keys.D:
				if(shift) {return "D";} else {return "d";}
			case Keys.E:
				if(shift) {return "E";} else {return "e";}
			case Keys.F:
				if(shift) {return "F";} else {return "f";}
			case Keys.G:
				if(shift) {return "G";} else {return "g";}
			case Keys.H:
				if(shift) {return "H";} else {return "h";}
			case Keys.I:
				if(shift) {return "I";} else {return "i";}
			case Keys.J:
				if(shift) {return "J";} else {return "j";}
			case Keys.K:
				if(shift) {return "K";} else {return "k";}
			case Keys.L:
				if(shift) {return "L";} else {return "l";}
			case Keys.M:
				if(shift) {return "M";} else {return "m";}
			case Keys.N:
				if(shift) {return "N";} else {return "n";}
			case Keys.O:
				if(shift) {return "O";} else {return "o";}
			case Keys.P:
				if(shift) {return "P";} else {return "p";}
			case Keys.Q:
				if(shift) {return "Q";} else {return "q";}
			case Keys.R:
				if(shift) {return "R";} else {return "r";}
			case Keys.S:
				if(shift) {return "S";} else {return "s";}
			case Keys.T:
				if(shift) {return "T";} else {return "t";}
			case Keys.U:
				if(shift) {return "U";} else {return "u";}
			case Keys.V:
				if(shift) {return "V";} else {return "v";}
			case Keys.W:
				if(shift) {return "W";} else {return "w";}
			case Keys.X:
				if(shift) {return "X";} else {return "x";}
			case Keys.Y:
				if(shift) {return "Y";} else {return "y";}
			case Keys.Z:
				if(shift) {return "Z";} else {return "z";}
				
			// numbers
			case Keys.NUM_0:
				if(shift) {return ")";} else {return "0";}
			case Keys.NUM_1:
				if(shift) {return "!";} else {return "1";}
			case Keys.NUM_2:
				if(shift) {return "@";} else {return "2";}
			case Keys.NUM_3:
				if(shift) {return "£";} else {return "3";}
			case Keys.NUM_4:
				if(shift) {return "$";} else {return "4";}
			case Keys.NUM_5:
				if(shift) {return "%";} else {return "5";}
			case Keys.NUM_6:
				if(shift) {return "^";} else {return "6";}
			case Keys.NUM_7:
				if(shift) {return "&";} else {return "7";}
			case Keys.NUM_8:
				if(shift) {return "*";} else {return "8";}
			case Keys.NUM_9:
				if(shift) {return "(";} else {return "9";}
				
			// symbols
			case Keys.SPACE:
				return " ";
			case Keys.APOSTROPHE:
				if(shift) {return "\"";} else {return "'";}
			case Keys.AT:
				return "@";
			case Keys.BACKSLASH:
				if(shift) {return "|";} else {return "\\";}
			case Keys.COLON:
				return ":";
			case Keys.COMMA:
				if(shift) {return "<";} else {return ",";}
			case Keys.GRAVE:
				if(shift) {return "~";} else {return "`";}
			case Keys.MINUS:
				if(shift) {return "_";} else {return "-";}
			case Keys.PERIOD:
				if(shift) {return ">";} else {return ".";}
			case Keys.SEMICOLON:
				if(shift) {return ":";} else {return ";";}
			case Keys.SLASH:
				if(shift) {return "?";} else {return "/";}
			case Keys.STAR:
				return "*";
			case Keys.LEFT_BRACKET:
				if(shift) {return "{";} else {return "[";}
			case Keys.RIGHT_BRACKET:
				if(shift) {return "}";} else {return "]";}
		}
		
		return "";
	}
}
