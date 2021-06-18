package com.example.brainteaser;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.Random;


public class MainActivity extends AppCompatActivity {
    TextView ansText;

    int optionState[] = new int[4];
    String operatorArr[] = {"+", "-", "%", "/", "*"};
    int answer, currentScore, maxScore = 0;
    GridLayout gridLayout;
    Button playAgainBtn;
    Button goButton;
    LinearLayout horizontalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horizontalLayout = findViewById(R.id.horizontalView);
        horizontalLayout.setVisibility(View.INVISIBLE);

        ansText = findViewById(R.id.ansText);
        ansText.setVisibility(View.INVISIBLE);

        gridLayout = findViewById(R.id.gridLayout);
        gridLayout.setVisibility(View.INVISIBLE);

        playAgainBtn = findViewById(R.id.playAgainId);
        playAgainBtn.setVisibility(View.INVISIBLE);

        goButton = findViewById(R.id.goButton);
        goButton.setVisibility(View.VISIBLE);
    }

    public void startGame(View view) {
        goButton.setVisibility(View.INVISIBLE);
        horizontalLayout.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);
        setNewQuestion();
        startTimer();
    }

    private void startTimer() {
        TextView timerText = findViewById(R.id.timerText);

        //start Counter
        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int timeLeft = (int) (millisUntilFinished / 1000);
                timerText.setText(timeLeft + "s");
            }

            @Override
            public void onFinish() {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.air_horn);
                mediaPlayer.start();
                gridLayout.setEnabled(false);
                ansText.setText("Done!");
                playAgainBtn.setVisibility(View.VISIBLE);
                playAgainBtn.setText("Play Again!");
            }
        }.start();
    }

    private void setOptions() {
        Random rand = new Random();
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button options = (Button) gridLayout.getChildAt(i);
            int randVal = rand.nextInt(1000);
            optionState[i] = randVal;
            options.setText(String.valueOf(randVal));
        }
        int optionIndex = rand.nextInt(gridLayout.getChildCount());
        Button correctOption = (Button) gridLayout.getChildAt(optionIndex);
        correctOption.setText(String.valueOf(answer));
        optionState[optionIndex] = answer;
    }

    private void setNewQuestion() {
        TextView quesText = findViewById(R.id.quesText);
        Random rand = new Random();
        int firstNum = rand.nextInt(100);
        int secondNum = rand.nextInt(100);
        String operator = getRandomOperator();
        calculateAnswer(firstNum, secondNum, operator);
        quesText.setText("" + firstNum + operator + secondNum);
        setOptions();
    }

    private void calculateAnswer(int firstNum, int secondNum, String operator) {
        switch (operator) {
            case "+":
                answer = firstNum + secondNum;
                break;
            case "-":
                answer = firstNum - secondNum;
                break;
            case "/":
                answer = firstNum / secondNum;
                break;
            case "*":
                answer = firstNum * secondNum;
                break;
            case "%":
                answer = firstNum % secondNum;
                break;
            default:
                answer = 0;
        }
    }

    private String getRandomOperator() {
        Random rand = new Random();
        return operatorArr[rand.nextInt(operatorArr.length)];
    }

    public void optionClicked(View view) {
        maxScore = maxScore + 1;
        Button counter = (Button) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        ansText.setVisibility(View.VISIBLE);
        TextView scoreText = findViewById(R.id.scoreText);
        if (optionState[tappedCounter] == answer) {
            currentScore = currentScore + 1;
            ansText.setText("Correct!");
        } else {
            ansText.setText("Wrong :(");
        }
        scoreText.setText(currentScore + "/" + maxScore);
        setNewQuestion();
    }

    public void playAgain(View view) {
        ansText.setVisibility(View.INVISIBLE);
        playAgainBtn.setVisibility(View.INVISIBLE);
        currentScore = 0;
        maxScore = 0;
        answer = 0;
        gridLayout.setEnabled(true);
        setNewQuestion();
        startTimer();
    }

}