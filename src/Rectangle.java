import java.awt.*;
import java.util.ArrayList;

public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;
    private Color color;
    private Line[] lines;

    // constructors
    public Rectangle(Point upperLeft, double width, double height, Color color) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.color = color;
        recToLines();
    }

    public Rectangle(Point upperLeft, double width, double height) {
        this(upperLeft, width, height, Color.BLACK);
    }

    public Rectangle(double x, double y, double width, double height) {
        this(new Point(x, y), width, height);
    }


    // Return a (possibly empty) List of intersection points
    // with the specified line.
    public java.util.List<Point> intersectionPoints(Line line) {
        ArrayList<Point> intersectionPoints = new ArrayList<Point>();
        for (int i = 0; i < lines.length; i++) {
            if (line.intersectionWith(lines[i]) != null) {
                intersectionPoints.add(line.intersectionWith(lines[i]));
            }
        }
        return intersectionPoints;
    }

    public void recToLines() {
        this.lines = new Line[4];
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


    // Return the width and height of the rectangle
    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    // Returns the upper-left point of the rectangle.
    public Point getUpperLeft() {
        return upperLeft;
    }

    // Returns the color of the rectangle.
    public Color getColor() {
        return color;
    }

    public Line[] getLinesArr() {
        return this.lines;
    }
}
