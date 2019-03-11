package com.pakos.lcw;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectDevice extends AppCompatActivity {


    private ViewPager viewPager;
    private LinearLayout dots;
    private SliderAdapter sliderAdapter;
    private TextView[] mydots;
    private Button next;
    private Button back;
    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_device_onboarding);

        viewPager =  findViewById(R.id.slideView);
        dots =  findViewById(R.id.relativeLayout);
        next =  findViewById(R.id.button_right);
        back =  findViewById(R.id.button_left);
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(currentPage==2){
                    Intent deviceIntent = new Intent(SelectDevice.this,DeviceList.class);
                    startActivity(deviceIntent);
                }
                else{
                    viewPager.setCurrentItem(currentPage+1);}
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(currentPage==0){
                    Intent deviceIntent = new Intent(SelectDevice.this,DeviceList.class);
                    startActivity(deviceIntent);
                }
                else{
                    viewPager.setCurrentItem(currentPage-1);
                }

            }
        });



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
                next.setEnabled(true);
                back.setEnabled(false);
                back.setVisibility(View.VISIBLE);
                next.setText("Next");
                back.setText("Skip");
            }
            else if(i==mydots.length-1){
                next.setEnabled(true);
                back.setEnabled(true);
                next.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                next.setText("Start");
                back.setText("Back");
            }
            else {
                next.setEnabled(true);
                back.setEnabled(true);
                next.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                next.setText("Next");
                back.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
