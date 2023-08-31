package com.example.tesi.AppPavone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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

public class ActivityMedico extends AppCompatActivity implements View.OnClickListener {

    private Button button_letto;
    private Button button_freddo;
    private Button button_sci;
    private Button button_zuccheri;
    private Button button_pomata;
    private Button button_compresse;
    private ImageView ita;
    private ImageView eng;
    private ImageView fra;
    private TextToSpeech textToSpeech;
    private TextView textView;
    private String itaString;
    private String fraString;
    private String engString;
    private ImageView image_Balloon;
    private FrameLayout button_indietro;

    DatabaseReference dr;
    private String key;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico);

        image_Balloon = findViewById(R.id.imageBalloon);
        button_letto = findViewById(R.id.button_letto);
        button_freddo = findViewById(R.id.button_freddo);
        button_sci = findViewById(R.id.button_sci);
        button_zuccheri = findViewById(R.id.button_zuccheri);
        button_pomata = findViewById(R.id.button_pomata);
        button_compresse = findViewById(R.id.button_compresse);
        ita = findViewById(R.id.ita);
        fra = findViewById(R.id.fra);
        eng = findViewById(R.id.eng);
        ita.setVisibility(View.INVISIBLE);
        fra.setVisibility(View.INVISIBLE);
        eng.setVisibility(View.INVISIBLE);
        image_Balloon.setVisibility(View.INVISIBLE);
        button_indietro = findViewById(R.id.button_indietro);

        textView = findViewById(R.id.textView);

        user = FirebaseAuth.getInstance().getCurrentUser();

        //controllo se l'utente è loggato
        if(user != null) {


            String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();

            String nomeUtente =  mailLogged.replace("@gmail.com", "");

            trovaKeyUtente(nomeUtente);

        }

        button_indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

        String completato = "L'utente" + " " + nomeUtente + " " + "ha eseguito l'attività il medico consiglia in data:" + " " +formattedDate;

        //String dataFasulla = "29-08-2023";
        AttivitaUtente attivitaUtente = new AttivitaUtente(completato, formattedDate);

        dr.child("utenti").child(key).child("attivita").child(id).setValue(attivitaUtente);

    }

    @Override
    protected void onStart() {
        super.onStart();

        button_letto.setOnClickListener(this);
        button_freddo.setOnClickListener(this);
        button_sci.setOnClickListener(this);
        button_zuccheri.setOnClickListener(this);
        button_pomata.setOnClickListener(this);
        button_compresse.setOnClickListener(this);
        ita.setOnClickListener(this);
        fra.setOnClickListener(this);
        eng.setOnClickListener(this);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });



    }

    private void visibili(){
        ita.setVisibility(View.VISIBLE);
        fra.setVisibility(View.VISIBLE);
        eng.setVisibility(View.VISIBLE);
        button_letto.setBackgroundColor(Color.parseColor("#FFFFFF"));
        button_freddo.setBackgroundColor(Color.parseColor("#FFFFFF"));
        button_sci.setBackgroundColor(Color.parseColor("#FFFFFF"));
        button_zuccheri.setBackgroundColor(Color.parseColor("#FFFFFF"));
        button_pomata.setBackgroundColor(Color.parseColor("#FFFFFF"));
        button_compresse.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    private void riproduci(){
        textToSpeech.setLanguage(Locale.ITALIAN);
        textToSpeech.speak(itaString, TextToSpeech.QUEUE_FLUSH, null);
        image_Balloon.setVisibility(View.VISIBLE);
        textView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_balloon_text));
        image_Balloon.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_balloon));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_letto:
                visibili();
                itaString = "Resta a letto!";
                fraString = "Restez au lit!";
                engString = "Stay in bed!";
                textView.setText(itaString);
                button_letto.setBackgroundColor(Color.parseColor("#50e024"));
                riproduci();

                break;
            case R.id.button_freddo:
                visibili();
                itaString = "Non prendere il raffreddore!";
                fraString = "Ne prenez pas froid!";
                engString = "Don't catch a cold!";
                textView.setText(itaString);
                button_freddo.setBackgroundColor(Color.parseColor("#50e024"));
                riproduci();

                break;
            case R.id.button_sci:
                visibili();
                itaString = "Non sciare più!";
                fraString = "Ne faites plus de ski!";
                engString = "Don't ski anymore!";
                textView.setText(itaString);
                button_sci.setBackgroundColor(Color.parseColor("#50e024"));
                riproduci();
                break;
            case R.id.button_zuccheri:
                visibili();
                itaString = "Non mangiare più zuccheri!";
                fraString = "Ne mangez pas de sucreries!";
                engString = "Don't eat sweets!";
                textView.setText(itaString);
                button_zuccheri.setBackgroundColor(Color.parseColor("#50e024"));
                riproduci();
                break;
            case R.id.button_pomata:
                visibili();
                itaString = "Applica una pomata!";
                fraString = "Passez une pommade!";
                engString = "Apply an ointment!";
                textView.setText(itaString);
                button_pomata.setBackgroundColor(Color.parseColor("#50e024"));
                riproduci();
                break;
            case R.id.button_compresse:
                visibili();
                itaString = "Prendi queste compresse!";
                fraString = "Prenez ces comprimés!";
                engString = "Take these pills!";
                textView.setText(itaString);
                button_compresse.setBackgroundColor(Color.parseColor("#50e024"));
                riproduci();
                break;
            case R.id.ita:
                textView.setText(itaString);
                textToSpeech.setLanguage(Locale.ITALIAN);
                textToSpeech.speak(itaString, TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.fra:
                textView.setText(fraString);
                textToSpeech.setLanguage(Locale.FRENCH);
                textToSpeech.speak(fraString, TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.eng:
                textView.setText(engString);
                textToSpeech.setLanguage(Locale.ENGLISH);
                textToSpeech.speak(engString, TextToSpeech.QUEUE_FLUSH, null);
                break;

        }
    }
}