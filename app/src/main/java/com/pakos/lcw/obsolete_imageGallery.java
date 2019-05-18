package com.pakos.lcw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class obsolete_imageGallery extends AppCompatActivity {

    RecyclerView recyclerView;
    private int[] images = {R.drawable.img1,R.drawable.img2,R.drawable.img3,
            R.drawable.img4,R.drawable.img5,R.drawable.img6,R.drawable.img7,
            R.drawable.img8,R.drawable.img9,R.drawable.img10};
    private RecyclerView.LayoutManager layoutManager;
    private obsolete_RecyclerAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);
        recyclerView = findViewById(R.id.imagesView);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new obsolete_RecyclerAdapter(images);
        recyclerView.setAdapter(recyclerAdapter);

    }
}
