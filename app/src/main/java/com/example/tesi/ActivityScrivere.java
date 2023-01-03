package com.example.tesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ActivityScrivere extends AppCompatActivity {

    private ImageView imageView_Scrivere;
    private Button button_Ascolta;
    private Button button_Risp1;
    private Button button_Risp2;
    private Button button_Risp3;
    private Button button_Risp4;
    private JSONArray jsonArray;
    private String corretta;
    private int errori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrivere);

        errori = 0;
        button_Ascolta = findViewById(R.id.button_Ascolta2);
        button_Risp1 = findViewById(R.id.button_Risp1);
        button_Risp2 = findViewById(R.id.button_Risp2);
        button_Risp3 = findViewById(R.id.button_Risp3);
        button_Risp4 = findViewById(R.id.button_Risp4);
        imageView_Scrivere = findViewById(R.id.imageView_Scrivere);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String jsonString = read(this, "dati.json");
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int random = (int)(Math.random() * jsonArray.length());
        //Toast.makeText(this, "nu: "+ random, Toast.LENGTH_SHORT).show();


        try {
            corretta = jsonArray.getJSONObject(random).getString("ita");

            ArrayList<String> risposte = new ArrayList<>();
            risposte.add(jsonArray.getJSONObject(random).getString("ita"));
            risposte.add(jsonArray.getJSONObject(random).getString("sug1"));
            risposte.add(jsonArray.getJSONObject(random).getString("sug2"));
            risposte.add(jsonArray.getJSONObject(random).getString("sug3"));

            int temp = (int) (Math.random() * risposte.size() );
            button_Risp1.setText(risposte.get(temp));
            risposte.remove(temp);
            button_Risp1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (button_Risp1.getText().toString().equals(corretta)) {
                        //Corretto
                        if(errori == 0){
                            //metti il SVOLTO nel JSON

                        }
                    } else {
                        errori++;

                        if(errori == 3){
                            Toast.makeText(getApplicationContext(), "HAI PERSO", Toast.LENGTH_SHORT).show();
                        }

                        button_Risp1.setClickable(false);
                        button_Risp1.setBackgroundColor(Color.BLACK);

                    }

                }
            });

            temp = (int) (Math.random() * risposte.size() );
            button_Risp2.setText(risposte.get(temp));
            risposte.remove(temp);
            button_Risp2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (button_Risp2.getText().toString().equals(corretta)) {
                        //Corretto
                        if(errori == 0){
                            //metti il SVOLTO nel JSON

                        }
                    } else {
                        errori++;

                        if(errori == 3){
                            Toast.makeText(getApplicationContext(), "HAI PERSO", Toast.LENGTH_SHORT).show();
                        }

                        button_Risp2.setClickable(false);
                        button_Risp2.setBackgroundColor(Color.BLACK);

                    }
                }
            });

            temp = (int) (Math.random() * risposte.size() );
            button_Risp3.setText(risposte.get(temp));
            risposte.remove(temp);
            button_Risp3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (button_Risp3.getText().toString().equals(corretta)) {
                        //Corretto
                        if(errori == 0){
                            //metti il SVOLTO nel JSON

                        }
                    } else {
                        errori++;

                        if(errori == 3){
                            Toast.makeText(getApplicationContext(), "HAI PERSO", Toast.LENGTH_SHORT).show();
                        }

                        button_Risp3.setClickable(false);
                        button_Risp3.setBackgroundColor(Color.BLACK);

                    }

                }
            });

            temp = (int) (Math.random() * risposte.size() );
            button_Risp4.setText(risposte.get(temp));
            risposte.remove(temp);
            button_Risp4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (button_Risp4.getText().toString().equals(corretta)) {
                        //Corretto
                        if(errori == 0){
                            //metti il SVOLTO nel JSON


                        }

                        Toast.makeText(getApplicationContext(), "FAI MENO ERRORI", Toast.LENGTH_SHORT).show();
                    } else {
                        errori++;

                        if(errori == 3){
                            Toast.makeText(getApplicationContext(), "HAI PERSO", Toast.LENGTH_SHORT).show();
                        }

                        button_Risp4.setClickable(false);
                        button_Risp4.setBackgroundColor(Color.BLACK);

                    }

                }
            });

            if(jsonArray.getJSONObject(random).getString("img") != ""){
                byte[] decodedString = Base64.decode(jsonArray.getJSONObject(random).getString("img"), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView_Scrivere.setImageBitmap(decodedByte);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


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