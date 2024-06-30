//Shay Zingboim 208497255, Yair Kupershtock 322889015

/**
 * The Collidable interface represents an object that can be collided with.
 */
public interface Collidable {

    /**
     * Returns the "collision shape" of the object.
     *
     * @return The "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     *
     * @param collisionPoint  The point of collision.
     * @param currentVelocity The velocity of the object that collided with the collidable object.
     * @return The new velocity of the object that collided with the collidable object.
     */
    Velocity hit(Point collisionPoint, Velocity currentVelocity);

    /**
     * Returns if the object is a paddle object.
     *
     * @return true if the object is a paddle object, false otherwise.
     */
    Boolean isPaddle();
}
