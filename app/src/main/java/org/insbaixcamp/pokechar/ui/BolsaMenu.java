package org.insbaixcamp.pokechar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.insbaixcamp.pokechar.R;
import org.insbaixcamp.pokechar.api.BolsaApi;
import org.insbaixcamp.pokechar.api.PokeApi;

public class BolsaMenu extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btPokeball;
    private ImageButton btBotiquin;
    private ImageButton btremedios;
    private ImageButton btspray;
    private ImageButton btattack;
    private ImageButton btitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolsa_menu);

        btPokeball = findViewById(R.id.btPokeball);
        btBotiquin = findViewById(R.id.btBotiquin);
        btremedios = findViewById(R.id.btremedios);
        btspray = findViewById(R.id.btspray);
        btitems = findViewById(R.id.btitems);
        btattack = findViewById(R.id.btattack);

        btPokeball.setOnClickListener(this);
        btBotiquin.setOnClickListener(this);
        btremedios.setOnClickListener(this);
        btspray.setOnClickListener(this);
        btitems.setOnClickListener(this);
        btattack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, Bolsa.class);
        if (v.getId() == btPokeball.getId()) {
            i.putExtra("item", BolsaApi.pokeBall);
        } else if (v.getId() == btBotiquin.getId()) {
            i.putExtra("item", BolsaApi.botiquin);
        } else if (v.getId() == btremedios.getId()) {
            i.putExtra("item", BolsaApi.remedios);
        } else if (v.getId() == btspray.getId()) {
            i.putExtra("item", BolsaApi.sprayPotenciador);
        } else if (v.getId() == btattack.getId()) {
            i.putExtra("item", BolsaApi.tmHm);
        } else if (v.getId() == btitems.getId()) {
            i.putExtra("item", BolsaApi.objetoPotenciador);
        }
        startActivity(i);
    }
}