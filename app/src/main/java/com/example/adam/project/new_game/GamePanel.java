package com.example.adam.project.new_game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Adam on 4/29/2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);

        setFocusable(true);
    }

    public void update() {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Boolean retry = true;
        while (retry) {
            try {
                gameThread.setRunning(false);
                gameThread.join();
            }
            catch (Exception e) {}
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        return super.onTouchEvent(e);
    }
}
