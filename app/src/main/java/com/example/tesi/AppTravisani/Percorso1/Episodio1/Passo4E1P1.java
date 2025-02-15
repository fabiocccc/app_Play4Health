package com.example.tesi.AppTravisani.Percorso1.Episodio1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tesi.AppConte.AttivitaUtente;
import com.example.tesi.AppTravisani.Percorso1.Episodio2.PassiE2P1Activity;
import com.example.tesi.AppTravisani.Percorso1.Episodio2.Passo5E2P1;
import com.example.tesi.AppTravisani.Percorso1.P1EpisodiActivity;
import com.example.tesi.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class Passo4E1P1 extends AppCompatActivity {

    private FrameLayout btn_pause, btn_ripeti;
    private Dialog dialog, findialog; //finestra di dialogo
    private MediaPlayer player;
    private ImageView premio;
    private ImageView help1;
    private FrameLayout button_aiuto;
    private AnimationDrawable animationDrawable1 = null;
    private String urlVoice4;
    private TextView txtTimeFinal;
    private int timeback;
    private String timeScore;
    private String user;
    private Random codutente;

    private String nomePercorso;
    private int episodio2 = 0;

    private int episodio1Completato = 0;

    DatabaseReference dr;
    private String key;
    private FirebaseUser userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo4_e1_p1);

        btn_pause = findViewById(R.id.button_pause);
        btn_ripeti = findViewById(R.id.button_ripeti);
        button_aiuto = findViewById(R.id.button_aiuto);
        premio = findViewById(R.id.imagePremio);
        help1 = findViewById(R.id.help1);
        txtTimeFinal = findViewById(R.id.txtTimeEP1P1);

        dialog= new Dialog(this);
        findialog = new Dialog(this);
        nomePercorso = new String();


        //gestione tempo
        codutente = new Random();
        user = String.valueOf(codutente.nextInt()); // genera un cod utente casuale
        timeback = getIntent().getExtras().getInt("time");
        timeScore = "00:" + timeback; // tempo da salvare su Firebase


        userDb = FirebaseAuth.getInstance().getCurrentUser();

        //controllo se l'utente è loggato
        if(userDb != null) {

            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                nomePercorso = extras.getString("percorso1");
                episodio1Completato = extras.getInt("percorso1Fatto");
            }


            String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();

            String nomeUtente =  mailLogged.replace("@gmail.com", "");

            passiE2Completati(new Passo4E1P1.MyCallback() {

                public void onCallback(int numeroEpisodi, String key) {

                    //vale 1 se ha completato il percorso 2
                    episodio2 = numeroEpisodi;
                }


            });

            trovaKeyUtente(nomeUtente);

        }

        urlVoice4 = "https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Fita%2FFine%20E1P1.mp3?alt=media&token=aabb6c07-8591-415a-94df-f199a76abc4f";
        playsound(urlVoice4);


        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openCustomWindow();

            }
        });

        btn_ripeti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playsound(urlVoice4);
            }
        });

        button_aiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                help1.setVisibility(View.VISIBLE);

                animationDrawable1 = (AnimationDrawable) help1.getBackground();
                animationDrawable1.start();

                button_aiuto.setVisibility(View.GONE);

            }
        });

        premio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUPFinePercorso();
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

                        scriviAttivitaDb();

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    public void scriviAttivitaDb() {

        DatabaseReference dr2 = FirebaseDatabase.getInstance().getReference();

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        DatabaseReference rf2 = dr2.child("utenti").child(key).child("percorsi").child("TimeP1").child("P1E1");
        DatabaseReference gf = FirebaseDatabase.getInstance().getReference();

        rf2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int numeroEpisodi = 0;
                for(DataSnapshot ds : snapshot.getChildren()) {
                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                    // A new comment has been added, add it to the displayed list

                    numeroEpisodi = numeroEpisodi + 1;


                        rf2.removeEventListener(this);

                }
                //controllo che l'episodio non sia stato svolto
                if(numeroEpisodi == 0) {
                    String completato = "il gioco del calcio" + " "+"episodio 1";

                    AttivitaUtente attivitaUtente = new AttivitaUtente(completato, timeScore,formattedDate);
                    FirebaseDatabase.getInstance().getReference().child("utenti").child(key).child("percorsi").child("TimeP1").child("P1E1").child(user).setValue(attivitaUtente);
                    txtTimeFinal.setText(timeScore);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    public interface MyCallback {
        void onCallback(int numeroEpisodi, String key);
    }

    public void passiE2Completati(Passo4E1P1.MyCallback myCallback) {

        String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        String nomeUtente =  mailLogged.replace("@gmail.com", "");

        dr = FirebaseDatabase.getInstance().getReference();

        //prendo la key dello user loggato

        DatabaseReference rf = dr.child("utenti");
        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    String username = postSnapshot.child("username").getValue(String.class);
                    if (username.equals(nomeUtente)) {

                        //salvo la key dell'utente loggato per controllare qunati percorsi ha svolto
                        key = postSnapshot.getKey();

                        ///

                        DatabaseReference rf2 = dr.child("utenti").child(key).child("percorsi").child("TimeP1").child("P1E2");

                        rf2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int numeroEpisodi = 0;
                                for(DataSnapshot ds : snapshot.getChildren()) {
                                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                                    // A new comment has been added, add it to the displayed list
                                    numeroEpisodi = numeroEpisodi + 1;

                                }
                                myCallback.onCallback(numeroEpisodi, key);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        ///



                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }


    private void playsound(String urlVoice)  {

        if (player == null) {
            player = MediaPlayer.create(getApplicationContext(), Uri.parse(urlVoice)) ;
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {

                    stopPlayer();
                    active_premio();
                    help1.setVisibility(View.VISIBLE);
                    animationDrawable1 = (AnimationDrawable) help1.getBackground();
                    animationDrawable1.start();
                    button_aiuto.setVisibility(View.VISIBLE);
                }
            });
        }

        player.start();

    }

    private void active_premio() {

        premio.setVisibility(View.VISIBLE);
        //setta animazione lampeggiante
        //sul pulsante gioca
        YoYo.with(Techniques.Pulse)
                .duration(700)
                .repeat(10)
                .playOn(premio);

    }

    private void stopPlayer() {

        if (player != null) {
            player.release();
            player = null;
            // Toast.makeText(context, "MediaPlayer releases", Toast.LENGTH_SHORT).show();
        }
    }

    private void openCustomWindow() {

        stopPlayer();

        dialog.setContentView(R.layout.pausa_dialoglayout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
        Button btnSi = dialog.findViewById(R.id.btnSi);
        Button btnNo = dialog.findViewById(R.id.btnNo);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                playsound(urlVoice4);
            }
        });


        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent i = new Intent(getApplicationContext(), PassiE1P1Activity.class);
                i.putExtra("percorso1Fatto", episodio1Completato);
                startActivity(i);
                finish();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                playsound(urlVoice4);
            }
        });

        dialog.show();
    }
    private void PopUPFinePercorso() {

        stopPlayer();

        findialog.setContentView(R.layout.fine_livellolayout);
        findialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = findialog.findViewById(R.id.ClosPoPUp);
        ImageView imageViewStar = findialog.findViewById(R.id.imageViewStar);
        Button btnRipetiLivello = findialog.findViewById(R.id.btnRipetiLivello);
        Button btnAvanti = findialog.findViewById(R.id.btnAvanti);
        TextView popUpTitle = findialog.findViewById(R.id.titlePoPUP);
        TextView popUpMessage = findialog.findViewById(R.id.MessagePopUP);

        popUpTitle.setText("Hai superato l'episodio 1");
        popUpMessage.setText("Hai guadagnato 2 punti");

        imageViewStar.setImageDrawable(getDrawable(R.drawable.star1));


        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                findialog.dismiss();
                Intent i = new Intent(getApplicationContext(), PassiE1P1Activity.class);
                i.putExtra("percorso1Fatto", 1);
                startActivity(i);
                finish();


            }
        });


        btnRipetiLivello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findialog.dismiss();
                Intent i = new Intent(getApplicationContext(), Passo1E1P1.class);
                i.putExtra("percorso1Fatto", 1);
                startActivity(i);
                finish();
            }
        });

        btnAvanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                findialog.dismiss();
                Intent i = new Intent(getApplicationContext(), PassiE2P1Activity.class);

                //se ha completato l'episodio 2
                i.putExtra("percorso2Fatto",episodio2);
                startActivity(i);
                finish();

            }
        });

        findialog.show();
    }

    @Override
    public void onBackPressed() {
        openCustomWindow();
    }
}