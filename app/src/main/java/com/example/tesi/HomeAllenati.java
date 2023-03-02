package com.example.tesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

public class HomeAllenati extends AppCompatActivity {

    private FrameLayout button_corpo;
    private FrameLayout button_allenati;
    private FrameLayout button_salute;
    private FrameLayout button_medico;
    private FrameLayout button_parla;
    private FrameLayout button_indietro;
    private FrameLayout button_Aggiorna;
    private ProgressBar progressBar;
    private SharedPreferences shared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_allenati);

        button_corpo = findViewById(R.id.button_AllenatiCorpo);
        button_allenati = findViewById(R.id.button_AllenatiAllenamento);
        button_salute = findViewById(R.id.button_AllenatiSalute);
        button_medico = findViewById(R.id.button_AllenatiMedico);
        button_parla = findViewById(R.id.button_AllenatiParla);
        button_indietro = findViewById(R.id.button_indietro);
        button_Aggiorna = findViewById(R.id.button_HomeAggiorna);
        progressBar = findViewById(R.id.progress_circular);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

        if(connected){
            progressBar.setVisibility(View.VISIBLE);
            button_corpo.setEnabled(false);
            button_allenati.setEnabled(false);
            button_salute.setEnabled(false);
            button_medico.setEnabled(false);
            button_parla.setEnabled(false);
            button_Aggiorna.setEnabled(false);
            button_indietro.setEnabled(false);
            new HomeAllenati.DownloadFile().execute();

        } else {
            //Toast.makeText(getApplicationContext(), "Non sei connesso a Internet", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        button_Aggiorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

                if(connected){
                    progressBar.setVisibility(View.VISIBLE);
                    button_corpo.setEnabled(false);
                    button_allenati.setEnabled(false);
                    button_salute.setEnabled(false);
                    button_medico.setEnabled(false);
                    button_parla.setEnabled(false);
                    button_Aggiorna.setEnabled(false);
                    button_indietro.setEnabled(false);
                    new HomeAllenati.DownloadFile().execute();

                } else {
                    Toast.makeText(getApplicationContext(), "Non sei connesso a Internet", Toast.LENGTH_LONG).show();
                }

            }
        });


        button_indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button_corpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aggiornaStelle();
                Intent intent = new Intent(HomeAllenati.this, ActivityAscolta.class);
                intent.putExtra("tipo", "corpo");
                startActivity(intent);
            }
        });

        button_allenati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

                if(connected){
                    aggiornaStelle();
                    Intent intent = new Intent(HomeAllenati.this, ActivityGalleriaVideo.class);
                    intent.putExtra("tipo", "gesti");
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Non sei connesso a Internet", Toast.LENGTH_LONG).show();
                }

            }
        });

        button_salute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aggiornaStelle();
                Intent intent = new Intent(HomeAllenati.this, ActivityAscolta.class);
                intent.putExtra("tipo", "salute");
                startActivity(intent);
            }
        });

        button_medico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aggiornaStelle();
                Intent intent = new Intent(getApplicationContext(), ActivityMedico.class);
                startActivity(intent);
            }
        });

        button_parla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aggiornaStelle();
                Intent intent = new Intent(getApplicationContext(), ActivityDialogo.class);
                startActivity(intent);
            }
        });

        String jsonString = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.datisecondo);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();

        }

        boolean isFilePresent = isFilePresent(this, "datisecondo.json");
        if(isFilePresent) {
            //Toast.makeText(this,"file esiste", Toast.LENGTH_LONG).show();
            String jsonString1 = read(this, "dati.json");
            JSONArray jsonarray = null;
            try {
                jsonarray = new JSONArray(jsonString1);
                for (int i = 0; i < jsonarray.length(); i++) {

                    JSONObject jsonobject = jsonarray.getJSONObject(i);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //do the json parsing here and do the rest of functionality of app
        } else {
            boolean isFileCreated = create(this, "datisecondo.json", jsonString);
            if(isFileCreated) {
                //Toast.makeText(this,"file creato", Toast.LENGTH_LONG).show();
                //proceed with storing the first todo  or show ui
            } else {
                //show error or try again.
            }
        }
    }

    public boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
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

    private void aggiornaStelle(){

        shared =  getSharedPreferences("stelle", MODE_PRIVATE);

        if(shared.getString("stelle2", "").equals("")){
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("stelle2", "1");
            editor.commit();

        } else if(shared.getString("stelle2", "").equals("1")){
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("stelle2", "2");
            editor.commit();

        } else if(shared.getString("stelle2", "").equals("2")){
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("stelle2", "3");
            editor.commit();

        }

    }

    class DownloadFile extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference jsonFirebase = storageRef.child("datisecondo.json");

            String path = getFilesDir().getAbsolutePath() + "/datisecondo.json";
            File file = new File(path);
            jsonFirebase.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    HomeAllenati.this.runOnUiThread(new Runnable() {
                        public void run() {
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(HomeAllenati.this, "Dati aggiornati con successo.", Toast.LENGTH_LONG).show();
                            HomeAllenati.this.findViewById(R.id.button_AllenatiCorpo).setEnabled(true);
                            HomeAllenati.this.findViewById(R.id.button_AllenatiSalute).setEnabled(true);
                            HomeAllenati.this.findViewById(R.id.button_AllenatiAllenamento).setEnabled(true);
                            HomeAllenati.this.findViewById(R.id.button_AllenatiMedico).setEnabled(true);
                            HomeAllenati.this.findViewById(R.id.button_AllenatiParla).setEnabled(true);
                            HomeAllenati.this.findViewById(R.id.button_HomeAggiorna).setEnabled(true);
                            HomeAllenati.this.findViewById(R.id.button_indietro).setEnabled(true);
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    HomeAllenati.this.runOnUiThread(new Runnable() {
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(HomeAllenati.this, "Aggiornamento non riuscito.", Toast.LENGTH_LONG).show();
                            HomeAllenati.this.findViewById(R.id.button_AllenatiCorpo).setEnabled(true);
                            HomeAllenati.this.findViewById(R.id.button_AllenatiSalute).setEnabled(true);
                            HomeAllenati.this.findViewById(R.id.button_AllenatiAllenamento).setEnabled(true);
                            HomeAllenati.this.findViewById(R.id.button_AllenatiMedico).setEnabled(true);
                            HomeAllenati.this.findViewById(R.id.button_AllenatiParla).setEnabled(true);
                            HomeAllenati.this.findViewById(R.id.button_HomeAggiorna).setEnabled(true);
                            HomeAllenati.this.findViewById(R.id.button_indietro).setEnabled(true);
                        }
                    });
                }
            });


            return null;

        }
    }
}