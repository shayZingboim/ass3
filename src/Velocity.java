//Shay Zingboim 208497255, Yair Kupershtock 322889015

/**
 * Represents the velocity of an object, defined by its change in position (dx, dy).
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructs a Velocity object with the specified changes in position.
     *
     * @param dx The change in the x-coordinate.
     * @param dy The change in the y-coordinate.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Constructs a Velocity object from an angle and speed.
     *
     * @param angle The angle of the velocity.
     * @param speed The speed of the velocity.
     * @return A new Velocity object.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radian = Math.toRadians(angle) - (Math.PI / 2);
        double dx = Math.cos(radian) * speed;
        double dy = Math.sin(radian) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Applies this velocity to a given point, resulting in a new point.
     *
     * @param p The original point.
     * @return A new point with updated coordinates.
     */
    public Point applyToPoint(Point p) {
        // Return a new point with position (x + dx, y + dy)
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    /**
     * Gets the speed of the velocity.
     *
     * @return the speed of the velocity.
     */
    public double getSpeed() {
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)); // Calculating speed using Pitagoras formula
    }

    /**
     * Returns the change in the x-coordinate.
     *
     * @return The change in the x-coordinate.
     */
    public double getDx() {
        return dx;
    }

    /**
     * Returns the change in the y-coordinate.
     *
     * @return The change in the y-coordinate.
     */
    public double getDy() {
        return dy;
    }

    /**
     * Sets the change in the x-coordinate.
     *
     * @param dx The new change in the x-coordinate.
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Sets the change in the y-coordinate.
     *
     * @param dy The new change in the y-coordinate.
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Maps a speed to a radius.
     *
     * @param radius The radius.
     * @param min The minimum radius.
     * @param max The maximum radius.
     * @param speedMin The minimum speed.
     * @param speedMax The maximum speed.
     * @return The mapped speed.
     */
    public static double mapSpeed(double radius, double min, double max, double speedMin, double speedMax) {
        if (radius < min) {
            return speedMax;
        }
        if (radius > max) {
            return speedMin;
        }
        return speedMax + (radius - min) * (speedMin - speedMax) / (max - min);
    }
}
