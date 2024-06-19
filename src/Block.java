import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.Random;


public class Block implements Collidable {
    private Rectangle rectangle;
    private Color color;

    /**
     * Constructs a Block object with the specified rectangle and color.
     *
     * @param rectangle The rectangle representing the block.
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    public Block(Rectangle rectangle) {
        this(rectangle, Color.BLACK);
    }

    public Block(Point upperLeft, double width, double height) {
        this(new Rectangle(upperLeft, width, height));
    }


    /**
     * Draws the block on the given surface.
     *
     * @param d The surface to draw the block on.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.rectangle.getColor());
        d.fillRectangle((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return rectangle;
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        Line[] lines = rectangle.getLinesArr();
        //lines parallel to Y
        if (lines[0].isPointOnLine(collisionPoint, lines[0]) || lines[2].isPointOnLine(collisionPoint, lines[2])) {
            currentVelocity.setDx(-currentVelocity.getDx());
        }
        //lines parallel to X
        if (lines[1].isPointOnLine(collisionPoint, lines[1]) || lines[3].isPointOnLine(collisionPoint, lines[3])) {
            currentVelocity.setDy(-currentVelocity.getDy());
        }
        return currentVelocity;
    }
}
