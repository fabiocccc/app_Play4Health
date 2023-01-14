package com.example.tesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ActivityGalleriaVideo extends AppCompatActivity {

    private LinearLayout linearLayout;
    private ArrayList<String> listaVideo;
    private ArrayList<String> listaVideoFra;
    private ArrayList<String> listaVideoEng;

    private FirebaseStorage storage;
    private StorageReference storageReference;


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
        listaVideoFra = new ArrayList<>();
        listaVideoEng = new ArrayList<>();

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

        if(connected){

        } else {
            Toast.makeText(getApplicationContext(), "Non sei connesso a Internet", Toast.LENGTH_LONG).show();
            finish();
        }

        if(getIntent().getStringExtra("tipo").equals("commento")){
            //GALLERIA VIDEO COMMENTI
            StorageReference listRef = storageReference.child("/videos/commenti/");

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
                                listaVideo.add(item.getName());
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
                                        intent.putExtra("nome", listaVideo.get(finalI));
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
                                            intent.putExtra("nome", listaVideo.get(finalI1));
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

    @Override
    protected void onStart() {
        super.onStart();


    }
}