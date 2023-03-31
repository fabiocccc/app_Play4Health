package com.example.tesi.AppTravisani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tesi.AppPavone.HomePrima;
import com.example.tesi.R;

public class Home extends AppCompatActivity {

    private ImageView ball, book;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ball = findViewById(R.id.ball);
        book = findViewById(R.id.book);

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
//        Intent i = new Intent(getApplicationContext(), StoryCard.class);
//        startActivity(i);
//        finish();

    }


}