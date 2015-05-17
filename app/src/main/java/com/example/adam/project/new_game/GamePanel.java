package com.example.adam.project.new_game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import com.example.adam.project.R;
//import com.example.karol.project.GameObject;
import com.example.karol.project.EnemyObject;
import com.example.karol.project.GameObject;
import com.example.karol.project.PlayerObject;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Adam on 4/29/2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int PLAYER_SPEED_X = 10;
    public static final int PLAYER_SPEED_Y = 5;
    public static int WIDTH = 856;
    public static int HEIGHT = 480;
    public static int MOVE_SPEED = -5;
    private GameThread gameThread;
    private Background background;
    private ArrayBlockingQueue<Integer> inputX;
    private ArrayList<GameObject> gameObjects;
    private PlayerObject player;
    private ArrayList<EnemyObject> enemies;
    private long enemyStartTime,
                 enemySpaceTime,
                 enemyActualTime;
    private Random random = new Random();

    public GamePanel(Context context) {
        super(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        this.WIDTH = size.x;
        this.HEIGHT = size.y;

        //this.inputX = new ArrayBlockingQueue<Integer>(20);
        //this.initGameObjects();

        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        setFocusable(true);
    }

/*
    public void initGameObjects() {
        this.gameObjects = new ArrayList<>();
        this.playerObject = new PlayerObject(0, 0, GamePanel.PLAYER_SPEED_X, GamePanel.PLAYER_SPEED_Y, BitmapFactory.decodeResource(getResources(), R.drawable.player_object));
        this.gameObjects.add(playerObject);
    }
*/
    public void update() {
        /*
        for (GameObject o : this.gameObjects)
            if (!(o instanceof PlayerObject))
                o.update();
        */
        if (player.getPlaying()) {
            background.update(MOVE_SPEED);
            player.update();

            //Add enemy objects on timer
            if (enemies.size() == 0) {
                enemies.add(new EnemyObject(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_object), WIDTH + 10, (int)(HEIGHT*0.8), Config.getInstance().getEnemyWidth(), Config.getInstance().getEnemyHeight(), 2));
                enemySpaceTime = random.nextInt(2000) + 100;
                enemyActualTime = System.currentTimeMillis();
            }
            else {
                if (System.currentTimeMillis() - enemyActualTime > enemySpaceTime) {
                    enemies.add(new EnemyObject(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_object), WIDTH + 10, (int)(HEIGHT*0.8), Config.getInstance().getEnemyWidth(), Config.getInstance().getEnemyHeight(), 2));
                    enemySpaceTime = random.nextInt(2000) + 100;
                    enemyActualTime = System.currentTimeMillis();
                }
            }
        }

        for (EnemyObject enemy : enemies) {

            //Nie usuwam obiektow z listy jak wyjda poza ekran bo scina gre przy tym (nie wiem czemu)
            if (enemy.getX() > -100) {
                enemy.update();

                if (collision(enemy, player)) {
                    enemies.remove(enemy);
                    player.setPlaying(false);
                    if (player.getLives() > 1)
                        player.loseLive();
                    else
                        gameOver();
                    break;
                }
                //Means that player jumped after enemy
                else if (enemy.getX() < (WIDTH / 2 - 50) && !enemy.getJumped()) {

                    enemy.setJumped();
                    player.addToScore(enemy.getScoreValue());
                    System.out.println("Jumped "+ enemies.size()+" Player "+player.getScore());
                }
            }
        }
    }


    public void gameOver() {
        //not inmplemented
    }


    public Boolean collision(GameObject o1, GameObject o2) {
        if (Rect.intersects(o1.getRect(), o2.getRect())) {
            return true;
        }
        return false;
    }


    @Override
    public void draw(Canvas canvas) {
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);
        if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            background.draw(canvas);
            player.draw(canvas);

            for (EnemyObject enemy : enemies) {
                enemy.draw(canvas);
            }

            /*
            for (GameObject o : this.gameObjects)
                o.draw(canvas);
            */
            canvas.restoreToCount(savedState);
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Boolean retry = true;
        int counter = 0;
        while (retry && counter < 1000) {
            counter++;
            try {
                gameThread.setRunning(false);
                gameThread.join();
                retry = false;
            }
            catch (Exception e) {}
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background2));
        Bitmap playerFrames[] = new Bitmap[5];

        playerFrames[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hero_1_frame);
        playerFrames[1] = BitmapFactory.decodeResource(getResources(), R.drawable.hero_2_frame);
        playerFrames[2] = BitmapFactory.decodeResource(getResources(), R.drawable.hero_3_frame);
        playerFrames[3] = BitmapFactory.decodeResource(getResources(), R.drawable.hero_4_frame);
        playerFrames[4] = BitmapFactory.decodeResource(getResources(), R.drawable.hero_5_frame);

        player = new PlayerObject(playerFrames, 60, 100, 5);
        enemies = new ArrayList<EnemyObject>();
        enemyStartTime = System.currentTimeMillis();

        gameThread.setRunning(true);
        gameThread.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        /*
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
        */
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            if (!player.getPlaying()) {
                player.setPlaying(true);
            }
            else {
                player.setUp(true);
            }
            return true;
        }
        else if (e.getAction() == MotionEvent.ACTION_UP) {
            player.setUp(false);
            return true;
        }
        return super.onTouchEvent(e);
    }
}
