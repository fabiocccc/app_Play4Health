package com.example.tesi.AppTravisani.Percorso1.Episodio3;

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
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tesi.AppTravisani.Percorso1.Episodio1.PassiE1P1Activity;
import com.example.tesi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

import io.github.muddz.styleabletoast.StyleableToast;

public class Passo2E3P1 extends AppCompatActivity {

    private FloatingActionButton btnRiproduci;
    private Button btnRegistra;
    private TextView txtTestoRegistrato;
    private FrameLayout btn_pause, btn_ripeti;
    private FrameLayout button_aiuto;
    private AnimationDrawable animationDrawable1 = null;
    private ImageView help1;
    private ImageView help2;
    private String urlVoice1, urlVoice2, urlclick;
    private MediaPlayer player;
    private Dialog dialog; //finestra di dialogo
    private String fraseRegistrata;
    private int contatore = 0;

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private String chronoText;
    private int score, timeback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo2_e3_p1);

        btnRiproduci = findViewById(R.id.button_Ascolta);
        btnRegistra = findViewById(R.id.button_Parla);
        txtTestoRegistrato = findViewById(R.id.TextRiconosciuto);
        btn_pause = findViewById(R.id.button_pause);
        button_aiuto = findViewById(R.id.button_aiuto);
        btn_ripeti = findViewById(R.id.button_ripeti);
        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);

        dialog= new Dialog(this);

        //cronometro
        chronometer = findViewById(R.id.chronometer);
        resetChronometer();
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        timeback = getIntent().getExtras().getInt("time");
        chronometerstart();

        urlVoice1 ="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Fita%2FChe%20cosa%20sta%20facendo.mp3?alt=media&token=d0cff0d5-7832-439d-9036-f32cf616f8a9";
        playsound(urlVoice1, 1);
        contatore = 1;


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


                    playsound(urlVoice1, 1);

            }
        });

        button_aiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if( contatore == 1)
                {
                    help1.setVisibility(View.VISIBLE);

                    animationDrawable1 = (AnimationDrawable) help1.getBackground();
                    animationDrawable1.start();

                    button_aiuto.setVisibility(View.GONE);
                }
                else if(contatore == 2)
                {
                    help2.setVisibility(View.VISIBLE);

                    animationDrawable1 = (AnimationDrawable) help2.getBackground();
                    animationDrawable1.start();

                    button_aiuto.setVisibility(View.GONE);
                }


            }
        });


        btnRiproduci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopPlayer();
                urlVoice2 = "https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Fita%2FTocca%20i%20piedi.mp3?alt=media&token=4f4ab025-cbff-4110-b67b-226683893a4e";
                playsound(urlVoice2, 2);
                contatore = 2;
            }
        });

        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopPlayer();

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 10);
                } else {
                    Toast.makeText(getApplicationContext(), "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtTestoRegistrato.setText(result.get(0));
                    fraseRegistrata = result.get(0);
                    String fraseprova = "tocca i piedi";

                    if(fraseRegistrata.equals(fraseprova))
                    {
                        StyleableToast.makeText(getApplicationContext(), "Risposta corretta", R.style.rightToast).show();
                        stopPlayer();
                        pauseChronometer();
                        //passo 3 episodio3 P1
                        Intent i = new Intent(getApplicationContext(), Passo3E3P1.class);
                        i.putExtra("time", score);
                        startActivity(i);
                        finish();

                    }
                    else
                    {
                        StyleableToast.makeText(getApplicationContext(), "Risposta sbagliata, riprova!", R.style.errorToast).show();
                        urlclick="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Ferrorclick.mp3?alt=media&token=87bd10ea-c451-4322-8387-e58f254b545d";
                        playsound(urlclick,0);
                    }
                }
                break;
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

    private void playsound(String urlVoice, int flag)  {

        if (player == null) {
            player = MediaPlayer.create(getApplicationContext(), Uri.parse(urlVoice)) ;
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();

                    if(flag == 1)
                    {
                           btnRiproduci.setVisibility(View.VISIBLE);
                           button_aiuto.setVisibility(View.VISIBLE);

                    }
                    else if (flag == 2)
                    {
//                        btnRegistra.setVisibility(View.VISIBLE);
//                        txtTestoRegistrato.setVisibility(View.VISIBLE);
//                        button_aiuto.setVisibility(View.VISIBLE);
///////////////////////////PROVA PROSSIMO PASSSO
                        Intent i = new Intent(getApplicationContext(), Passo3E3P1.class);
                        pauseChronometer();
                        i.putExtra("time", score);
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
                Intent i = new Intent(getApplicationContext(), PassiE3P1Activity.class);
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