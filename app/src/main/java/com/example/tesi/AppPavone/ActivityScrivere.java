package com.example.tesi.AppPavone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.tesi.R;
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

public class ActivityScrivere extends AppCompatActivity {

    private ImageView imageView_Scrivere;
    private FloatingActionButton button_Ascolta;
    private Button button_Risp1;
    private Button button_Risp2;
    private Button button_Risp3;
    private Button button_Risp4;
    private JSONArray jsonArray;
    private String corretta;
    private int errori;
    private Spinner spinner;
    private TextToSpeech textToSpeech;
    private ArrayList<String> risposte;
    private ImageView esito1;
    private Boolean ascoltato;

    private FrameLayout button_aiuto;
    private ImageView help1;
    private ImageView help2;
    private ImageView help3;
    private ImageView help4;
    private ImageView help5;
    private AnimationDrawable animationDrawable = null;
    private AnimationDrawable animationDrawableScelta1 = null;
    private AnimationDrawable animationDrawableScelta2 = null;
    private AnimationDrawable animationDrawableScelta3 = null;
    private AnimationDrawable animationDrawableScelta4 = null;

    private Button button_avanti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrivere);

        button_Ascolta = findViewById(R.id.button_Ascolta2);
        button_Risp1 = findViewById(R.id.button_Risp1);
        button_Risp2 = findViewById(R.id.button_Risp2);
        button_Risp3 = findViewById(R.id.button_Risp3);
        button_Risp4 = findViewById(R.id.button_Risp4);
        imageView_Scrivere = findViewById(R.id.imageView_Scrivere);
        spinner = findViewById(R.id.spinner_Corpo);
        esito1 = findViewById(R.id.esito1);

        button_aiuto = findViewById(R.id.button_aiuto);
        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);
        help3 = findViewById(R.id.help3);
        help4 = findViewById(R.id.help4);
        help5 = findViewById(R.id.help5);

        button_avanti = findViewById(R.id.button_avanti);

        risposte = new ArrayList<>();

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

        button_aiuto.setVisibility(View.VISIBLE);
        esito1.setVisibility(View.GONE);
        esito1.clearAnimation();

        errori = 0;

        button_Risp1.setBackgroundColor(Color.parseColor("#93C572"));
        button_Risp2.setBackgroundColor(Color.parseColor("#93C572"));
        button_Risp3.setBackgroundColor(Color.parseColor("#93C572"));
        button_Risp4.setBackgroundColor(Color.parseColor("#93C572"));
        button_avanti.setVisibility(View.GONE);

        button_avanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();
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


        button_aiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                help1.setVisibility(View.VISIBLE);

                animationDrawable = (AnimationDrawable) help1.getBackground();
                animationDrawable.start();

                button_aiuto.setVisibility(View.GONE);
            }
        });

        try {
            corretta = jsonArray.getJSONObject(random).getString("ita");


            risposte.add(jsonArray.getJSONObject(random).getString("ita"));
            risposte.add(jsonArray.getJSONObject(random).getString("sug1"));
            risposte.add(jsonArray.getJSONObject(random).getString("sug2"));
            risposte.add(jsonArray.getJSONObject(random).getString("sug3"));


            parole.add(jsonArray.getJSONObject(random).getString("fra"));
            parole.add(jsonArray.getJSONObject(random).getString("eng"));

            SpinnerAdapter adapter = new SpinnerAdapter(this, parole);
            spinner.setAdapter(adapter);
            spinner.setSelection(0);


            int temp = (int) (Math.random() * risposte.size() );
            button_Risp1.setText(risposte.get(temp));
            risposte.remove(temp);
            button_Risp1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(ascoltato){
                        if (button_Risp1.getText().toString().equals(corretta)) {
                            //Corretto
                            disabilitaBottoni();
                            terminaAnimazioneScelta();


                            button_avanti.setVisibility(View.VISIBLE);

                            if(errori == 0){
                                //metti il SVOLTO nel JSON

                            } else {

                            }

                            button_aiuto.setVisibility(View.GONE);
                            button_avanti.setVisibility(View.VISIBLE);

                            esito1.setVisibility(View.VISIBLE);
                            esito1.setImageResource(R.drawable.thumbs_up);

                            esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

                        } else {
                            errori++;

                            if(errori == 3){
                                button_aiuto.setVisibility(View.GONE);
                                button_avanti.setVisibility(View.VISIBLE);

                                esito1.setVisibility(View.VISIBLE);
                                esito1.setImageResource(R.drawable.thumbs_down);

                                esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

                                disabilitaBottoni();
                                terminaAnimazioneScelta();

                            }

                            button_Risp1.setClickable(false);
                            button_Risp1.setBackgroundColor(Color.parseColor("#f54518"));

                        }

                    } else {
                        help1.setVisibility(View.VISIBLE);

                        animationDrawable = (AnimationDrawable) help1.getBackground();
                        animationDrawable.start();
                        button_aiuto.setVisibility(View.GONE);
                    }



                }
                });

            temp = (int) (Math.random() * risposte.size() );
            button_Risp2.setText(risposte.get(temp));
            risposte.remove(temp);
            button_Risp2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(ascoltato){
                        if (button_Risp2.getText().toString().equals(corretta)) {
                            //Corretto
                            disabilitaBottoni();
                            terminaAnimazioneScelta();
                            button_Risp2.setBackgroundColor(Color.parseColor("#50e024"));
                            button_avanti.setVisibility(View.VISIBLE);

                            if(errori == 0){
                                //metti il SVOLTO nel JSON

                            }else {

                            }

                            button_aiuto.setVisibility(View.GONE);
                            button_avanti.setVisibility(View.VISIBLE);

                            esito1.setVisibility(View.VISIBLE);
                            esito1.setImageResource(R.drawable.thumbs_up);

                            esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));


                        } else {
                            errori++;

                            if(errori == 3){
                                button_aiuto.setVisibility(View.GONE);
                                button_avanti.setVisibility(View.VISIBLE);

                                esito1.setVisibility(View.VISIBLE);
                                esito1.setImageResource(R.drawable.thumbs_down);

                                esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));
                                disabilitaBottoni();
                                terminaAnimazioneScelta();
                            }

                            button_Risp2.setClickable(false);
                            button_Risp2.setBackgroundColor(Color.parseColor("#f54518"));

                        }

                    } else {
                        help1.setVisibility(View.VISIBLE);

                        animationDrawable = (AnimationDrawable) help1.getBackground();
                        animationDrawable.start();
                        button_aiuto.setVisibility(View.GONE);
                    }


                }
            });




            temp = (int) (Math.random() * risposte.size() );
            button_Risp3.setText(risposte.get(temp));
            risposte.remove(temp);
            button_Risp3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(ascoltato){
                        if (button_Risp3.getText().toString().equals(corretta)) {
                            //Corretto
                            disabilitaBottoni();
                            terminaAnimazioneScelta();
                            button_avanti.setVisibility(View.VISIBLE);

                            if(errori == 0){
                                //metti il SVOLTO nel JSON

                            } else {


                            }

                            button_aiuto.setVisibility(View.GONE);
                            button_avanti.setVisibility(View.VISIBLE);

                            esito1.setVisibility(View.VISIBLE);
                            esito1.setImageResource(R.drawable.thumbs_up);

                            esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

                        } else {
                            errori++;

                            if(errori == 3){
                                button_aiuto.setVisibility(View.GONE);
                                button_avanti.setVisibility(View.VISIBLE);

                                esito1.setVisibility(View.VISIBLE);
                                esito1.setImageResource(R.drawable.thumbs_down);

                                esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));
                                disabilitaBottoni();
                                terminaAnimazioneScelta();
                            }

                            button_Risp3.setClickable(false);
                            button_Risp3.setBackgroundColor(Color.parseColor("#f54518"));

                        }

                    } else {
                        help1.setVisibility(View.VISIBLE);

                        animationDrawable = (AnimationDrawable) help1.getBackground();
                        animationDrawable.start();
                        button_aiuto.setVisibility(View.GONE);
                    }



                }
            });

            temp = (int) (Math.random() * risposte.size() );
            button_Risp4.setText(risposte.get(temp));
            risposte.remove(temp);
            button_Risp4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(ascoltato){
                        if (button_Risp4.getText().toString().equals(corretta)) {
                            //Corretto
                            disabilitaBottoni();
                            terminaAnimazioneScelta();
                            button_avanti.setVisibility(View.VISIBLE);

                            if(errori == 0){
                                //metti il SVOLTO nel JSON


                            }else {

                            }

                            button_aiuto.setVisibility(View.GONE);
                            button_avanti.setVisibility(View.VISIBLE);

                            esito1.setVisibility(View.VISIBLE);
                            esito1.setImageResource(R.drawable.thumbs_up);

                            esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

                        } else {
                            errori++;

                            if(errori == 3){
                                button_aiuto.setVisibility(View.GONE);
                                button_avanti.setVisibility(View.VISIBLE);

                                esito1.setVisibility(View.VISIBLE);
                                esito1.setImageResource(R.drawable.thumbs_down);

                                esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));
                                disabilitaBottoni();
                                terminaAnimazioneScelta();
                            }

                            button_Risp4.setClickable(false);
                            button_Risp4.setBackgroundColor(Color.parseColor("#f54518"));

                        }

                    } else {
                        help1.setVisibility(View.VISIBLE);

                        animationDrawable = (AnimationDrawable) help1.getBackground();
                        animationDrawable.start();
                        button_aiuto.setVisibility(View.GONE);
                    }



                }
            });

            if(jsonArray.getJSONObject(random).getString("img") != ""){
                byte[] decodedString = Base64.decode(jsonArray.getJSONObject(random).getString("img"), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView_Scrivere.setImageBitmap(decodedByte);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ///////////////////////////////////



        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {

                    button_Ascolta.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ascoltato = true;

                            if(animationDrawable != null) {
                                help1.setVisibility(View.GONE);
                                animationDrawable.stop();
                                animationDrawable = null;

                                help2.setVisibility(View.VISIBLE);
                                help3.setVisibility(View.VISIBLE);
                                help4.setVisibility(View.VISIBLE);
                                help5.setVisibility(View.VISIBLE);

                                animationDrawableScelta1 = (AnimationDrawable) help2.getBackground();
                                animationDrawableScelta2 = (AnimationDrawable) help3.getBackground();
                                animationDrawableScelta3 = (AnimationDrawable) help4.getBackground();
                                animationDrawableScelta4 = (AnimationDrawable) help5.getBackground();
                                animationDrawableScelta1.start();
                                animationDrawableScelta2.start();
                                animationDrawableScelta3.start();
                                animationDrawableScelta4.start();

                            }

                            textToSpeech.setLanguage(Locale.ITALIAN);
                            String toSpeakIt = corretta;

                            textToSpeech.speak(toSpeakIt, TextToSpeech.QUEUE_FLUSH, null);

                        }
                    });

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if(ascoltato){
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
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
        });


    }

    private void terminaAnimazioneScelta(){
        if(animationDrawableScelta1 != null && animationDrawableScelta2 != null
                && animationDrawableScelta3 != null && animationDrawableScelta4 != null) {

            help2.setVisibility(View.GONE);
            animationDrawableScelta1.stop();
            animationDrawableScelta1 = null;

            help3.setVisibility(View.GONE);
            animationDrawableScelta2.stop();
            animationDrawableScelta2 = null;

            help4.setVisibility(View.GONE);
            animationDrawableScelta3.stop();
            animationDrawableScelta3 = null;

            help5.setVisibility(View.GONE);
            animationDrawableScelta4.stop();
            animationDrawableScelta4 = null;

            button_aiuto.setVisibility(View.VISIBLE);
        }
    }

    private void disabilitaBottoni(){



        if( button_Risp1.getText().toString().equals(corretta)){
            button_Risp1.setBackgroundColor(Color.parseColor("#50e024"));
        } else if ( button_Risp2.getText().toString().equals(corretta)){
            button_Risp2.setBackgroundColor(Color.parseColor("#50e024"));
        } else if ( button_Risp3.getText().toString().equals(corretta)){
            button_Risp3.setBackgroundColor(Color.parseColor("#50e024"));
        } else if ( button_Risp4.getText().toString().equals(corretta)){
            button_Risp4.setBackgroundColor(Color.parseColor("#50e024"));
        }

        button_Risp1.setClickable(false);
        button_Risp2.setClickable(false);
        button_Risp3.setClickable(false);
        button_Risp4.setClickable(false);
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