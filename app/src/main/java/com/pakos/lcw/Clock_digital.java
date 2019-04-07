package com.pakos.lcw;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextClock;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Clock_digital extends AppCompatActivity {
    TextClock textClock;
    TextView textView;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_digital);
        textClock = findViewById(R.id.textClockdig);
        textClock.setFormat24Hour("HH:mm:ss");
        textClock.setFormat12Hour(null);
        textView = findViewById(R.id.textViewdate);
        String date = new SimpleDateFormat("dd MMMM yyyy G, z",Locale.ENGLISH).format(new Date());
        textView.setText(date);
        typeface = Typeface.createFromAsset(getAssets(),"digital-7.ttf");
        textClock.setTypeface(typeface);
        textView.setTypeface(typeface);
    }
}
