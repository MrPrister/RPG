package game.item.weapon;

import main.SavePart;
import tools.Globals;
import tools.random.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Sword extends AbstractMeleeWeapon {

	public Sword() {
		super();
	}
	public Sword(int x, int y) {
		super(x, y);
	}
	public Sword(SavePart save) {
		super(save);
	}
	
	@Override
	public Texture itemImage() {
		Texture img = new Texture(Gdx.files.internal("item/sword-1.png"));
		return img;
	}
	
	@Override
	public String itemName() {
		return "Ornate Sword";
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void equipped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unequipped() {
		// TODO Auto-generated method stub
		
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
		return 30;
	}

	@Override
	public float baseAttackRange() {
		return 5;
	}

}
