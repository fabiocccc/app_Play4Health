package com.example.tesi.AppTravisani;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tesi.AppConte.Login;
import com.example.tesi.AppConte.TipoLogin;
import com.example.tesi.AppPavone.HomePrima;
import com.example.tesi.AppTravisani.Percorso1.P1EpisodiActivity;
import com.example.tesi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.net.NetworkInterface;

public class Home extends AppCompatActivity {

    private ImageView ball, book;
    private FrameLayout button_aiuto;
    private AnimationDrawable animationDrawable1 = null;
    private AnimationDrawable animationDrawable2 = null;
    private ImageView help1, help2, logout;
    private TextView txtCiao;
    private TextView txtUser;

    DatabaseReference dr;
    private String key;
    private FirebaseUser userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ball = findViewById(R.id.ball);
        book = findViewById(R.id.book);
        button_aiuto = findViewById(R.id.button_aiuto);
        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);
        logout = findViewById(R.id.logout);
        txtCiao = findViewById(R.id.textCiao);
        txtUser = findViewById(R.id.textUsername);

        //logout.setVisibility(View.GONE);

        userDb = FirebaseAuth.getInstance().getCurrentUser();

        //controllo se l'utente è loggato
        if(userDb != null) {
            logout.setVisibility(View.VISIBLE);

            txtCiao.setText("Ciao");
            txtCiao.setVisibility(View.VISIBLE);

            String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            String nomeUtente =  mailLogged.replace("@gmail.com", "");
            txtUser.setText(nomeUtente);
            txtUser.setVisibility(View.VISIBLE);

        }
        else if(userDb == null) {
            logout.setVisibility(View.GONE);
            txtCiao.setVisibility(View.INVISIBLE);
            txtUser.setVisibility(View.INVISIBLE);
        }

        button_aiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                help1.setVisibility(View.VISIBLE);
                help2.setVisibility(View.VISIBLE);

                animationDrawable1 = (AnimationDrawable) help1.getBackground();
                animationDrawable2 = (AnimationDrawable) help2.getBackground();
                animationDrawable1.start();
                animationDrawable2.start();

                button_aiuto.setVisibility(View.GONE);

            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String messaggio = "Sei sicuro di volerti disconnettere?";
                showMessage(messaggio);


            }
        });

        //setta animazione lampeggiante
        //sulla palla
        YoYo.with(Techniques.Pulse)
                .duration(700)
                .repeat(10)
                .playOn(ball);
        //sul libro
        YoYo.with(Techniques.Pulse)
                .duration(700)
                .repeat(10)
                .playOn(book);
    }

    private void showMessage(String messaggio) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setMessage(messaggio)
                .setTitle("Logout");
// Add the buttons
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String mailLogged = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                System.out.println("firebase:"+mailLogged);

                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "Disconnessione effettuata", Toast.LENGTH_SHORT).show();

                finish();
                Intent intent = new Intent(getApplicationContext(), TipoLogin.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
// Set other dialog properties
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean controlConnection() {

        if(!isConnected(getApplicationContext()))
        {
            return true;
        }else
        {
            return false;}
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Connsessione assente!");
        builder.setMessage("Collega il tuo dispositivo a Internet");
        builder.setIcon(R.drawable.ic_baseline_signal_wifi_connected_no_internet_4_24);
        builder.setCancelable(false);
        builder.setPositiveButton("Connetti", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                     }});
        builder.setNegativeButton("Chiudi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();

    }

    private boolean isConnected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if( (wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected()))
        {
                return true;
        }else
        {
            return false;
        }

    }

    public void StartExercise(View view) {

        //aprire APP PAVONE
        if(controlConnection())
        {
            //Connessione assente
            showCustomDialog();

        }else
        {
            Intent i = new Intent(getApplicationContext(), HomePrima.class);
            startActivity(i);
            finish();
        }

    }
    public void StartStory(View view) {

        //aprire Sezione CardView APP TRAVISANI
        if(controlConnection())
        {
            //Connessione assente
            showCustomDialog();
        }
        else
        {
            Intent i = new Intent(getApplicationContext(), StoryCard.class);
            startActivity(i);
            finish();
        }


    }

    @Override
    public void onBackPressed() {
        finish();
    }


}