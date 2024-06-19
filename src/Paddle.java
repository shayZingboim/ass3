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
        return null;
    }

    // Add this paddle to the game.
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}