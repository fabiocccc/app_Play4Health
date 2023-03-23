package com.example.tesi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityVideo extends AppCompatActivity {

    private PlayerView playerView;
    private ProgressBar progressBar;
    private ImageView fullscreen;
    private ImageView lockscreen;
    private SimpleExoPlayer exoPlayer;
    private ConstraintLayout.LayoutParams params;
    private AppBarLayout appBarLayout;
    private String nomeVideo;
    private ArrayList<String> parole;
    private TextView textView;
    private Spinner spinner;
    private TextToSpeech textToSpeech;
    private FloatingActionButton button_Ascolta;

    private TextView textDomanda;
    private Button risp1;
    private Button risp2;
    private ImageView esito1;

    private Boolean isFullscreen = false;
    private Boolean isLock = false;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);

        findViewById(R.id.button_indietro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        playerView = findViewById(R.id.player);
        progressBar = findViewById(R.id.progress_bar);
        fullscreen = findViewById(R.id.exo_fullscreen);
        lockscreen = findViewById(R.id.exo_lock);
        appBarLayout = findViewById(R.id.appBarLayout);
        textView = findViewById(R.id.textView);
        spinner = findViewById(R.id.spinner);
        button_Ascolta = findViewById(R.id.button_Ascolta);

        textDomanda = findViewById(R.id.textDomanda);
        risp1 = findViewById(R.id.button_Risp1);
        risp2 = findViewById(R.id.button_Risp2);
        esito1 = findViewById(R.id.esito1);

        params = (ConstraintLayout.LayoutParams) playerView.getLayoutParams();

        Long inc = Long.valueOf(5000);

        exoPlayer = new SimpleExoPlayer.Builder(this).setSeekParameters(new SeekParameters(inc,inc)).build();

        playerView.setPlayer(exoPlayer);
        playerView.setKeepScreenOn(true);


        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Player.Listener.super.onPlayerStateChanged(playWhenReady, playbackState);

                if(playbackState == Player.STATE_BUFFERING){
                    progressBar.setVisibility(View.VISIBLE);
                } else if(playbackState == Player.STATE_READY){
                    progressBar.setVisibility(View.GONE);
                }
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

        if(getIntent().getStringExtra("tipo").equals("commento")){

            textDomanda.setVisibility(View.VISIBLE);
            risp1.setVisibility(View.VISIBLE);
            risp2.setVisibility(View.VISIBLE);
            parole = new ArrayList<>();
            String[] parts = getIntent().getStringExtra("nome").split("\\$");
            parole.add(parts[0]);
            parole.add(parts[1]);
            parole.add(parts[2]); //corretta
            parole.add(parts[3]);

            spinner.setVisibility(View.GONE);
            button_Ascolta.setVisibility(View.VISIBLE);
            //nomeVideo = getIntent().getStringExtra("nome");
            nomeVideo = parole.get(0);
            textView.setText(nomeVideo);
            textDomanda.setText(parole.get(1));

            button_Ascolta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textToSpeech.setLanguage(Locale.ITALY);
                    textToSpeech.speak(parole.get(1), TextToSpeech.QUEUE_FLUSH, null);
                }
            });

            int random = (int)(Math.random() * 2+1);

            if(random == 1){
                risp1.setText(parole.get(2));
                risp2.setText(parole.get(3));

                risp1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        risp1.setBackgroundColor(Color.parseColor("#50e024"));

                        esito1.setVisibility(View.VISIBLE);
                        esito1.setImageResource(R.drawable.thumbs_up);

                        esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

                        risp1.setClickable(false);
                        risp2.setClickable(false);
                    }
                });

                risp2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        risp2.setBackgroundColor(Color.parseColor("#f54518"));

                        esito1.setVisibility(View.VISIBLE);
                        esito1.setImageResource(R.drawable.thumbs_down);

                        esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

                        risp1.setClickable(false);
                        risp2.setClickable(false);
                    }
                });


            } else {
                risp1.setText(parole.get(3));
                risp2.setText(parole.get(2));

                risp1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        risp1.setBackgroundColor(Color.parseColor("#f54518"));

                        esito1.setVisibility(View.VISIBLE);
                        esito1.setImageResource(R.drawable.thumbs_down);

                        esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

                        risp1.setClickable(false);
                        risp2.setClickable(false);
                    }
                });

                risp2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        risp2.setBackgroundColor(Color.parseColor("#50e024"));

                        esito1.setVisibility(View.VISIBLE);
                        esito1.setImageResource(R.drawable.thumbs_up);

                        esito1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_esito));

                        risp1.setClickable(false);
                        risp2.setClickable(false);
                    }
                });
            }


            //VIDEO COMMENTO
            StorageReference refVideo = storage.getReference();
            refVideo.child("/videos/commenti/" + getIntent().getStringExtra("nome")).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    exoPlayer.setMediaItem( MediaItem.fromUri(uri));
                    exoPlayer.prepare();
                    exoPlayer.play();

                }
            });


        } else if(getIntent().getStringExtra("tipo").equals("gesto")){

            parole = new ArrayList<>();
            String[] parts = getIntent().getStringExtra("nome").split("\\$");
            parole.add(parts[0]);
            parole.add(parts[1]);
            parole.add(parts[2]);

            textView.setText(parole.get(0));
            SpinnerAdapter adapter = new SpinnerAdapter(this, parole, false);
            spinner.setAdapter(adapter);
            spinner.setSelection(0);

            //VIDEO GESTo
            StorageReference refVideo = storage.getReference();
            refVideo.child("/videos/gesti/" + getIntent().getStringExtra("nome")).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    exoPlayer.setMediaItem( MediaItem.fromUri(uri));
                    exoPlayer.prepare();
                    exoPlayer.play();

                }
            });

            button_Ascolta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String toSpeak = spinner.getItemAtPosition(0).toString();
                    spinner.setSelection(0);
                    textToSpeech.setLanguage(Locale.ITALY);
                    textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
            });



        }



        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFullscreen){

                    ActivityVideo.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);

                    playerView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT ));
                    fullscreen.setImageResource(R.drawable.ic_baseline_fullscreen_exit);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

                    //RENDI INVISIBILE TUTTO IL RESTO
                    appBarLayout.setVisibility(View.GONE);

                } else {

                    ActivityVideo.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                    playerView.setLayoutParams(new ConstraintLayout.LayoutParams(params));
                    fullscreen.setImageResource(R.drawable.ic_baseline_fullscreen);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

                    //RIATTIVA TUTTO IL RESTO
                    appBarLayout.setVisibility(View.VISIBLE);
                }

                isFullscreen = !isFullscreen;
            }
        });

        lockscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isLock){
                    lockscreen.setImageResource(R.drawable.ic_baseline_lock);
                } else {

                    lockscreen.setImageResource(R.drawable.ic_baseline_lock_open);
                }

                isLock = !isLock;
                
                lockScreen(isLock);
            }
        });

    }

    private void lockScreen(Boolean isLock) {
        LinearLayout linear1 = findViewById(R.id.exo_control_center);
        LinearLayout linear2 = findViewById(R.id.exo_control_bottom);

        if(isLock){
            linear1.setVisibility(View.INVISIBLE);
            linear2.setVisibility(View.INVISIBLE);

        } else{
            linear1.setVisibility(View.VISIBLE);
            linear2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        exoPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                switch (i){
                                    case 0:
                                        //Toast.makeText(getApplicationContext(), "ITALIA",Toast.LENGTH_SHORT).show();
                                        textToSpeech.setLanguage(Locale.ITALIAN);
                                        String toSpeakIt = spinner.getSelectedItem().toString();

                                        textToSpeech.speak(toSpeakIt, TextToSpeech.QUEUE_FLUSH, null);
                                        break;
                                    case 1:
                                        //Toast.makeText(getApplicationContext(), "ENGLISH",Toast.LENGTH_SHORT).show();
                                        textToSpeech.setLanguage(Locale.FRANCE);
                                        String toSpeakFr = spinner.getSelectedItem().toString();

                                        textToSpeech.speak(toSpeakFr, TextToSpeech.QUEUE_FLUSH, null);
                                        break;
                                    case 2:
                                        //Toast.makeText(getApplicationContext(), "BAGUETA",Toast.LENGTH_SHORT).show();
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
    }
}