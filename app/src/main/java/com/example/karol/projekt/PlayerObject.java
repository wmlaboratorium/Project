package com.example.karol.projekt;

import android.graphics.Bitmap;

/**
 * Created by karol on 30.04.15.
 */
public class PlayerObject extends GameObject {

    public PlayerObject(int posX, int posY, int spdX, int spdY) {
        super(posX, posY, spdX, spdY);
    }

    public PlayerObject(int posX, int posY, int spdX, int spdY, Bitmap texture) {
        super(posX, posY, spdX, spdY, texture);
    }

    public PlayerObject(Vector2 position, Vector2 speed, Bitmap texture) {
        super(position, speed, texture);
    }

    @Override
    public void update() {
        //TODO wypelnic kodem updatujacym
    }

    @Override
    public void move() {
        //TODO a moze by tak tutaj wrzucic obsluge klikniec itp?
    }

    @Override
    public void onClick() {
        //TODO moze sie zrobi jakas super moc czy cos po kliknieciu na gracza :D np GOD_MODE
    }
}
