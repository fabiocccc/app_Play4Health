package com.example.tesi.AppTravisani.Percorso1.Episodio3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tesi.AppTravisani.Percorso1.Episodio1.PassiE1P1Activity;
import com.example.tesi.AppTravisani.Percorso1.Episodio2.PassiE2P1Activity;
import com.example.tesi.AppTravisani.Percorso1.Episodio2.Passo1E2P1;
import com.example.tesi.AppTravisani.StoryCard;
import com.example.tesi.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Passo5E3P1 extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo5_e3_p1);

        btn_pause = findViewById(R.id.button_pause);
        btn_ripeti = findViewById(R.id.button_ripeti);
        button_aiuto = findViewById(R.id.button_aiuto);
        premio = findViewById(R.id.imagePremio);
        help1 = findViewById(R.id.help1);
        txtTimeFinal = findViewById(R.id.txtTimeEP1P1);


        dialog= new Dialog(this);
        findialog = new Dialog(this);

        //gestione tempo
        codutente = new Random();
        user = String.valueOf(codutente.nextInt()); // genera un cod utente casuale
        timeback = getIntent().getExtras().getInt("time");
        timeScore = "Tempo 00:" + timeback; // tempo da salvare su Firebase
        //salvataggio su Firebase del tempo
        FirebaseDatabase.getInstance().getReference().child("TimeP1").child("P1E3").child(user).setValue(timeScore);
        txtTimeFinal.setText(timeScore);

        urlVoice4 = "https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Fita%2FFine%20E3P1.mp3?alt=media&token=36e50be5-703d-4b82-afa0-fc5a2d7c7af3";
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
                Intent i = new Intent(getApplicationContext(), PassiE3P1Activity.class);
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

        popUpTitle.setText("Hai superato il Percorso 1");
        popUpMessage.setText("Hai guadagnato 5 punti e concluso l'episodio 3");

        imageViewStar.setImageDrawable(getDrawable(R.drawable.stella));


        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findialog.dismiss();
                Intent i = new Intent(getApplicationContext(), PassiE3P1Activity.class);
                i.putExtra("flagDo",4);
                startActivity(i);
                finish();
            }
        });


        btnRipetiLivello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findialog.dismiss();
                Intent i = new Intent(getApplicationContext(), Passo1E3P1.class);
                startActivity(i);
                finish();
            }
        });

        btnAvanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                findialog.dismiss();
                Intent i = new Intent(getApplicationContext(), StoryCard.class);
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