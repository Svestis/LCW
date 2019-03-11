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
            "Thank you for purchasing Pectus\u2122 ,\nthe new generation wearable.\nLets get started with the basics!",
            "You are almost there. You will just need to pair your device and you are all set!\nThe app will guide you through.",
            "Simply click next and explore the \u221e options. You can always come back here through the app settings."};

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
