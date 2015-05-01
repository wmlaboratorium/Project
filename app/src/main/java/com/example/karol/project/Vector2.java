package com.example.karol.project;

/**
 * Created by karol on 30.04.15.
 */
public final class Vector2 {
    private int x;
    private int y;

    public Vector2() {
    }

    public Vector2(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public int compareTo(Vector2 v) {
        if (v.getX() == this.x && v.getY() == this.y)
            return 0;
        else {
            int dist = (int) (this.getDistanceTo(v));
            if (this.x > v.getX())
                return -dist;
            else
                return dist;
        }
    }

    public double getDistanceTo(Vector2 v) {
        return Math.sqrt(
                Math.pow((double) this.x - (double) v.getX(), 2)
                        + Math.pow((double) this.y - (double) v.getY(), 2));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "X = " + this.x + " Y = " + this.y + " Class instance = " + this;
    }
}
