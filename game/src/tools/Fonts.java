package tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Fonts {

	public static BitmapFont defaultFont;
	public static BitmapFont font12;
	public static BitmapFont font14;
	public static BitmapFont font16;
	public static BitmapFont font18;
	public static BitmapFont font20;
	public static BitmapFont font24;
	public static BitmapFont font28;
	public static BitmapFont font32;
	public static BitmapFont font36;
	public static BitmapFont font42;
	public static BitmapFont font48;
	public static BitmapFont font56;
	
	public Fonts() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PatrickHandSC-Regular.ttf"));
		font12 = generator.generateFont(12, FreeTypeFontGenerator.DEFAULT_CHARS, false);
		font14 = generator.generateFont(14, FreeTypeFontGenerator.DEFAULT_CHARS, false);
		font16 = generator.generateFont(16, FreeTypeFontGenerator.DEFAULT_CHARS, false);
		font18 = generator.generateFont(18, FreeTypeFontGenerator.DEFAULT_CHARS, false);
		font20 = generator.generateFont(20, FreeTypeFontGenerator.DEFAULT_CHARS, false);
		font24 = generator.generateFont(24, FreeTypeFontGenerator.DEFAULT_CHARS, false);
		font28 = generator.generateFont(28, FreeTypeFontGenerator.DEFAULT_CHARS, false);
		font32 = generator.generateFont(32, FreeTypeFontGenerator.DEFAULT_CHARS, false);
		font36 = generator.generateFont(36, FreeTypeFontGenerator.DEFAULT_CHARS, false);
		font42 = generator.generateFont(42, FreeTypeFontGenerator.DEFAULT_CHARS, false);
		font48 = generator.generateFont(48, FreeTypeFontGenerator.DEFAULT_CHARS, false);
		font56 = generator.generateFont(56, FreeTypeFontGenerator.DEFAULT_CHARS, false);
		
		defaultFont = font18;
		
		generator.dispose();
	}
}
