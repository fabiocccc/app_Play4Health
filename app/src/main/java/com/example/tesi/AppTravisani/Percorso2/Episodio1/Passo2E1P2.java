package com.example.tesi.AppTravisani.Percorso2.Episodio1;

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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tesi.R;

public class Passo2E1P2 extends AppCompatActivity {

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
    private String urlVoice1, urlVoiceRuolo;
    private MediaPlayer player;
    private TextView txtRuolo, txtfrasescelta;
    private int contatore = 0;
    private  int ruolo = 0;


    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private String chronoText;
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo2_e1_p2);

        btn_pause = findViewById(R.id.button_pause);
        button_aiuto = findViewById(R.id.button_aiuto);
        btn_ripeti = findViewById(R.id.button_ripeti);
        btn_alleniamoci = findViewById(R.id.btnAlleniamoci);
        help1 = findViewById(R.id.help1);
        txtRuolo =findViewById(R.id.txtRuoloScelto);
        txtfrasescelta = findViewById(R.id.txtFraseRuolo);

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

        chronometerstart();


        urlVoice1="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FAllenatore%20scelta%20ruolo.mp3?alt=media&token=320013c1-84b0-4653-b258-64e190740831";
        playsound(urlVoice1, 0);


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

                ripeti_frase();

            }
        });

        btn_alleniamoci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayer();
                pauseChronometer();
                Intent i = new Intent(getApplicationContext(), Passo3E1P2.class);
                i.putExtra("time", score);
                startActivity(i);
                finish();

            }
        });




    }

    private void ripeti_frase() {

        if(contatore == 0)
        {
            playsound(urlVoice1, 0);
            txtfrasescelta.setText("Scegli il ruolo che                                 ti piace in campo!");
            txtRuolo.setText("");
            resetbutton(); // deseleziona i pulsanti

        }else
        {
            playsound(urlVoiceRuolo, 1);
            modifyBgButton(ruolo);
        }

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
                ripeti_frase();
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
                ripeti_frase();
            }
        });

        dialog.show();
    }

    //gestione click 11 ruoli in campo
    public void btn_attaccante2(View view) {

        ruolo=11;
        urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FAttaccante.mp3?alt=media&token=6a56b4b0-f8d9-4a7f-8539-ba6eaa319c0f";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Attaccante");
        txtfrasescelta.setText("Hai scelto il ruolo");
        txtfrasescelta.setTextSize(25);
        contatore = 1;
        modifyBgButton(ruolo);

    }

    public void btn_attaccante1(View view) {

        ruolo=10;
        urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FAttaccante.mp3?alt=media&token=6a56b4b0-f8d9-4a7f-8539-ba6eaa319c0f";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Attaccante");
        txtfrasescelta.setText("Hai scelto il ruolo");
        txtfrasescelta.setTextSize(25);
        contatore = 1;
        modifyBgButton(ruolo);

    }

    public void btn_esternoS(View view) {

        ruolo=9;
        urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FEsternoSX.mp3?alt=media&token=c885185d-af01-402e-aa11-c5687a940c5c";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Esterno Sinistro");
        txtRuolo.setTextSize(30);
        txtfrasescelta.setText("Hai scelto il ruolo");
        txtfrasescelta.setTextSize(25);
        contatore = 1;
        modifyBgButton(ruolo);

    }

    public void btn_centro1(View view) {

        ruolo=6;
        urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FCentrocampista.mp3?alt=media&token=c48f7f93-d26c-4dfc-be1f-51cd754f89a3";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Centrocampista");
        txtfrasescelta.setText("Hai scelto il ruolo");
        txtfrasescelta.setTextSize(25);
        contatore = 1;
        modifyBgButton(ruolo);

    }

    public void btn_centro2(View view) {
        ruolo=7;
        urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FCentrocampista.mp3?alt=media&token=c48f7f93-d26c-4dfc-be1f-51cd754f89a3";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Centrocampista");
        txtfrasescelta.setText("Hai scelto il ruolo");
        txtfrasescelta.setTextSize(25);
        contatore = 1;
        modifyBgButton(ruolo);

    }

    public void btn_esternoD(View view) {
        ruolo=8;
        urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FEsternoDX.mp3?alt=media&token=5d2b0527-8eed-4cc3-8b02-6f2eb5f85840";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Esterno Destro");
        txtRuolo.setTextSize(30);
        txtfrasescelta.setText("Hai scelto il ruolo");
        txtfrasescelta.setTextSize(25);
        contatore = 1;
        modifyBgButton(ruolo);

    }

    public void btn_terzinoS(View view) {
        ruolo=5;
        urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FTerzinoSX.mp3?alt=media&token=4e07a0b3-160c-4fd0-b22d-ced599d8739d";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Terzino sinistro");
        txtRuolo.setTextSize(30);
        txtfrasescelta.setText("Hai scelto il ruolo");
        txtfrasescelta.setTextSize(25);
        contatore = 1;
        modifyBgButton(ruolo);

    }

    public void btn_difensore2(View view) {
        ruolo=3;
        urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FDifensore.mp3?alt=media&token=f630831e-cf64-403f-8924-3a5dafd95146";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Difensore");
        txtfrasescelta.setText("Hai scelto il ruolo");
        txtfrasescelta.setTextSize(25);
        contatore = 1;
        modifyBgButton(ruolo);

    }

    public void btn_difensore1(View view) {
        ruolo=2;
        urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FDifensore.mp3?alt=media&token=f630831e-cf64-403f-8924-3a5dafd95146";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Difensore");
        txtfrasescelta.setText("Hai scelto il ruolo");
        txtfrasescelta.setTextSize(25);
        contatore = 1;
        modifyBgButton(ruolo);


    }
    public void btn_terzinoD(View view) {
        ruolo=4;
        urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FTerzinoDX.mp3?alt=media&token=f986f1f6-d69f-4d4a-98b0-9f030176b41d";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Terzino destro");
        txtRuolo.setTextSize(30);
        txtfrasescelta.setText("Hai scelto il ruolo");
        txtfrasescelta.setTextSize(25);
        contatore = 1;
        modifyBgButton(ruolo);

    }

    public void btn_portiere(View view) {
        ruolo=1;
        urlVoiceRuolo="https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2FPortiere.mp3?alt=media&token=dcc0e44b-0c22-440c-b19a-70cdf6d58d22";
        playsound(urlVoiceRuolo, 1);
        txtRuolo.setText("Portiere");
        txtfrasescelta.setText("Hai scelto il ruolo");
        txtfrasescelta.setTextSize(25);
        contatore = 1;
        modifyBgButton(ruolo);

    }

    private void modifyBgButton(int ruolo)
    {
        switch (ruolo)
        {

            case 1:
                button_portiere.setBackground(getDrawable(R.drawable.circle_portiere));
                //2
                button_difensore1.setBackground(getDrawable(R.drawable.circle_centr));
                //3
                button_difensore2.setBackground(getDrawable(R.drawable.circle_centr));
                //4
                button_terzinod.setBackground(getDrawable(R.drawable.circle_centr));
                //5
                button_terzinos.setBackground(getDrawable(R.drawable.circle_centr));
                //6
                button_centro1.setBackground(getDrawable(R.drawable.circle_centr));
                //7
                button_centro2.setBackground(getDrawable(R.drawable.circle_centr));
                //8
                button_esternod.setBackground(getDrawable(R.drawable.circle_centr));
                //9
                button_esternos.setBackground(getDrawable(R.drawable.circle_centr));
                //10
                button_att1.setBackground(getDrawable(R.drawable.circle_centr));
                //11
                button_att2.setBackground(getDrawable(R.drawable.circle_centr));
                break;
            case 2:
                button_difensore1.setBackground(getDrawable(R.drawable.circle_portiere));
                //1
                button_portiere.setBackground(getDrawable(R.drawable.circle_centr));
                //3
                button_difensore2.setBackground(getDrawable(R.drawable.circle_centr));
                //4
                button_terzinod.setBackground(getDrawable(R.drawable.circle_centr));
                //5
                button_terzinos.setBackground(getDrawable(R.drawable.circle_centr));
                //6
                button_centro1.setBackground(getDrawable(R.drawable.circle_centr));
                //7
                button_centro2.setBackground(getDrawable(R.drawable.circle_centr));
                //8
                button_esternod.setBackground(getDrawable(R.drawable.circle_centr));
                //9
                button_esternos.setBackground(getDrawable(R.drawable.circle_centr));
                //10
                button_att1.setBackground(getDrawable(R.drawable.circle_centr));
                //11
                button_att2.setBackground(getDrawable(R.drawable.circle_centr));
                break;
            case 3:
                button_difensore2.setBackground(getDrawable(R.drawable.circle_portiere));
                //1
                button_portiere.setBackground(getDrawable(R.drawable.circle_centr));
                //2
                button_difensore1.setBackground(getDrawable(R.drawable.circle_centr));
                //4
                button_terzinod.setBackground(getDrawable(R.drawable.circle_centr));
                //5
                button_terzinos.setBackground(getDrawable(R.drawable.circle_centr));
                //6
                button_centro1.setBackground(getDrawable(R.drawable.circle_centr));
                //7
                button_centro2.setBackground(getDrawable(R.drawable.circle_centr));
                //8
                button_esternod.setBackground(getDrawable(R.drawable.circle_centr));
                //9
                button_esternos.setBackground(getDrawable(R.drawable.circle_centr));
                //10
                button_att1.setBackground(getDrawable(R.drawable.circle_centr));
                //11
                button_att2.setBackground(getDrawable(R.drawable.circle_centr));
                break;
            case 4:
                button_terzinod.setBackground(getDrawable(R.drawable.circle_portiere));
                //1
                button_portiere.setBackground(getDrawable(R.drawable.circle_centr));
                //2
                button_difensore1.setBackground(getDrawable(R.drawable.circle_centr));
                //3
                button_difensore2.setBackground(getDrawable(R.drawable.circle_centr));
                //5
                button_terzinos.setBackground(getDrawable(R.drawable.circle_centr));
                //6
                button_centro1.setBackground(getDrawable(R.drawable.circle_centr));
                //7
                button_centro2.setBackground(getDrawable(R.drawable.circle_centr));
                //8
                button_esternod.setBackground(getDrawable(R.drawable.circle_centr));
                //9
                button_esternos.setBackground(getDrawable(R.drawable.circle_centr));
                //10
                button_att1.setBackground(getDrawable(R.drawable.circle_centr));
                //11
                button_att2.setBackground(getDrawable(R.drawable.circle_centr));
                break;
            case 5:
                button_terzinos.setBackground(getDrawable(R.drawable.circle_portiere));
                //1
                button_portiere.setBackground(getDrawable(R.drawable.circle_centr));
                //2
                button_difensore1.setBackground(getDrawable(R.drawable.circle_centr));
                //3
                button_difensore2.setBackground(getDrawable(R.drawable.circle_centr));
                //4
                button_terzinod.setBackground(getDrawable(R.drawable.circle_centr));
                //6
                button_centro1.setBackground(getDrawable(R.drawable.circle_centr));
                //7
                button_centro2.setBackground(getDrawable(R.drawable.circle_centr));
                //8
                button_esternod.setBackground(getDrawable(R.drawable.circle_centr));
                //9
                button_esternos.setBackground(getDrawable(R.drawable.circle_centr));
                //10
                button_att1.setBackground(getDrawable(R.drawable.circle_centr));
                //11
                button_att2.setBackground(getDrawable(R.drawable.circle_centr));
                break;
            case 6:
                button_centro1.setBackground(getDrawable(R.drawable.circle_portiere));
                //1
                button_portiere.setBackground(getDrawable(R.drawable.circle_centr));
                //2
                button_difensore1.setBackground(getDrawable(R.drawable.circle_centr));
                //3
                button_difensore2.setBackground(getDrawable(R.drawable.circle_centr));
                //4
                button_terzinod.setBackground(getDrawable(R.drawable.circle_centr));
                //5
                button_terzinos.setBackground(getDrawable(R.drawable.circle_centr));
                //7
                button_centro2.setBackground(getDrawable(R.drawable.circle_centr));
                //8
                button_esternod.setBackground(getDrawable(R.drawable.circle_centr));
                //9
                button_esternos.setBackground(getDrawable(R.drawable.circle_centr));
                //10
                button_att1.setBackground(getDrawable(R.drawable.circle_centr));
                //11
                button_att2.setBackground(getDrawable(R.drawable.circle_centr));
                break;
            case 7:
                button_centro2.setBackground(getDrawable(R.drawable.circle_portiere));
                //1
                button_portiere.setBackground(getDrawable(R.drawable.circle_centr));
                //2
                button_difensore1.setBackground(getDrawable(R.drawable.circle_centr));
                //3
                button_difensore2.setBackground(getDrawable(R.drawable.circle_centr));
                //4
                button_terzinod.setBackground(getDrawable(R.drawable.circle_centr));
                //5
                button_terzinos.setBackground(getDrawable(R.drawable.circle_centr));
                //6
                button_centro1.setBackground(getDrawable(R.drawable.circle_centr));
                //8
                button_esternod.setBackground(getDrawable(R.drawable.circle_centr));
                //9
                button_esternos.setBackground(getDrawable(R.drawable.circle_centr));
                //10
                button_att1.setBackground(getDrawable(R.drawable.circle_centr));
                //11
                button_att2.setBackground(getDrawable(R.drawable.circle_centr));
                break;
            case 8:
                button_esternod.setBackground(getDrawable(R.drawable.circle_portiere));
                //1
                button_portiere.setBackground(getDrawable(R.drawable.circle_centr));
                //2
                button_difensore1.setBackground(getDrawable(R.drawable.circle_centr));
                //3
                button_difensore2.setBackground(getDrawable(R.drawable.circle_centr));
                //4
                button_terzinod.setBackground(getDrawable(R.drawable.circle_centr));
                //5
                button_terzinos.setBackground(getDrawable(R.drawable.circle_centr));
                //6
                button_centro1.setBackground(getDrawable(R.drawable.circle_centr));
                //7
                button_centro2.setBackground(getDrawable(R.drawable.circle_centr));
                //9
                button_esternos.setBackground(getDrawable(R.drawable.circle_centr));
                //10
                button_att1.setBackground(getDrawable(R.drawable.circle_centr));
                //11
                button_att2.setBackground(getDrawable(R.drawable.circle_centr));
                break;
            case 9:
                button_esternos.setBackground(getDrawable(R.drawable.circle_portiere));
                //1
                button_portiere.setBackground(getDrawable(R.drawable.circle_centr));
                //2
                button_difensore1.setBackground(getDrawable(R.drawable.circle_centr));
                //3
                button_difensore2.setBackground(getDrawable(R.drawable.circle_centr));
                //4
                button_terzinod.setBackground(getDrawable(R.drawable.circle_centr));
                //5
                button_terzinos.setBackground(getDrawable(R.drawable.circle_centr));
                //6
                button_centro1.setBackground(getDrawable(R.drawable.circle_centr));
                //7
                button_centro2.setBackground(getDrawable(R.drawable.circle_centr));
                //8
                button_esternod.setBackground(getDrawable(R.drawable.circle_centr));
                //10
                button_att1.setBackground(getDrawable(R.drawable.circle_centr));
                //11
                button_att2.setBackground(getDrawable(R.drawable.circle_centr));
                break;
            case 10:
                button_att1.setBackground(getDrawable(R.drawable.circle_portiere));
                //1
                button_portiere.setBackground(getDrawable(R.drawable.circle_centr));
                //2
                button_difensore1.setBackground(getDrawable(R.drawable.circle_centr));
                //3
                button_difensore2.setBackground(getDrawable(R.drawable.circle_centr));
                //4
                button_terzinod.setBackground(getDrawable(R.drawable.circle_centr));
                //5
                button_terzinos.setBackground(getDrawable(R.drawable.circle_centr));
                //6
                button_centro1.setBackground(getDrawable(R.drawable.circle_centr));
                //7
                button_centro2.setBackground(getDrawable(R.drawable.circle_centr));
                //8
                button_esternod.setBackground(getDrawable(R.drawable.circle_centr));
                //9
                button_esternos.setBackground(getDrawable(R.drawable.circle_centr));
                //11
                button_att2.setBackground(getDrawable(R.drawable.circle_centr));
                break;
            case 11:
                button_att2.setBackground(getDrawable(R.drawable.circle_portiere));
                //1
                button_portiere.setBackground(getDrawable(R.drawable.circle_centr));
                //2
                button_difensore1.setBackground(getDrawable(R.drawable.circle_centr));
                //3
                button_difensore2.setBackground(getDrawable(R.drawable.circle_centr));
                //4
                button_terzinod.setBackground(getDrawable(R.drawable.circle_centr));
                //5
                button_terzinos.setBackground(getDrawable(R.drawable.circle_centr));
                //6
                button_centro1.setBackground(getDrawable(R.drawable.circle_centr));
                //7
                button_centro2.setBackground(getDrawable(R.drawable.circle_centr));
                //8
                button_esternod.setBackground(getDrawable(R.drawable.circle_centr));
                //9
                button_esternos.setBackground(getDrawable(R.drawable.circle_centr));
                //10
                button_att1.setBackground(getDrawable(R.drawable.circle_centr));
                break;

            default:
                resetbutton();
        }




    }



    private void resetbutton() {

        ruolo=0;

        //ruoli in campo
        //1
        button_portiere.setBackground(getDrawable(R.drawable.circle_centr));
        //2
        button_difensore1.setBackground(getDrawable(R.drawable.circle_centr));
        //3
        button_difensore2.setBackground(getDrawable(R.drawable.circle_centr));
        //4
        button_terzinod.setBackground(getDrawable(R.drawable.circle_centr));
        //5
        button_terzinos.setBackground(getDrawable(R.drawable.circle_centr));
        //6
        button_centro1.setBackground(getDrawable(R.drawable.circle_centr));
        //7
        button_centro2.setBackground(getDrawable(R.drawable.circle_centr));
        //8
        button_esternod.setBackground(getDrawable(R.drawable.circle_centr));
        //9
        button_esternos.setBackground(getDrawable(R.drawable.circle_centr));
        //10
        button_att1.setBackground(getDrawable(R.drawable.circle_centr));
        //11
        button_att2.setBackground(getDrawable(R.drawable.circle_centr));
    }

    @Override
    public void onBackPressed() {
        openCustomWindow();
        pauseChronometer();
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
}