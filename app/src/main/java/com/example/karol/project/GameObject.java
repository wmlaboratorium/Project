package com.example.karol.project;

import android.graphics.Canvas;
import android.graphics.Rect;
import java.io.Serializable;

/**
 * Created by karol on 30.04.15.
 */
public abstract class GameObject implements Serializable {

    protected int x,
            y,
            dy,
            width,
            height;

    /**
     * ***************KONSTRUKTORY**********************
     */
    protected GameObject() {}

    /**
     * ***************KONIEC KONSTRUKTOROW**********************
     */

    public abstract void update();

    //rysujemy na ekranie nasz obiekt
    public abstract void draw(Canvas canvas);

    public int getX() { return x; }

    public Rect getRect() { return new Rect(x, y, x + width, y + height); }

}
