package game.item.weapon;

import tools.Globals;
import tools.random.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * SPECIAL CLASS
 * 
 * use when the player attacks with no weapon equipped
 * 
 * @author Matt
 *
 */

public class Fists extends AbstractMeleeWeapon {

	public Fists() {
		// do nothing
	}
	
	@Override
	public Texture itemImage() {
		return null;
	}
	
	@Override
	public String itemName() {
		return "Fists";
	}

	@Override
	public void use() {
		
	}

	@Override
	public int maxStack() {
		return 1;
	}

	@Override
	public void pickup() {

	}

	@Override
	public void drop() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void equipped() {

	}

	@Override
	public void unequipped() {

	}

	@Override
	public void attack() {
		Vector2 toAttack = Globals.player.getFacingTile();
		Globals.actors.hurtActorInTile(toAttack, Random.number((int)(baseAttack() - baseAttackRange()), (int)(baseAttack() + baseAttackRange())));

		if(Globals.delayManager.isDelayFinished("AttackSound")) {
			Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/sword_swipe.mp3"));
			sound.play();
			Globals.delayManager.add("AttackSound", 1f);
		}
	}

	@Override
	public float baseAttack() {
		return 2;
	}

	@Override
	public float baseAttackRange() {
		return 1;
	}
	
	@Override
	public boolean canEquip() {
		return true;
	}
	@Override
	public int equipSlot() {
		return 2;
	}
	@Override
	public boolean canUse() {
		return false;
	}
	@Override
	public boolean canDrop() {
		return false;
	}
	@Override
	public boolean canDestory() {
		return false;
	}

}
