package com.example.tesi.AppPavone;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tesi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.util.ArrayList;
import java.util.Locale;

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
        button_aiuto.setVisibility(View.VISIBLE);
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
                //speechRecognizer.setRecognitionListener(this);              commentato ma potrebbe dare problemi forse


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
}