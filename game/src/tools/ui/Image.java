package tools.ui;

import tools.UserInput;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

// simple image

public class Image extends AbstractUIElement {

	private Texture image = null;
	private float scale = 1;
	private float rotation = 0;
	
	@Override
	public void render(SpriteBatch batch, float x, float y) {
		//renderX = getX();
		//renderY = getY();
		
		renderX = x;
		renderY = y;
		
		if(height == -1) {
			height = image.getHeight();
		}
		if(width == -1) {
			width = image.getWidth();
		}
		
		float scaledHeight = image.getHeight() * scale;
		float scaledWidth = image.getWidth() * scale;
		
		if(hover && !focus) {
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
		}
		
		if(backgroundColor != null) {
			ShapeRenderer shapeRenderer = new ShapeRenderer();
			shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
	    	shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
	    	
	    	batch.end();
	    	
			shapeRenderer.begin(ShapeType.Filled);
				shapeRenderer.setColor(backgroundColor);
				shapeRenderer.rect(renderX, renderY, scaledWidth, -scaledHeight);
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
				shapeRenderer.rect(renderX, renderY, scaledWidth, -scaledHeight);
			shapeRenderer.end();
			
			batch.begin();
		}
		
		batch.draw(image, renderX, renderY - scaledHeight, 0, 0, scaledWidth, scaledHeight, scale, scale, rotation, 0, 0, (int) image.getWidth(), (int) image.getHeight(), false, false);
	}

	@Override
	void hover(UserInput input) {
		
	}

	@Override
	void inFocus(UserInput input) {
		
	}

	@Override
	void clicked(UserInput input) {
		
	}

	@Override
	void keyPressed(UserInput input) {
		
	}
	
	public void setImage(Texture image) {
		this.image = image;
	}
	public Texture getImage() {
		return image;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	public float getScale() {
		return scale;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public float getRotation() {
		return rotation;
	}

}
