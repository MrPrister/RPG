package tools.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class UISkin {

	public UISkin() {
		
	}
	
	// ------------------------------------------------------------
	//		MARGIN
	// ------------------------------------------------------------
	private float marginTop = 0;
	private float marginRight = 0;
	private float marginBottom = 0;
	private float marginLeft = 0;
	
	public float getMarginTop() {
		return marginTop;
	}
	public void setMarginTop(float marginTop) {
		this.marginTop = marginTop;
	}
	public float getMarginRight() {
		return marginRight;
	}
	public void setMarginRight(float marginRight) {
		this.marginRight = marginRight;
	}
	public float getMarginBottom() {
		return marginBottom;
	}
	public void setMarginBottom(float marginBottom) {
		this.marginBottom = marginBottom;
	}
	public float getMarginLeft() {
		return marginLeft;
	}
	public void setMarginLeft(float marginLeft) {
		this.marginLeft = marginLeft;
	}
	public void setMargin(float top, float right, float bottom, float left) {
		setMarginTop(top);
		setMarginRight(right);
		setMarginBottom(bottom);
		setMarginLeft(left);
	}
	
	
	// ------------------------------------------------------------
	//		BORDER
	// ------------------------------------------------------------
	private float borderWidth = 1f;
	private Color borderColor = null;
	
	private boolean borderTop = false;
	private boolean borderRight = false;
	private boolean borderBottom = false;
	private boolean borderLeft = false;
	
	public float getBorderWidth() {
		return borderWidth;
	}
	public void setBorderWidth(float borderWidth) {
		this.borderWidth = borderWidth;
	}
	public Color getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(Color color) {
		borderColor = color;
	}
	public boolean isBorderTop() {
		return borderTop;
	}
	public void setBorderTop(boolean borderTop) {
		this.borderTop = borderTop;
	}
	public boolean isBorderRight() {
		return borderRight;
	}
	public void setBorderRight(boolean borderRight) {
		this.borderRight = borderRight;
	}
	public boolean isBorderBottom() {
		return borderBottom;
	}
	public void setBorderBottom(boolean borderBottom) {
		this.borderBottom = borderBottom;
	}
	public boolean isBorderLeft() {
		return borderLeft;
	}
	public void setBorderLeft(boolean borderLeft) {
		this.borderLeft = borderLeft;
	}
	public void setBorder(boolean top, boolean right, boolean bottom, boolean left) {
		setBorderTop(top);
		setBorderRight(right);
		setBorderBottom(bottom);
		setBorderLeft(left);
	}
	
	// ------------------------------------------------------------
	//		PADDING
	// ------------------------------------------------------------
	private float paddingTop = 0;
	private float paddingRight = 0;
	private float paddingBottom = 0;
	private float paddingLeft = 0;
	
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
	
	// ------------------------------------------------------------
	//		FONT
	// ------------------------------------------------------------
	private BitmapFont font;
	
	public BitmapFont getFont() {
		return font;
	}
	public void setFont(BitmapFont font) {
		this.font = font;
	}
	
	// ------------------------------------------------------------
	//		BACKGROUND
	// ------------------------------------------------------------
	private Color backgroundColor = null;
	private Texture backgroundImage = null;
	
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

}
