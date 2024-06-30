//Shay Zingboim 208497255, Yair Kupershtock 322889015

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;


/**
 * Represents a paddle in the game.
 */
public class Paddle implements Sprite, Collidable {
    private final biuoop.KeyboardSensor keyboard;
    private final Color color;
    private Block paddleBlock;

    /**
     * Constructs a new Paddle.
     *
     * @param keyboard the keyboard sensor used to control the paddle.
     * @param block    the block representing the paddle.
     */
    public Paddle(biuoop.KeyboardSensor keyboard, Block block) {
        this.keyboard = keyboard;
        this.paddleBlock = block;
        this.color = block.getCollisionRectangle().getColor();
    }

    /**
     * Moves the paddle to the left.
     */
    public void moveLeft() {
        // Move the paddle 5 pixels to the left
        double newX = this.paddleBlock.getCollisionRectangle().getUpperLeft().getX() - 5;
        // If the paddle is at the left edge of the screen, move it to the right edge
        if (newX < 25 - this.paddleBlock.getCollisionRectangle().getWidth()) {
            newX = 800 - this.paddleBlock.getCollisionRectangle().getWidth();
        }
        // Create a new paddle block with the new x coordinate
        this.paddleBlock = new Block(
                new Rectangle(
                        new Point(newX, this.paddleBlock.getCollisionRectangle().getUpperLeft().getY()),
                        this.paddleBlock.getCollisionRectangle().getWidth(),
                        this.paddleBlock.getCollisionRectangle().getHeight()), this.color);
    }

    /**
     * Moves the paddle to the right.
     */
    public void moveRight() {
        // Move the paddle 5 pixels to the right
        double newX = this.paddleBlock.getCollisionRectangle().getUpperLeft().getX() + 5;
        // If the paddle is at the right edge of the screen, move it to the left edge
        if (newX > 800 - this.paddleBlock.getCollisionRectangle().getWidth()) {
            newX = 25;
        }
        // Create a new paddle block with the new x coordinate
        this.paddleBlock = new Block(
                new Rectangle(
                        new Point(newX, this.paddleBlock.getCollisionRectangle().getUpperLeft().getY()),
                        this.paddleBlock.getCollisionRectangle().getWidth(),
                        this.paddleBlock.getCollisionRectangle().getHeight()), this.color);
    }

    @Override
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.paddleBlock.getCollisionRectangle().getUpperLeft().getX(),
                (int) this.paddleBlock.getCollisionRectangle().getUpperLeft().getY(),
                (int) this.paddleBlock.getCollisionRectangle().getWidth(),
                (int) this.paddleBlock.getCollisionRectangle().getHeight());
        d.setColor(this.paddleBlock.getCollisionRectangle().getColor());
        d.drawRectangle((int) this.paddleBlock.getCollisionRectangle().getUpperLeft().getX(),
                (int) this.paddleBlock.getCollisionRectangle().getUpperLeft().getY(),
                (int) this.paddleBlock.getCollisionRectangle().getWidth(),
                (int) this.paddleBlock.getCollisionRectangle().getHeight());
    }

    // Collidable

    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddleBlock.getCollisionRectangle();

    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        // Calculate the new velocity based on the collision point
        int areaIndex = findArea(this.paddleBlock.getCollisionRectangle().getUpperLeft().getX(),
                this.paddleBlock.getCollisionRectangle().getWidth(), collisionPoint.getX());
        double currentSpeed = currentVelocity.getSpeed();
        // Change the velocity based on the area of the paddle that the collision occurred in
        switch (areaIndex) {
            case 1:
                currentVelocity = Velocity.fromAngleAndSpeed(300, currentSpeed);
                break;
            case 2:
                currentVelocity = Velocity.fromAngleAndSpeed(330, currentSpeed);
                break;
            case 3:
                currentVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
                break;
            case 4:
                currentVelocity = Velocity.fromAngleAndSpeed(30, currentSpeed);
                break;
            case 5:
                currentVelocity = Velocity.fromAngleAndSpeed(60, currentSpeed);
                break;
            default:
                break;
        }
        return currentVelocity;
    }

    /**
     * Finds the area of the paddle that the collision occurred in.
     *
     * @param x          the x coordinate of the paddle.
     * @param width      the width of the paddle.
     * @param collisionX the x coordinate of the collision.
     * @return the area of the paddle that the collision occurred in.
     */
    public int findArea(double x, double width, double collisionX) {
        // Divide the paddle into 5 regions
        double regionWidth = width / 5;
        // Check which region the collision occurred in
        for (int i = 0; i < 5; i++) {
            double regionStart = x + i * regionWidth;
            double regionEnd = x + (i + 1) * regionWidth;
            if (collisionX >= regionStart && collisionX <= regionEnd) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * Adds the paddle to the game.
     *
     * @param g the game to add the paddle to.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    @Override
    public Boolean isPaddle() {
        return true;
    }
}