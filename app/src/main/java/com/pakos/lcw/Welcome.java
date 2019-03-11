package com.pakos.lcw;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        MediaPlayer welcomeSound = MediaPlayer.create(this,R.raw.welcomesound);
        TextView welcomeText = findViewById(R.id.welcomeText);
        TextView welcomeTextSub = findViewById(R.id.welcomeText2);
        ImageView welcomeImage = findViewById(R.id.welcomeImage);
        Animation welcomeAnim = AnimationUtils.loadAnimation(this,R.anim.welcometransition);
        welcomeSound.start();
        welcomeText.startAnimation(welcomeAnim);
        welcomeTextSub.startAnimation(welcomeAnim);
        welcomeImage.startAnimation(welcomeAnim);

        int SPLASH_TIME_OUT = 4000;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent deviceIntent = new Intent(Welcome.this, FirstStart.class);
                startActivity(deviceIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
