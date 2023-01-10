package com.example.tesi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.CornerPathEffect;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.Locale;

public class Home extends AppCompatActivity {

    private FrameLayout button_HomeAscolta;
    private FrameLayout button_HomeParla;
    private FrameLayout button_HomeScrivere;
    private FrameLayout button_HomeScegli;
    private FrameLayout button_HomeCampo;
    private FrameLayout button_HomeCorpo;
    private Button button_HomeCarica;
    private TextToSpeech textToSpeech;

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

    public void riproduci(String toSpeak, Locale locale){
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {

                    textToSpeech.setLanguage(locale);
                    textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                }
            }
        });
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

                TextView text_Difficoltà = dialogView.findViewById(R.id.textDifficolta);
                Button button_ScrivereFacile = dialogView.findViewById(R.id.button_ScrivereFacile);
                Button button_ScrivereDifficile = dialogView.findViewById(R.id.button_ScrivereDifficile);
                ImageView ita = dialogView.findViewById(R.id.ita);
                ImageView fra = dialogView.findViewById(R.id.fra);
                ImageView eng = dialogView.findViewById(R.id.eng);

                button_ScrivereFacile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Home.this, ActivityScrivere.class);
                        startActivity(intent);
                    }
                });

                button_ScrivereDifficile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Home.this, ActivityScrivereDifficile.class);
                        startActivity(intent);
                    }
                });

                ita.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button_ScrivereFacile.setText("Facile");
                        button_ScrivereDifficile.setText("Difficile");
                        text_Difficoltà.setText("Difficoltà");
                        riproduci("Difficoltà facile o difficile", Locale.ITALY);
                    }
                });

                fra.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button_ScrivereFacile.setText("Facile");
                        button_ScrivereDifficile.setText("Difficile");
                        text_Difficoltà.setText("Difficulté");
                        riproduci("Difficulté facile ou difficile", Locale.FRANCE);
                    }
                });

                eng.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button_ScrivereFacile.setText("Easy");
                        button_ScrivereDifficile.setText("Hard");
                        text_Difficoltà.setText("Difficulty");
                        riproduci("Easy or hard difficulty", Locale.ENGLISH);
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

                TextView text_Titolo= dialogView.findViewById(R.id.textAlertCorpoTitolo);
                FrameLayout frame_Corpo = dialogView.findViewById(R.id.button_AlertCorpo);
                FrameLayout frame_Viso = dialogView.findViewById(R.id.button_AlertViso);
                FrameLayout frame_Mano = dialogView.findViewById(R.id.button_AlertMano);
                FrameLayout frame_Scegli = dialogView.findViewById(R.id.button_AlertCorpoScegli);
                TextView text_Corpo = dialogView.findViewById(R.id.textAlertCorpoCorpo);
                TextView text_Viso = dialogView.findViewById(R.id.textAlertCorpoViso);
                TextView text_Mano = dialogView.findViewById(R.id.textAlertCorpoMano);
                TextView text_CorpoScegli  = dialogView.findViewById(R.id.textAlertCorpoScegli);
                ImageView ita = dialogView.findViewById(R.id.ita);
                ImageView fra = dialogView.findViewById(R.id.fra);
                ImageView eng = dialogView.findViewById(R.id.eng);

                ita.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        text_Titolo.setText("PARTI DEL CORPO");
                        text_Corpo.setText("Corpo");
                        text_Viso.setText("Viso");
                        text_Mano.setText("Mano");
                        text_CorpoScegli.setText("Scegli");
                        riproduci("Parti del corpo", Locale.ITALY);

                    }
                });

                fra.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        text_Titolo.setText("PARTIES DU CORPS");
                        text_Corpo.setText("Corps");
                        text_Viso.setText("Visage");
                        text_Mano.setText("Main");
                        text_CorpoScegli.setText("Choisir");
                        riproduci("Parties du corps", Locale.FRANCE);

                    }
                });

                eng.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        text_Titolo.setText("BODY PARTS");
                        text_Corpo.setText("Body");
                        text_Viso.setText("Face");
                        text_Mano.setText("Hand");
                        text_CorpoScegli.setText("Choose");
                        riproduci("Body parts", Locale.ENGLISH);
                    }
                });

                frame_Corpo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Home.this, ActivityCorpo.class);
                        startActivity(intent);
                    }
                });

                frame_Viso.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Home.this, ActivityViso.class);
                        startActivity(intent);
                    }
                });

                frame_Mano.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Home.this, ActivityMano.class);
                        startActivity(intent);
                    }
                });

                frame_Scegli.setOnClickListener(new View.OnClickListener() {
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