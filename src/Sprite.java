import biuoop.DrawSurface;

/**
 * The Sprite interface represents a sprite object.
 */
public interface Sprite {
    // draw the sprite to the screen

    /**
     * Draw on.
     *
     * @param d the d
     */
    void drawOn(DrawSurface d);

    // notify the sprite that time has passed

    /**
     * Time passed.
     */
    void timePassed();
}