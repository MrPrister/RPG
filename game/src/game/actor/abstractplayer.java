package game.actor;

import main.SavePart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import game.actor.attribute.Attribute;
import game.camera.ScreenText;
import game.item.Inventory;
import game.item.Item;
import game.item.interfaces.Weapon;
import game.item.weapon.Fists;
import tools.Globals;
import tools.random.Random;


public class AbstractPlayer extends Actor {
	
	// skill static variable names
	public static String ATTR_STRENGTH = "strength";
	public static String ATTR_AGILITY = "agility";
	public static String ATTR_PERCEPTION = "perception";
	public static String ATTR_INTELLECT = "intellect";
	public static String ATTR_LUCK = "luck";
	
	public static String SKILL_INVENTORY = "inventory";
	public static String SKILL_ONE_HANDED = "oneHanded";
	public static String SKILL_TWO_HANDED = "twoHanded";
	public static String SKILL_RANGED = "ranged";
	public static String SKILL_MAGIC = "magic";
	public static String SKILL_MEDICINE = "medicine";
	public static String SKILL_ENGINEERING = "engineering";
	public static String SKILL_DECIPHER = "decipher";
	public static String SKILL_LOCK_PICKING = "lockPicking";
	public static String SKILL_COOKING = "cooking";
	public static String SKILL_FORAGING = "foraging";
	public static String SKILL_STEALTH = "stealth";
	public static String SKILL_MAGIC_RESISTANCE = "magicResistance";
	public static String SKILL_BLOCKING = "blocking";
	public static String SKILL_MELEE = "melee";
	public static String SKILL_BREWING = "brewing";
	
	// when the player is locked the we ignore all keyboard input
	private boolean locked;
	
	// what level the player is currently on
	private int level;
	
	public Inventory inventory;
	
	/**
	 * load a player
	 * @param playerData
	 */
	public AbstractPlayer(SavePart playerData) {
		super(playerData);
		inventory = new Inventory(save.getSubset("inventory"));
	}
	
	/**
	 * create a new player
	 * @param startX
	 * @param startY
	 */
	public AbstractPlayer(int startX, int startY) {
		super(startX, startY);
		createSkills();
		
		inventory = new Inventory(20);
        
        current = idleDown;
        
        Globals.delayManager.add("AttackSound", 1f);
	}
	
	@Override
	public void createAttributes() {
		super.createAttributes();
		attributes.add(new Attribute("food", food(), 0, food()));
	}
	
	public void createSkills() {
		// we don't need to create moves as it's already been created
		Attribute moves = attributes.getAttribute("moves");
		moves.setMax(7);
		moves.setMin(0);
		moves.setBase(2);
		
		attributes.add(new Attribute(AbstractPlayer.SKILL_INVENTORY, 40, 0, 0));
		attributes.add(new Attribute(AbstractPlayer.SKILL_ONE_HANDED, 100, 0, 0));
		attributes.add(new Attribute(AbstractPlayer.SKILL_TWO_HANDED, 100, 0, 0));
		attributes.add(new Attribute(AbstractPlayer.SKILL_RANGED, 100, 0, 0));
		attributes.add(new Attribute(AbstractPlayer.SKILL_MAGIC, 100, 0, 0));
		attributes.add(new Attribute(AbstractPlayer.SKILL_MEDICINE, 100, 0, 0));
		attributes.add(new Attribute(AbstractPlayer.SKILL_ENGINEERING, 100, 0, 0));
		attributes.add(new Attribute(AbstractPlayer.SKILL_DECIPHER, 100, 0, 0));
		attributes.add(new Attribute(AbstractPlayer.SKILL_LOCK_PICKING, 100, 0, 0));
		attributes.add(new Attribute(AbstractPlayer.SKILL_COOKING, 100, 0, 0));
		attributes.add(new Attribute(AbstractPlayer.SKILL_FORAGING, 100, 0, 0));
		attributes.add(new Attribute(AbstractPlayer.SKILL_STEALTH, 100, 0, 0));
		attributes.add(new Attribute(AbstractPlayer.SKILL_MAGIC_RESISTANCE, 100, 0, 0));
		attributes.add(new Attribute(AbstractPlayer.SKILL_BLOCKING, 100, 0, 0));
		attributes.add(new Attribute(AbstractPlayer.SKILL_MELEE, 100, 0, 0));
		attributes.add(new Attribute(AbstractPlayer.SKILL_BREWING, 100, 0, 0));
		
		// override this function in each player class and apply a class bonus to each skill to determine the starting value
	}
	
