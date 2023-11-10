package com.example.tesi.AppTravisani;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tesi.AppPavone.ActivityAscolta;
import com.example.tesi.AppPavone.Json;
import com.example.tesi.AppTravisani.Percorso1.P1EpisodiActivity;
import com.example.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaPremi extends AppCompatActivity {

    String listaPremi[] = {"Percorso 1","Percorso 2","Percorso 3","Percorso 4", "Miglior Giocatore" };

    int premiImages[] = {R.drawable.stella,R.drawable.stella,R.drawable.stella, R.drawable.premio_finale, R.drawable.premio_finale};

    //se non ha completato niente o non è loggato
    int premiImagesVuoti[] = {R.drawable.stella_vuota,R.drawable.stella_vuota,R.drawable.stella_vuota, R.drawable.stella_vuota, R.drawable.immagine1};

    //se completa solo episodio 1 del primo percorso
    int premiImages1[] = {R.drawable.star1,R.drawable.stella_vuota,R.drawable.stella_vuota,R.drawable.stella_vuota, R.drawable.immagine1};

    //se completa episodio 1 e episodio 2 del primo percorso
    int premiImages2[] = {R.drawable.star2,R.drawable.stella_vuota,R.drawable.stella_vuota,R.drawable.stella_vuota, R.drawable.immagine1};

    //se completa episodio 1,2,3 del primo percorso
    int premiImages3[] = {R.drawable.stella,R.drawable.stella_vuota,R.drawable.stella_vuota,R.drawable.stella_vuota, R.drawable.immagine1};


    private TextView titlePremi;
    private ImageView badgeIcon;
    private ImageView backIcon;

    ListView listView;

    DatabaseReference dr;
    private String key;
    private FirebaseUser user;
    private String nomeUtente;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_premi);

        titlePremi = findViewById(R.id.toolbar_title);
        badgeIcon = findViewById(R.id.badge_icon);
        backIcon = findViewById(R.id.back_icon);
        listView = findViewById(R.id.PremiListView);

        nomeUtente = new String();
        key = new String();

        user = FirebaseAuth.getInstance().getCurrentUser();

        titlePremi.setText("Stelle");
        badgeIcon.setVisibility(View.INVISIBLE);


        progressBar = findViewById(R.id.progress);


        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), StoryCard.class);
                startActivity(i);
                finish();
            }
        });

        if(user == null) {
            progressBar.setVisibility(View.INVISIBLE);
            CustomPremiAdapter customPremiAdapter = new CustomPremiAdapter(getApplicationContext(),listaPremi,premiImagesVuoti);
            listView.setAdapter(customPremiAdapter);
        }

        else if (user != null) {

            progressBar.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
            String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            nomeUtente =  mailLogged.replace("@gmail.com", "");

            readEpisodiCompletatiPercorso1(new ListaPremi.MyCallback() {

                public void onCallback(int numeroEpisodi) {

                    progressBar.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);

                    if(numeroEpisodi == 0) {
                        CustomPremiAdapter customPremiAdapter = new CustomPremiAdapter(getApplicationContext(),listaPremi,premiImagesVuoti);
                        listView.setAdapter(customPremiAdapter);
                    }
                    if(numeroEpisodi == 1) {
                        CustomPremiAdapter customPremiAdapter = new CustomPremiAdapter(getApplicationContext(),listaPremi,premiImages1);
                        listView.setAdapter(customPremiAdapter);
                    }
                    if(numeroEpisodi == 2) {
                        CustomPremiAdapter customPremiAdapter = new CustomPremiAdapter(getApplicationContext(),listaPremi,premiImages2);
                        listView.setAdapter(customPremiAdapter);
                    }
                    if(numeroEpisodi == 3) {
                        CustomPremiAdapter customPremiAdapter = new CustomPremiAdapter(getApplicationContext(),listaPremi,premiImages3);
                        listView.setAdapter(customPremiAdapter);
                    }

                }


            });
        }

        CustomPremiAdapter customPremiAdapter = new CustomPremiAdapter(getApplicationContext(),listaPremi,premiImagesVuoti);
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

    public interface MyCallback {
        void onCallback(int numeroEpisodi);
    }

    public void readEpisodiCompletatiPercorso1(ListaPremi.MyCallback myCallback) {
        dr = FirebaseDatabase.getInstance().getReference();

        //prendo la key dello user loggato

        DatabaseReference rf = dr.child("utenti");
        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    String username = postSnapshot.child("username").getValue(String.class);
                    if (username.equals(nomeUtente)) {

                        //salvo la key dell'utente loggato per controllare qunati percorsi ha svolto
                        key = postSnapshot.getKey();

                        ///

                        DatabaseReference rf2 = dr.child("utenti").child(key).child("percorsi").child("TimeP1");

                        rf2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int numeroEpisodi = 0;
                                for(DataSnapshot ds : snapshot.getChildren()) {
                                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                                    // A new comment has been added, add it to the displayed list
                                    numeroEpisodi = numeroEpisodi + 1;

                                }
                                myCallback.onCallback(numeroEpisodi);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        ///



                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }

    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), StoryCard.class);
        startActivity(i);
        finish();
    }


}