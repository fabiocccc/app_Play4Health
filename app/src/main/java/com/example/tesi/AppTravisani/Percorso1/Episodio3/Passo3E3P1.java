package com.example.tesi.AppTravisani.Percorso1.Episodio3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
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

import com.example.tesi.AppTravisani.Percorso1.Episodio1.PassiE1P1Activity;
import com.example.tesi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.github.muddz.styleabletoast.StyleableToast;

public class Passo3E3P1 extends AppCompatActivity {


    private FrameLayout btn_pause, btn_ripeti;
    private FrameLayout button_aiuto;
    private AnimationDrawable animationDrawable1 = null;
    private AnimationDrawable animationDrawable2 = null;
    private ImageView help1;
    private ImageView help2;
    private String urlVoice1;
    private MediaPlayer player;
    private Dialog dialog; //finestra di dialogo
    private ImageView ivPortiere, ivpalla;
    private int click = 0;
    private TextView txtGoal;
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private String chronoText;
    private int score, timeback;
    private int goal = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo3_e3_p1);

        btn_pause = findViewById(R.id.button_pause);
        button_aiuto = findViewById(R.id.button_aiuto);
        btn_ripeti = findViewById(R.id.button_ripeti);
        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);
        ivPortiere =  findViewById(R.id.imgPortiere);
        ivpalla = findViewById(R.id.imgPallone);
        txtGoal = findViewById(R.id.txtGoal);

        dialog= new Dialog(this);

        //cronometro
        chronometer = findViewById(R.id.chronometer);
        resetChronometer();
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        timeback = getIntent().getExtras().getInt("time");
        chronometerstart();

        urlVoice1 ="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Fita%2FFai%20goal.mp3?alt=media&token=6e1f9b5a-1d0a-40ac-bf15-1e6df04f12a7";
        playsound(urlVoice1);

        StyleableToast.makeText(getApplicationContext(), "Dopo l’allenamento puoi passare alle azioni di gioco quindi FAI GOAL!", R.style.mytoast).show();

        ivPortiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SpringAnimationX(view, 300f);
                SpringAnimationX(view, 0f);

            }
        });

        ivpalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SpringAnimationY(view, -1120f);
                SpringAnimationY(view, 0f);
                click = click + 1;


                if(click == 1 || click == 3 || click == 5 || click == 7 || click==9 || click == 11)
                goal = goal + 1;
                {txtGoal.setText("Goal: " + goal + "/6");}

                if(click == 11)
                {
                    stopPlayer();
                    pauseChronometer();
                    ///aprire passo4 episodio 3 P1
                    Intent i = new Intent(getApplicationContext(), Passo4E3P1.class);
                    i.putExtra("time", score);
                    startActivity(i);
                    finish();
                }
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


    //animazone per la palla
    public void SpringAnimationY(View view, float position)
    {
        SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.TRANSLATION_Y); // VERSO ANIMAZIONE ORIZZONTALE O VERTICALE
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(SpringForce.STIFFNESS_LOW); // ANIMAZIONE VELOCE LENTA
        springForce.setFinalPosition(position); //posizione finale animazione
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        springAnimation.setSpring(springForce);
        springAnimation.start();
    }

    //animazone per il portiere
    public void SpringAnimationX(View view, float position)
    {
        SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.TRANSLATION_X); // VERSO ANIMAZIONE ORIZZONTALE O VERTICALE
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(SpringForce.STIFFNESS_LOW); // ANIMAZIONE VELOCE LENTA
        springForce.setFinalPosition(position); //posizione finale animazione
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        springAnimation.setSpring(springForce);
        springAnimation.start();
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
                    ivpalla.setVisibility(View.VISIBLE);
                    txtGoal.setVisibility(View.VISIBLE);
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
                Intent i = new Intent(getApplicationContext(), PassiE3P1Activity.class);
                i.putExtra("flagDo",3);
                i.putExtra("time", 0);
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