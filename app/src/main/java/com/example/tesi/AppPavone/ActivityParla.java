package com.example.tesi.AppPavone;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
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
import android.widget.TextView;
import android.widget.Toast;

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

public class ActivityParla extends AppCompatActivity implements RecognitionListener {

    private static final int REQUEST_RECORD_PERMISSION = 100;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    private Spinner spinner;
    private Button button_Parla;
    private FloatingActionButton button_Ascolta;
    private TextView text_Riconosciuto;
    private TextToSpeech textToSpeech;
    private SpeechRecognizer speechRecognizer;
    private Intent recognizerIntent;
    private ImageView imageView_Parla;
    private String corretta;
    private ImageView esito1;
    private Boolean ascoltato;

    private FrameLayout button_aiuto;
    private ImageView help1;
    private ImageView help2;
    private AnimationDrawable animationDrawable = null;

    private Button button_avanti;

    private ArrayList<Json> arrayJson;
    DatabaseReference dr;
    private ProgressBar progressBar;

    private CardView card;

    private String key;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parla);

        spinner = findViewById(R.id.spinner_Corpo);
        button_Parla = findViewById(R.id.button_Parla);
        button_Ascolta = findViewById(R.id.button_Ascolta);
        text_Riconosciuto = findViewById(R.id.TextRiconosciuto);
        imageView_Parla = findViewById(R.id.imageView_Scrivere);
        esito1 = findViewById(R.id.esito1);

        button_aiuto = findViewById(R.id.button_aiuto);
        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);
        button_avanti = findViewById(R.id.button_avanti);

        //

        card = findViewById(R.id.card_Parla);
        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);
        card.setVisibility(View.GONE);
        button_Parla.setVisibility(View.GONE);
        button_Ascolta.setVisibility(View.GONE);
        imageView_Parla.setVisibility(View.GONE);
        button_aiuto.setVisibility(View.GONE);
        text_Riconosciuto.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);

        user = FirebaseAuth.getInstance().getCurrentUser();

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

        button_avanti.setVisibility(View.GONE);
        button_aiuto.setVisibility(View.GONE);
        esito1.setVisibility(View.GONE);
        esito1.clearAnimation();

        text_Riconosciuto.setText("");

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
        ArrayList<String> parole = new ArrayList<>();

        readDataJson1(new MyCallback() {
            @Override
            public void onCallback(ArrayList<Json> arrayJson) {

                progressBar.setVisibility(View.GONE);
                card.setVisibility(View.VISIBLE);
                button_Parla.setVisibility(View.VISIBLE);
                button_Ascolta.setVisibility(View.VISIBLE);
                imageView_Parla.setVisibility(View.VISIBLE);
                button_aiuto.setVisibility(View.VISIBLE);
                text_Riconosciuto.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);

                int random = (int)(Math.random() * arrayJson.size());

                corretta = arrayJson.get(random).getIta();
                parole.add(arrayJson.get(random).getIta());
                parole.add(arrayJson.get(random).getFra());
                parole.add(arrayJson.get(random).getEng());


                    if(arrayJson.get(random).getImg() != ""){
                        byte[] decodedString = Base64.decode(arrayJson.get(random).getImg(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageView_Parla.setImageBitmap(decodedByte);
                    }

                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);

                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
                speechRecognizer.setRecognitionListener(ActivityParla.this);              //commentato ma potrebbe dare problemi forse


                button_Parla.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(ascoltato){
                            button_avanti.setVisibility(View.VISIBLE);

                            if(animationDrawable != null) {
                                help2.setVisibility(View.GONE);
                                animationDrawable.stop();
                                animationDrawable = null;

                                button_aiuto.setVisibility(View.VISIBLE);
                            }

                            recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "it-IT");
                            ActivityCompat.requestPermissions(ActivityParla.this, new String[] { Manifest.permission.RECORD_AUDIO }, REQUEST_RECORD_PERMISSION);

                        }else {
                            help1.setVisibility(View.VISIBLE);

                            animationDrawable = (AnimationDrawable) help1.getBackground();
                            animationDrawable.start();

                            button_aiuto.setVisibility(View.GONE);
                        }

                    }
                });


                textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR) {
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if(ascoltato){
                                        switch (i){
                                            case 0:
                                                //Toast.makeText(getApplicationContext(), "ITALIA",Toast.LENGTH_SHORT).show();
                                                textToSpeech.setLanguage(Locale.ITALIAN);
                                                String toSpeakIt = spinner.getSelectedItem().toString();

                                                textToSpeech.speak(toSpeakIt, TextToSpeech.QUEUE_FLUSH, null);
                                                break;
                                            case 1:
                                                //Toast.makeText(getApplicationContext(), "ENGLISH",Toast.LENGTH_SHORT).show();
                                                textToSpeech.setLanguage(Locale.FRANCE);
                                                String toSpeakFr = spinner.getSelectedItem().toString();

                                                textToSpeech.speak(toSpeakFr, TextToSpeech.QUEUE_FLUSH, null);
                                                break;
                                            case 2:
                                                //Toast.makeText(getApplicationContext(), "BAGUETA",Toast.LENGTH_SHORT).show();
                                                textToSpeech.setLanguage(Locale.ENGLISH);
                                                String toSpeakEn = spinner.getSelectedItem().toString();

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



                button_Ascolta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ascoltato = true;

                        if(animationDrawable != null) {
                            help1.setVisibility(View.GONE);
                            animationDrawable.stop();

                            help2.setVisibility(View.VISIBLE);

                            animationDrawable = (AnimationDrawable) help2.getBackground();
                            animationDrawable.start();
                        }

                        String toSpeak = spinner.getItemAtPosition(0).toString();
                        spinner.setSelection(0);
                        textToSpeech.setLanguage(Locale.ITALY);
                        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                    }
                });



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

            DatabaseReference rf = dr.child("utenti").child(key).child("ripeti");

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

                        gf.child("utenti").child(key).child("ripeti").child(id).child("parola").setValue(elementoCliccato);
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

        String completato = "Ha eseguito l'attività ripeti in data:" + " " +formattedDate;

        //String dataFasulla = "29-08-2023";
        AttivitaUtente attivitaUtente = new AttivitaUtente(completato, formattedDate);

        dr.child("utenti").child(key).child("attivita").child(id).setValue(attivitaUtente);

    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this, new String[] { permission }, requestCode);
        }
        else {
            //Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    speechRecognizer.startListening(recognizerIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Accetta il permessi per registrare", Toast.LENGTH_LONG).show();
                }
        }
    }



    @Override
    public void onReadyForSpeech(Bundle bundle) {
        button_Parla.setForegroundTintList(ColorStateList.valueOf(Color.parseColor("#ff4d00")));
    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {
        button_Parla.setForegroundTintList(ColorStateList.valueOf(Color.parseColor("#93C572")));
    }

    @Override
    public void onError(int i) {
        String errorMessage = getErrorText(i);
        Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text = result + "";
        text_Riconosciuto.setText(text);


        if(text_Riconosciuto.getText().toString().equalsIgnoreCase(corretta)){
            button_aiuto.setVisibility(View.GONE);
            button_avanti.setVisibility(View.VISIBLE);

            esito1.setVisibility(View.VISIBLE);
            esito1.setImageResource(R.drawable.thumbs_up);

            //controllo se l'utente è loggato
            if(user != null) {


                String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                String nomeUtente =  mailLogged.replace("@gmail.com", "");

                trovaKeyUtente(nomeUtente, corretta);

            }

            esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

            button_Parla.setClickable(false);
        } else {
            button_aiuto.setVisibility(View.GONE);
            button_avanti.setVisibility(View.VISIBLE);

            esito1.setVisibility(View.VISIBLE);
            esito1.setImageResource(R.drawable.thumbs_down);

            esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));
        }
    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Errore audio";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Errore client";
                break;
            case
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Accetta i permessi";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Errore di rete";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Errore di rete";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "Riprova";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "Aspetta";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "Errore server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "Nessuna voce riconosciuta.";
                break;
            default:
                message = "Prova ancora.";
                break;
        }
        return message;
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
}