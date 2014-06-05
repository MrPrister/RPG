package tools.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import tools.UserInput;

public class MessageBox extends AbstractUIContainer {

	// only renders when open is set to true
	public boolean open = false;
	
	// will not close if the user presses ESC
	public boolean modal = false;
	
	public MessageBox() {
		
	}
	
	@Override
	public void render(SpriteBatch batch, float x, float y) {
		if(open) {
			batch.begin();
			
			// draw screen overlay
			Texture overlay = new Texture(Gdx.files.internal("trans-70-black.png"));
			batch.draw(overlay, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			
			batch.end();
			
			if(backgroundColor != null) {
				ShapeRenderer shapeRenderer = new ShapeRenderer();
				shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		    	shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
		    	
		    	batch.end();
		    	
				shapeRenderer.begin(ShapeType.Filled);
					shapeRenderer.setColor(backgroundColor);
					shapeRenderer.rect(x, y, width, -height);
				shapeRenderer.end();
				
				batch.begin();
			}
			
			if(borderColor != null) {
				ShapeRenderer shapeRenderer = new ShapeRenderer();
				shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		    	shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
		    	
		    	batch.end();
		    	
				shapeRenderer.begin(ShapeType.Line);
					shapeRenderer.setColor(borderColor);
					shapeRenderer.rect(x, y, width, -height);
				shapeRenderer.end();
				
				batch.begin();
			}
			
			super.render(batch, x, y);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	void keyPressed(UserInput input) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
