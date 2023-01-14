package com.example.tesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.commons.net.ftp.FTPClient;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
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
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Locale;

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
    private TextToSpeech textToSpeech;
    private ProgressBar progressBar;

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
        progressBar = findViewById(R.id.progress_circular);

        findViewById(R.id.button_indietro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        image_CaricaBase64 = "";
        edit_Ita.setText("");
        edit_Fra.setText("");
        edit_Eng.setText("");
        edit_Sug1.setText("");
        edit_Sug2.setText("");
        edit_Sug3.setText("");

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

        if(connected){
            progressBar.setVisibility(View.VISIBLE);
            ActivityCarica.this.findViewById(R.id.edit_Ita).setEnabled(false);
            ActivityCarica.this.findViewById(R.id.edit_Fra).setEnabled(false);
            ActivityCarica.this.findViewById(R.id.edit_Eng).setEnabled(false);
            ActivityCarica.this.findViewById(R.id.edit_Sug1).setEnabled(false);
            ActivityCarica.this.findViewById(R.id.edit_Sug2).setEnabled(false);
            ActivityCarica.this.findViewById(R.id.edit_Sug3).setEnabled(false);
            ActivityCarica.this.findViewById(R.id.button_Foto).setEnabled(false);
            ActivityCarica.this.findViewById(R.id.image_Carica).setEnabled(false);
            ActivityCarica.this.findViewById(R.id.button_Salva).setEnabled(false);
            ActivityCarica.this.findViewById(R.id.button_indietro).setClickable(false);
            new ActivityCarica.DownloadFile().execute();

        } else {
            Toast.makeText(getApplicationContext(), "Non sei connesso a Internet", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

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



                if(edit_Ita.getText().toString().equals("") || edit_Fra.getText().toString().equals("") || edit_Eng.getText().toString().equals("")
                        || edit_Sug1.getText().toString().equals("") || edit_Sug2.getText().toString().equals("") || edit_Sug3.getText().toString().equals("")){

                    Toast.makeText(getApplicationContext(), "Inserisci tutti i dati!", Toast.LENGTH_SHORT).show();

                } else {
                    if(!image_CaricaBase64.equals("")){

                        JSONObject jsonObject = new JSONObject();
                        try {

                            Boolean trov = false;
                            for (int i=0; i<jsonArray.length(); i++){
                                if(jsonArray.getJSONObject(i).get("ita").equals(edit_Ita.getText().toString())
                                        || jsonArray.getJSONObject(i).get("fra").equals(edit_Fra.getText().toString())
                                        || jsonArray.getJSONObject(i).get("eng").equals(edit_Eng.getText().toString()) ){

                                    Toast.makeText(getApplicationContext(), "<" + edit_Ita.getText().toString() + "> già presente! Impossibile inserire.", Toast.LENGTH_SHORT).show();
                                    trov = true;
                                    break;
                                }
                            }

                            if(!trov){

                                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                                boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

                                if(connected){
                                    progressBar.setVisibility(View.VISIBLE);
                                    ActivityCarica.this.findViewById(R.id.edit_Ita).setEnabled(false);
                                    ActivityCarica.this.findViewById(R.id.edit_Fra).setEnabled(false);
                                    ActivityCarica.this.findViewById(R.id.edit_Eng).setEnabled(false);
                                    ActivityCarica.this.findViewById(R.id.edit_Sug1).setEnabled(false);
                                    ActivityCarica.this.findViewById(R.id.edit_Sug2).setEnabled(false);
                                    ActivityCarica.this.findViewById(R.id.edit_Sug3).setEnabled(false);
                                    ActivityCarica.this.findViewById(R.id.button_Foto).setEnabled(false);
                                    ActivityCarica.this.findViewById(R.id.image_Carica).setEnabled(false);
                                    ActivityCarica.this.findViewById(R.id.button_Salva).setEnabled(false);
                                    ActivityCarica.this.findViewById(R.id.button_indietro).setClickable(false);

                                    jsonObject.put("ita", edit_Ita.getText().toString());
                                    jsonObject.put("fra", edit_Fra.getText().toString());
                                    jsonObject.put("eng", edit_Eng.getText().toString());
                                    jsonObject.put("sug1", edit_Sug1.getText().toString());
                                    jsonObject.put("sug2", edit_Sug2.getText().toString());
                                    jsonObject.put("sug3", edit_Sug3.getText().toString());
                                    jsonObject.put("img", image_CaricaBase64);

                                    jsonArray.put(jsonObject);
                                    create(getApplicationContext(), "dati.json", jsonArray.toString());
                                    new UploadFile().execute();


                                } else {
                                    Toast.makeText(getApplicationContext(), "Non sei connesso a Internet", Toast.LENGTH_LONG).show();
                                }

                            }

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Inserisci l'immagine!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }



    private void buildCard(String ita){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCarica.this);
        builder.setCancelable(true);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_card, null);
        Spinner spinner = dialogView.findViewById(R.id.spinnerCard);
        ImageView imageView = dialogView.findViewById(R.id.imageCard);

        ArrayList<String> paroleAlert = new ArrayList<>();
        paroleAlert.add(ita);

        builder.setPositiveButton("Nuova Parola", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                image_CaricaBase64 = "";
                edit_Ita.setText("");
                edit_Fra.setText("");
                edit_Eng.setText("");
                edit_Sug1.setText("");
                edit_Sug2.setText("");
                edit_Sug3.setText("");
                image_Carica.setImageURI(null);

            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                image_CaricaBase64 = "";
                edit_Ita.setText("");
                edit_Fra.setText("");
                edit_Eng.setText("");
                edit_Sug1.setText("");
                edit_Sug2.setText("");
                edit_Sug3.setText("");
                image_Carica.setImageURI(null);
            }
        });



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

        builder.setView(dialogView);
        AlertDialog alert11 = builder.create();
        alert11.show();



        Toast.makeText(getApplicationContext(), "Dati caricati con successo", Toast.LENGTH_LONG).show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {

            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                // update the preview image in the layout
                image_Carica.setImageURI(selectedImageUri);
                image_Carica.setVisibility(View.VISIBLE);


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

    class UploadFile extends AsyncTask<Void, Void, Void> {
        String TAG = "MAIN_ACTIVITY";

        @Override
        protected Void doInBackground(Void... voids) {
/*
            FTPClient ftpClient = new FTPClient();

            try {
                ftpClient.connect("ftp.prenotazionetamponi.altervista.org", 21);
                ftpClient.login( "prenotazionetamponi","wFhppBsmP588");
                ftpClient.enterLocalPassiveMode();

                String dirPath = "";

                InputStream inputStream = getApplicationContext().openFileInput("dati.json");
                ftpClient.storeFile(dirPath + "/dati.json", inputStream);
                inputStream.close();

                //END OF FILE UPLOADING
                ActivityCarica.this.runOnUiThread(new Runnable() {
                    public void run() {
                        buildCard(edit_Ita.getText().toString());
                    }
                });

                ftpClient.logout();
                ftpClient.disconnect();

                ActivityCarica.this.runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });




            } catch (NetworkOnMainThreadException e) {
                ActivityCarica.this.runOnUiThread(new Runnable() {
                    public void run() {
                        finish();
                        Toast.makeText(ActivityCarica.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (SocketException e) {
                ActivityCarica.this.runOnUiThread(new Runnable() {
                    public void run() {
                        finish();
                        Toast.makeText(ActivityCarica.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            } catch (FileNotFoundException e) {
                ActivityCarica.this.runOnUiThread(new Runnable() {
                    public void run() {
                        finish();
                        Toast.makeText(ActivityCarica.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (IOException e) {
                ActivityCarica.this.runOnUiThread(new Runnable() {
                    public void run() {
                        finish();
                        Toast.makeText(ActivityCarica.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }*/

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference jsonFirebase = storageRef.child("dati.json");

            try {
                FileInputStream fis = ActivityCarica.this.openFileInput("dati.json");
                jsonFirebase.putStream(fis).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ActivityCarica.this.runOnUiThread(new Runnable() {
                            public void run() {

                                ActivityCarica.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        ActivityCarica.this.findViewById(R.id.progress_circular).setVisibility(View.GONE);
                                        buildCard(edit_Ita.getText().toString());

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

            ActivityCarica.this.runOnUiThread(new Runnable() {
                public void run() {
                    ActivityCarica.this.findViewById(R.id.edit_Ita).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.edit_Fra).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.edit_Eng).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.edit_Sug1).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.edit_Sug2).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.edit_Sug3).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.button_Foto).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.image_Carica).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.button_Salva).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.button_indietro).setClickable(true);
                }
            });

            return null;

        }
    }

    class DownloadFile extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
