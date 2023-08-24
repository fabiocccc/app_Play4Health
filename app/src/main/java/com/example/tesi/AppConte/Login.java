package com.example.tesi.AppConte;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tesi.AppPavone.ActivityAscolta;
import com.example.tesi.AppPavone.Json;
import com.example.tesi.AppTravisani.Home;
import com.example.tesi.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class Login extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    private Button button_Foto;
    private Button button_Login;
    private Button button_Ospite;
    private ImageView image_Carica;
    private ImageView imageView2;
    private String image_CaricaBase64;
    private ArrayList<Utente> listaUtenti;
    private String nomeUtente;
    private int utenteTrovato = 0;
    DatabaseReference dr;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        button_Foto = findViewById(R.id.button_Foto);
        button_Login = findViewById(R.id.button_Salva);
        button_Ospite = findViewById(R.id.button_Ospite);
        image_Carica= findViewById(R.id.image_Carica);
        imageView2= findViewById(R.id.image_Carica2);


        listaUtenti = new ArrayList<>();
        nomeUtente = new String();
        imageView2.setVisibility(View.INVISIBLE);



        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (utenteTrovato == 1) {
                    System.out.println("utenteTrovato:"+utenteTrovato);
                    //TODO salva nome utente per passarlo agli altri activity
                    //TODO mettere schermata login effettuato con successo

                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    intent.putExtra("nomeUtente", nomeUtente);
                    startActivity(intent);
                    finish();

                }
                else {

                    //TODO utente non trovato, login fallito
                }




            }
        });

        button_Ospite.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();
            }
        });

/*
        readData(new MyCallback() {
            @Override
            public void onCallback(ArrayList<Utente> listaUtenti) {
                int trovato = 0;
                String utente = "";

                image_Carica.setVisibility(View.VISIBLE);
                //image_Carica.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                byte[] decodedString = Base64.decode(listaUtenti.get(0).getImmagine(), Base64.NO_WRAP);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                image_Carica.setImageBitmap(decodedByte);
                //image_Carica.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                image_Carica.setDrawingCacheEnabled(true);

                Bitmap bmap = image_Carica.getDrawingCache();
                //bmap.eraseColor(Color.TRANSPARENT);


                BitmapFactory.Options op = new BitmapFactory.Options();
                op.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bmap = BitmapFactory.decodeFile(path, op);


                bmap = Bitmap.createScaledBitmap(bmap, 80, 56, false);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
                byte[] imageBytes = baos.toByteArray();
                String input = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
                System.out.println("encode i:"+ input);



                System.out.println("stringa da cercare:"+ image_CaricaBase64);
                for (int i=0; i<listaUtenti.size(); i++){


                    System.out.println("elemento i:"+ listaUtenti.get(i).getImmagine());
                    if((listaUtenti.get(i).getImmagine()).equals(image_CaricaBase64)) {
                        System.out.print("elemento trovato\n");
                        trovato = 1;
                        utente = listaUtenti.get(i).getUsername();
                    }

                }

                if (trovato == 1) {

                    System.out.print("elemento trovato\n");
                    System.out.print("nome utente:"+utente);
                }
                else if (trovato == 0){
                    System.out.print("elemento non trovato\n");
                }

            }


        });
*/




    }

    protected void onStart() {
        super.onStart();

        button_Foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Seleziona immagine"), PICK_IMAGE);
            }
        });



    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {

            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                // update the preview image in the layout
                image_Carica.setImageURI(selectedImageUri);
                image_Carica.setVisibility(View.VISIBLE);


                Bitmap photo = null;
                image_Carica.setDrawingCacheEnabled(true);
                photo = image_Carica.getDrawingCache();

                try {
                    photo = MediaStore.Images.Media.getBitmap(getContentResolver() , selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();

                image_CaricaBase64 = Base64.encodeToString(b, Base64.DEFAULT);
                //System.out.println("stringa da cercare:"+ image_CaricaBase64);

                listaUtenti = new ArrayList<>();

                readData(new MyCallback() {
                    @Override
                    public void onCallback(ArrayList<Utente> listaUtenti) {
                        int trovato = 0;

                        //converto in base64 l'immagine presente nella ImageView inserita dall'utente
                        byte[] decodedString = Base64.decode(image_CaricaBase64, Base64.NO_WRAP);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        image_Carica.setImageBitmap(decodedByte);

                        for (int i=0; i<listaUtenti.size(); i++){

                            //prendo l'immagine dal db e la inserisco nella ImageView non visibile
                            byte[] decodedString2 = Base64.decode(listaUtenti.get(i).getImmagine(), Base64.NO_WRAP);
                            Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
                            imageView2.setImageBitmap(decodedByte2);

                            //converto in base64 l'immagine presente nella ImageView
                            ByteArrayOutputStream prova = new ByteArrayOutputStream();
                            decodedByte2.compress(Bitmap.CompressFormat.PNG, 100, prova); //bm is the bitmap object
                            byte[] prova2 = prova.toByteArray();

                            //controllo che le 2 immagini siano uguali controllando i loro byte array
                            if(Arrays.equals(decodedString, prova2)) {

                                trovato = 1;
                                nomeUtente = listaUtenti.get(i).getUsername();
                                System.out.println("nome:"+nomeUtente);

                            }

                        }

                        if (trovato == 1) {

                            utenteTrovato = 1;
                        }
                        else if (trovato == 0){
                            System.out.print("elemento non trovato\n");
                            nomeUtente ="";
                        }

                    }


                    });

            }

        }




    }


    public interface MyCallback {
        void onCallback(ArrayList<Utente> listaUtenti);
    }

    public void readData(MyCallback myCallback) {
        dr = FirebaseDatabase.getInstance().getReference();

        DatabaseReference rf = dr.child("utenti");

        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                    // A new comment has been added, add it to the displayed list
                    String immagine = ds.child("immagine").getValue(String.class);
                    String username = ds.child("username").getValue(String.class);

                    Utente utente = new Utente(immagine, username);
                    listaUtenti.add(utente);

                }
                myCallback.onCallback(listaUtenti);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
