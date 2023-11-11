package com.example.tesi.AppTravisani.Percorso1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tesi.AppTravisani.Percorso1.Episodio1.PassiE1P1Activity;
import com.example.tesi.AppTravisani.Percorso1.Episodio1.Passo1E1P1;
import com.example.tesi.AppTravisani.Percorso1.Episodio2.PassiE2P1Activity;
import com.example.tesi.AppTravisani.Percorso1.Episodio3.PassiE3P1Activity;
import com.example.tesi.AppTravisani.StoryCard;
import com.example.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class P1EpisodiActivity extends AppCompatActivity {

    private CardView cv1, cv2 , cv3;

    private ImageView backIcon;
    private ImageView badgeIcon;
    private TextView title_toolbar;
    private String nomePercorso;

    DatabaseReference dr;
    private String key;
    private FirebaseUser user;
    private String nomeUtente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p1_episodi);

        title_toolbar= findViewById(R.id.toolbar_title);
        badgeIcon= findViewById(R.id.badge_icon);
        backIcon = findViewById(R.id.back_icon);


        badgeIcon.setVisibility(View.GONE);
        title_toolbar.setText("EPISODI Percorso 1");
        title_toolbar.setTextSize(25);

        key = new String();

        user = FirebaseAuth.getInstance().getCurrentUser();


        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        nomePercorso = new String();
        cv1 = findViewById(R.id.cv1);
        cv2 = findViewById(R.id.cv2);
        cv3 = findViewById(R.id.cv3);


        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            nomePercorso = extras.getString("percorso1");
        }


        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user != null) {

                    String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    nomeUtente = mailLogged.replace("@gmail.com", "");

                    passiE1Completati(new P1EpisodiActivity.MyCallback() {

                        public void onCallback(int numeroEpisodi, String key) {

                            //aprire passi dell'episodio 1 del percorso 1
                            Intent i = new Intent(getApplicationContext(), PassiE1P1Activity.class);
                            i.putExtra("percorso1Fatto",numeroEpisodi);
                            i.putExtra("keyUser",key);
                            startActivity(i);
                            finish();
                        }


                    });

                }
                else {
                    //aprire passi dell'episodio 1 del percorso 1
                    Intent i = new Intent(getApplicationContext(), PassiE1P1Activity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user != null) {

                    String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    nomeUtente = mailLogged.replace("@gmail.com", "");

                    passiE2Completati(new P1EpisodiActivity.MyCallback() {

                        public void onCallback(int numeroEpisodi, String key) {

                            //aprire passi dell'episodio 1 del percorso 1
                            Intent i = new Intent(getApplicationContext(), PassiE2P1Activity.class);
                            i.putExtra("percorso2Fatto",numeroEpisodi);
                            i.putExtra("keyUser",key);
                            startActivity(i);
                            finish();
                        }


                    });

                }
                else {
                    //aprire passi dell'episodio 2 del percorso 1
                    Intent i = new Intent(getApplicationContext(), PassiE2P1Activity.class);
                    startActivity(i);
                    finish();
                }

            }
        });

        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user != null) {

                    String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    nomeUtente = mailLogged.replace("@gmail.com", "");

                    passiE3Completati(new P1EpisodiActivity.MyCallback() {

                        public void onCallback(int numeroEpisodi, String key) {

                            //aprire passi dell'episodio 1 del percorso 1
                            Intent i = new Intent(getApplicationContext(), PassiE3P1Activity.class);
                            i.putExtra("percorso3Fatto",numeroEpisodi);
                            i.putExtra("keyUser",key);
                            startActivity(i);
                            finish();
                        }


                    });

                }
                else {
                    //aprire passi dell'episodio 2 del percorso 1
                    Intent i = new Intent(getApplicationContext(), PassiE3P1Activity.class);
                    startActivity(i);
                    finish();
                }


            }
        });

    }
    public interface MyCallback {
        void onCallback(int numeroEpisodi, String key);
    }

    public void passiE1Completati(P1EpisodiActivity.MyCallback myCallback) {
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

                        DatabaseReference rf2 = dr.child("utenti").child(key).child("percorsi").child("TimeP1").child("P1E1");

                        rf2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int numeroEpisodi = 0;
                                for(DataSnapshot ds : snapshot.getChildren()) {
                                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                                    // A new comment has been added, add it to the displayed list
                                    numeroEpisodi = numeroEpisodi + 1;

                                }
                                rf2.removeEventListener(this);
                                myCallback.onCallback(numeroEpisodi, key);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        ///



                    }


                }
                rf.removeEventListener(this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }

    public void passiE2Completati(P1EpisodiActivity.MyCallback myCallback) {
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

                        DatabaseReference rf2 = dr.child("utenti").child(key).child("percorsi").child("TimeP1").child("P1E2");

                        rf2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int numeroEpisodi = 0;
                                for(DataSnapshot ds : snapshot.getChildren()) {
                                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                                    // A new comment has been added, add it to the displayed list
                                    numeroEpisodi = numeroEpisodi + 1;

                                }
                                rf2.removeEventListener(this);
                                myCallback.onCallback(numeroEpisodi, key);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        ///



                    }


                }
                rf.removeEventListener(this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }

    public void passiE3Completati(P1EpisodiActivity.MyCallback myCallback) {
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

                        DatabaseReference rf2 = dr.child("utenti").child(key).child("percorsi").child("TimeP1").child("P1E3");

                        rf2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int numeroEpisodi = 0;
                                for(DataSnapshot ds : snapshot.getChildren()) {
                                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                                    // A new comment has been added, add it to the displayed list
                                    numeroEpisodi = numeroEpisodi + 1;

                                }
                                rf2.removeEventListener(this);
                                myCallback.onCallback(numeroEpisodi, key);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        ///



                    }


                }
                rf.removeEventListener(this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }




    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), StoryCard.class);
        startActivity(i);
        finish();
    }
}