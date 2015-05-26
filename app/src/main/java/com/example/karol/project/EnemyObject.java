package com.example.karol.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import com.example.adam.project.new_game.Animation;
import com.example.adam.project.new_game.GamePanel;
import java.util.Random;

/**
 * Created by karol on 30.04.15.
 */
public class EnemyObject extends GameObject {

    private int speed,
            scoreValue = 2;
    private Random random = new Random();
    private Boolean jumped = false;
    private Animation animation;

    public EnemyObject(Bitmap frames[], int x, int y, int w, int h) {
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        animation = new Animation(frames, 100);
        speed = GamePanel.actualEnemySpeed + random.nextInt((int)(GamePanel.actualEnemySpeed*0.6));
    }

    @Override
    public void update() {
        animation.update();
        x -= speed;
    }

    @Override
    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(animation.getImage(), x - 100, y - 30, null);
        }
        catch (Exception e) {}
    }

    @Override
    public Rect getRect() { return new Rect(x - 70, y - 40, x + width - 70, y + height - 40); }

    @Override
    public int getWidth() { return width - 10; }

    public void setJumped(boolean flag) { jumped = flag; }

    public Boolean getJumped() { return jumped; }

    public int getScoreValue() { return scoreValue; }
}
