package com.pakos.lcw;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] imgs = {
            R.drawable.smartwatch100,
            R.drawable.tvshow100,
            R.drawable.appsettings
    };

    public String[] heading_txt = {
            "Lorem ipsum 1",
            "Lorem ipsum 2",
            "Lorem ipsum 3"
    };

    public String[] txt = {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    };

    @Override
    public int getCount() {

        return heading_txt.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o){

        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_select_device_onboard_slide, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView heading = (TextView) view.findViewById(R.id.header);
        TextView body = (TextView) view.findViewById(R.id.main);

        imageView.setImageResource(imgs[position]);
        heading.setText(heading_txt[position]);
        body.setText(txt[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){

        container.removeView((RelativeLayout)object);

    }
}
