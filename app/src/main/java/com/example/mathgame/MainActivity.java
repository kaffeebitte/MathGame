package com.example.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button additionButton = findViewById(R.id.additionBtn);
        additionButton.setOnClickListener(v -> {
            int mode = 0;
            Intent intent = new Intent(MainActivity.this, GameScreen.class);
            intent.putExtra("mode", mode);
            startActivity(intent);
        });

        Button subtractionButton = findViewById(R.id.subtractionBtn);
        subtractionButton.setOnClickListener(v -> {
            int mode = 1;
            Intent intent = new Intent(MainActivity.this, GameScreen.class);
            intent.putExtra("mode", mode);
            startActivity(intent);
        });

        Button multiplicationButton = findViewById(R.id.multiplicationBtn);
        multiplicationButton.setOnClickListener(v -> {
            int mode = 2;
            Intent intent = new Intent(MainActivity.this, GameScreen.class);
            intent.putExtra("mode", mode);
            startActivity(intent);
        });
    }
}