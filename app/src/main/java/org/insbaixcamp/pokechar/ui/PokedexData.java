package org.insbaixcamp.pokechar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

public class PokedexData extends AppCompatActivity {

    private String urlData;

    private String TAG = "PokeChar/PokedexData";

    private TextView tvName;
    private ImageView ibFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex_data);

        Bundle bundle = getIntent().getExtras();
        urlData = bundle.getString("urlData");

        tvName = findViewById(R.id.tvNamePokedexData);
        ibFoto = findViewById(R.id.ibFotoPokedexData);


        cargarJSON();


    }

    public void cargarJSON() {
        cargarPokemon();

    }

    public void cargarPokemon() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlData,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String str = response.getString("name");
                            str = str.toUpperCase().charAt(0) + str.substring(1).toLowerCase();
                            tvName.setText(str);

                            Picasso.get().load(posPokemon(urlData)).into(ibFoto);

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

    public String posPokemon (String str) {
        str = str.replace(PokeApi.pokedex, "");
        str = str.replace("/", ".png");

        str = PokeApi.spriteFront + str;

        return str;
    }

}
