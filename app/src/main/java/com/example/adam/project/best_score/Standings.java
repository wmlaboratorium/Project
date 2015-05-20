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
    public Boolean isGoodResult(int score) {

        String input = readFromFile();
        if (!input.equals("")) {
            String results[] = input.split(" ");
            System.out.println(input+": "+results.length);
            ranking = new BestScoreItem[results.length + 1];

            for (int i = 0; i < results.length; i++) {
                String data[] = results[i].split(",");
                ranking[i] = new BestScoreItem(data[0], Integer.parseInt(data[1]));
            }
            ranking[results.length] = new BestScoreItem("NewScore", score);
            ranking = QuickSort.Sort(ranking, 0, ranking.length - 1);

            for (int i = 0; i < ranking.length; i++) {
                if (ranking[i].getName().equals("NewScore") && i < 10) {
                    return true;
                }
            }
        }
        else {
            System.out.println(input+": new");
            ranking = new BestScoreItem[1];
            ranking[0] = new BestScoreItem("NewScore", score);
            return true;
        }

        return false;
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
        System.out.println("read: "+contents);
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
        for (int i = 0; i < ranking.length; i++) {
            rank += ranking[i].getName()+","+ranking[i].getResult()+" ";
        }

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
