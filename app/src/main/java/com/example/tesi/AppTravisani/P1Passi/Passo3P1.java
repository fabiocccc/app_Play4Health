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

public class Passo3P1 extends AppCompatActivity {

    private FrameLayout btn_pause, btn_ripeti;
    private MediaPlayer player;
    private Dialog dialog; //finestra di dialogo
    private ImageView ore5, ore7;
    private Button btn_gioca;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo3_p1);
        btn_pause = findViewById(R.id.button_pause);
        btn_ripeti = findViewById(R.id.button_ripeti);
        btn_gioca = findViewById(R.id.btnGioca);

        ore5 = findViewById(R.id.ore5);
        ore7 = findViewById(R.id.ore7);


        dialog= new Dialog(this);

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
                String urlVoice3 = "https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FQuante%20ore%20dormi%20.mp3?alt=media&token=5b711682-931c-4f00-94e3-432fc3a72b30";
                playsound(urlVoice3);
            }
        });

        ore5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  urlVoiceRisp = "https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FDormi%20poco.mp3?alt=media&token=120a69e4-232d-485a-b31e-50ea5a46180b" ;
                playsound(urlVoiceRisp);
                btn_gioca.setVisibility(View.VISIBLE);
            }
        });

        ore7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  urlVoiceRisp = "https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FBenissimo%20sei%20in%20forma.mp3?alt=media&token=aebe1192-7523-4236-86e2-5eb801de2c07" ;
                playsound(urlVoiceRisp);
                btn_gioca.setVisibility(View.VISIBLE);
            }
        });

        btn_gioca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Passo4P1.class);
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
//                    layoutRispMedico.setVisibility(View.VISIBLE);
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