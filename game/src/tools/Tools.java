package tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.badlogic.gdx.math.Vector2;

/**
 * common, non class specific functions
 * @author mattadams
 *
 */
public class Tools {

 	public static int UP = 1;
	public static int DOWN = 2;
	public static int LEFT = 3;
	public static int RIGHT = 4;
	
	
	/**
	 * return the direction the goal tile is in relation to the current tile
	 * the tiles must be adjacent!
	 * @param current
	 * @param goal
	 * @return
	 */
	public static int getDirection(Vector2 current, Vector2 goal) {
		int dx = (int) (goal.x - current.x);
		int dy = (int) (goal.y - current.y);
		
		if((dx > 1 || dx < -1) || (dy > 1 || dy < -1) || (dy != 0 && dx != 0)) {
			return 0;
		}
		
		if(dy == 1) {
			return UP;
		} else if(dy == -1) {
			return DOWN;
		} else if(dx == -1) {
			return LEFT;
		} else if(dx == 1) {
			return RIGHT;
		} else {
			return 0;
		}
	}
	
	/**
	 * return the left most x co-ordinate required to center the contents in the container
	 * @param containerX
	 * @param contentsX
	 * @return
	 */
	public static float centreX(float containerX, float contentsX) {
		return (containerX - contentsX) / 2;
	}
	
	/**
	 * return the top most y co-ordinate required to center the contents in the container
	 * @param containerY the containers height
	 * @param contentsY the contents height
	 * @param blOrigin is the Y co-ordinate origin from the bottom left 
	 * @return
	 */
	public static float centreY(float containerY, float contentsY, boolean blOrigin) {
		if(blOrigin) {
			return containerY - ((containerY - contentsY) / 2);
		} else {
			return (containerY - contentsY) / 2;
		}
	}
	
	/**
	 * return the top most y co-ordinate required to center the contents in the container
	 * (y origin assumed as bottom left)
	 * @param containerY the containers height
	 * @param contentsY the contents height
	 * @return
	 */
	public static float centreY(float containerY, float contentsY) {
		return containerY - ((containerY - contentsY) / 2);
	}
	
	/**
	 * get the vector of the top left position required to centre the contents in the container
	 * @param container
	 * @param contents
	 * @param yblOrigin
	 * @return
	 */
	public static Vector2 centre(Vector2 container, Vector2 contents, boolean yblOrigin) {
		float cx = 0;
		float cy = 0;
		
		cx = centreX(container.x, contents.x);
		cy = centreY(container.y, contents.y, yblOrigin);
		
		return new Vector2(cx,cy);
	}
	
	/**
	 * does the point lie between the bounds (boundX,boundY is top left)
	 * @param boundX
	 * @param boundY
	 * @param boundWidth
	 * @param boundHeight
	 * @param pointX
	 * @param pointY
	 * @return
	 */
	public static boolean inBounds(float boundX, float boundY, float boundWidth, float boundHeight, float pointX, float pointY) {
		if(((pointX >= boundX) && (pointX <= boundX + boundWidth)) && ((pointY <= boundY) && (pointY >= boundY - boundHeight))) {
			return true;
		}
		
		return false;
	}
	
	public static String getDate() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
	}
}
