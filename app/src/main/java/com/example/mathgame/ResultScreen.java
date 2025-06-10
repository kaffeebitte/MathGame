package com.example.mathgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultScreen extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_screen);

        int score = getIntent().getIntExtra("score", 0);
        int mode = getIntent().getIntExtra("mode", 0);

        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Score: " + score);

        Button playAgainButton = findViewById(R.id.resetButton);
        Button exitButton = findViewById(R.id.exitButton);

        playAgainButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultScreen.this, GameScreen.class);
            intent.putExtra("mode", mode);
            startActivity(intent);
            finish();
        });

        exitButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultScreen.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}
