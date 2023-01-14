package com.example.tesi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.BitSet;

public class ActivityCaricaVideo extends AppCompatActivity {

    private static final int PICK_VIDEO = 200;
    private Button button_Salva;
    private Button button_ScegliVideo;
    private VideoView videoView;
    private Uri videoUri;
    private EditText edit_NomeVideo;
    private ProgressBar progressBar;
    private TextInputLayout textInputLayoutNomeVideo;
    private TextInputLayout textInputLayoutIta;
    private TextInputLayout textInputLayoutFra;
    private TextInputLayout textInputLayoutEng;
    private Spinner spinner;
    private EditText edit_Ita;
    private EditText edit_Fra;
    private EditText edit_Eng;

    private int tipo;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        storage = FirebaseStorage.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carica_video);

        button_Salva = findViewById(R.id.button_Salva);
        button_ScegliVideo = findViewById(R.id.button_ScegliVideo);
        videoView = findViewById(R.id.videoView);

        textInputLayoutNomeVideo = findViewById(R.id.textInputLayoutNomeVideo);
        textInputLayoutIta = findViewById(R.id.textInputLayoutIta);
        textInputLayoutFra = findViewById(R.id.textInputLayoutFra);
        textInputLayoutEng = findViewById(R.id.textInputLayoutEng);

        edit_NomeVideo = findViewById(R.id.edit_NomeVideo);
        edit_Ita = findViewById(R.id.edit_Ita);
        edit_Fra = findViewById(R.id.edit_Fra);
        edit_Eng = findViewById(R.id.edit_Eng);

        progressBar = findViewById(R.id.progress_circular);
        spinner = findViewById(R.id.spinner_CaricaVideo);

        tipo = 0;

        findViewById(R.id.button_indietro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);

        if(connected){


        } else {
            Toast.makeText(getApplicationContext(), "Non sei connesso a Internet", Toast.LENGTH_LONG).show();
            finish();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        ArrayList<String> parole = new ArrayList<>();
        parole.add("Tipo di video");
        parole.add("Commento azione/partita");
        parole.add("Gesti");
        SpinnerAdapterVideo adapter = new SpinnerAdapterVideo(this, parole);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){

                } else if(i == 1){
                    //Commento
                    tipo = 1;
                    textInputLayoutIta.setVisibility(View.GONE);
                    textInputLayoutFra.setVisibility(View.GONE);
                    textInputLayoutEng.setVisibility(View.GONE);
                    textInputLayoutNomeVideo.setVisibility(View.VISIBLE);

                } else if(i == 2){
                    //Gesto
                    tipo = 2;
                    //textInputLayoutNomeVideo.setVisibility(View.GONE);
                    textInputLayoutIta.setVisibility(View.VISIBLE);
                    textInputLayoutFra.setVisibility(View.VISIBLE);
                    textInputLayoutEng.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button_ScegliVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("video/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "choose App"), PICK_VIDEO);

            }
        });

        button_Salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tipo == 0){
                    Toast.makeText(getApplicationContext(), "Seleziona il tipo di video!", Toast.LENGTH_LONG).show();

                } else if(tipo == 1){
                    //Commento

                    if(!edit_NomeVideo.getText().toString().equals("")) {
                        storageReference = storage.getReference();
                        String filename = edit_NomeVideo.getText().toString(); //prendi da editext
                        StorageReference videoRef = storageReference.child("/videos/commenti/" + filename);

                        if (videoUri != null) {
                            UploadTask uploadTask = videoRef.putFile(videoUri);
                            progressBar.setVisibility(View.VISIBLE);
                            button_Salva.setEnabled(false);
                            textInputLayoutNomeVideo.setEnabled(false);
                            button_ScegliVideo.setEnabled(false);
                            button_Salva.setEnabled(false);
                            ActivityCaricaVideo.this.findViewById(R.id.button_indietro).setClickable(false);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Caricamento fallito!", Toast.LENGTH_LONG).show();
                                    button_Salva.setEnabled(true);
                                    textInputLayoutNomeVideo.setEnabled(true);
                                    button_ScegliVideo.setEnabled(true);
                                    button_Salva.setEnabled(true);
                                    ActivityCaricaVideo.this.findViewById(R.id.button_indietro).setClickable(true);
                                    edit_NomeVideo.setText("");
                                    spinner.setSelection(0);
                                    videoView.setVideoURI(null);
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                    // ...
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Caricamento riuscito!", Toast.LENGTH_LONG).show();
                                    button_Salva.setEnabled(true);
                                    textInputLayoutNomeVideo.setEnabled(true);
                                    button_ScegliVideo.setEnabled(true);
                                    button_Salva.setEnabled(true);
                                    ActivityCaricaVideo.this.findViewById(R.id.button_indietro).setClickable(true);
                                    edit_NomeVideo.setText("");
                                    spinner.setSelection(0);
                                    videoView.setVideoURI(null);
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Video nullo!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Inserisci tutti i dati!", Toast.LENGTH_LONG).show();
                    }


                } else if(tipo == 2){
                    //Gesto

                    if(edit_Ita.getText().toString().equals("") || edit_Fra.getText().toString().equals("") || edit_Eng.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Inserisci tutti i dati!", Toast.LENGTH_LONG).show();
                    } else {

                        storageReference = storage.getReference();
                        String filename = edit_Ita.getText().toString() + "$" + edit_Fra.getText().toString() + "$" + edit_Eng.getText().toString();
                        StorageReference videoRef = storageReference.child("/videos/gesti/" + filename);

                        if (videoUri != null) {
                            UploadTask uploadTask = videoRef.putFile(videoUri);
                            progressBar.setVisibility(View.VISIBLE);
                            button_Salva.setEnabled(false);
                            textInputLayoutIta.setEnabled(false);
                            textInputLayoutFra.setEnabled(false);
                            textInputLayoutEng.setEnabled(false);
                            button_ScegliVideo.setEnabled(false);
                            button_Salva.setEnabled(false);
                            ActivityCaricaVideo.this.findViewById(R.id.button_indietro).setClickable(false);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Caricamento fallito!", Toast.LENGTH_LONG).show();
                                    button_Salva.setEnabled(true);
                                    textInputLayoutIta.setEnabled(true);
                                    textInputLayoutFra.setEnabled(true);
                                    textInputLayoutEng.setEnabled(true);
                                    button_ScegliVideo.setEnabled(true);
                                    button_Salva.setEnabled(true);
                                    ActivityCaricaVideo.this.findViewById(R.id.button_indietro).setClickable(true);
                                    edit_Ita.setText("");
                                    edit_Fra.setText("");
                                    edit_Eng.setText("");
                                    spinner.setSelection(0);
                                    videoView.setVideoURI(null);
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                    // ...
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Caricamento riuscito!", Toast.LENGTH_LONG).show();
                                    button_Salva.setEnabled(true);
                                    textInputLayoutIta.setEnabled(true);
                                    textInputLayoutFra.setEnabled(true);
                                    textInputLayoutEng.setEnabled(true);
                                    button_ScegliVideo.setEnabled(true);
                                    button_Salva.setEnabled(true);
                                    ActivityCaricaVideo.this.findViewById(R.id.button_indietro).setClickable(true);
                                    edit_Ita.setText("");
                                    edit_Fra.setText("");
                                    edit_Eng.setText("");
                                    spinner.setSelection(0);
                                    videoView.setVideoURI(null);
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Video nullo!", Toast.LENGTH_SHORT).show();
                        }
                    }


                }


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PICK_VIDEO) {
                videoUri = data.getData();
                videoView.setVideoURI(videoUri);
                videoView.setVisibility(View.VISIBLE);
                videoView.start();


                /*if(tipo == 0){

                } else if(tipo == 1){
                    //Commento
                    tipo = 1;
                    textInputLayoutNomeVideo.setVisibility(View.VISIBLE);

                } else if(tipo == 2){
                    //Gesto
                    tipo = 2;
                    textInputLayoutIta.setVisibility(View.VISIBLE);
                    textInputLayoutFra.setVisibility(View.VISIBLE);
                    textInputLayoutEng.setVisibility(View.VISIBLE);


                }*/


            }
        }
    }

    private class SpinnerAdapterVideo extends BaseAdapter {

        private Context context;
        private ArrayList<String> list;
        private Boolean doppio;

        public SpinnerAdapterVideo(Context context, ArrayList<String> list){
            this.context = context;
            this.list = list;
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
                        .inflate(R.layout.spinner_base, viewGroup, false);


            TextView textView = (TextView) rootView.findViewById(R.id.textView_SB);
            String text = getItem(i);

            if(text.equals("Tipo di video")){
                textView.setTextColor(Color.LTGRAY);
            } else{
                textView.setTextColor(Color.BLACK);
            }

            textView.setText(text);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView_SB);
            imageView.setVisibility(View.GONE);

            return rootView;
        }
    }

}