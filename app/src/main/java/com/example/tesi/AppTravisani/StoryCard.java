package com.example.tesi.AppTravisani;

import android.animation.ArgbEvaluator;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.tesi.R;

import java.util.ArrayList;
import java.util.List;

public class StoryCard extends AppCompatActivity {

    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator(); //animazione

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_card);

        models = new ArrayList<>();
        models.add(new Model(R.drawable.storyportiere, "STORIA 1", "TED IL PORTIERE"));
        models.add(new Model(R.drawable.ic_baseline_lock, "STORIA 2", "titolo 2"));
        models.add(new Model(R.drawable.ic_baseline_lock, "STORIA 3", "titolo 3"));
        models.add(new Model(R.drawable.ic_baseline_lock, "STORIA 4", "titolo 4"));

        adapter = new Adapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,0,130,0);

        //i colori dello sfondo che si alternano quando le card scorrono
        Integer[] colors_temp = {
                getResources().getColor(R.color.teal_100),
                getResources().getColor(R.color.teal_300),
                getResources().getColor(R.color.verdepistacchio),
                getResources().getColor(R.color.teal_400),
        };

        colors = colors_temp;


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(position < (adapter.getCount()-1) && position < (colors.length - 1) )
                { viewPager.setBackgroundColor(
                        (Integer) argbEvaluator.evaluate(
                                positionOffset,
                                colors[position],
                                colors[position+1]));}
                else{
                    viewPager.setBackgroundColor(colors[colors.length-1]);
                }
            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}