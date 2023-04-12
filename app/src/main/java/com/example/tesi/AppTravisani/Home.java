package com.example.tesi.AppTravisani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tesi.AppPavone.HomePrima;
import com.example.tesi.R;

public class Home extends AppCompatActivity {

    private ImageView ball, book;
    private FrameLayout button_aiuto;
    private AnimationDrawable animationDrawable1 = null;
    private AnimationDrawable animationDrawable2 = null;
    private ImageView help1, help2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ball = findViewById(R.id.ball);
        book = findViewById(R.id.book);
        button_aiuto = findViewById(R.id.button_aiuto);
        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);

        button_aiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                help1.setVisibility(View.VISIBLE);
                help2.setVisibility(View.VISIBLE);

                animationDrawable1 = (AnimationDrawable) help1.getBackground();
                animationDrawable2 = (AnimationDrawable) help2.getBackground();
                animationDrawable1.start();
                animationDrawable2.start();

                button_aiuto.setVisibility(View.GONE);

            }
        });

        //setta animazione lampeggiante
        //sulla palla
        YoYo.with(Techniques.Pulse)
                .duration(700)
                .repeat(10)
                .playOn(ball);
        //sul libro
        YoYo.with(Techniques.Pulse)
                .duration(700)
                .repeat(10)
                .playOn(book);
    }

    public void StartExercise(View view) {

        //aprire APP PAVONE
        Intent i = new Intent(getApplicationContext(), HomePrima.class);
        startActivity(i);
        finish();


    }
    public void StartStory(View view) {

        //aprire Sezione CardView
        Intent i = new Intent(getApplicationContext(), StoryCard.class);
        startActivity(i);
        finish();

    }


}