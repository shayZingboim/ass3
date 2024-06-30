import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.Random;

/**
 * the ball class.
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private final GameEnvironment gameEnvironment;
    private static final double THRESHOLD = 0.0001;

    // constructors

    /**
     * Constructs a ball with a given center point, radius, and color.
     *
     * @param center          The center point of the ball.
     * @param r               The radius of the ball.
     * @param color           The color of the ball.
     * @param velocity        The velocity of the ball.
     * @param gameEnvironment The game environment the ball interacts with.
     */
    public Ball(Point center, int r, java.awt.Color color, Velocity velocity, GameEnvironment gameEnvironment) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.velocity = velocity;
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * Constructs a ball with given coordinates, radius, color, and game environment.
     *
     * @param x               The x-coordinate of the center of the ball.
     * @param y               The y-coordinate of the center of the ball.
     * @param r               The radius of the ball.
     * @param color           The color of the ball.
     * @param gameEnvironment The game environment the ball interacts with.
     */
    public Ball(int x, int y, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this(new Point(x, y), r, color, gameEnvironment);
    }

    /**
     * Constructs a ball with given coordinates, radius, color, and game environment.
     *
     * @param point           The point of the center of the ball.
     * @param r               The radius of the ball.
     * @param color           The color of the ball.
     * @param gameEnvironment The game environment the ball interacts with.
     */
    public Ball(Point point, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this(point, r, color, new Velocity(0, 0), gameEnvironment);
    }

    // accessors

    /**
     * Gets the x-coordinate of the center of the ball.
     *
     * @return The x-coordinate of the center of the ball.
     */
    public double getX() {
        if (this.center == null) {
            return -1;
        }
        return this.center.getX();
    }

    /**
     * Gets the y-coordinate of the center of the ball.
     *
     * @return The y-coordinate of the center of the ball.
     */
    public double getY() {
        if (this.center == null) {
            return -1;
        }
        return this.center.getY();
    }

    /**
     * Gets the radius of the ball.
     *
     * @return The radius of the ball.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * Gets the color of the ball.
     *
     * @return The color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Gets the game environment the ball interacts with.
     *
     * @return The game environment the ball interacts with.
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * Gets the game environment the ball interacts with.
     *
     * @return The game environment the ball interacts with.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Gets the game environment the ball interacts with.
     *
     * @return The game environment the ball interacts with.
     */
    public GameEnvironment getGameEnvironment() {
        return this.gameEnvironment;
    }

    // set the velocity of the ball

    /**
     * Sets the velocity of the ball.
     *
     * @param dx The change in the x-coordinate.
     * @param dy The change in the y-coordinate.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v The new velocity of the ball.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }


    /**
     * draw the ball on the given DrawSurface.
     *
     * @param surface the DrawSurface to draw the ball on.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
    }

    /**
     * move the ball one step.
     */
    public void moveOneStep() {
        //the trajectory of the ball
        Point nextPoint = this.velocity.applyToPoint(this.center);
        Line trajectory = new Line(this.center, nextPoint);
        CollisionInfo collisionInfo = this.gameEnvironment.getClosestCollision(trajectory);
        if (collisionInfo != null) {
            Point collisionPoint = collisionInfo.collisionPoint();
            //move the ball to "almost" the hit point
            double sigXy = Math.signum(this.velocity.getDx());
            double sigYy = Math.signum(this.velocity.getDy());
            this.center.setX(collisionPoint.getX() - this.radius * sigXy);
            this.center.setY(collisionPoint.getY() - this.radius * sigYy);
            //update the velocity to the new velocity returned by the hit() method
            this.velocity = collisionInfo.collisionObject().hit(collisionPoint, this.velocity);
            //check if the ball enters the paddle
            if (collisionInfo.collisionObject().isPaddle()) {
                //if the ball enters the paddle, we need to move it to the edge of the paddle
                //to avoid the ball from getting stuck in the paddle
                this.center.setX(this.center.getX());
                this.center.setY(this.gameEnvironment.getCollidablesList().get(0).getCollisionRectangle().getHeight()
                        - collisionInfo.collisionObject().getCollisionRectangle().getHeight() - this.radius - 35);
            }
        } else {
            //if there is no collision, move the ball to the next point
            this.center = this.velocity.applyToPoint(this.center);
        }
    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Add the ball to the game.
     *
     * @param g The game to add the ball to.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        }
}
