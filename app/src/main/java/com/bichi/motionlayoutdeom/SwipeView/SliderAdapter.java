package com.bichi.motionlayoutdeom.SwipeView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bichi.motionlayoutdeom.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private List<SlideItem> slideItems;
    private ViewPager2 pager2;

    public SliderAdapter(List<SlideItem> slideItems, ViewPager2 pager2) {
        this.slideItems = slideItems;
        this.pager2 = pager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slider_item_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, final int position) {
        holder.setImage(slideItems.get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+position);
            }
        });
        if (position == slideItems.size() -2){
            pager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return slideItems.size();
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView imageView;
        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgeSlide);
        }

        void setImage(SlideItem slideItem){
            imageView.setImageResource(slideItem.getImage());
        }
    }

    //infinite scroll
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            slideItems.addAll(slideItems);
            notifyDataSetChanged();
        }
    };
}