	public void updateSkills() {
		// update the skills
		
		int i = 0;	// temp variable to hold current skill total
		int strength = attributes.getValue(AbstractPlayer.ATTR_STRENGTH);
		int agility = attributes.getValue(AbstractPlayer.ATTR_AGILITY);
		int perception = attributes.getValue(AbstractPlayer.ATTR_PERCEPTION);
		int intellect = attributes.getValue(AbstractPlayer.ATTR_INTELLECT);
		int luck = attributes.getValue(AbstractPlayer.ATTR_LUCK);
		
		// i = (int) ((strength * 0f) + (agility * 0f) + (perception * 0f) + (intellect * 0f) + (luck * 0f));
		
		// moves - max: 4
		i = (int) ((strength * 0f) + (agility * 0.4f) + (perception * 0f) + (intellect * 0f) + (luck * 0f));
		attributes.getAttribute("moves").setBase(i);
		
		// inventory space - max: 10
		i = (int) ((strength * 1f) + (agility * 0f) + (perception * 0f) + (intellect * 0f) + (luck * 0f));
		attributes.getAttribute(AbstractPlayer.SKILL_INVENTORY).setBase(i);
		
		// one handed - max: 32 
		i = (int) ((strength * 1.2f) + (agility * 1.7f) + (perception * 0f) + (intellect * 0f) + (luck * 0f));
		attributes.getAttribute((String) AbstractPlayer.SKILL_ONE_HANDED).setBase(i);
		
		// two handed - max: 26 
		i = (int) ((strength * 1.8f) + (agility * 0.8f) + (perception * 0f) + (intellect * 0f) + (luck * 0f));
		attributes.getAttribute((String) AbstractPlayer.SKILL_TWO_HANDED).setBase(i);
		
		// ranged - max: 30
		i = (int) ((strength * 0.6f) + (agility * 1.0f) + (perception * 1.4f) + (intellect * 0f) + (luck * 0f));
		attributes.getAttribute((String) AbstractPlayer.SKILL_RANGED).setBase(i);
		
		// magic - max: 26
		i = (int) ((strength * 0f) + (agility * 0.4f) + (perception * 0.4f) + (intellect * 1.8f) + (luck * 0f));
		attributes.getAttribute((String) AbstractPlayer.SKILL_MAGIC).setBase(i);
		
		// medicine - max: 26 
		i = (int) ((strength * 0f) + (agility * 0f) + (perception * 0.6f) + (intellect * 1.6f) + (luck * 0.4f));
		attributes.getAttribute((String) AbstractPlayer.SKILL_MEDICINE).setBase(i);
		
		// engineering - max: 28 
		i = (int) ((strength * 0.2f) + (agility * 0f) + (perception * 0.4f) + (intellect * 1.8f) + (luck * 0.2f));
		attributes.getAttribute((String) AbstractPlayer.SKILL_ENGINEERING).setBase(i);
		
		// decipher - max: 23
		i = (int) ((strength * 0f) + (agility * 0f) + (perception * 0.5f) + (intellect * 1.8f) + (luck * 0f));
		attributes.getAttribute((String) AbstractPlayer.SKILL_DECIPHER).setBase(i);
		
		// lock picking - max: 23
		i = (int) ((strength * 0f) + (agility * 0f) + (perception * 1.4f) + (intellect * 0.4f) + (luck * 0.4f));
		attributes.getAttribute((String) AbstractPlayer.SKILL_LOCK_PICKING).setBase(i);
		
		// cooking - max: 15
		i = (int) ((strength * 0.2f) + (agility * 0.2f) + (perception * 0.4f) + (intellect * 0.5f) + (luck * 0.2f));
		attributes.getAttribute((String) AbstractPlayer.SKILL_COOKING).setBase(i);
		
		// foraging - max: 22
		i = (int) ((strength * 0f) + (agility * 0.2f) + (perception * 1f) + (intellect * 0f) + (luck * 1f));
		attributes.getAttribute((String) AbstractPlayer.SKILL_FORAGING).setBase(i);
		
		// stealth - max: 28
		i = (int) ((strength * 0f) + (agility * 0.8f) + (perception * 1.6f) + (intellect * 0.2f) + (luck * 0.2f));
		attributes.getAttribute((String) AbstractPlayer.SKILL_STEALTH).setBase(i);
		
		// magic resistance - max: 32
		i = (int) ((strength * 0.6f) + (agility * 0.2f) + (perception * 0.8f) + (intellect * 1.4f) + (luck * 0.2f));
		attributes.getAttribute((String) AbstractPlayer.SKILL_MAGIC_RESISTANCE).setBase(i);
		
		// blocking - max: 24
		i = (int) ((strength * 1.4f) + (agility * 0.6f) + (perception * 0f) + (intellect * 0.2f) + (luck * 0.2f));
		attributes.getAttribute((String) AbstractPlayer.SKILL_BLOCKING).setBase(i);
		
		// melee - max: 24
		i = (int) ((strength * 1.8f) + (agility * 0.2f) + (perception * 0f) + (intellect * 0.2f) + (luck * 0.2f));
		attributes.getAttribute((String) AbstractPlayer.SKILL_MELEE).setBase(i);
		
		// brewing - max: 22
		i = (int) ((strength * 0f) + (agility * 0f) + (perception * 0.4f) + (intellect * 1.8f) + (luck * 0f));
		attributes.getAttribute((String) AbstractPlayer.SKILL_BREWING).setBase(i);
	}
	