/*
            FTPClient ftpClient = new FTPClient();


            try {
                ftpClient.connect("ftp.prenotazionetamponi.altervista.org", 21);
                ftpClient.login( "prenotazionetamponi","wFhppBsmP588");
                ftpClient.enterLocalPassiveMode();

                FileOutputStream fos = openFileOutput("dati.json",Context.MODE_PRIVATE);
                OutputStream outputStream = null;
                boolean success = false;
                try {
                    outputStream = new BufferedOutputStream(fos);
                    success = ftpClient.retrieveFile("dati.json", outputStream);
                } finally {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                }

                ftpClient.logout();
                ftpClient.disconnect();

                //Sincronizza dati in locale
                String jsonString = read(ActivityCarica.this, "dati.json");
                try {
                    jsonArray = new JSONArray(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ActivityCarica.this.runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });

            } catch (NetworkOnMainThreadException e) {
                ActivityCarica.this.runOnUiThread(new Runnable() {
                    public void run() {
                        finish();
                        Toast.makeText(ActivityCarica.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            } catch (SocketException e) {
                ActivityCarica.this.runOnUiThread(new Runnable() {
                    public void run() {
                        finish();
                        Toast.makeText(ActivityCarica.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            } catch (FileNotFoundException e) {
                ActivityCarica.this.runOnUiThread(new Runnable() {
                    public void run() {
                        finish();
                        Toast.makeText(ActivityCarica.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            } catch (IOException e) {
                ActivityCarica.this.runOnUiThread(new Runnable() {
                    public void run() {
                        finish();
                        Toast.makeText(ActivityCarica.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }*/

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference jsonFirebase = storageRef.child("dati.json");

            String path = getFilesDir().getAbsolutePath() + "/dati.json";
            File file = new File(path);
            jsonFirebase.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    ActivityCarica.this.runOnUiThread(new Runnable() {
                        public void run() {

                            //Sincronizza dati in locale
                            String jsonString = read(ActivityCarica.this, "dati.json");
                            try {
                                jsonArray = new JSONArray(jsonString);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ActivityCarica.this, "Dati aggiornati con successo.", Toast.LENGTH_LONG).show();

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            ActivityCarica.this.runOnUiThread(new Runnable() {
                public void run() {
                    ActivityCarica.this.findViewById(R.id.edit_Ita).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.edit_Fra).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.edit_Eng).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.edit_Sug1).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.edit_Sug2).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.edit_Sug3).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.button_Foto).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.image_Carica).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.button_Salva).setEnabled(true);
                    ActivityCarica.this.findViewById(R.id.button_indietro).setClickable(true);
                }
            });


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