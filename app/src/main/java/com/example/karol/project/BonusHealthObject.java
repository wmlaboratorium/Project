package com.example.karol.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.example.adam.project.new_game.Config;
import java.util.Random;

/**
 * Created by kplos on 19.05.2015.
 */
public class BonusHealthObject extends GameObject {
    private int speed;
    private Random random = new Random();
    private Bitmap bitmap;

    public BonusHealthObject(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        super.x = x;
        super.y = y;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        speed = Config.getInstance().getBonusSpeed() + random.nextInt(Config.getInstance().getBonusSpeed());
    }

    public void update() { x -= speed; }

    @Override
    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(bitmap, x, y, null);
        }
        catch (Exception e) {}
    }
}
