package com.example.adam.project.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.adam.project.R;
import com.example.adam.project.best_score.BestScoreActivity;
import com.example.adam.project.new_game.GameActivity;

public class MainActivity extends Activity {

    private ImageButton bestScoreButton,
                        startGameButton,
                        optionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }


    private void initialize() {
        startGameButton = (ImageButton)findViewById(R.id.button_startgame);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });


        bestScoreButton = (ImageButton)findViewById(R.id.button_score);
        bestScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BestScoreActivity.class);
                startActivity(intent);
            }
        });


        optionsButton = (ImageButton)findViewById(R.id.button_options);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
