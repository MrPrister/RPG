package main.state;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;

import tools.Globals;

/**
 * using the program state the state manager decides which functions from renderManager, inputManager and updateManager to call
 * @author mattadams
 *
 */
public class StateManager {
	
	ArrayList<AbstractState> states;
	private int stateIndex = 0;						// variable to calculate the next ID for adding a state
	
	private AbstractState currentState;				// holds the current state of the game (splash, menu, options, game, etc)
	
	public StateManager(String startingState) {
		states = new ArrayList<AbstractState>();
		
		// add the states
		addState(new StateSplash());
		addState(new StateMainMenu());
		addState(new StateNewGame());
		addState(new StateLoadGame());
		addState(new StateCredits());
		
		addState(new StateGame());
		addState(new StateGameInventory());
		addState(new StateGameOver());
		addState(new StateGameMenu());
		
		addState(new StateTest());
		
		setState(getStateID(startingState));
	}
	
	public void start() {
		// call the init methods on each state
		for (Iterator<AbstractState> iterator = states.iterator(); iterator.hasNext();) {
			AbstractState state = (AbstractState) iterator.next();
			
			state.init();
		}
	}
	
	public void addState(AbstractState state) {
		stateIndex++;
		state.setID(stateIndex);
		
		System.out.println("created state '" + state.getClass().getSimpleName() + "' with ID of [" + stateIndex + "]");
		
		states.add(state);
	}
	
	/**
	 * get the class id for the given state
	 * @param stateName
	 * @return the state id if found or -1 if not found
	 */
	public int getStateID(String stateName) {
		for (Iterator<AbstractState> iterator = states.iterator(); iterator.hasNext();) {
			AbstractState state = (AbstractState) iterator.next();
			
			if(state.getClass().getSimpleName().equals(stateName)) {
				return state.ID;
			}	
		}
		
		System.out.println("no state with the name " + stateName + " could be found");
		return -1;
	}
	
	public AbstractState getStateByName(String stateName) {
		return getStateByID(getStateID(stateName));
	}
	
	public void setState(int stateID) {
		if(doesStateExist(stateID)) {
			int previousStateID;
			
			// only change the state if the id specified actually is an id for a state
			if(currentState != null) {
				currentState.close(stateID);
				previousStateID = currentState.getID();
			} else {
				previousStateID = -1;
			}
			
			this.currentState = getStateByID(stateID);
			currentState.open(previousStateID);
		}
	}
	
	public void setState(String stateName) {
		setState(getStateID(stateName));
	}
	
	private AbstractState getStateByID(int stateID) {
		for (Iterator<AbstractState> iterator = states.iterator(); iterator.hasNext();) {
			AbstractState state = (AbstractState) iterator.next();
			
			if(state.getID() == stateID) {
				return state;
			}
		}
		
		System.out.println("state ["+stateID+"] not found!!");
		return null;
	}
	
	private boolean doesStateExist(int stateID) {
		for (Iterator<AbstractState> iterator = states.iterator(); iterator.hasNext();) {
			AbstractState state = (AbstractState) iterator.next();
			
			if(state.getID() == stateID) {
				return true;
			}
		}
		
		return false;
	}

	public void run() {
		float delta = Gdx.graphics.getDeltaTime();
		
		Globals.delayManager.update(delta);
		
		currentState.input(Globals.input);
		currentState.update(delta);
		currentState.render();
	}
	
}
