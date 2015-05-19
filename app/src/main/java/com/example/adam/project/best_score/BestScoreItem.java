package com.example.adam.project.best_score;

/**
 * Created by Adam on 4/27/2015.
 */
public class BestScoreItem {
    private String name;
    private int result;

    BestScoreItem(String name, int result) {
        this.name = name;
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
