package com.slvrmn.DnfAssistant.Model;

public class Rectangle {
    public static Rectangle INVALID_RECTANGLE = new Rectangle(-1, -1, -1, -1);

    public int x1, y1, x2, y2;

    public Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean Contains(Point p) {
        if (p.getX() >= x1 && p.getX() <= x2 && p.getY() >= y1 && p.getY() <= y2) {
            return true;
        }
        return false;
    }

    public boolean isValid() {
        if (x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0 || x2 < x1 || y2 < y1) {
            return false;
        }
        return true;
    }
}