package com.example.karol.projekt;

import android.graphics.Bitmap;

/**
 * Created by karol on 30.04.15.
 */
public class EnemyObject extends GameObject {

    public EnemyObject(int posX, int posY, int spdX, int spdY) {
        super(posX, posY, spdX, spdY);
    }

    public EnemyObject(int posX, int posY, int spdX, int spdY, Bitmap texture) {
        super(posX, posY, spdX, spdY, texture);
    }

    public EnemyObject(Vector2 position, Vector2 speed, Bitmap texture) {
        super(position, speed, texture);
    }

    public EnemyObject(Vector2 position, Vector2 speed) {
        super(position, speed);
    }

    @Override
    public void update() {
        //TODO wypelnic kodem updatujacym
    }

    @Override
    public void move() {
        //TODO wypelnic kodem poruszajacym
    }

    @Override
    public void onClick() {
        //TODO moze cos sie wymysli tutaj :P
    }
}
