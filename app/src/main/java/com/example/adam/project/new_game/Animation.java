package com.example.adam.project.new_game;

import android.graphics.Bitmap;

/**
 * Created by Adam on 5/12/2015.
 */
public class Animation {
    private Bitmap frames[];
    private int currentFrame,
                framesNum;
    private long startTime,
                 delay;
    private Boolean playedOnce;

    public Animation(int framesNum, Bitmap frames[], long delay) {
        this.framesNum = framesNum;
        this.frames = frames;
        currentFrame = 0;
        startTime = System.currentTimeMillis();
        this.delay = delay;
    }


    public void setFrames(Bitmap frames[]) {
        this.frames = frames;
    }


    public void update() {
        long elapsed = (System.currentTimeMillis() - startTime);

        if (elapsed > delay) {
            currentFrame++;
            if (currentFrame >= framesNum) {
                currentFrame = 0;
                playedOnce = true;
            }
            startTime = System.currentTimeMillis();
        }
    }


    public Bitmap getImage() { return frames[currentFrame];  }

    public int getCurrentFrame() { return currentFrame; }

    public Boolean getPlayedOnce() { return playedOnce; }

    public void setDelay(long l) { delay = l; }

    public void setFrame(int i) { currentFrame = i; }

}
