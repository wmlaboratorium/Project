package com.example.adam.project.new_game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Adam on 5/11/2015.
 */
public class Player extends GameObject {
    private Bitmap bitmap;
    private int score,
                startY = (int)(GamePanel.HEIGHT*0.8),
                jumpHeight = 200;
    private Boolean up,
                    playing = false,
                    jumping = false;
    private long startTime;

    public Player( Bitmap bitmap, int w, int h, int numFrames) {
        this.bitmap = bitmap;
        x = GamePanel.WIDTH/2;
        y = startY;
        dy = 0;
        score = 0;
        height = h;
        width = w;
    }

    public void setUp(Boolean b) { up = b; }

    public void update() {
        if (up) {
            if (y >= startY) {
                dy = -12;
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
                    dy = 12;
            }
            else
                dy = 0;
        }
        y += dy;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public int getScore() { return score; }

    public Boolean getPlaying() { return playing; }

    public void setPlaying(Boolean b) { playing = b;  }

    public void resetScore() { score = 0; }

}
