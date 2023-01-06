package com.example.tesi;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityCorpoScegli extends AppCompatActivity {

    private ImageView imageView;
    private Button button_Risp1;
    private Button button_Risp2;
    private FloatingActionButton button_Ascolta;
    private TextToSpeech textToSpeech;

    private ArrayList<String> ita;
    private String corretta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corpo_scegli);

        button_Ascolta = findViewById(R.id.button_Ascolta);
        button_Risp1 = findViewById(R.id.button_Risp1);
        button_Risp2 = findViewById(R.id.button_Risp2);
        imageView = findViewById(R.id.imageView_CorpoScegli);

        ita = new ArrayList<>();
        ita.add("Bocca");
        ita.add("Naso");
        ita.add("Occhi");
        ita.add("Sopracciglia");
        ita.add("Orecchie");
        ita.add("Capelli");
        ita.add("Polso");
        ita.add("Palmo");
        ita.add("Pollice");
        ita.add("Indice");
        ita.add("Medio");
        ita.add("Anulare");
        ita.add("Mignolo");
    }

    @Override
    protected void onStart() {
        super.onStart();

        int random = (int)(Math.random() * ita.size());
        corretta = ita.get(random);

        switch (corretta){
            case "Bocca":
                imageView.setImageResource(R.drawable.ex_bocca);
                break;
            case "Naso":
                imageView.setImageResource(R.drawable.ex_naso);
                break;
            case "Occhi":
                imageView.setImageResource(R.drawable.ex_occhi);
                break;
            case "Sopracciglia":
                imageView.setImageResource(R.drawable.ex_sopracciglia);
                break;
            case "Orecchie":
                imageView.setImageResource(R.drawable.ex_orecchie);
                break;
            case "Capelli":
                imageView.setImageResource(R.drawable.ex_capelli);
                break;
            case "Polso":
                imageView.setImageResource(R.drawable.ex_polso);
                break;
            case "Palmo":
                imageView.setImageResource(R.drawable.ex_palmo);
                break;
            case "Pollice":
                imageView.setImageResource(R.drawable.ex_pollice);
                break;
            case "Indice":
                imageView.setImageResource(R.drawable.ex_indice);
                break;
            case "Medio":
                imageView.setImageResource(R.drawable.ex_medio);
                break;
            case "Anulare":
                imageView.setImageResource(R.drawable.ex_anulare);
                break;
            case "Mignolo":
                imageView.setImageResource(R.drawable.ex_mignolo);
                break;

        }


        int randomSbagliata =  (int)(Math.random() * ita.size());
        while (randomSbagliata == random) {
            randomSbagliata = (int)(Math.random() * ita.size());
        }

        int posGiusta = (int)(Math.random() * 2 + 1);
        //Toast.makeText(getApplicationContext(), "- " + posGiusta, Toast.LENGTH_SHORT).show();

        if(posGiusta == 1 ){
            button_Risp1.setText(corretta);
            button_Risp2.setText(ita.get(randomSbagliata));

            button_Risp1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Ex SVOLTO BENE", Toast.LENGTH_SHORT).show();
                }
            });

            button_Risp2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "SBAGLIATO", Toast.LENGTH_SHORT).show();
                }
            });

        } else{
            button_Risp1.setText(ita.get(randomSbagliata));
            button_Risp2.setText(corretta);

            button_Risp1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "SBAGLIATO", Toast.LENGTH_SHORT).show();
                }
            });

            button_Risp2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Ex SVOLTO BENE", Toast.LENGTH_SHORT).show();
                }
            });

        }

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

                }
            }
        });


    }
}