package org.insbaixcamp.pokechar.ui;

import androidx.appcompat.app.AppCompatActivity;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BolsaData extends AppCompatActivity {
    private ImageView imagenI;
    private TextView tvnomI;
    private TextView tvDesI;
    private TextView tvCategoryI;

    private String urlItem;

    private String TAG = "PokeChar/BolsaData";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolsa_data);

        Bundle bundle = getIntent().getExtras();
        urlItem = bundle.getString("urlData");

        imagenI = findViewById(R.id.imagenI);
        tvnomI = findViewById(R.id.tvnomI);
        tvDesI = findViewById(R.id.tvDesI);
        tvCategoryI = findViewById(R.id.tvCategoryI);

        cargarItem();
    }

    private void cargarItem() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlItem,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String name;
                        String des;
                        String url;
                        String category;

                        try {
                            name = response.getString("name");
                            url = response.getJSONObject("sprites").getString("default");
                            des = response.getJSONArray("flavor_text_entries")
                                    .getJSONObject(0).getString("text");
                            category = response.getJSONObject("category").getString("name");

                            name = name.toUpperCase().charAt(0) + name.substring(1).toLowerCase();
                            category = category.toUpperCase().charAt(0) + category.substring(1).toLowerCase();
                            tvnomI.setText(name);
                            tvDesI.setText(des);
                            tvCategoryI.setText(category);


                            Picasso.get().load(url).into(imagenI);



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
}