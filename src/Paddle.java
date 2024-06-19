import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Block paddleBlock;

    public Paddle(biuoop.KeyboardSensor keyboard, Block block) {
        this.keyboard = keyboard;
        this.paddleBlock = block;
    }

    public void moveLeft() {
    }

    public void moveRight() {
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
    }

    // Add this paddle to the game.
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}