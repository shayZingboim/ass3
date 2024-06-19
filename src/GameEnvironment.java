import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {
    private List<Collidable> collidables;

    // constructor
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }
    public GameEnvironment(List<Collidable> collidables) {
        this.collidables = collidables;
    }

    // add the given collidable to the environment.
    public void addCollidable(Collidable c) {
        if (c != null) {
            this.collidables.add(c);
        }
    }

    public List<Collidable> getCollidablesList() {
        return collidables;
    }

    // Assume an object moving from line.start() to line.end().
    // If this object will not collide with any of the collidables
    // in this collection, return null. Else, return the information
    // about the closest collision that is going to occur.
    public CollisionInfo getClosestCollision(Line trajectory) {
        List<CollisionInfo> collisionInfos = new ArrayList<CollisionInfo>();
        for (Collidable c : collidables) {
            Point closestIntersection = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (closestIntersection != null) {
                collisionInfos.add(new CollisionInfo(closestIntersection, c));
            }
        }
        if (collisionInfos.isEmpty()) {
            return null;
        }
        CollisionInfo closestCollision = collisionInfos.get(0);
        for (CollisionInfo c : collisionInfos) {
            if (trajectory.start().distance(c.collisionPoint())
                    < trajectory.start().distance(closestCollision.collisionPoint())) {
                closestCollision = c;
            }
        }
        return closestCollision;
    }
}
