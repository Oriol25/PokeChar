package org.insbaixcamp.pokechar.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.insbaixcamp.pokechar.R;
import org.insbaixcamp.pokechar.adapter.BolsaAdapter;
import org.insbaixcamp.pokechar.adapter.PokedexAdapter;
import org.insbaixcamp.pokechar.api.BolsaApi;
import org.insbaixcamp.pokechar.api.PokeApi;
import org.insbaixcamp.pokechar.model.BolsaBasic;
import org.insbaixcamp.pokechar.model.PokedexBasic;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Bolsa extends AppCompatActivity implements BolsaAdapter.ListItemClickListener{
    private BolsaBasic[] bolsaBasics;
    private String urlJSON;
    private String TAG = "PokeChar/Bolsa";
    private View vSplash;
    private ProgressBar pbSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolsa);

        Bundle bundle = getIntent().getExtras();
        urlJSON = bundle.getString("item");

        vSplash = findViewById(R.id.vSplashBolsa);
        pbSplash = findViewById(R.id.pbSplashBolsa);

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

                        String name;
                        String urlD;
                        String urlFoto;

                        try {
                            jsonArray = response.getJSONArray("results");

                            bolsaBasics = new BolsaBasic[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {
                                name = jsonArray.getJSONObject(i).getString("name");
                                urlD = jsonArray.getJSONObject(i).getString("url");
                                urlFoto = posBolsa(name, jsonArray.getJSONObject(i).getString("name"));
                                bolsaBasics[i] = new BolsaBasic(i, name, urlD, urlFoto);

                            }

                            cargarBolsa();

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

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);

    }

    public String posBolsa(String name, String url) {
        if (name.startsWith("tm")) {
            url = BolsaApi.tmSprite;
        } else if (name.startsWith("hm")) {
            url = BolsaApi.hmSprite;
        } else {
            url = BolsaApi.sprite + url + ".png";
        }


        return url;
    }

    public void cargarBolsa() {
        RecyclerView rvBolsa = findViewById(R.id.rvBolsa);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvBolsa.setLayoutManager(linearLayoutManager);
        rvBolsa.setHasFixedSize(true);
        BolsaAdapter bolsaAdapter = new BolsaAdapter(bolsaBasics,  this);
        rvBolsa.setAdapter(bolsaAdapter);

        pbSplash.setVisibility(View.GONE);
        vSplash.setVisibility(View.GONE);

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent i = new Intent(this, BolsaData.class);
        i.putExtra("urlData", bolsaBasics[clickedItemIndex].getUrlData());
        startActivity(i);
    }
}