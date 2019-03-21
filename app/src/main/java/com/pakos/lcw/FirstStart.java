package com.pakos.lcw;
//COMPLETED: DONE customize alert dialog appearance for skip into
//COMPLETED: DONE button appearance on alert dialog for skip
//COMPLETED: make the intend open only on first load
//todo: change button appearance on alert dialog for quit
//todo: customize alert dialog appearance for quit app
//todo: change button appearance on click
//todo: getting things ready intent
//REMOVED: entirely close app on back
//COMPLETED: fixing onbackpressed for skip View
//COMPLETED: fix deprecated html + get color
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v4.text.HtmlCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FirstStart extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean firstTimeOpen;
    private ViewPager viewPager;
    private LinearLayout dots;
    private TextView[] mydots;
    private Button next;
    private Button back;
    private int currentPage;
    View.OnClickListener nextClick;
    View.OnClickListener previousClick;
    View.OnClickListener intentClick;
    View.OnClickListener skipClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        firstTimeOpen = sharedPreferences.getBoolean("firstTimeOpen",true);
        if(firstTimeOpen==false){
            startActivity(new Intent(FirstStart.this,DeviceList.class));
            finish();
        }
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
                SharedPreferences.Editor editor = sharedPreferences.edit();
                firstTimeOpen = false;
                editor.putBoolean("firstTimeOpen", firstTimeOpen);
                editor.apply();
                startActivity(new Intent(FirstStart.this,DeviceList.class));
                finish();
            }
        };

        skipClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(FirstStart.this);
                final View customAlert = getLayoutInflater().inflate(R.layout.alert_dialog_skip_firststart, null);
                builder.setCancelable(false);
                Button skip = customAlert.findViewById(R.id.cancel_btn);
                Button doskip = customAlert.findViewById(R.id.yes_btn);
                builder.setView(customAlert);
                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(true);
                skip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                doskip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        firstTimeOpen = false;
                        editor.putBoolean("firstTimeOpen", firstTimeOpen);
                        editor.apply();
                        startActivity(new Intent(FirstStart.this,DeviceList.class));
                        finish();
                    }
                });
            }
        };
        back.setOnClickListener(skipClick);
        next.setOnClickListener(nextClick);   }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FirstStart.this);
        builder.setCancelable(false);
        builder.setTitle("Are you sure you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
    }

    public void addDotsIndicator(int position) {
        mydots = new TextView[3];
        dots.removeAllViews();
        for(int i = 0; i<mydots.length; i++){
            mydots[i] = new TextView(this);
            mydots[i].setText(HtmlCompat.fromHtml("&#8226",HtmlCompat.FROM_HTML_MODE_LEGACY));
            mydots[i].setTextSize(35);
            int colorchange = ContextCompat.getColor(this,R.color.transparent);
            mydots[i].setTextColor(colorchange);
            dots.addView(mydots[i]);


        }

        if(mydots.length>0){
            int color = ContextCompat.getColor(this, R.color.white);
            mydots[position].setTextColor(color);
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
                back.setOnClickListener(skipClick);
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
