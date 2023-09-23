package com.example.tesi.AppPavone;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tesi.AppConte.AttivitaUtente;
import com.example.tesi.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class ActivityGalleriaVideo extends AppCompatActivity {

    private LinearLayout linearLayout;

    private ArrayList<String> listaVideo;
    private ArrayList<String> listaDomanda;
    private ArrayList<String> listaCorretta;
    private ArrayList<String> listaSbagliata;
    private ArrayList<String> listaVideoFra;
    private ArrayList<String> listaVideoEng;
    private ArrayList<String> nomiContenuti;
    private TextView textView;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    DatabaseReference dr;
    private String key;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galleria_video);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        findViewById(R.id.button_indietro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        linearLayout = findViewById(R.id.linear);

        listaVideo = new ArrayList<>();
        listaDomanda = new ArrayList<>();
        listaCorretta = new ArrayList<>();
        listaSbagliata = new ArrayList<>();
        listaVideoFra = new ArrayList<>();
        listaVideoEng = new ArrayList<>();
        nomiContenuti = new ArrayList<>();
        textView = findViewById(R.id.textView);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

        if(connected){


        } else {
            Toast.makeText(getApplicationContext(), "Non sei connesso a Internet", Toast.LENGTH_LONG).show();
            finish();
        }

        if(getIntent().getStringExtra("tipo").equals("commento")){

            user = FirebaseAuth.getInstance().getCurrentUser();


            //GALLERIA VIDEO COMMENTI
            StorageReference listRef = storageReference.child("/videos/commenti/");


            SharedPreferences shared =  getSharedPreferences("stelle", MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("stelle3", "3");

            textView.setText("Azioni salienti");
            listRef.listAll()
                    .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                        @Override
                        public void onSuccess(ListResult listResult) {
                            for (StorageReference prefix : listResult.getPrefixes()) {
                                // All the prefixes under listRef.
                                // You may call listAll() recursively on them.
                            }

                            readCommenti(new MyCallback() {
                                @Override
                                public void onCallback(ArrayList<String> nomiContenuti) {


                                    for (StorageReference item : listResult.getItems()) {
                                        // All the items under listRef.
                                        String[] parts = item.getName().split("\\$");

                                        if (nomiContenuti.contains(parts[0])) {
                                            listaVideo.add(parts[0]);
                                            listaDomanda.add(parts[1]);
                                            listaCorretta.add(parts[2]);
                                            listaSbagliata.add(parts[3]);

                                        }

                                    }


                                    int j = 0;
                                    int i = 0;
                                    while (i != listaVideo.size()) {

                                        LinearLayout linear = (LinearLayout) LayoutInflater.from(ActivityGalleriaVideo.this).inflate(R.layout.linear_galleria, null);
                                        linearLayout.addView(linear, j);
                                        j++;

                                        //PRIMA CARD
                                        View card1 = linear.getChildAt(0);
                                        TextView textCard1 = linear.getChildAt(0).findViewById(R.id.textCard1);
                                        textCard1.setText(listaVideo.get(i));

                                        ImageView imageCard1 = linear.getChildAt(0).findViewById(R.id.imageCard1);

                                        storageReference.child("/videos/commenti/" + listaVideo.get(i) + "$" + listaDomanda.get(i) + "$" + listaCorretta.get(i) + "$" + listaSbagliata.get(i)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Glide.with(ActivityGalleriaVideo.this)
                                                        .load(uri)
                                                        .centerCrop()
                                                        .into(imageCard1);
                                            }
                                        });

                                        int finalI = i;
                                        imageCard1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(getApplicationContext(), ActivityVideo.class);
                                                intent.putExtra("nome", listaVideo.get(finalI) + "$" + listaDomanda.get(finalI) + "$" + listaCorretta.get(finalI) + "$" + listaSbagliata.get(finalI));
                                                intent.putExtra("tipo", "commento");
                                                //controllo se l'utente è loggato
                                                if(user != null) {


                                                    String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                                                    String nomeUtente =  mailLogged.replace("@gmail.com", "");

                                                    trovaKeyUtente(nomeUtente, "commenti", listaVideo.get(finalI));

                                                }
                                                startActivity(intent);
                                            }
                                        });

                                        i++;

                                        if (i != listaVideo.size()) {
                                            //SECONDA CARD
                                            View card2 = linear.getChildAt(1);
                                            TextView textCard2 = linear.getChildAt(1).findViewById(R.id.textCard2);
                                            textCard2.setText(listaVideo.get(i));

                                            ImageView imageCard2 = linear.getChildAt(1).findViewById(R.id.imageCard2);

                                            storageReference.child("/videos/commenti/" + listaVideo.get(i) + "$" + listaDomanda.get(i) + "$" + listaCorretta.get(i) + "$" + listaSbagliata.get(i)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Glide.with(ActivityGalleriaVideo.this)
                                                            .load(uri)
                                                            .centerCrop()
                                                            .into(imageCard2);
                                                }
                                            });

                                            int finalI1 = i;
                                            imageCard2.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent = new Intent(getApplicationContext(), ActivityVideo.class);

                                                    intent.putExtra("nome", listaVideo.get(finalI1) + "$" + listaDomanda.get(finalI1) + "$" + listaCorretta.get(finalI1) + "$" + listaSbagliata.get(finalI1));
                                                    intent.putExtra("tipo", "commento");
                                                    //controllo se l'utente è loggato
                                                    if(user != null) {


                                                        String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                                                        String nomeUtente =  mailLogged.replace("@gmail.com", "");

                                                        trovaKeyUtente(nomeUtente, "commenti", listaVideo.get(finalI1));

                                                    }
                                                    startActivity(intent);
                                                    startActivity(intent);
                                                }
                                            });

                                            i++;
                                        } else {
                                            linear.getChildAt(1).setVisibility(View.INVISIBLE);
                                            break;
                                        }

                                    }

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Uh-oh, an error occurred!
                        }
                    });



        } else {

            user = FirebaseAuth.getInstance().getCurrentUser();




            //GALLERIA VIDEO GESTI
            StorageReference listRef = storageReference.child("/videos/gesti/");
            textView.setText("Allenati");
            listRef.listAll()
                    .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                        @Override
                        public void onSuccess(ListResult listResult) {
                            for (StorageReference prefix : listResult.getPrefixes()) {
                                // All the prefixes under listRef.
                                // You may call listAll() recursively on them.
                            }

                            readGesti(new MyCallback() {
                                @Override
                                public void onCallback(ArrayList<String> nomiContenuti) {


                                    for (StorageReference item : listResult.getItems()) {
                                        // All the items under listRef.
                                        String[] parts = item.getName().split("\\$");

                                        if(nomiContenuti.contains(parts[0])) {
                                            listaVideo.add(parts[0]);
                                            listaVideoFra.add(parts[1]);
                                            listaVideoEng.add(parts[2]);

                                        }

                                    }

                                    int j = 0;
                                    int i = 0;
                                    while (i != listaVideo.size()) {

                                        LinearLayout linear = (LinearLayout) LayoutInflater.from(ActivityGalleriaVideo.this).inflate(R.layout.linear_galleria, null);
                                        linearLayout.addView(linear, j);
                                        j++;

                                        //PRIMA CARD
                                        View card1 = linear.getChildAt(0);
                                        TextView textCard1 = linear.getChildAt(0).findViewById(R.id.textCard1);
                                        textCard1.setText(listaVideo.get(i));

                                        ImageView imageCard1 = linear.getChildAt(0).findViewById(R.id.imageCard1);


                                        storageReference.child("/videos/gesti/" + listaVideo.get(i) + "$" + listaVideoFra.get(i) + "$" +
                                                listaVideoEng.get(i)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Glide.with(ActivityGalleriaVideo.this)
                                                        .load(uri)
                                                        .centerCrop()
                                                        .into(imageCard1);
                                            }
                                        });


                                        int finalI = i;
                                        imageCard1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(getApplicationContext(), ActivityVideo.class);
                                                intent.putExtra("nome", listaVideo.get(finalI) + "$" + listaVideoFra.get(finalI) + "$" +
                                                        listaVideoEng.get(finalI));
                                                //controllo se l'utente è loggato
                                                if(user != null) {


                                                    String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                                                    String nomeUtente =  mailLogged.replace("@gmail.com", "");

                                                    trovaKeyUtente(nomeUtente, "gesti", listaVideo.get(finalI));

                                                }
                                                intent.putExtra("tipo", "gesto");
                                                startActivity(intent);
                                            }
                                        });

                                        i++;

                                        if (i != listaVideo.size()) {
                                            //SECONDA CARD
                                            View card2 = linear.getChildAt(1);
                                            TextView textCard2 = linear.getChildAt(1).findViewById(R.id.textCard2);
                                            textCard2.setText(listaVideo.get(i));
                                            ImageView imageCard2 = linear.getChildAt(1).findViewById(R.id.imageCard2);

                                            storageReference.child("/videos/gesti/" + listaVideo.get(i) + "$" + listaVideoFra.get(i) + "$" +
                                                    listaVideoEng.get(i)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Glide.with(ActivityGalleriaVideo.this)
                                                            .load(uri)
                                                            .centerCrop()
                                                            .into(imageCard2);
                                                }
                                            });

                                            int finalI1 = i;
                                            imageCard2.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent = new Intent(getApplicationContext(), ActivityVideo.class);
                                                    intent.putExtra("nome", listaVideo.get(finalI1) + "$" + listaVideoFra.get(finalI1) + "$" +
                                                            listaVideoEng.get(finalI1));
                                                    intent.putExtra("tipo", "gesto");
                                                    //controllo se l'utente è loggato
                                                    if(user != null) {


                                                        String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                                                        String nomeUtente =  mailLogged.replace("@gmail.com", "");

                                                        trovaKeyUtente(nomeUtente, "gesti", listaVideo.get(finalI1));

                                                    }
                                                    startActivity(intent);
                                                }
                                            });

                                            i++;
                                        } else {
                                            linear.getChildAt(1).setVisibility(View.INVISIBLE);
                                            break;
                                        }

                                    }

                                }


                            });
                        }
   //


                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Uh-oh, an error occurred!
                        }
                    });



        }



    }
    public interface MyCallback {
        void onCallback(ArrayList<String> nomiContenuti);
    }

    public void readGesti(MyCallback myCallback) {
        dr = FirebaseDatabase.getInstance().getReference();

        DatabaseReference rf = dr.child("video").child("gesti");

        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                    // A new comment has been added, add it to the displayed list
                    String mostra = ds.child("mostra").getValue(String.class);
                    String ita = ds.child("ita").getValue(String.class);

                    if(mostra.equals("si")) {

                        nomiContenuti.add(ita);

                    }


                }
                myCallback.onCallback(nomiContenuti);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void readCommenti(MyCallback myCallback) {
        dr = FirebaseDatabase.getInstance().getReference();

        DatabaseReference rf = dr.child("video").child("commenti");

        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                    // A new comment has been added, add it to the displayed list
                    String mostra = ds.child("mostra").getValue(String.class);
                    String ita = ds.child("nome").getValue(String.class);

                    if(mostra.equals("si")) {

                        nomiContenuti.add(ita);

                    }


                }
                myCallback.onCallback(nomiContenuti);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void trovaKeyUtente(String nomeUtente, String tipoVideo, String nomeVideo) {

        String id = UUID.randomUUID().toString();

        dr = FirebaseDatabase.getInstance().getReference();

        DatabaseReference rf = dr.child("utenti");
        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    String username = postSnapshot.child("username").getValue(String.class);
                    if (username.equals(nomeUtente)) {

                        key = postSnapshot.getKey();

                        scriviElemento(id, tipoVideo, nomeVideo);

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    public void scriviElemento(String id, String tipoVideo, String nomeVideo) {

        ArrayList<String> videos = new ArrayList<>();

        dr = FirebaseDatabase.getInstance().getReference();

        if(tipoVideo.equals("gesti")) {
            DatabaseReference rf = dr.child("utenti").child(key).child("allenati");

            DatabaseReference gf = FirebaseDatabase.getInstance().getReference();


            rf.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot ds : snapshot.getChildren()) {
                        Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                        // A new comment has been added, add it to the displayed list
                        String video = ds.child("nomeVideo").getValue(String.class);

                        videos.add(video);

                    }

                    if(!videos.contains(nomeVideo)) {

                        gf.child("utenti").child(key).child("allenati").child(id).child("nomeVideo").setValue(nomeVideo);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        else if(tipoVideo.equals("commenti")) {
            DatabaseReference rf = dr.child("utenti").child(key).child("video");

            DatabaseReference gf = FirebaseDatabase.getInstance().getReference();


            rf.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot ds : snapshot.getChildren()) {
                        Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                        // A new comment has been added, add it to the displayed list
                        String video = ds.child("nomeVideo").getValue(String.class);

                        videos.add(video);

                    }

                    if(!videos.contains(nomeVideo)) {

                        gf.child("utenti").child(key).child("video").child(id).child("nomeVideo").setValue(nomeVideo);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }




    }

    public void scriviAttivitaDb(String id, String nomeUtente, String stringa) {

        //prendo la key dello user  loggato
        dr = FirebaseDatabase.getInstance().getReference();

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        if(stringa.equals("gesti")) {

            String completato = "Ha eseguito l'attività allenamento video in data:" + " " +formattedDate;
            AttivitaUtente attivitaUtente = new AttivitaUtente(completato, formattedDate);

            dr.child("utenti").child(key).child("attivita").child(id).setValue(attivitaUtente);

        }

        if(stringa.equals("commenti")) {

            String completato = "Ha guardato un video presente nella sezione video in data:" + " " +formattedDate;
            AttivitaUtente attivitaUtente = new AttivitaUtente(completato, formattedDate);

            dr.child("utenti").child(key).child("attivita").child(id).setValue(attivitaUtente);

        }


    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}