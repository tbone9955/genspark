//Goblin class representing goblin enemies
	public class Goblin extends Entity {
	private int health;
	private int attack;

	public Goblin(int x, int y, int health, int attack) {
	   super(x, y);
	   this.health = health;
	   this.attack = attack;
	}

	// Override toString to visually represent goblins on the grid
	@Override
	public String toString() {
	   return "\uD83D\uDC7A";
	}

	// Method to perform attack
	public int attack() {
	   return attack;
	}

	// Method to take damage
	public void takeDamage(int damage) {
	   health -= damage;
	}

	// Method to check if goblin is alive
	public boolean isAlive() {
	   return health > 0;
	}
}
	

