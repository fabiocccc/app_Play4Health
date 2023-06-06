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
import com.example.tesi.AppTravisani.StoryCard;
import com.example.tesi.R;

public class PassiE3P1Activity extends AppCompatActivity {

    private CardView passo1, passo2, passo3, passo4, passo5;
    private ImageView backIcon;
    private ImageView badgeIcon;
    private ImageView imgCardP2,imgCardP3,imgCardP4, imgCardP5;
    private TextView title_toolbar;


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

        backIcon = findViewById(R.id.back_icon);
        badgeIcon = findViewById(R.id.badge_icon);
        badgeIcon.setVisibility(View.INVISIBLE);

        //flag per capire se ha fatto già tutti i passi del primo percorso
       int flag = getIntent().getExtras().getInt("flagDo");


        //gestione memoria dell'esecuzione dei passi in diverse sessioni
        SharedPreferences sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int flagDo = sharedPref.getInt("flagDo3", 0);

        if (flagDo == 5) {
            sbloccaPassi(5);
        }

        //5-->ha fatto tutti i passi del primo episodio
        sbloccaPassi(flag);



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

        ///PASSO 2 PERCORSO 1

//        passo2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //aprire passo 2 del percorso 1
//
//            }
//        });
        ///PASSO 3 PERCORSO 1
//        passo3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //aprire passo 3 del percorso 1
//
//            }
//        });
        ///PASSO 4 PERCORSO 1
//        passo4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //aprire passo 4 del percorso 1
//
//            }
//        });

        ///PASSO 5 PERCORSO 1
//        passo5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //aprire passo 5 del percorso 1
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), P1EpisodiActivity.class);
        startActivity(i);
        finish();
    }

    private void sbloccaPassi(int flag) {

        switch (flag) {
            case 2:
                imgCardP2.setImageResource(R.drawable.ic_baseline_lock_open);
                passo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //aprire passo 2
                        Intent i = new Intent(getApplicationContext(), Passo2E3P1.class);
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
                        Intent i = new Intent(getApplicationContext(), Passo2E3P1.class);
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
                        Intent i = new Intent(getApplicationContext(), Passo3E3P1.class);
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
                        Intent i = new Intent(getApplicationContext(), Passo2E3P1.class);
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
                        Intent i = new Intent(getApplicationContext(), Passo3E3P1.class);
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
                        Intent i = new Intent(getApplicationContext(), Passo4E3P1.class);
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
                        Intent i = new Intent(getApplicationContext(), Passo2E3P1.class);
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
                        Intent i = new Intent(getApplicationContext(), Passo3E3P1.class);
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
                        Intent i = new Intent(getApplicationContext(), Passo4E3P1.class);
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
                        Intent i = new Intent(getApplicationContext(), Passo5E3P1.class);
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