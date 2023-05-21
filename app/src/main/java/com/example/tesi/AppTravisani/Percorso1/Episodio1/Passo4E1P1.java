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
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tesi.AppTravisani.Percorso1.Passo1P1;
import com.example.tesi.AppTravisani.Percorso2.PassiP2Activity;
import com.example.tesi.R;

public class Passo4E1P1 extends AppCompatActivity {

    private FrameLayout btn_pause, btn_ripeti;
    private Dialog dialog, findialog; //finestra di dialogo
    private MediaPlayer player;
    private ImageView premio;
    private ImageView help1;
    private FrameLayout button_aiuto;
    private AnimationDrawable animationDrawable1 = null;
    private String urlVoice4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passo4_e1_p1);

        btn_pause = findViewById(R.id.button_pause);
        btn_ripeti = findViewById(R.id.button_ripeti);
        button_aiuto = findViewById(R.id.button_aiuto);
        premio = findViewById(R.id.imagePremio);
        help1 = findViewById(R.id.help1);


        dialog= new Dialog(this);
        findialog = new Dialog(this);

        urlVoice4 = "https://firebasestorage.googleapis.com/v0/b/appplay4health.appspot.com/o/audios%2Fita%2FFine%20E1P1.mp3?alt=media&token=bc0d2d4a-d0f5-4488-bd7e-80841c3975a9";
        playsound(urlVoice4);


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

                playsound(urlVoice4);
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

        premio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUPFinePercorso();
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
                    active_premio();
                    help1.setVisibility(View.VISIBLE);
                    animationDrawable1 = (AnimationDrawable) help1.getBackground();
                    animationDrawable1.start();
                    button_aiuto.setVisibility(View.VISIBLE);
                }
            });
        }

        player.start();

    }

    private void active_premio() {

        premio.setVisibility(View.VISIBLE);
        //setta animazione lampeggiante
        //sul pulsante gioca
        YoYo.with(Techniques.Pulse)
                .duration(700)
                .repeat(10)
                .playOn(premio);

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
                playsound(urlVoice4);
            }
        });


        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent i = new Intent(getApplicationContext(), PassiE1P1Activity.class);
                startActivity(i);
                finish();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                playsound(urlVoice4);
            }
        });

        dialog.show();
    }
    private void PopUPFinePercorso() {

        findialog.setContentView(R.layout.fine_livellolayout);
        findialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = findialog.findViewById(R.id.ClosPoPUp);
        Button btnRipetiLivello = findialog.findViewById(R.id.btnRipetiLivello);
        Button btnAvanti = findialog.findViewById(R.id.btnAvanti);
        TextView popUpTitle = findialog.findViewById(R.id.titlePoPUP);
        TextView popUpMessage = findialog.findViewById(R.id.MessagePopUP);

        popUpTitle.setText("Hai superato l'episodio 1");
        popUpMessage.setText("Hai guadagnato 2 punti");


        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findialog.dismiss();
                Intent i = new Intent(getApplicationContext(), PassiE1P1Activity.class);
                i.putExtra("flagDo",4);
                startActivity(i);
                finish();
            }
        });


        btnRipetiLivello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findialog.dismiss();
                Intent i = new Intent(getApplicationContext(), Passo1P1.class);
                startActivity(i);
                finish();
            }
        });

        btnAvanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                findialog.dismiss();

                //continua con il percorso 2
                Intent i = new Intent(getApplicationContext(), PassiP2Activity.class);
                startActivity(i);
                finish();

            }
        });

        findialog.show();
    }
}