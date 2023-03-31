package com.example.tesi.AppPavone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tesi.R;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityCampoRuoli extends AppCompatActivity {

    private FrameLayout button_portiere;
    private FrameLayout button_difensore1;
    private FrameLayout button_difensore2;
    private FrameLayout button_terzinod;
    private FrameLayout button_terzinos;
    private FrameLayout button_centro1;
    private FrameLayout button_centro2;
    private FrameLayout button_esternod;
    private FrameLayout button_esternos;
    private FrameLayout button_att1;
    private FrameLayout button_att2;
    private Button button_campo;
    private TextToSpeech textToSpeech;
    private Spinner spinner;
    private FrameLayout button_aiuto;
    private ImageView help1;
    private AnimationDrawable animationDrawable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campo_ruoli);

        button_portiere = findViewById(R.id.button_Rigore2);
        button_difensore1 = findViewById(R.id.button_Difensore1);
        button_difensore2 = findViewById(R.id.button_Difensore2);
        button_terzinod = findViewById(R.id.button_TerzinoD);
        button_terzinos = findViewById(R.id.button_TerzinoS);
        button_centro1 = findViewById(R.id.button_Centro1);
        button_centro2 = findViewById(R.id.button_Centro2);
        button_esternod = findViewById(R.id.button_EsternoD);
        button_esternos = findViewById(R.id.button_EsternoS);
        button_att1 = findViewById(R.id.button_Att1);
        button_att2 = findViewById(R.id.button_Att2);
        button_campo = findViewById(R.id.button_Campo);
        button_aiuto = findViewById(R.id.button_aiuto);
        help1 = findViewById(R.id.help1);

        spinner = findViewById(R.id.spinner_Corpo);

        findViewById(R.id.button_indietro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    boolean abort;
    int count = 0;

    private void changeBackground(){
        if (abort)
            return;
        new Handler().postDelayed(new Runnable() {

            public void run() {

                Toast.makeText(getApplicationContext(), "ITALIA",Toast.LENGTH_SHORT).show();
                count++;
                if(count % 2 == 0){
                    button_portiere.setBackground(getDrawable(R.drawable.circle_att));
                }else{
                    button_portiere.setBackground(getDrawable(R.drawable.circle_portiere));
                }
                changeBackground();

            }
        }, 500);
    }

    @Override
    protected void onStart() {
        super.onStart();

        button_aiuto.setVisibility(View.VISIBLE);

        button_aiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                help1.setVisibility(View.VISIBLE);

                animationDrawable = (AnimationDrawable) help1.getBackground();
                animationDrawable.start();

                button_aiuto.setVisibility(View.GONE);

            }
        });

        button_portiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList parole = new ArrayList();
                parole.add("Portiere"); parole.add("Gardien de but"); parole.add("Goalkeeper");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);

            }
        });

        button_difensore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList parole = new ArrayList();
                parole.add("Difensore"); parole.add("Défenseur"); parole.add("Defender");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);

            }
        });

        button_difensore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(animationDrawable != null){
                    animationDrawable.stop();
                    animationDrawable = null;
                    help1.setVisibility(View.GONE);
                    button_aiuto.setVisibility(View.VISIBLE);
                }

                ArrayList parole = new ArrayList();
                parole.add("Difensore"); parole.add("Défenseur"); parole.add("Defender");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);

            }
        });

        button_terzinod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList parole = new ArrayList();
                parole.add("Terzino destro"); parole.add("Arrière droit"); parole.add("Right-back");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);

            }
        });

        button_terzinos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList parole = new ArrayList();
                parole.add("Terzino sinistro"); parole.add("Arrière gauche"); parole.add("Left-back");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);

            }
        });

        button_centro1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList parole = new ArrayList();
                parole.add("Centrocampista"); parole.add("Milieu de terrain"); parole.add("Midfielder");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);

            }
        });

        button_centro2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList parole = new ArrayList();
                parole.add("Centrocampista"); parole.add("Milieu de terrain"); parole.add("Midfielder");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);

            }
        });

        button_esternod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList parole = new ArrayList();
                parole.add("Esterno destro"); parole.add("Champ droit"); parole.add("Right winger");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);

            }
        });

        button_esternos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList parole = new ArrayList();
                parole.add("Esterno sinistro"); parole.add("Champ gauche"); parole.add("Left winger");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);

            }
        });

        button_att1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList parole = new ArrayList();
                parole.add("Attaccante"); parole.add("Attaquant"); parole.add("Striker");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);

            }
        });

        button_att2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList parole = new ArrayList();
                parole.add("Attaccante"); parole.add("Attaquant"); parole.add("Striker");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);

            }
        });


        button_campo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityCampo.class);
                finish();
                startActivity(intent);
            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            switch (i){
                                case 0:
                                    textToSpeech.setLanguage(Locale.ITALIAN);
                                    String toSpeakIt = spinner.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakIt, TextToSpeech.QUEUE_FLUSH, null);
                                    break;
                                case 1:
                                    textToSpeech.setLanguage(Locale.FRANCE);
                                    String toSpeakFr = spinner.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakFr, TextToSpeech.QUEUE_FLUSH, null);
                                    break;
                                case 2:
                                    textToSpeech.setLanguage(Locale.ENGLISH);
                                    String toSpeakEn = spinner.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakEn, TextToSpeech.QUEUE_FLUSH, null);
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
}