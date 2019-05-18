package com.pakos.lcw;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

public class obsolete_RecyclerAdapter extends RecyclerView.Adapter<obsolete_RecyclerAdapter.ImageViewHolder> {
    private  int[] images;
    public obsolete_RecyclerAdapter(int[] images){
        this.images = images;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image,viewGroup,false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder viewHolder, final int i) {
        final int image_id = images[i];

        viewHolder.imageButton.setImageResource(image_id);
        viewHolder.textView.setText("Image :" + i);

        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"selected item" + (i+1));
                String msg = "img"+(i+1)+".jpg";
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ImageViewHolder extends  RecyclerView.ViewHolder
    {
        ImageButton imageButton;
        TextView textView;
        public ImageViewHolder (View itemView){
            super(itemView);
            imageButton = itemView.findViewById(R.id.img);
            textView = itemView.findViewById(R.id.img_txt);
        }

    }
}
