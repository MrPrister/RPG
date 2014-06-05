package main.state;

import tools.UserInput;

// instead of separate managers handling all the rendering, updates and input for all states
// we have each state handle its own update, render and input for more flexibility

public abstract class AbstractState {

	public int ID; 
	
	/**
	 * return the unique integer id for this state
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * called only once when the state is added to the stateManager
	 */
	abstract void init();
	
	/**
	 * called every time the state becomes the active state
	 * @param delta
	 */
	abstract void open(int previousStateID);
	
	/**
	 * called every game loop (before update) when the state is current, handles input logic
	 * @param input 
	 */
	abstract void input(UserInput input);
	
	/**
	 * called every game loop when the state is current, handles state logic
	 * @param delta
	 */
	abstract void update(float delta);
	
	/**
	 * called every game loop when the state is current, handles rendering the state
	 */
	abstract void render();
	
	/**
	 * called every time the state stops being the current state
	 */
	abstract void close(int nextStateID);
	
	/**
	 * called when the game is quit
	 */
	abstract void dispose();
	
	@Override
    public boolean equals(Object object) {
		
        if (object != null && object instanceof AbstractState) {
            if(this.getID() == ((AbstractState) object).getID()) {
            	return true;
            }
        }
        
        return false;
    }

	public void setID(int stateIndex) {
		ID = stateIndex;
	}
}
