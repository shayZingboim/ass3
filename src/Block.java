import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.Random;


public class Block implements Collidable {
    private Rectangle rectangle;
    private java.awt.Color color;

    /**
     * Constructs a Block object with the specified rectangle and color.
     *
     * @param rectangle The rectangle representing the block.
     * @param color     The color of the block.
     */
    public Block(Rectangle rectangle, java.awt.Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    /**
     * Returns the rectangle representing the block.
     *
     * @return The rectangle representing the block.
     */
    public Rectangle getCollisionRectangle() {
        return rectangle;
    }

    /**
     * Draws the block on the given surface.
     *
     * @param d The surface to draw the block on.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(color);
        d.fillRectangle((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }

    /**
     * Notifies the block that it was hit at the given collision point with the given velocity.
     *
     * @param collisionPoint  The point of collision.
     * @param currentVelocity The current velocity of the object that hit the block.
     * @return The new velocity expected after the hit.
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        // Calculate the new velocity based on the collision point
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        // If the collision point is on the left or right side of the block
        if (collisionPoint.getX() == rectangle.getUpperLeft().getX()
                || collisionPoint.getX() == rectangle.getUpperLeft().getX() + rectangle.getWidth()) {
            dx = -dx;
        }
        // If the collision point is on the top or bottom side of the block
        if (collisionPoint.getY() == rectangle.getUpperLeft().getY()
                || collisionPoint.getY() == rectangle.getUpperLeft().getY() + rectangle.getHeight()) {
            dy = -dy;
        }
        return new Velocity(dx, dy);
    }
}
