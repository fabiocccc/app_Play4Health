package com.example.tesi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.speech.tts.TextToSpeech;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Locale;

public class ActivityAscolta extends AppCompatActivity {

    private LinearLayout linearLayout;
    private JSONArray jsonArray;
    private TextToSpeech textToSpeech;
    private FrameLayout button_aiuto;
    private ImageView help1;
    private AnimationDrawable animationDrawable = null;
    private AnimationDrawable animationDrawable1 = null;
    private AnimationDrawable animationDrawable2 = null;
    private ArrayList<String> parole;
    private String tipo;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ascolta);

        textView = findViewById(R.id.textView);
        button_aiuto = findViewById(R.id.button_aiuto);
        help1 = findViewById(R.id.help1);

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

        if(tipo.equals("calcio")){
            textView.setText("Ascolta");
            String jsonString = read(this, "dati.json");
            try {
                jsonArray = new JSONArray(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            parole = new ArrayList<>();
            try {
                for(int i=0; i!= jsonArray.length(); i++){
                    parole.add(jsonArray.getJSONObject(i).getString("ita"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

            if(tipo.equals("corpo")){
                textView.setText("Parti del corpo");
            } else {
                textView.setText("Salute e benessere");
            }


            String jsonString = read(this, "datisecondo.json");
            try {
                jsonArray = new JSONArray(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            parole = new ArrayList<>();
            try {
                for(int i=0; i!= jsonArray.length(); i++){
                    if(jsonArray.getJSONObject(i).getString("tipo").equals(tipo)){
                        parole.add(jsonArray.getJSONObject(i).getString("ita"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        int i = 0;
        int j = 0;
        while( i!= parole.size() ){
            LinearLayout linear = (LinearLayout) LayoutInflater.from(this).inflate( R.layout.linear_ascolta, null);
            linearLayout.addView(linear, j);
            j++;

            //PRIMA CARD
            View card1 = linear.getChildAt(0);
            TextView textCard1 = linear.getChildAt(0).findViewById(R.id.textCard1);
            textCard1.setText(parole.get(i));
            ImageView imageCard1 = linear.getChildAt(0).findViewById(R.id.imageCard1);

            try {
                for(int k=0; k!= jsonArray.length(); k++){
                    if(parole.get(i).equals(jsonArray.getJSONObject(k).getString("ita")) && jsonArray.getJSONObject(k).getString("img") != ""){
                        byte[] decodedString = Base64.decode(jsonArray.getJSONObject(k).getString("img"), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageCard1.setImageBitmap(decodedByte);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int finalI1 = i;
            card1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(animationDrawable != null) {
                        help1.setVisibility(View.GONE);
                        animationDrawable.stop();
                    }
                    buildCard(parole.get(finalI1));
                }
            });
            i++;

            //SECONDA CARD
            if(i != parole.size()){
                View card2 = linear.getChildAt(1);
                TextView textCard2 = linear.getChildAt(1).findViewById(R.id.textCard2);
                textCard2.setText(parole.get(i));
                ImageView imageCard2 = linear.getChildAt(1).findViewById(R.id.imageCard2);

                try {
                    for(int k=0; k!= jsonArray.length(); k++){
                        if(parole.get(i).equals(jsonArray.getJSONObject(k).getString("ita")) && jsonArray.getJSONObject(k).getString("img") != ""){
                            byte[] decodedString = Base64.decode(jsonArray.getJSONObject(k).getString("img"), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imageCard2.setImageBitmap(decodedByte);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                int finalI = i;
                card2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(animationDrawable != null) {
                            help1.setVisibility(View.GONE);
                            animationDrawable.stop();
                        }
                        buildCard(parole.get(finalI));
                    }
                });

                i++;
            } else {
                linear.getChildAt(1).setVisibility(View.INVISIBLE);
                linear.getChildAt(2).setVisibility(View.INVISIBLE);
                break;
            }

            //TERZA CARD
            if(i != parole.size()){
                View card3 = linear.getChildAt(2);
                TextView textCard3 = linear.getChildAt(2).findViewById(R.id.textCard3);
                textCard3.setText(parole.get(i));
                ImageView imageCard3 = linear.getChildAt(2).findViewById(R.id.imageCard3);

                try {
                    for(int k=0; k!= jsonArray.length(); k++){
                        if(parole.get(i).equals(jsonArray.getJSONObject(k).getString("ita")) && jsonArray.getJSONObject(k).getString("img") != ""){
                            byte[] decodedString = Base64.decode(jsonArray.getJSONObject(k).getString("img"), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imageCard3.setImageBitmap(decodedByte);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                int finalI = i;
                card3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(animationDrawable != null) {
                            help1.setVisibility(View.GONE);
                            animationDrawable.stop();
                        }
                        buildCard(parole.get(finalI));
                    }
                });

                i++;
            } else {
                linear.getChildAt(2).setVisibility(View.INVISIBLE);
                break;
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    private void buildCard(String ita){
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

        try {
            for(int k=0; k!= jsonArray.length(); k++){
                if(ita.equals(jsonArray.getJSONObject(k).getString("ita"))){
                    paroleAlert.add(jsonArray.getJSONObject(k).getString("fra"));
                    paroleAlert.add(jsonArray.getJSONObject(k).getString("eng"));
                    byte[] decodedString = Base64.decode(jsonArray.getJSONObject(k).getString("img"), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageView.setImageBitmap(decodedByte);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
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


    private String read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }
}