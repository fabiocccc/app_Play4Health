package com.example.tesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class ActivityScegli extends AppCompatActivity {

    private FloatingActionButton button_Ascolta;
    private ImageView imageView1;
    private ImageView imageView2;
    private JSONArray jsonArray;
    private Spinner spinner1;
    private Spinner spinner2;
    private TextToSpeech textToSpeech;
    private String corretta;
    private Boolean ascoltato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scegli);

        button_Ascolta = findViewById(R.id.button_AscoltaScegli);
        imageView1 = findViewById(R.id.imageView1_Scegli);
        imageView2 = findViewById(R.id.imageView2_Scegli);
        spinner1 = findViewById(R.id.spinner_Scelta1);
        spinner2 = findViewById(R.id.spinner_Scelta2);

        ascoltato = false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        String jsonString = read(this, "dati.json");
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int random1 = (int)(Math.random() * jsonArray.length());
        int random2 = (int)(Math.random() * jsonArray.length());

        ArrayList<String> parole1 = new ArrayList<>();
        ArrayList<String> parole2 = new ArrayList<>();

        try {
            parole1.add(jsonArray.getJSONObject(random1).getString("ita"));
            parole1.add(jsonArray.getJSONObject(random1).getString("fra"));
            parole1.add(jsonArray.getJSONObject(random1).getString("eng"));

            parole2.add(jsonArray.getJSONObject(random2).getString("ita"));
            parole2.add(jsonArray.getJSONObject(random2).getString("fra"));
            parole2.add(jsonArray.getJSONObject(random2).getString("eng"));

            if(jsonArray.getJSONObject(random1).getString("img") != ""){
                byte[] decodedString = Base64.decode(jsonArray.getJSONObject(random1).getString("img"), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView1.setImageBitmap(decodedByte);
            }

            if(jsonArray.getJSONObject(random2).getString("img") != ""){
                byte[] decodedString = Base64.decode(jsonArray.getJSONObject(random2).getString("img"), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView2.setImageBitmap(decodedByte);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        int corr = (int)(Math.random() * 2 + 1);
        if(corr == 1){
            corretta = parole1.get(0);
        }else {
            corretta = parole2.get(0);
        }

        SpinnerAdapter adapter1 = new SpinnerAdapter(getApplicationContext(), parole1, true);
        spinner1.setAdapter(adapter1);
        spinner1.setSelection(0);

        SpinnerAdapter adapter2 = new SpinnerAdapter(getApplicationContext(), parole2, true);
        spinner2.setAdapter(adapter2);
        spinner2.setSelection(0);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {

                    button_Ascolta.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

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

                    /*spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            switch (i){
                                case 0:
                                    //Toast.makeText(getApplicationContext(), "ITALIA",Toast.LENGTH_SHORT).show();
                                    textToSpeech.setLanguage(Locale.ITALIAN);
                                    String toSpeakIt = spinner1.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakIt, TextToSpeech.QUEUE_FLUSH, null);
                                    break;
                                case 1:
                                    //Toast.makeText(getApplicationContext(), "ENGLISH",Toast.LENGTH_SHORT).show();
                                    textToSpeech.setLanguage(Locale.FRANCE);
                                    String toSpeakFr = spinner1.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakFr, TextToSpeech.QUEUE_FLUSH, null);
                                    break;
                                case 2:
                                    //Toast.makeText(getApplicationContext(), "BAGUETA",Toast.LENGTH_SHORT).show();
                                    textToSpeech.setLanguage(Locale.ENGLISH);
                                    String toSpeakEn = spinner1.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakEn, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            switch (i){
                                case 0:
                                    //Toast.makeText(getApplicationContext(), "ITALIA",Toast.LENGTH_SHORT).show();
                                    textToSpeech.setLanguage(Locale.ITALIAN);
                                    String toSpeakIt = spinner2.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakIt, TextToSpeech.QUEUE_FLUSH, null);
                                    break;
                                case 1:
                                    //Toast.makeText(getApplicationContext(), "ENGLISH",Toast.LENGTH_SHORT).show();
                                    textToSpeech.setLanguage(Locale.FRANCE);
                                    String toSpeakFr = spinner2.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakFr, TextToSpeech.QUEUE_FLUSH, null);
                                    break;
                                case 2:
                                    //Toast.makeText(getApplicationContext(), "BAGUETA",Toast.LENGTH_SHORT).show();
                                    textToSpeech.setLanguage(Locale.ENGLISH);
                                    String toSpeakEn = spinner2.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakEn, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });*/
                }
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ascoltato){
                    if(corr == 1){
                        //giusto
                        Toast.makeText(getApplicationContext(), "giusto", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "sbalgio", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "ASCOLTA PRIM;A", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ascoltato){
                    if(corr == 2){
                        //giusto
                        Toast.makeText(getApplicationContext(), "giusto", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "sbalgio", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "ASCOLTA PRIM;A", Toast.LENGTH_SHORT).show();
                }
            }
        });


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