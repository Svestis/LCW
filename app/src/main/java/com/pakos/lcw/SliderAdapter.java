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
            R.drawable.shopping,
            R.drawable.optionset,
            R.drawable.start
    };

    private String[] heading_txt = {
            "Welcome!",
            "Now what?",
            "How?"
    };

    private String[] txt = {
            "\nThank you for purchasing Pectus\u2122,\nthe new generation wearable.\n\nLets get started!",
            "\nYou will just need to pair your device and you are all set!\n\nThe app will guide you through.",
            "Simply click next and explore the \u221e options!"};

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
