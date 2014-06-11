package game.actor;

import game.actor.animation.Animation;
import game.actor.attribute.Attribute;
import game.actor.attribute.AttributeManager;

import java.util.Stack;

import main.SavePart;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import tools.Globals;
import tools.Tools;

/**
 * basic class for all moving objects in the game
 * @author Matt-SSD
 *
 */
public abstract class Actor {
		
		// actor save file
		public SavePart save;
	
		// actors location, relative to the map coordinates
		public int x;
		public int y;
		//public Vector2 location;
		public int facing;
		
		// actor attributes
		public AttributeManager attributes;
		
		// the path the actor will follow
		public Stack<Vector2> path;
		
		// actor flags
		private boolean moving;		// is the actor currently moving between tiles
		private boolean dead;		// is the actor dead
		
		// actor move directions
		public static int ACTOR_UP = 1;
		public static int ACTOR_DOWN = 2;
		public static int ACTOR_LEFT = 3;
		public static int ACTOR_RIGHT = 4;
		
		// actor padding
		protected int paddingX = 0;
		protected int paddingY = 0;
		
		// tile movement translation
		protected float transX;
		protected float transY;
		protected float transStep;
		
		// animations & sprite sheet
		protected int spriteWidth;
		protected int spriteHeight;
		protected Texture spritesheet;
		protected Animation current;
		
		// common animations
		protected Animation moveUp;
		protected Animation moveDown; 
		protected Animation moveLeft;
		protected Animation moveRight;
		protected Animation idleUp;
		protected Animation idleDown; 
		protected Animation idleLeft;
		protected Animation idleRight;
		
		//animations that must exist for every actor
		abstract Texture spriteSheet();
		
		Animation moveUp() {
			TextureRegion regions[] = {
					new TextureRegion(spritesheet, 0, spriteHeight * 3, spriteWidth, spriteHeight),
					new TextureRegion(spritesheet, spriteWidth, spriteHeight * 3, spriteWidth, spriteHeight),
					new TextureRegion(spritesheet, spriteWidth * 2, spriteHeight * 3, spriteWidth, spriteHeight)
			};
			
			Animation animation = new Animation(1f, regions);
			animation.setPlayMode(Animation.LOOP);
			
			return animation;
		}

		Animation moveDown() {
			TextureRegion regions[] = {
					new TextureRegion(spritesheet, 0, spriteHeight * 0, spriteWidth, spriteHeight),
					new TextureRegion(spritesheet, spriteWidth, spriteHeight * 0, spriteWidth, spriteHeight),
					new TextureRegion(spritesheet, spriteWidth * 2, spriteHeight * 0, spriteWidth, spriteHeight)
			};
			
			Animation animation = new Animation(1f, regions);
			animation.setPlayMode(Animation.LOOP);
			
			
			return animation;
		}

		Animation moveLeft() {
			TextureRegion regions[] = {
					new TextureRegion(spritesheet, 0, spriteHeight * 1, spriteWidth, spriteHeight),
					new TextureRegion(spritesheet, spriteWidth, spriteHeight * 1, spriteWidth, spriteHeight),
					new TextureRegion(spritesheet, spriteWidth * 2, spriteHeight * 1, spriteWidth, spriteHeight)
			};
			
			Animation animation = new Animation(1f, regions);
			animation.setPlayMode(Animation.LOOP);
			
			return animation;
		}

		Animation moveRight() {
			TextureRegion regions[] = {
					new TextureRegion(spritesheet, 0, spriteHeight * 2, spriteWidth, spriteHeight),
					new TextureRegion(spritesheet, spriteWidth, spriteHeight * 2, spriteWidth, spriteHeight),
					new TextureRegion(spritesheet, spriteWidth * 2, spriteHeight * 2, spriteWidth, spriteHeight)
			};
			
			Animation animation = new Animation(1f, regions);
			animation.setPlayMode(Animation.LOOP);
			
			return animation;
		}

