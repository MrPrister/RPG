package game.camera;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import tools.Fonts;
import tools.Globals;

public class ScreenText {

	private Boolean delete;				// should this ScreenText be removed on the next check
	
	private String text;
	private Color color;
	private Vector2 location;
	private BitmapFont font;
	private float speed;				// how fast the text will move on screen
	private float distance;				// how far the text will float
	
	private float traveled;				// how far the text has moved so far

	public ScreenText(String content, Vector2 location, Color color) {
		this.text = content;
		this.location = location;
		
		// get shared font from render manager
		font = Fonts.font20;
		
		this.color = color;
		
		this.speed = 1f;			// 1f = 1 second
		this.distance = 1f;			// 1f = 1 tile
		
		this.delete = false;
	}

	/**
	 * 
	 * @param content
	 * @param location
	 * @param color
	 * @param distance
	 * @param speed
	 */
	public ScreenText(String content, Vector2 location, BitmapFont font, Color color, float distance, float speed) {
		this.text = content;
		this.location = location;
		this.font = font;
		this.color = color;
		this.speed = speed;					// 1f = 1 tile/second
		this.distance = distance;			// 1f = 1 tile
		
		this.delete = false;
	}
	
	public void render(SpriteBatch batch) {		
		font.setColor(color);

		TextBounds bounds = font.getBounds(text);
		
		font.draw(
				batch,
				text,
				(location.x * Globals.tileWidth) - ((bounds.width - Globals.tileWidth) / 2),
				((location.y * Globals.tileHeight) + (traveled * Globals.tileHeight) + (Globals.tileHeight / 2))
		);
	}

	public void update(float delta) {
		animate(delta);
	}

	private void animate(float delta) {
		traveled += (speed * delta);
		
		if(traveled > distance) {
			traveled = distance;
			delete = true;
		}
	}

	public boolean delete() {
		return delete;
	}

}
