package com.example.adam.project.new_game;

/**
 * Created by Adam on 5/13/2015.
 */

//Singleton configuration class
public class Config {
    private static Config instance = null;
    private static final int playerLives = 3,
                             jumpHeight = 200,
                             enemyWidth = 60,
                             enemyHeight = 60;

    public static int getPlayerLives() {
        return playerLives;
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

    public static synchronized Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

}
