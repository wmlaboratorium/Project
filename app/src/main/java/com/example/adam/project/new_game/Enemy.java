package com.example.adam.project.new_game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Adam on 5/12/2015.
 */
public class Enemy extends GameObject {
    private int speed,
                scoreValue = 2;
    private Random random = new Random();
    private Bitmap bitmap;
    private Boolean jumped = false;

    public Enemy(Bitmap bitmap, int x, int y, int w, int h, int numFrames) {
        this.bitmap = bitmap;
        super.x = x;
        super.y = y;
        width = w;
        height = h;

        speed = 15 + random.nextInt(15);
    }

    public void update() { x -= speed; }

    public void setJumped() { jumped = true; }

    public Boolean getJumped() { return jumped; }

    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(bitmap, x, y, null);
        }
        catch (Exception e) {}
    }

    @Override
    public int getWidth() { return width - 10; }

    public int getScoreValue() { return scoreValue; }
}
