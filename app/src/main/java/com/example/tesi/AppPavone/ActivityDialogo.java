package com.example.tesi.AppPavone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class ActivityDialogo extends AppCompatActivity implements View.OnClickListener{

    private FrameLayout tab1;
    private FrameLayout tab2;
    private Button button_risp1;
    private Button button_risp2;
    private Button button_risp3;
    private Button button_risp4;
    private Button button_risp5;
    private Button button_risp6;
    private ImageView ita;
    private ImageView eng;
    private ImageView fra;
    private TextToSpeech textToSpeech;
    private TextView textMedico;
    private TextView textPaziente;
    private ImageView imageBalloonMedico;
    private ImageView imageBalloonPaziente;
    private FrameLayout button_indietro;
    private String itaStringMedico;
    private String fraStringMedico;
    private String engStringMedico;
    private String itaStringPaziente;
    private String fraStringPaziente;
    private String engStringPaziente;
    private Map<Integer, ArrayList> domande = new HashMap<>();
    private Map<Integer, Map> risposte = new HashMap<>();
    private int dialogoScelto;
    private int rispScelta;
    private int lingua;
    private ImageView risp1;
    private ImageView risp2;
    private ImageView risp3;
    private ImageView risp4;
    private ImageView risp5;
    private ImageView risp6;

    DatabaseReference dr;
    private String key;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogo);

        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        risp1 = findViewById(R.id.risp1);
        risp2 = findViewById(R.id.risp2);
        risp3 = findViewById(R.id.risp3);
        risp4 = findViewById(R.id.risp4);
        risp5 = findViewById(R.id.risp5);
        risp6 = findViewById(R.id.risp6);
        button_risp1 = findViewById(R.id.button_risp1);
        button_risp2 = findViewById(R.id.button_risp2);
        button_risp3 = findViewById(R.id.button_risp3);
        button_risp4 = findViewById(R.id.button_risp4);
        button_risp5 = findViewById(R.id.button_risp5);
        button_risp6 = findViewById(R.id.button_risp6);
        ita  = findViewById(R.id.ita);
        eng = findViewById(R.id.eng);
        fra = findViewById(R.id.fra);
        textMedico = findViewById(R.id.textMedico);
        textPaziente = findViewById(R.id.textPaziente);
        imageBalloonMedico = findViewById(R.id.imageBalloonMedico);
        imageBalloonPaziente = findViewById(R.id.imageBalloonPaziente);
        button_indietro = findViewById(R.id.button_indietro);

        imageBalloonPaziente.setVisibility(View.INVISIBLE);
        textPaziente.setVisibility(View.INVISIBLE);

        user = FirebaseAuth.getInstance().getCurrentUser();

        //controllo se l'utente è loggato
        if(user != null) {


            String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();

            String nomeUtente =  mailLogged.replace("@gmail.com", "");

            trovaKeyUtente(nomeUtente);

        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<String> domanda1 = new ArrayList<>();
        domanda1.add("Come ti senti?"); domanda1.add("Comment tu te sens?"); domanda1.add("How are you feeling?");

        ArrayList<String> risp1_1 = new ArrayList<>();
        risp1_1.add("Sono in piena forma"); risp1_1.add("Je suis en pleine forme"); risp1_1.add("I am in great shape");

        ArrayList<String> risp1_2 = new ArrayList<>();
        risp1_2.add("Sono malato"); risp1_2.add("Je suis malade"); risp1_2.add("I am ill");

        ArrayList<String> risp1_3 = new ArrayList<>();
        risp1_3.add("Mi sento male"); risp1_3.add("Je me sens mal"); risp1_3.add("I don’t feel good");

        ArrayList<String> risp1_4 = new ArrayList<>();
        risp1_4.add("Ho l’influenza"); risp1_4.add("J’ai la grippe"); risp1_4.add("I got the flu");

        ArrayList<String> risp1_5 = new ArrayList<>();
        risp1_5.add("Ho la febbre"); risp1_5.add("J’ai la fièvre"); risp1_5.add("I have a fever");

        ArrayList<String> risp1_6 = new ArrayList<>();
        risp1_6.add("Ho il raffreddore"); risp1_6.add("J’ai un rhume"); risp1_6.add("I have a cold");

        Map<Integer, ArrayList> risposte1 = new HashMap<>();
        risposte1.put(1, risp1_1);
        risposte1.put(2, risp1_2);
        risposte1.put(3, risp1_3);
        risposte1.put(4, risp1_4);
        risposte1.put(5, risp1_5);
        risposte1.put(6, risp1_6);

        domande.put(1, domanda1);
        risposte.put(1, risposte1);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<String> domanda2 = new ArrayList<>();
        domanda2.add("Dove ti fa male?"); domanda2.add("Tu as mal où?"); domanda2.add("Where does it hurt?");

        ArrayList<String> risp2_1 = new ArrayList<>();
        risp2_1.add("Ho mal di gola"); risp2_1.add("J’ai mal à la gorge"); risp2_1.add("I have a sore throat");

        ArrayList<String> risp2_2 = new ArrayList<>();
        risp2_2.add("Ho la tosse"); risp2_2.add("J’ai la toux"); risp2_2.add("I have a cough");

        ArrayList<String> risp2_3 = new ArrayList<>();
        risp2_3.add("Ho mal di testa"); risp2_3.add("J’ai mal à la tête"); risp2_3.add("I have a headache");

        ArrayList<String> risp2_4 = new ArrayList<>();
        risp2_4.add("Ho mal di pancia"); risp2_4.add("J’ai mal au ventre"); risp2_4.add("My heart aches");

        ArrayList<String> risp2_5 = new ArrayList<>();
        risp2_5.add("Mi fa male il cuore"); risp2_5.add("My heart aches"); risp2_5.add("My heart aches");

        ArrayList<String> risp2_6 = new ArrayList<>();
        risp2_6.add("Ho mal di denti"); risp2_6.add("J’ai mal aux dents "); risp2_6.add("I have a toothache");

        Map<Integer, ArrayList> risposte2 = new HashMap<>();;
        risposte2.put(1, risp2_1);
        risposte2.put(2, risp2_2);
        risposte2.put(3, risp2_3);
        risposte2.put(4, risp2_4);
        risposte2.put(5, risp2_5);
        risposte2.put(6, risp2_6);

        domande.put(2, domanda2);
        risposte.put(2, risposte2);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        tab1.setBackgroundResource(R.drawable.tabdialogoscelto);
        dialogoScelto = 1;
        lingua = 1;

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

        String completato = "L'utente" + " " + nomeUtente + " " + "ha eseguito l'attività parla con il medico in data:" + " " +formattedDate;

        //String dataFasulla = "29-08-2023";
        AttivitaUtente attivitaUtente = new AttivitaUtente(completato, formattedDate);

        dr.child("utenti").child(key).child("attivita").child(id).setValue(attivitaUtente);

    }

    @Override
    protected void onStart() {
        super.onStart();

        button_indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button_risp1.setOnClickListener(this);
        button_risp2.setOnClickListener(this);
        button_risp3.setOnClickListener(this);
        button_risp4.setOnClickListener(this);
        button_risp5.setOnClickListener(this);
        button_risp6.setOnClickListener(this);
        ita.setOnClickListener(this);
        fra.setOnClickListener(this);
        eng.setOnClickListener(this);
        imageBalloonMedico.setOnClickListener(this);
        imageBalloonPaziente.setOnClickListener(this);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(Locale.ITALIAN);
                textToSpeech.speak((String) domande.get(dialogoScelto).get(0), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        risp1.setImageResource(R.drawable.dialogo1_1);
        risp2.setImageResource(R.drawable.dialogo1_2);
        risp3.setImageResource(R.drawable.dialogo1_3);
        risp4.setImageResource(R.drawable.dialogo1_4);
        risp5.setImageResource(R.drawable.dialogo1_5);
        risp6.setImageResource(R.drawable.dialogo1_6);

        textMedico.setText((String) domande.get(dialogoScelto).get(0));
        imageBalloonMedico.setVisibility(View.VISIBLE);
        imageBalloonMedico.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_balloon));
        textMedico.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_balloon_text));

    }

    public void animazione() {
        textPaziente.setVisibility(View.VISIBLE);
        imageBalloonPaziente.setVisibility(View.VISIBLE);
        imageBalloonPaziente.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_balloon_paz));
        textPaziente.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_balloon_text_paz));
    }

    private void deleseziona(){
        textMedico.setText((String) domande.get(dialogoScelto).get(0));
        button_risp1.setBackgroundColor(Color.parseColor("#ffffff"));
        button_risp2.setBackgroundColor(Color.parseColor("#ffffff"));
        button_risp3.setBackgroundColor(Color.parseColor("#ffffff"));
        button_risp4.setBackgroundColor(Color.parseColor("#ffffff"));
        button_risp5.setBackgroundColor(Color.parseColor("#ffffff"));
        button_risp6.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_risp1:
                deleseziona();
                ArrayList<String> risp = (ArrayList<String>) risposte.get(dialogoScelto).get(1);
                textPaziente.setText((String) risp.get(0));
                button_risp1.setBackgroundColor(Color.parseColor("#50e024"));
                rispScelta = 1;
                animazione();

                textToSpeech.setLanguage(Locale.ITALIAN);
                textToSpeech.speak((String) textPaziente.getText(), TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.button_risp2:
                deleseziona();
                risp = (ArrayList<String>) risposte.get(dialogoScelto).get(2);
                textPaziente.setText((String) risp.get(0));
                button_risp2.setBackgroundColor(Color.parseColor("#50e024"));
                rispScelta = 2;
                animazione();

                textToSpeech.setLanguage(Locale.ITALIAN);
                textToSpeech.speak((String) textPaziente.getText(), TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.button_risp3:
                deleseziona();
                risp = (ArrayList<String>) risposte.get(dialogoScelto).get(3);
                textPaziente.setText((String) risp.get(0));
                button_risp3.setBackgroundColor(Color.parseColor("#50e024"));
                rispScelta = 3;
                animazione();

                textToSpeech.setLanguage(Locale.ITALIAN);
                textToSpeech.speak((String) textPaziente.getText(), TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.button_risp4:
                deleseziona();
                 risp = (ArrayList<String>) risposte.get(dialogoScelto).get(4);
                textPaziente.setText((String) risp.get(0));
                button_risp4.setBackgroundColor(Color.parseColor("#50e024"));
                rispScelta = 4;
                animazione();

                textToSpeech.setLanguage(Locale.ITALIAN);
                textToSpeech.speak((String) textPaziente.getText(), TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.button_risp5:
                deleseziona();
                risp = (ArrayList<String>) risposte.get(dialogoScelto).get(5);
                textPaziente.setText((String) risp.get(0));
                button_risp5.setBackgroundColor(Color.parseColor("#50e024"));
                rispScelta = 5;
                animazione();

                textToSpeech.setLanguage(Locale.ITALIAN);
                textToSpeech.speak((String) textPaziente.getText(), TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.button_risp6:
                deleseziona();
                risp = (ArrayList<String>) risposte.get(dialogoScelto).get(6);
                textPaziente.setText((String) risp.get(0));
                button_risp6.setBackgroundColor(Color.parseColor("#50e024"));
                rispScelta = 6;
                animazione();

                textToSpeech.setLanguage(Locale.ITALIAN);
                textToSpeech.speak((String) textPaziente.getText(), TextToSpeech.QUEUE_FLUSH, null);
                break;

            //////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////////////////////
            case R.id.ita:
                lingua = 1;
                switch (rispScelta) {
                    case 1:
                        textMedico.setText((String) domande.get(dialogoScelto).get(0));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(1);
                        textPaziente.setText((String) risp.get(0));
                        break;
                    case 2:
                        textMedico.setText((String) domande.get(dialogoScelto).get(0));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(2);
                        textPaziente.setText((String) risp.get(0));
                        break;
                    case 3:
                        textMedico.setText((String) domande.get(dialogoScelto).get(0));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(3);
                        textPaziente.setText((String) risp.get(0));
                        break;
                    case 4:
                        textMedico.setText((String) domande.get(dialogoScelto).get(0));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(4);
                        textPaziente.setText((String) risp.get(0));
                        break;
                    case 5:
                        textMedico.setText((String) domande.get(dialogoScelto).get(0));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(5);
                        textPaziente.setText((String) risp.get(0));
                        break;
                    case 6:
                        textMedico.setText((String) domande.get(dialogoScelto).get(0));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(6);
                        textPaziente.setText((String) risp.get(0));
                        break;
                }

                break;
            case R.id.fra:
                lingua = 2;
                switch (rispScelta) {
                    case 1:
                        textMedico.setText((String) domande.get(dialogoScelto).get(1));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(1);
                        textPaziente.setText((String) risp.get(1));
                        break;
                    case 2:
                        textMedico.setText((String) domande.get(dialogoScelto).get(1));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(2);
                        textPaziente.setText((String) risp.get(1));
                        break;
                    case 3:
                        textMedico.setText((String) domande.get(dialogoScelto).get(1));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(3);
                        textPaziente.setText((String) risp.get(1));
                        break;
                    case 4:
                        textMedico.setText((String) domande.get(dialogoScelto).get(1));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(4);
                        textPaziente.setText((String) risp.get(1));
                        break;
                    case 5:
                        textMedico.setText((String) domande.get(dialogoScelto).get(1));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(5);
                        textPaziente.setText((String) risp.get(1));
                        break;
                    case 6:
                        textMedico.setText((String) domande.get(dialogoScelto).get(1));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(6);
                        textPaziente.setText((String) risp.get(1));
                        break;
                }
                break;
            case R.id.eng:
                lingua = 3;
                switch (rispScelta) {
                    case 1:
                        textMedico.setText((String) domande.get(dialogoScelto).get(2));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(1);
                        textPaziente.setText((String) risp.get(2));
                        break;
                    case 2:
                        textMedico.setText((String) domande.get(dialogoScelto).get(2));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(2);
                        textPaziente.setText((String) risp.get(2));
                        break;
                    case 3:
                        textMedico.setText((String) domande.get(dialogoScelto).get(2));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(3);
                        textPaziente.setText((String) risp.get(2));
                        break;
                    case 4:
                        textMedico.setText((String) domande.get(dialogoScelto).get(2));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(4);
                        textPaziente.setText((String) risp.get(2));
                        break;
                    case 5:
                        textMedico.setText((String) domande.get(dialogoScelto).get(2));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(5);
                        textPaziente.setText((String) risp.get(2));
                        break;
                    case 6:
                        textMedico.setText((String) domande.get(dialogoScelto).get(2));
                        risp = (ArrayList<String>) risposte.get(dialogoScelto).get(6);
                        textPaziente.setText((String) risp.get(2));
                        break;
                }
                break;
            case R.id.imageBalloonMedico:
                if(lingua == 1){
                    textToSpeech.setLanguage(Locale.ITALIAN);
                    textToSpeech.speak((String) textMedico.getText(), TextToSpeech.QUEUE_FLUSH, null);
                } else if(lingua == 2){
                    textToSpeech.setLanguage(Locale.FRENCH);
                    textToSpeech.speak((String) textMedico.getText(), TextToSpeech.QUEUE_FLUSH, null);
                } else if(lingua == 3){
                    textToSpeech.setLanguage(Locale.ENGLISH);
                    textToSpeech.speak((String) textMedico.getText(), TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
            case R.id.imageBalloonPaziente:
                if(lingua == 1){
                    textToSpeech.setLanguage(Locale.ITALIAN);
                    textToSpeech.speak((String) textPaziente.getText(), TextToSpeech.QUEUE_FLUSH, null);
                } else if(lingua == 2){
                    textToSpeech.setLanguage(Locale.FRENCH);
                    textToSpeech.speak((String) textPaziente.getText(), TextToSpeech.QUEUE_FLUSH, null);
                } else if(lingua == 3){
                    textToSpeech.setLanguage(Locale.ENGLISH);
                    textToSpeech.speak((String) textPaziente.getText(), TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
            case R.id.tab1:
                deleseziona();
                dialogoScelto = 1;
                imageBalloonPaziente.setVisibility(View.INVISIBLE);
                textPaziente.setVisibility(View.INVISIBLE);
                tab2.setBackgroundResource(R.drawable.tabdialogo);
                tab1.setBackgroundResource(R.drawable.tabdialogoscelto);

                textMedico.setText((String) domande.get(dialogoScelto).get(0));
                imageBalloonMedico.setVisibility(View.VISIBLE);
                imageBalloonMedico.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_balloon));
                textMedico.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_balloon_text));
                textToSpeech.setLanguage(Locale.ITALIAN);
                textToSpeech.speak((String) domande.get(dialogoScelto).get(0), TextToSpeech.QUEUE_FLUSH, null);

                risp1.setImageResource(R.drawable.dialogo1_1);
                risp2.setImageResource(R.drawable.dialogo1_2);
                risp3.setImageResource(R.drawable.dialogo1_3);
                risp4.setImageResource(R.drawable.dialogo1_4);
                risp5.setImageResource(R.drawable.dialogo1_5);
                risp6.setImageResource(R.drawable.dialogo1_6);

                break;
            case R.id.tab2:
                deleseziona();
                dialogoScelto = 2;
                imageBalloonPaziente.setVisibility(View.INVISIBLE);
                textPaziente.setVisibility(View.INVISIBLE);
                tab1.setBackgroundResource(R.drawable.tabdialogo);
                tab2.setBackgroundResource(R.drawable.tabdialogoscelto);

                textMedico.setText((String) domande.get(dialogoScelto).get(0));
                imageBalloonMedico.setVisibility(View.VISIBLE);
                imageBalloonMedico.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_balloon));
                textMedico.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_balloon_text));
                textToSpeech.setLanguage(Locale.ITALIAN);
                textToSpeech.speak((String) domande.get(dialogoScelto).get(0), TextToSpeech.QUEUE_FLUSH, null);

                risp1.setImageResource(R.drawable.dialogo2_1);
                risp2.setImageResource(R.drawable.dialogo2_2);
                risp3.setImageResource(R.drawable.dialogo2_3);
                risp4.setImageResource(R.drawable.dialogo2_4);
                risp5.setImageResource(R.drawable.dialogo2_5);
                risp6.setImageResource(R.drawable.dialogo2_6);

                break;
        }
    }
}