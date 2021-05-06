package org.insbaixcamp.pokechar.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.insbaixcamp.pokechar.R;
import org.insbaixcamp.pokechar.adapter.PokedexAdapter;
import org.insbaixcamp.pokechar.api.PokeApi;
import org.insbaixcamp.pokechar.model.PokedexBasic;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pokedex extends AppCompatActivity implements PokedexAdapter.ListItemClickListener {

    private int limit;
    private int offset;

    private String urlJSON;

    private PokedexBasic[] pokedexBasics;
    private String urlFoto;

    private String TAG = "PokeChar/Pokedex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);

        Bundle bundle = getIntent().getExtras();
        limit = bundle.getInt("limit");
        offset = bundle.getInt("offset");

        urlJSON = PokeApi.pokedex + "limit=" + limit + "&offset=" + offset;
        cargarJSON();


    }

    public void cargarJSON() {


        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlJSON,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray jsonArray;

                        try {
                            jsonArray = response.getJSONArray("results");

                            pokedexBasics = new PokedexBasic[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {
                                response = jsonArray.getJSONObject(i);
                                urlFoto = posPokemon(response.getString("url"));
                                pokedexBasics[i] = new PokedexBasic(i, response.getString("name"), urlFoto);

                            }

                            cargarPokedex();

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

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);

    }

    public String posPokemon (String str) {
        str = str.replace("https://pokeapi.co/api/v2/pokemon/", "");
        str = str.replace("/", ".png");

        str = PokeApi.spriteFront + str;

        return str;
    }

    public void cargarPokedex() {
        RecyclerView rvPokedex = findViewById(R.id.rvPokedex);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPokedex.setLayoutManager(linearLayoutManager);
        rvPokedex.setHasFixedSize(true);
        PokedexAdapter pokedexAdapter = new PokedexAdapter(pokedexBasics, this);
        rvPokedex.setAdapter(pokedexAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.i(TAG, "Pokemon Selecionado: " + pokedexBasics[clickedItemIndex].getName());
    }
}