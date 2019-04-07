package com.pakos.lcw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.darwindeveloper.horizontalscrollmenulibrary.custom_views.HorizontalScrollMenuView;
import com.darwindeveloper.horizontalscrollmenulibrary.extras.MenuItem;

public class Functions extends AppCompatActivity {

    HorizontalScrollMenuView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functions);
        menu = findViewById(R.id.horizontal_cat);
        initMenu();


    }

    private void initMenu() {
        menu.addItem("Clock", R.drawable.ic_clock_white_18dp);
        menu.addItem("Audio",R.drawable.ic_audio_white_18dp);
        menu.addItem("Images", R.drawable.ic_image_white_18dp);
        menu.addItem("Settings",R.drawable.settings);

        menu.setOnHSMenuClickListener(new HorizontalScrollMenuView.OnHSMenuClickListener() {
            @Override
            public void onHSMClick(MenuItem menuItem, int position) {
                Toast.makeText(Functions.this,""+menuItem.getText(),Toast.LENGTH_LONG);
            }
        });
    }
}
