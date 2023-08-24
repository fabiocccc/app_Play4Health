package com.example.tesi.AppTravisani;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tesi.AppConte.Login;
import com.example.tesi.R;

public class SplashScreen extends AppCompatActivity {

    //tempo di proiezione
    private static final int SPLASH_TIMER = 5000;

    ImageView image;
    TextView slogan, logo;

    //Animation
    Animation bottomAnim, sideAnim;

    SharedPreferences onBordingScreen;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //nasconde la barra dell'app
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        //Hooks
        image= findViewById(R.id.imagelogo);
        slogan= findViewById(R.id.nomelogo);
        logo = findViewById(R.id.slogan);

        //Animations
        bottomAnim= AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);

        //set Animation on background
        image.setAnimation(sideAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);


        new Handler().postDelayed(() -> {
            onBordingScreen = getSharedPreferences("onBordingScreen",MODE_PRIVATE);

            boolean isFirstTime= onBordingScreen.getBoolean("firstTime",true);

            if(isFirstTime){//se è la prima volta che un user apre l'app succede:
                SharedPreferences.Editor editor = onBordingScreen.edit();
                editor.putBoolean("firstTime",false);
                editor.apply();

                //aprire la sequenza di slide che spiegano come si usa l'app
                Intent i = new Intent(getApplicationContext(), Home.class);
                startActivity(i);
                finish();
            } else {

                //apri la schermata login
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();

            }


        },SPLASH_TIMER);

    }
}