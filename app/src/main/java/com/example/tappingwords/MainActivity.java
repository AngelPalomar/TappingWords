package com.example.tappingwords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnToHard, btnToNormal, btnToInsane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToNormal = findViewById(R.id.btn_to_normal);
        btnToHard = findViewById(R.id.btn_to_hard);
        btnToInsane = findViewById(R.id.btn_to_insane);

        //Iniciar modo normal
        btnToNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NormalMode.class));
            }
        });

        //Iniciar modo dificil
        btnToHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HardMode.class));
            }
        });

        btnToInsane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InsaneMode.class));
            }
        });
    }
}