package com.example.tesi.AppPavone;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.tesi.AppConte.AttivitaUtente;
import com.example.tesi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class ActivityScegli extends AppCompatActivity {

    private FloatingActionButton button_Ascolta;
    private ImageView imageView1;
    private ImageView imageView2;
    private Spinner spinner1;
    private Spinner spinner2;
    private TextToSpeech textToSpeech;
    private String corretta;
    private Boolean ascoltato;
    private ImageView esito1;
    private ImageView esito2;

    private FrameLayout button_aiuto;
    private ImageView help1;
    private ImageView help2;
    private ImageView help3;
    private AnimationDrawable animationDrawable = null;
    private AnimationDrawable animationDrawableScelta1 = null;
    private AnimationDrawable animationDrawableScelta2 = null;

    private Button button_avanti;
    private ArrayList<Json> arrayJson;
    DatabaseReference dr;

    private ProgressBar progressBar;

    private CardView card1;
    private CardView card2;

    private String key;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scegli);

        button_Ascolta = findViewById(R.id.button_AscoltaScegli);
        imageView1 = findViewById(R.id.imageView1_Scegli);
        imageView2 = findViewById(R.id.imageView2_Scegli);
        spinner1 = findViewById(R.id.spinner_Scelta1);
        spinner2 = findViewById(R.id.spinner_Scelta2);
        esito1 = findViewById(R.id.esito1);
        esito2 = findViewById(R.id.esito2);

        button_aiuto = findViewById(R.id.button_aiuto);
        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);
        help3 = findViewById(R.id.help3);

        button_avanti = findViewById(R.id.button_avanti);

        progressBar = findViewById(R.id.progress_bar);

        card1 = findViewById(R.id.card_Scelta1);
        card2 = findViewById(R.id.card_Scelta2);

        //
        progressBar.setVisibility(View.VISIBLE);
        button_Ascolta.setVisibility(View.GONE);
        imageView1.setVisibility(View.GONE);
        imageView2.setVisibility(View.GONE);
        spinner1.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);
        card1.setVisibility(View.GONE);
        card2.setVisibility(View.GONE);
        button_aiuto.setVisibility(View.GONE);

        findViewById(R.id.button_indietro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        ascoltato = false;
        esito1.setVisibility(View.GONE);
        esito1.clearAnimation();
        esito2.setVisibility(View.GONE);
        esito2.clearAnimation();
        button_aiuto.setVisibility(View.GONE);

        user = FirebaseAuth.getInstance().getCurrentUser();

        button_avanti.setVisibility(View.GONE);

        button_avanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();

            }
        });

        button_aiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                help1.setVisibility(View.VISIBLE);

                animationDrawable = (AnimationDrawable) help1.getBackground();
                animationDrawable.start();
                button_aiuto.setVisibility(View.GONE);

            }
        });

        arrayJson = new ArrayList<>();

        readDataJson1(new MyCallback() {
            @Override
            public void onCallback(ArrayList<Json> arrayJson) {
                progressBar.setVisibility(View.GONE);
                card1.setVisibility(View.VISIBLE);
                card2.setVisibility(View.VISIBLE);
                button_Ascolta.setVisibility(View.VISIBLE);
                imageView1.setVisibility(View.VISIBLE);
                imageView2.setVisibility(View.VISIBLE);
                spinner1.setVisibility(View.VISIBLE);
                spinner2.setVisibility(View.VISIBLE);
                button_aiuto.setVisibility(View.VISIBLE);

                int random1 = (int)(Math.random() * arrayJson.size());
                int random2 = (int)(Math.random() * arrayJson.size());
                while(random2 == random1){
                    random2 = (int)(Math.random() * arrayJson.size());
                }

                ArrayList<String> parole1 = new ArrayList<>();
                ArrayList<String> parole2 = new ArrayList<>();

                parole1.add((arrayJson.get(random1).getIta()));
                parole1.add((arrayJson.get(random1).getFra()));
                parole1.add((arrayJson.get(random1).getEng()));

                parole2.add((arrayJson.get(random2).getIta()));
                parole2.add((arrayJson.get(random2).getFra()));
                parole2.add((arrayJson.get(random2).getEng()));

                if(arrayJson.get(random1).getImg() != ""){
                    byte[] decodedString = Base64.decode(arrayJson.get(random1).getImg(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageView1.setImageBitmap(decodedByte);
                }

                if(arrayJson.get(random2).getImg() != ""){
                    byte[] decodedString = Base64.decode(arrayJson.get(random2).getImg(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageView2.setImageBitmap(decodedByte);
                }

                int corr = (int)(Math.random() * 2 + 1);
                if(corr == 1){
                    corretta = parole1.get(0);
                }else if (corr == 2) {
                    corretta = parole2.get(0);
                }

                SpinnerAdapter adapter1 = new SpinnerAdapter(getApplicationContext(), parole1, true);
                spinner1.setAdapter(adapter1);

                SpinnerAdapter adapter2 = new SpinnerAdapter(getApplicationContext(), parole2, true);
                spinner2.setAdapter(adapter2);

                textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR) {

                            button_Ascolta.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if(animationDrawable != null) {
                                        help1.setVisibility(View.GONE);
                                        animationDrawable.stop();
                                        animationDrawable = null;

                                        help2.setVisibility(View.VISIBLE);
                                        help3.setVisibility(View.VISIBLE);

                                        animationDrawableScelta1 = (AnimationDrawable) help2.getBackground();
                                        animationDrawableScelta2 = (AnimationDrawable) help3.getBackground();
                                        animationDrawableScelta1.start();
                                        animationDrawableScelta2.start();

                                    }

                                    ascoltato = true;

                                    if(corr == 1){
                                        textToSpeech.setLanguage(Locale.ITALIAN);
                                        textToSpeech.speak(parole1.get(0), TextToSpeech.QUEUE_FLUSH, null);
                                    }else {
                                        textToSpeech.setLanguage(Locale.ITALIAN);
                                        textToSpeech.speak(parole2.get(0), TextToSpeech.QUEUE_FLUSH, null);
                                    }
                                }
                            });

                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if(ascoltato){
                                        switch (i){
                                            case 0:
                                                textToSpeech.setLanguage(Locale.ITALIAN);
                                                String toSpeakIt = spinner1.getSelectedItem().toString();

                                                textToSpeech.speak(toSpeakIt, TextToSpeech.QUEUE_FLUSH, null);
                                                break;
                                            case 1:
                                                textToSpeech.setLanguage(Locale.FRANCE);
                                                String toSpeakFr = spinner1.getSelectedItem().toString();

                                                textToSpeech.speak(toSpeakFr, TextToSpeech.QUEUE_FLUSH, null);
                                                break;
                                            case 2:
                                                textToSpeech.setLanguage(Locale.ENGLISH);
                                                String toSpeakEn = spinner1.getSelectedItem().toString();

                                                textToSpeech.speak(toSpeakEn, TextToSpeech.QUEUE_FLUSH, null);
                                        }
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if(ascoltato){
                                        switch (i){
                                            case 0:
                                                textToSpeech.setLanguage(Locale.ITALIAN);
                                                String toSpeakIt = spinner2.getSelectedItem().toString();

                                                textToSpeech.speak(toSpeakIt, TextToSpeech.QUEUE_FLUSH, null);
                                                break;
                                            case 1:
                                                textToSpeech.setLanguage(Locale.FRANCE);
                                                String toSpeakFr = spinner2.getSelectedItem().toString();

                                                textToSpeech.speak(toSpeakFr, TextToSpeech.QUEUE_FLUSH, null);
                                                break;
                                            case 2:
                                                textToSpeech.setLanguage(Locale.ENGLISH);
                                                String toSpeakEn = spinner2.getSelectedItem().toString();

                                                textToSpeech.speak(toSpeakEn, TextToSpeech.QUEUE_FLUSH, null);
                                        }
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }
                });

                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(animationDrawableScelta1 != null && animationDrawableScelta2 != null) {
                            help2.setVisibility(View.GONE);
                            animationDrawableScelta1.stop();
                            animationDrawableScelta1 = null;

                            help3.setVisibility(View.GONE);
                            animationDrawableScelta2.stop();
                            animationDrawableScelta2 = null;

                            button_aiuto.setVisibility(View.VISIBLE);
                        }

                        if(ascoltato){
                            if(corr == 1){
                                //controllo se l'utente è loggato
                                if(user != null) {


                                    String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                                    String nomeUtente =  mailLogged.replace("@gmail.com", "");

                                    trovaKeyUtente(nomeUtente, corretta);

                                }
                                //giusto
                                button_aiuto.setVisibility(View.GONE);
                                button_avanti.setVisibility(View.VISIBLE);

                                esito1.setVisibility(View.VISIBLE);
                                esito1.setImageResource(R.drawable.thumbs_up);

                                esito2.clearAnimation();
                                esito2.setVisibility(View.INVISIBLE);
                                esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

                                imageView1.setClickable(false);
                                imageView2.setClickable(false);

                            }else{
                                button_aiuto.setVisibility(View.GONE);
                                button_avanti.setVisibility(View.VISIBLE);

                                esito1.setVisibility(View.VISIBLE);
                                esito1.setImageResource(R.drawable.thumbs_down);

                                esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

                                imageView1.setClickable(false);
                                //imageView2.setClickable(false);
                            }
                        }else{
                            help1.setVisibility(View.VISIBLE);

                            animationDrawable = (AnimationDrawable) help1.getBackground();
                            animationDrawable.start();
                        }
                    }
                });

                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(animationDrawableScelta1 != null && animationDrawableScelta2 != null) {
                            help2.setVisibility(View.GONE);
                            animationDrawableScelta1.stop();
                            animationDrawableScelta1 = null;

                            help3.setVisibility(View.GONE);
                            animationDrawableScelta2.stop();
                            animationDrawableScelta2 = null;

                            button_aiuto.setVisibility(View.VISIBLE);
                        }

                        if(ascoltato){
                            if(corr == 2){

                                //controllo se l'utente è loggato
                                if(user != null) {


                                    String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                                    String nomeUtente =  mailLogged.replace("@gmail.com", "");

                                    trovaKeyUtente(nomeUtente, corretta);

                                }
                                //giusto
                                button_avanti.setVisibility(View.VISIBLE);
                                button_aiuto.setVisibility(View.GONE);

                                esito2.setVisibility(View.VISIBLE);
                                esito2.setImageResource(R.drawable.thumbs_up);

                                esito1.clearAnimation();
                                esito1.setVisibility(View.INVISIBLE);
                                esito2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

                                imageView1.setClickable(false);
                                imageView2.setClickable(false);
                            }else{
                                button_aiuto.setVisibility(View.GONE);
                                button_avanti.setVisibility(View.VISIBLE);

                                esito2.setVisibility(View.VISIBLE);
                                esito2.setImageResource(R.drawable.thumbs_down);

                                esito2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

                                //imageView1.setClickable(false);
                                imageView2.setClickable(false);
                            }
                        }else{
                            help1.setVisibility(View.VISIBLE);

                            animationDrawable = (AnimationDrawable) help1.getBackground();
                            animationDrawable.start();
                        }
                    }
                });




            }


        });





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

                    String mostra = ds.child("mostra").getValue(String.class);

                    if(mostra.equals("si")) {

                        Json json = new Json(ita, fra, eng, sug1, sug2, sug3, img, svolto);
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

    public void trovaKeyUtente(String nomeUtente, String elementoCliccato) {

        String id = UUID.randomUUID().toString();

        dr = FirebaseDatabase.getInstance().getReference();

        DatabaseReference rf = dr.child("utenti");
        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    String username = postSnapshot.child("username").getValue(String.class);
                    if (username.equals(nomeUtente)) {

                        key = postSnapshot.getKey();

                        scriviElemento(id, elementoCliccato);

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    public void scriviElemento(String id, String elementoCliccato) {

        System.out.println("ele:"+elementoCliccato);

        ArrayList<String> paroleEseguite = new ArrayList<>();

        dr = FirebaseDatabase.getInstance().getReference();

        DatabaseReference rf = dr.child("utenti").child(key).child("scegli");

        DatabaseReference gf = FirebaseDatabase.getInstance().getReference();


        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                    // A new comment has been added, add it to the displayed list
                    String parola = ds.child("parola").getValue(String.class);

                    paroleEseguite.add(parola);

                }

                if(!paroleEseguite.contains(elementoCliccato)) {

                    gf.child("utenti").child(key).child("scegli").child(id).child("parola").setValue(elementoCliccato);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void scriviAttivitaDb(String id, String nomeUtente) {

        //prendo la key dello user  loggato
        dr = FirebaseDatabase.getInstance().getReference();

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        String completato = "Ha eseguito l'attività scegli in data:" + " " +formattedDate;

        //String dataFasulla = "29-08-2023";
        AttivitaUtente attivitaUtente = new AttivitaUtente(completato, formattedDate);

        dr.child("utenti").child(key).child("attivita").child(id).setValue(attivitaUtente);

    }
}