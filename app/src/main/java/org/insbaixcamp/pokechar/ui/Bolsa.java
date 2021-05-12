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
    private int limit;
    private int offset;

    private BolsaBasic[] bolsaBasics;
    private String urlFoto;

    private String urlJSON;

    private String TAG = "PokeChar/Bolsa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolsa);

        Bundle bundle = getIntent().getExtras();
        limit = bundle.getInt("limit");
        offset = bundle.getInt("offset");

        urlJSON = BolsaApi.bolsa + "limit=" + limit + "&offset=" + offset;

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

                        String name;
                        String urlD;

                        try {
                            jsonArray = response.getJSONArray("results");

                            bolsaBasics = new BolsaBasic[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {
                                name = jsonArray.getJSONObject(i).getString("name");
                                urlD = jsonArray.getJSONObject(i).getString("url");
                                urlFoto = posBolsa(jsonArray.getJSONObject(i).getString("name"));
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

    public String posBolsa(String name) {

        name = BolsaApi.sprite + name + ".png";

        return name;
    }

    public void cargarBolsa() {
        RecyclerView rvBolsa = findViewById(R.id.rvBolsa);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvBolsa.setLayoutManager(linearLayoutManager);
        rvBolsa.setHasFixedSize(true);
        BolsaAdapter bolsaAdapter = new BolsaAdapter(bolsaBasics,  this);
        rvBolsa.setAdapter(bolsaAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
            Intent i = new Intent(this, BolsaData.class);
            i.putExtra("urlData", bolsaBasics[clickedItemIndex].getUrlData());
            startActivity(i);

    }

}