package com.example.adam.project.new_game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Adam on 4/29/2015.
 */
public class GameThread extends Thread {
    private int FPS = 30,
                frameCount = 0;
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
             targetTime = 1000/FPS;


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

            long usedTime = System.currentTimeMillis() - startTime;
            try {
                Thread.sleep(targetTime - usedTime);
                if (frameCount%20 == 0)
                    System.out.println("We can have "+1000/usedTime+" FPS");
            }
            catch (Exception e) {}
            frameCount++;
        }
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }
}
