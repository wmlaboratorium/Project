package com.example.adam.project.new_game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Adam on 4/29/2015.
 */
public class Background {
    private Bitmap bitmap;
    private int x, y;

    public Background(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void update(int dx) {
        x += dx;
        if (x < -GamePanel.WIDTH) {
            x = 0;
            
        }
    }

    public void draw (Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
        if (x < 0) {
            canvas.drawBitmap(bitmap, x + GamePanel.WIDTH, y, null);
        }
    }

    /*public void setVector(int dx) {
        this.dx = dx;
    }*/


}
