package com.example.karol.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import java.io.Serializable;

/**
 * Created by karol on 30.04.15.
 */
public abstract class GameObject implements Serializable {
    //to taka zmienna w razie potrzeby przetrzymywania czegos dodatkowego :P
    protected Object otherSource = null;
    //pozycja obiektu
    protected Vector2 position = null;
    //predkosc obiektu
    protected Vector2 speed = null;
    //texturka obiektu
    protected Bitmap texture = null;

    protected int x,
            y,
            dy,
            width,
            height;

    /**
     * ***************KONSTRUKTORY**********************
     */
    protected GameObject() {
        this.setPosition(new Vector2());
        this.setSpeed(new Vector2());
    }

    protected GameObject(int posX, int posY, int spdX, int spdY) {
        this.setPosition(new Vector2(posX, posY));
        this.setSpeed(new Vector2(spdX, spdY));
    }

    protected GameObject(int posX, int posY, int spdX, int spdY, Bitmap texture) {
        this.setPosition(new Vector2(posX, posY));
        this.setSpeed(new Vector2(spdX, spdY));
        this.setTexture(texture);
    }

    protected GameObject(Vector2 position, Vector2 speed, Bitmap texture) {
        this.setPosition(position);
        this.setSpeed(speed);
        this.setTexture(texture);
    }

    protected GameObject(Vector2 position, Vector2 speed) {
        this.setPosition(position);
        this.setSpeed(speed);
    }

    /**
     * ***************KONIEC KONSTRUKTOROW**********************
     */

    public Object getOtherSource() {
        return otherSource;
    }

    public void setOtherSource(Object otherSource) {
        this.otherSource = otherSource;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getSpeed() {
        return speed;
    }

    public void setSpeed(Vector2 speed) {
        this.speed = speed;
    }

    public Bitmap getTexture() {
        return texture;
    }

    public void setTexture(Bitmap texture) {
        this.texture = texture;
    }

    //to funkcja ktora bedzie updateowac stan obiektu (czyt. przesuniecia itp)
    //public abstract void update(int x, int y);

    public abstract void update();

    //rysujemy na ekranie nasz obiekt
    public abstract void draw(Canvas canvas);

    public void setX(int x) {  this.x = x; }

    public void setY(int y) {  this.y = y; }

    public int getX() { return x; }

    public int getY() {  return y; }

    public int getWidth() { return width; }

    public int getHeight() {  return height;  }

    public Rect getRect() { return new Rect(x, y, x + width, y + height); }

}
