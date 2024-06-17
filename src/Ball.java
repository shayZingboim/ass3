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
    private GameEnvironment gameEnvironment;

    // constructors
    public Ball(Point center, double r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
    }

    public Ball(double x, double y, double r, java.awt.Color color) {
        this(new Point(x, y), r, color);
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

    // mutators
    public void setGameEnvironment(GameEnvironment ge) {
        this.gameEnvironment = ge;
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
    public void moveOneStep() {
        Line trajectory = new Line(this.center, this.velocity.applyToPoint(this.center));
        CollisionInfo collisionInfo = this.gameEnvironment.getClosestCollision(trajectory);
        if (collisionInfo == null) {
            this.center = this.velocity.applyToPoint(this.center);
        } else {
            this.center = new Point(collisionInfo.collisionPoint().getX() - this.velocity.getDx(),
                    collisionInfo.collisionPoint().getY() - this.velocity.getDy());
            this.velocity = collisionInfo.collisionObject().hit(collisionInfo.collisionPoint(), this.velocity);
        }
    }
}
