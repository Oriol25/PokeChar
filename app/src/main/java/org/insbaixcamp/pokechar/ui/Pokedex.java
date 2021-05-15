package org.insbaixcamp.pokechar.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

    private String urlJSON;
    private PokedexBasic[] pokedexBasics;
    private String urlFoto;
    private String TAG = "PokeChar/Pokedex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Bundle bundle = getIntent().getExtras();
        urlJSON = bundle.getString("pokedex");

        cargarJSON();

    }

    public void cargarJSON() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

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
                                urlFoto = posPokemon(jsonArray.getJSONObject(i).getString("url"));
                                pokedexBasics[i] = new PokedexBasic(i+1, jsonArray.getJSONObject(i).getString("name"), jsonArray.getJSONObject(i).getString("url"), urlFoto);

                            }

                            cargarPokedex();

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

    public String posPokemon (String str) {
        str = str.replace(PokeApi.pokedex, "");
        str = str.replace("/", ".png");
        str = PokeApi.spriteFront + str;

        return str;
    }

    public void cargarPokedex() {

        setContentView(R.layout.activity_pokedex);

        RecyclerView rvPokedex = findViewById(R.id.rvPokedex);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPokedex.setLayoutManager(linearLayoutManager);
        rvPokedex.setHasFixedSize(true);
        PokedexAdapter pokedexAdapter = new PokedexAdapter(pokedexBasics, this);
        rvPokedex.setAdapter(pokedexAdapter);

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent i = new Intent(this, PokedexData.class);
        i.putExtra("urlData", pokedexBasics[clickedItemIndex].getUrlData());
        i.putExtra("pokedex", urlJSON);
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, PokedexMenu.class);
        startActivity(i);
    }

}
