package org.insbaixcamp.pokechar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.insbaixcamp.pokechar.R;
import org.insbaixcamp.pokechar.api.PokeApi;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pokedex extends AppCompatActivity {

    private int limit;
    private int offset;

    private String urlJSON;

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


                        try {
                            response = response.getJSONObject("results");



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
}