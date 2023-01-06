package com.example.tesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.FrameMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class ActivityCampoRuoli extends AppCompatActivity {

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
    private Button button_campo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campo_ruoli);

        button_portiere = findViewById(R.id.button_Rigore2);
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
        button_campo = findViewById(R.id.button_Campo);
    }

    boolean abort;
    int count = 0;

    private void changeBackground(){
        if (abort)
            return;
        new Handler().postDelayed(new Runnable() {

            public void run() {

                Toast.makeText(getApplicationContext(), "ITALIA",Toast.LENGTH_SHORT).show();
                count++;
                if(count % 2 == 0){
                    button_portiere.setBackground(getDrawable(R.drawable.circle_att));
                }else{
                    button_portiere.setBackground(getDrawable(R.drawable.circle_portiere));
                }
                changeBackground();

            }
        }, 500);
    }

    @Override
    protected void onStart() {
        super.onStart();

        button_portiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //changeBackground();

            }
        });

        button_campo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityCampo.class);
                startActivity(intent);
            }
        });
    }
}