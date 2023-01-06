package com.example.tesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ActivityScrivereDifficile extends AppCompatActivity {

    private LinearLayout linearLettere;
    private LinearLayout linearLettere1;
    private LinearLayout linearRisposta;
    private LinearLayout linearRisposta1;
    private ImageView imageView;
    private Spinner spinner;
    private TextToSpeech textToSpeech;
    private FloatingActionButton button_Ascolta;

    private JSONArray jsonArray;
    private String corretta;
    private String mischiata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrivere_difficile);

        imageView = findViewById(R.id.imageView_Scrivere);
        spinner = findViewById(R.id.spinner);
        button_Ascolta = findViewById(R.id.button_Ascolta2);

        String jsonString = read(this, "dati.json");
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int random = (int) (Math.random() * jsonArray.length());
        //Toast.makeText(this, "nu: "+ random, Toast.LENGTH_SHORT).show();

        ArrayList<String> parole = new ArrayList<>();




        try {
            corretta = jsonArray.getJSONObject(random).getString("ita");
            parole.add(jsonArray.getJSONObject(random).getString("fra"));
            parole.add(jsonArray.getJSONObject(random).getString("eng"));

            byte[] decodedString = Base64.decode(jsonArray.getJSONObject(random).getString("img"), Base64.DEFAULT);

            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, parole, true);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0);



        //Toast.makeText(getApplicationContext(), "-"+ lettera.getChildAt(1).toString(), Toast.LENGTH_SHORT).show();
        List<String> characters = Arrays.asList(corretta.split(""));
        Collections.shuffle(characters);
        mischiata = "";
        for (String character : characters)
        {
            mischiata += character;
        }

        linearLettere = findViewById(R.id.linear_lettere);
        linearLettere1 = findViewById(R.id.linear_lettere1);
        linearRisposta = findViewById(R.id.linear_risposta);
        linearRisposta1 = findViewById(R.id.linear_risposta1);

        int i=0;
        while(i != corretta.length() ){
            FrameLayout lettera = null;
            if(i >= 7){
                linearRisposta1.setVisibility(View.VISIBLE);
                lettera = (FrameLayout) LayoutInflater.from(this).inflate( R.layout.lettera, null);
                linearLettere1.addView(lettera);
                TextView t1 = (TextView) lettera.getChildAt(0);
                Character c = mischiata.charAt(i);
                t1.setText(c.toString());
                i++;
            } else{
                lettera = (FrameLayout) LayoutInflater.from(this).inflate( R.layout.lettera, null);
                linearLettere.addView(lettera);
                TextView t1 = (TextView) lettera.getChildAt(0);
                Character c = mischiata.charAt(i);
                t1.setText(c.toString());
                i++;
            }


            FrameLayout finalLettera = lettera;
            lettera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LinearLayout v = (LinearLayout) finalLettera.getParent();

                    if(getResources().getResourceEntryName(v.getId()).equals("linear_risposta")){
                        linearRisposta.removeView(finalLettera);

                        if(linearLettere.getChildCount() < 7){
                            linearLettere.addView(finalLettera);
                        } else {
                            linearLettere1.addView(finalLettera);
                        }
                        controllaLettere();

                    } else if (getResources().getResourceEntryName(v.getId()).equals("linear_risposta1")){
                        linearRisposta1.removeView(finalLettera);

                        if(linearLettere.getChildCount() < 7){
                            linearLettere.addView(finalLettera);
                        } else {
                            linearLettere1.addView(finalLettera);
                        }
                        controllaLettere();


                    }else if (getResources().getResourceEntryName(v.getId()).equals("linear_lettere")){
                        linearLettere.removeView(finalLettera);

                        if(linearRisposta.getChildCount() < 7){
                            linearRisposta.addView(finalLettera);
                        } else {
                            linearRisposta1.addView(finalLettera);
                        }
                        controllaLettere();


                    } else if (getResources().getResourceEntryName(v.getId()).equals("linear_lettere1")){
                        linearLettere1.removeView(finalLettera);

                        if(linearRisposta.getChildCount() < 7){
                            linearRisposta.addView(finalLettera);
                        } else {
                            linearRisposta1.addView(finalLettera);
                        }
                        controllaLettere();

                    }
                }
            });

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                button_Ascolta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        textToSpeech.setLanguage(Locale.ITALIAN);
                        String toSpeakIt = corretta;

                        textToSpeech.speak(toSpeakIt, TextToSpeech.QUEUE_FLUSH, null);

                    }
                });

                if(status != TextToSpeech.ERROR) {

                    button_Ascolta.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            textToSpeech.setLanguage(Locale.ITALIAN);
                            String toSpeakIt = corretta;

                            textToSpeech.speak(toSpeakIt, TextToSpeech.QUEUE_FLUSH, null);

                        }
                    });

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            switch (i){
                                case 0:
                                    textToSpeech.setLanguage(Locale.FRANCE);
                                    String toSpeakFr = spinner.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakFr, TextToSpeech.QUEUE_FLUSH, null);
                                    break;
                                case 1:
                                    textToSpeech.setLanguage(Locale.ENGLISH);
                                    String toSpeakEn = spinner.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakEn, TextToSpeech.QUEUE_FLUSH, null);
                                    break;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
        });



    }

    private void controllaLettere(){

        String parola = "";

        for(int i = 0; i != linearRisposta.getChildCount(); i++){
            FrameLayout f = (FrameLayout) linearRisposta.getChildAt(i);
            TextView t = (TextView) f.getChildAt(0);
            char c =  t.getText().toString().charAt(0);
            parola+=c;

        }

        for(int i = 0; i != linearRisposta1.getChildCount(); i++){
            FrameLayout f = (FrameLayout) linearRisposta1.getChildAt(i);
            TextView t = (TextView) f.getChildAt(0);
            char c =  t.getText().toString().charAt(0);
            parola+=c;

        }

        if(parola.equals(corretta)){
            Toast.makeText(getApplicationContext(), "Ex Svolto", Toast.LENGTH_SHORT).show();

        }else if(!parola.equals(corretta) && linearRisposta.getChildCount() == corretta.length()){
            Toast.makeText(getApplicationContext(), "Ex SBAGLIATO", Toast.LENGTH_SHORT).show();

        }else if(!parola.equals(corretta) && linearRisposta.getChildCount() + linearRisposta1.getChildCount() == corretta.length()){
            Toast.makeText(getApplicationContext(), "Ex SBAGLIATO", Toast.LENGTH_SHORT).show();

        }


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