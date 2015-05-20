package com.example.adam.project.best_score;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.adam.project.R;
import java.util.ArrayList;
import java.util.List;


public class BestScoreActivity extends ActionBarActivity {
    private List<BestScoreItem> ranking = new ArrayList<BestScoreItem>(6);
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_score);
        createRankingList();
        createRankingListView();
    }

    private void createRankingList() {
        BestScoreItem tab[] = Standings.getInstance(getApplicationContext()).getRankingTable();

        if (tab != null) {
            for (BestScoreItem item : tab)
                ranking.add(item);
        }
    }


    private void createRankingListView() {
        ArrayAdapter<BestScoreItem> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.best_score_ListView);
        list.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_best_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyListAdapter extends ArrayAdapter<BestScoreItem> {
        public MyListAdapter() {
            super(BestScoreActivity.this, R.layout.best_score_item, ranking);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.best_score_item, parent, false);
            }

            String color;
            if (counter%2 == 0)
                color = "#59FFD6";
            else
                color = "#007BFF";
            BestScoreItem current = ranking.get(position);

            TextView playerName = (TextView)itemView.findViewById(R.id.best_score_playerName);
            playerName.setText(current.getName());
            playerName.setBackgroundColor(Color.parseColor(color));

            TextView playerScore = (TextView)itemView.findViewById(R.id.best_score_playerScore);
            playerScore.setText(current.getResult()+"pkt");
            playerScore.setBackgroundColor(Color.parseColor(color));

            counter++;
            return itemView;
        }
    }
}
