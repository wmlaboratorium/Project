package com.example.adam.project.new_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adam.project.R;
import com.example.adam.project.best_score.BestScoreActivity;
import com.example.adam.project.best_score.Standings;

public class AddToRankingActivity extends Activity {
    private int score;
    private Button button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_ranking);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);

        editText = (EditText)findViewById(R.id.add_to_ranking_editText);

        button = (Button)findViewById(R.id.add_to_ranking_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                name = name.replace(" ","_");
                name = name.replace(",","_");
                if (name.length() < 3)
                    Toast.makeText(getApplicationContext(), "Nazwa musi zawierac conajmniej 3 znaki", Toast.LENGTH_LONG).show();
                else {
                    Standings.getInstance(getApplicationContext()).addToRanking(name);;
                    Intent intent = new Intent(AddToRankingActivity.this, EndedGameActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_to_ranking, menu);
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
