//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;

class GoblinBattle {

    public static void main(String[] args) {
        // Create the game world
        GameWorld gameWorld = new GameWorld(10, 10);

        // Create entities (humans and goblins) and add them to the game world
        Human player = new Human(0, 0, 25, 30);  // x, y, health, attack
        Goblin goblin = new Goblin(5, 5, 30, 20);  // x, y, health, attack

        gameWorld.addEntity(player);
        gameWorld.addEntity(goblin);

        // Display the initial state of the game world
        gameWorld.display();

        // Simulate movement and combat
        Scanner scanner = new Scanner(System.in);
        while (player.isAlive() && goblin.isAlive()) {

            System.out.print("\nEnter direction (WASD): ");
            char direction = scanner.next().charAt(0);   //either w, a, s, d
            movePlayer(player, direction, gameWorld);
            gameWorld.display();
        }

        //To get here, either the Player or Goblin has lost the battle
        //the getActionLog() returns the results of the combat
        System.out.println("\n*** An Epic Battle Begins ***");
        System.out.println(gameWorld.getActionLog());
        System.out.println("***     Game Over         ***");
        System.out.println("*** Thank you for playing ***");

        scanner.close();
    }


    private static void movePlayer(Human player, char direction, GameWorld gameWorld) {
        int newX = player.x;
        int newY = player.y;

        switch (direction) {
            case 'w':
                newY--;
                break;
            case 'a':
                newX--;
                break;
            case 's':
                newY++;
                break;
            case 'd':
                newX++;
                break;
        }


        if (gameWorld.isValidPosition(newX, newY)) {
            Entity entityAtNewPosition = gameWorld.entities[newY][newX];
            if (entityAtNewPosition == null || entityAtNewPosition instanceof Goblin) {

                //If there is a Goblin you are landing on, the FIGHT is ON!
                if(entityAtNewPosition instanceof Goblin) {
                    Goblin g = (Goblin)entityAtNewPosition;
                    Entity winner = gameWorld.combat(player, g);  //Battle to the finish!  Combat returns the entity that won (either player or goblin)
                    gameWorld.moveEntity(winner, newX, newY);  // only the winner remains in the new position
                }
                else {
                    // Move the player only if the new position is empty or Treasure
                    if(gameWorld.isTreasure(newY, newX))
                    {
                        gameWorld.addInventory("Treasure");
                        gameWorld.removeTreasure();
                    }

                    gameWorld.moveEntity(player, newX, newY);
                }


            } else {
                // Handle collision between player and non-Goblin entity if needed
                System.out.println("Collision detected at (" + newX + ", " + newY + ")");

            }
        } else {
            System.out.println("Invalid move!");
        }
    }
}

