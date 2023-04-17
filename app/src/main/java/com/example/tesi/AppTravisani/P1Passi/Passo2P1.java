package com.example.tesi.AppTravisani.P1Passi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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
    private Dialog dialog; //finestra di dialogo
    private String urlVoice2;
    private ImageView dolci, carne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo2_p1);
        dolci= findViewById(R.id.dolci);
        carne= findViewById(R.id.carne);
        btn_pause = findViewById(R.id.button_pause);
        btn_ripeti = findViewById(R.id.button_ripeti);
        layoutRispMedico = findViewById(R.id.RispMedico);
        layoutRispMedico2 = findViewById(R.id.RispMedico2);
        dialog= new Dialog(this);

        urlVoice2="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FMedico%20dolci%20o%20carne.mp3?alt=media&token=7a74e88b-4d4c-4d4c-b672-2be292f3bc30";
        playsound(urlVoice2, 0);

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

                playsound(urlVoice2,0);
            }
        });

        dolci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              String  urlVoiceRisp = "https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FMale%20non%20mangi%20sano%20.mp3?alt=media&token=e84fd4fb-25ff-454e-8523-693fbc99456a" ;
              playsound(urlVoiceRisp, 1);

            }
        });

        carne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  urlVoiceRisp = "https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FBene%20mangi%20sano.mp3?alt=media&token=2788805f-8e39-4573-815d-ccd6bae89224";
                playsound(urlVoiceRisp, 1);


            }
        });

    }

    private void playsound(String urlVoice, int flag)  {

        if (player == null) {
            player = MediaPlayer.create(getApplicationContext(), Uri.parse(urlVoice)) ;
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                    layoutRispMedico.setVisibility(View.VISIBLE);
                    if(flag == 1)
                    {
                        Intent i = new Intent(getApplicationContext(), Passo3P1.class);
                        startActivity(i);
                        finish();
                    }

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