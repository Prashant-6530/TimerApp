package com.example.timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView timerText;
    private ImageView timerImage;
    private SeekBar timerSeekbar;
    private CountDownTimer countDownTimer;
    private Button timerButton;
    boolean counterIsActive = false;

    public void updateText(int timeLeft){
        int minutes = timeLeft/60;
        int secondsLeft = timeLeft - (minutes*60);
        if(minutes < 10 && secondsLeft < 10){
            timerText.setText("0" +minutes + ":0" +secondsLeft);
        }else if(minutes < 10){
            timerText.setText("0" +minutes + ":" +secondsLeft);
        }
        else if(secondsLeft < 10){
            timerText.setText(minutes + ":0" +secondsLeft);
        }
        else{
            timerText.setText(minutes + ":" +secondsLeft);
        }
    }

    public void resetTimer(){
        timerSeekbar.setEnabled(true);
        countDownTimer.cancel();
        timerSeekbar.setProgress(30);
        timerText.setText("00:30");
        timerButton.setText("Start");
        counterIsActive = false;
    }

    public void clickButton(View view) {
        if (counterIsActive) {
            resetTimer();
        } else {
            timerButton.setText("Stop");
            counterIsActive = true;
            timerSeekbar.setEnabled(false);
            countDownTimer = new CountDownTimer(timerSeekbar.getProgress() * 1000 + 300, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateText((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    Toast.makeText(MainActivity.this, "Time Up !", Toast.LENGTH_SHORT).show();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.timeup);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerImage = findViewById(R.id.timerImageView);
        timerText = findViewById(R.id.timerTextView);
        timerSeekbar = findViewById(R.id.timerSeekBar);
        timerButton = findViewById(R.id.timerButton);

        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}