package com.example.tesi.AppTravisani.Percorso1.Episodio1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tesi.R;

public class Passo2E1P1 extends AppCompatActivity {

    private FrameLayout btn_pause, btn_ripeti;
    private FrameLayout button_aiuto;
    private AnimationDrawable animationDrawable1 = null;
    private ImageView help1;
    private String urlVoice1, urlVoice2;
    private MediaPlayer player;
    private GridLayout layoutrisp2;
    private Dialog dialog; //finestra di dialogo
    private CardView risp5, risp11, risp13, risp15;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo2_e1_p1);

        button_aiuto = findViewById(R.id.button_aiuto);
        btn_pause = findViewById(R.id.button_pause);
        btn_ripeti = findViewById(R.id.button_ripeti);
        help1 = findViewById(R.id.help1);
        layoutrisp2 = findViewById(R.id.RispGiocatore2);
        risp5 = findViewById(R.id.card5);
        risp11 = findViewById(R.id.card11);
        risp13 = findViewById(R.id.card13);
        risp15 = findViewById(R.id.card15);

        dialog= new Dialog(this);

        urlVoice1 = "https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Fita%2FNro%20Giocatori%20Squadra.mp3?alt=media&token=52215c75-d967-4eb6-9132-1a50fb1ad70c";
        playsound(urlVoice1);

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //programmare popup fine percorso con custom dialog
                openCustomWindow();
                //  Toast.makeText(Passo1P1.this, "Hai cliccato stop percorso", Toast.LENGTH_SHORT).show();

            }
        });

        btn_ripeti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    playsound(urlVoice1);

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

        risp5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                risp5.setBackgroundColor(Color.RED);
            }
        });

        risp11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                risp11.setBackgroundColor(Color.GREEN);
                //PASSO 3 EPISODIO 1 P1
                Intent i = new Intent(getApplicationContext(), Passo3E1P1.class);
                startActivity(i);
                finish();
            }
        });


        risp13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                risp13.setBackgroundColor(Color.RED);
            }
        });

        risp15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                risp15.setBackgroundColor(Color.RED);
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
                    layoutrisp2.setVisibility(View.VISIBLE);
                    button_aiuto.setVisibility(View.VISIBLE);
                }
            });
        }

        player.start();

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
                playsound(urlVoice1);
            }
        });


        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent i = new Intent(getApplicationContext(), PassiE1P1Activity.class);
                startActivity(i);
                finish();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                playsound(urlVoice1);
            }
        });

        dialog.show();
    }


}