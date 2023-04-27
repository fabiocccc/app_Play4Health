package com.example.tesi.AppTravisani.P2Passi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tesi.AppTravisani.Home;
import com.example.tesi.R;

public class Passo2P2 extends AppCompatActivity {

    private ImageView help1;
    private FrameLayout button_aiuto;
    private FrameLayout btn_pause, btn_ripeti;
    private Button btn_alleniamoci;
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
    private String urlVoice;
    private MediaPlayer player;
    private TextView txtRuolo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo2_p2);

        btn_pause = findViewById(R.id.button_pause);
        button_aiuto = findViewById(R.id.button_aiuto);
        btn_ripeti = findViewById(R.id.button_ripeti);
        btn_alleniamoci = findViewById(R.id.btnAlleniamoci);
        help1 = findViewById(R.id.help1);
        txtRuolo =findViewById(R.id.txtRuoloScelto);

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

        urlVoice="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FScelta%20ruolo.mp3?alt=media&token=d526e4dd-b1bf-4445-8dba-d4f68347c10f";
        playsound(urlVoice, 0);

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
                //  Toast.makeText(Passo1P1.this, "Hai cliccato stop percorso", Toast.LENGTH_SHORT).show();

            }
        });

        btn_ripeti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtRuolo.setText("Scegli il ruolo");
                playsound(urlVoice, 0);
            }
        });

        btn_alleniamoci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Passo3P2.class);
                startActivity(i);
                finish();

            }
        });




    }

    private void active_btnalleniamoci() {

        btn_alleniamoci.setVisibility(View.VISIBLE);
        //setta animazione lampeggiante
        //sul pulsante gioca
        YoYo.with(Techniques.Pulse)
                .duration(700)
                .repeat(10)
                .playOn(btn_alleniamoci);

    }

    private void playsound(String urlVoice, int flag)  {

        if (player == null) {
            player = MediaPlayer.create(getApplicationContext(), Uri.parse(urlVoice)) ;
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                    button_aiuto.setVisibility(View.VISIBLE);

                    if(flag == 1)
                    {
                        active_btnalleniamoci();
                        button_aiuto.setVisibility(View.GONE);

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

    //gestione click 11 ruoli in campo
    public void btn_attaccante2(View view) {
        txtRuolo.setText("Attaccante");
        String urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FAttaccante.mp3?alt=media&token=c88603d2-271a-48ab-bd25-a0a43b81d629";
        playsound(urlVoiceRuolo, 1);

    }

    public void btn_attaccante1(View view) {
        txtRuolo.setText("Attaccante");
        String urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FAttaccante.mp3?alt=media&token=c88603d2-271a-48ab-bd25-a0a43b81d629";
        playsound(urlVoiceRuolo, 1);

    }

    public void btn_esternoS(View view) {
        String urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FEsternoSX.mp3?alt=media&token=2a88ccf5-0646-43bd-8943-720fb3caccad";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Esterno Sinistro");
        txtRuolo.setTextSize(30);

    }

    public void btn_centro1(View view) {
        String urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FCentrocampista.mp3?alt=media&token=56e9beb1-b5dd-4542-81fa-19d45b40e15e";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Centrocampista");

    }

    public void btn_centro2(View view) {
        String urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FCentrocampista.mp3?alt=media&token=56e9beb1-b5dd-4542-81fa-19d45b40e15e";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Centrocampista");

    }

    public void btn_esternoD(View view) {
        String urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FEsternoDX.mp3?alt=media&token=c7f612e2-59dd-43f8-8635-5d70b0f2725e";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Esterno Destro");
        txtRuolo.setTextSize(30);

    }

    public void btn_terzinoS(View view) {
        String urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FTerzinoSX.mp3?alt=media&token=b8f3e131-b449-45dd-a373-4d2ac4abf394";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Terzino sinistro");
        txtRuolo.setTextSize(30);

    }

    public void btn_difensore2(View view) {
        String urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FDifensore.mp3?alt=media&token=9065be57-84fd-4515-894a-c2b548e484b6";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Difensore");

    }

    public void btn_difensore1(View view) {
        String urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FDifensore.mp3?alt=media&token=9065be57-84fd-4515-894a-c2b548e484b6";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Difensore");


    }
    public void btn_terzinoD(View view) {
        String urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FTerzinoDX.mp3?alt=media&token=18c227ec-5abc-4b35-822f-89ec7c883714";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Terzino destro");
        txtRuolo.setTextSize(30);

    }

    public void btn_portiere(View view) {
        String urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FPortiere.mp3?alt=media&token=78332c1e-89d1-4dd6-b6be-95926ea8271a";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Portiere");

    }
}