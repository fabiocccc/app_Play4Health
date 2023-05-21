package com.example.tesi.AppTravisani;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.tesi.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StoryCard extends AppCompatActivity {

    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator(); //animazione

    ImageView backIcon;
    ImageView badgeIcon;
    TextView title_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_card);

        backIcon = findViewById(R.id.back_icon);
        badgeIcon = findViewById(R.id.badge_icon);
        title_toolbar= findViewById(R.id.toolbar_title);

        title_toolbar.setText("La Storia");

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        badgeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Si aprirà la sezione dei badge", Toast.LENGTH_SHORT).show();
                //Aprire activity con la lista dei premi
                Intent i = new Intent(getApplicationContext(), ListaPremi.class);
                startActivity(i);
                finish();

            }
        });



        models = new ArrayList<>();
        models.add(new Model(R.drawable.startgame, "PERCORSO 1", "INIZIAMO A GIOCARE"));
        models.add(new Model(R.drawable.ic_baseline_lock, "PERCORSO 2", "TITOLO P2"));
        models.add(new Model(R.drawable.ic_baseline_lock, "PERCORSO 3", "TITOLO P3"));
        models.add(new Model(R.drawable.ic_baseline_lock, "PERCORSO 4", "TITOLO P4"));

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

    @Override
    public void onBackPressed() {
        //aprire Home
        Intent i = new Intent(getApplicationContext(), Home.class);
        startActivity(i);
        finish();
    }
}