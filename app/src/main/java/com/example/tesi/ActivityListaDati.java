package com.example.tesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

public class ActivityListaDati extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button button_CaricaDati;
    private ListView listView;
    private FrameLayout button_indietro;
    private JSONArray jsonArray;
    private ArrayList<String> parole;
    private ListViewAdapter adapter;
    private TextToSpeech textToSpeech;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_dati);

        listView = findViewById(R.id.listaDati);
        progressBar = findViewById(R.id.progress_circular);
        button_CaricaDati = findViewById(R.id.button_CaricaDati);
        button_indietro = findViewById(R.id.button_indietro);

        button_indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(getIntent().getStringExtra("tipo").equals("calcio")){
            tipo = "calcio";
        } else if(getIntent().getStringExtra("tipo").equals("corpo")) {
            tipo = "corpo";
        } else if(getIntent().getStringExtra("tipo").equals("salute")) {
            tipo = "salute";
        }

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

        if(connected){

        } else {
            Toast.makeText(getApplicationContext(), "Non sei connesso a Internet", Toast.LENGTH_LONG).show();
            finish();
        }

        if(tipo.equals("calcio")){
            progressBar.setVisibility(View.VISIBLE);
            listView.setEnabled(false);
            button_CaricaDati.setEnabled(false);
            button_indietro.setClickable(false);
            new ActivityListaDati.DownloadFile().execute();

        } else {

            progressBar.setVisibility(View.VISIBLE);
            listView.setEnabled(false);
            button_CaricaDati.setEnabled(false);
            button_indietro.setClickable(false);
            new ActivityListaDati.DownloadFileSecondo().execute();

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        button_CaricaDati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityListaDati.this, ActivityCarica.class);
                intent.putExtra("tipo",tipo);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                buildCard(parole.get(i), false);
            }
        });

    }


    class DownloadFile extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference jsonFirebase = storageRef.child("dati.json");

            String path = getFilesDir().getAbsolutePath() + "/dati.json";
            File file = new File(path);
            jsonFirebase.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    ActivityListaDati.this.runOnUiThread(new Runnable() {
                        public void run() {

                            //Sincronizza dati in locale
                            String jsonString = read(ActivityListaDati.this, "dati.json");
                            try {
                                jsonArray = new JSONArray(jsonString);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            progressBar.setVisibility(View.GONE);

                            parole = new ArrayList<>();
                            try {
                                for(int i=0; i!= jsonArray.length(); i++){

                                    parole.add(jsonArray.getJSONObject(i).getString("ita"));

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            adapter = new ListViewAdapter(ActivityListaDati.this, parole);
                            listView.setAdapter(adapter);
                            ActivityListaDati.this.findViewById(R.id.listaDati).setEnabled(true);
                            ActivityListaDati.this.findViewById(R.id.button_CaricaDati).setEnabled(true);
                            ActivityListaDati.this.findViewById(R.id.button_indietro).setClickable(true);

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();

                    ActivityListaDati.this.runOnUiThread(new Runnable() {
                        public void run() {
                            ActivityListaDati.this.findViewById(R.id.listaDati).setEnabled(true);
                            ActivityListaDati.this.findViewById(R.id.button_indietro).setClickable(true);
                        }
                    });
                }
            });

            return null;
        }
    }

    class DownloadFileSecondo extends AsyncTask<Void, Void, Void> {

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
                    ActivityListaDati.this.runOnUiThread(new Runnable() {
                        public void run() {

                            //Sincronizza dati in locale
                            String jsonString = read(ActivityListaDati.this, "datisecondo.json");
                            try {
                                jsonArray = new JSONArray(jsonString);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            progressBar.setVisibility(View.GONE);

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

                            adapter = new ListViewAdapter(ActivityListaDati.this, parole);
                            listView.setAdapter(adapter);
                            ActivityListaDati.this.findViewById(R.id.listaDati).setEnabled(true);
                            ActivityListaDati.this.findViewById(R.id.button_CaricaDati).setEnabled(true);
                            ActivityListaDati.this.findViewById(R.id.button_indietro).setClickable(true);

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();

                    ActivityListaDati.this.runOnUiThread(new Runnable() {
                        public void run() {
                            ActivityListaDati.this.findViewById(R.id.listaDati).setEnabled(true);
                            ActivityListaDati.this.findViewById(R.id.button_indietro).setClickable(true);
                        }
                    });
                }
            });

            return null;
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

    public class ListViewAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<String> list;
        private Boolean doppio;

        public ListViewAdapter(Context context, ArrayList<String> list) {
            this.context = context;
            this.list = list;
            doppio = false;
        }


        @Override
        public int getCount() {
            return list != null ? list.size() : 0;
        }

        @Override
        public String getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View rootView = LayoutInflater.from(context)
                    .inflate(R.layout.spinner_gestione, viewGroup, false);

            TextView textView = (TextView) rootView.findViewById(R.id.textView_SB);
            String text = getItem(i);
            textView.setTextColor(Color.parseColor("#000000"));

            textView.setText(text);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView_SB);

            imageView.setImageResource(R.drawable.ic_baseline_cancel_presentation_24);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    buildCard(parole.get(i), true);
                }
            });

            return rootView;
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

    class UploadFile extends AsyncTask<Void, Void, Void> {
        String TAG = "MAIN_ACTIVITY";

        @Override
        protected Void doInBackground(Void... voids) {

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference jsonFirebase = storageRef.child("dati.json");

            try {
                FileInputStream fis = ActivityListaDati.this.openFileInput("dati.json");
                jsonFirebase.putStream(fis).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ActivityListaDati.this.runOnUiThread(new Runnable() {
                            public void run() {

                                ActivityListaDati.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        ActivityListaDati.this.findViewById(R.id.progress_circular).setVisibility(View.GONE);
                                        Toast.makeText(ActivityListaDati.this, "Eliminato", Toast.LENGTH_LONG).show();
                                        ActivityListaDati.this.findViewById(R.id.listaDati).setEnabled(true);
                                        ActivityListaDati.this.findViewById(R.id.button_CaricaDati).setEnabled(true);

                                        ActivityListaDati.this.findViewById(R.id.button_indietro).setClickable(true);
                                        adapter.notifyDataSetChanged();


                                    }
                                });

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            ActivityListaDati.this.runOnUiThread(new Runnable() {
                public void run() {
                    ActivityListaDati.this.findViewById(R.id.listaDati).setEnabled(true);
                    ActivityListaDati.this.findViewById(R.id.button_CaricaDati).setEnabled(true);

                    ActivityListaDati.this.findViewById(R.id.button_indietro).setClickable(true);
                }
            });

            return null;

        }
    }

    class UploadFileSecondo extends AsyncTask<Void, Void, Void> {
        String TAG = "MAIN_ACTIVITY";

        @Override
        protected Void doInBackground(Void... voids) {

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference jsonFirebase = storageRef.child("datisecondo.json");

            try {
                FileInputStream fis = ActivityListaDati.this.openFileInput("datisecondo.json");
                jsonFirebase.putStream(fis).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ActivityListaDati.this.runOnUiThread(new Runnable() {
                            public void run() {

                                ActivityListaDati.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        ActivityListaDati.this.findViewById(R.id.progress_circular).setVisibility(View.GONE);
                                        Toast.makeText(ActivityListaDati.this, "Eliminato", Toast.LENGTH_LONG).show();
                                        ActivityListaDati.this.findViewById(R.id.listaDati).setEnabled(true);
                                        ActivityListaDati.this.findViewById(R.id.button_CaricaDati).setEnabled(true);

                                        ActivityListaDati.this.findViewById(R.id.button_indietro).setClickable(true);
                                        adapter.notifyDataSetChanged();


                                    }
                                });

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            ActivityListaDati.this.runOnUiThread(new Runnable() {
                public void run() {
                    ActivityListaDati.this.findViewById(R.id.listaDati).setEnabled(true);
                    ActivityListaDati.this.findViewById(R.id.button_CaricaDati).setEnabled(true);

                    ActivityListaDati.this.findViewById(R.id.button_indietro).setClickable(true);
                }
            });

            return null;

        }
    }

    private void buildCard(String ita, Boolean cancella){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityListaDati.this);
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

        if(cancella){
            builder.setPositiveButton("Conferma elimina", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        Boolean trov = false;
                        int j = 0;
                        while (j<jsonArray.length() && !trov){
                            if(jsonArray.getJSONObject(j).get("ita").equals(ita) ){
                                trov = true;
                                parole.remove(ita);
                                jsonArray.remove(j);

                                if(tipo.equals("calcio")){
                                    create(getApplicationContext(), "dati.json", jsonArray.toString());
                                    progressBar.setVisibility(View.VISIBLE);
                                    listView.setEnabled(false);
                                    button_CaricaDati.setEnabled(false);
                                    ActivityListaDati.this.findViewById(R.id.button_indietro).setClickable(false);
                                    new ActivityListaDati.UploadFile().execute();
                                } else {
                                    create(getApplicationContext(), "datisecondo.json", jsonArray.toString());
                                    progressBar.setVisibility(View.VISIBLE);
                                    listView.setEnabled(false);
                                    button_CaricaDati.setEnabled(false);
                                    ActivityListaDati.this.findViewById(R.id.button_indietro).setClickable(false);
                                    new ActivityListaDati.UploadFileSecondo().execute();
                                }



                            }else{
                                j++;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                            switch (i){
                                case 0:

                                    textToSpeech.setLanguage(Locale.ITALIAN);
                                    String toSpeakIt = spinner.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakIt, TextToSpeech.QUEUE_FLUSH, null);
                                    break;
                                case 1:

                                    textToSpeech.setLanguage(Locale.FRANCE);
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

            }
        });
        builder.setView(dialogView);
        AlertDialog alert11 = builder.create();
        alert11.show();


    }

}