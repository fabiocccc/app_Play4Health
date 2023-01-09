package com.example.tesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ActivityCarica extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    private EditText edit_Ita;
    private EditText edit_Fra;
    private EditText edit_Eng;
    private EditText edit_Sug1;
    private EditText edit_Sug2;
    private EditText edit_Sug3;
    private Button button_Foto;
    private Button button_Salva;
    private ImageView image_Carica;
    private String image_CaricaBase64;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carica);

        edit_Ita = findViewById(R.id.edit_Ita);
        edit_Fra = findViewById(R.id.edit_Fra);
        edit_Eng = findViewById(R.id.edit_Eng);
        edit_Sug1 = findViewById(R.id.edit_Sug1);
        edit_Sug2 = findViewById(R.id.edit_Sug2);
        edit_Sug3 = findViewById(R.id.edit_Sug3);
        button_Foto = findViewById(R.id.button_Foto);
        button_Salva = findViewById(R.id.button_Salva);
        image_Carica= findViewById(R.id.image_Carica);
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

        button_Foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Seleziona immagine"), PICK_IMAGE);
            }
        });

        button_Salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("ita", edit_Ita.getText().toString());
                    jsonObject.put("fra", edit_Fra.getText().toString());
                    jsonObject.put("eng", edit_Eng.getText().toString());
                    jsonObject.put("sug1", edit_Sug1.getText().toString());
                    jsonObject.put("sug2", edit_Sug2.getText().toString());
                    jsonObject.put("sug3", edit_Sug3.getText().toString());
                    jsonObject.put("img", image_CaricaBase64);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArray.put(jsonObject);

                //TextView textView2 = findViewById(R.id.textView2);
                //textView2.setText(jsonArray.toString());
                create(getApplicationContext(), "dati.json", jsonArray.toString());


            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {

            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                // update the preview image in the layout
                image_Carica.setImageURI(selectedImageUri);


                Bitmap photo = null;
                try {
                    photo = MediaStore.Images.Media.getBitmap(getContentResolver() , selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 80, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                image_CaricaBase64 = Base64.encodeToString(b, Base64.NO_WRAP);
                //TextView textView2 = findViewById(R.id.textView2);
                //textView2.setText(image_CaricaBase64);
                //Log.v("myTag", image_CaricaBase64);

            }

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

    private boolean create(Context context, String fileName, String jsonString){
        String FILENAME = "dati.json";
        try {
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }

    }
}