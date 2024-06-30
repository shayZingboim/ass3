//Shay Zingboim 208497255, Yair Kupershtock 322889015

import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;

/**
 * A class representing a rectangle.
 */
public class Rectangle {
    private Point upperLeft;
    private final double width;
    private final double height;
    private final Color color;
    private Line[] lines;

    // constructors

    /**
     * Constructor.
     *
     * @param upperLeft the upper left point of the rectangle
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     * @param color     the color of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height, Color color) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.color = color;
        recToLines();
    }

    /**
     * Constructor.
     *
     * @param upperLeft the upper left point of the rectangle
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this(upperLeft, width, height, Color.BLACK);
    }

    /**
     * Constructor.
     *
     * @param x      the x value of the upper left point of the rectangle
     * @param y      the y value of the upper left point of the rectangle
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(double x, double y, double width, double height) {
        this(new Point(x, y), width, height);
    }


    /**
     * find the intersection points of the rectangle with a line.
     * @param line the line to check intersection with
     * @return a list of the intersection points
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        // create a list of the intersection points
        ArrayList<Point> intersectionPoints = new ArrayList<Point>();
        // check intersection with each line of the rectangle
        for (Line value : lines) {
            // if there is an intersection, add it to the list
            if (line.intersectionWith(value) != null) {
                intersectionPoints.add(line.intersectionWith(value));
            }
        }
        return intersectionPoints;
    }

    /**
     * Convert the rectangle to lines array.
     */
    public void recToLines() {
        // create an array of the lines of the rectangle
        this.lines = new Line[4];
        // create the lines
        lines[0] = new Line(
                this.getUpperLeft().getX(), this.getUpperLeft().getY(),
                this.getUpperLeft().getX(), this.getUpperLeft().getY() + this.getHeight()
        );
        lines[1] = new Line(
                this.getUpperLeft().getX(), this.getUpperLeft().getY() + this.getHeight(),
                this.getUpperLeft().getX() + this.getWidth(), this.getUpperLeft().getY() + this.getHeight()
        );
        lines[2] = new Line(
                this.getUpperLeft().getX() + this.getWidth(), this.getUpperLeft().getY() + this.getHeight(),
                this.getUpperLeft().getX() + this.getWidth(), this.getUpperLeft().getY()
        );
        lines[3] = new Line(
                this.getUpperLeft().getX(), this.getUpperLeft().getY(),
                this.getUpperLeft().getX() + this.getWidth(), this.getUpperLeft().getY()
        );
    }

    /**
     * Draw the rectangle on a given surface.
     *
     * @param d the surface to draw the rectangle on
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.getColor());
        d.fillRectangle((int) this.getUpperLeft().getX(), (int) this.getUpperLeft().getY(),
                (int) this.getWidth(), (int) this.getHeight());
    }


    /**
     * Return the width of the rectangle.
     *
     * @return the width of the rectangle
     */
    public double getWidth() {
        return width;
    }

    /**
     * Return the height of the rectangle.
     *
     * @return the height of the rectangle
     */
    public double getHeight() {
        return height;
    }

    /**
     * Return the upper left point of the rectangle.
     *
     * @return the upper left point of the rectangle
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * Return the color of the rectangle.
     *
     * @return the color of the rectangle
     */
    public Color getColor() {
        return color;
    }

    /**
     * Return the lines of the rectangle.
     *
     * @return the lines of the rectangle
     */
    public Line[] getLinesArr() {
        return this.lines;
    }

    /**
     * Set the upper left point of the rectangle.
     *
     * @param point the new upper left point of the rectangle
     */
    public void setUpperLeft(Point point) {
        this.upperLeft = point;
    }
}
