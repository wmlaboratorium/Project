package com.example.karol.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.adam.project.new_game.Animation;
import com.example.adam.project.new_game.Config;
import com.example.adam.project.new_game.GamePanel;

import java.util.Random;

/**
 * Created by kplos on 26.05.2015.
 */
public class BirdObject extends GameObject {
    private Animation animation;

    private int speed,
            scoreValue = 2;
    private Random random = new Random();
    private Bitmap bitmap;
    private Boolean jumped = false;

    public BirdObject(Bitmap frames[], int x, int y, int w, int h) {
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        animation = new Animation(frames, 100);
        animation.setFrame(random.nextInt(100000) % 14);
        speed = GamePanel.actualEnemySpeed + random.nextInt((int)(GamePanel.actualEnemySpeed*0.1)) - 4;
    }

    public void update() {
        animation.update();
        x += speed;
    }

    @Override
    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(animation.getImage(), x - 100, y - 30, null);
        }
        catch (Exception e) {}
    }

    @Override
    public int getWidth() { return width - 10; }
}
