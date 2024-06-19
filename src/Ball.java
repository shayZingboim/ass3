import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.Random;

public class Ball {
    private Point center;
    private double radius;
    private java.awt.Color color;
    private Velocity velocity;
    private  final GameEnvironment gameEnvironment;
    private static final double THRESHOLD = 0.0001;

    // constructors
    public Ball(Point center, double r, java.awt.Color color, Velocity velocity, GameEnvironment gameEnvironment) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.velocity = velocity;
        this.gameEnvironment = gameEnvironment;
    }
    public Ball(Point point, double r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this(point, r, color, new Velocity(0, 0), gameEnvironment);
    }

    // accessors
    public double getX() {
        return this.center.getX();
    }

    public double getY() {
        return this.center.getY();
    }

    public double getSize() {
        return this.radius;
    }

    public java.awt.Color getColor() {
        return this.color;
    }

    public Point getCenter() {
        return this.center;
    }

    public Velocity getVelocity() {
        return this.velocity;
    }

    //get the next point of the ball with the radius
    public Point getNextPoint() {
        double sigDx = Math.signum(this.velocity.getDx());
        double sigDy = Math.signum(this.velocity.getDy());
        return new Point(this.center.getX() + this.velocity.getDx() + sigDx * this.radius,
                this.center.getY() + this.velocity.getDy() + sigDy * this.radius);
    }


    // draw the ball on the given DrawSurface
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.getX(), (int) this.getY(), (int) this.getSize());
    }

    // set the velocity of the ball
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    // set the velocity of the ball
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    // move the ball one step
    //1) compute the ball trajectory (the trajectory is "how the ball will move
    // without any obstacles" -- its a line starting at current location, and
    //ending where the velocity will take the ball if no collisions will occur).
    //2) Check (using the game environment) if moving on this trajectory will
    //hit anything.
    //2.1) If no, then move the ball to the end of the trajectory
    //2.2) Otherwise (there is a hit):
    //2.2.2) move the ball to "almost" the hit point, but just slightly before
    //it.
    //2.2.3) notify the hit object (using its hit() method) that a collision
    //occurred.
    //2.2.4) update the velocity to the new velocity returned by the hit()
    //method.
    public void moveOneStep() {
        Point newCenter = this.getVelocity().applyToPoint(this.center);
        Point nextLocation = this.getNextPoint();
        // specify line as the movement of the ball from the center
        Line ballMove = new Line(this.center.getX(), this.center.getY(), nextLocation.getX(), nextLocation.getY());
        CollisionInfo collisionInfo = this.gameEnvironment.getClosestCollision(ballMove);
        if (collisionInfo != null) {
            Point collisionPoint = collisionInfo.collisionPoint();
            // if from the right
            if (collisionPoint.getX() >= this.center.getX()) {
                newCenter.setX(collisionPoint.getX() - THRESHOLD);
                // from the left
            } else if (this.center.getX() >= collisionPoint.getX()) {
                newCenter.setX(collisionPoint.getX() + THRESHOLD);
            }
            // from above
            if (collisionPoint.getY() <= this.center.getY()) {
                newCenter.setY(collisionPoint.getY() + THRESHOLD);
            } else if (this.center.getY() <= collisionPoint.getY()) {
                newCenter.setY(collisionPoint.getY() - THRESHOLD);
            }
            this.setVelocity(collisionInfo.collisionObject().hit(collisionPoint, this.getVelocity()));
        }
        this.center = newCenter;
    }
}
