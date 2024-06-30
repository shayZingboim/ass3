//Shay Zingboim 208497255, Yair Kupershtock 322889015

/**
 * Represents a line segment defined by two points.
 */
public class Line {
    private Point start; // Starting point of the line
    private Point end; // Ending point of the line
    private Point inPoint = null; // Intersection point with another line, if any
    public static final double EPSILON = 0.00000001; // Small constant to handle floating-point comparison

    /**
     * Constructs a line given two points.
     *
     * @param start The starting point of the line.
     * @param end   The ending point of the line.
     */
    public Line(Point start, Point end) {
        if (start.getX() == end.getX()) {
            if (start.getY() < end.getY()) {
                this.start = start;
                this.end = end;
            } else {
                this.start = end;
                this.end = start;
            }
        } else if (start.getX() < end.getX()) {
            this.start = start;
            this.end = end;
        } else {
            this.start = end;
            this.end = start;
        }
    }

    /**
     * Constructs a line given the coordinates of two points.
     *
     * @param x1 The x-coordinate of the starting point.
     * @param y1 The y-coordinate of the starting point.
     * @param x2 The x-coordinate of the ending point.
     * @param y2 The y-coordinate of the ending point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Returns the length of the line.
     *
     * @return The length of the line.
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return The middle point of the line.
     */
    public Point middle() {
        return new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    /**
     * Returns the starting point of the line.
     *
     * @return The starting point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the ending point of the line.
     *
     * @return The ending point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Returns the intersection point of this line with another line, if any.
     *
     * @return The intersection point, or null if there is no intersection.
     */
    public Point getInPoint() {
        return this.inPoint;
    }

    /**
     * Calculates the slope of a given line.
     * The slope is calculated as the change in y divided by the change in x. If the line is vertical
     * (i.e., the difference in x-coordinates is negligible), the slope is considered to be positive infinity.
     *
     * @param line The line for which the slope is to be calculated.
     * @return The slope of the line. If the line is vertical, returns Double.POSITIVE_INFINITY.
     */
    public double getSlope(Line line) {
        double slope;
        // Check if the line is vertical by comparing the x-coordinates of the start and end points
        if (Math.abs(line.start.getX() - line.end.getX()) <= EPSILON) {
            // If the x-coordinates are effectively the same, the line is vertical
            slope = Double.POSITIVE_INFINITY;
        } else {
            // Calculate the slope using the difference in y-coordinates divided by the difference in x-coordinates
            slope = (line.end.getY() - line.start.getY()) / (line.end.getX() - line.start.getX());
        }
        return slope;
    }

    /**
     * Calculates the y-intercept (b) of a given line using its slope.
     * The y-intercept is calculated using the formula b = y - mx,
     * where y and x are the coordinates of a point on the line
     * and m is the slope of the line.
     *
     * @param line  The line for which the y-intercept is to be calculated.
     * @param slope The slope of the line.
     * @return The y-intercept of the line.
     */
    public double getB(Line line, double slope) {
        // Calculate the y-intercept using the formula b = y - mx
        double b = (line.start.getY() - (slope * line.start.getX()));
        return b;
    }

    /**
     * Determines if this line intersects with another line.
     *
     * @param other The other line to check for intersection.
     * @return True if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        double slope1 = getSlope(this);
        double slope2 = getSlope(other);
        double b1 = getB(this, slope1);
        double b2 = getB(other, slope2);
        double xIntersection = '\0';
        double yIntersection = '\0';

        //Case 1: where the two lines are points.
        if (this.start.equals(this.end) && other.start.equals(other.end)) {
            if (this.start.equals(other.start)) {
                this.inPoint = this.start;
                return true;
            }
            return false;
        }
        //Case 2: this line is a point.
        if (this.start.equals(this.end)) {
            if (slope2 == Double.POSITIVE_INFINITY) {
                if (Math.abs(this.start.getX() - other.start.getX()) > EPSILON
                        || this.start.getY() - EPSILON > other.end.getY()
                        || this.start.getY() + EPSILON < other.start.getY()) {
                    return false;
                }
                this.inPoint = this.start;
                return true;
            }
            if (Math.abs(this.start.getY() - (slope2 * this.start.getX() + b2)) < EPSILON) {
                if (this.end.getX() + EPSILON < other.start.getX() || this.start.getX() - EPSILON > other.end.getX()) {
                    return false;
                }
                this.inPoint = this.start;
                return true;
            }
            return false;
        }
        //Case 3: other line is a point.
        if (other.start.equals(other.end)) {
            if (slope1 == Double.POSITIVE_INFINITY) {
                if (Math.abs(other.start.getX() - this.start.getX()) > EPSILON
                        || other.start.getY() - EPSILON > this.end.getY()
                        || other.start.getY() + EPSILON < this.start.getY()) {
                    return false;
                }
                this.inPoint = other.start;
                return true;
            }
            if (Math.abs(other.start.getY() - (slope1 * other.start.getX() + b1)) < EPSILON) {
                if (other.end.getX() + EPSILON < this.start.getX() || other.start.getX() - EPSILON > this.end.getX()) {
                    return false;
                }
                this.inPoint = other.start;
                return true;
            }
            return false;
        }
        //Case 4: both lines are vertical.
        if (slope1 == Double.POSITIVE_INFINITY && slope2 == Double.POSITIVE_INFINITY) {
            if (Math.abs(this.start.getX() - other.end.getX()) > EPSILON) {
                return false;
            }
            if (this.end.getY() + EPSILON < other.start.getY() || this.start.getY() - EPSILON > other.end.getY()) {
                return false;
            }
            if (Math.abs(this.start.getY() - other.end.getY()) < EPSILON) {
                this.inPoint = this.start;
            }
            if (Math.abs(this.end.getY() - other.start.getY()) < EPSILON) {
                this.inPoint = this.end;
            }
            return true;
        }
        //Case 5: this line is vertical.
        if (slope1 == Double.POSITIVE_INFINITY) {
            if (this.start.getX() + EPSILON < other.start.getX() || this.start.getX() - EPSILON > other.end.getX()) {
                return false;
            }
            yIntersection = slope2 * this.start.getX() + b2;
            if (yIntersection - EPSILON > this.end.getY() || yIntersection + EPSILON < this.start.getY()) {
                return false;
            }
            this.inPoint = new Point(this.start.getX(), yIntersection);
            return true;
        }
        //Case 6: other line is vertical.
        if (slope2 == Double.POSITIVE_INFINITY) {
            if (other.start.getX() - EPSILON > this.end.getX() || other.start.getX() + EPSILON < this.start.getX()) {
                return false;
            }
            yIntersection = slope1 * other.start.getX() + b1;
            if (yIntersection - EPSILON > other.end.getY() || yIntersection + EPSILON < other.start.getY()) {
                return false;
            }
            this.inPoint = new Point(other.start.getX(), yIntersection);
            return true;
        }
        //Case 7: the lines are parallel to each other.
        if (Math.abs(slope1 - slope2) < EPSILON) {
            if (Math.abs(b1 - b2) > EPSILON) {
                return false;
            }
            if (this.start.getX() - EPSILON > other.end.getX() || this.end.getX() + EPSILON < other.start.getX()) {
                return false;
            }
            if (Math.abs(this.start.getX() - other.end.getX()) < EPSILON) {
                this.inPoint = this.start;
                return true;
            }
            if (Math.abs(this.end.getX() - other.start.getX()) < EPSILON) {
                this.inPoint = this.end;
                return true;
            }
            return true;
        }
        //Case 8: common case.
        xIntersection = (b2 - b1) / (slope1 - slope2);
        yIntersection = slope1 * xIntersection + b1;
        if (xIntersection + EPSILON < this.start.getX() || xIntersection - EPSILON > this.end.getX()
                || yIntersection - EPSILON > Math.max(this.start.getY(), this.end.getY())
                || yIntersection + EPSILON < Math.min(this.start.getY(), this.end.getY())
                || xIntersection + EPSILON < other.start.getX() || xIntersection - EPSILON > other.end.getX()
                || yIntersection - EPSILON > Math.max(other.start.getY(), other.end.getY())
                || yIntersection + EPSILON < Math.min(other.start.getY(), other.end.getY())) {
            return false;
        }
        this.inPoint = new

                Point(xIntersection, yIntersection);
        return true;
    }

    /**
     * Determines if this line intersects with both given lines.
     *
     * @param other1 The first other line to check for intersection.
     * @param other2 The second other line to check for intersection.
     * @return True if this line intersects with both given lines, false otherwise.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return this.isIntersecting(other1) && this.isIntersecting(other2);
    }

    /**
     * Returns the intersection point if this line intersects with another line.
     *
     * @param other The other line to check for intersection.
     * @return The intersection point if the lines intersect, null otherwise.
     */
    public Point intersectionWith(Line other) {
        if (this.isIntersecting(other)) {
            if (this.getInPoint() != null) {
                Point intersection = new Point(this.getInPoint().getX(), this.getInPoint().getY());
                this.inPoint = null; // Clear the intersection point for subsequent calls
                return intersection;
            }
        }
        return null;
    }

    /**
     * Compares this line with another line for equality.
     *
     * @param other The other line to compare with.
     * @return True if the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        return ((this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end) && this.end.equals(other.start)));
    }

    /**
     * Returns the closest intersection point to the start of the line with a given rectangle.
     *
     * @param rect The rectangle to check for intersection.
     * @return The closest intersection point to the start of the line, or null if there is no intersection.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // Get the lines of the rectangle
        Line[] lines = rect.getLinesArr();
        Point closestIntersection = null;
        // start is based on lower x
        double minDistance = Double.MAX_VALUE;
        // Find the closest intersection point to the start of the line
        for (Line line : lines) {
            Point intersection = this.intersectionWith(line);
            if (intersection != null) {
                double distance = this.start.distance(intersection);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestIntersection = intersection;
                }
            }
        }
        return closestIntersection;
    }

    /**
     * Checks if the x-coordinate of a point is within the segment of the line.
     *
     * @param pt The point to check.
     * @return true if the x-coordinate is within the segment, false otherwise.
     */
    public boolean isPointXInSegment(Point pt) {
        double maxX = Math.max(this.start.getX(), this.end.getX());
        double minX = Math.min(this.start.getX(), this.end.getX());
        return (pt.getX() > minX || Math.abs(pt.getX() - minX) < EPSILON)
                && (pt.getX() < maxX || Math.abs(pt.getX() - maxX) < EPSILON);
    }

    /**
     * Checks if the y-coordinate of a point is within the segment of the line.
     *
     * @param pt The point to check.
     * @return true if the y-coordinate is within the segment, false otherwise.
     */
    public boolean isPointYInSegment(Point pt) {
        double maxY = Math.max(this.start.getY(), this.end.getY());
        double minY = Math.min(this.start.getY(), this.end.getY());
        return (pt.getY() > minY || Math.abs(pt.getY() - minY) < EPSILON)
                && (pt.getY() < maxY || Math.abs(pt.getY() - maxY) < EPSILON);
    }

    /**
     * Checks if a point lies on the line segment.
     *
     * @param pt The point to check.
     * @return true if the point lies on the line segment, false otherwise.
     */
    public boolean isPointInRange(Point pt) {
        return this.isPointXInSegment(pt) && this.isPointYInSegment(pt);
    }

    /**
     * Checks if the line segment is parallel to the y-axis.
     *
     * @return true if the line segment is parallel to the y-axis, false otherwise.
     */
    public boolean isLineParallelToY() {
        return Math.abs(this.end.getX() - this.start.getX()) < EPSILON;
    }

    /**
     * Checks if the line segment is parallel to the x-axis.
     *
     * @return true if the line segment is parallel to the x-axis, false otherwise.
     */
    public boolean isLineParallelToX() {
        return Math.abs(this.end.getY() - this.start.getY()) < EPSILON;
    }

    /**
     * Checks if a point lies on the line.
     *
     * @param pt   The point to check.
     * @param line The line to check against.
     * @return true if the point lies on the line, false otherwise.
     */
    public boolean isPointOnLine(Point pt, Line line) {
        // Check if the line is parallel to the y-axis
        if (line.isLineParallelToY()) {
            return Math.abs(pt.getX() - line.start.getX()) < EPSILON
                    && line.isPointYInSegment(pt);
        }
        // Check if the line is parallel to the x-axis
        if (line.isLineParallelToX()) {
            return Math.abs(pt.getY() - line.start.getY()) < EPSILON
                    && line.isPointXInSegment(pt);
        }
        double slope = getSlope(line);
        double b = getB(line, slope);
        // Check if the point lies on the line
        return Math.abs(pt.getY() - (slope * pt.getX() + b)) < EPSILON
                && line.isPointInRange(pt);
    }

}
