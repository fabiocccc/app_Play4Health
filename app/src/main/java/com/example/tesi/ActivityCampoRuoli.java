package com.example.tesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityCampoRuoli extends AppCompatActivity {

    private Button button_portiere;
    private Button button_difensore1;
    private Button button_difensore2;
    private Button button_terzinod;
    private Button button_terzinos;
    private Button button_centro1;
    private Button button_centro2;
    private Button button_esternod;
    private Button button_esternos;
    private Button button_att1;
    private Button button_att2;
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

    @Override
    protected void onStart() {
        super.onStart();

        button_campo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityCampo.class);
                startActivity(intent);
            }
        });
    }
}