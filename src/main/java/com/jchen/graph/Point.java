package com.jchen.graph;

public class Point {
    private double x, y;

    public Point(double x, double y) {
        this.x = x; this.y = y;
    }

    public Point() {
        this(0, 0);
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
