package br.ufsc.gdev.zkirmisher.physics;


import java.util.Locale;


/**
 * Represents discrete points in two-dimensional space.
 */
public class Point2D extends Vector2 {

    // CONSTRUCTORS
    public Point2D() {
        super();
    }

    public Point2D(float x, float y) {
        super((int) x, (int) y);
    }

    public Point2D(final Vector2 otherVector) {
    	this((int) otherVector.getX(), (int) otherVector.getY());
    }


    // METHODS
    /**
     * @return Integer horizontal position.
     */
    public int x() {
        return (int) super.getX();
    }

    /**
     * @return Integer vertical position.
     */
    public int y() {
        return (int) super.getY();
    }

    /**
     * @return Returns the same value as x(), but as a float.
     */
    @Override
    public float getX() {
        return (int) super.getX();
    }

    @Override
    public void setX(float x) {
        super.setX((int) x);
    }

    /**
     * @return Returns the same value as y(), but as a float.
     */
    @Override
    public float getY() {
        return (int) super.getY();
    }

    @Override
    public void setY(float y) {
        super.setY((int) y);
    }

    @Override
    public void set(float x, float y) {
        super.set((int) x, (int) y);
    }

    /**
     * Add values (x, y) to original vector.
     */
    @Override
    public void offset(float x, float y) {
    	super.offset((int) x, (int) y);
    }

    /**
     * Add some Vector2 to original vector.
     */
    @Override
    public void offset(final Vector2 vector) {
    	offset((int) vector.getX(), (int) vector.getY());
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "(%d, %d)", x(), y());
    }

}
