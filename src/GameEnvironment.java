//Shay Zingboim 208497255, Yair Kupershtock 322889015

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game environment.
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * Constructs a new game environment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    /**
     * Constructs a new game environment with the specified collidables.
     *
     * @param collidables the collidables to add to the environment
     */
    public GameEnvironment(List<Collidable> collidables) {
        this.collidables = collidables;
    }

    /**
     * Adds a collidable to the environment.
     *
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        if (c != null) {
            this.collidables.add(c);
        }
    }

    /**
     * Gets the list of collidables.
     *
     * @return the list of collidables
     */
    public List<Collidable> getCollidablesList() {
        return collidables;
    }

    /**
     * Gets the closest collision to the trajectory of the object.
     *
     * @param trajectory the trajectory of the object
     * @return the closest collision to the trajectory of the object
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // Create a list of collision infos
        List<CollisionInfo> collisionInfos = new ArrayList<CollisionInfo>();
        // Check for collision with each collidable
        for (Collidable c : collidables) {
            Point closestIntersection = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (closestIntersection != null) {
                collisionInfos.add(new CollisionInfo(closestIntersection, c));
            }
        }
        // If there are no collisions, return null
        if (collisionInfos.isEmpty()) {
            return null;
        }
        // Find the closest collision
        CollisionInfo closestCollision = collisionInfos.get(0);
        // Check for the closest collision
        for (CollisionInfo c : collisionInfos) {
            if (trajectory.start().distance(c.collisionPoint())
                    < trajectory.start().distance(closestCollision.collisionPoint())) {
                closestCollision = c;
            }
        }
        return closestCollision;
    }
}
