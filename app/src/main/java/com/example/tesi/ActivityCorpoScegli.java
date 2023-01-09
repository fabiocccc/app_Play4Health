package com.example.tesi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
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

    private FrameLayout button_aiuto;
    private ImageView help1;
    private ImageView help2;
    private ImageView help3;
    private AnimationDrawable animationDrawable = null;
    private AnimationDrawable animationDrawableScelta1 = null;
    private AnimationDrawable animationDrawableScelta2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corpo_scegli);

        button_Ascolta = findViewById(R.id.button_Ascolta);
        button_Risp1 = findViewById(R.id.button_Risp1);
        button_Risp2 = findViewById(R.id.button_Risp2);
        imageView = findViewById(R.id.imageView_CorpoScegli);

        button_aiuto = findViewById(R.id.button_aiuto);
        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);
        help3 = findViewById(R.id.help3);

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
        ita.add("Testa");
        ita.add("Collo");
        ita.add("Torace");
        ita.add("Braccia");
        ita.add("Avambracci");
        ita.add("Mani");
        ita.add("Addome");
        ita.add("Coscie");
        ita.add("Ginocchia");
        ita.add("Stinchi");
        ita.add("Caviglie");
        ita.add("Piedi");


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

        button_aiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                help1.setVisibility(View.VISIBLE);

                animationDrawable = (AnimationDrawable) help1.getBackground();
                animationDrawable.start();
                button_aiuto.setVisibility(View.GONE);
            }
        });

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
            case "Testa":
                imageView.setImageResource(R.drawable.ex_testa);
                break;
            case "Collo":
                imageView.setImageResource(R.drawable.ex_collo);
                break;
            case "Torace":
                imageView.setImageResource(R.drawable.ex_torace);
                break;
            case "Braccia":
                imageView.setImageResource(R.drawable.ex_braccia);
                break;
            case "Avambracci":
                imageView.setImageResource(R.drawable.ex_avambracci);
                break;
            case "Mani":
                imageView.setImageResource(R.drawable.ex_mani);
                break;
            case "Addome":
                imageView.setImageResource(R.drawable.ex_addome);
                break;
            case "Coscie":
                imageView.setImageResource(R.drawable.ex_coscie);
                break;
            case "Ginocchia":
                imageView.setImageResource(R.drawable.ex_ginocchia);
                break;
            case "Stinchi":
                imageView.setImageResource(R.drawable.ex_stinchi);
                break;
            case "Caviglie":
                imageView.setImageResource(R.drawable.ex_caviglie);
                break;
            case "Piedi":
                imageView.setImageResource(R.drawable.ex_piedi);
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

                    if(animationDrawableScelta1 != null && animationDrawableScelta2 != null) {
                        help2.setVisibility(View.GONE);
                        animationDrawableScelta1.stop();
                        animationDrawableScelta1 = null;

                        help3.setVisibility(View.GONE);
                        animationDrawableScelta2.stop();
                        animationDrawableScelta2 = null;

                        button_aiuto.setVisibility(View.VISIBLE);
                    }

                    disabilitaBottoni();
                    button_Risp1.setBackgroundColor(Color.parseColor("#50e024"));

                    Toast.makeText(getApplicationContext(), "Ex SVOLTO BENE", Toast.LENGTH_SHORT).show();
                }
            });

            button_Risp2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    button_Risp2.setClickable(false);
                    button_Risp2.setBackgroundColor(Color.parseColor("#f54518"));
                    Toast.makeText(getApplicationContext(), "SBAGLIATO", Toast.LENGTH_SHORT).show();
                }
            });

        } else{
            button_Risp1.setText(ita.get(randomSbagliata));
            button_Risp2.setText(corretta);

            button_Risp1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    button_Risp1.setClickable(false);
                    button_Risp1.setBackgroundColor(Color.parseColor("#f54518"));
                    Toast.makeText(getApplicationContext(), "SBAGLIATO", Toast.LENGTH_SHORT).show();
                }
            });

            button_Risp2.setOnClickListener(new View.OnClickListener() {
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

                    disabilitaBottoni();
                    button_Risp2.setBackgroundColor(Color.parseColor("#50e024"));
                    Toast.makeText(getApplicationContext(), "Ex SVOLTO BENE", Toast.LENGTH_SHORT).show();
                }
            });

        }

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

                            textToSpeech.setLanguage(Locale.ITALIAN);
                            String toSpeakIt = corretta;

                            textToSpeech.speak(toSpeakIt, TextToSpeech.QUEUE_FLUSH, null);

                        }
                    });

                }
            }
        });


    }

    private void disabilitaBottoni(){
        button_Risp1.setClickable(false);
        button_Risp2.setClickable(false);
    }
}