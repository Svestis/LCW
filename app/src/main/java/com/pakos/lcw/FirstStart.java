package com.pakos.lcw;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FirstStart extends AppCompatActivity {


    private ViewPager viewPager;
    private LinearLayout dots;
    private TextView[] mydots;
    private Button next;
    private Button back;
    private int currentPage;
    Intent deviceIntent;
    View.OnClickListener nextClick;
    View.OnClickListener previousClick;
    View.OnClickListener intentClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_device_onboarding);
        viewPager =  findViewById(R.id.slideView);
        dots =  findViewById(R.id.relativeLayout);
        next =  findViewById(R.id.button_right);
        back =  findViewById(R.id.button_left);
        SliderAdapter sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);
        nextClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentPage+1);
            }
        };
        previousClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentPage-1);
            }
        };
        intentClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deviceIntent = new Intent(FirstStart.this,DeviceList.class);
                startActivity(deviceIntent);
                finish();
            }
        };
        next.setOnClickListener(nextClick);
        back.setOnClickListener(intentClick);
    }

    public void addDotsIndicator(int position) {
        mydots = new TextView[3];
        dots.removeAllViews();
        for(int i = 0; i<mydots.length; i++){
            mydots[i] = new TextView(this);
            mydots[i].setText(Html.fromHtml("&#8226"));
            mydots[i].setTextSize(35);
            mydots[i].setTextColor(getResources().getColor(R.color.transparent));
            dots.addView(mydots[i]);


        }

        if(mydots.length>0){
            mydots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            currentPage = i;
            if(i==0){
                next.setText(R.string.next);
                back.setText(R.string.skip);
                back.setOnClickListener(intentClick);
                next.setOnClickListener(nextClick);
            }
            else if(i==mydots.length-1){
                next.setText(R.string.start);
                back.setText(R.string.back);
                next.setOnClickListener(intentClick);
                back.setOnClickListener(previousClick);
            }
            else {
                next.setText(R.string.next);
                back.setText(R.string.back);
                next.setOnClickListener(nextClick);
                back.setOnClickListener(previousClick);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
