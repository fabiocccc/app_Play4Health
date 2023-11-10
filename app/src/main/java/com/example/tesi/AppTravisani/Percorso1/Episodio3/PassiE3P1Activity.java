package com.example.tesi.AppTravisani.Percorso1.Episodio3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tesi.AppTravisani.Percorso1.Episodio2.Passo1E2P1;
import com.example.tesi.AppTravisani.Percorso1.Episodio2.Passo2E2P1;
import com.example.tesi.AppTravisani.Percorso1.Episodio2.Passo3E2P1;
import com.example.tesi.AppTravisani.Percorso1.Episodio2.Passo4E2P1;
import com.example.tesi.AppTravisani.Percorso1.Episodio2.Passo5E2P1;
import com.example.tesi.AppTravisani.Percorso1.P1EpisodiActivity;
import com.example.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class PassiE3P1Activity extends AppCompatActivity {

    private CardView passo1, passo2, passo3, passo4, passo5;
    private ImageView backIcon;
    private ImageView badgeIcon;
    private ImageView imgCardP2,imgCardP3,imgCardP4, imgCardP5, imgCardP2Palla, imgCardP3Palla, imgCardP4Palla, imgCardP5Palla;
    private TextView title_toolbar;

    DatabaseReference dr;
    private String key;
    private FirebaseUser user;
    private String nomeUtente;
    private String nomePercorso;
    private int numeroEpisodi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passi_e3_p1);

        title_toolbar= findViewById(R.id.toolbar_title);
        title_toolbar.setText("EPISODIO 3 - P1");
        title_toolbar.setTextSize(25);

        passo1 = findViewById(R.id.cardPasso1);
        passo2 = findViewById(R.id.cardPasso2);
        passo3 = findViewById(R.id.cardPasso3);
        passo4 = findViewById(R.id.cardPasso4);
        passo5 = findViewById(R.id.cardPasso5);


        imgCardP2= findViewById(R.id.passo2img);
        imgCardP3= findViewById(R.id.passo3img);
        imgCardP4= findViewById(R.id.passo4img);
        imgCardP5= findViewById(R.id.passo5img);

        imgCardP2Palla= findViewById(R.id.passo2imgPalla);
        imgCardP3Palla= findViewById(R.id.passo3imgPalla);
        imgCardP4Palla= findViewById(R.id.passo4imgPalla);
        imgCardP5Palla= findViewById(R.id.passo5imgPalla);

        backIcon = findViewById(R.id.back_icon);
        badgeIcon = findViewById(R.id.badge_icon);
        badgeIcon.setVisibility(View.INVISIBLE);

        imgCardP2.setVisibility(View.INVISIBLE);
        imgCardP3.setVisibility(View.INVISIBLE);
        imgCardP4.setVisibility(View.INVISIBLE);
        imgCardP5.setVisibility(View.INVISIBLE);
        imgCardP2Palla.setVisibility(View.INVISIBLE);
        imgCardP3Palla.setVisibility(View.INVISIBLE);
        imgCardP4Palla.setVisibility(View.INVISIBLE);
        imgCardP5Palla.setVisibility(View.INVISIBLE);

        nomePercorso = new String();
        key = new String();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null) {

            String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            nomeUtente =  mailLogged.replace("@gmail.com", "");
            Bundle extras = getIntent().getExtras();

            if(extras != null) {
                nomePercorso = extras.getString("percorso3");
                numeroEpisodi = extras.getInt("percorso3Fatto");
                key = extras.getString("keyUser");

            sbloccaPassi(numeroEpisodi);

            ///PASSO 1 PERCORSO 1
            passo1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //aprire passo 1 del percorso 1
                    Intent i = new Intent(getApplicationContext(), Passo1E3P1.class);

                    nomePercorso = "il gioco del calcio";
                    i.putExtra("percorso3Fatto",numeroEpisodi);
                    i.putExtra("percorso3", nomePercorso);
                    i.putExtra("keyUser",key);
                    startActivity(i);
                    finish();


                }
            });

            }
        }
        else if(user == null ){

            sbloccaPassi(0);

            ///PASSO 1 PERCORSO 1
            passo1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //aprire passo 1 del percorso 1
                    Intent i = new Intent(getApplicationContext(), Passo1E3P1.class);
                    startActivity(i);
                    finish();
                }
            });

        }


        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), P1EpisodiActivity.class);
                startActivity(i);
                finish();

            }
        });


        ///PASSO 1 PERCORSO 1
        passo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //aprire passo 1 del percorso 1
                Intent i = new Intent(getApplicationContext(), Passo1E3P1.class);
                startActivity(i);
                finish();
            }
        });

    }

    //gestione pulsante back android
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), P1EpisodiActivity.class);
        startActivity(i);
        finish();
    }

    private void sbloccaPassi(int flag) {
        if (flag == 1)
        {

            imgCardP2Palla.setVisibility(View.VISIBLE);
            imgCardP2.setVisibility(View.GONE);

            passo2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //aprire passo 2
                    Intent i = new Intent(getApplicationContext(), Passo2E3P1.class);
                    int score = getIntent().getExtras().getInt("time");
                    i.putExtra("time", score);
                    i.putExtra("percorso1", nomePercorso);
                    startActivity(i);
                    finish();

                }
            });


            imgCardP3Palla.setVisibility(View.VISIBLE);
            imgCardP3.setVisibility(View.GONE);
            passo3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //aprire passo 3
                    Intent i = new Intent(getApplicationContext(), Passo3E3P1.class);
                    int score = getIntent().getExtras().getInt("time");
                    i.putExtra("time", score);
                    i.putExtra("percorso1", nomePercorso);
                    startActivity(i);
                    finish();

                }
            });



            imgCardP4Palla.setVisibility(View.VISIBLE);
            imgCardP4.setVisibility(View.GONE);
            passo4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //aprire passo 4
                    Intent i = new Intent(getApplicationContext(), Passo4E3P1.class);
                    int score = getIntent().getExtras().getInt("time");
                    i.putExtra("time", score);
                    i.putExtra("percorso1", nomePercorso);
                    startActivity(i);
                    finish();

                }
            });

            imgCardP5Palla.setVisibility(View.VISIBLE);
            imgCardP5.setVisibility(View.GONE);
            passo5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //aprire passo 4
                    Intent i = new Intent(getApplicationContext(), Passo5E3P1.class);
                    int score = getIntent().getExtras().getInt("time");
                    i.putExtra("time", score);
                    i.putExtra("percorso1", nomePercorso);
                    startActivity(i);
                    finish();

                }
            });

        }
        else if (flag == 0) {

            imgCardP2Palla.setVisibility(View.GONE);
            imgCardP2.setVisibility(View.VISIBLE);

            imgCardP3Palla.setVisibility(View.GONE);
            imgCardP3.setVisibility(View.VISIBLE);

            imgCardP4Palla.setVisibility(View.GONE);
            imgCardP4.setVisibility(View.VISIBLE);

            imgCardP5Palla.setVisibility(View.GONE);
            imgCardP5.setVisibility(View.VISIBLE);

        }


    }
}