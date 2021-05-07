package org.insbaixcamp.pokechar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import org.insbaixcamp.pokechar.ui.BolsaMenu;
import org.insbaixcamp.pokechar.ui.PokedexMenu;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ibPokedex;
    private ImageButton ibBag;
    private ImageButton ibOptions;
    private ImageButton ibTrivial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ibPokedex = findViewById(R.id.ibPokedex);
        ibBag = findViewById(R.id.ibBag);
        ibOptions = findViewById(R.id.ibOptions);
        ibTrivial = findViewById(R.id.ibTrivial);

        ibPokedex.setOnClickListener(this);
        ibBag.setOnClickListener(this);
        ibOptions.setOnClickListener(this);
        ibTrivial.setOnClickListener(this);

        //Hola Jesus GG
    }

    @Override
    public void onClick(View v) {
        Intent i;
        if (v.getId() == ibPokedex.getId()) {
            i = new Intent(this, PokedexMenu.class);
        } else if (v.getId() == ibBag.getId()) {
            i = new Intent(this, BolsaMenu.class);
        } else if (v.getId() == ibOptions.getId()) {
            i = new Intent(this, PokedexMenu.class);
        } else {
            i = new Intent(this, PokedexMenu.class);
        }

        startActivity(i);

    }
}