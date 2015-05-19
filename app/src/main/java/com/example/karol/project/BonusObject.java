package com.example.karol.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by kplos on 19.05.2015.
 */
public class BonusObject extends GameObject {
    private int speed,
            scoreValue = 2;
    private Random random = new Random();
    private Bitmap bitmap;
    private Boolean jumped = false;

    public BonusObject(Bitmap bitmap, int x, int y, int w, int h, int numFrames) {
        this.bitmap = bitmap;
        super.x = x;
        super.y = y;
        width = w;
        height = h;

        speed = 10 + random.nextInt(10);
    }

    @Override
    public void update(int x, int y) {

    }

    public void update() { x -= speed; }

    @Override
    public void move() {

    }

    @Override
    public void onClick() {

    }

    @Override
    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(bitmap, x, y, null);
        }
        catch (Exception e) {}
    }

    @Override
    public int getWidth() { return width - 10; }

    public void setJumped(boolean flag) { jumped = flag; }

    public Boolean getJumped() { return jumped; }

    public int getScoreValue() { return scoreValue; }
}
