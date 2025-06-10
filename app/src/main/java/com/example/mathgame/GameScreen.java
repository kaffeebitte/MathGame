package com.example.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class GameScreen extends AppCompatActivity {
    private final int[] scoreCounter = {0};
    private final int[] livesCounter = {5};
    private int timeLeft = 30;
    private TextView timerTextView;
    private TextView questionTextView;
    private EditText answerEditText;
    private TextView scoreCounterTextView;
    private  TextView livesCounterTextView;
    private CountDownTimer timer;
    private final Random random = new Random();
    private int correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addition_screen);

        int mode = getIntent().getIntExtra("mode", 0);
        questionTextView = findViewById(R.id.questionTextView);
        answerEditText = findViewById(R.id.answerEditText);
        timerTextView = findViewById(R.id.timeValueTextView);
        scoreCounterTextView = findViewById(R.id.scoreValueTextView);
        livesCounterTextView = findViewById(R.id.lifeValueTextView);
        Button checkButton = findViewById(R.id.checkBtn);
        Button nextButton = findViewById(R.id.nextBtn);

        scoreCounterTextView.setText(String.valueOf(scoreCounter[0]));
        livesCounterTextView.setText(String.valueOf(livesCounter[0]));

        Intent resultIntent = new Intent(GameScreen.this, ResultScreen.class);

        generateNewQuestion(mode);

        startTimer();

        checkButton.setOnClickListener(v -> {
            if (answerEditText.getText().toString().isEmpty()){
                Toast.makeText(GameScreen.this, "Please enter an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            checkAnswer();

            if (livesCounter[0] <= 0) {
                endGame(resultIntent);
            } else {
                generateNewQuestion(mode);
            }
        });

        nextButton.setOnClickListener(v -> generateNewQuestion(mode));
    }

    private void generateNewQuestion(int mode) {
        int num1 = random.nextInt(100);
        int num2 = random.nextInt(100);
        String question;

        switch (mode) {
            case 0:
                question = num1 + " + " + num2 + " = ?";
                correctAnswer = num1 + num2;
                break;
            case 1:
                if (num1 < num2) {
                    int temp = num1;
                    num1 = num2;
                    num2 = temp;
                }
                question = num1 + " - " + num2 + " = ?";
                correctAnswer = num1 - num2;
                break;
            case 2:
                num1 = random.nextInt(10);
                num2 = random.nextInt(10);
                question = num1 + " x " + num2 + " = ?";
                correctAnswer = num1 * num2;
                break;
            default:
                throw new IllegalArgumentException("Invalid mode: " + mode);
        }
        questionTextView.setText(question);
        answerEditText.setText("");
    }

    private void checkAnswer() {
        int userAnswer;
        try {
            userAnswer = Integer.parseInt(answerEditText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userAnswer == correctAnswer) {
            scoreCounter[0] += 1;
            scoreCounterTextView.setText(String.valueOf(scoreCounter[0]));
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            livesCounter[0] -= 1;
            livesCounterTextView.setText(String.valueOf(livesCounter[0]));
            Toast.makeText(this, "Incorrect! The correct answer was " + correctAnswer, Toast.LENGTH_SHORT).show();
        }
    }

    private void startTimer() {
        timer = new CountDownTimer(timeLeft * 1000L, 1000) {
            @Override
            public void onTick(long millisUnitFinished) {
                timeLeft = (int) (millisUnitFinished / 1000);
                timerTextView.setText(String.valueOf(timeLeft));
            }

            @Override
            public void onFinish() {
                Intent resultIntent = new Intent(GameScreen.this, ResultScreen.class);
                endGame(resultIntent);
            }
        }.start();
    }

    private void endGame(Intent resultIntent) {
        if (timer != null) {
            timer.cancel();
        }
        resultIntent.putExtra("mode", getIntent().getIntExtra("mode", 0));
        resultIntent.putExtra("score", scoreCounter[0]);
        startActivity(resultIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}