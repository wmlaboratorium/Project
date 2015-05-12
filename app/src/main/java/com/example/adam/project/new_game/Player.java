package com.example.adam.project.new_game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Adam on 5/11/2015.
 */
public class Player extends GameObject {
    private int score,
                startY = (int)(GamePanel.HEIGHT*0.8),
                jumpHeight = 200;
    private Boolean up,
                    playing = false,
                    jumping = false;
    private long startTime;
    private Animation animation;

    public Player(Bitmap frames[], int w, int h, int numFrames) {
        x = GamePanel.WIDTH/2;
        y = startY;
        dy = 0;
        score = 0;
        height = h;
        width = w;
        animation = new Animation(numFrames, frames, 100);
    }

    public void setUp(Boolean b) { up = b; }

    public void update() {
        animation.update();
        if (up) {
            if (y >= startY) {
                dy = -20;
                jumping = true;
            }
        }
        else {
            if (jumping) {
                if (y >= startY) {
                    dy = 0;
                    jumping = false;
                }
                else if (y < startY - jumpHeight)
                    dy = 20;
            }
            else
                dy = 0;
        }
        y += dy;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), x - 100, y - 60, null);
    }

    public int getScore() { return score; }

    public Boolean getPlaying() { return playing; }

    public void setPlaying(Boolean b) { playing = b; }

    public void resetScore() { score = 0; }

}
