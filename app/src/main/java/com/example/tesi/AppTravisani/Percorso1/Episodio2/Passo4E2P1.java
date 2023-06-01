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
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tesi.R;

import io.github.muddz.styleabletoast.StyleableToast;

public class Passo4E2P1 extends AppCompatActivity {

    private FrameLayout btn_pause, btn_ripeti;
    private FrameLayout button_aiuto;
    private AnimationDrawable animationDrawable1 = null;
    private ImageView help1;
    private String urlVoice1, urlclick;
    private MediaPlayer player;
    private GridLayout layoutrisp2;
    private Dialog dialog; //finestra di dialogo

    private FrameLayout parola1 = null;
    private FrameLayout parola2 = null;
    private FrameLayout parola3 = null;
    private FrameLayout parola4 = null;
    private FrameLayout parola11 = null;
    private FrameLayout parola22 = null;
    private FrameLayout parola33 = null;
    private FrameLayout parola44 = null;
    private Button control;


    private LinearLayout linearParoleRandom;

    private LinearLayout linearRisposta;

    private String frasecorretta = "Il giocatore che difende la sua porta";
    private String frasecomposta = "";
    private int flag = 0;

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private String chronoText;
    private int score, timeback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo4_e2_p1);

        button_aiuto = findViewById(R.id.button_aiuto);
        btn_pause = findViewById(R.id.button_pause);
        btn_ripeti = findViewById(R.id.button_ripeti);
        help1 = findViewById(R.id.help1);
        layoutrisp2 = findViewById(R.id.RispGiocatore2);


        dialog= new Dialog(this);

        //cronometro
        chronometer = findViewById(R.id.chronometer);
        resetChronometer();
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        timeback = getIntent().getExtras().getInt("time");
        chronometerstart();

        urlVoice1 = "https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Fita%2FFunzione%20Portiere%20ordina%20frase.mp3?alt=media&token=c790466c-3992-459d-8565-89938b921aa3";
        playsound(urlVoice1);

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

        button_aiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                help1.setVisibility(View.VISIBLE);

                animationDrawable1 = (AnimationDrawable) help1.getBackground();
                animationDrawable1.start();

                button_aiuto.setVisibility(View.GONE);

            }
        });


        control = findViewById(R.id.button_check);

        linearParoleRandom= findViewById(R.id.linear_parole_random);
        linearRisposta = findViewById(R.id.linear_risposta);


        parola1 = (FrameLayout) LayoutInflater.from(this).inflate( R.layout.parola, null);
        parola2 = (FrameLayout) LayoutInflater.from(this).inflate( R.layout.parola, null);
        parola3 = (FrameLayout) LayoutInflater.from(this).inflate( R.layout.parola, null);
        parola4 = (FrameLayout) LayoutInflater.from(this).inflate( R.layout.parola, null);


        parola11 = (FrameLayout) LayoutInflater.from(this).inflate( R.layout.parola, null);
        parola22 = (FrameLayout) LayoutInflater.from(this).inflate( R.layout.parola, null);
        parola33 = (FrameLayout) LayoutInflater.from(this).inflate( R.layout.parola, null);
        parola44 = (FrameLayout) LayoutInflater.from(this).inflate( R.layout.parola, null);




        TextView t1 = (TextView) parola1.getChildAt(0);
        t1.setText("Il giocatore");
        t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView t2 = (TextView) parola2.getChildAt(0);
        t2.setText("che difende");
        t2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView t3= (TextView) parola3.getChildAt(0);
        t3.setText("la sua");
        t3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView t4 = (TextView) parola4.getChildAt(0);
        t4.setText("porta");
        t4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView t11 = (TextView) parola11.getChildAt(0);
        t11.setText("Il giocatore");
        t11.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView t22 = (TextView) parola22.getChildAt(0);
        t22.setText("che difende");
        t22.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView t33= (TextView) parola33.getChildAt(0);
        t33.setText("la sua");
        t33.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView t44 = (TextView) parola44.getChildAt(0);
        t44.setText("porta");
        t44.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        linearParoleRandom.addView(parola2);
        linearParoleRandom.addView(parola4);
        linearParoleRandom.addView(parola3);
        linearParoleRandom.addView(parola1);

        parola1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = flag +1;
                linearParoleRandom.removeView(parola1);
                linearRisposta.addView(parola11);
                frasecomposta = frasecomposta + " Il giocatore "; }
        });

        parola2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = flag +1;

                linearParoleRandom.removeView(parola2);
                linearRisposta.addView(parola22);
                frasecomposta = frasecomposta + " che difende "; }
        });

        parola3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = flag +1;

                linearParoleRandom.removeView(parola3);
                linearRisposta.addView(parola33);
                frasecomposta = frasecomposta + " la sua ";}
        });

        parola4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = flag +1;

                linearParoleRandom.removeView(parola4);
                linearRisposta.addView(parola44);
                frasecomposta = frasecomposta + " porta ";}
        });


        parola11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearParoleRandom.addView(parola1);
                linearRisposta.removeView(parola11);
                frasecomposta.replaceAll(" Il giocatore ", "" );

            }
        });

        parola22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearParoleRandom.addView(parola2);
                linearRisposta.removeView(parola22);
                frasecomposta.replaceAll(" che difende ", "" );}
        });

        parola33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearParoleRandom.addView(parola3);
                linearRisposta.removeView(parola33);
                frasecomposta.replaceAll(" la sua ", "" );}
        });

        parola44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearParoleRandom.addView(parola4);
                linearRisposta.removeView(parola44);
                frasecomposta.replaceAll(" porta ", "" );}
        });


        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                // Toast.makeText(MainActivity.this, frasecomposta.replaceAll(" ", ""), Toast.LENGTH_SHORT).show();

                String s1 = frasecomposta.replaceAll(" ", "");
                String s2 = frasecorretta.replaceAll(" ", "");

                if (flag < 4)
                {

                    StyleableToast.makeText(getApplicationContext(), "Componi correttamente la frase", R.style.warningToast).show();
                }
                else if ( s1.equals(s2))
                {

                    //StyleableToast.makeText(getApplicationContext(), "Risposta corretta", R.style.rightToast).show();
                    stopPlayer();
                    pauseChronometer();
                    //Aprire passo 5 episodio 2 P1
                    Intent i = new Intent(getApplicationContext(), Passo5E2P1.class);
                    i.putExtra("time", score);
                    startActivity(i);
                    finish();

                }
                else
                {

                    StyleableToast.makeText(getApplicationContext(), "Risposta sbagliata, riprova!", R.style.errorToast).show();

                    urlclick="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Ferrorclick.mp3?alt=media&token=87bd10ea-c451-4322-8387-e58f254b545d";
                    playsound(urlclick);

                    linearRisposta.removeAllViews();
                    linearParoleRandom.addView(parola2);
                    linearParoleRandom.addView(parola4);
                    linearParoleRandom.addView(parola3);
                    linearParoleRandom.addView(parola1);
                    frasecomposta="";
                }


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
          // Toast.makeText(getApplicationContext(), "milliseconds: "+ score, Toast.LENGTH_SHORT).show();
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
                    control.setVisibility(View.VISIBLE);
                    linearParoleRandom.setVisibility(View.VISIBLE);
                    linearRisposta.setVisibility(View.VISIBLE);
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
                Intent i = new Intent(getApplicationContext(), PassiE2P1Activity.class);
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