//Shay Zingboim 208497255, Yair Kupershtock 322889015

import biuoop.DrawSurface;
import java.awt.Color;


/**
 * A Block class.
 */
public class Block implements Collidable, Sprite {
    private final Rectangle rectangle;
    private final Color boundsColor;
    private Color color;

    /**
     * Constructs a Block object with the specified rectangle and color.
     *
     * @param rectangle The rectangle representing the block.
     * @param color     The color of the block.
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.boundsColor = Color.BLACK;
        this.color = color;
    }

    /**
     * Constructs a Block object with the specified rectangle and color.
     *
     * @param rectangle The rectangle representing the block.
     */
    public Block(Rectangle rectangle) {
        this(rectangle, Color.BLACK);
    }

    /**
     * Constructs a Block object with the specified position, width, height, and color.
     *
     * @param upperLeft The upper-left corner of the block.
     * @param width     The width of the block.
     * @param height    The height of the block.
     */
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
        d.setColor(this.boundsColor);
        d.drawRectangle((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return rectangle;
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        //get the lines of the rectangle
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

    @Override
    public Boolean isPaddle() {
        return false;
    }

    @Override
    public void timePassed() {
        // do nothing
    }

    /**
     * Adds the block to the game.
     *
     * @param game The game to add the block to.
     */
    public void addToGame(Game game) {
        game.addCollidable(this);
        game.addSprite(this);
    }
}
