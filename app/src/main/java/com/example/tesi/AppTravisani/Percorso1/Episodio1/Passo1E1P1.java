package com.example.tesi.AppTravisani.Percorso1.Episodio1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tesi.AppTravisani.CodeTimer;
import com.example.tesi.AppTravisani.Percorso1.P1EpisodiActivity;
import com.example.tesi.R;

import java.util.Timer;


public class Passo1E1P1 extends AppCompatActivity {

    private FrameLayout btn_pause, btn_ripeti;
    private FrameLayout button_aiuto;
    private AnimationDrawable animationDrawable1 = null;
    private ImageView help1;
    private String urlVoice1, urlVoice2;
    private MediaPlayer player, playerclick;
    private GridLayout layoutrisp1;
    private Dialog dialog; //finestra di dialogo
    private CardView rispOk, rispPollice;
    private TextView txtAllenatore;
    private int contatore = 0;

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private String chronoText;
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo1_e1_p1);
        btn_pause = findViewById(R.id.button_pause);
        button_aiuto = findViewById(R.id.button_aiuto);
        btn_ripeti = findViewById(R.id.button_ripeti);
        help1 = findViewById(R.id.help1);
        layoutrisp1 = findViewById(R.id.RispGiocatore1);
        rispPollice = findViewById(R.id.cardPollice);
        rispOk = findViewById(R.id.cardOK);
        txtAllenatore = findViewById(R.id.txt_allenatoreB);

        dialog= new Dialog(this);

        //cronometro
        chronometer = findViewById(R.id.chronometer);
        resetChronometer();
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        chronometerstart();


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

                if(contatore == 1)
                {
                    playsound(urlVoice1, 1);
                }
                else
                {
                    playsound(urlVoice2, 2);
                }
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


        urlVoice1="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Fita%2FBenvenuto.mp3?alt=media&token=73d40da7-dccc-4359-9b5f-9b88e4e0fff6";
        playsound(urlVoice1, 1);
        contatore = 1;


        rispOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rispOk.setBackgroundColor(Color.GREEN);
                playsoundClick(R.raw.soundclick);
                layoutrisp1.setVisibility(View.GONE);
                txtAllenatore.setText("Molto bene! Ma, prima di cominciare, rispondi a qualche domanda.");
                button_aiuto.setVisibility(View.GONE);
                help1.setVisibility(View.GONE);
                urlVoice2="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Fita%2FPrima%20di%20cominciare.mp3?alt=media&token=1cc6e8a2-8a6b-445e-a0d6-d3ab95a0a2d8";
                contatore=2;
                playsound(urlVoice2, 2);



            }
        });

        rispPollice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rispPollice.setBackgroundColor(Color.GREEN);
                playsoundClick(R.raw.soundclick);
                stopPlayer();
                pauseChronometer();
                //PASSO 2 EPISODIO 1 P1
                Intent i = new Intent(getApplicationContext(), Passo2E1P1.class);
                i.putExtra("time", score);
                startActivity(i);
                finish();
            }
        });


    }

    private void playsoundClick(int soundclick) {
        if(playerclick == null)
        {
            playerclick = MediaPlayer.create(getApplicationContext(), soundclick);
            playerclick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayerClick();
                }
            });
        }

        playerclick.start();
    }

    private void stopPlayerClick() {

        if (playerclick != null) {
            playerclick.release();
            playerclick = null;
            // Toast.makeText(context, "MediaPlayer releases", Toast.LENGTH_SHORT).show();
        }
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




    private void playsound(String urlVoice, int flag)  {

        if (player == null) {
            player = MediaPlayer.create(getApplicationContext(), Uri.parse(urlVoice)) ;
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();

                    if(flag == 1)
                    {
                        layoutrisp1.setVisibility(View.VISIBLE);
                        rispOk.setVisibility(View.VISIBLE);
                        rispPollice.setVisibility(View.GONE);
                        button_aiuto.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        layoutrisp1.setVisibility(View.VISIBLE);
                        rispOk.setVisibility(View.GONE);
                        rispPollice.setVisibility(View.VISIBLE);
                        button_aiuto.setVisibility(View.VISIBLE);
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
                if(contatore == 1)
                {
                    playsound(urlVoice1, 1);
                }
                else
                {
                    playsound(urlVoice2, 2);
                }

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
                if(contatore == 1)
                {
                    playsound(urlVoice1, 1);
                }
                else
                {
                    playsound(urlVoice2, 2);
                }

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
