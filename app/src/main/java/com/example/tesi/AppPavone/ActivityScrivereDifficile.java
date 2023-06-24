package com.example.tesi.AppPavone;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tesi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ActivityScrivereDifficile extends AppCompatActivity {

    private LinearLayout linearLettere;
    private LinearLayout linearLettere1;
    private LinearLayout linearRisposta;
    private LinearLayout linearRisposta1;
    private ImageView imageView;
    private Spinner spinner;
    private TextToSpeech textToSpeech;
    private FloatingActionButton button_Ascolta;
    private ImageView esito1;
    private Boolean ascoltato;
    
    private String corretta;
    private String mischiata;

    private FrameLayout button_aiuto;
    private ImageView help1;
    private ImageView help2;
    private AnimationDrawable animationDrawable = null;
    private AnimationDrawable animationDrawableScelta1 = null;

    private Button button_avanti;

    private ArrayList<Json> arrayJson;

    DatabaseReference dr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrivere_difficile);

        findViewById(R.id.button_indietro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        imageView = findViewById(R.id.imageView_Scrivere);
        spinner = findViewById(R.id.spinner);
        button_Ascolta = findViewById(R.id.button_Ascolta2);
        esito1 = findViewById(R.id.esito1);

        button_aiuto = findViewById(R.id.button_aiuto);
        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);

        button_avanti = findViewById(R.id.button_avanti);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ascoltato = false;

        button_aiuto.setVisibility(View.VISIBLE);
        esito1.setVisibility(View.GONE);
        esito1.clearAnimation();

        button_avanti.setVisibility(View.GONE);
        button_avanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();

            }
        });

        arrayJson = new ArrayList<>();
        readDataJson1(new MyCallback() {
            @Override
            public void onCallback(ArrayList<Json> arrayJson) {

                int random = (int) (Math.random() * arrayJson.size());

                ArrayList<String> parole = new ArrayList<>();

                    String s = arrayJson.get(random).getIta();
                    while(s.contains(" ")){
                        random = (int) (Math.random() * arrayJson.size());
                        s = arrayJson.get(random).getIta();
                    }

                    corretta = arrayJson.get(random).getIta();
                    parole.add(arrayJson.get(random).getFra());
                    parole.add(arrayJson.get(random).getEng());

                    byte[] decodedString = Base64.decode(arrayJson.get(random).getImg(), Base64.DEFAULT);

                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageView.setImageBitmap(decodedByte);
                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(), parole, false);
                spinner.setAdapter(spinnerAdapter);
                spinner.setSelection(-1);

                //Toast.makeText(getApplicationContext(), "-"+ lettera.getChildAt(1).toString(), Toast.LENGTH_SHORT).show();
                List<String> characters = Arrays.asList(corretta.split(""));
                Collections.shuffle(characters);
                mischiata = "";
                for (String character : characters)
                {
                    mischiata += character;
                }

                linearLettere = findViewById(R.id.linear_lettere);
                linearLettere1 = findViewById(R.id.linear_lettere1);
                linearRisposta = findViewById(R.id.linear_risposta);
                linearRisposta1 = findViewById(R.id.linear_risposta1);

                linearRisposta.removeAllViews();
                linearRisposta1.removeAllViews();

                int i=0;
                while(i != corretta.length() ){
                    FrameLayout lettera = null;
                    if(i >= 7){
                        linearRisposta1.setVisibility(View.VISIBLE);
                        lettera = (FrameLayout) LayoutInflater.from(getApplicationContext()).inflate( R.layout.lettera, null);
                        linearLettere1.addView(lettera);
                        TextView t1 = (TextView) lettera.getChildAt(0);
                        Character c = mischiata.charAt(i);
                        t1.setText(c.toString());
                        i++;
                    } else{
                        lettera = (FrameLayout) LayoutInflater.from(getApplicationContext()).inflate( R.layout.lettera, null);
                        linearLettere.addView(lettera);
                        TextView t1 = (TextView) lettera.getChildAt(0);
                        Character c = mischiata.charAt(i);
                        t1.setText(c.toString());
                        i++;
                    }


                    FrameLayout finalLettera = lettera;
                    lettera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(ascoltato){

                                esito1.setVisibility(View.GONE);
                                esito1.clearAnimation();

                                if(animationDrawableScelta1 != null) {
                                    help2.setVisibility(View.GONE);
                                    animationDrawableScelta1.stop();
                                    animationDrawableScelta1 = null;

                                    button_aiuto.setVisibility(View.VISIBLE);
                                }

                                LinearLayout v = (LinearLayout) finalLettera.getParent();

                                if(getResources().getResourceEntryName(v.getId()).equals("linear_risposta")){
                                    linearRisposta.removeView(finalLettera);

                                    if(linearLettere.getChildCount() < 7){
                                        linearLettere.addView(finalLettera);
                                    } else {
                                        linearLettere1.addView(finalLettera);
                                    }
                                    controllaLettere();

                                } else if (getResources().getResourceEntryName(v.getId()).equals("linear_risposta1")){
                                    linearRisposta1.removeView(finalLettera);

                                    if(linearLettere.getChildCount() < 7){
                                        linearLettere.addView(finalLettera);
                                    } else {
                                        linearLettere1.addView(finalLettera);
                                    }
                                    controllaLettere();


                                }else if (getResources().getResourceEntryName(v.getId()).equals("linear_lettere")){
                                    linearLettere.removeView(finalLettera);

                                    if(linearRisposta.getChildCount() < 7){
                                        linearRisposta.addView(finalLettera);
                                    } else {
                                        linearRisposta1.addView(finalLettera);
                                    }
                                    controllaLettere();


                                } else if (getResources().getResourceEntryName(v.getId()).equals("linear_lettere1")){
                                    linearLettere1.removeView(finalLettera);

                                    if(linearRisposta.getChildCount() < 7){
                                        linearRisposta.addView(finalLettera);
                                    } else {
                                        linearRisposta1.addView(finalLettera);
                                    }
                                    controllaLettere();

                                }

                            } else {
                                help1.setVisibility(View.VISIBLE);

                                animationDrawable = (AnimationDrawable) help1.getBackground();
                                animationDrawable.start();
                                button_aiuto.setVisibility(View.GONE);
                            }


                        }
                    });

                }

                button_aiuto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        help1.setVisibility(View.VISIBLE);

                        animationDrawable = (AnimationDrawable) help1.getBackground();
                        animationDrawable.start();
                        button_aiuto.setVisibility(View.GONE);
                    }
                });

                textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {

                        if(status != TextToSpeech.ERROR) {

                            button_Ascolta.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    ascoltato = true;

                                    if(animationDrawable != null) {
                                        help1.setVisibility(View.GONE);
                                        animationDrawable.stop();
                                        animationDrawable = null;

                                        help2.setVisibility(View.VISIBLE);

                                        animationDrawableScelta1 = (AnimationDrawable) help2.getBackground();
                                        animationDrawableScelta1.start();

                                    }

                                    textToSpeech.setLanguage(Locale.ITALIAN);
                                    String toSpeakIt = corretta;

                                    textToSpeech.speak(toSpeakIt, TextToSpeech.QUEUE_FLUSH, null);

                                }
                            });

                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if(ascoltato){
                                        switch (i){
                                            case 0:
                                                textToSpeech.setLanguage(Locale.FRANCE);
                                                String toSpeakFr = spinner.getSelectedItem().toString();

                                                textToSpeech.speak(toSpeakFr, TextToSpeech.QUEUE_FLUSH, null);
                                                break;
                                            case 1:
                                                textToSpeech.setLanguage(Locale.ENGLISH);
                                                String toSpeakEn = spinner.getSelectedItem().toString();

                                                textToSpeech.speak(toSpeakEn, TextToSpeech.QUEUE_FLUSH, null);
                                                break;
                                        }
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }
                });



            }


        });




    }

    private void controllaLettere(){

        String parola = "";

        for(int i = 0; i != linearRisposta.getChildCount(); i++){
            FrameLayout f = (FrameLayout) linearRisposta.getChildAt(i);
            TextView t = (TextView) f.getChildAt(0);
            char c =  t.getText().toString().charAt(0);
            parola+=c;

        }

        for(int i = 0; i != linearRisposta1.getChildCount(); i++){
            FrameLayout f = (FrameLayout) linearRisposta1.getChildAt(i);
            TextView t = (TextView) f.getChildAt(0);
            char c =  t.getText().toString().charAt(0);
            parola+=c;

        }

        if(parola.equals(corretta)){
            button_aiuto.setVisibility(View.GONE);
            button_avanti.setVisibility(View.VISIBLE);

            esito1.setVisibility(View.VISIBLE);
            esito1.setImageResource(R.drawable.thumbs_up);

            esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

            for(int i = 0; i != linearRisposta.getChildCount(); i++){
                linearRisposta.getChildAt(i).setClickable(false);
            }
            for(int i = 0; i != linearRisposta1.getChildCount(); i++){
                linearRisposta1.getChildAt(i).setClickable(false);
            }

        }else if(!parola.equals(corretta) && linearRisposta.getChildCount() == corretta.length()){
            button_aiuto.setVisibility(View.GONE);
            button_avanti.setVisibility(View.VISIBLE);

            esito1.setVisibility(View.VISIBLE);
            esito1.setImageResource(R.drawable.thumbs_down);

            esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

        }else if(!parola.equals(corretta) && linearRisposta.getChildCount() + linearRisposta1.getChildCount() == corretta.length()){
            button_aiuto.setVisibility(View.GONE);
            button_avanti.setVisibility(View.VISIBLE);

            esito1.setVisibility(View.VISIBLE);
            esito1.setImageResource(R.drawable.thumbs_down);

            esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

        }

    }

    public interface MyCallback {
        void onCallback(ArrayList<Json> arrayJson);
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
                    arrayJson.add(json);

                }
                myCallback.onCallback(arrayJson);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}