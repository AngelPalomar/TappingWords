package com.example.tappingwords;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

public class UploadScore extends AppCompatActivity {

    private TextView tvPoints, tvDiff;
    private EditText edUsername;
    private Button btnSave;
    private int points;
    private String difficulty;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_score);

        client = new AsyncHttpClient();

        tvPoints = findViewById(R.id.tv_score_result);
        tvDiff = findViewById(R.id.tv_diff);
        edUsername = findViewById(R.id.ed_username);
        btnSave = findViewById(R.id.btn_save);

        points = getIntent().getIntExtra("points", 0);
        difficulty = getIntent().getStringExtra("difficulty");

        tvPoints.setText(String.format("%d pts", points));
        tvDiff.setText(difficulty.equals("normal") ? "Normal" :
                difficulty.equals("hard") ? "Difícil" :
                        difficulty.equals("insane") ? "Insano" : "Dificultad");
    }

    public void saveData(View view) {
        if (edUsername.getText().toString().isEmpty()) {
            edUsername.requestFocus();
            Toast.makeText(this, "El nombre de usuario es requerido", Toast.LENGTH_SHORT).show();
            return;
        }

        String username = edUsername.getText().toString();

        //Deshabilitar botón
        btnSave.setEnabled(false);

        //enviar POST
        insertData(username, points, difficulty);
    }

    public void insertData(String usrname, int sc, String df) {
        final AlertDialog.Builder message = new AlertDialog.Builder(this);

        final String url = "http://dtai.uteq.edu.mx/~crupal192/tapping_words/save_score.php?"; //PHP Script
        String params = "username=" + usrname + "&score=" + sc + "&difficulty=" + df;

        client.post(url + params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    message.setTitle("Acción completada con éxito")
                            .setMessage("Los datos fueron guardados")
                            .setCancelable(false)
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(UploadScore.this, MainActivity.class));
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                message.setTitle("Ocurrió un error")
                        .setMessage("Error al guardar los datos, inténtelo de nuevo." + error)
                        .setCancelable(false)
                        .setPositiveButton("Intentar de nuevo", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                btnSave.setEnabled(true);
                            }
                        })
                        .setNegativeButton("Volver al menú", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(UploadScore.this, MainActivity.class));
                            }
                        })
                        .show();
            }
        });
    }

    public void cancelUpload(View view) {
        startActivity(new Intent(UploadScore.this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {}
}