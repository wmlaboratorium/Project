package com.example.adam.project.new_game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.example.adam.project.R;
import com.example.karol.project.BonusObject;
import com.example.adam.project.best_score.Standings;
import com.example.karol.project.EnemyObject;
import com.example.karol.project.GameObject;
import com.example.karol.project.PlayerObject;
import java.io.File;
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
    private Bitmap ground;
    private ArrayBlockingQueue<Integer> inputX;
    private PlayerObject player;
    private ArrayList<GameObject> gameObjects;
    private long enemyStartTime,
            enemySpaceTime,
            enemyActualTime;
    private Random random = new Random();
    private Context context;

    public GamePanel(Context context) {
        super(context);
        this.context = context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        this.WIDTH = size.x;
        this.HEIGHT = size.y;

        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        setFocusable(true);
    }

    public void update() {
        if (player.getPlaying()) {
            background.update(MOVE_SPEED);
            player.update();

            //Add enemy objects on timer
            if (gameObjects.size() == 0) {
                gameObjects.add(new EnemyObject(
                        BitmapFactory.decodeResource(getResources(), R.drawable.enemy_object),
                        WIDTH + 10,
                        (int) (HEIGHT * 0.8),
                        Config.getInstance().getEnemyWidth(),
                        Config.getInstance().getEnemyHeight(),
                        2));
                enemySpaceTime = random.nextInt(2000) + 100;
                enemyActualTime = System.currentTimeMillis();
            } else {
                if (System.currentTimeMillis() - enemyActualTime > enemySpaceTime + 500) {
                    gameObjects.add(new EnemyObject(
                            BitmapFactory.decodeResource(getResources(), R.drawable.enemy_object),
                            WIDTH + 10,
                            (int) (HEIGHT * 0.8),
                            Config.getInstance().getEnemyWidth(),
                            Config.getInstance().getEnemyHeight(),
                            2));
                    enemySpaceTime = random.nextInt(2000) + 100;
                    enemyActualTime = System.currentTimeMillis();
                }
            }
            int rand = random.nextInt(3000);
            if(rand < 10) {
                gameObjects.add(new BonusObject(
                        BitmapFactory.decodeResource(getResources(), R.drawable.bonus),
                        WIDTH + 10,
                        (int) (HEIGHT * 0.8),
                        Config.getInstance().getBonusWidth(),
                        Config.getInstance().getBonusHeight(),
                        2));
            }
        }

        ArrayList<GameObject> objectsToDelete = new ArrayList<>();

        for (GameObject obj : gameObjects) {
            obj.update();
            if (obj.getX() < -20)
                objectsToDelete.add(obj);

            if(obj instanceof BonusObject) {
                if(collision(obj, player)) {
                    //dodajemy losowa ilosc pkt zycia
                    gameObjects.remove(obj);
                    player.addLives(random.nextInt(100) % 3 + 1);
                    break;
                } else if (obj.getX() < (WIDTH / 2 - 50) && !((BonusObject) obj).getJumped()) {
                    ((BonusObject) obj).setJumped(true);
                }
            }

            if (obj instanceof EnemyObject) {
                if (collision(obj, player)) {
                    gameObjects.remove(obj);
                    player.loseLive();
                    if (player.getLives() < 1)
                        gameOver();
                    break;
                }
                //Means that player jumped after enemy
                else if (obj.getX() < (WIDTH / 2 - 50) && !((EnemyObject) obj).getJumped()) {
                    ((EnemyObject) obj).setJumped(true);
                    player.addToScore(((EnemyObject) obj).getScoreValue());
                }
            }
        }
        //tutaj usuwamy smieci (byc moze malo optymalne ale na ta gre nie wplynie zbytnio ze wzgledu na brak duzej ilosci obiektow)
        for (GameObject obj : objectsToDelete)
            gameObjects.remove(obj);

        System.out.println(gameObjects.size());
    }


    public void gameOver() {
        player.setPlaying(false);

        if (Standings.getInstance(context).isGoodResult(player.getScore())) {
            System.out.println("Good Result");
        }


    }


    public Boolean collision(GameObject o1, GameObject o2) {
        if (Rect.intersects(o1.getRect(), o2.getRect())) {
            return true;
        }
        return false;
    }


    @Override
    public void draw(Canvas canvas) {
        final float scaleFactorX = getWidth() / (WIDTH * 1.f);
        final float scaleFactorY = getHeight() / (HEIGHT * 1.f);
        if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            background.draw(canvas);
            canvas.drawBitmap(ground, 0, GamePanel.HEIGHT - 30, null);
            player.draw(canvas);
            if (player.getLives() < 1) {
                Paint p = new Paint();
                p.setTextSize(GamePanel.HEIGHT / 10);
                canvas.drawText("GAME OVER!", GamePanel.WIDTH / 2 - 150, GamePanel.HEIGHT / 2, p);
            }

            for (GameObject obj : gameObjects) {
                obj.draw(canvas);
            }
            canvas.restoreToCount(savedState);
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

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
            } catch (Exception e) {
            }
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
        gameObjects = new ArrayList<GameObject>();
        enemyStartTime = System.currentTimeMillis();
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground);

        gameThread.setRunning(true);
        gameThread.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            if (!player.getPlaying()) {
                player.setPlaying(true);
                player.reset();
                gameObjects.clear();
                gameObjects = new ArrayList<>();
            } else {
                player.setUp(true);
            }
            return true;
        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            player.setUp(false);
            return true;
        }
        return super.onTouchEvent(e);
    }
}
