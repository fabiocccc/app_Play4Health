package com.example.tesi.AppTravisani.Percorso2.Episodio1;

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
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.tesi.R;

public class Passo4E1P2 extends AppCompatActivity {

    private FrameLayout btn_pause, btn_ripeti;
    private FrameLayout button_aiuto;
    private String urlVoice;
    private MediaPlayer player;
    private ImageView help1, help2;
    private CardView malore, tosse;
    private Dialog dialog; //finestra di dialogo
    private AnimationDrawable animationDrawable1 = null;
    private AnimationDrawable animationDrawable2 = null;
    private GridLayout layoutrispAllenatoreSalute;

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private String chronoText;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo4_e1_p2);
        btn_pause = findViewById(R.id.button_pause);
        button_aiuto = findViewById(R.id.button_aiuto);
        btn_ripeti = findViewById(R.id.button_ripeti);
        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);
        dialog= new Dialog(this);
        layoutrispAllenatoreSalute = findViewById(R.id.RispAllenatoreSalute);
        malore = findViewById(R.id.cardMalore);
        tosse = findViewById(R.id.cardTosse);

        urlVoice="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FTosseMalore.mp3?alt=media&token=2510cc62-6964-4526-94d1-271af39748dd";
        playsound(urlVoice);

        //cronometro
        chronometer = findViewById(R.id.chronometer);
        resetChronometer();
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        chronometerstart();

        malore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                malore.setBackgroundColor(Color.RED);
                pauseChronometer();
                Intent i = new Intent(getApplicationContext(), Passo5E1P2.class);
                i.putExtra("time", score);
                startActivity(i);
                finish();

            }
        });

        tosse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tosse.setBackgroundColor(Color.RED);
                pauseChronometer();
                Intent i = new Intent(getApplicationContext(), Passo5E1P2.class);
                i.putExtra("time", score);
                startActivity(i);
                finish();

            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomWindow();
                stopChronometer();

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
    }

    private void stopChronometer() {
        if(running)
        {
            chronometer.stop();
            String chronoText = chronometer.getText().toString();
            // Toast.makeText(getApplicationContext(), "milliseconds: "+ chronoText, Toast.LENGTH_SHORT).show();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void chronometerstart() {

        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronometer() {
        if (running) {
            chronometer.stop();
            chronoText = chronometer.getText().toString(); // string tempo da salvare su Firebase
            String [] splits1 = chronoText.split("\\:");
            score = Integer.parseInt(splits1[1]);
            //Toast.makeText(getApplicationContext(), "milliseconds: "+chronoText, Toast.LENGTH_SHORT).show();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void resetChronometer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    private void playsound(String urlVoice)  {

        if (player == null) {
            player = MediaPlayer.create(getApplicationContext(), Uri.parse(urlVoice)) ;
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                    layoutrispAllenatoreSalute.setVisibility(View.VISIBLE);
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
                Intent i = new Intent(getApplicationContext(), PassiE1P2Activity.class);
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

    @Override
    public void onBackPressed() {
        openCustomWindow();
        stopChronometer();
    }
}