package com.example.adam.project.new_game;

/**
 * Created by Adam on 5/13/2015.
 */

//Singleton configuration class
public class Config {
    private static Config instance = null;
    private static final int playerStartHp = 100;
    private static final int jumpHeight = 200;
    private static final int enemyWidth = 80;
    private static final int enemyHeight = 80;
    private static final int bonusWidth = 40;
    private static final int bonusHeight = 40;
    private static final int enemySpeed = 20;
    private static final int bonusSpeed = 15;
    private static final int enemySpaceTime = 800;
    private static final int backgroundMoveSpeed = -5;


    public static int getBackgroundMoveSpeed() { return backgroundMoveSpeed; }

    public static int getEnemySpaceTime() { return enemySpaceTime; }

    public static int getEnemySpeed() { return enemySpeed; }

    public static int getBonusSpeed() { return bonusSpeed; }

    public static int getPlayerStartHp() {
        return playerStartHp;
    }

    public static int getJumpHeight() {
        return jumpHeight;
    }

    public static int getEnemyWidth() {
        return enemyWidth;
    }

    public static int getEnemyHeight() {
        return enemyHeight;
    }

    public static int getBonusWidth() {
        return bonusWidth;
    }

    public static int getBonusHeight() {
        return bonusHeight;
    }

    public static synchronized Config getInstance() {
        if (instance == null)
            instance = new Config();
        return instance;
    }
}
