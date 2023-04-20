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
import android.widget.RelativeLayout;

import com.example.tesi.AppTravisani.Home;
import com.example.tesi.R;

public class Passo1P1 extends AppCompatActivity {

    private FrameLayout btn_pause;
    private FrameLayout button_aiuto;
    private AnimationDrawable animationDrawable1 = null;
    private AnimationDrawable animationDrawable2 = null;
    private ImageView help1, help2;
    private String urlVoice;
    private MediaPlayer player;
    private GridLayout layoutrisp1;
    private Dialog dialog; //finestra di dialogo
    private CardView rispRipeti, rispOk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo1_p1);
        btn_pause = findViewById(R.id.button_pause);
        button_aiuto = findViewById(R.id.button_aiuto);
        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);
        layoutrisp1 = findViewById(R.id.RispMedico1);
        rispRipeti = findViewById(R.id.cardRipeti);
        rispOk = findViewById(R.id.cardok);

        dialog= new Dialog(this);


        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //programmare popup fine percorso con custom dialog
                openCustomWindow();
              //  Toast.makeText(Passo1P1.this, "Hai cliccato stop percorso", Toast.LENGTH_SHORT).show();

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


        urlVoice="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FBenvenuto.mp3?alt=media&token=822072f9-ac1d-4b87-8007-c39bbe5a33c7";
        playsound(urlVoice);


    }



    private void playsound(String urlVoice)  {

        if (player == null) {
            player = MediaPlayer.create(getApplicationContext(), Uri.parse(urlVoice)) ;
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                    layoutrisp1.setVisibility(View.VISIBLE);
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

    public void manoOk(View view) {
        rispOk.setBackgroundColor(Color.GREEN);
        Intent i = new Intent(getApplicationContext(), Passo2P1.class);
        startActivity(i);
        finish();
    }

    public void ripeti(View view) {
        rispRipeti.setBackgroundColor(Color.GREEN);
        playsound(urlVoice);
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