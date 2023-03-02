package com.example.tesi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.Animator;
import android.animation.TimeInterpolator;
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
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

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
    private JSONArray jsonArray;
    private ImageView imageView_Parla;
    private String corretta;
    private ImageView esito1;
    private Boolean ascoltato;

    private FrameLayout button_aiuto;
    private ImageView help1;
    private ImageView help2;
    private AnimationDrawable animationDrawable = null;

    private Button button_avanti;

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



        String jsonString = read(this, "dati.json");
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int random = (int)(Math.random() * jsonArray.length());
        //Toast.makeText(this, "nu: "+ random, Toast.LENGTH_SHORT).show();


        ArrayList<String> parole = new ArrayList<>();
        try {
            corretta = jsonArray.getJSONObject(random).getString("ita");
            parole.add(jsonArray.getJSONObject(random).getString("ita"));
            parole.add(jsonArray.getJSONObject(random).getString("fra"));
            parole.add(jsonArray.getJSONObject(random).getString("eng"));

            if(jsonArray.getJSONObject(random).getString("img") != ""){
                        byte[] decodedString = Base64.decode(jsonArray.getJSONObject(random).getString("img"), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageView_Parla.setImageBitmap(decodedByte);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        SpinnerAdapter adapter = new SpinnerAdapter(this, parole);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);


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
        button_Parla.setForegroundTintList(ColorStateList.valueOf(Color.parseColor("#A91F70")));
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

    private String read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }
}