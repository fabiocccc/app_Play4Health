package com.example.tesi.AppConte;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tesi.AppPavone.ActivityAscolta;
import com.example.tesi.AppPavone.ActivityGestioneDati;
import com.example.tesi.AppPavone.HomePrima;
import com.example.tesi.AppPavone.Json;
import com.example.tesi.AppTravisani.Home;
import com.example.tesi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
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
import java.util.Random;

public class Login extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    private Button button_Verifica;
    private ImageView image_Carica;
    private ImageView imagePw1;
    private ImageView imagePw2;
    private ImageView imagePw3;
    private String image_CaricaBase64;
    private ArrayList<Utente> listaUtenti;
    private String nomeUtente = "";
    private int utenteTrovato = 0;
    DatabaseReference dr;
    private String passwordStringaUser;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private TextView textUsername, textPassword;
    private EditText editTextUsername;

    private int errori = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();
        button_Verifica = findViewById(R.id.button_Verifica);
        image_Carica= findViewById(R.id.image_Carica2);

        imagePw1= findViewById(R.id.image1);
        imagePw2= findViewById(R.id.image2);
        imagePw3= findViewById(R.id.image3);

        textUsername = findViewById(R.id.txtUsername);
        textPassword = findViewById(R.id.txtPassword);
        editTextUsername = findViewById(R.id.editTextUsername);

        listaUtenti = new ArrayList<>();
        nomeUtente = new String();
        image_Carica.setVisibility(View.INVISIBLE);

        imagePw1.setVisibility(View.INVISIBLE);
        imagePw2.setVisibility(View.INVISIBLE);
        imagePw3.setVisibility(View.INVISIBLE);
        textPassword.setVisibility(View.INVISIBLE);



        button_Verifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button_Verifica.setVisibility(View.INVISIBLE);

                String username = editTextUsername.getText().toString();
                System.out.println(username);
                readData(new MyCallback() {
                    @Override
                    public void onCallback(ArrayList<Utente> listaUtenti) {
                        int trovato = 0;

                        for (int i=0; i<listaUtenti.size(); i++){

                            if(listaUtenti.get(i).getUsername().equals(username)) {

                                trovato = 1;
                                nomeUtente = listaUtenti.get(i).getUsername();
                                passwordStringaUser = listaUtenti.get(i).getImmagine();

                            }

                        }

                        if(trovato == 1) {
                            textPassword.setVisibility(View.VISIBLE);
                            imagePw1.setVisibility(View.VISIBLE);
                            imagePw2.setVisibility(View.VISIBLE);
                            imagePw3.setVisibility(View.VISIBLE);
                            editTextUsername.setVisibility(View.INVISIBLE);
                            textUsername.setVisibility(View.INVISIBLE);

                            int random = new Random().nextInt(6) + 0;
                            System.out.println(random);

                            editTextUsername.setKeyListener(null);
                            button_Verifica.setVisibility(View.INVISIBLE);

                            //inserisco la stringa password utente nell'imageview non visibile
                            byte[] decodedString = Base64.decode(passwordStringaUser, Base64.NO_WRAP);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            image_Carica.setImageBitmap(decodedByte);

                            //converto in base64 l'immagine presente nella ImageView
                            ByteArrayOutputStream prov = new ByteArrayOutputStream();
                            decodedByte.compress(Bitmap.CompressFormat.PNG, 100, prov); //bm is the bitmap object
                            byte[] prova = prov.toByteArray();
                            System.out.println(prova);


                            if(random == 0) {
                                imagePw1.setBackgroundResource(R.drawable.gatto_41);

                                byte[] decodedString2 = Base64.decode(passwordStringaUser, Base64.NO_WRAP);
                                Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
                                imagePw2.setImageBitmap(decodedByte2);

                                //converto in base64 l'immagine presente nella ImageView
                                ByteArrayOutputStream prov2 = new ByteArrayOutputStream();
                                decodedByte2.compress(Bitmap.CompressFormat.PNG, 100, prov2); //bm is the bitmap object
                                byte[] prova2 = prov2.toByteArray();
                                System.out.println(prova2);

                                imagePw3.setBackgroundResource(R.drawable.progetto_senza_titolo_2023_08_05t133418_991_870x563);

                                controllaImmagine(imagePw1, imagePw2, imagePw3, 2, prova2, prova);

                            }

                            else if(random == 1) {
                                imagePw2.setBackgroundResource(R.drawable.gatto_41);

                                byte[] decodedString2 = Base64.decode(passwordStringaUser, Base64.NO_WRAP);
                                Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
                                imagePw1.setImageBitmap(decodedByte2);

                                //converto in base64 l'immagine presente nella ImageView
                                ByteArrayOutputStream prov2 = new ByteArrayOutputStream();
                                decodedByte2.compress(Bitmap.CompressFormat.PNG, 100, prov2); //bm is the bitmap object
                                byte[] prova2 = prov2.toByteArray();
                                System.out.println(prova2);

                                imagePw3.setBackgroundResource(R.drawable.progetto_senza_titolo_2023_08_05t133418_991_870x563);

                                controllaImmagine(imagePw1, imagePw2, imagePw3, 1, prova2, prova);

                            }

                            else if(random == 2) {
                                imagePw2.setBackgroundResource(R.drawable.gatto_41);

                                byte[] decodedString2 = Base64.decode(passwordStringaUser, Base64.NO_WRAP);
                                Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
                                imagePw3.setImageBitmap(decodedByte2);

                                //converto in base64 l'immagine presente nella ImageView
                                ByteArrayOutputStream prov2 = new ByteArrayOutputStream();
                                decodedByte2.compress(Bitmap.CompressFormat.PNG, 100, prov2); //bm is the bitmap object
                                byte[] prova2 = prov2.toByteArray();
                                System.out.println(prova2);

                                imagePw1.setBackgroundResource(R.drawable.progetto_senza_titolo_2023_08_05t133418_991_870x563);

                                controllaImmagine(imagePw1, imagePw2, imagePw3, 3, prova2, prova);

                            }

                            else if(random == 3) {
                                imagePw3.setBackgroundResource(R.drawable.gatto_41);

                                byte[] decodedString2 = Base64.decode(passwordStringaUser, Base64.NO_WRAP);
                                Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
                                imagePw2.setImageBitmap(decodedByte2);

                                //converto in base64 l'immagine presente nella ImageView
                                ByteArrayOutputStream prov2 = new ByteArrayOutputStream();
                                decodedByte2.compress(Bitmap.CompressFormat.PNG, 100, prov2); //bm is the bitmap object
                                byte[] prova2 = prov2.toByteArray();
                                System.out.println(prova2);

                                imagePw1.setBackgroundResource(R.drawable.progetto_senza_titolo_2023_08_05t133418_991_870x563);

                                controllaImmagine(imagePw1, imagePw2, imagePw3, 2, prova2, prova);

                            }

                            else if(random == 4) {
                                imagePw1.setBackgroundResource(R.drawable.gatto_41);

                                byte[] decodedString2 = Base64.decode(passwordStringaUser, Base64.NO_WRAP);
                                Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
                                imagePw3.setImageBitmap(decodedByte2);

                                //converto in base64 l'immagine presente nella ImageView
                                ByteArrayOutputStream prov2 = new ByteArrayOutputStream();
                                decodedByte2.compress(Bitmap.CompressFormat.PNG, 100, prov2); //bm is the bitmap object
                                byte[] prova2 = prov2.toByteArray();
                                System.out.println(prova2);

                                imagePw2.setBackgroundResource(R.drawable.progetto_senza_titolo_2023_08_05t133418_991_870x563);

                                controllaImmagine(imagePw1, imagePw2, imagePw3, 3, prova2, prova);

                            }

                            else if(random == 5) {
                                imagePw3.setBackgroundResource(R.drawable.gatto_41);

                                byte[] decodedString2 = Base64.decode(passwordStringaUser, Base64.NO_WRAP);
                                Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
                                imagePw1.setImageBitmap(decodedByte2);

                                //converto in base64 l'immagine presente nella ImageView
                                ByteArrayOutputStream prov2 = new ByteArrayOutputStream();
                                decodedByte2.compress(Bitmap.CompressFormat.PNG, 100, prov2); //bm is the bitmap object
                                byte[] prova2 = prov2.toByteArray();
                                System.out.println(prova2);

                                imagePw2.setBackgroundResource(R.drawable.progetto_senza_titolo_2023_08_05t133418_991_870x563);

                                controllaImmagine(imagePw1, imagePw2, imagePw3, 1, prova2, prova);




                            }


                        }
                        else if (trovato == 0) {
                            Toast.makeText(getApplicationContext(), "Utente non trovato", Toast.LENGTH_SHORT).show();
                            button_Verifica.setVisibility(View.VISIBLE);
                        }




                    }


                });



            }
        });




    }

    public void controllaImmagine(ImageView image1, ImageView image2, ImageView image3, int numero, byte[] prova, byte[] prova2) {

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numero == 1) {

                    if(Arrays.equals(prova, prova2)) {

                        //FirebaseUser user = mAuth.getCurrentUser();

                        System.out.println("nome:"+nomeUtente);

                        String password = "Abcd12!m";

                        String email = nomeUtente.concat("@gmail.com");


                        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                if (task.isSuccessful()){
                                    boolean check =!task.getResult().getSignInMethods().isEmpty();
                                    if (!check){

                                        //effettuo la registrazione

                                        //creo l'utente nella sezione autenticazione di firebase

                                        mAuth.createUserWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            // Sign in success, update UI with the signed-in user's information
                                                            Log.d(TAG, "createUserWithEmail:success");
                                                            FirebaseUser user = mAuth.getCurrentUser();
                                                            System.out.println("firebase oncomplete:"+user);
                                                            //updateUI(user);
                                                        } else {
                                                            // If sign in fails, display a message to the user.
                                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                                                            //updateUI(null);
                                                        }
                                                    }
                                                });
                                    }
                                    else {
                                        //Toast.makeText(getApplicationContext(),"email already exst",Toast.LENGTH_LONG).show();

                                        //effettuo login
                                        mAuth.signInWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            // Sign in success, update UI with the signed-in user's information
                                                            Log.d(TAG, "signInWithEmail:success");
                                                            //if(user!= null) {

                                                            FirebaseUser user = mAuth.getCurrentUser();
                                                            //System.out.println("firebase oncomplete:"+user);

                                                            //email utente loggato
                                                            String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                                                            //System.out.println("firebase:"+mailLogged);
                                                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                            //System.out.println("firebase:"+uid);
                                                            // }

                                                            Toast.makeText(getApplicationContext(), "Accesso eseguito", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                            Intent intent = new Intent(getApplicationContext(), Home.class);
                                                            startActivity(intent);

                                                            //updateUI(user);
                                                        } else {
                                                            // If sign in fails, display a message to the user.
                                                            Log.d(TAG, "signInWithEmail:failure");

                                                            //updateUI(null);
                                                        }
                                                    }
                                                });


                                    }
                                }
                            }
                        });





                        dr = FirebaseDatabase.getInstance().getReference();

                        DatabaseReference rf = dr.child("utenti");
                        rf.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    String key = postSnapshot.getKey();
                                    Log.d("datafriebase", "" + key);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });
                    }

                }
                else {

                    errori = errori + 1;

                    if(errori == 1) {
                        String messaggio = "Tentativo 1/2";
                        showMessage(messaggio);

                    }

                    if(errori == 2) {
                        String messaggio = "Tentativo 2/2";
                        showMessage(messaggio);
                    }
                }


            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numero == 2) {

                    if(Arrays.equals(prova, prova2)) {

                        //FirebaseUser user = mAuth.getCurrentUser();

                        System.out.println("nome:"+nomeUtente);

                        String password = "Abcd12!m";

                        String email = nomeUtente.concat("@gmail.com");


                        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                if (task.isSuccessful()){
                                    boolean check =!task.getResult().getSignInMethods().isEmpty();
                                    if (!check){

                                        //effettuo la registrazione

                                        //creo l'utente nella sezione autenticazione di firebase

                                        mAuth.createUserWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            // Sign in success, update UI with the signed-in user's information
                                                            Log.d(TAG, "createUserWithEmail:success");
                                                            FirebaseUser user = mAuth.getCurrentUser();
                                                            System.out.println("firebase oncomplete:"+user);
                                                            //updateUI(user);
                                                        } else {
                                                            // If sign in fails, display a message to the user.
                                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                                                            //updateUI(null);
                                                        }
                                                    }
                                                });
                                    }
                                    else {
                                        //Toast.makeText(getApplicationContext(),"email already exst",Toast.LENGTH_LONG).show();

                                        //effettuo login
                                        mAuth.signInWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            // Sign in success, update UI with the signed-in user's information
                                                            Log.d(TAG, "signInWithEmail:success");
                                                            //if(user!= null) {

                                                            FirebaseUser user = mAuth.getCurrentUser();
                                                            //System.out.println("firebase oncomplete:"+user);

                                                            //email utente loggato
                                                            String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                                                            //System.out.println("firebase:"+mailLogged);
                                                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                            //System.out.println("firebase:"+uid);
                                                            // }

                                                            Toast.makeText(getApplicationContext(), "Accesso eseguito", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                            Intent intent = new Intent(getApplicationContext(), Home.class);
                                                            startActivity(intent);

                                                            //updateUI(user);
                                                        } else {
                                                            // If sign in fails, display a message to the user.
                                                            Log.d(TAG, "signInWithEmail:failure");

                                                            //updateUI(null);
                                                        }
                                                    }
                                                });


                                    }
                                }
                            }
                        });





                        dr = FirebaseDatabase.getInstance().getReference();

                        DatabaseReference rf = dr.child("utenti");
                        rf.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    String key = postSnapshot.getKey();
                                    Log.d("datafriebase", "" + key);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });

                    }

                }
                else {

                    errori = errori + 1;

                    if(errori == 1) {
                        String messaggio = "Tentativo 1/2";
                        showMessage(messaggio);

                    }

                    if(errori == 2) {
                        String messaggio = "Tentativo 2/2";
                        showMessage(messaggio);
                    }
                }


            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numero == 3) {

                    if(Arrays.equals(prova, prova2)) {

                        //FirebaseUser user = mAuth.getCurrentUser();

                        System.out.println("nome:"+nomeUtente);

                        String password = "Abcd12!m";

                        String email = nomeUtente.concat("@gmail.com");


                        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                if (task.isSuccessful()){
                                    boolean check =!task.getResult().getSignInMethods().isEmpty();
                                    if (!check){

                                        //effettuo la registrazione

                                        //creo l'utente nella sezione autenticazione di firebase

                                        mAuth.createUserWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            // Sign in success, update UI with the signed-in user's information
                                                            Log.d(TAG, "createUserWithEmail:success");
                                                            FirebaseUser user = mAuth.getCurrentUser();
                                                            System.out.println("firebase oncomplete:"+user);
                                                            //updateUI(user);
                                                        } else {
                                                            // If sign in fails, display a message to the user.
                                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                                                            //updateUI(null);
                                                        }
                                                    }
                                                });
                                    }
                                    else {
                                        //Toast.makeText(getApplicationContext(),"email already exst",Toast.LENGTH_LONG).show();

                                        //effettuo login
                                        mAuth.signInWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            // Sign in success, update UI with the signed-in user's information
                                                            Log.d(TAG, "signInWithEmail:success");
                                                            //if(user!= null) {

                                                            FirebaseUser user = mAuth.getCurrentUser();
                                                            //System.out.println("firebase oncomplete:"+user);

                                                            //email utente loggato
                                                            String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                                                            //System.out.println("firebase:"+mailLogged);
                                                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                            //System.out.println("firebase:"+uid);
                                                            // }

                                                            Toast.makeText(getApplicationContext(), "Accesso eseguito", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                            Intent intent = new Intent(getApplicationContext(), Home.class);
                                                            startActivity(intent);

                                                            //updateUI(user);
                                                        } else {
                                                            // If sign in fails, display a message to the user.
                                                            Log.d(TAG, "signInWithEmail:failure");

                                                            //updateUI(null);
                                                        }
                                                    }
                                                });


                                    }
                                }
                            }
                        });





                        dr = FirebaseDatabase.getInstance().getReference();

                        DatabaseReference rf = dr.child("utenti");
                        rf.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    String key = postSnapshot.getKey();
                                    Log.d("datafriebase", "" + key);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });
                    }

                }
                else {

                    errori = errori + 1;

                    if(errori == 1) {
                        String messaggio = "Tentativo 1/2";
                        showMessage(messaggio);
                    }

                    if(errori == 2) {
                        String messaggio = "Tentativo 2/2";
                        showMessage(messaggio);
                    }
                }


            }
        });
    }

    private void showMessage(String messaggio) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage(messaggio)
                .setTitle("Password sbagliata");
// Add the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                //System.out.println("firebase:"+mailLogged);

                //FirebaseAuth.getInstance().signOut();
                //Toast.makeText(getApplicationContext(), "Disconnessione effettuata", Toast.LENGTH_SHORT).show();

                if(messaggio.equals("Tentativo 2/2")) {

                    Intent i = new Intent(getApplicationContext(), TipoLogin.class);
                    startActivity(i);
                    finish();

                }


            }
        });

// Set other dialog properties
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void onStart() {
        super.onStart();

        user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        if(user != null) {

            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
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
