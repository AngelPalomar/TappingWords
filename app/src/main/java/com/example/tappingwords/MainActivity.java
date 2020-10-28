package com.example.tappingwords;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    public void howToPlay(View view) {
        AlertDialog.Builder howToPlayInfo = new AlertDialog.Builder(this);

        howToPlayInfo.setTitle("¿Cómo jugar?")
                .setMessage("El juego consiste en seleccionar el botón del color indicado en el " +
                        "texto que aparezca en pantalla; cuando el botón seleccionado sea el " +
                        "correcto, se sumarán 5 puntos, en caso contrario se restará 5 puntos. " +
                        "\nLa partida termina cuando el reloj llegue a cero.")
                .setPositiveButton("Aceptar", null)
                .show();
    }
}