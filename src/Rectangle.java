public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    // Create a new rectangle with location and width/height.
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    // Return a (possibly empty) List of intersection points
    // with the specified line.
    public java.util.List<Point> intersectionPoints(Line line) {
        java.util.List<Point> intersectionPoints = new java.util.ArrayList<Point>();

        // Create the rectangle's lines
        Line upperLine = new Line(upperLeft, new Point(upperLeft.getX() + width, upperLeft.getY()));
        Line lowerLine = new Line(new Point(upperLeft.getX(), upperLeft.getY() + height),
                new Point(upperLeft.getX() + width, upperLeft.getY() + height));
        Line leftLine = new Line(upperLeft, new Point(upperLeft.getX(), upperLeft.getY() + height));
        Line rightLine = new Line(new Point(upperLeft.getX() + width, upperLeft.getY()),
                new Point(upperLeft.getX() + width, upperLeft.getY() + height));

        // Add the intersection points with the rectangle's lines

        // Add the intersection points with the upper line
        if (line.isIntersecting(upperLine)) {
            intersectionPoints.add(line.intersectionWith(upperLine));
        }
        // Add the intersection points with the lower line
        if (line.isIntersecting(lowerLine)) {
            intersectionPoints.add(line.intersectionWith(lowerLine));
        }
        // Add the intersection points with the left line
        if (line.isIntersecting(leftLine)) {
            intersectionPoints.add(line.intersectionWith(leftLine));
        }
        // Add the intersection points with the right line
        if (line.isIntersecting(rightLine)) {
            intersectionPoints.add(line.intersectionWith(rightLine));
        }
        return intersectionPoints;
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
}
