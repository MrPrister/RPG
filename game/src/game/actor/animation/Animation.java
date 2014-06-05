package game.actor.animation;

import tools.Globals;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation extends com.badlogic.gdx.graphics.g2d.Animation {

	public Animation(float frameDuration, TextureRegion[] keyFrames) {
		super(frameDuration, keyFrames);
		addToAnimationManager(Globals.animations);
	}

	public Animation(float frameDuration, TextureRegion region) {
		super(frameDuration, region);
		addToAnimationManager(Globals.animations);
	}

	private boolean paused = false;
	private float runTime = 0;
	
	/**
	 * add delta to the runTime
	 * @param delta
	 */
	public void update(float delta) {
		if(!paused) {
			runTime += delta;
		}
	}
	
	/**
	 * pause the animation
	 */
	public void pause() {
		paused = true;
	}
	
	/**
	 * play the animation
	 */
	public void play() {
		paused = false;
	}
	
	/**
	 * set the run time to time
	 * @param time
	 */
	public void setRunTime(float time) {
		runTime = time;
	}
	
	/**
	 * return the current run time
	 * @return
	 */
	public float getRunTime() {
		return runTime;
	}
	
	/**
	 * is the animation paused
	 * @return
	 */
	public boolean isPaused() {
		return paused;
	}
	
	/**
	 * is the animation running
	 * @return
	 */
	public boolean isPlaying() {
		return !paused;
	}

	public TextureRegion getCurrentFrame() {
		return this.getKeyFrame(runTime);
	}
	
	/**
	 * add this animation to the animation manager to update the run time
	 * @param am
	 */
	private void addToAnimationManager(AnimationManager am) {
		am.add(this);
	}
}
