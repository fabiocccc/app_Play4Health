package com.example.tesi.AppPavone;

import static android.content.ContentValues.TAG;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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

import com.example.tesi.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private ArrayList<String> parole;
    private ListViewAdapter adapter;
    private TextToSpeech textToSpeech;
    private String tipo;
    private ArrayList<Json> arrayJson;

    DatabaseReference dr;

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

        arrayJson = new ArrayList<>();
        parole = new ArrayList<>();
        if(tipo.equals("calcio")){
            progressBar.setVisibility(View.GONE);


            readDataJson1(new MyCallback() {
                @Override
                public void onCallback(ArrayList<String> parole, ArrayList<Json> arrayJson) {

                    adapter = new ListViewAdapter(ActivityListaDati.this, parole);
                    listView.setAdapter(adapter);
                    ActivityListaDati.this.findViewById(R.id.listaDati).setEnabled(true);
                    ActivityListaDati.this.findViewById(R.id.button_CaricaDati).setEnabled(true);
                    ActivityListaDati.this.findViewById(R.id.button_indietro).setClickable(true);

                }

            });

        } else if(tipo.equals("corpo")) {

            progressBar.setVisibility(View.GONE);
            listView.setEnabled(false);
            button_CaricaDati.setEnabled(false);
            button_indietro.setClickable(false);

            readDataJson2(new MyCallback() {
                @Override
                public void onCallback(ArrayList<String> parole, ArrayList<Json> arrayJson) {

                    adapter = new ListViewAdapter(ActivityListaDati.this, parole);
                    listView.setAdapter(adapter);
                    ActivityListaDati.this.findViewById(R.id.listaDati).setEnabled(true);
                    ActivityListaDati.this.findViewById(R.id.button_CaricaDati).setEnabled(true);
                    ActivityListaDati.this.findViewById(R.id.button_indietro).setClickable(true);

                }

            });

        }

        else if(tipo.equals("salute")) {

            progressBar.setVisibility(View.GONE);
            listView.setEnabled(false);
            button_CaricaDati.setEnabled(false);
            button_indietro.setClickable(false);

            readDataJson3(new MyCallback() {
                @Override
                public void onCallback(ArrayList<String> parole, ArrayList<Json> arrayJson) {

                    adapter = new ListViewAdapter(ActivityListaDati.this, parole);
                    listView.setAdapter(adapter);
                    ActivityListaDati.this.findViewById(R.id.listaDati).setEnabled(true);
                    ActivityListaDati.this.findViewById(R.id.button_CaricaDati).setEnabled(true);
                    ActivityListaDati.this.findViewById(R.id.button_indietro).setClickable(true);

                }

            });

        }


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

                    String eng = ds.child("eng").getValue(String.class);
                    String fra = ds.child("fra").getValue(String.class);
                    String img = ds.child("img").getValue(String.class);
                    String ita = ds.child("ita").getValue(String.class);
                    String sug1 = ds.child("sug1").getValue(String.class);
                    String sug2 = ds.child("sug2").getValue(String.class);
                    String sug3 = ds.child("sug3").getValue(String.class);
                    Boolean svolto = ds.child("svolto").getValue(Boolean.class);

                    Json json = new Json(ita, fra, eng, sug1, sug2, sug3, img, svolto);
                    parole.add(ita);
                    arrayJson.add(json);

                }
                myCallback.onCallback(parole, arrayJson);
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
                        parole.add(ita);
                        arrayJson.add(json);
                    }

                }

                myCallback.onCallback(parole, arrayJson);

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
                        parole.add(ita);
                        arrayJson.add(json);
                    }

                }

                myCallback.onCallback(parole, arrayJson);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

            for(int k=0; k!= arrayJson.size(); k++){
                if(ita.equals(arrayJson.get(k).getIta())){
                        paroleAlert.add(arrayJson.get(k).getFra());
                        paroleAlert.add(arrayJson.get(k).getEng());
                    byte[] decodedString = Base64.decode(arrayJson.get(k).getImg(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageView.setImageBitmap(decodedByte);
                }
            }


//inserisce una traduzione in più sia di francese che di inglese dopo aver effettuato l'eliminazione la prima volta
          if (paroleAlert.size() == 5) {
              paroleAlert.remove(2);
              paroleAlert.remove(3);
          }

        if(cancella){
            builder.setPositiveButton("Conferma elimina", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                        Boolean trov = false;
                        int j = 0;
                        while (j<arrayJson.size() && !trov){
                            if(arrayJson.get(j).getIta().equals(ita) ){
                                trov = true;
                                arrayJson.remove(j);
                                parole.clear();
                                adapter.notifyDataSetChanged();

                                if(tipo.equals("calcio")){

                                    deleteFromJson1(ita);
                                    Toast.makeText(ActivityListaDati.this, "Eliminato", Toast.LENGTH_LONG).show();

                                } if(tipo.equals("corpo")) {
                                    deleteFromJson2(ita);
                                    Toast.makeText(ActivityListaDati.this, "Eliminato", Toast.LENGTH_LONG).show();

                                }
                                if(tipo.equals("salute")) {
                                    deleteFromJson3(ita);
                                    Toast.makeText(ActivityListaDati.this, "Eliminato", Toast.LENGTH_LONG).show();

                                }



                            }else{
                                j++;
                            }
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

    public void deleteFromJson1(String itaDaEliminare) {
        dr = FirebaseDatabase.getInstance().getReference();

        DatabaseReference rf = dr.child("Json1");

        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                    String ita = ds.child("ita").getValue(String.class);

                    if (itaDaEliminare.equals(ita)) {
                        ds.getRef().removeValue();
                    }

                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void deleteFromJson2(String itaDaEliminare) {
        dr = FirebaseDatabase.getInstance().getReference();

        DatabaseReference rf = dr.child("Json2");

        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                    String ita = ds.child("ita").getValue(String.class);
                    String tipo = ds.child("tipo").getValue(String.class);

                    if(tipo.equals("corpo")) {
                        if (itaDaEliminare.equals(ita)) {
                            ds.getRef().removeValue();
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void deleteFromJson3(String itaDaEliminare) {
        dr = FirebaseDatabase.getInstance().getReference();

        DatabaseReference rf = dr.child("Json2");

        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    Log.d(TAG, "onChildAdded:" + snapshot.getKey());

                    String ita = ds.child("ita").getValue(String.class);
                    String tipo = ds.child("tipo").getValue(String.class);

                    if(tipo.equals("salute")) {
                        if (itaDaEliminare.equals(ita)) {
                            ds.getRef().removeValue();
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}