package com.example.adam.project.best_score;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Adam on 5/19/2015.
 */
public class Standings {
    private static volatile Standings instance = null;
    private BestScoreItem ranking[];
    private File file;

    public Standings(Context context) {
        String path = context.getFilesDir().getAbsolutePath();
        file = new File(path + "/ranking.txt");
    }


    //Function check that our result is in top 10 results of all time
    public Boolean isGoodResult(int score, String time) {
        String input = readFromFile();
        if (!input.equals("")) {
            String results[] = input.split(" ");
            ranking = new BestScoreItem[results.length + 1];

            for (int i = 0; i < results.length; i++) {
                String data[] = results[i].split(",");
                ranking[i] = new BestScoreItem(data[0], Integer.parseInt(data[1]), data[2]);
            }
            ranking[results.length] = new BestScoreItem("NewScore", score, time);
            ranking = QuickSort.Sort(ranking, 0, ranking.length - 1);

            for (int i = 0; i < ranking.length; i++) {
                if (ranking[i].getName().equals("NewScore") && i < 10) {
                    return true;
                }
            }
        }
        else {
            ranking = new BestScoreItem[1];
            ranking[0] = new BestScoreItem("NewScore", score, time);
            return true;
        }
        return false;
    }


    public BestScoreItem[] getRankingTable() {
        String input = readFromFile();
        if (!input.equals("")) {
            String results[] = input.split(" ");
            ranking = new BestScoreItem[results.length];

            int length;
            if (results.length > 10)
                length = 10;
            else
                length = results.length;

            for (int i = 0; i < length; i++) {
                String data[] = results[i].split(",");
                ranking[i] = new BestScoreItem(data[0], Integer.parseInt(data[1]), data[2]);
            }
            ranking = QuickSort.Sort(ranking, 0, ranking.length - 1);
            return ranking;
        }
        else
            return null;
    }


    public String readFromFile() {
        int length = (int)file.length();
        byte[] bytes = new byte[length];

        try {
            FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();
        }
        catch (Exception e) {}

        String contents = new String(bytes);
        return contents;
    }


    public void addToRanking(String name) {
        for (BestScoreItem item : ranking) {
            if (item.getName().equals("NewScore")) {
                item.setName(name);
                break;
            }
        }
        saveToFile();
    }


    public void saveToFile() {
        String rank = "";
        int length;
        if (ranking.length > 10)
            length = 10;
        else
            length = ranking.length;

        for (int i = 0; i < length; i++)
            rank += ranking[i].getName()+","+ranking[i].getResult()+","+ranking[i].getTime()+" ";

        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(rank.getBytes());
            stream.close();
        }
        catch (Exception e) {}
    }


    public static synchronized Standings getInstance(Context context) {
        if (instance == null) {
            instance = new Standings(context);
        }
        return instance;
    }
}
