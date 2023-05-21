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

import com.example.tesi.AppTravisani.Percorso1.Episodio1.PassiE1P1Activity;
import com.example.tesi.AppTravisani.Percorso1.Episodio1.Passo3E1P1;
import com.example.tesi.R;

public class Passo3E2P1 extends AppCompatActivity {

    private FrameLayout btn_pause, btn_ripeti;
    private FrameLayout button_aiuto;
    private AnimationDrawable animationDrawable1 = null;
    private ImageView help1;
    private String urlVoice1;
    private MediaPlayer player;
    private GridLayout layoutrisp2;
    private Dialog dialog; //finestra di dialogo
    private CardView rispPortiere, rispPivot, rispAlzatore, rispLanciatore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo3_e2_p1);

        button_aiuto = findViewById(R.id.button_aiuto);
        btn_pause = findViewById(R.id.button_pause);
        btn_ripeti = findViewById(R.id.button_ripeti);
        help1 = findViewById(R.id.help1);
        layoutrisp2 = findViewById(R.id.RispGiocatore2);
        rispPortiere = findViewById(R.id.cardPortiere);
        rispPivot = findViewById(R.id.cardPivot);
        rispAlzatore = findViewById(R.id.cardAlzatore);
        rispLanciatore = findViewById(R.id.cardLanciatore);

        dialog= new Dialog(this);

        urlVoice1 = "https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Fita%2FScelta%20ruolo%20giusto.mp3?alt=media&token=e4d5cb9b-e627-4acd-a72d-44d16cfafad6";
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

        rispPortiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rispPortiere.setBackgroundColor(Color.GREEN);
                //PASSO 4 EPISODIO 2 P1
//                Intent i = new Intent(getApplicationContext(), Passo4E1P1.class);
//                startActivity(i);
//                finish()
            }
        });

        rispPivot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rispPivot.setBackgroundColor(Color.RED);
;
            }
        });


        rispAlzatore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rispAlzatore.setBackgroundColor(Color.RED);
            }
        });

        rispLanciatore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rispLanciatore.setBackgroundColor(Color.RED);
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
                Intent i = new Intent(getApplicationContext(), PassiE2P1Activity.class);
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