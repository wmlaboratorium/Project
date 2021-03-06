package com.example.adam.project.new_game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Adam on 4/29/2015.
 */
public class GameThread extends Thread {
    private int FPS = 30;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        long startTime,
             targetTime = 1000 / FPS;

        while (running) {
            startTime = System.currentTimeMillis();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            }
            catch (Exception e) {}
            finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e) {}
                }
            }

            //Needed to archive exactly how many FPS as we want
            long usedTime = System.currentTimeMillis() - startTime;
            try {
                Thread.sleep(targetTime - usedTime);
            }
            catch (Exception e) {}
        }
    }

    public void setRunning(Boolean b) {
        running = b;
    }
}
