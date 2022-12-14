package com.slvrmn.DNFAssistant.Model;

public class Point {

    public static final Point INVALID_POINT = new Point(-1, -1);
    private int x = 0;
    private int y = 0;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
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

    public boolean isValid() {
        return !(x < 0 || y < 0);
    }


    @Override
    public String toString() {
        return "{" + x + "," + y + "}";
    }

    public Rectangle MakeRectangle(int width, int height) {
        if(isValid()){
            return new Rectangle(x,y,x+width,y+height);
        }
        else {
            return Rectangle.INVALID_RECTANGLE;
        }
    }
}
