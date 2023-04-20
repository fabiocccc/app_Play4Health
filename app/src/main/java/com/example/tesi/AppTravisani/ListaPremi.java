package com.example.tesi.AppTravisani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tesi.AppTravisani.P1Passi.Passo1P1;
import com.example.tesi.R;

public class ListaPremi extends AppCompatActivity {

    String listaPremi[] = {"Stella 1","Stella 2","Stella 3","Stella 4", "Miglior Giocatore" };
    int premiImages[] = {R.drawable.stella,R.drawable.stella,R.drawable.stella,R.drawable.stella, R.drawable.premio_finale};
    private TextView titlePremi;
    private ImageView badgeIcon;
    private ImageView backIcon;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_premi);

        titlePremi = findViewById(R.id.toolbar_title);
        badgeIcon = findViewById(R.id.badge_icon);
        backIcon = findViewById(R.id.back_icon);
        listView = findViewById(R.id.PremiListView);

        titlePremi.setText("Lista Premi");
        badgeIcon.setVisibility(View.INVISIBLE);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), StoryCard.class);
                startActivity(i);
                finish();
            }
        });


        CustomPremiAdapter customPremiAdapter = new CustomPremiAdapter(getApplicationContext(),listaPremi,premiImages);
        listView.setAdapter(customPremiAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position < 4)
                {
                    Toast.makeText(ListaPremi.this, "Premio del PERCORSO " + (position+1) , Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ListaPremi.this, "Premio fine partita", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}