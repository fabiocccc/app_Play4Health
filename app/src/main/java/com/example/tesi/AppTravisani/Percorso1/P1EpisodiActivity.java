package com.example.tesi.AppTravisani.Percorso1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tesi.AppTravisani.Percorso1.Episodio1.PassiE1P1Activity;
import com.example.tesi.AppTravisani.Percorso1.Episodio1.Passo1E1P1;
import com.example.tesi.AppTravisani.Percorso1.Episodio2.PassiE2P1Activity;
import com.example.tesi.AppTravisani.Percorso1.Episodio3.PassiE3P1Activity;
import com.example.tesi.AppTravisani.StoryCard;
import com.example.tesi.R;

public class P1EpisodiActivity extends AppCompatActivity {

    private CardView cv1, cv2 , cv3;

    private ImageView backIcon;
    private ImageView badgeIcon;
    private TextView title_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p1_episodi);

        title_toolbar= findViewById(R.id.toolbar_title);
        badgeIcon= findViewById(R.id.badge_icon);
        backIcon = findViewById(R.id.back_icon);


        badgeIcon.setVisibility(View.GONE);
        title_toolbar.setText("EPISODI Percorso 1");
        title_toolbar.setTextSize(25);


        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        cv1 = findViewById(R.id.cv1);
        cv2 = findViewById(R.id.cv2);
        cv3 = findViewById(R.id.cv3);


        //gestione memoria dell'esecuzione dei passi in diverse sessioni
        SharedPreferences sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int flagDo = sharedPref.getInt("flagDo", 0);


        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //aprire passi dell'episodio 1 del percorso 1
                Intent i = new Intent(getApplicationContext(), PassiE1P1Activity.class);
                i.putExtra("flagDo",flagDo);
                startActivity(i);
                finish();
            }
        });

        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //aprire passi dell'episodio 2 del percorso 1
                Intent i = new Intent(getApplicationContext(), PassiE2P1Activity.class);
                i.putExtra("flagDo",flagDo);
                startActivity(i);
                finish();


            }
        });

        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //aprire passi dell'episodio 3 del percorso 1
                Intent i = new Intent(getApplicationContext(), PassiE3P1Activity.class);
                i.putExtra("flagDo",flagDo);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), StoryCard.class);
        startActivity(i);
        finish();
    }
}