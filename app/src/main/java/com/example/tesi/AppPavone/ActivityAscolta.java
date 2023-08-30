package com.example.tesi.AppPavone;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tesi.AppConte.AttivitaUtente;
import com.example.tesi.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ActivityAscolta extends AppCompatActivity {

    private LinearLayout linearLayout;
    private TextToSpeech textToSpeech;
    private FrameLayout button_aiuto;
    private ImageView help1;
    private AnimationDrawable animationDrawable = null;
    private AnimationDrawable animationDrawable1 = null;
    private AnimationDrawable animationDrawable2 = null;
    private String tipo;
    private TextView textView;

    private ArrayList<String> paroleIta;
    private ArrayList<Json> arrayJson;

    DatabaseReference dr;
    private ProgressBar progressBar;

    private String key;

    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ascolta);

        textView = findViewById(R.id.textView);
        button_aiuto = findViewById(R.id.button_aiuto);
        help1 = findViewById(R.id.help1);

        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        user = FirebaseAuth.getInstance().getCurrentUser();

        button_aiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                help1.setVisibility(View.VISIBLE);

                animationDrawable = (AnimationDrawable) help1.getBackground();
                animationDrawable.start();

                button_aiuto.setVisibility(View.GONE);

            }
        });

        findViewById(R.id.button_indietro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        linearLayout = findViewById(R.id.linear);

        if(getIntent().getStringExtra("tipo").equals("calcio")){
            tipo = "calcio";

        } else if(getIntent().getStringExtra("tipo").equals("corpo")) {
            tipo = "corpo";

        } else if(getIntent().getStringExtra("tipo").equals("salute")) {
            tipo = "salute";

        }

        arrayJson = new ArrayList<>();
        paroleIta = new ArrayList<>();

        if(tipo.equals("calcio")){
            textView.setText("Ascolta");

            //callback per leggere quello che stava sul primo json

            readDataJson1(new MyCallback() {
                @Override
                public void onCallback(ArrayList<String> paroleIta, ArrayList<Json> arrayJson) {
                    progressBar.setVisibility(View.GONE);
                    costruisciFinestre(paroleIta, arrayJson);

                    //controllo se l'utente è loggato
                    if(user != null) {


                            String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                            String nomeUtente =  mailLogged.replace("@gmail.com", "");

                            trovaKeyUtente(nomeUtente);

                    }
                    else{
                        System.out.println("nessun utene loggato");
                    }


                }

            });


        }  else {

            if(tipo.equals("corpo")){
                textView.setText("Parti del corpo");

                readDataJson2(new MyCallback() {
                    @Override
                    public void onCallback(ArrayList<String> paroleIta, ArrayList<Json> arrayJson) {
                        progressBar.setVisibility(View.GONE);
                        costruisciFinestre(paroleIta, arrayJson);


                    }


                });
            } else {
                textView.setText("Salute e benessere");

                readDataJson3(new MyCallback() {
                    @Override
                    public void onCallback(ArrayList<String> paroleIta, ArrayList<Json> arrayJson) {
                        progressBar.setVisibility(View.GONE);
                        costruisciFinestre(paroleIta, arrayJson);


                    }


                });
            }


        }

    }

    public void trovaKeyUtente(String nomeUtente) {

        String id = UUID.randomUUID().toString();

        dr = FirebaseDatabase.getInstance().getReference();

        DatabaseReference rf = dr.child("utenti");
        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                int cont = 0;
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

        String completato = "L'utente" + " " + nomeUtente + " " + "ha completato l'attività ascolta in data:" + " " +formattedDate;

        //String dataFasulla = "29-08-2023";
        AttivitaUtente attivitaUtente = new AttivitaUtente(completato, formattedDate);

        dr.child("utenti").child(key).child("attivita").child(id).setValue(attivitaUtente);

    }


    @Override
    protected void onStart() {
        super.onStart();



    }

    private void buildCard(String ita, ArrayList<Json> array){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAscolta.this);
        builder.setCancelable(true);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_card, null);
        Spinner spinner = dialogView.findViewById(R.id.spinnerCard);
        ImageView imageView = dialogView.findViewById(R.id.imageCard);
        ImageView help2 = dialogView.findViewById(R.id.help2);
        ImageView help3 = dialogView.findViewById(R.id.help3);

        ArrayList<String> paroleAlert = new ArrayList<>();
        paroleAlert.add(ita);


            for(int k=0; k!= array.size(); k++){
                if(ita.equals(array.get(k).getIta())){
                    paroleAlert.add(array.get(k).getFra());
                    paroleAlert.add(array.get(k).getEng());
                    byte[] decodedString = Base64.decode(array.get(k).getImg(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageView.setImageBitmap(decodedByte);
                }
            }


        if(animationDrawable != null){
            animationDrawable = null;
            help2.setVisibility(View.VISIBLE);

            animationDrawable1 = (AnimationDrawable) help2.getBackground();
            animationDrawable1.start();

        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(animationDrawable1 != null){
                    animationDrawable1.stop();
                    animationDrawable1 = null;

                    help2.setVisibility(View.GONE);
                    help3.setVisibility(View.VISIBLE);

                    animationDrawable2 = (AnimationDrawable) help3.getBackground();
                    animationDrawable2.start();
                }
                textToSpeech.setLanguage(Locale.ITALIAN);
                String toSpeakIt = spinner.getSelectedItem().toString();

                textToSpeech.speak(toSpeakIt, TextToSpeech.QUEUE_FLUSH, null);
                spinner.setSelection(0);
            }
        });

        SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), paroleAlert);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ITALIAN);
                    textToSpeech.speak(ita, TextToSpeech.QUEUE_FLUSH, null);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if(animationDrawable2 != null){
                                help3.setVisibility(View.GONE);
                                animationDrawable2.stop();
                                animationDrawable2 = null;

                                button_aiuto.setVisibility(View.VISIBLE);
                            }

                            switch (i){
                                case 0:

                                    textToSpeech.setLanguage(Locale.ITALIAN);
                                    String toSpeakIt = spinner.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakIt, TextToSpeech.QUEUE_FLUSH, null);
                                    break;
                                case 1:

                                    textToSpeech.setLanguage(Locale.FRENCH);
                                    String toSpeakFr = spinner.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakFr, TextToSpeech.QUEUE_FLUSH, null);
                                    break;
                                case 2:

                                    textToSpeech.setLanguage(Locale.ENGLISH);
                                    String toSpeakEn = spinner.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakEn, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                button_aiuto.setVisibility(View.VISIBLE);
            }
        });
        builder.setView(dialogView);
        AlertDialog alert11 = builder.create();
        alert11.show();


    }

    public interface MyCallback {
        void onCallback(ArrayList<String> paroleIta, ArrayList<Json> arrayJson);
    }

    public void readDataJson1(MyCallback myCallback) {
        dr = FirebaseDatabase.getInstance().getReference();

            DatabaseReference rf = dr.child("Json1");

            rf.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot ds : snapshot.getChildren()) {
                        Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                        // A new comment has been added, add it to the displayed list
                        String eng = ds.child("eng").getValue(String.class);
                        String fra = ds.child("fra").getValue(String.class);
                        String img = ds.child("img").getValue(String.class);
                        String ita = ds.child("ita").getValue(String.class);
                        String sug1 = ds.child("sug1").getValue(String.class);
                        String sug2 = ds.child("sug2").getValue(String.class);
                        String sug3 = ds.child("sug3").getValue(String.class);
                        Boolean svolto = ds.child("svolto").getValue(Boolean.class);

                        Json json = new Json(ita, fra, eng, sug1, sug2, sug3, img, svolto);
                        paroleIta.add(ita);
                        arrayJson.add(json);

                    }
                    myCallback.onCallback(paroleIta, arrayJson);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }

    public void readDataJson2(MyCallback myCallback) {
        dr = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rf = dr.child("Json2");

        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                    String eng = ds.child("eng").getValue(String.class);
                    String fra = ds.child("fra").getValue(String.class);
                    String img = ds.child("img").getValue(String.class);
                    String ita = ds.child("ita").getValue(String.class);
                    String tipo = ds.child("tipo").getValue(String.class);

                    if(tipo.equals("corpo")) {
                        Json json = new Json(ita, fra, eng, tipo, img);
                        paroleIta.add(ita);
                        arrayJson.add(json);
                    }

                }

                myCallback.onCallback(paroleIta, arrayJson);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readDataJson3(MyCallback myCallback) {
        dr = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rf = dr.child("Json2");

        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                    String eng = ds.child("eng").getValue(String.class);
                    String fra = ds.child("fra").getValue(String.class);
                    String img = ds.child("img").getValue(String.class);
                    String ita = ds.child("ita").getValue(String.class);
                    String tipo = ds.child("tipo").getValue(String.class);

                    if(tipo.equals("salute")) {
                        Json json = new Json(ita, fra, eng, tipo, img);
                        paroleIta.add(ita);
                        arrayJson.add(json);
                    }

                }

                myCallback.onCallback(paroleIta, arrayJson);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

        public void costruisciFinestre(ArrayList<String> paroleIta, ArrayList<Json> arrayJson) {

        int i = 0;
        int j = 0;

            while( i!= paroleIta.size() ){
                LinearLayout linear = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate( R.layout.linear_ascolta, null);
                linearLayout.addView(linear, j);
                j++;

                //PRIMA CARD
                View card1 = linear.getChildAt(0);
                TextView textCard1 = linear.getChildAt(0).findViewById(R.id.textCard1);
                textCard1.setText(paroleIta.get(i));
                ImageView imageCard1 = linear.getChildAt(0).findViewById(R.id.imageCard1);


                for(int k=0; k!= arrayJson.size(); k++){
                    if(paroleIta.get(i).equals(arrayJson.get(k).getIta()) && arrayJson.get(k).getImg() != ""){
                        byte[] decodedString = Base64.decode(arrayJson.get(k).getImg(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageCard1.setImageBitmap(decodedByte);
                    }
                }


                int finalI1 = i;
                card1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(animationDrawable != null) {
                            help1.setVisibility(View.GONE);
                            animationDrawable.stop();
                        }
                        buildCard(paroleIta.get(finalI1), arrayJson);
                    }
                });
                i++;


                //SECONDA CARD
                if(i != paroleIta.size()){
                    View card2 = linear.getChildAt(1);
                    TextView textCard2 = linear.getChildAt(1).findViewById(R.id.textCard2);
                    textCard2.setText(paroleIta.get(i));
                    ImageView imageCard2 = linear.getChildAt(1).findViewById(R.id.imageCard2);


                    for(int k=0; k!= arrayJson.size(); k++){
                        if(paroleIta.get(i).equals(arrayJson.get(k).getIta()) && arrayJson.get(k).getImg() != ""){
                            byte[] decodedString = Base64.decode(arrayJson.get(k).getImg(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imageCard2.setImageBitmap(decodedByte);

                        }
                    }

                    int finalI = i;
                    card2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(animationDrawable != null) {
                                help1.setVisibility(View.GONE);
                                animationDrawable.stop();
                            }
                            buildCard(paroleIta.get(finalI), arrayJson);
                        }
                    });

                    i++;
                } else {
                    linear.getChildAt(1).setVisibility(View.INVISIBLE);
                    linear.getChildAt(2).setVisibility(View.INVISIBLE);
                    break;
                }

                //TERZA CARD
                if(i != paroleIta.size()){
                    View card3 = linear.getChildAt(2);
                    TextView textCard3 = linear.getChildAt(2).findViewById(R.id.textCard3);
                    textCard3.setText(paroleIta.get(i));
                    ImageView imageCard3 = linear.getChildAt(2).findViewById(R.id.imageCard3);


                    for(int k=0; k!= arrayJson.size(); k++){
                        if(paroleIta.get(i).equals(arrayJson.get(k).getIta()) && arrayJson.get(k).getImg() != ""){
                            byte[] decodedString = Base64.decode(arrayJson.get(k).getImg(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imageCard3.setImageBitmap(decodedByte);
                        }
                    }


                    int finalI = i;
                    card3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(animationDrawable != null) {
                                help1.setVisibility(View.GONE);
                                animationDrawable.stop();
                            }
                            buildCard(paroleIta.get(finalI), arrayJson);
                        }
                    });

                    i++;
                } else {
                    linear.getChildAt(2).setVisibility(View.INVISIBLE);
                    break;
                }
            }

    }
}