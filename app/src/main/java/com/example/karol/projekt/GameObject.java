package com.example.karol.projekt;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by karol on 30.04.15.
 */
public abstract class GameObject implements Serializable {
    //to taka zmienna w razie potrzeby przetrzymywania czegos dodatkowego :P
    private Object otherSource = null;
    //pozycja obiektu
    private Vector2 position = null;
    //predkosc obiektu
    private Vector2 speed = null;
    //texturka obiektu
    private Bitmap texture = null;


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
    public abstract void update();

    //na wszelki wypadek wrzucilem, bo sam jeszcze nie wiem czy sie przyda, bynajmniej tutaj beda algorytmy przesuwania ?
    public abstract void move();

    //no jezeli sobie na obiekt klikniemy to zostanie to wywolane (zakladam ze w wiekszosci obiektow bedzie puste :P)
    public abstract void onClick();
}
