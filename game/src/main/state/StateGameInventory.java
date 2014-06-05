package main.state;

import java.lang.reflect.Constructor;

import main.SavePart;
import game.item.InventorySlot;
import game.item.Item;
import tools.Fonts;
import tools.Globals;
import tools.UserInput;
import tools.ui.Label;
import tools.ui.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StateGameInventory extends AbstractState {

	
	public StateGameInventory() {}
	
	@Override
	void init() {
		
	}

	@Override
	void open(int previousStateID) {
		
	}
	
	public int INVENTORY = Keys.I;
	
	@Override
	void input(UserInput input) {
		if(input.keyReleased(INVENTORY) || input.keyReleased(Keys.ESCAPE)) {
			if(input.keyReleased(Keys.ESCAPE) && showMenu) {
				menuClose();
			} else {
				// close the inventory
				Globals.stateManager.setState("StateGame");
			}
		}
		
		if(showMenu) {
			menu.input(input);
		}
		
		if(input.mouseClicked()) {
			// the mouse has been clicked
			// if the menu is closed and the click was on an inventory item, show the menu for that item
			// if the menu is open see if the click was within that menu
				// if so pass the command on to the item
				// if not was it within the inventory, show menu for that item
				// if it was not in the inventory or the menu close the menu
			if(!showMenu) {
				// display the menu for the given item
				// get the item
				// build the menu
				clickedSlot = Globals.player.inventory.getSlot(input.mouseX(), input.mouseY());
				
				if(clickedSlot != null) {
					Item slotItem = clickedSlot.getItem();
					
					// only show a menu if an item is in the slot and an action is valid for the item
					if(slotItem != null && (slotItem.canUse || slotItem.canDrop || slotItem.canDestory)) {
						BitmapFont font = Fonts.font32;
						menu = new Menu();
						if(slotItem.canUse) {
							menu.addOption(new Label(clickedSlot.getItem().getUseOption(), font), ITEM_MENU_USE);
						}
						if(slotItem.canDrop) {
							menu.addOption(new Label(clickedSlot.getItem().getDropOption(), font), ITEM_MENU_DROP);
						}
						if(slotItem.canDestory) {
							menu.addOption(new Label(clickedSlot.getItem().getDestroyOption(), font), ITEM_MENU_DESTROY);
						}
						menu.addOption(new Label("Cancel", font), ITEM_MENU_CANCEL);
						
						menu.setX(input.mouseX());
						menu.setY(input.mouseY());
						
						menu.setBackgroundColor(Color.BLACK);
						menu.setOutlineColor(Color.WHITE);
						menu.setDrawOutline(true);
						
						menu.setFocusXDelta(10);
						
						// need a way to do this automatically based on menu options
						menu.setWidth(100);
						menu.setHeight(100);
						
						showMenu = true;
					} else {
						System.out.println("you clicked on an empty slot ["+Globals.player.inventory.getSlotIndex(input.mouseX(), input.mouseY())+"]");
					}
				}
			}
			
		}
	}

	public InventorySlot clickedSlot;		// the slot that has been clicked on
	public boolean showMenu;				// whether to show the menu
	public Menu menu;						// the actual menu item
	
	public void menuClose() {
		menu = null;
		showMenu = false;
		clickedSlot = null;
	}
	
	public final int ITEM_MENU_USE = 0;
	public final int ITEM_MENU_DROP = 1;
	public final int ITEM_MENU_DESTROY = 2;
	public final int ITEM_MENU_CANCEL = 3;
	
	@Override
	void update(float delta) {
		if(menu != null) {
			if(menu.hasSelected()) {
				// System.out.println("menu option clicked ["+menu.getSelected()+"]");
				
				Item slotItem = clickedSlot.getItem();
				
				switch(menu.getSelected()) {
					case ITEM_MENU_USE:
						/*
						 * call the use command on the item from the slot - (can the item handle it's own destruction?)
						 */
						clickedSlot.decreaseQty(1);
						slotItem.use();
						menuClose();
						
						break;
					case ITEM_MENU_DROP:
						/*
						 * drop the item from the slot, if more than 3 ask how many user would like to drop
						 */
						SavePart itemSave = slotItem.getSavePart();

						if(itemSave.get("class") != null) {
							try {
								
								String className = itemSave.get("class");
								@SuppressWarnings("rawtypes")
								Class cl = Class.forName(className);
								@SuppressWarnings({ "rawtypes", "unchecked" })
								Constructor con = cl.getConstructor(SavePart.class);
								Item itemToDrop = (Item) con.newInstance(itemSave);
								
								itemToDrop.setInInventory(false);
								itemToDrop.setX(Globals.player.x);
								itemToDrop.setY(Globals.player.y);
								
								Globals.items.add(itemToDrop);
								
								clickedSlot.decreaseQty(1);
								
								menuClose();
								
								// how do we stop the player picking up the item we've just dropped on the next game loop???
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
						break;
					case ITEM_MENU_DESTROY:
						/*
						 * similar to drop but instead of putting back into game world delete the item(s)
						 */
						clickedSlot.decreaseQty(1);
						menuClose();
						
						break;
					case ITEM_MENU_CANCEL:
						menuClose();
						
						break;
					default:
						menuClose();
						
						break;
				}
				
			} else {
				// menu.update();
			}
		}
	}
	
	@Override
	void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// render the game - call the render method from the game state
		AbstractState gameState = Globals.stateManager.getStateByName("StateGame");
		gameState.render();
		
		// render the inventory as an overlay
		SpriteBatch batch = new SpriteBatch();
		batch.begin();
			Texture overlay = new Texture(Gdx.files.internal("trans-70-black.png"));
			batch.draw(overlay, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
			Globals.player.inventory.render(batch);
			if(showMenu) {
				menu.render(batch);
			}
		batch.end();
	}

	@Override
	void close(int nextStateID) {
		
	}

	@Override
	void dispose() {
		// TODO Auto-generated method stub

	}

}
