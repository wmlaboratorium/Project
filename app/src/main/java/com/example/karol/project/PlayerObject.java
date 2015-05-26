package com.example.karol.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.example.adam.project.new_game.Animation;
import com.example.adam.project.new_game.Config;
import com.example.adam.project.new_game.GamePanel;

/**
 * Created by karol on 30.04.15.
 */
public class PlayerObject extends GameObject {

    private int score,
            startY = (int)(GamePanel.HEIGHT - 150),
            hp = Config.getInstance().getPlayerStartHp(),
            speed = Config.getInstance().getPlayerSpeed();
    private Boolean up,
            playing = false,
            jumping = false;
    private Animation animation;
    private Paint paint = new Paint();
    private String time;
    private double factor = 1.0,
                   factorModifier = Config.getInstance().getPlayerFactorModifier();

    public PlayerObject(Bitmap frames[], int w, int h) {
        x = (int)(GamePanel.WIDTH * 0.4);
        y = startY;
        dy = 0;
        score = 0;
        height = h;
        width = w;
        animation = new Animation(frames, 100);
        paint.setTextSize(GamePanel.HEIGHT / 15);
    }


    @Override
    public void update() {
        animation.update();
        if (up) {                               //Start jumping
            if (y >= startY) {
                dy = speed;
                jumping = true;
            }
            up = false;
        }
        else {
            if (jumping) {                       //While jumping
                if (y >= startY - 20) {          //Player fell to ground
                    dy = 0;
                    y = startY;
                    jumping = false;
                    factor = 1;
                }
                else {   dy = (int)(speed*factor);     factor -= factorModifier;   }
            }
        }
        if (dy != 0)
            y += dy;
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), x - 30, y - 60, null);
        canvas.drawText("Score: "+score, 40, (int)(GamePanel.HEIGHT * 0.1), paint);
        canvas.drawText("HP: "+hp, 40, (int)(GamePanel.HEIGHT * 0.18), paint);
        time = getTimeInMinutes(GamePanel.duringGameInSeconds);
        canvas.drawText("Game Time: "+time, 40, (int)(GamePanel.HEIGHT * 0.26), paint);
    }

    @Override
    public Rect getRect() { return new Rect(x, y - 40, x + width, y + height - 40); }

    public String getTimeInMinutes(int seconds) {
        String result = "";
        if (seconds >= 60)
            result += seconds/60 + "\"";

        if (seconds%60 < 10)
            result += "0";

        result += seconds%60 + "\"";
        return result;
    }

    public void addHp(int value) {
        hp += value;
        if (hp > 100)
            hp = 100;
    }

    public String getTime() { return time; }

    public void setUp(Boolean b) { up = b; }

    public void addToScore(int additional) { score += additional; }

    public int getScore() { return score; }

    public void loseHp(int value) { hp -= value; }

    public int getHp() { return hp; }

    public Boolean getPlaying() { return playing; }

    public void setPlaying(Boolean b) { playing = b; }
}
