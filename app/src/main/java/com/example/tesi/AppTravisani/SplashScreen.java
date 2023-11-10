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
import androidx.appcompat.app.AppCompatDelegate;

import com.example.tesi.AppConte.Login;
import com.example.tesi.AppConte.TipoLogin;
import com.example.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    //tempo di proiezione
    private static final int SPLASH_TIMER = 5000;

    ImageView image;
    TextView slogan, logo;

    //Animation
    Animation bottomAnim, sideAnim;

    SharedPreferences onBordingScreen;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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

            user = FirebaseAuth.getInstance().getCurrentUser();
            mAuth = FirebaseAuth.getInstance();

            if(user != null) {
                Intent intent = new Intent(SplashScreen.this, Home.class);
                startActivity(intent);
            }
            else {
                //apri la schermata login
                Intent i = new Intent(getApplicationContext(), TipoLogin.class);
                startActivity(i);
                finish();
            }







        },SPLASH_TIMER);

    }

}