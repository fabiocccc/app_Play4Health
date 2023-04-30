package com.example.tesi.AppTravisani.P1Passi;

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

import com.example.tesi.AppTravisani.Home;
import com.example.tesi.R;

public class Passo2P1 extends AppCompatActivity {

    private MediaPlayer player;
    private GridLayout layoutRispMedico,layoutRispMedico2 ;
    private FrameLayout btn_pause, btn_ripeti;
    private ImageView help1, help2;
    private FrameLayout button_aiuto;
    private AnimationDrawable animationDrawable1 = null;
    private AnimationDrawable animationDrawable2 = null;
    private Dialog dialog; //finestra di dialogo
    private String urlVoice2;
    private ImageView dolci, frutta;
    private CardView rispDolcicard, rispFruttacard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo2_p1);
        dolci= findViewById(R.id.dolci);
        frutta= findViewById(R.id.frutta);
        btn_pause = findViewById(R.id.button_pause);
        btn_ripeti = findViewById(R.id.button_ripeti);
        button_aiuto = findViewById(R.id.button_aiuto);
        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);
        layoutRispMedico = findViewById(R.id.RispMedico);
        layoutRispMedico2 = findViewById(R.id.RispMedico3);
        rispDolcicard = findViewById(R.id.cardDolci);
        rispFruttacard = findViewById(R.id.cardFrutta);

        dialog= new Dialog(this);

        urlVoice2="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FMedico%20dolci%20o%20carne.mp3?alt=media&token=7a74e88b-4d4c-4d4c-b672-2be292f3bc30";
        playsound(urlVoice2);

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //programmare popup fine percorso con custom dialog
                openCustomWindow();
            }
        });

        btn_ripeti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playsound(urlVoice2);
            }
        });

        button_aiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                help1.setVisibility(View.VISIBLE);
                help2.setVisibility(View.VISIBLE);

                animationDrawable1 = (AnimationDrawable) help1.getBackground();
                animationDrawable2 = (AnimationDrawable) help2.getBackground();
                animationDrawable1.start();
                animationDrawable2.start();

                button_aiuto.setVisibility(View.GONE);

            }
        });

        dolci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rispDolcicard.setBackgroundColor(Color.RED);
                Intent i = new Intent(getApplicationContext(), Passo3P1.class);
                i.putExtra("flag",1);
                startActivity(i);
                finish();

            }
        });

        frutta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rispFruttacard.setBackgroundColor(Color.GREEN);
                Intent i = new Intent(getApplicationContext(), Passo3P1.class);
                i.putExtra("flag",2);
                startActivity(i);
                finish();

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
                    layoutRispMedico.setVisibility(View.VISIBLE);
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
            }
        });


        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent i = new Intent(getApplicationContext(), Home.class);
                startActivity(i);
                finish();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }



}