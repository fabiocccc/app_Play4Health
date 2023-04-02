package com.example.tesi.AppPavone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tesi.AppTravisani.Home;
import com.example.tesi.R;

public class HomePrima extends AppCompatActivity {

    private FrameLayout button_GiocaImpara;
    private FrameLayout button_GiocaAllenati;
    private FrameLayout button_AzioniSalienti;
    private FrameLayout button_HomeDati;
    private SharedPreferences shared;
    private String stelle1;
    private String stelle2;
    private String stelle3;
    private ImageView stella1_1;
    private ImageView stella1_2;
    private ImageView stella1_3;
    private ImageView stella2_1;
    private ImageView stella2_2;
    private ImageView stella2_3;
    private ImageView stella3_1;
    private ImageView stella3_2;
    private ImageView stella3_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_prima);

        button_HomeDati = findViewById(R.id.button_HomeDati);
        button_GiocaImpara = findViewById(R.id.button_GiocaImpara);
        button_GiocaAllenati = findViewById(R.id.button_GiocaAllenati);
        button_AzioniSalienti = findViewById(R.id.button_AzioniSalienti);
        stella1_1 = findViewById(R.id.stella1_1);
        stella1_2 = findViewById(R.id.stella1_2);
        stella1_3 = findViewById(R.id.stella1_3);
        stella2_1 = findViewById(R.id.stella2_1);
        stella2_2 = findViewById(R.id.stella2_2);
        stella2_3 = findViewById(R.id.stella2_3);
        stella3_1 = findViewById(R.id.stella3_1);
        stella3_2 = findViewById(R.id.stella3_2);
        stella3_3 = findViewById(R.id.stella3_3);

    }

    @Override
    protected void onStart() {
        super.onStart();

        aggiornaStelle();



        button_HomeDati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePrima.this, ActivityGestioneDati.class);
                startActivity(intent);
            }
        });

        button_GiocaImpara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePrima.this, ActivityImpara.class);
                startActivity(intent);
            }
        });

        button_GiocaAllenati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shared = getSharedPreferences("stelle",MODE_PRIVATE);
                if(shared.getString("stelle1", "").equals("3")){
                    Intent intent = new Intent(HomePrima.this, HomeAllenati.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(HomePrima.this, "Completa le stelle della sezione precedente", Toast.LENGTH_SHORT).show();
                }

            }
        });

        button_AzioniSalienti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shared = getSharedPreferences("stelle",MODE_PRIVATE);
                if(shared.getString("stelle2", "").equals("3")){
                    Intent intent = new Intent(HomePrima.this, ActivityGalleriaVideo.class);
                    intent.putExtra("tipo", "commento");
                    startActivity(intent);
                } else {
                    Toast.makeText(HomePrima.this, "Completa le stelle della sezione precedente", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void aggiornaStelle(){

        shared = getSharedPreferences("stelle",MODE_PRIVATE);
        stelle1 = shared.getString("stelle1", "");
        stelle2 = shared.getString("stelle2", "");
        stelle3 = shared.getString("stelle3", "");

        if(stelle1.equals("1")){
            stella1_1.setBackgroundResource(R.drawable.stella);

        } else if (stelle1.equals("2")){
            stella1_1.setBackgroundResource(R.drawable.stella);
            stella1_2.setBackgroundResource(R.drawable.stella);

        } else if (stelle1.equals("3")){
            stella1_1.setBackgroundResource(R.drawable.stella);
            stella1_2.setBackgroundResource(R.drawable.stella);
            stella1_3.setBackgroundResource(R.drawable.stella);
        }

        if(stelle2.equals("1")){
            stella2_1.setBackgroundResource(R.drawable.stella);

        } else if (stelle2.equals("2")){
            stella2_1.setBackgroundResource(R.drawable.stella);
            stella2_2.setBackgroundResource(R.drawable.stella);

        } else if (stelle2.equals("3")){
            stella2_1.setBackgroundResource(R.drawable.stella);
            stella2_2.setBackgroundResource(R.drawable.stella);
            stella2_3.setBackgroundResource(R.drawable.stella);
        }

        if(stelle3.equals("1")){
            stella3_1.setBackgroundResource(R.drawable.stella);

        } else if (stelle3.equals("2")){
            stella3_1.setBackgroundResource(R.drawable.stella);
            stella3_2.setBackgroundResource(R.drawable.stella);

        } else if (stelle3.equals("3")){
            stella3_1.setBackgroundResource(R.drawable.stella);
            stella3_2.setBackgroundResource(R.drawable.stella);
            stella3_3.setBackgroundResource(R.drawable.stella);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        aggiornaStelle();
    }

    @Override
    public void onBackPressed() {
        //aprire Home
        Intent i = new Intent(getApplicationContext(), Home.class);
        startActivity(i);
        finish();
    }
}