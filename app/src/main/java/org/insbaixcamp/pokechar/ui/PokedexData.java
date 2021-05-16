package org.insbaixcamp.pokechar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import org.insbaixcamp.pokechar.api.TypeApi;
import org.insbaixcamp.pokechar.conf.Language;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PokedexData extends AppCompatActivity implements View.OnClickListener {

    private String urlPokemon;
    private String urlSpecies;
    private String urlEvolution;
    private String urlPokedex;

    private String urlFirstEvolution;
    private String urlSecondEvolution;
    private String urlThirdEvolution;

    private String urlTypeP;
    private String urlTypeS;

    private String TAG = "PokeChar/PokedexData";

    private TextView tvName;
    private TextView tvDes;
    private TextView tvAtt;
    private TextView tvSpAtt;
    private TextView tvHP;
    private TextView tvDef;
    private TextView tvSpDef;
    private TextView tvSpeed;
    private TextView tvTypeP;
    private TextView tvTypeS;

    private ImageView ibFoto;
    private ImageView ibEvolution;
    private ImageView ibEvolutionDos;
    private ImageView ibEvolutionTres;

    private View vSplash;
    private ProgressBar pbSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex_data);

        Bundle bundle = getIntent().getExtras();
        urlPokemon = bundle.getString("urlData");
        urlPokedex = bundle.getString("pokedex");

        urlSpecies = urlPokemon.replace("pokemon", "pokemon-species");

        tvName = findViewById(R.id.tvNamePokedexData);
        tvDes = findViewById(R.id.tvDescription);

        tvTypeP = findViewById(R.id.tvTypePrimary);
        tvTypeS = findViewById(R.id.tvTypeSecundary);

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

        vSplash = findViewById(R.id.vSplashPokedexData);
        pbSplash = findViewById(R.id.pbSplashPokedexData);

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
                                    tvHP.setText(" " + stat);
                                } else if (name.equals("attack")) {
                                    tvAtt.setText(" " + stat);
                                } else if (name.equals("defense")) {
                                    tvDef.setText(" " + stat);
                                } else if (name.equals("special-attack")) {
                                    tvSpAtt.setText(" " + stat);
                                } else if (name.equals("special-defense")) {
                                    tvSpDef.setText(" " + stat);
                                } else if (name.equals("speed")) {
                                    tvSpeed.setText(" " + stat);
                                }

                            }

                            urlTypeP = response.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("url");
                            cargarTypeP(urlTypeP);

                            if (response.getJSONArray("types").length() == 2) {
                                urlTypeS = response.getJSONArray("types").getJSONObject(1).getJSONObject("type").getString("url");
                                cargarTypeS(urlTypeS);

                            } else {
                                tvTypeS.setVisibility(View.GONE);
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

                        String des;
                        String idioma;

                        try {

                            genera = response.getJSONArray("genera");

                            for (int i = 0; i < genera.length(); i++) {
                                des = genera.getJSONObject(i).getString("genus");
                                idioma = genera.getJSONObject(i).getJSONObject("language").getString("name");

                                if (idioma.equals(Language.language())) {
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
                            Picasso.get().load(posPokemonSpecies(urlFirstEvolution)).into(ibEvolution);

                            if (jsonObject.getJSONArray("evolves_to").length() != 0) {
                                urlSecondEvolution = jsonObject.getJSONArray("evolves_to").getJSONObject(0).getJSONObject("species").getString("url");
                                Picasso.get().load(posPokemonSpecies(urlSecondEvolution)).into(ibEvolutionDos);
                                if (jsonObject.getJSONArray("evolves_to").getJSONObject(0).getJSONArray("evolves_to").length() != 0) {
                                    urlThirdEvolution = jsonObject.getJSONArray("evolves_to")
                                            .getJSONObject(0).getJSONArray("evolves_to")
                                            .getJSONObject(0).getJSONObject("species").getString("url");
                                    Picasso.get().load(posPokemonSpecies(urlThirdEvolution)).into(ibEvolutionTres);
                                } else {
                                    ibEvolutionTres.setVisibility(View.GONE);
                                }

                            } else {
                                ibEvolutionDos.setVisibility(View.GONE);
                                ibEvolutionTres.setVisibility(View.GONE);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
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

    public void cargarTypeP(final String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray jsonArray;

                        String type;

                        try {

                            jsonArray = response.getJSONArray("names");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                if (jsonArray.getJSONObject(i).getJSONObject("language").getString("name").equals(Language.language())) {
                                    type = jsonArray.getJSONObject(i).getString("name");
                                    tvTypeP.setText(type);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (url.equals(TypeApi.normal)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.normalColor));
                        } else if (url.equals(TypeApi.fighting)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.fightingColor));
                        } else if (url.equals(TypeApi.flying)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.flyingColor));
                        } else if (url.equals(TypeApi.poison)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.fightingColor));
                        } else if (url.equals(TypeApi.ground)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.groundColor));
                        } else if (url.equals(TypeApi.rock)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.rockColor));
                        } else if (url.equals(TypeApi.bug)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.bugColor));
                        } else if (url.equals(TypeApi.ghost)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.ghostColor));
                        } else if (url.equals(TypeApi.steel)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.steelColor));
                        } else if (url.equals(TypeApi.fire)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.fireColor));
                        } else if (url.equals(TypeApi.water)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.waterColor));
                        } else if (url.equals(TypeApi.grass)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.grassColor));
                        } else if (url.equals(TypeApi.electric)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.electricColor));
                        } else if (url.equals(TypeApi.psychic)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.psychicColor));
                        } else if (url.equals(TypeApi.ice)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.iceColor));
                        } else if (url.equals(TypeApi.dragon)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.dragonColor));
                        } else if (url.equals(TypeApi.dark)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.darkColor));
                        } else if (url.equals(TypeApi.fairy)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.fairyColor));
                        }

                        vSplash.setVisibility(View.GONE);
                        pbSplash.setVisibility(View.GONE);

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

    public void cargarTypeS(final String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray jsonArray;
                        String type;

                        try {

                            jsonArray = response.getJSONArray("names");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                if (jsonArray.getJSONObject(i).getJSONObject("language").getString("name").equals(Language.language())) {
                                    type = jsonArray.getJSONObject(i).getString("name");
                                    tvTypeS.setText(type);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (url.equals(TypeApi.normal)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.normalColor));
                        } else if (url.equals(TypeApi.fighting)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.fightingColor));
                        } else if (url.equals(TypeApi.flying)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.flyingColor));
                        } else if (url.equals(TypeApi.poison)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.poisonColor));
                        } else if (url.equals(TypeApi.ground)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.groundColor));
                        } else if (url.equals(TypeApi.rock)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.rockColor));
                        } else if (url.equals(TypeApi.bug)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.bugColor));
                        } else if (url.equals(TypeApi.ghost)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.ghostColor));
                        } else if (url.equals(TypeApi.steel)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.steelColor));
                        } else if (url.equals(TypeApi.fire)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.fireColor));
                        } else if (url.equals(TypeApi.water)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.waterColor));
                        } else if (url.equals(TypeApi.grass)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.grassColor));
                        } else if (url.equals(TypeApi.electric)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.electricColor));
                        } else if (url.equals(TypeApi.psychic)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.psychicColor));
                        } else if (url.equals(TypeApi.ice)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.iceColor));
                        } else if (url.equals(TypeApi.dragon)) {
                            tvTypeP.setBackgroundColor(Color.parseColor(TypeApi.dragonColor));
                        } else if (url.equals(TypeApi.dark)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.darkColor));
                        } else if (url.equals(TypeApi.fairy)) {
                            tvTypeS.setBackgroundColor(Color.parseColor(TypeApi.fairyColor));
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
        i.putExtra("pokedex", urlPokedex);
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, Pokedex.class);
        i.putExtra("pokedex", urlPokedex);
        startActivity(i);
    }

}
