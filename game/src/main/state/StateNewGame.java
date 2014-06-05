package main.state;

import main.ProgramLoop;
import game.actor.animation.Animation;
import game.actor.animation.AnimationManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import tools.Fonts;
import tools.Globals;
import tools.UserInput;
import tools.ui.Label;

public class StateNewGame extends AbstractState {

	public StateNewGame() {}

	@Override
	void init() {
		// TODO Auto-generated method stub

	}

	public SpriteBatch batch;
	public Stage stage;
	public Skin skin;
	
	// form elements
	TextField input;
	Table table;
	TextButton button;
	
	public String chosenClass = "";
	
	public AnimationManager am;
	public Animation wizard;
	public Animation knight;
	public Animation ranger;
	public Animation champion;
	
	public Image wizardImage;
	public Image knightImage;
	public Image rangerImage;
	public Image championImage;
	
	@Override
	void open(int previousStateID) {
		
		batch = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		initAnimations();
		
		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
		skin = new Skin();

		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		// Store the default libgdx font under the name "default".
		skin.add("default", Fonts.font24);
		
		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		
		TextFieldStyle textFieldStyle = new TextFieldStyle();
		textFieldStyle.fontColor = new Color(Color.LIGHT_GRAY);
		textFieldStyle.focusedFontColor = new Color(Color.WHITE);
		textFieldStyle.cursor = skin.newDrawable("white", new Color(1f, 1f, 1f, 1));
		textFieldStyle.background = skin.newDrawable("white", new Color(0.2f, 0.2f, 0.2f, 1));
		textFieldStyle.font = skin.getFont("default");
		skin.add("default", textFieldStyle);
		
		LabelStyle ls = new LabelStyle();
		ls.font = skin.getFont("default");
		skin.add("default", ls);
		
		// create table elements
		
		input = new TextField("", skin);
		input.setMessageText("game name");
		input.setBlinkTime(0.5f);
		
		// Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
		button = new TextButton("Submit", skin);

		// Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
		// Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
		// ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
		// revert the checked state.
		button.addCaptureListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println(input.getText());
				// check an player class has been selected
				// check a game name has been submitted
				// check if a file already exists with this name
				// create a new game with the given player class and game name
				
				if(input.getText() != "" && chosenClass != null) {
					Globals.newGameName = input.getText();
					Globals.newGameClass = chosenClass;
					
					Globals.stateManager.setState("StateGame");
				}
			}
		});
		
		wizardImage.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				System.out.println("wizard clicked");
				chosenClass = "PlayerWizard";
			}
		});
		
		knightImage.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				System.out.println("knight clicked");
				chosenClass = "PlayerKnight";
			}
		});
		
		rangerImage.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				System.out.println("ranger clicked");
				chosenClass = "PlayerRanger";
			}
		});
		
		championImage.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				System.out.println("champion clicked");
				chosenClass = "PlayerChampion";
			}
		});
		
		buildStage();
		
	}

	private void initAnimations() {
		// set up animations
		Globals.animations = am = new AnimationManager();
		
		Texture spritesheet;
		int spriteHeight;
		int spriteWidth;
		
		// wizard
		spriteHeight = spriteWidth = 32;
		spritesheet = new Texture(Gdx.files.internal("actor/wizard.png"));
		
		TextureRegion wr[] = {
				new TextureRegion(spritesheet, 0, spriteHeight * 0, spriteWidth, spriteHeight),
				new TextureRegion(spritesheet, spriteWidth, spriteHeight * 0, spriteWidth, spriteHeight),
				new TextureRegion(spritesheet, spriteWidth * 2, spriteHeight * 0, spriteWidth, spriteHeight)
		};
		
		wizard = new Animation(0.3f, wr);
		wizard.setPlayMode(Animation.LOOP_PINGPONG);
		
		// knight
		spriteHeight = spriteWidth = 32;
		spritesheet = new Texture(Gdx.files.internal("actor/knight.png"));
		
		TextureRegion kr[] = {
				new TextureRegion(spritesheet, 0, spriteHeight * 0, spriteWidth, spriteHeight),
				new TextureRegion(spritesheet, spriteWidth, spriteHeight * 0, spriteWidth, spriteHeight),
				new TextureRegion(spritesheet, spriteWidth * 2, spriteHeight * 0, spriteWidth, spriteHeight)
		};
		
		knight = new Animation(0.3f, kr);
		knight.setPlayMode(Animation.LOOP_PINGPONG);
		
		// rogue
		spriteHeight = spriteWidth = 32;
		spritesheet = new Texture(Gdx.files.internal("actor/ranger.png"));
		
		TextureRegion rr[] = {
				new TextureRegion(spritesheet, 0, spriteHeight * 0, spriteWidth, spriteHeight),
				new TextureRegion(spritesheet, spriteWidth, spriteHeight * 0, spriteWidth, spriteHeight),
				new TextureRegion(spritesheet, spriteWidth * 2, spriteHeight * 0, spriteWidth, spriteHeight)
		};
		
		ranger = new Animation(0.3f, rr);
		ranger.setPlayMode(Animation.LOOP_PINGPONG);
		
		// champion
		spriteHeight = spriteWidth = 32;
		spritesheet = new Texture(Gdx.files.internal("actor/champion.png"));
		
		TextureRegion cr[] = {
				new TextureRegion(spritesheet, 0, spriteHeight * 0, spriteWidth, spriteHeight),
				new TextureRegion(spritesheet, spriteWidth, spriteHeight * 0, spriteWidth, spriteHeight),
				new TextureRegion(spritesheet, spriteWidth * 2, spriteHeight * 0, spriteWidth, spriteHeight)
		};
		
		champion = new Animation(0.3f, cr);
		champion.setPlayMode(Animation.LOOP_PINGPONG);
		
		am.add(wizard);
		am.add(knight);
		am.add(ranger);
		am.add(champion);
		
		updateClassImages();
		getClassImages();
	}
	
	public void buildStage() {
		stage.clear();
		
		// Create a table that fills the screen. Everything else will go inside this table.
		table = new Table();
		table.setFillParent(true);
		table.setSkin(skin);
		stage.addActor(table);
		
		table.row().colspan(7).pad(0, 0, 80, 0);
		table.add("Choose A Character").left();
		
		table.row().pad(0, 0, 0, 0);
		
		table.add(wizardImage).size(48, 48).pad(0, 50, 0 ,50);
		table.add(knightImage).size(48, 48).pad(0, 50, 0 ,50);
		table.add(rangerImage).size(48, 48).pad(0, 50, 0 ,50);
		table.add(championImage).size(48, 48).pad(0, 50, 0 ,50);
		
		table.row().pad(20, 0, 0, 0);
		
		table.add("Wizard").center();
		table.add("Knight").center();
		table.add("Ranger").center();
		table.add("Champion").center();
		
		table.row().pad(30, 0, 0, 0).colspan(7);
		table.add(input).fillX();
		
		table.row().pad(80, 0, 0, 0).colspan(7);
		table.add(button);
	}

	private void updateClassImages() {
		if(chosenClass.equals("PlayerWizard") && wizard.isPaused()) {
			wizard.play();
		} else if(!chosenClass.equals("PlayerWizard")) {
			wizard.pause();
			wizard.setRunTime(0.5f);
		}
		
		if(chosenClass.equals("PlayerKnight") && knight.isPaused()) {
			knight.play();
		} else if(!chosenClass.equals("PlayerKnight")) {
			knight.pause();
			knight.setRunTime(0.5f);
		}
		
		if(chosenClass.equals("PlayerRanger") && ranger.isPaused()) {
			ranger.play();
		} else if(!chosenClass.equals("PlayerRanger")) {
			ranger.pause();
			ranger.setRunTime(0.5f);
		}
		
		if(chosenClass.equals("PlayerChampion") && champion.isPaused()) {
			champion.play();
		} else if(!chosenClass.equals("PlayerChampion")) {
			champion.pause();
			champion.setRunTime(0.5f);
		}
	}
		
	Runnable classImages = new Runnable() {
	    @Override
	    public void run() {
	    	wizardImage.setDrawable(new TextureRegionDrawable(wizard.getCurrentFrame()));
	    	knightImage.setDrawable(new TextureRegionDrawable(knight.getCurrentFrame()));
	    	rangerImage.setDrawable(new TextureRegionDrawable(ranger.getCurrentFrame()));
	    	championImage.setDrawable(new TextureRegionDrawable(champion.getCurrentFrame()));
	    }
	};
	
	private void getClassImages() {
		wizardImage = new Image(wizard.getCurrentFrame());
		knightImage = new Image(knight.getCurrentFrame());
		rangerImage = new Image(ranger.getCurrentFrame());
		championImage = new Image(champion.getCurrentFrame());
	}

	@Override
	void input(UserInput input) {
		
	}

	@Override
	void update(float delta) {
		updateClassImages();
		am.update(Gdx.graphics.getDeltaTime());
		
		Gdx.input.setInputProcessor(stage);
		
		stage.addAction(Actions.run(classImages));
		stage.act();

	}

	@Override
	void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		SpriteBatch batch = new SpriteBatch();
		batch.begin();
			
			stage.draw();
		
		batch.end();
	}

	@Override
	void close(int nextStateID) {
		// reset the input processor
		ProgramLoop.resetInputProcessor();
		
		Globals.animations = am = null;
		wizard = null;
		knight = null;
		ranger = null;
		champion = null;
		chosenClass = null;
	}

	@Override
	void dispose() {
		// TODO Auto-generated method stub

	}

}