		Animation idleUp() {
			TextureRegion region = new TextureRegion(spritesheet, spriteWidth, 3 * spriteHeight, spriteWidth, spriteHeight);
			
			Animation animation = new Animation(1f, region);
			animation.setPlayMode(Animation.LOOP);
			
			return animation;
		}

		Animation idleDown() {
			TextureRegion region = new TextureRegion(spritesheet, spriteWidth, 0 * spriteHeight, spriteWidth, spriteHeight);
			
			Animation animation = new Animation(1f, region);
			animation.setPlayMode(Animation.LOOP);
			
			return animation;
		}

		Animation idleLeft() {
			TextureRegion region = new TextureRegion(spritesheet, spriteWidth, 1 * spriteHeight, spriteWidth, spriteHeight);
			
			Animation animation = new Animation(1f, region);
			animation.setPlayMode(Animation.LOOP);
			
			return animation;
		}

		Animation idleRight() {
			TextureRegion region = new TextureRegion(spritesheet, spriteWidth, 2 * spriteHeight, spriteWidth, spriteHeight);
			
			Animation animation = new Animation(1f, region);
			animation.setPlayMode(Animation.LOOP);
			
			return animation;
		}
		
		// base stats
		abstract int health();
		abstract int moves();
		
		
		// 'scripts'
		abstract void death();		// run when the actor dies
		abstract void attack();		// logic for attacking
		
		/**
		 * load the actor from the save file
		 * @param save
		 */
		public Actor(SavePart actorSave) {
			save = actorSave;
			
			x = Integer.parseInt(save.get("xPos", "1"));
			y = Integer.parseInt(save.get("yPos", "1"));
			facing = Integer.parseInt(save.get("facing", "2"));
			
			dead = Boolean.parseBoolean(save.get("dead", "false"));
			moving = Boolean.getBoolean(save.get("moving", "false"));
			
			attributes = new AttributeManager(save.getSubset("attributes"));
			
			init();
		}
		
		/**
		 * create actor at the given map coordinates
		 * @param startX
		 * @param startY
		 */
		public Actor(int startX, int startY) {
			save = new SavePart();
			create(startX, startY);
			init();
		}
		
		public SavePart save() {
			save.clear();
			
			save.put("class", this.getClass().getName());
			
			save.put("xPos", x);
			save.put("yPos", y);
			save.put("facing", facing);
			
			save.put("transX", transX);
			save.put("transY", transY);
			
			save.put("dead", dead);
			save.put("moving", moving);
			
			save.putSubset("attributes", attributes.save());
			
			return save;
		}
		
		public SavePart getSavePart() {
			return save;
		}
		
		public void init() {
			transStep = 2f;
			
			spritesheet = spriteSheet();
			moveUp = moveUp();
			moveDown = moveDown();
			moveLeft = moveLeft();
			moveRight = moveRight();
			idleUp = idleUp();
			idleDown = idleDown();
			idleLeft = idleLeft();
			idleRight = idleRight();
			
			current = getIdleAnimation();
			
			paddingX = (Globals.tileWidth - spriteWidth) / 2;
			paddingY = (Globals.tileHeight - spriteHeight) / 2;
		}
		
		public void create(int startX, int startY) {
			x = startX;
			y = startY;
			facing = Actor.ACTOR_DOWN;
			
			dead = false;
			
			createAttributes();
		}
		
		public void createAttributes() {
			attributes = new AttributeManager();
			
			// basic health value
			attributes.add(new Attribute("health", health(), 0, health()));
			attributes.add(new Attribute("moves", moves(), 0, moves()));
		}
		
		public void setTransX(float x) {
			transX = x;
		}
		public void setTransY(float y) {
			transY = y;
		}
		
		/**
		 * renders actor in the screen location
		 * @param g
		 * @param map
		 */
		public void render(SpriteBatch batch) {
			float rx = ((x * Globals.tileWidth) + paddingX + (transX * Globals.tileWidth));
			float ry = ((y * Globals.tileHeight) + paddingY + (transY * Globals.tileHeight));
			
			TextureRegion texture = current.getCurrentFrame();
			
			batch.draw(
					texture,
	    			rx,
	    			ry,
	    			texture.getRegionWidth(),
	    			texture.getRegionHeight()
	    			);
		}
		
