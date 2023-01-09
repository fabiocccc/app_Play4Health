package com.example.tesi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.CornerPathEffect;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Home extends AppCompatActivity {

    private FrameLayout button_HomeAscolta;
    private FrameLayout button_HomeParla;
    private FrameLayout button_HomeScrivere;
    private FrameLayout button_HomeScegli;
    private FrameLayout button_HomeCampo;
    private FrameLayout button_HomeCorpo;
    private Button button_HomeCorpoScegli;
    private Button button_HomeCarica;

    private ImageView imageViewTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        button_HomeParla = findViewById(R.id.button_HomeParla);
        button_HomeScrivere = findViewById(R.id.button_HomeScrivere);
        button_HomeAscolta = findViewById(R.id.button_HomeAscolta);
        button_HomeScegli = findViewById(R.id.button_HomeScegli);
        button_HomeCampo = findViewById(R.id.button_HomeCampo);
        button_HomeCorpo = findViewById(R.id.button_HomeCorpo);
        button_HomeCarica = findViewById(R.id.button_HomeCarica);

    }

    @Override
    protected void onStart() {
        super.onStart();

        button_HomeParla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, ActivityParla.class);
                startActivity(intent);
            }
        });

        button_HomeScrivere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setCancelable(true);

                View dialogView = getLayoutInflater().inflate(R.layout.alertdialog_scrivere, null);
                builder.setView(dialogView);
                AlertDialog alert = builder.create();
                alert.show();

                dialogView.findViewById(R.id.button_ScrivereFacile).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Home.this, ActivityScrivere.class);
                        startActivity(intent);
                    }
                });

                dialogView.findViewById(R.id.button_ScrivereDifficile).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Home.this, ActivityScrivereDifficile.class);
                        startActivity(intent);
                    }
                });


            }
        });

        button_HomeScegli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, ActivityScegli.class);
                startActivity(intent);
            }
        });

        button_HomeAscolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, ActivityAscolta.class);
                startActivity(intent);
            }
        });

        button_HomeCampo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, ActivityCampo.class);
                startActivity(intent);
            }
        });

        button_HomeCorpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setCancelable(true);

                View dialogView = getLayoutInflater().inflate(R.layout.alertdialog_corpo, null);
                builder.setView(dialogView);
                AlertDialog alert = builder.create();
                alert.show();

                dialogView.findViewById(R.id.button_AlertCorpo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Home.this, ActivityCorpo.class);
                        startActivity(intent);
                    }
                });

                dialogView.findViewById(R.id.button_AlertViso).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Home.this, ActivityViso.class);
                        startActivity(intent);
                    }
                });

                dialogView.findViewById(R.id.button_AlertMano).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Home.this, ActivityMano.class);
                        startActivity(intent);
                    }
                });

                dialogView.findViewById(R.id.button_AlertCorpoScegli).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Home.this, ActivityCorpoScegli.class);
                        startActivity(intent);
                    }
                });
            }
        });


        button_HomeCarica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, ActivityCarica.class);
                startActivity(intent);
            }
        });


        String jsonString = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.dati);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();

        }

        boolean isFilePresent = isFilePresent(this, "dati.json");
        if(isFilePresent) {
            //Toast.makeText(this,"file esiste", Toast.LENGTH_LONG).show();
            String jsonString1 = read(this, "dati.json");
            JSONArray jsonarray = null;
            try {
                jsonarray = new JSONArray(jsonString1);
                for (int i = 0; i < jsonarray.length(); i++) {

                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    //Toast.makeText(this, jsonobject.toString(), Toast.LENGTH_LONG).show();
                    if(jsonobject.getString("img") != ""){
                        /*byte[] decodedString = Base64.decode(jsonobject.getString("img"), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageViewTest.setImageBitmap(decodedByte);*/
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //do the json parsing here and do the rest of functionality of app
        } else {
            boolean isFileCreated = create(this, "dati.json", jsonString);
            if(isFileCreated) {
                Toast.makeText(this,"file creato", Toast.LENGTH_LONG).show();
                //proceed with storing the first todo  or show ui
            } else {
                //show error or try again.
            }
        }




        /*JSONArray jsonarray = null;
        try {
            jsonarray = new JSONArray(json);
            for (int i = 0; i < jsonarray.length(); i++) {

                JSONObject jsonobject = jsonarray.getJSONObject(i);
                Toast.makeText(this, jsonobject.toString(), Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

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

    public boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }
}