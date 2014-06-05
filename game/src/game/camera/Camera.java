package game.camera;

import tools.Globals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class Camera {
    
	public OrthographicCamera view;
	
    // where the camera is focused
	private int currentX;
	//private int prevX;
	private int currentY;
	//private int prevY;
	
	// for panning
	protected float transX;
	protected float transY;
	protected float transStep;
	
	// controls the game view, all rendering calls and translation should happen here?!?!?
    public Camera() {        
    	view = new OrthographicCamera();
		view.setToOrtho(
				false,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight()
		);
    	
    	transX = 0.0f;
        transY = 0.0f;
        transStep = 1.8f;
    }
    
    /**
     * centre on the given tile with a pan animation
     * @param x
     * @param y
     * @param panSpeed
     */
    public void panToTile(float x, float y) {
    	panToTile(x, y, 1.8f);
    }
    
    /**
     * centre on the given tile with a pan animation
     * @param x
     * @param y
     * @param panSpeed
     */
    public void panToTile(float x, float y, float panSpeed) {
    	if(currentX == 0) {
    		currentX = (int) x;
    	}
    	if(currentY == 0) {
    		currentY = (int) y;
    	}
    	
    	if(currentX != x) {
    		transX += (float) (x - currentX);
    	}
    	if(currentY != y) {
    		transY += (float) (y - currentY);
    	}

    	// set pan speed
    	transStep = panSpeed;
    	
    	currentX = (int) x;
    	currentY = (int) y;
    	
    	view.position.set(
				new Vector3(
						(x * Globals.tileWidth) + (Globals.tileWidth / 2) - (transX * Globals.tileWidth),
						(y * Globals.tileHeight) + (Globals.tileHeight / 2) - (transY * Globals.tileHeight),
						0
				)
		);
    	view.update();
    }
	
    // like pan but without smooth movement
    public void centerOnTile(float x, float y) {
    	transX = 0.0f;
    	transY = 0.0f;
    	currentX = (int) x;
    	currentY = (int) y;
    	
    	System.out.println("tile width: " + Globals.tileWidth + ", tile height: " + Globals.tileHeight);
    	
    	view.position.set(
				new Vector3(
						(x * Globals.tileWidth) + (Globals.tileWidth / 2),
						(y * Globals.tileHeight) + (Globals.tileHeight / 2),
						0
				)
		);
    	view.update();
    }
    
	/**
	* "locks" the camera on the given coordinates. The camera tries to keep the location in it's center.
	* 
	* @param x the real x-coordinate (in pixel) which should be centered on the screen
	* @param y the real y-coordinate (in pixel) which should be centered on the screen
	*/
	public void centerOn(float x, float y) {
		view.position.set(
				new Vector3(
						x,
						y,
						0
				)
		);
		view.update();
	}
	
	public void update(float delta) {
		// calculate translation for smooth scrolling for map
		if(transX != 0.00f) {
    		if(transX > 0f) {
    			transX -= transStep * delta;
    			if(transX < 0.01f) {
    				transX = 0.0f;
    			}
    		}
    		if(transX < 0f) {
    			transX += transStep * delta;
    			if(transX > -0.01f) {
    				transX = 0.0f;
    			}
    		}
    	}
    	if(transY != 0.00f) {
    		if(transY > 0f) {
    			transY -= (transStep * delta);
    			if(transY < 0.01f) {
    				transY = 0.0f;
    			}
    		}
    		if(transY < 0f) {
    			transY += transStep * delta;
    			if(transY > -0.01f) {
    				transY = 0.0f;
    			}
    		}
    	}
	}
	
}
