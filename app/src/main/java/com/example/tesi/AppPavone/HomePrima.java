package com.example.tesi.AppPavone;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tesi.AppTravisani.Home;
import com.example.tesi.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomePrima extends AppCompatActivity {

    private FrameLayout button_GiocaImpara;
    private FrameLayout button_GiocaAllenati;
    private FrameLayout button_AzioniSalienti;
    private FrameLayout button_HomeDati;
    private FrameLayout button_back;
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

    private ArrayList<Json> arrayJson;

    DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_prima);

        button_HomeDati = findViewById(R.id.button_HomeDati);
        button_GiocaImpara = findViewById(R.id.button_GiocaImpara);
        button_GiocaAllenati = findViewById(R.id.button_GiocaAllenati);
        button_AzioniSalienti = findViewById(R.id.button_AzioniSalienti);
        button_back= findViewById(R.id.button_back);
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
        arrayJson = new ArrayList<>();

        //la lettura fatta qui velocizza la prima volta che viene letto da realtime
        readDataJson1(new MyCallback() {
            @Override
            public void onCallback(ArrayList<Json> arrayJson) {

            }

        });

        //la lettura fatta qui velocizza la prima volta che viene letto da realtime
        readDataJson2(new MyCallback() {
            @Override
            public void onCallback(ArrayList<Json> arrayJson) {

            }

        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

    public interface MyCallback {
        void onCallback(ArrayList<Json> arrayJson);
    }

    public void readDataJson1(MyCallback myCallback) {
        dr = FirebaseDatabase.getInstance().getReference();

        DatabaseReference rf = dr.child("Json1");

        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                    // A new comment has been added, add it to the displayed list
                    String eng = ds.child("eng").getValue(String.class);
                    String fra = ds.child("fra").getValue(String.class);
                    String img = ds.child("img").getValue(String.class);
                    String ita = ds.child("ita").getValue(String.class);
                    String sug1 = ds.child("sug1").getValue(String.class);
                    String sug2 = ds.child("sug2").getValue(String.class);
                    String sug3 = ds.child("sug3").getValue(String.class);
                    Boolean svolto = ds.child("svolto").getValue(Boolean.class);

                    Json json = new Json(ita, fra, eng, sug1, sug2, sug3, img, svolto);
                    arrayJson.add(json);

                }
                myCallback.onCallback(arrayJson);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void readDataJson2(MyCallback myCallback) {
        dr = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rf = dr.child("Json2");

        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                    String eng = ds.child("eng").getValue(String.class);
                    String fra = ds.child("fra").getValue(String.class);
                    String img = ds.child("img").getValue(String.class);
                    String ita = ds.child("ita").getValue(String.class);
                    String tipo = ds.child("tipo").getValue(String.class);

                    if(tipo.equals("corpo")) {
                        Json json = new Json(ita, fra, eng, tipo, img);
                        arrayJson.add(json);
                    }

                }

                myCallback.onCallback(arrayJson);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}