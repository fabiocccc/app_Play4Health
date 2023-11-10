package com.example.tesi.AppTravisani.Percorso1.Episodio1;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.tesi.AppTravisani.CustomPremiAdapter;
import com.example.tesi.AppTravisani.ListaPremi;
import com.example.tesi.AppTravisani.Percorso1.P1EpisodiActivity;
import com.example.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PassiE1P1Activity extends AppCompatActivity {

    private CardView passo1, passo2, passo3, passo4;
    private ImageView backIcon;
    private ImageView badgeIcon;
    private ImageView imgCardP2,imgCardP3,imgCardP4,imgCardP2Calcio,imgCardP3Calcio,imgCardP4Calcio;
    private TextView title_toolbar;
    private String nomePercorso;
    private int numeroEpisodi = 0;

    DatabaseReference dr;
    private String key;
    private FirebaseUser user;
    private String nomeUtente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passi_e1_p1);

        title_toolbar= findViewById(R.id.toolbar_title);
        title_toolbar.setText("EPISODIO 1 - P1");
        title_toolbar.setTextSize(25);

        passo1 = findViewById(R.id.cardPasso1);
        passo2 = findViewById(R.id.cardPasso2);
        passo3 = findViewById(R.id.cardPasso3);
        passo4 = findViewById(R.id.cardPasso4);

        imgCardP2= findViewById(R.id.passo2img);
        imgCardP3= findViewById(R.id.passo3img);
        imgCardP4= findViewById(R.id.passo4img);

        imgCardP2Calcio= findViewById(R.id.passo2imgCalcio);
        imgCardP3Calcio= findViewById(R.id.passo3imgCalcio);
        imgCardP4Calcio= findViewById(R.id.passo4imgCalcio);

        backIcon = findViewById(R.id.back_icon);
        badgeIcon = findViewById(R.id.badge_icon);
        badgeIcon.setVisibility(View.INVISIBLE);

        imgCardP2.setVisibility(View.INVISIBLE);
        imgCardP3.setVisibility(View.INVISIBLE);
        imgCardP4.setVisibility(View.INVISIBLE);
        imgCardP2Calcio.setVisibility(View.INVISIBLE);
        imgCardP3Calcio.setVisibility(View.INVISIBLE);
        imgCardP4Calcio.setVisibility(View.INVISIBLE);

        nomePercorso = new String();
        key = new String();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null) {

            String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            nomeUtente =  mailLogged.replace("@gmail.com", "");
            Bundle extras = getIntent().getExtras();

            if(extras != null) {
                nomePercorso = extras.getString("percorso1");
                numeroEpisodi = extras.getInt("percorso1Fatto");
                key = extras.getString("keyUser");
            }
                sbloccaPassi(numeroEpisodi);

            ///PASSO 1 PERCORSO 1
            passo1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //aprire passo 1 del percorso 1
                    Intent i = new Intent(getApplicationContext(), Passo1E1P1.class);

                    nomePercorso = "il gioco del calcio";
                    i.putExtra("percorso1Fatto",numeroEpisodi);
                    i.putExtra("percorso1", nomePercorso);
                    i.putExtra("keyUser",key);
                    startActivity(i);
                    finish();


                }
            });

        }
        else if(user == null ){

            sbloccaPassi(0);

            ///PASSO 1 PERCORSO 1
            passo1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //aprire passo 1 del percorso 1
                    Intent i = new Intent(getApplicationContext(), Passo1E1P1.class);

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

    }

    //sblocca passi a seconda del flag
    private void sbloccaPassi(int flag) {
        if (flag == 1)
        {

            imgCardP2Calcio.setVisibility(View.VISIBLE);
            imgCardP2.setVisibility(View.GONE);

                passo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //aprire passo 2
                        Intent i = new Intent(getApplicationContext(), Passo2E1P1.class);
                        int score = getIntent().getExtras().getInt("time");
                        i.putExtra("time", score);
                        i.putExtra("percorso1", nomePercorso);
                        startActivity(i);
                        finish();

                    }
                });


            imgCardP3Calcio.setVisibility(View.VISIBLE);
            imgCardP3.setVisibility(View.GONE);
                passo3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //aprire passo 3
                        Intent i = new Intent(getApplicationContext(), Passo3E1P1.class);
                        int score = getIntent().getExtras().getInt("time");
                        i.putExtra("time", score);
                        i.putExtra("percorso1", nomePercorso);
                        startActivity(i);
                        finish();

                    }
                });



            imgCardP4Calcio.setVisibility(View.VISIBLE);
            imgCardP4.setVisibility(View.GONE);
                passo4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //aprire passo 4
                        Intent i = new Intent(getApplicationContext(), Passo4E1P1.class);
                        int score = getIntent().getExtras().getInt("time");
                        i.putExtra("time", score);
                        i.putExtra("percorso1", nomePercorso);
                        startActivity(i);
                        finish();

                    }
                });

        }
        else if (flag == 0) {

            imgCardP2Calcio.setVisibility(View.GONE);
            imgCardP2.setVisibility(View.VISIBLE);

            imgCardP3Calcio.setVisibility(View.GONE);
            imgCardP3.setVisibility(View.VISIBLE);

            imgCardP4Calcio.setVisibility(View.GONE);
            imgCardP4.setVisibility(View.VISIBLE);

        }


    }

    //gestione pulsante back android

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), P1EpisodiActivity.class);
        startActivity(i);
        finish();
    }
}