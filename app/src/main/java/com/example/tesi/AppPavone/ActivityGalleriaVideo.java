package com.example.tesi.AppPavone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
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
    private ArrayList<String> lista;
    private ArrayList<String> listaVideo;
    private ArrayList<String> listaVideoFra;
    private ArrayList<String> listaVideoEng;
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
        lista = new ArrayList<>();
        listaVideo = new ArrayList<>();
        listaVideoFra = new ArrayList<>();
        listaVideoEng = new ArrayList<>();
        textView = findViewById(R.id.textView);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

        if(connected){
            user = FirebaseAuth.getInstance().getCurrentUser();

            //controllo se l'utente è loggato
            if(user != null) {


                String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                String nomeUtente =  mailLogged.replace("@gmail.com", "");

                trovaKeyUtente(nomeUtente);

            }

        } else {
            Toast.makeText(getApplicationContext(), "Non sei connesso a Internet", Toast.LENGTH_LONG).show();
            finish();
        }

        if(getIntent().getStringExtra("tipo").equals("commento")){
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

                            for (StorageReference item : listResult.getItems()) {
                                // All the items under listRef.
                                lista.add(item.getName());
                                String[] parts = item.getName().split("\\$");
                                listaVideo.add(parts[0]);
                            }

                            int j = 0;
                            int i = 0;
                            while (i != listaVideo.size()){

                                LinearLayout linear = (LinearLayout) LayoutInflater.from(ActivityGalleriaVideo.this).inflate( R.layout.linear_galleria, null);
                                linearLayout.addView(linear, j);
                                j++;

                                //PRIMA CARD
                                View card1 = linear.getChildAt(0);
                                TextView textCard1 = linear.getChildAt(0).findViewById(R.id.textCard1);
                                textCard1.setText(listaVideo.get(i));
                                ImageView imageCard1 = linear.getChildAt(0).findViewById(R.id.imageCard1);

                                storageReference.child("/videos/commenti/" + listaVideo.get(i)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
                                        intent.putExtra("nome", lista.get(finalI));
                                        intent.putExtra("tipo", "commento");
                                        startActivity(intent);
                                    }
                                });

                                i++;

                                if(i != listaVideo.size()){
                                    //SECONDA CARD
                                    View card2 = linear.getChildAt(1);
                                    TextView textCard2 = linear.getChildAt(1).findViewById(R.id.textCard2);
                                    textCard2.setText(listaVideo.get(i));
                                    ImageView imageCard2 = linear.getChildAt(1).findViewById(R.id.imageCard2);

                                    storageReference.child("/videos/commenti/" + listaVideo.get(i)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
                                            intent.putExtra("nome", listaVideo.get(finalI1) );
                                            intent.putExtra("tipo", "commento");
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
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Uh-oh, an error occurred!
                        }
                    });



        } else {
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

                            for (StorageReference item : listResult.getItems()) {
                                // All the items under listRef.
                                String[] parts = item.getName().split("\\$");
                                listaVideo.add(parts[0]);
                                listaVideoFra.add(parts[1]);
                                listaVideoEng.add(parts[2]);
                            }

                            int j = 0;
                            int i = 0;
                            while (i != listaVideo.size()){

                                LinearLayout linear = (LinearLayout) LayoutInflater.from(ActivityGalleriaVideo.this).inflate( R.layout.linear_galleria, null);
                                linearLayout.addView(linear, j);
                                j++;

                                //PRIMA CARD
                                View card1 = linear.getChildAt(0);
                                TextView textCard1 = linear.getChildAt(0).findViewById(R.id.textCard1);
                                textCard1.setText(listaVideo.get(i));
                                ImageView imageCard1 = linear.getChildAt(0).findViewById(R.id.imageCard1);


                                storageReference.child("/videos/gesti/" + listaVideo.get(i) +"$"+ listaVideoFra.get(i) +"$"+
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
                                        intent.putExtra("nome", listaVideo.get(finalI) + "$"+ listaVideoFra.get(finalI) +"$"+
                                                listaVideoEng.get(finalI));
                                        intent.putExtra("tipo", "gesto");
                                        startActivity(intent);
                                    }
                                });

                                i++;

                                if(i != listaVideo.size()){
                                    //SECONDA CARD
                                    View card2 = linear.getChildAt(1);
                                    TextView textCard2 = linear.getChildAt(1).findViewById(R.id.textCard2);
                                    textCard2.setText(listaVideo.get(i));
                                    ImageView imageCard2 = linear.getChildAt(1).findViewById(R.id.imageCard2);

                                    storageReference.child("/videos/gesti/" + listaVideo.get(i) +"$"+ listaVideoFra.get(i) +"$"+
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
                                            intent.putExtra("nome", listaVideo.get(finalI1) + "$"+ listaVideoFra.get(finalI1) +"$"+
                                                    listaVideoEng.get(finalI1));
                                            intent.putExtra("tipo", "gesto");
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
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Uh-oh, an error occurred!
                        }
                    });



        }



    }

    public void trovaKeyUtente(String nomeUtente) {

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

                        scriviAttivitaDb(id, nomeUtente);

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    public void scriviAttivitaDb(String id, String nomeUtente) {

        //prendo la key dello user  loggato
        dr = FirebaseDatabase.getInstance().getReference();

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        String completato = "Ha eseguito l'attività allenamento video:" + " " +formattedDate;

        //String dataFasulla = "29-08-2023";
        AttivitaUtente attivitaUtente = new AttivitaUtente(completato, formattedDate);

        dr.child("utenti").child(key).child("attivita").child(id).setValue(attivitaUtente);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}