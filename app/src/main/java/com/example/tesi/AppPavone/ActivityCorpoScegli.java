package com.example.tesi.AppPavone;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tesi.AppConte.AttivitaUtente;
import com.example.tesi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class ActivityCorpoScegli extends AppCompatActivity {

    private ImageView imageView;
    private Button button_Risp1;
    private Button button_Risp2;
    private FloatingActionButton button_Ascolta;
    private TextToSpeech textToSpeech;
    private ImageView esito1;
    private Boolean ascoltato;

    private ArrayList<String> ita;
    private String corretta;

    private FrameLayout button_aiuto;
    private ImageView help1;
    private ImageView help2;
    private ImageView help3;
    private AnimationDrawable animationDrawable = null;
    private AnimationDrawable animationDrawableScelta1 = null;
    private AnimationDrawable animationDrawableScelta2 = null;

    private Button button_avanti;

    DatabaseReference dr;
    private String key;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corpo_scegli);

        button_Ascolta = findViewById(R.id.button_Ascolta);
        button_Risp1 = findViewById(R.id.button_Risp1);
        button_Risp2 = findViewById(R.id.button_Risp2);
        imageView = findViewById(R.id.imageView_CorpoScegli);
        esito1 = findViewById(R.id.esito1);

        button_aiuto = findViewById(R.id.button_aiuto);
        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);
        help3 = findViewById(R.id.help3);

        button_avanti = findViewById(R.id.button_avanti);

        user = FirebaseAuth.getInstance().getCurrentUser();

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

    public void trovaKeyUtente(String nomeUtente, String elementoCliccato) {

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

                        scriviElemento(id, elementoCliccato);

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    public void scriviElemento(String id, String elementoCliccato) {

        System.out.println("ele:"+elementoCliccato);

        ArrayList<String> paroleEseguite = new ArrayList<>();

        dr = FirebaseDatabase.getInstance().getReference();

        DatabaseReference rf = dr.child("utenti").child(key).child("corpo scegli");

        DatabaseReference gf = FirebaseDatabase.getInstance().getReference();


        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                    // A new comment has been added, add it to the displayed list
                    String parola = ds.child("parola").getValue(String.class);

                    paroleEseguite.add(parola);

                }

                if(!paroleEseguite.contains(elementoCliccato)) {

                    gf.child("utenti").child(key).child("corpo scegli").child(id).child("parola").setValue(elementoCliccato);
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

        String completato = "Ha eseguito l'attività completa parti del corpo scegli in data:" + " " +formattedDate;

        //String dataFasulla = "29-08-2023";
        AttivitaUtente attivitaUtente = new AttivitaUtente(completato, formattedDate);

        dr.child("utenti").child(key).child("attivita").child(id).setValue(attivitaUtente);

    }

    @Override
    protected void onStart() {
        super.onStart();

        ascoltato = false;

        button_aiuto.setVisibility(View.VISIBLE);
        esito1.setVisibility(View.GONE);
        esito1.clearAnimation();

        button_Risp1.setBackgroundColor(Color.parseColor("#93C572"));
        button_Risp2.setBackgroundColor(Color.parseColor("#93C572"));

        button_avanti.setVisibility(View.GONE);
        button_avanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();

            }
        });

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

                    if(ascoltato){
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

                        button_aiuto.setVisibility(View.GONE);
                        button_avanti.setVisibility(View.VISIBLE);

                        esito1.setVisibility(View.VISIBLE);
                        esito1.setImageResource(R.drawable.thumbs_up);

                        //controllo se l'utente è loggato
                        if(user != null) {


                            String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                            String nomeUtente =  mailLogged.replace("@gmail.com", "");

                            trovaKeyUtente(nomeUtente, corretta);

                        }

                        esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

                        button_Risp1.setClickable(false);
                        button_Risp2.setClickable(false);

                    } else {
                        help1.setVisibility(View.VISIBLE);

                        animationDrawable = (AnimationDrawable) help1.getBackground();
                        animationDrawable.start();
                        button_aiuto.setVisibility(View.GONE);
                    }


                }
            });

            button_Risp2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(ascoltato){

                        if(animationDrawableScelta1 != null && animationDrawableScelta2 != null) {
                            help2.setVisibility(View.GONE);
                            animationDrawableScelta1.stop();
                            animationDrawableScelta1 = null;

                            help3.setVisibility(View.GONE);
                            animationDrawableScelta2.stop();
                            animationDrawableScelta2 = null;

                            button_aiuto.setVisibility(View.VISIBLE);
                        }

                        button_Risp2.setClickable(false);
                        button_Risp2.setBackgroundColor(Color.parseColor("#f54518"));
                        button_Risp1.setBackgroundColor(Color.parseColor("#50e024"));

                        button_aiuto.setVisibility(View.GONE);
                        button_avanti.setVisibility(View.VISIBLE);

                        esito1.setVisibility(View.VISIBLE);
                        esito1.setImageResource(R.drawable.thumbs_down);

                        esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

                        button_Risp1.setClickable(false);
                        button_Risp2.setClickable(false);
                    } else {
                        help1.setVisibility(View.VISIBLE);

                        animationDrawable = (AnimationDrawable) help1.getBackground();
                        animationDrawable.start();
                        button_aiuto.setVisibility(View.GONE);
                    }

                }
            });

        } else{
            button_Risp1.setText(ita.get(randomSbagliata));
            button_Risp2.setText(corretta);

            button_Risp1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(ascoltato){

                        if(animationDrawableScelta1 != null && animationDrawableScelta2 != null) {
                            help2.setVisibility(View.GONE);
                            animationDrawableScelta1.stop();
                            animationDrawableScelta1 = null;

                            help3.setVisibility(View.GONE);
                            animationDrawableScelta2.stop();
                            animationDrawableScelta2 = null;

                            button_aiuto.setVisibility(View.VISIBLE);
                        }

                        button_Risp1.setClickable(false);
                        button_Risp1.setBackgroundColor(Color.parseColor("#f54518"));
                        button_Risp2.setBackgroundColor(Color.parseColor("#50e024"));

                        button_aiuto.setVisibility(View.GONE);
                        button_avanti.setVisibility(View.VISIBLE);

                        esito1.setVisibility(View.VISIBLE);
                        esito1.setImageResource(R.drawable.thumbs_down);

                        esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

                        button_Risp1.setClickable(false);
                        button_Risp2.setClickable(false);

                    } else {
                        help1.setVisibility(View.VISIBLE);

                        animationDrawable = (AnimationDrawable) help1.getBackground();
                        animationDrawable.start();
                        button_aiuto.setVisibility(View.GONE);
                    }


                }
            });

            button_Risp2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(ascoltato){

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

                        button_aiuto.setVisibility(View.GONE);
                        button_avanti.setVisibility(View.VISIBLE);

                        esito1.setVisibility(View.VISIBLE);
                        esito1.setImageResource(R.drawable.thumbs_up);

                        //controllo se l'utente è loggato
                        if(user != null) {


                            String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                            String nomeUtente =  mailLogged.replace("@gmail.com", "");

                            trovaKeyUtente(nomeUtente, corretta);

                        }

                        esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

                        button_Risp1.setClickable(false);
                        button_Risp2.setClickable(false);
                        button_avanti.setVisibility(View.VISIBLE);

                    } else {
                        help1.setVisibility(View.VISIBLE);

                        animationDrawable = (AnimationDrawable) help1.getBackground();
                        animationDrawable.start();
                        button_aiuto.setVisibility(View.GONE);
                    }
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

                            ascoltato = true;

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