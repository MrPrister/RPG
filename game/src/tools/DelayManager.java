package tools;

import java.util.ArrayList;
import java.util.Iterator;

public class DelayManager {

	public ArrayList<Delay> delays;
	
	public DelayManager() {
		delays = new ArrayList<Delay>();
	}
	
	/**
	 * update each delay
	 * @param delta
	 */
	public void update(float delta) {
		//System.out.println("there are " + delays.size() + " delays to update");
		
		for (Iterator<Delay> iterator = delays.iterator(); iterator.hasNext();) {
			Delay delay = (Delay) iterator.next();
			
			delay.update(delta);
		}
	}
	
	public void add(Delay delay) {
		delays.add(delay);
	}
	
	public void add(String UID, float delayTime) {
		// if the delay already exists update it instead of adding it again
		if(delays.contains(new Delay(UID, 0))) {
			Delay delay = delays.get(delays.lastIndexOf(new Delay(UID, 0)));
			delay.setDelay(delayTime);
		} else {
			delays.add(new Delay(UID, delayTime));
		}
	}
	
	/**
	 * return if the delay is finished for the delay with the given UID
	 * @param UID
	 * @return
	 */
	public boolean isDelayFinished(String UID) {
		for (Iterator<Delay> iterator = delays.iterator(); iterator.hasNext();) {
			Delay delay = (Delay) iterator.next();
			
			if(delay.UID == UID) {
				return delay.isFinished();
			}
		}
		
		// if we couldn't find the delay then we cant say it hasn't finished, so return true
		return true;
	}
	
}
