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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tesi.AppTravisani.Home;
import com.example.tesi.AppTravisani.StoryCard;
import com.example.tesi.R;

public class Passo1P1 extends AppCompatActivity {

    private FrameLayout btn_pause;
    private String urlVoice;
    MediaPlayer player;
    RelativeLayout layoutrisp;
    Dialog dialog; //finestra di dialogo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo1_p1);
        btn_pause = findViewById(R.id.button_pause);
        layoutrisp = findViewById(R.id.LayoutRisposta);
        dialog= new Dialog(this);


        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //programmare popup fine percorso con custom dialog
                openCustomWindow();
              //  Toast.makeText(Passo1P1.this, "Hai cliccato stop percorso", Toast.LENGTH_SHORT).show();

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
                    layoutrisp.setVisibility(View.VISIBLE);
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


    }

    public void ripeti(View view) {
        playsound(urlVoice);
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