package org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Timelines;

/**
 * User: Jason Wilmans
 * Date: 25.11.13
 * Time: 18:16
 * <p/>
 * This class implements a single point in a time line.
 */
public class DataPoint {

    private final long x;
    private final float y;

    public DataPoint(long x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public long getX() {
        return x;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
