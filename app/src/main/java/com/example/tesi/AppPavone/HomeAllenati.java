package com.example.tesi.AppPavone;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tesi.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HomeAllenati extends AppCompatActivity {

    private FrameLayout button_corpo;
    private FrameLayout button_allenati;
    private FrameLayout button_salute;
    private FrameLayout button_medico;
    private FrameLayout button_parla;
    private FrameLayout button_indietro;
    private FrameLayout button_Aggiorna;
    private ProgressBar progressBar;
    private SharedPreferences shared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_allenati);

        button_corpo = findViewById(R.id.button_AllenatiCorpo);
        button_allenati = findViewById(R.id.button_AllenatiAllenamento);
        button_salute = findViewById(R.id.button_AllenatiSalute);
        button_medico = findViewById(R.id.button_AllenatiMedico);
        button_parla = findViewById(R.id.button_AllenatiParla);
        button_indietro = findViewById(R.id.button_indietro);
        button_Aggiorna = findViewById(R.id.button_HomeAggiorna);
        progressBar = findViewById(R.id.progress_circular);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

        if(connected){
            progressBar.setVisibility(View.GONE);
            button_corpo.setEnabled(true);
            button_allenati.setEnabled(true);
            button_salute.setEnabled(true);
            button_medico.setEnabled(true);
            button_parla.setEnabled(true);
            button_Aggiorna.setEnabled(true);
            button_indietro.setEnabled(true);

        } else {
            Toast.makeText(getApplicationContext(), "Non sei connesso a Internet", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        button_Aggiorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

                if(connected){
                    progressBar.setVisibility(View.GONE);
                    button_corpo.setEnabled(true);
                    button_allenati.setEnabled(true);
                    button_salute.setEnabled(true);
                    button_medico.setEnabled(true);
                    button_parla.setEnabled(true);
                    button_Aggiorna.setEnabled(true);
                    button_indietro.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Aggiornamento riuscito", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Non sei connesso a Internet", Toast.LENGTH_LONG).show();
                }

            }
        });


        button_indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button_corpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aggiornaStelle();
                Intent intent = new Intent(HomeAllenati.this, ActivityAscolta.class);
                intent.putExtra("tipo", "corpo");
                startActivity(intent);
            }
        });

        button_allenati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

                if(connected){
                    aggiornaStelle();
                    Intent intent = new Intent(HomeAllenati.this, ActivityGalleriaVideo.class);
                    intent.putExtra("tipo", "gesti");
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Non sei connesso a Internet", Toast.LENGTH_LONG).show();
                }

            }
        });

        button_salute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aggiornaStelle();
                Intent intent = new Intent(HomeAllenati.this, ActivityAscolta.class);
                intent.putExtra("tipo", "salute");
                startActivity(intent);
            }
        });

        button_medico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aggiornaStelle();
                Intent intent = new Intent(getApplicationContext(), ActivityMedico.class);
                startActivity(intent);
            }
        });

        button_parla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aggiornaStelle();
                Intent intent = new Intent(getApplicationContext(), ActivityDialogo.class);
                startActivity(intent);
            }
        });


    }

    private void aggiornaStelle(){

        shared =  getSharedPreferences("stelle", MODE_PRIVATE);

        if(shared.getString("stelle2", "").equals("")){
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("stelle2", "1");
            editor.commit();

        } else if(shared.getString("stelle2", "").equals("1")){
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("stelle2", "2");
            editor.commit();

        } else if(shared.getString("stelle2", "").equals("2")){
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("stelle2", "3");
            editor.commit();

        }

    }
}