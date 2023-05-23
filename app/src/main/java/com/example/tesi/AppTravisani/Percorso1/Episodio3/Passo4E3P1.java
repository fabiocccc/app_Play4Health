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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tesi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Passo4E3P1 extends AppCompatActivity {


    private Button btnPolso, btnCaviglia;
    private FrameLayout btn_pause, btn_ripeti;
    private FrameLayout button_aiuto;
    private AnimationDrawable animationDrawable1 = null;
    private ImageView help1;
    private String urlVoice1;
    private MediaPlayer player;
    private Dialog dialog; //finestra di dialogo
    private LinearLayout linearLayoutSlogatura;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo4_e3_p1);

        btn_pause = findViewById(R.id.button_pause);
        button_aiuto = findViewById(R.id.button_aiuto);
        btn_ripeti = findViewById(R.id.button_ripeti);
        help1 = findViewById(R.id.help1);;
        linearLayoutSlogatura = findViewById(R.id.linearSlogatura);
        btnPolso = findViewById(R.id.btnPolso);
        btnCaviglia = findViewById(R.id.btnCaviglia);

        dialog= new Dialog(this);

        urlVoice1="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Fita%2FSlogatura.mp3?alt=media&token=c6f77116-80f3-4843-a2d4-90d444b0a22e";
        playsound(urlVoice1);

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openCustomWindow();
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


        btnPolso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPolso.setBackgroundColor(Color.GREEN);
                stopPlayer();
                //PASSO 5 EPISODIO 3 P1
                Intent i = new Intent(getApplicationContext(), Passo5E3P1.class);
                startActivity(i);
                finish();
            }
        });

        btnCaviglia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCaviglia.setBackgroundColor(Color.RED);
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
                    linearLayoutSlogatura.setVisibility(View.VISIBLE);
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
                Intent i = new Intent(getApplicationContext(), PassiE3P1Activity.class);
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