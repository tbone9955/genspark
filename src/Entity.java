//Base class for all game entities
abstract class Entity {
protected int x;
protected int y;

public Entity(int x, int y) {
   this.x = x;
   this.y = y;
}

// Override toString to provide a meaningful representation
@Override
public abstract String toString();

}




