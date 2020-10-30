package com.example.tappingwords;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ViewTop extends AppCompatActivity {

    private TextView tvTopTitle;
    private String difficulty;
    private ListView lvScore;
    private ArrayList<String> topScoreList;
    private ArrayAdapter<String> adapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_top);

        tvTopTitle = findViewById(R.id.tv_top_title);
        lvScore = findViewById(R.id.lv_top);
        difficulty = getIntent().getStringExtra("diff");

        if (difficulty.equals("normal")) {
            tvTopTitle.setText("Top 10 dificultad Normal");
        } else if (difficulty.equals("hard")) {
            tvTopTitle.setText("Top 10 dificultad Difícil");
        } else {
            tvTopTitle.setText("Top 10 dificultad Insano");
        }

        String url = "http://dtai.uteq.edu.mx/~crupal192/tapping_words/view_top_" + difficulty + ".php";
        getScoresData(url);
    }

    public void getScoresData (String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("] [", ",");
                if (response.length() > 0) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        Log.i("sizejson", "" + jsonArray.length());
                        chargeList(jsonArray); //Agreggo el objeto JSON a la lista
                    } catch (JSONException e) {
                        Toast.makeText(ViewTop.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewTop.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                Log.i("error", String.valueOf(error.getMessage()));
            }
        });

        queue.add(stringRequest);
    }

    public void chargeList(JSONArray jsonArray) {

        Toast.makeText(this, "Cargando...", Toast.LENGTH_SHORT).show();
        topScoreList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                topScoreList.add((i + 1) + ".- " + jsonArray.getString(i));
                //topScoreList.add(jsonArray.getString(i) + " " + jsonArray.getString(i + 1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        adapter = new ArrayAdapter<>(ViewTop.this, android.R.layout.simple_list_item_1, topScoreList);
        lvScore.setAdapter(adapter);

    }
}