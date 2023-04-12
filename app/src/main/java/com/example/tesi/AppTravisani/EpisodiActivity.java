package com.example.tesi.AppTravisani;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tesi.R;

public class EpisodiActivity extends AppCompatActivity {

    ImageView episodio1img;
    ImageView episodio2img;
    ImageView backIcon;
    ImageView badgeIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodi);

        episodio1img= findViewById(R.id.episodio1img);
        episodio2img= findViewById(R.id.episodio2img);
        backIcon = findViewById(R.id.back_icon);
        badgeIcon = findViewById(R.id.badge_icon);


        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        badgeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Si aprirà la sezione dei badge", Toast.LENGTH_SHORT).show();
            }
        });



        episodio1img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //aprire Activity STORIA 1
//                Intent i = new Intent(getApplicationContext(), Passo1.class);
//                startActivity(i);
//                finish();
            }
        });

        episodio2img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                //aprire Activity STORIA 2
//                Intent i = new Intent(getApplicationContext(), Storia2.class);
//                startActivity(i);
//                finish();
            }
        });



    }
}