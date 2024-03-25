public class Human extends Entity {

	//Human class representing human players
	private int health;
	private int attack;

	public Human(int x, int y, int health, int attack) {
	   super(x, y);
	   this.health = health;
	   this.attack = attack;
	}

	// Override toString to visually represent humans on the grid
	@Override
	public String toString() {
	   return "\uD83D\uDC71";

	}

	// Method to perform attack
	public int attack() {
	   return attack;
	}

	// Method to take damage
	public void takeDamage(int damage) {
	   health -= damage;
	}

	// Method to check if human is alive
	public boolean isAlive() {
	   return health > 0;
	}
		
}
