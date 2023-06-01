package com.example.tesi.AppTravisani.Percorso1.Episodio1;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tesi.AppTravisani.Percorso2.PassiP2Activity;
import com.example.tesi.AppTravisani.Percorso2.Passo3P2;
import com.example.tesi.R;

public class Passo3E1P1 extends AppCompatActivity {

    private ImageView help1;
    private FrameLayout button_aiuto;
    private FrameLayout btn_pause, btn_ripeti;
    private FrameLayout button_portiere;
    private FrameLayout button_difensore1;
    private FrameLayout button_difensore2;
    private FrameLayout button_terzinod;
    private FrameLayout button_terzinos;
    private FrameLayout button_centro1;
    private FrameLayout button_centro2;
    private FrameLayout button_esternod;
    private FrameLayout button_esternos;
    private FrameLayout button_att1;
    private FrameLayout button_att2;
    private AnimationDrawable animationDrawable = null;
    private Dialog dialog; //finestra di dialogo
    private String urlVoice1, urlclick;
    private MediaPlayer player;

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private String chronoText;
    private int score, timeback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo3_e1_p1);

        btn_pause = findViewById(R.id.button_pause);
        button_aiuto = findViewById(R.id.button_aiuto);
        btn_ripeti = findViewById(R.id.button_ripeti);
        help1 = findViewById(R.id.help1);


        button_portiere = findViewById(R.id.button_Portiere);
        button_difensore1 = findViewById(R.id.button_Difensore1);
        button_difensore2 = findViewById(R.id.button_Difensore2);
        button_terzinod = findViewById(R.id.button_TerzinoD);
        button_terzinos = findViewById(R.id.button_TerzinoS);
        button_centro1 = findViewById(R.id.button_Centro1);
        button_centro2 = findViewById(R.id.button_Centro2);
        button_esternod = findViewById(R.id.button_EsternoD);
        button_esternos = findViewById(R.id.button_EsternoS);
        button_att1 = findViewById(R.id.button_Att1);
        button_att2 = findViewById(R.id.button_Att2);

        dialog= new Dialog(this);

        //cronometro
        chronometer = findViewById(R.id.chronometer);
        resetChronometer();
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        timeback = getIntent().getExtras().getInt("time");
        chronometerstart();

        urlVoice1="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Fita%2FPosizione%20portiere.mp3?alt=media&token=05ab4ebd-3a87-4ddc-8863-3ce6e7693a2f";
        playsound(urlVoice1);


        button_aiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                help1.setVisibility(View.VISIBLE);

                animationDrawable = (AnimationDrawable) help1.getBackground();
                animationDrawable.start();

                button_aiuto.setVisibility(View.GONE);

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

                playsound(urlVoice1);

            }
        });

        button_portiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayer();
                button_portiere.setBackground(getDrawable(R.drawable.circle_portiere));
                pauseChronometer();
                Intent i = new Intent(getApplicationContext(), Passo4E1P1.class);
                i.putExtra("time", score);
                startActivity(i);
                finish();

            }
        });
        button_difensore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlclick="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Ferrorclick.mp3?alt=media&token=87bd10ea-c451-4322-8387-e58f254b545d" ;
                playsound(urlclick);
                button_difensore1.setBackground(getDrawable(R.drawable.circle_att));

            }
        });
        button_difensore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlclick="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Ferrorclick.mp3?alt=media&token=87bd10ea-c451-4322-8387-e58f254b545d" ;
                playsound(urlclick);
                button_difensore2.setBackground(getDrawable(R.drawable.circle_att));

            }
        });
        button_terzinod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlclick="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Ferrorclick.mp3?alt=media&token=87bd10ea-c451-4322-8387-e58f254b545d" ;
                playsound(urlclick);
                button_terzinod.setBackground(getDrawable(R.drawable.circle_att));

            }
        });
        button_terzinos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlclick="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Ferrorclick.mp3?alt=media&token=87bd10ea-c451-4322-8387-e58f254b545d" ;
                playsound(urlclick);
                button_terzinos.setBackground(getDrawable(R.drawable.circle_att));

            }
        });
        button_centro1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlclick="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Ferrorclick.mp3?alt=media&token=87bd10ea-c451-4322-8387-e58f254b545d" ;
                playsound(urlclick);
                button_centro1.setBackground(getDrawable(R.drawable.circle_att));

            }
        });
        button_centro2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlclick="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Ferrorclick.mp3?alt=media&token=87bd10ea-c451-4322-8387-e58f254b545d" ;
                playsound(urlclick);
                button_centro2.setBackground(getDrawable(R.drawable.circle_att));

            }
        });
        button_esternod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlclick="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Ferrorclick.mp3?alt=media&token=87bd10ea-c451-4322-8387-e58f254b545d" ;
                playsound(urlclick);
                button_esternod.setBackground(getDrawable(R.drawable.circle_att));

            }
        });
        button_esternos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlclick="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Ferrorclick.mp3?alt=media&token=87bd10ea-c451-4322-8387-e58f254b545d" ;
                playsound(urlclick);
                button_esternos.setBackground(getDrawable(R.drawable.circle_att));
            }
        });
        button_att1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlclick="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Ferrorclick.mp3?alt=media&token=87bd10ea-c451-4322-8387-e58f254b545d" ;
                playsound(urlclick);
                button_att1.setBackground(getDrawable(R.drawable.circle_att));

            }
        });
        button_att2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlclick="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Ferrorclick.mp3?alt=media&token=87bd10ea-c451-4322-8387-e58f254b545d" ;
                playsound(urlclick);
                button_att2.setBackground(getDrawable(R.drawable.circle_att));
            }
        });



    }

    private void stopChronometer() {
        if(running)
        {
            chronometer.stop();
            String chronoText = chronometer.getText().toString();
            //Toast.makeText(getApplicationContext(), "milliseconds: "+ chronoText, Toast.LENGTH_SHORT).show();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    private void chronometerstart() {

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
            int time1 = Integer.parseInt(splits1[1]);
            score = time1 + timeback;
            //Toast.makeText(getApplicationContext(), "milliseconds: "+ score, Toast.LENGTH_SHORT).show();
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
                chronometerstart();
                playsound(urlVoice1);
            }
        });


        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                resetChronometer();
                Intent i = new Intent(getApplicationContext(), PassiE1P1Activity.class);
                startActivity(i);
                finish();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                chronometerstart();
                playsound(urlVoice1);
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