import java.util.Random;
import java.util.ArrayList;

import java.io.IOException;

//GameWorld class representing the game world
public class GameWorld {

    // Changed grid from char to String to get the UTF-8 Tree, Goblin, Player and Treasure graphics working
     private String[][] grid;
Entity[][] entities;
private Random random;
private ArrayList<String> actionLog;  // keeps a running log of the combat
private ArrayList<String> inventory;  // inventory

public GameWorld(int width, int height) {
   grid = new String[height][width];
   entities = new Entity[height][width];
   random = new Random();
   actionLog = new ArrayList<String>();
   inventory = new ArrayList<String>();
   initializeTerrain();
}

// Method to initialize the game world with terrain
private void initializeTerrain() {
   for (int i = 0; i < grid.length; i++) {
       for (int j = 0; j < grid[i].length; j++) {
           grid[i][j] = Terrain.TREE.getSymbol();   //Terrain.LAND.getSymbol();

       }
   }
   // Place treasure chest randomly on the map
   int treasureX = random.nextInt(grid[0].length);
   int treasureY = random.nextInt(grid.length);
   grid[treasureY][treasureX] = Terrain.TREASURE.getSymbol();
   
}

//Returns the Battle Log as a lines of text
public String getActionLog() {
    String s = "";
    for(String line : actionLog){
        s += line;
    }
    return s;
}

public String getInventory() {
    String s = "";
    for(String line : inventory){
        s += line;
    }
    if(s=="")
      return "<No Inventory>";

    return s;
}

//Sets the Log as a lines of text to replay at the end 
public void addActionLog(String actionText) {
      actionLog.add(actionText);
}
//Sets the Log as a lines of text to replay at the end 
public void addInventory(String inventoryText) {
    if (inventoryText == "Treasure")
        inventory.add(Terrain.TREASURE.getSymbol());

    inventory.add(inventoryText);
}

//Checked by the MovePlayer function to see if Player landed on the Treasure
public boolean isTreasure(int x, int y) {
   if (grid[x][y] == Terrain.TREASURE.getSymbol())
        return true;
   
   return false;
} 

//Called from MovePlayer right after the treasure is added to the Inventory Arraylist
//This function removes the treasure from the map and replaces with LAND
public void removeTreasure() {
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[i].length; j++) {
            if (grid[i][j] == Terrain.TREASURE.getSymbol()) {
                grid[i][j] = Terrain.TREE.getSymbol();  // set the Treasure space back to Land
            }
        }
    }
}

// Method to add entities (humans and goblins) to the game world
public void addEntity(Entity entity) {
   entities[entity.y][entity.x] = entity;
}

// Method to remove entities from the game world
public void removeEntity(Entity entity) {
   entities[entity.y][entity.x] = null;
}

// Method to move an entity to a new position
public void moveEntity(Entity entity, int newX, int newY) {
   removeEntity(entity);
   entity.x = newX;
   entity.y = newY;
   addEntity(entity);
}

// Method to check if a position is valid
public boolean isValidPosition(int x, int y) {
   return x >= 0 && x < grid[0].length && y >= 0 && y < grid.length;
}


// This combat function returns the winner of a human vs. goblin deathmatch
// In the battle loop below, as the goblin and player take turns doing random damage,
// we add the results of each strike to an ArrayList of String (addActionLog()) so
// it can be displayed after the winner is decided 
public Entity combat(Human human, Goblin goblin) {
    // Determine combat outcome using random math
    // This loop is a fight to the finish - until there is only one left standing!
    while (human.isAlive() && goblin.isAlive()) {
        int humanDamage = random.nextInt(human.attack());
        int goblinDamage = random.nextInt(goblin.attack());

        addActionLog("The player strikes the goblin with [" + humanDamage + "] hitpoints of damage.\n");
        addActionLog("The goblin retaliates with [" + goblinDamage + "] hitpoints against the player.\n");

        // Apply damage to entities
        human.takeDamage(goblinDamage);
        goblin.takeDamage(humanDamage);      
    }

    // Check if entities are alive
    if (!human.isAlive()) {
        removeEntity(human);
        addActionLog("The goblin strikes down the Player and celebrates with a fist pump.\n");
        return goblin;
    } else if (!goblin.isAlive()) { // Goblin defeated
        removeEntity(goblin);
        addActionLog("The player deals a crushing blow and wins!  The Goblin is no more.\n");
        // Stretch goal:  Goblin drops gold somewhere in the grid to be picked up in inventory
        
        return human;
    } else {
        // Both entities are still alive, return null
        return null;   // should never reach this null
    }
}

// Clears the console output.
    private void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }



public void display() {
        // inventoryStartingPosition: 
        // This is the anchor row used to display the inventory (to the right of the maze)
        // Divided by 3 to start the position approx 1/3 from the top of the grid
	    int inventoryStartingPosition = Math.round(grid.length/3);

      clearScreen();

        System.out.println("==== MAZE MADNESS ====");

	    for (int i = 0; i < grid.length; i++) {
	        for (int j = 0; j < grid[i].length; j++) {
	            Entity entity = entities[i][j];
	            if (entity != null) {
	                System.out.print(entity.toString() + " ");

	            } else {
	                System.out.print(grid[i][j] + " ");
	            }

                //Inventory Display: Adds the Inventory to the right of the Maze at a certain coordinate
                if(i == inventoryStartingPosition && j == grid[i].length-1)
                    System.out.print("\t Inventory: ");
                if(i == (inventoryStartingPosition + 1) && j == grid[i].length-1)
                    System.out.print("\t   " + getInventory());
                
	        }
	        System.out.println();
	    }
	   
}

//Enum representing different terrain types
enum Terrain {
LAND("'░"),
TREASURE("\uD83D\uDC8E"),
TREE("\uD83C\uDF32"),  //Evergreen

;

private String symbol;

Terrain(String symbol) {
   this.symbol = symbol;
}

public String getSymbol() {
   return symbol;
}
}

}