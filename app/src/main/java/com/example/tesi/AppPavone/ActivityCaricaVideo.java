package com.example.tesi.AppPavone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tesi.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class ActivityCaricaVideo extends AppCompatActivity {

    private static final int PICK_VIDEO = 200;
    private Button button_Salva;
    private Button button_ScegliVideo;
    private PlayerView playerView;
    private SimpleExoPlayer exoPlayer;
    private Uri videoUri;
    private EditText edit_NomeVideo;
    private EditText edit_Domanda;
    private EditText edit_Corretta;
    private EditText edit_Sbagliata;
    private ProgressBar progressBar;
    private TextInputLayout textInputLayoutNomeVideo;
    private TextInputLayout textInputLayoutDomanda;
    private TextInputLayout textInputLayoutCorretta;
    private TextInputLayout textInputLayoutSbagliata;
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
        playerView = findViewById(R.id.player);

        textInputLayoutNomeVideo = findViewById(R.id.textInputLayoutNomeVideo);
        textInputLayoutDomanda = findViewById(R.id.textInputLayoutDomanda);
        textInputLayoutCorretta = findViewById(R.id.textInputLayoutCorretta);
        textInputLayoutSbagliata = findViewById(R.id.textInputLayoutSbagliata);
        textInputLayoutIta = findViewById(R.id.textInputLayoutIta);
        textInputLayoutFra = findViewById(R.id.textInputLayoutFra);
        textInputLayoutEng = findViewById(R.id.textInputLayoutEng);

        edit_NomeVideo = findViewById(R.id.edit_NomeVideo);
        edit_Domanda = findViewById(R.id.edit_Domanda);
        edit_Corretta = findViewById(R.id.edit_Corretta);
        edit_Sbagliata = findViewById(R.id.edit_Sbagliata);
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
                    textInputLayoutIta.setVisibility(View.GONE);
                    textInputLayoutFra.setVisibility(View.GONE);
                    textInputLayoutEng.setVisibility(View.GONE);
                    textInputLayoutNomeVideo.setVisibility(View.GONE);
                    textInputLayoutDomanda.setVisibility(View.GONE);
                    textInputLayoutCorretta.setVisibility(View.GONE);
                    textInputLayoutSbagliata.setVisibility(View.GONE);
                } else if(i == 1){
                    //Commento
                    tipo = 1;
                    textInputLayoutIta.setVisibility(View.GONE);
                    textInputLayoutFra.setVisibility(View.GONE);
                    textInputLayoutEng.setVisibility(View.GONE);
                    textInputLayoutNomeVideo.setVisibility(View.VISIBLE);
                    textInputLayoutDomanda.setVisibility(View.VISIBLE);
                    textInputLayoutCorretta.setVisibility(View.VISIBLE);
                    textInputLayoutSbagliata.setVisibility(View.VISIBLE);

                } else if(i == 2){
                    //Gesto
                    tipo = 2;
                    textInputLayoutNomeVideo.setVisibility(View.GONE);
                    textInputLayoutDomanda.setVisibility(View.GONE);
                    textInputLayoutCorretta.setVisibility(View.GONE);
                    textInputLayoutSbagliata.setVisibility(View.GONE);
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

                    if(!edit_NomeVideo.getText().toString().equals("") || !edit_Domanda.getText().toString().equals("")
                            || !edit_Corretta.getText().toString().equals("") || !edit_Sbagliata.getText().toString().equals("")) {
                        storageReference = storage.getReference();
                        //String filename = edit_NomeVideo.getText().toString();
                        String filename = edit_NomeVideo.getText().toString() + "$" + edit_Domanda.getText().toString()
                                + "$" + edit_Corretta.getText().toString() + "$" + edit_Sbagliata.getText().toString() ; //prendi da editext
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
                                    //videoView.setVideoURI(null);
                                    playerView.setVisibility(View.INVISIBLE);
                                    exoPlayer.release();
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
                                    //videoView.setVideoURI(null);
                                    playerView.setVisibility(View.INVISIBLE);
                                    exoPlayer.release();
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
                                    //videoView.setVideoURI(null);
                                    playerView.setVisibility(View.INVISIBLE);
                                    exoPlayer.release();
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
                                    //videoView.setVideoURI(null);
                                    playerView.setVisibility(View.INVISIBLE);
                                    exoPlayer.release();
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
                /*videoView.setVideoURI(videoUri);
                videoView.setVisibility(View.VISIBLE);
                videoView.start();*/

                Long inc = Long.valueOf(5000);
                exoPlayer = new SimpleExoPlayer.Builder(getApplicationContext()).setSeekParameters(new SeekParameters(inc,inc)).build();

                playerView.setPlayer(exoPlayer);
                playerView.setKeepScreenOn(true);
                playerView.setVisibility(View.VISIBLE);


                exoPlayer.addListener(new Player.Listener() {
                    @Override
                    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                        Player.Listener.super.onPlayerStateChanged(playWhenReady, playbackState);
                    }
                });

                exoPlayer.setMediaItem( MediaItem.fromUri(videoUri));
                exoPlayer.prepare();
                exoPlayer.play();

                ActivityCaricaVideo.this.findViewById(R.id.exo_fullscreen).setVisibility(View.INVISIBLE);
                ActivityCaricaVideo.this.findViewById(R.id.exo_lock).setVisibility(View.INVISIBLE);


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