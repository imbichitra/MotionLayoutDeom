package com.bichi.motionlayoutdeom.SwipeView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.bichi.motionlayoutdeom.R;

import java.util.ArrayList;
import java.util.List;

public class SwipeView2 extends AppCompatActivity {

    private ViewPager2 viewPager2;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private Handler slideHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_view2);

        viewPager2 = findViewById(R.id.viewPagerImageSlider);
        List<SlideItem> slideItems = new ArrayList<>();

        slideItems.add(new SlideItem(R.drawable.brochure));
        slideItems.add(new SlideItem(R.drawable.sticker));
        slideItems.add(new SlideItem(R.drawable.poster));
        slideItems.add(new SlideItem(R.drawable.namecard));

        Integer[] colors_temp = {
                ContextCompat.getColor(this,R.color.color1),
                ContextCompat.getColor(this,R.color.color2),
                ContextCompat.getColor(this,R.color.color3),
                ContextCompat.getColor(this,R.color.color4),
        };

        colors = colors_temp;

        final SliderAdapter adapter = new SliderAdapter(slideItems,viewPager2);
        viewPager2.setAdapter(adapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r =1-Math.abs(position);
                page.setScaleY(0.85f + r*0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getItemCount() -1) && position < (colors.length - 1)) {
                    viewPager2.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }

                else {
                    //viewPager2.setBackgroundColor(colors[colors.length - 1]);
                    viewPager2.setBackgroundColor(colors[position%4]);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(sliderRunnable);
                slideHandler.postDelayed(sliderRunnable,2000);//slide duration 2second
            }
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() +1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable,2000);
    }
}