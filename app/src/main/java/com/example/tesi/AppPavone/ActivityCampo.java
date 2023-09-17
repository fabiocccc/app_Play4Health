package com.example.tesi.AppPavone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.tesi.AppConte.AttivitaUtente;
import com.example.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class ActivityCampo extends AppCompatActivity {

    private FrameLayout button_Porta1;
    private FrameLayout button_Rigore1;
    private FrameLayout button_Porta2;
    private FrameLayout button_Rigore2;
    private FrameLayout button_Angolo1;
    private FrameLayout button_Angolo2;
    private FrameLayout button_Angolo3;
    private FrameLayout button_Angolo4;
    private FrameLayout button_Centro;
    private FrameLayout button_Linea1;
    private FrameLayout button_Linea2;
    private Button button_Ruoli;
    private Spinner spinner;
    private TextToSpeech textToSpeech;
    private FrameLayout button_aiuto;
    private ImageView help1;
    private AnimationDrawable animationDrawable = null;

    DatabaseReference dr;
    private String key;
    private FirebaseUser user;

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
        button_aiuto = findViewById(R.id.button_aiuto);
        help1 = findViewById(R.id.help1);

        user = FirebaseAuth.getInstance().getCurrentUser();

        findViewById(R.id.button_indietro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //controllo se l'utente è loggato
        if(user != null) {


            String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();

            String nomeUtente =  mailLogged.replace("@gmail.com", "");

            trovaKeyUtente(nomeUtente);

        }

    }

    public void trovaKeyUtente(String nomeUtente) {

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

                        scriviAttivitaDb(id, nomeUtente);

                    }


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

        String completato = "Ha eseguito l'attività campo in data:" + " " +formattedDate;

        //String dataFasulla = "29-08-2023";
        AttivitaUtente attivitaUtente = new AttivitaUtente(completato, formattedDate);

        dr.child("utenti").child(key).child("attivita").child(id).setValue(attivitaUtente);

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

        button_Porta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resettaEvidenzia();
                ArrayList parole = new ArrayList();
                parole.add("Area di porta"); parole.add("Surface de but"); parole.add("Goal area");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                button_Porta1.setBackground(getResources().getDrawable(R.drawable.evidenzia_area));

            }
        });

        button_Porta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resettaEvidenzia();
                ArrayList parole = new ArrayList();
                parole.add("Area di porta"); parole.add("Surface de but"); parole.add("Goal area");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                button_Porta2.setBackground(getResources().getDrawable(R.drawable.evidenzia_area));
            }
        });

        button_Rigore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resettaEvidenzia();
                ArrayList parole = new ArrayList();
                parole.add("Area di rigore"); parole.add("Zone de penalty"); parole.add("Penalty area");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                button_Rigore1.setBackground(getResources().getDrawable(R.drawable.evidenzia_area));
            }
        });

        button_Rigore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(animationDrawable != null){
                    animationDrawable.stop();
                    animationDrawable = null;
                    help1.setVisibility(View.GONE);
                    button_aiuto.setVisibility(View.VISIBLE);
                }

                resettaEvidenzia();
                ArrayList parole = new ArrayList();
                parole.add("Area di rigore"); parole.add("Zone de penalty"); parole.add("Penalty area");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                button_Rigore2.setBackground(getResources().getDrawable(R.drawable.evidenzia_area));
            }
        });

        button_Angolo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resettaEvidenzia();
                ArrayList parole = new ArrayList();
                parole.add("Angolo"); parole.add("Corner"); parole.add("Corner");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                button_Angolo1.setBackground(getResources().getDrawable(R.drawable.evidenzia_area));
            }
        });

        button_Angolo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resettaEvidenzia();
                ArrayList parole = new ArrayList();
                parole.add("Angolo"); parole.add("Corner"); parole.add("Corner");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                button_Angolo2.setBackground(getResources().getDrawable(R.drawable.evidenzia_area));
            }
        });

        button_Angolo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resettaEvidenzia();
                ArrayList parole = new ArrayList();
                parole.add("Angolo"); parole.add("Corner"); parole.add("Corner");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                button_Angolo3.setBackground(getResources().getDrawable(R.drawable.evidenzia_area));
            }
        });

        button_Angolo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resettaEvidenzia();
                ArrayList parole = new ArrayList();
                parole.add("Angolo"); parole.add("Corner"); parole.add("Corner");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                button_Angolo4.setBackground(getResources().getDrawable(R.drawable.evidenzia_area));
            }
        });

        button_Linea1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resettaEvidenzia();
                ArrayList parole = new ArrayList();
                parole.add("Linea mediana"); parole.add("Ligne médiane"); parole.add("Half-way line");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                button_Linea1.setBackground(getResources().getDrawable(R.drawable.evidenzia_area));
            }
        });

        button_Linea2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resettaEvidenzia();
                ArrayList parole = new ArrayList();
                parole.add("Linea mediana"); parole.add("Ligne médiane"); parole.add("Half-way line");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                button_Linea2.setBackground(getResources().getDrawable(R.drawable.evidenzia_area));
            }
        });

        button_Centro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resettaEvidenzia();
                ArrayList parole = new ArrayList();
                parole.add("Cerchio di centrocampo"); parole.add("Rond central"); parole.add("Centre circle");
                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                button_Centro.setBackground(getResources().getDrawable(R.drawable.evidenzia_area));
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

        button_Ruoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityCampoRuoli.class);
                finish();
                startActivity(intent);
            }
        });
    }

    public void resettaEvidenzia(){
        button_Porta1.setBackgroundColor(0x0FFFFFF);
        button_Porta2.setBackgroundColor(0x0FFFFFF);
        button_Rigore1.setBackgroundColor(0x0FFFFFF);
        button_Rigore2.setBackgroundColor(0x0FFFFFF);
        button_Angolo1.setBackgroundColor(0x0FFFFFF);
        button_Angolo2.setBackgroundColor(0x0FFFFFF);
        button_Angolo3.setBackgroundColor(0x0FFFFFF);
        button_Angolo4.setBackgroundColor(0x0FFFFFF);
        button_Centro.setBackgroundColor(0x0FFFFFF);
        button_Linea1.setBackgroundColor(0x0FFFFFF);
        button_Linea2.setBackgroundColor(0x0FFFFFF);
    }
}