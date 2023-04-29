package stronghold.model.people;

import stronghold.model.Government;

public class Archer extends Troop {
	private int firingRange;
	private int fireRate;

	public Archer(int speed, int hp, int damage, int x, int y, Government owner, StanceType stance,
			boolean canClimbLadder, boolean canDigMoats) {
		super(speed, hp, damage, x, y, owner, stance, canClimbLadder, canDigMoats);
		this.fireRate = fireRate;
		this.firingRange = firingRange;
	}

	public int getFiringRange() {
		return firingRange;
	}

	public int getFireRate() {
		return fireRate;
	}
	
}