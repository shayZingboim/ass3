//Shay Zingboim 208497255, Yair Kupershtock 322889015

/**
 * CollisionInfo class.
 * This class holds the information about the collision point and the collidable object.
 */
public class CollisionInfo {
    private final Point collisionPoint;
    private final Collidable collisionObject;

    /**
     * Constructor.
     *
     * @param collisionPoint  the point at which the collision occurs.
     * @param collisionObject the collidable object involved in the collision.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
