package com.example.tesi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityCampo extends AppCompatActivity {

    private Button button_Porta1;
    private Button button_Rigore1;
    private Button button_Porta2;
    private Button button_Rigore2;
    private Button button_Angolo1;
    private Button button_Angolo2;
    private Button button_Angolo3;
    private Button button_Angolo4;
    private Button button_Centro;
    private Button button_Linea1;
    private Button button_Linea2;
    private Button button_Ruoli;
    private Spinner spinner;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campo);

        button_Porta1 = findViewById(R.id.button_Porta1);
        button_Porta2 = findViewById(R.id.button_Porta2);
        button_Rigore1 = findViewById(R.id.button_Rigore1);
        button_Rigore2 = findViewById(R.id.button_Rigore2);
        button_Angolo1 = findViewById(R.id.button_Angolo1);
        button_Angolo2 = findViewById(R.id.button_Angolo2);
        button_Angolo3 = findViewById(R.id.button_Angolo3);
        button_Angolo4 = findViewById(R.id.button_Angolo4);
        button_Centro = findViewById(R.id.button_Centro);
        button_Linea1 = findViewById(R.id.button_Linea1);
        button_Linea2 = findViewById(R.id.button_Linea2);
        button_Ruoli = findViewById(R.id.button_Ruoli);
        spinner = findViewById(R.id.spinner_Corpo);
    }

    @Override
    protected void onStart() {
        super.onStart();

        button_Porta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList parole = new ArrayList();
                parole.add("Area di porta"); parole.add("Surface de but"); parole.add("Goal area");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
            }
        });

        button_Porta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList parole = new ArrayList();
                parole.add("Area di porta"); parole.add("Surface de but"); parole.add("Goal area");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
            }
        });

        button_Rigore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList parole = new ArrayList();
                parole.add("Area di rigore"); parole.add("Zone de penalty"); parole.add("Penalty area");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
            }
        });

        button_Rigore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList parole = new ArrayList();
                parole.add("Area di rigore"); parole.add("Zone de penalty"); parole.add("Penalty area");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
            }
        });

        button_Angolo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList parole = new ArrayList();
                parole.add("Angolo"); parole.add("Corner"); parole.add("Corner");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
            }
        });

        button_Angolo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList parole = new ArrayList();
                parole.add("Angolo"); parole.add("Corner"); parole.add("Corner");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
            }
        });

        button_Angolo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList parole = new ArrayList();
                parole.add("Angolo"); parole.add("Corner"); parole.add("Corner");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
            }
        });

        button_Angolo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList parole = new ArrayList();
                parole.add("Angolo"); parole.add("Corner"); parole.add("Corner");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
            }
        });

        button_Linea1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList parole = new ArrayList();
                parole.add("Linea mediana"); parole.add("Ligne médiane"); parole.add("Half-way line");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
            }
        });

        button_Linea2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList parole = new ArrayList();
                parole.add("Linea mediana"); parole.add("Ligne médiane"); parole.add("Half-way line");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
            }
        });

        button_Centro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList parole = new ArrayList();
                parole.add("Cerchio di centrocampo"); parole.add("Rond central"); parole.add("Centre circle");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
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

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
        });

        /*button_Ruoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityCampoRuoli.class);
                startActivity(intent);
            }
        });*/
    }
}