package org.insbaixcamp.pokechar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import org.insbaixcamp.pokechar.model.PokedexBasic;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PokedexData extends AppCompatActivity implements View.OnClickListener {

    private String urlPokemon;
    private String urlSpecies;
    private String urlEvolution;

    private String urlFirstEvolution;
    private String urlSecondEvolution;
    private String urlThirdEvolution;

    private String TAG = "PokeChar/PokedexData";

    private TextView tvName;
    private TextView tvDes;
    private TextView tvAtt;
    private TextView tvSpAtt;
    private TextView tvHP;
    private TextView tvDef;
    private TextView tvSpDef;
    private TextView tvSpeed;

    private ImageView ibFoto;
    private ImageView ibEvolution;
    private ImageView ibEvolutionDos;
    private ImageView ibEvolutionTres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex_data);

        Bundle bundle = getIntent().getExtras();
        urlPokemon = bundle.getString("urlData");
        urlSpecies = urlPokemon.replace("pokemon", "pokemon-species");

        tvName = findViewById(R.id.tvNamePokedexData);
        tvDes = findViewById(R.id.tvDescription);

        tvAtt = findViewById(R.id.tvResAtt);
        tvSpAtt = findViewById(R.id.tvResSA);
        tvHP = findViewById(R.id.tvResHP);
        tvDef = findViewById(R.id.tvResD);
        tvSpDef = findViewById(R.id.tvResSD);
        tvSpeed = findViewById(R.id.tvResSpeed);

        ibFoto = findViewById(R.id.ibFotoPokedexData);
        ibEvolution = findViewById(R.id.ibEvolution1);
        ibEvolutionDos = findViewById(R.id.ibEvolution2);
        ibEvolutionTres = findViewById(R.id.ibEvolution3);

        ibEvolution.setOnClickListener(this);
        ibEvolutionDos.setOnClickListener(this);
        ibEvolutionTres.setOnClickListener(this);

        cargarJSON();

    }

    public void cargarJSON() {
        cargarPokemon();
        cargarPokemonSpecies();
    }

    public void cargarPokemon() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlPokemon,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray jsonArray;
                        JSONObject jsonObject;

                        int stat;
                        String name;

                        try {

                            //Cargar Nombre
                            String str = response.getString("name");
                            str = str.toUpperCase().charAt(0) + str.substring(1).toLowerCase();
                            tvName.setText(str);

                            //Cargar LOS STATS
                            jsonArray = response.getJSONArray("stats");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);

                                stat = jsonObject.getInt("base_stat");
                                name = jsonObject.getJSONObject("stat").getString("name");

                                if (name.equals("hp")) {
                                    tvHP.setText(String.valueOf(stat));
                                } else if (name.equals("attack")) {
                                    tvAtt.setText(String.valueOf(stat));
                                } else if (name.equals("defense")) {
                                    tvDef.setText(String.valueOf(stat));
                                } else if (name.equals("special-attack")) {
                                    tvSpAtt.setText(String.valueOf(stat));
                                } else if (name.equals("special-defense")) {
                                    tvSpDef.setText(String.valueOf(stat));
                                } else if (name.equals("speed")) {
                                    tvSpeed.setText(String.valueOf(stat));
                                }

                            }

                            // CARGAR IMAGEN
                            Picasso.get().load(posPokemon(urlPokemon)).into(ibFoto);

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

    public void cargarPokemonSpecies() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlSpecies,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONObject jsonEvolves;
                        JSONArray genera;

                        String des = "";
                        String idioma = "";

                        try {

                            genera = response.getJSONArray("genera");

                            for (int i = 0; i < genera.length(); i++) {
                                des = genera.getJSONObject(i).getString("genus");
                                idioma = genera.getJSONObject(i).getJSONObject("language").getString("name");

                                if (idioma.equals("es")) {
                                    tvDes.setText(des);
                                }
                            }

                            //Cargamos url de las evoluciones
                            jsonEvolves = response.getJSONObject("evolution_chain");
                            urlEvolution = jsonEvolves.getString("url");

                            cargarEvolution();

                        } catch (JSONException e) {
                            Log.i(TAG,"ERROR: " + e.getMessage());
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

    public void cargarEvolution() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlEvolution,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONObject jsonObject;

                        try {

                            jsonObject = response.getJSONObject("chain");

                            urlFirstEvolution = jsonObject.getJSONObject("species").getString("url");
                            urlSecondEvolution = jsonObject.getJSONArray("evolves_to").getJSONObject(0).getJSONObject("species").getString("url");
                            urlThirdEvolution = jsonObject.getJSONArray("evolves_to").getJSONObject(0).getJSONArray("evolves_to").getJSONObject(0).getJSONObject("species").getString("url");

                            Picasso.get().load(posPokemonSpecies(urlFirstEvolution)).into(ibEvolution);
                            Picasso.get().load(posPokemonSpecies(urlSecondEvolution)).into(ibEvolutionDos);
                            Picasso.get().load(posPokemonSpecies(urlThirdEvolution)).into(ibEvolutionTres);

                        } catch (JSONException e) {
                            Log.i(TAG, "ERROR: " + e.getMessage());
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

    public String posPokemon (String str) {
        str = str.replace(PokeApi.pokedex, "");
        str = str.replace("/", ".png");

        str = PokeApi.spriteFront + str;

        return str;
    }

    public String posPokemonSpecies(String str) {
        str = str.replace(PokeApi.pokedexSpecies, "");
        str = str.replace("/", ".png");

        str = PokeApi.spriteFront + str;

        return str;
    }

    public String posPokemonSpeciesToPokemon(String str) {

        str = str.replace("-species", "");

        return str;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, PokedexData.class);
        if (v.getId() == ibEvolution.getId()) {
            i.putExtra("urlData", posPokemonSpeciesToPokemon(urlFirstEvolution));
        } else if (v.getId() == ibEvolutionDos.getId()) {
            i.putExtra("urlData", posPokemonSpeciesToPokemon(urlSecondEvolution));
        } else if (v.getId() == ibEvolutionTres.getId()) {
            i.putExtra("urlData", posPokemonSpeciesToPokemon(urlThirdEvolution));
        }
        startActivity(i);

    }
}
