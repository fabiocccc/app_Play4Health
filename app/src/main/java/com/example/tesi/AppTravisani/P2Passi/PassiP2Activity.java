package com.example.tesi.AppTravisani.P2Passi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tesi.AppTravisani.P1Passi.Passo1P1;
import com.example.tesi.R;

public class PassiP2Activity extends AppCompatActivity {

    private CardView passo1, passo2, passo3, passo4, passo5, passo6;
    private ImageView backIcon;
    private ImageView badgeIcon;
    private TextView title_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passi_p2);

        title_toolbar= findViewById(R.id.toolbar_title);
        title_toolbar.setText("Passi P2");

        backIcon = findViewById(R.id.back_icon);
        badgeIcon = findViewById(R.id.badge_icon);
        badgeIcon.setVisibility(View.INVISIBLE);

        passo1 = findViewById(R.id.cardDolci);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        ///PASSO 1 PERCORSO 2
        passo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //aprire passo 1 del percorso 1
                Intent i = new Intent(getApplicationContext(), Passo1P2.class);
                startActivity(i);
                finish();
            }
        });

    }
}