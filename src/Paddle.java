import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Represents a paddle in the game.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
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
    }

    /**
     * Moves the paddle to the left.
     */
    public void moveLeft() {
        double newX = this.paddleBlock.getCollisionRectangle().getUpperLeft().getX() - 5;
        if (newX < 25 - this.paddleBlock.getCollisionRectangle().getWidth()) {
            newX = 800 - this.paddleBlock.getCollisionRectangle().getWidth();
        }
        this.paddleBlock = new Block(new Point(newX, this.paddleBlock.getCollisionRectangle().getUpperLeft().getY()),
                this.paddleBlock.getCollisionRectangle().getWidth(),
                this.paddleBlock.getCollisionRectangle().getHeight());
    }

    /**
     * Moves the paddle to the right.
     */
    public void moveRight() {
        double newX = this.paddleBlock.getCollisionRectangle().getUpperLeft().getX() + 5;
        if (newX > 800 - this.paddleBlock.getCollisionRectangle().getWidth()) {
            newX = 25;
        }
        this.paddleBlock = new Block(new Point(newX, this.paddleBlock.getCollisionRectangle().getUpperLeft().getY()),
                this.paddleBlock.getCollisionRectangle().getWidth(),
                this.paddleBlock.getCollisionRectangle().getHeight());
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
        d.setColor(this.paddleBlock.getCollisionRectangle().getColor());
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
        int areaIndex = findArea(this.paddleBlock.getCollisionRectangle().getUpperLeft().getX(),
                this.paddleBlock.getCollisionRectangle().getWidth(), collisionPoint.getX());
        double currentSpeed = currentVelocity.getSpeed();
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

    //return the part of the paddle that the ball hit
    public int findArea(double x, double width, double collisionX) {
        double regionWidth = width / 5;
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