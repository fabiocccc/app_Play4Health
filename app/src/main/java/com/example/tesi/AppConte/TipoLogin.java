package com.example.tesi.AppConte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tesi.AppTravisani.Home;
import com.example.tesi.R;

public class TipoLogin extends AppCompatActivity {

    private Button btnOspite;
    private Button btnUsername;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_login);

        btnOspite = findViewById(R.id.button_Ospite);
        btnUsername = findViewById(R.id.button_Username);

        btnOspite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Toast.makeText(getApplicationContext(), "Accesso eseguito come ospite", Toast.LENGTH_SHORT).show();

                    finish();
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);


            }
        });

        btnUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(), "Accesso eseguito come ospite", Toast.LENGTH_SHORT).show();

                finish();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);


            }
        });

    }
}
