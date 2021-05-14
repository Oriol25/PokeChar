package org.insbaixcamp.pokechar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.insbaixcamp.pokechar.R;
import org.insbaixcamp.pokechar.api.PokeApi;
import org.insbaixcamp.pokechar.conf.Score;
import org.insbaixcamp.pokechar.model.PokedexBasic;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static org.insbaixcamp.pokechar.R.drawable.quiz;
import static org.insbaixcamp.pokechar.R.drawable.quizcorrect;
import static org.insbaixcamp.pokechar.R.drawable.quizincorrect;

public class Trivial extends AppCompatActivity implements View.OnClickListener {

    ImageView ivPokemon;

    Button btop1;
    Button btop2;
    Button btop3;
    Button btNext;

    TextView tvScore;

    PokedexBasic[] pokedexBasic;
    PokedexBasic[] pokemon = new PokedexBasic[3];

    private String TAG = "PokeChar/Trivial";
    private String scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivial);

        ivPokemon = findViewById(R.id.ivPokemon);

        tvScore = findViewById(R.id.tvScore);
        scoreText = getString(R.string.score) + " " + Score.score;
        tvScore.setText(scoreText);

        btop1 = findViewById(R.id.btOpUno);
        btop2 = findViewById(R.id.btOpDos);
        btop3 = findViewById(R.id.btOpTres);
        btNext = findViewById(R.id.btNext);

        btop1.setOnClickListener(this);
        btop2.setOnClickListener(this);
        btop3.setOnClickListener(this);
        btNext.setOnClickListener(this);

        cargarPokemon();

    }

    public void cargarPokemon() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                PokeApi.pokedexAll,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray jsonArray;
                        String urlFoto;

                        try {

                            jsonArray = response.getJSONArray("results");
                            pokedexBasic = new PokedexBasic[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {
                                urlFoto = posPokemon(jsonArray.getJSONObject(i).getString("url"));
                                pokedexBasic[i] = new PokedexBasic(i+1, jsonArray.getJSONObject(i).getString("name"), jsonArray.getJSONObject(i).getString("url"), urlFoto);

                            }

                            mostrarQuiz();

                        } catch (JSONException e) {
                            Log.i(TAG, e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, error.getMessage());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public void mostrarQuiz() {

        int rand;
        int op1;
        int op2;
        int op3;

        btNext.setVisibility(View.GONE);

        btop1.setBackgroundResource(quiz);
        btop2.setBackgroundResource(quiz);
        btop3.setBackgroundResource(quiz);

        for (int i = 0; i < pokemon.length; i++) {
            rand = (int) (Math.random()*894);
            pokemon[i] = pokedexBasic[rand];
        }

        op1 = (int) (Math.random()*3);
        btop1.setText(pokemon[op1].getName());
        Picasso.get().load(pokemon[0].getUrlImage()).into(ivPokemon);

        do {
            op2 = (int) (Math.random()*3);

        } while (op2 == op1);

        btop2.setText(pokemon[op2].getName());

        do {
            op3 = (int) (Math.random()*3);
        } while (op3 == op1 || op3 == op2);

        btop3.setText(pokemon[op3].getName());

    }

    public String posPokemon (String str) {
        str = str.replace(PokeApi.pokedex, "");
        str = str.replace("/", ".png");

        str = PokeApi.spriteFront + str;

        return str;
    }

    @Override
    public void onClick(View v) {

        boolean boolControl = false;

            if (v.getId() == btop1.getId()) {
                if (btop1.getText() == pokemon[0].getName()) {
                    btop1.setBackgroundResource(quizcorrect);
                    Score.score += 1000;
                    scoreText = getString(R.string.score) + " " + Score.score;
                    tvScore.setText(scoreText);
                } else {
                    btop1.setBackgroundResource(quizincorrect);
                    if (btop2.getText() == pokemon[0].getName()) {
                        btop2.setBackgroundResource(quizcorrect);
                    } else if (btop3.getText() == pokemon[0].getName()) {
                        btop3.setBackgroundResource(quizcorrect);
                    }
                    Score.score = 0;
                    scoreText = getString(R.string.score) + " " + Score.score;
                    tvScore.setText(scoreText);
                }
                btNext.setVisibility(View.VISIBLE);
                btop1.setClickable(false);
                btop2.setClickable(false);
                btop3.setClickable(false);
            } else if (v.getId() == btop2.getId()) {
                if (btop2.getText() == pokemon[0].getName()) {
                    btop2.setBackgroundResource(quizcorrect);
                    Score.score += 1000;
                    scoreText = getString(R.string.score) + " " + Score.score;
                    tvScore.setText(scoreText);
                } else {
                    btop2.setBackgroundResource(quizincorrect);
                    if (btop1.getText() == pokemon[0].getName()) {
                        btop1.setBackgroundResource(quizcorrect);
                    } else if (btop3.getText() == pokemon[0].getName()) {
                        btop3.setBackgroundResource(quizcorrect);
                    }
                    Score.score = 0;
                    scoreText = getString(R.string.score) + " " + Score.score;
                    tvScore.setText(scoreText);
                }
                btNext.setVisibility(View.VISIBLE);
                btop1.setClickable(false);
                btop2.setClickable(false);
                btop3.setClickable(false);
            } else if (v.getId() == btop3.getId()) {
                if (btop3.getText() == pokemon[0].getName()) {
                    btop3.setBackgroundResource(quizcorrect);
                    Score.score += 1000;
                    scoreText = getString(R.string.score) + " " + Score.score;
                    tvScore.setText(scoreText);
                } else {
                    btop3.setBackgroundResource(quizincorrect);
                    if (btop1.getText() == pokemon[0].getName()) {
                        btop1.setBackgroundResource(quizcorrect);
                    } else if (btop2.getText() == pokemon[0].getName()) {
                        btop2.setBackgroundResource(quizcorrect);
                    }
                    Score.score = 0;
                    scoreText = getString(R.string.score) + " " + Score.score;
                    tvScore.setText(scoreText);
                }
                btNext.setVisibility(View.VISIBLE);
                btop1.setClickable(false);
                btop2.setClickable(false);
                btop3.setClickable(false);
            }

        if (v.getId() == btNext.getId()) {
            btop1.setClickable(true);
            btop2.setClickable(true);
            btop3.setClickable(true);
            mostrarQuiz();
        }
    }
}