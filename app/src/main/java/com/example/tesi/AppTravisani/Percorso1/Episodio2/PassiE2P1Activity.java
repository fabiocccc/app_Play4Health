package com.example.tesi.AppTravisani.Percorso1.Episodio2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tesi.AppTravisani.Percorso1.Episodio1.Passo2E1P1;
import com.example.tesi.AppTravisani.Percorso1.Episodio1.Passo3E1P1;
import com.example.tesi.AppTravisani.Percorso1.Episodio1.Passo4E1P1;
import com.example.tesi.AppTravisani.Percorso1.P1EpisodiActivity;
import com.example.tesi.R;

public class PassiE2P1Activity extends AppCompatActivity {

    private CardView passo1, passo2, passo3, passo4, passo5;
    private ImageView backIcon;
    private ImageView badgeIcon;
    private ImageView imgCardP2, imgCardP3, imgCardP4, imgCardP5;
    private TextView title_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passi_e2_p1);

        title_toolbar = findViewById(R.id.toolbar_title);
        title_toolbar.setText("EPISODIO 2 - P1");
        title_toolbar.setTextSize(25);

        passo1 = findViewById(R.id.cardPasso1);
        passo2 = findViewById(R.id.cardPasso2);
        passo3 = findViewById(R.id.cardPasso3);
        passo4 = findViewById(R.id.cardPasso4);
        passo5 = findViewById(R.id.cardPasso5);


        imgCardP2 = findViewById(R.id.passo2img);
        imgCardP3 = findViewById(R.id.passo3img);
        imgCardP4 = findViewById(R.id.passo4img);
        imgCardP5 = findViewById(R.id.passo5img);

        backIcon = findViewById(R.id.back_icon);
        badgeIcon = findViewById(R.id.badge_icon);
        badgeIcon.setVisibility(View.INVISIBLE);

        //gestione memoria dell'esecuzione dei passi in diverse sessioni
        SharedPreferences sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int flagDo = sharedPref.getInt("flagDo2", 0);

        if (flagDo == 5) {
            sbloccaPassi(5);
        }

        //5-->ha fatto tutti i passi del secondo episodio
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
                Intent i = new Intent(getApplicationContext(), Passo1E2P1.class);
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

    //sblocca passi a seconda del flag
    private void sbloccaPassi(int flag) {

        switch (flag) {
            case 2:
                imgCardP2.setImageResource(R.drawable.ic_baseline_lock_open);
                passo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //aprire passo 2
                        Intent i = new Intent(getApplicationContext(), Passo2E2P1.class);
                        int score = getIntent().getExtras().getInt("time");
                        i.putExtra("time", score);
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
                        Intent i = new Intent(getApplicationContext(), Passo2E2P1.class);
                        int score = getIntent().getExtras().getInt("time");
                        i.putExtra("time", score);
                        startActivity(i);
                        finish();

                    }
                });
                imgCardP3.setImageResource(R.drawable.ic_baseline_lock_open);
                passo3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //aprire passo 3
                        Intent i = new Intent(getApplicationContext(), Passo3E2P1.class);
                        int score = getIntent().getExtras().getInt("time");
                        i.putExtra("time", score);
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
                        Intent i = new Intent(getApplicationContext(), Passo2E2P1.class);
                        int score = getIntent().getExtras().getInt("time");
                        i.putExtra("time", score);
                        startActivity(i);
                        finish();

                    }
                });
                imgCardP3.setImageResource(R.drawable.ic_baseline_lock_open);
                passo3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //aprire passo 3
                        Intent i = new Intent(getApplicationContext(), Passo3E2P1.class);
                        int score = getIntent().getExtras().getInt("time");
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
                        Intent i = new Intent(getApplicationContext(), Passo4E2P1.class);
                        int score = getIntent().getExtras().getInt("time");
                        i.putExtra("time", score);
                        startActivity(i);
                        finish();

                    }
                });
                break;
            case 5:
                imgCardP2.setImageResource(R.drawable.ic_baseline_lock_open);
                passo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //aprire passo 2
                        Intent i = new Intent(getApplicationContext(), Passo2E2P1.class);
                        int score = getIntent().getExtras().getInt("time");
                        i.putExtra("time", score);
                        startActivity(i);
                        finish();

                    }
                });
                imgCardP3.setImageResource(R.drawable.ic_baseline_lock_open);
                passo3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //aprire passo 3
                        Intent i = new Intent(getApplicationContext(), Passo3E2P1.class);
                        int score = getIntent().getExtras().getInt("time");
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
                        Intent i = new Intent(getApplicationContext(), Passo4E2P1.class);
                        int score = getIntent().getExtras().getInt("time");
                        i.putExtra("time", score);
                        startActivity(i);
                        finish();

                    }
                });
                imgCardP5.setImageResource(R.drawable.ic_baseline_lock_open);
                passo5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //aprire passo 5
                        Intent i = new Intent(getApplicationContext(), Passo5E2P1.class);
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
}