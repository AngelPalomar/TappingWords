package com.example.tappingwords;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NormalMode extends AppCompatActivity {

    private TextView tvTime, tvPoints, tvWord;
    private int points = 0, seconds = 60;
    private ColorWord colorWord;
    private Timer timer;
    private MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_mode);

        music = MediaPlayer.create(NormalMode.this, R.raw.normal_theme);
        music.start();

        //Inicializar colores y palabras
        colorWord = new ColorWord();
        colorWord.setWordList(new ArrayList<String>());
        colorWord.setColorList(new ArrayList<String>());

        tvTime = findViewById(R.id.tv_time);
        tvPoints = findViewById(R.id.tv_points);
        tvWord = findViewById(R.id.tv_word);

        final AlertDialog.Builder gameResults = new AlertDialog.Builder(this);

        //Palabras
        colorWord.wordList.add("ROJO");
        colorWord.wordList.add("AZUL");

        //Colores
        //Rojos
        colorWord.colorList.add("EF4848");
        colorWord.colorList.add("ED3434");
        colorWord.colorList.add("EC1F1F");
        colorWord.colorList.add("E11414");
        colorWord.colorList.add("CC1212");
        colorWord.colorList.add("F05555");

        //Azules
        colorWord.colorList.add("4A95ED");
        colorWord.colorList.add("368AEB");
        colorWord.colorList.add("227FE9");
        colorWord.colorList.add("1674DE");
        colorWord.colorList.add("1469CA");
        colorWord.colorList.add("125FB6");

        printColorWord(colorWord.generateColor(), colorWord.generateWord());

        //Temporizador
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void run() {
                        seconds--;

                        if (seconds == 60) {
                            tvTime.setText("01:00");
                        } else {
                            if (seconds < 10) {
                                tvTime.setText(String.format("00:0%d", seconds));
                            } else {
                                tvTime.setText(String.format("00:%d", seconds));
                            }
                        }

                        //Cuando el reloj llegue a cero
                        if (seconds == 0) {
                            timer.cancel();
                            if (points > 0) {
                                gameResults.setTitle("¡El tiempo se ha acabado!")
                                        .setMessage(String.format("Puntos: %d", points))
                                        .setCancelable(false)
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                music.stop();
                                                startActivity(new Intent(NormalMode.this, MainActivity.class));
                                            }
                                        })
                                        .setNeutralButton("Guardar puntuación", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                music.stop();
                                                startActivity(new Intent(NormalMode.this, UploadScore.class)
                                                        .putExtra("points", points)
                                                        .putExtra("difficulty", "normal")
                                                );
                                            }
                                        })
                                        .setNegativeButton("Volver a jugar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                restartGame();
                                            }
                                        })
                                        .show();
                            } else {
                                gameResults.setTitle("¡El tiempo se ha acabado!")
                                        .setMessage(String.format("Puntos: %d", points))
                                        .setCancelable(false)
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                music.stop();
                                                startActivity(new Intent(NormalMode.this, MainActivity.class));
                                            }
                                        })
                                        .setNegativeButton("Volver a jugar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                restartGame();
                                            }
                                        })
                                        .show();
                            }
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    public void clickColorNormal (View view) {
        ImageButton btnColor = findViewById(view.getId());

        switch (btnColor.getId()) {
            //Si presionó el botón rojo
            case R.id.btn_red:
                if (tvWord.getText().toString().equals("ROJO")) {
                    points += 5;
                } else {
                    if (points > 0) {
                        points -= 5;
                    }
                }

                //Genera nuevo color y palabra
                printColorWord(colorWord.generateColor(), colorWord.generateWord());
                break;

            //Si presionó el botón azul
            case R.id.btn_blue:
                if (tvWord.getText().toString().equals("AZUL")) {
                    points += 5;
                } else {
                    if (points > 0) {
                        points -= 5;
                    }
                }

                //Genera nuevo color y palabra
                printColorWord(colorWord.generateColor(), colorWord.generateWord());
                break;
        }

        tvPoints.setText(String.valueOf(points));
    }

    public void printColorWord(String color, String word) {
        int hexColor = Integer.parseInt(color, 16) + 0xFF000000;

        tvWord.setText(String.valueOf(word));
        tvWord.setTextColor(hexColor);
    }

    public void restartGame() {
        music.stop();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder exit = new AlertDialog.Builder(this);

        exit.setTitle("Espera")
                .setMessage("¿Deseas salir? Tu progreso no se guardará")
                .setPositiveButton("Quedarse", null)
                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        music.stop();
                        startActivity(new Intent(NormalMode.this, MainActivity.class));
                    }
                })
                .show();
    }
}