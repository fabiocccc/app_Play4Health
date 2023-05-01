package com.example.tesi.AppTravisani.P1Passi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.tesi.R;

public class PassiP1Activity extends AppCompatActivity {

    private CardView passo1, passo2, passo3, passo4, passo5, passo6;
    private ImageView backIcon;
    private ImageView badgeIcon;
    private ImageView imgCardP2,imgCardP3,imgCardP4;
    private TextView title_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passi_p1);

        title_toolbar= findViewById(R.id.toolbar_title);
        title_toolbar.setText("PERCORSO 1");

        passo1 = findViewById(R.id.cardDolci);
        passo2 = findViewById(R.id.cardCarne);
        passo3 = findViewById(R.id.cardPasso3);
        passo4 = findViewById(R.id.cardPasso4);
//        passo5 = findViewById(R.id.cardPasso5);
//        passo6 = findViewById(R.id.cardPasso6);

        imgCardP2= findViewById(R.id.passo2img);
        imgCardP3= findViewById(R.id.passo3img);
        imgCardP4= findViewById(R.id.passo4img);

        backIcon = findViewById(R.id.back_icon);
        badgeIcon = findViewById(R.id.badge_icon);
        badgeIcon.setVisibility(View.INVISIBLE);

//        //flag per capire se ha fatto già tutti i passi del primo percorso
//        int flag = getIntent().getExtras().getInt("flagDo");
//
//        //6-->ha fatto i passi del primo percorso
//        if(flag == 4)
//        {
//            imgCardP2.setImageResource(R.drawable.ic_baseline_lock_open);
//            imgCardP3.setImageResource(R.drawable.ic_baseline_lock_open);
//            imgCardP4.setImageResource(R.drawable.ic_baseline_lock_open);
//        }



        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




       ///PASSO 1 PERCORSO 1
        passo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //aprire passo 1 del percorso 1
                Intent i = new Intent(getApplicationContext(), Passo1P1.class);
                startActivity(i);
                finish();
            }
        });

        ///PASSO 2 PERCORSO 1

        passo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //aprire passo 2 del percorso 1

            }
        });
        ///PASSO 3 PERCORSO 1
        passo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //aprire passo 3 del percorso 1

            }
        });
        ///PASSO 4 PERCORSO 1
        passo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //aprire passo 4 del percorso 1

            }
        });
//        ///PASSO 5 PERCORSO 1
//        passo5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //aprire passo 5 del percorso 1
//
//            }
//        });
//        ///PASSO 6 PERCORSO 1
//        passo6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //aprire passo 6 del percorso 1
//
//            }
//        });




    }
}