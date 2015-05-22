package com.example.adam.project.best_score;

/**
 * Created by Adam on 4/27/2015.
 */
public class BestScoreItem {
    private String name,
                   time;
    private int result;

    BestScoreItem(String name, int result, String time) {
        this.name = name;
        this.result = result;
        this.time = time;
    }

    public int getResult() {
        return result;
    }

    public String getName() {
        return name;
    }

    public String getTime() { return time; }

    public void setName(String name) {
        this.name = name;
    }
}
