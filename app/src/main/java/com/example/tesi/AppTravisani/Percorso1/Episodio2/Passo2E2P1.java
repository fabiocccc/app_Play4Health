package com.example.tesi.AppTravisani.Percorso1.Episodio2;

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

import com.example.tesi.AppTravisani.Percorso2.Passo3P2;
import com.example.tesi.R;

public class Passo2E2P1 extends AppCompatActivity {

    private FrameLayout btn_pause, btn_ripeti;
    private FrameLayout button_aiuto;
    private AnimationDrawable animationDrawable1 = null;
    private AnimationDrawable animationDrawable2 = null;
    private ImageView help1, help2;
    private String urlVoice;
    private MediaPlayer player;
    private GridLayout layoutrispAllenatore;
    private Dialog dialog; //finestra di dialogo
    private CardView rispUno, rispDue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo2_e2_p1);

        btn_pause = findViewById(R.id.button_pause);
        button_aiuto = findViewById(R.id.button_aiuto);
        btn_ripeti = findViewById(R.id.button_ripeti);
        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);
        layoutrispAllenatore = findViewById(R.id.RispAllenatore1);
        rispUno = findViewById(R.id.cardSq1);
        rispDue = findViewById(R.id.cardSq2);

        dialog= new Dialog(this);


        urlVoice="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Fita%2FScelta%20squadra.mp3?alt=media&token=8c2b2d40-6489-4ec7-ac70-1b181c94acfb";
        playsound(urlVoice);

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

                playsound(urlVoice);
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

        rispUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rispUno.setBackgroundColor(Color.GREEN);
                Intent i = new Intent(getApplicationContext(), Passo3E2P1.class);
                startActivity(i);
                finish();


            }
        });

        rispDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rispDue.setBackgroundColor(Color.GREEN);
                Intent i = new Intent(getApplicationContext(), Passo3E2P1.class);
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
                    layoutrispAllenatore.setVisibility(View.VISIBLE);
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
                playsound(urlVoice);
            }
        });


        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent i = new Intent(getApplicationContext(), PassiE2P1Activity.class);
                startActivity(i);
                finish();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                playsound(urlVoice);
            }
        });

        dialog.show();
    }
}