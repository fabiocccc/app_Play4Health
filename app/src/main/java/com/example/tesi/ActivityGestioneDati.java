package com.example.tesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonArray;

import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Locale;

public class ActivityGestioneDati extends AppCompatActivity {

    private ListView listView;
    private ProgressBar progressBar;
    private Button button_DatiCalcio;
    private Button button_DatiCorpo;
    private Button button_DatiSalute;
    private Button button_CaricaVideo;
    private FrameLayout frame_Accesso;
    private EditText edit_Accesso;
    private Button button_Accesso;
    private String password = "password";
    private SharedPreferences shared;
    private Boolean admin;
    private FrameLayout button_indietro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestione_dati);
        listView = findViewById(R.id.listaDati);
        progressBar = findViewById(R.id.progress_circular);
        button_DatiCalcio = findViewById(R.id.button_DatiCalcio);
        button_DatiCorpo = findViewById(R.id.button_DatiCorpo);
        button_DatiSalute = findViewById(R.id.button_DatiSalute);
        button_CaricaVideo = findViewById(R.id.button_CaricaVideo);
        frame_Accesso = findViewById(R.id.frame_Accesso);
        edit_Accesso = findViewById(R.id.edit_Accesso);
        button_Accesso = findViewById(R.id.button_Accesso);
        button_indietro = findViewById(R.id.button_indietro);

        button_indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }


    @Override
    protected void onStart() {
        super.onStart();

        frame_Accesso.setVisibility(View.VISIBLE);
        edit_Accesso.setVisibility(View.VISIBLE);
        button_Accesso.setVisibility(View.VISIBLE);
        button_CaricaVideo.setVisibility(View.GONE);



        shared = getPreferences(MODE_PRIVATE);
        admin = shared.getBoolean("admin", false);



        if(admin){
            frame_Accesso.setVisibility(View.GONE);
            edit_Accesso.setVisibility(View.GONE);
            button_Accesso.setVisibility(View.GONE);
            button_CaricaVideo.setVisibility(View.VISIBLE);

            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

            boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

            if(connected){

            } else {
                Toast.makeText(getApplicationContext(), "Non sei connesso a Internet", Toast.LENGTH_LONG).show();
                finish();
            }

            button_DatiSalute.setVisibility(View.VISIBLE);
            button_DatiCorpo.setVisibility(View.VISIBLE);
            button_DatiCalcio.setVisibility(View.VISIBLE);

            button_DatiCalcio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ActivityGestioneDati.this, ActivityListaDati.class);
                    intent.putExtra("tipo", "calcio");
                    startActivity(intent);
                }
            });

            button_DatiCorpo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ActivityGestioneDati.this, ActivityListaDati.class);
                    intent.putExtra("tipo", "corpo");
                    startActivity(intent);
                }
            });

            button_DatiSalute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ActivityGestioneDati.this, ActivityListaDati.class);
                    intent.putExtra("tipo", "salute");
                    startActivity(intent);
                }
            });

            button_CaricaVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ActivityGestioneDati.this, ActivityCaricaVideo.class);
                    startActivity(intent);

                }
            });

        } else {

            button_DatiSalute.setVisibility(View.GONE);
            button_DatiCorpo.setVisibility(View.GONE);
            button_DatiCalcio.setVisibility(View.GONE);

            button_Accesso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(edit_Accesso.getText().toString().equals(password)){
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putBoolean("admin", true);
                        editor.commit();

                        frame_Accesso.setVisibility(View.GONE);
                        edit_Accesso.setVisibility(View.GONE);
                        button_Accesso.setVisibility(View.GONE);
                        button_CaricaVideo.setVisibility(View.VISIBLE);

                        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

                        if(connected){

                        } else {
                            Toast.makeText(getApplicationContext(), "Non sei connesso a Internet", Toast.LENGTH_LONG).show();
                            finish();
                        }

                        button_DatiSalute.setVisibility(View.VISIBLE);
                        button_DatiCorpo.setVisibility(View.VISIBLE);
                        button_DatiCalcio.setVisibility(View.VISIBLE);

                        button_DatiCalcio.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ActivityGestioneDati.this, ActivityListaDati.class);
                                intent.putExtra("tipo", "calcio");
                                startActivity(intent);
                            }
                        });

                        button_DatiCorpo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ActivityGestioneDati.this, ActivityListaDati.class);
                                intent.putExtra("tipo", "corpo");
                                startActivity(intent);
                            }
                        });

                        button_DatiSalute.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ActivityGestioneDati.this, ActivityListaDati.class);
                                intent.putExtra("tipo", "salute");
                                startActivity(intent);
                            }
                        });

                        button_CaricaVideo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ActivityGestioneDati.this, ActivityCaricaVideo.class);
                                startActivity(intent);

                            }
                        });



                    } else {
                        finish();
                        Toast.makeText(getApplicationContext(), "Accesso negato", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}