package com.example.adam.project.best_score;

/**
 * Created by Adam on 5/19/2015.
 */
public class QuickSort {

    public static BestScoreItem[] Sort(BestScoreItem tab[], int x, int y) {
        int v, i, j;
        BestScoreItem tmp;
        i = x;
        j = y;
        v = tab[(x + y)/2].getResult();

        do {
            while (tab[i].getResult() > v)
                i++;
            while (v > tab[j].getResult())
                j--;
            if (i <= j) {
                tmp = tab[i];
                tab[i] = tab[j];
                tab[j] = tmp;
                i++;
                j--;
            }
        }
        while (i <= j);
        if (x < j)
            Sort(tab, x, j);
        if (i < y)
            Sort(tab, i, y);

        return tab;
    }
}
