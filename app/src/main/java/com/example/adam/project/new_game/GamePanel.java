package com.example.adam.project.new_game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.adam.project.R;

/**
 * Created by Adam on 4/29/2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int WIDTH = 750;
    public static final int HEIGHT = 450;
    private GameThread gameThread;
    private Background background;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);

        setFocusable(true);
    }

    public void update() {
        background.update();
    }

    @Override
    public void draw(Canvas canvas) {
        final float scaleFactorX = getWidth()/WIDTH;
        final float scaleFactorY = getHeight()/HEIGHT;
        if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            background.draw(canvas);
            canvas.restoreToCount(savedState);
        }
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
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        background.setVector(-5);

        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        return super.onTouchEvent(e);
    }
}
