import static java.lang.Math.sqrt;
import static java.lang.Math.atan2;
import static java.lang.Math.acos;


/**
 * Bidimensional vector: (x, y) plus Vector2 math
 */
public class Vector2 implements Comparable<Vector2>, Cloneable {

    // ATTRIBUTES
    private float x = 0f;
    private float y = 0f;


    // CONSTRUCTORS
    public Vector2() {}

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(final Vector2 other) {
    	this(other.x(), other.y());
    }


    // METHODS
    public float x() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float y() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setXY(float x, float y) {
        setX(x);
        setY(y);
    }

    /**
     * Reset vector to (0, 0)
     */
    public void reset() {
        setXY(0f, 0f);
    }

    /**
     * Add values (x, y) to original vector
     */
    public void offset(float x, float y) {
    	this.x += x;
    	this.y += y;
    }

    /**
     * Add some Vector2 to original vector
     */
    public void offset(final Vector2 vector) {
    	offset(vector.x(), vector.y());
    }

    /**
     * Multiplies vector by a constant
     */
    public void scale(double scalar) {
        x *= scalar;
        y *= scalar;
    }

    /**
     * @return Vector2's length
     */
    public double mag() {
        return sqrt(x*x + y*y);
    }

    /**
     * Makes Vector unitary
     */
    public void normalize() {
        scale( 1/mag() );
    }

    /**
     * @return Vector2's angle to origin; 
     * always in range [-pi , pi]
     */
    public double angle() {
    	return atan2(y, x);
    }

    /**
     * Returns vector as a string, "(x, y)" format
     */
    @Override
    public String toString() {
        return String.format(java.util.Locale.US, "(%.2f, %.2f)", x, y);
    }

    /**
     * Checks if both Vector components are the same
     */
    public boolean equals(final Vector2 other) {
    	return x == other.x() && y == other.y();
    }

    /**
     * Compare by vertical component; break tie with horizontal component
     */
    public int compareTo(final Vector2 that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return +1;
        return 0;
    }

    @Override
    protected final Object clone() throws CloneNotSupportedException {
        return this;
    }


    // CLASS FUNCTIONS
    /**
     * Sums two given Vector2s A and B
     * @return Result Vector
     */
    public static Vector2 add(final Vector2 A, final Vector2 B) {
        return new Vector2(A.x() + B.x(), A.y() + B.y());
    }

    /**
     * Multiplies vector by a constant 
     * @return Result Vector
     */
    public static Vector2 mult(final Vector2 vector, float scalar) {
        return new Vector2(vector.x() * scalar, vector.y() * scalar);
    }
    
    /**
     * Subtracts two given Vector2s
     * @return Vector going from A to B
     */
    public static Vector2 sub(final Vector2 A, final Vector2 B) {
        return Vector2.add(A, Vector2.mult(B, -1));
    }

    /**
     * Divides Vector by a constant 
     * @return Result Vector
     */
    public static Vector2 div(final Vector2 vector, float scalar) {
        return new Vector2(vector.x() / scalar, vector.y() / scalar);
    }

    /**
     * @return Dot product between two vectors A and B
     */
    public static double dot(final Vector2 A, final Vector2 B) {
        return A.x() * B.x() + A.y() * B.y();
    }

    /**
     * @return Angle between two vectors A and B; 
     * always in range [0.0 , pi]
     */
    public static double angleBetween(final Vector2 A, final Vector2 B) {
        return acos( Vector2.dot(A, B) / (A.mag() * B.mag()) );
    }

}