		public void update(float delta) {
			smoothScrolling(delta);
			
			if(transX != 0.0f || transY != 0.0f) {
				setMoving(true);
			} else {
				setMoving(false);
			}
			
			if(!isMoving()) {
				// actor isn't moving so use the idle animation
				current = getIdleAnimation();
			}
			
			updatePath();
			
			// if the player is adjacent to the actor then make sure the actor is facing the player
			int playerDirection = Tools.getDirection(this.getLocation(), Globals.player.getLocation());
			if(playerDirection != 0) {
				this.setFacing(playerDirection);
			}
		}
		
		/**
		 * handle pathing updates
		 */
		private void updatePath() {
			if(!this.moving && path != null && !path.empty()) {
				// the actor is standing still and has a path to complete
				this.move(Tools.getDirection(this.getLocation(), path.pop()));
			}
		}
		
		public Vector2 getLocation() {
			return new Vector2(x, y);
		}
		
		public void setFacing(int direction) {
			facing = direction;
		}
		public int getFacing() {
			return facing;
		}
		public boolean isFacing(int direction) {
			if(direction == facing) {
				return true;
			} else {
				return false;
			}
		}
		private void setMoving(boolean b) {
			moving = b;
		}
		public boolean isMoving() {
			return moving;
		}
		
		public void setPath(Stack<Vector2> path) {
			this.path = path;
		}
		public Stack<Vector2> getPath() {
			return path;
		}
		
		private void smoothScrolling(float delta) {
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
		
		/**
		 * move in the direction specified on the map specified
		 * @param direction
		 * @param map
		 */
		public void move(int direction) {
			switch (direction) {
				case 1:
					//moving up
					if(canMove(direction)) {
						y++;
						transY--;
						facing = Actor.ACTOR_UP;
						current = moveUp;
					}
					break;
				case 2:
					//moving down
					if(canMove(direction)) {
						y--;
						transY++;
						facing = Actor.ACTOR_DOWN;
						current = moveDown;
					}
					break;
				case 3:
					//moving left
					if(canMove(direction)) {
						x--;
						transX++;
						facing = Actor.ACTOR_LEFT;
						current = moveLeft;
					}
					break;
				case 4:
					//moving right
					if(canMove(direction)) {
						x++;
						transX--;
						facing = Actor.ACTOR_RIGHT;
						current = moveRight;
					}
					break;
				default:
					// do nothing
					break;
			}
			
			// update location vector
			//location = new Vector2(this.x, this.y);
		}
		
		public boolean canMove(int direction) {
			int checkX = x;
			int checkY = y;
			
			switch (direction) {
			case 1:
				//moving up
				checkY = y + 1;
				break;
			case 2:
				//moving down
				checkY = y - 1;
				break;
			case 3:
				//moving left
				checkX = x - 1;
				break;
			case 4:
				//moving right
				checkX = x + 1;
				break;
			default:
				// do nothing
				break;
			}
						
			if(Globals.level.tileHasCollision(checkX, checkY) || Globals.actors.isOccupied(checkX, checkY)) {
				return false;
			} else {
				return true;
			}
		}
		
		private Animation getIdleAnimation() {
			switch (facing) {
			case 1:
				return idleUp;
			case 2:
				return idleDown;
			case 3:
				return idleLeft;
			case 4:
				return idleRight;
			default:
				return null;
			}
		}
		
		public void hurt(int amount) {
			Attribute health = attributes.getAttribute("health");
			
			health.addToBase(-amount);
			
			if(health.getValue() <= 0) {
				kill();
			}
		}
		
		public void heal(int amount) {
			Attribute health = attributes.getAttribute("health");
			health.addToBase(amount);
		}
		
		public void kill() {
			dead = true;
		}
		
		public boolean isDead() {
			return dead;
		}
		
		public int getHealth() {
			return attributes.getValue("health");
		}
}
