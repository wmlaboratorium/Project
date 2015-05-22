package com.example.adam.project.new_game;

import android.content.Context;
import android.content.Intent;
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
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Adam on 4/29/2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static int WIDTH = 856;
    public static int HEIGHT = 480;
    public static int MOVE_SPEED = -5;
    public static volatile int duringGameInSeconds,
                               actualEnemySpeed = Config.getInstance().getEnemySpeed();
    private GameThread gameThread;
    private Background background;
    private Bitmap ground,
                   penguinFrames[];
    private PlayerObject player;
    private ArrayList<GameObject> gameObjects;
    private long enemySpaceTime,
            enemyActualTime,
            gameTime;
    private Random random = new Random();
    private Context context;
    private Boolean gameOver = false;

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
            duringGameInSeconds = (int)((System.currentTimeMillis() - gameTime) / 1000);
            background.update(MOVE_SPEED);
            player.update();

            //Add enemy objects to screen in fixed spaces of time
            if (gameObjects.size() == 0) {
                gameObjects.add(new EnemyObject(penguinFrames,
                        WIDTH + 10,
                        (int) (HEIGHT * 0.8),
                        Config.getInstance().getEnemyWidth(),
                        Config.getInstance().getEnemyHeight()));
                int time = Config.getInstance().getEnemySpaceTime();
                enemySpaceTime = random.nextInt((int)(time * 1.5)) + time;
                enemyActualTime = System.currentTimeMillis();
            }
            else {
                if (System.currentTimeMillis() - enemyActualTime > enemySpaceTime) {
                    actualEnemySpeed = Config.getEnemySpeed() + (int)(duringGameInSeconds / 10);
                    if (actualEnemySpeed > 35)
                        actualEnemySpeed = 35;
                    gameObjects.add(new EnemyObject(penguinFrames,
                            WIDTH + 10,
                            (int) (HEIGHT * 0.8),
                            Config.getInstance().getEnemyWidth(),
                            Config.getInstance().getEnemyHeight()));

                    //Reducing spaces between appearing new enemy objects while during the game
                    int time = (int)(Config.getInstance().getEnemySpaceTime()*(1.0 - (duringGameInSeconds/120.0)));
                    if (time < 500)
                        time = 500;
                    enemySpaceTime = random.nextInt((int)(time * 1.5)) + time;
                    enemyActualTime = System.currentTimeMillis();
                }
            }
            int rand = random.nextInt(3000);
            if(rand < 10) {
                gameObjects.add(new BonusObject(
                        BitmapFactory.decodeResource(getResources(), R.drawable.bonus),
                        WIDTH + 10,
                        (int) (HEIGHT * 0.8)));
            }
        }

        ArrayList<GameObject> objectsToDelete = new ArrayList<>();

        for (GameObject obj : gameObjects) {
            obj.update();
            if (obj.getX() < -20)
                objectsToDelete.add(obj);

            //Adding bonus amount of hp to player
            if(obj instanceof BonusObject) {
                if(isCollision(obj, player)) {
                    gameObjects.remove(obj);
                    player.addHp(random.nextInt(20) + 10);
                    break;
                } else if (obj.getX() < (WIDTH / 2 - 50) && !((BonusObject) obj).getJumped()) {
                    ((BonusObject) obj).setJumped(true);
                }
            }

            //Checking collisions between player and enemies
            if (obj instanceof EnemyObject) {
                if (isCollision(obj, player)) {
                    gameObjects.remove(obj);
                    player.loseHp(20);
                    if (player.getHp() < 1) {
                        player.setPlaying(false);
                        gameOver = true;
                    }
                    break;
                }
                //Means that player jumped after enemy
                else if (obj.getX() < (WIDTH / 2 - 50) && !((EnemyObject) obj).getJumped()) {
                    ((EnemyObject) obj).setJumped(true);
                    player.addToScore(((EnemyObject) obj).getScoreValue());
                }
            }
        }
        //Deleting out of the screen Game Objects
        for (GameObject obj : objectsToDelete)
            gameObjects.remove(obj);
    }


    //Function which detecting collisions between two Game Objects
    public Boolean isCollision(GameObject o1, GameObject o2) {
        if (Rect.intersects(o1.getRect(), o2.getRect()))
            return true;
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
            if (player.getHp() < 1) {
                Paint p = new Paint();
                p.setTextSize(GamePanel.HEIGHT / 10);
                canvas.drawText("GAME OVER!", GamePanel.WIDTH / 2 - 150, GamePanel.HEIGHT / 2, p);
            }

            for (GameObject obj : gameObjects)
                obj.draw(canvas);
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
            } catch (Exception e) {}
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Initialize all necessary Game Objects and Attributes
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background2));
        Bitmap playerFrames[] = new Bitmap[5];
        penguinFrames = new Bitmap[4];

        playerFrames[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hero_1_frame);
        playerFrames[1] = BitmapFactory.decodeResource(getResources(), R.drawable.hero_2_frame);
        playerFrames[2] = BitmapFactory.decodeResource(getResources(), R.drawable.hero_3_frame);
        playerFrames[3] = BitmapFactory.decodeResource(getResources(), R.drawable.hero_4_frame);
        playerFrames[4] = BitmapFactory.decodeResource(getResources(), R.drawable.hero_5_frame);

        penguinFrames[0] = BitmapFactory.decodeResource(getResources(), R.drawable.penguin1);
        penguinFrames[1] = BitmapFactory.decodeResource(getResources(), R.drawable.penguin2);
        penguinFrames[2] = BitmapFactory.decodeResource(getResources(), R.drawable.penguin3);
        penguinFrames[3] = BitmapFactory.decodeResource(getResources(), R.drawable.penguin4);

        player = new PlayerObject(playerFrames, 60, 100);
        gameObjects = new ArrayList<GameObject>();
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground);

        gameThread.setRunning(true);
        gameThread.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            if (!player.getPlaying() && !gameOver) {
                player.setPlaying(true);
                gameTime = System.currentTimeMillis();
                gameObjects.clear();
                gameObjects = new ArrayList<>();
            }
            else if (gameOver) {
                gameThread.setRunning(false);
                if (Standings.getInstance(context).isGoodResult(player.getScore())) {
                    Intent intent = new Intent(context, AddToRankingActivity.class);
                    intent.putExtra("score", player.getScore());
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, EndedGameActivity.class);
                    context.startActivity(intent);
                }
            }
            else
                player.setUp(true);
            return true;
        }
        else if (e.getAction() == MotionEvent.ACTION_UP) {
            player.setUp(false);
            return true;
        }
        return super.onTouchEvent(e);
    }
}
