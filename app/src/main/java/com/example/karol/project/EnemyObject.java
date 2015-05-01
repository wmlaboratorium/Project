package com.example.karol.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;

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
    public void update(int x, int y) {

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

    @Override
    public void draw(Canvas canvas) {

    }
}
