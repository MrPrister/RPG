package game.camera;

import game.actor.attribute.Attribute;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import tools.Globals;

public class Hud {

	private BitmapFont font;
	
	public Hud() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PatrickHandSC-Regular.ttf"));
		font = generator.generateFont(20, FreeTypeFontGenerator.DEFAULT_CHARS, false);
		generator.dispose();
	}
	
	/**
	 * draw the hud and hud elements
	 * @param g
	 */
	public void render(SpriteBatch batch) {
		//int x = Gdx.graphics.getWidth();
		int y = Gdx.graphics.getHeight();
		
		font.draw(
				batch,
				"FPS: " + Gdx.graphics.getFramesPerSecond(),
				(float) 40,
				(float) y - 20
		);
		
		font.draw(
				batch,
				"health: " + Globals.player.getHealth(),
				(float) 40,
				(float) y - 40
		);
		
		font.draw(
				batch,
				"food: " + Globals.player.getFood(),
				(float) 40,
				(float) y - 60
		);
		
		font.draw(
				batch,
				"x: " + Globals.player.x + ", y: " + Globals.player.y,
				(float) 40,
				(float) y - 80
		);
		
		Attribute strength = Globals.player.attributes.getAttribute("strength");
		Attribute agility = Globals.player.attributes.getAttribute("agility");
		Attribute intellect = Globals.player.attributes.getAttribute("intellect");
		
		font.draw(
				batch,
				"strength: " + strength.getValue(),
				(float) 40,
				(float) y - 100
		);
		
		font.draw(
				batch,
				"agility: " + agility.getValue(),
				(float) 40,
				(float) y - 120
		);
		
		font.draw(
				batch,
				"intellect: " + intellect.getValue(),
				(float) 40,
				(float) y - 140
		);
	}
	
}