	@Override
	public SavePart save() {
		super.save();
		save.put("class", this.getClass().getSimpleName());
		save.putSubset("inventory", inventory.save());
		
		return save;
	}
	
	@Override
	public int health() {
		return 100;
	}
	
	@Override
	public int moves() {
		// formality, player moves are calculated in the update skills function
		return 0;
	}
	
	//@Override
	public int food() {
		return 100;
	}
	
	public void update(float delta) {
		if(isDead()) {
			Globals.stateManager.setState("StateGameOver");
		} else {
			super.update(delta);
			
			if(transX != 0.0f || transY != 0.0f) {
				lock();
			} else {
				unlock();
			}
			
			pickUp();
			checkFood();
			updateSkills();
			inventory.setSlots(attributes.getValue(AbstractPlayer.SKILL_INVENTORY));
		}
	}
	
	public Vector2 getFacingTile() {
		int tx = x;
		int ty = y;
		
		switch (facing) {
		case 1:
			ty++;
			break;
		case 2:
			ty--;
			break;
		case 3:
			tx--;
			break;
		case 4:
			tx++;
			break;
		default:
			break;
		}
		
		return new Vector2(tx, ty);
	}
	
	public boolean isLocked() {
		return locked;
	}
	public void lock() {
		locked = true;
	}
	public void unlock() {
		locked = false;
	}
	
	@Override
	public
	Texture spriteSheet() {
		spriteWidth = 32;
		spriteHeight = 32;
		return new Texture(Gdx.files.internal("actor/knight.png"));
	}
	
	/**
	 * check if the player has enough food, kill them if they don't
	 */
	private void checkFood() {
		Attribute food = attributes.getAttribute("food");
		
		if(food.getValue() <= 0) {
			kill();
		}
	}
	/**
	 * return the player's food level
	 * @return
	 */
	public int getFood() {
		return attributes.getValue("food");
	}
	/**
	 * add an amount to the player's food level
	 * @param amount
	 */
	public void addFood(int amount) {
		Attribute food = attributes.getAttribute("food");
		food.addToBase(amount);
		checkFood();
	}
	/**
	 * remove an amount from the player's food level
	 * @param amount
	 */
	public void removeFood(int amount) {
		addFood(-amount);
	}
	
	@Override
	public void move(int direction) {
		super.move(direction);
		
		removeFood(1);
	}

	@Override
	public void death() {
		// do nothing... yet
	}

	public Vector2 getLocation() {
		return new Vector2(this.x, this.y);
	}
	
	public void pickUp() {
		// try to add all the items from the current tile to the inventory
		
		Item item = Globals.items.getItemInTile(getLocation());
		if(item != null && item.canCollect()) {
			int tempx = item.getX();
			int tempy = item.getY();
			if(inventory.add(item)) {
				Globals.items.remove(item);
				Globals.screenText.add(new ScreenText(item.getName(), new Vector2(tempx, tempy), Color.WHITE));
			}
		}
	}

	@Override
	public void attack() {
		// get the equipped weapon and attack with it
		Weapon weapon = (Weapon) Globals.player.inventory.getEquipped(Inventory.equipWeapon);
		
		if(weapon != null) {
			weapon.attack();
		} else {
			Fists fists = new Fists();
			fists.attack();
		}
	}

}
