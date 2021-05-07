package org.insbaixcamp.pokechar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.insbaixcamp.pokechar.R;
import org.insbaixcamp.pokechar.api.BolsaApi;
import org.insbaixcamp.pokechar.api.PokeApi;

public class BolsaMenu extends AppCompatActivity implements View.OnClickListener {

    private Button btPokeball;
    private Button btBotiquin;
    private Button btremedios;
    private Button btspray;
    private Button btattack;
    private Button btitems;

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
            i.putExtra("offset", BolsaApi.offsetPokeball);
            i.putExtra("limit", BolsaApi.limitPokeball);
        } else if (v.getId() == btBotiquin.getId()) {
            i.putExtra("offset", BolsaApi.offsetBotiquin);
            i.putExtra("limit", BolsaApi.limitBotiquin);
        } else if (v.getId() == btremedios.getId()) {
            i.putExtra("offset", BolsaApi.offsetRemedios);
            i.putExtra("limit", BolsaApi.limitRemedios);
        } else if (v.getId() == btspray.getId()) {
            i.putExtra("offset", BolsaApi.offsetSpray);
            i.putExtra("limit", BolsaApi.limitSpray);
        } else if (v.getId() == btattack.getId()) {
            i.putExtra("offset", BolsaApi.offsetAttack);
            i.putExtra("limit", BolsaApi.limitAttack);
        } else if (v.getId() == btitems.getId()) {
            i.putExtra("offset", BolsaApi.offsetObjetos);
            i.putExtra("limit", BolsaApi.limitObjetos);
        }
        startActivity(i);
    }
}