package com.example.tesi.AppTravisani.Percorso1.Episodio1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.tesi.AppTravisani.Percorso1.P1EpisodiActivity;
import com.example.tesi.R;

public class PassiE1P1Activity extends AppCompatActivity {

    private CardView passo1, passo2, passo3, passo4;
    private ImageView backIcon;
    private ImageView badgeIcon;
    private ImageView imgCardP2,imgCardP3,imgCardP4;
    private TextView title_toolbar;
    private String nomePercorso;


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

        backIcon = findViewById(R.id.back_icon);
        badgeIcon = findViewById(R.id.badge_icon);
        badgeIcon.setVisibility(View.INVISIBLE);
        nomePercorso = new String();

        //gestione memoria dell'esecuzione dei passi in diverse sessioni
        SharedPreferences sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int flagDo = sharedPref.getInt("flagDo1", 0);

        if(flagDo == 4)
        {
            sbloccaPassi(4);
        }

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            nomePercorso = extras.getString("percorso1");
            System.out.println("nom1:"+nomePercorso);
        }

        //4-->ha fatto tutti i passi del primo episodio
        sbloccaPassi(flagDo);

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
                Intent i = new Intent(getApplicationContext(), Passo1E1P1.class);
                i.putExtra("percorso1", nomePercorso);
                startActivity(i);
                finish();
            }
        });


    }

    //sblocca passi a seconda del flag
    private void sbloccaPassi(int flag) {

        switch (flag)
        {
            case 2:
                imgCardP2.setImageResource(R.drawable.ic_baseline_lock_open);
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
                break;
            case 3:
                imgCardP2.setImageResource(R.drawable.ic_baseline_lock_open);
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
                imgCardP3.setImageResource(R.drawable.ic_baseline_lock_open);
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
                break;
            case 4:
                imgCardP2.setImageResource(R.drawable.ic_baseline_lock_open);
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
                imgCardP3.setImageResource(R.drawable.ic_baseline_lock_open);
                passo3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //aprire passo 3
                        Intent i = new Intent(getApplicationContext(), Passo3E1P1.class);
                        int score = getIntent().getExtras().getInt("time");
                        i.putExtra("percorso1", nomePercorso);
                        i.putExtra("time", score);
                        startActivity(i);
                        finish();

                    }
                });
                imgCardP4.setImageResource(R.drawable.ic_baseline_lock_open);
                passo4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //aprire passo 4
                        Intent i = new Intent(getApplicationContext(), Passo4E1P1.class);
                        int score = getIntent().getExtras().getInt("time");
                        i.putExtra("time", score);
                        startActivity(i);
                        finish();

                    }
                });
                break;
            default:
                break;
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