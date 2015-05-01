package com.example.adam.project.new_game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.example.adam.project.R;
import com.example.karol.project.GameObject;
import com.example.karol.project.PlayerObject;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Adam on 4/29/2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int PLAYER_SPEED_X = 10;
    public static final int PLAYER_SPEED_Y = 5;
    public static int WIDTH;
    public static int HEIGHT;
    private GameThread gameThread;
    private Background background;

    private ArrayBlockingQueue<Integer> inputX;

    private ArrayList<GameObject> gameObjects;
    private PlayerObject playerObject;

    public GamePanel(Context context) {
        super(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        this.WIDTH = size.x;
        this.HEIGHT = size.y;

        this.inputX = new ArrayBlockingQueue<Integer>(20);

        this.initGameObjects();

        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);

        setFocusable(true);
    }

    public void initGameObjects() {
        this.gameObjects = new ArrayList<>();

        this.playerObject = new PlayerObject(0, 0, GamePanel.PLAYER_SPEED_X, GamePanel.PLAYER_SPEED_Y, BitmapFactory.decodeResource(getResources(), R.drawable.player_object));

        this.gameObjects.add(playerObject);
    }

    public void update() {
        for (GameObject o : this.gameObjects)
            if (!(o instanceof PlayerObject))
                o.update();

    }

    @Override
    public void draw(Canvas canvas) {
        final float scaleFactorX = getWidth()/WIDTH;
        final float scaleFactorY = getHeight()/HEIGHT;
        if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            background.draw(canvas);
            for (GameObject o : this.gameObjects)
                o.draw(canvas);
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
        //background.setVector(-5);

        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        System.err.println("Lapie event " + e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) e.getX();
                int y = (int) e.getY();
                if (x < this.WIDTH / 2)
                    background.update(this.playerObject.getSpeed().getX());
                else
                    background.update(-this.playerObject.getSpeed().getX());
                this.playerObject.update(x, y);
                break;
            default:

                break;
        }
        return super.onTouchEvent(e);
    }
}
