package main.state;

import java.util.ArrayList;

import game.Game;
import game.actor.AbstractPlayer;
import game.actor.Actor;
import game.actor.ActorManager;
import game.actor.Goblin;
import game.actor.Zombie;
import game.actor.animation.AnimationManager;
import game.actor.attribute.Attribute;
import game.actor.player.PlayerKnight;
import game.camera.Camera;
import game.camera.Hud;
import game.camera.ScreenTextManager;
import game.item.ItemManager;
import game.item.consumable.Apple;
import game.level.Map;
import tools.Globals;
import tools.UserInput;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

public class StateGame extends AbstractState {
	
	public StateGame() {}
	
	private ArrayList<Integer>gameSubStates;
	private Game game;
	
	@Override
	void init() {
		gameSubStates = new ArrayList<Integer>();
		gameSubStates.add(Globals.stateManager.getStateID("StateGameInventory"));
		gameSubStates.add(Globals.stateManager.getStateID("StateGameMenu"));
		gameSubStates.add(Globals.stateManager.getStateID("StateGameOver"));
		
		Globals.delayManager.add("KeyMove", 0f);
		Globals.delayManager.add("KeyAttack", 0f);
		
		// testing
		Globals.delayManager.add("KeyPath", 0f);
	}

	@Override
	void open(int previousStateID) {
		if(Globals.gameToLoad != null) {
			game = new Game(new FileHandle(Globals.savesFolder + "/" + Globals.gameToLoad + ".data"), "PlayerRanger");
		    Globals.game = game;
		    Globals.gameToLoad = null;
		} else {
			if(Globals.newGameName != null && Globals.newGameClass != null) {
				game = new Game(new FileHandle(Globals.savesFolder + "/" + Globals.newGameName + ".data"), Globals.newGameClass);
			    Globals.game = game;
			    
			    Globals.newGameName = null;
			    Globals.newGameClass = null;
			}
		}
		
		Globals.camera.centerOnTile(Globals.player.x, Globals.player.y);
	}

	@Override
	void update(float delta) {
		Globals.animations.update(delta);
		
		Globals.camera.update(delta);
		
		Globals.player.update(delta);
		
		Globals.tiles.update(delta);
		Globals.objects.update(delta);
		Globals.actors.update(delta);
		Globals.items.update(delta);
		Globals.screenText.update(delta);
		
		if(Globals.delayManager.isDelayFinished("GameSaveDelay")) {
			Globals.game.save();
			Globals.delayManager.add("GameSaveDelay", 5);
		}
	}

	@Override
	void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		SpriteBatch batch = new SpriteBatch();
		SpriteBatch hudBatch = new SpriteBatch();
		
		// render the map / level
		//Globals.map.render();
		//batch.begin();
			//Globals.tiles.render(batch);
		//batch.end();
		
		// render the objects and actors on the map / level
		batch.setProjectionMatrix(Globals.camera.view.combined);
		batch.begin();
			Globals.tiles.render(batch);
			Globals.objects.render(batch);
			Globals.items.render(batch);	
			Globals.actors.render(batch);
			Globals.player.render(batch);
			Globals.screenText.render(batch);
		batch.end();
		
		// render the HUD
		hudBatch.begin();
			Globals.hud.render(hudBatch);
		hudBatch.end();
		
		// center the camera to the player but with a pan
		Globals.camera.panToTile(Globals.player.x, Globals.player.y);
	}

	public int DOWN = Keys.DOWN;
	public int UP = Keys.UP;
	public int LEFT = Keys.LEFT;
	public int RIGHT = Keys.RIGHT;
	
	public int INTERACT = Keys.E;
	public int ATTACK = Keys.SPACE;
	public int INVENTORY = Keys.I;
	
	// testing
	public int PATH = Keys.P;
	
	@Override
	void input(UserInput input) {
		AbstractPlayer player = Globals.player;
		
		// use keyPressed() and acceptKeys() to stop multiple key presses registering before the user can stop pressing the key
		
		// if the player is locked then we don't accept any movement or attack commands
		if(!player.isLocked() && Globals.delayManager.isDelayFinished("KeyMove")) {
    		if (input.keyReleased(DOWN)) {
        		if(player.isFacing(Actor.ACTOR_DOWN)) {
        			player.move(Actor.ACTOR_DOWN);
        			Globals.delayManager.add("KeyMove", 0.25f);
        		} else {
        			player.setFacing(Actor.ACTOR_DOWN);
        		}
            } else if (input.keyReleased(UP)) {
            	if(player.isFacing(Actor.ACTOR_UP)) {
            		player.move(Actor.ACTOR_UP);
            		Globals.delayManager.add("KeyMove", 0.25f);
            	} else {
            		player.setFacing(Actor.ACTOR_UP);
            	}
            } else if (input.keyReleased(LEFT)) {
            	if(player.isFacing(Actor.ACTOR_LEFT)) {
            		player.move(Actor.ACTOR_LEFT);
            		Globals.delayManager.add("KeyMove", 0.25f);
            	} else {
            		player.setFacing(Actor.ACTOR_LEFT);
            	}
            } else if (input.keyReleased(RIGHT)) {
            	if(player.isFacing(Actor.ACTOR_RIGHT)) {
            		player.move(Actor.ACTOR_RIGHT);
            		Globals.delayManager.add("KeyMove", 0.25f);
            	} else {
            		player.setFacing(Actor.ACTOR_RIGHT);
            	}
            }
    	}
		
		if(input.keyReleased(ATTACK) && Globals.delayManager.isDelayFinished("KeyAttack")) {
			player.attack();
			Globals.delayManager.add("KeyAttack", 1f);
		}
		
		if(input.keyReleased(INTERACT) && Globals.delayManager.isDelayFinished("KeyInteract")) {
			Vector2 tileToCheck = Globals.player.getFacingTile();
			Globals.objects.interact(tileToCheck);
			
			Globals.delayManager.add("KeyInteract", 1f);
		}
		
		if(input.keyReleased(INVENTORY)) {
			// open the inventory
			Globals.stateManager.setState("StateGameInventory");
		}
		
		// testing
		if(input.keyReleased(PATH) && Globals.delayManager.isDelayFinished("KeyPath")) {
			// run pathing
			Globals.actors.buildPaths();
			Globals.delayManager.add("KeyPath", 1f);
		}
		
		if (input.keyReleased(Keys.ESCAPE)) {
            Globals.stateManager.setState("StateGameMenu");
        }
		
        if (input.keyReleased(Keys.F2)) {
            if(Gdx.graphics.isFullscreen()) {
            	Gdx.graphics.setDisplayMode(1024, 768, false);
            } else {
            	Gdx.graphics.setDisplayMode(0, 0, true);
            }
        }
	}

	@Override
	void close(int nextStateID) {
		// save stuff here
		if(!gameSubStates.contains(nextStateID)) {
			
		}
	}

	@Override
	void dispose() {
		
	}

}
