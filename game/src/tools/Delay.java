package tools;


public class Delay {

	public final String UID;
	
	// the time left in this delay
	private float timeLeft;
	
	// if true the delay won't be updated
	private boolean locked;
	
	public Delay(String UID, float time) {
		this.UID = UID;
		timeLeft = time;
		unlock();
	}
	
	public void setDelay(float time) {
		timeLeft = time;
	}
	
	public void update(float delta) {
		if(isUnlocked() && timeLeft > 0) {
			timeLeft -= delta;
		}
		
		if(timeLeft < 0) {
			timeLeft = 0;
		}
	}
	
	public void lock() {
		locked = true;
	}
	public void unlock() {
		locked = false;
	}
	public void toggleLock() {
		locked = !locked;
	}
	public boolean isLocked() {
		return locked;
	}
	public boolean isUnlocked() {
		return !locked;
	}
	
	public boolean isFinished() {
		if(timeLeft == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public float getDelay() {
		return timeLeft;
	}
	
	@Override
    public boolean equals(Object object) {
        if (object != null && object instanceof Delay) {
            if(this.UID == ((Delay) object).UID) {
            	return true;
            }
        }
        
        return false;
    }
}
