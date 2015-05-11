package com.example.adam.project.new_game;

import android.graphics.Rect;

/**
 * Created by Adam on 5/11/2015.
 */
public abstract class GameObject {
    protected int x,
                  y,
                  dx,
                  dy,
                  width,
                  height;

    public void setX(int x) {  this.x = x; }

    public void setY(int y) {  this.y = y; }

    public int getX() { return x; }

    public int getY() {  return y; }

    public int getWidth() { return width; }

    public int getHeight() {  return height;  }

    public Rect getRect() { return new Rect(x, y, x + width, y + height); }

}
