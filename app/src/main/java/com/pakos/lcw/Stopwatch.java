package com.pakos.lcw;

import android.animation.LayoutTransition;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class Stopwatch extends AppCompatActivity {

    private Button startButton;
    private Button lapButton;
    private TextView textView;
    private Timer timer;
    private LinearLayout linearLayout;
    private int currentTime = 0;
    private int lapTime = 0;
    private int lapCounter = 0;
    private boolean lapViewExists;
    private boolean isButtonStartPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        startButton = findViewById(R.id.btn_start);
        lapButton = findViewById(R.id.btn_lap);
        textView = findViewById(R.id.stopwatch_view);
        textView.setTextSize(55);
    }

    public void onSWatchStart(View view) {
        if (isButtonStartPressed) {
            onSWatchStop();
        } else {
            isButtonStartPressed = true;
            startButton.setBackgroundResource(R.drawable.btn_blue_1_1);
            startButton.setText(R.string.btn_stop);
            lapButton.setBackgroundResource(R.drawable.btn_blue_1_1);
            lapButton.setText(R.string.btn_lap);
            lapButton.setEnabled(true);
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            currentTime += 1;
                            lapTime += 1;
                            textView.setText(currentTime);
                        }
                    });
                }
            }, 0, 10);
        }
    }

    public void onSWatchStop() {
        startButton.setBackgroundResource(R.drawable.btn_blue_1_1);
        startButton.setText(R.string.btn_start);
        lapButton.setEnabled(true);
        lapButton.setBackgroundResource(R.drawable.btn_blue_1_1);
        lapButton.setText(R.string.btn_reset);
        isButtonStartPressed = false;
        timer.cancel();
    }

    public void onSWatchReset() {
        timer.cancel();
        currentTime = 0;
        lapTime = 0;
        lapCounter = 0;
        textView.setText(currentTime);
        lapButton.setEnabled(false);
        lapButton.setText(R.string.btn_lap);
        lapButton.setBackgroundResource(R.drawable.btn_blue_1_1);
        if (lapViewExists) {
            linearLayout.setLayoutTransition(null);
            linearLayout.removeAllViews();
            lapViewExists = false;
        }
    }

    public void onSWatchLap(View view) {
        if (!isButtonStartPressed) {
            onSWatchReset();
        } else {
            lapViewExists = true;
            lapCounter++;
            LayoutTransition transition = new LayoutTransition();
            transition.setAnimator(LayoutTransition.CHANGE_APPEARING, null);
            transition.setStartDelay(LayoutTransition.APPEARING, 0);
            linearLayout = findViewById(R.id.layout);
            linearLayout.setLayoutTransition(transition);
            TextView lapDisplay = new TextView(this);
            ImageView imageView = new ImageView(this);
            imageView.setFocusableInTouchMode(true);
            lapDisplay.setGravity(Gravity.CENTER);
            lapDisplay.setTextSize(30);
            lapDisplay.setTextColor(Color.BLACK);
            linearLayout.addView(lapDisplay);
            linearLayout.addView(imageView);
            imageView.requestFocus();
            lapDisplay.setText(String.format("Lap %d: %s", lapCounter, TimeFormatUtil.toDisplayString(lapTime)));
            imageView.setImageResource(R.drawable.shape);
            lapTime = 0;
        }
    }
}
