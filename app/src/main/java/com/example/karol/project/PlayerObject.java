package com.example.karol.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.adam.project.new_game.GamePanel;

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
    public void update(int x, int y) {
        if (GamePanel.WIDTH / 2 > x)
            this.position.setX(this.position.getX() - this.speed.getX());
        else if (GamePanel.WIDTH / 2 <= x)
            this.position.setX(this.position.getX() + this.speed.getX());
        System.err.println("Player position: X = " + this.position.getX() + " Y = " + this.position.getY());
    }

    @Override
    public void update() {

    }

    @Override
    public void move() {
        //TODO a moze by tak tutaj wrzucic obsluge klikniec itp?
    }

    @Override
    public void onClick() {
        //TODO moze sie zrobi jakas super moc czy cos po kliknieciu na gracza :D np GOD_MODE
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.texture, GamePanel.WIDTH / 2, GamePanel.HEIGHT - 60, null);
    }
}
