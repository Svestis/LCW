package com.pakos.lcw;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    private Context context;

    SliderAdapter(Context context){
        this.context = context;
    }

    private int[] imgs = {
            R.drawable.smartwatch100,
            R.drawable.tvshow100,
            R.drawable.appsettings
    };

    private String[] heading_txt = {
            "Lorem ipsum 1",
            "Lorem ipsum 2",
            "Lorem ipsum 3"
    };

    private String[] txt = {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    };

    @Override
    public int getCount() {

        return heading_txt.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o){

        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_select_device_onboard_slide, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView heading = view.findViewById(R.id.header);
        TextView body = view.findViewById(R.id.main);

        imageView.setImageResource(imgs[position]);
        heading.setText(heading_txt[position]);
        body.setText(txt[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){

        container.removeView((RelativeLayout)object);

    }
}
