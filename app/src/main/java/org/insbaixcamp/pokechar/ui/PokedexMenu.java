package org.insbaixcamp.pokechar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.insbaixcamp.pokechar.R;
import org.insbaixcamp.pokechar.api.PokeApi;

public class PokedexMenu extends AppCompatActivity implements View.OnClickListener {

    private Button btFirst;
    private Button btSecond;
    private Button btThird;
    private Button btFourth;
    private Button btFifth;
    private Button btSixth;
    private Button btSeventh;
    private Button btEighth;
    private Button btpokedex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex_menu);

        btFirst = findViewById(R.id.btFirstGen);
        btSecond = findViewById(R.id.btSecondGen);
        btThird = findViewById(R.id.btThirdGen);
        btFourth = findViewById(R.id.btFourthGen);
        btFifth = findViewById(R.id.btFifthGen);
        btSixth = findViewById(R.id.btSixthGen);
        btSeventh = findViewById(R.id.btSeventhGen);
        btEighth = findViewById(R.id.btEighthGen);
        btpokedex = findViewById(R.id.btPokedex);

        btFirst.setOnClickListener(this);
        btSecond.setOnClickListener(this);
        btThird.setOnClickListener(this);
        btFourth.setOnClickListener(this);
        btFifth.setOnClickListener(this);
        btSixth.setOnClickListener(this);
        btSeventh.setOnClickListener(this);
        btEighth.setOnClickListener(this);
        btpokedex.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, Pokedex.class);
        if (v.getId() == btFirst.getId()) {
            i.putExtra("offset", PokeApi.offsetFirstGen);
            i.putExtra("limit", PokeApi.limitFirstGen);
        } else if (v.getId() == btSecond.getId()) {
            i.putExtra("offset", PokeApi.offsetSecondGen);
            i.putExtra("limit", PokeApi.limitSecondGen);
        } else if (v.getId() == btThird.getId()) {
            i.putExtra("offset", PokeApi.offsetThirdGen);
            i.putExtra("limit", PokeApi.limitThirdGen);
        } else if (v.getId() == btFourth.getId()) {
            i.putExtra("offset", PokeApi.offsetFourthGen);
            i.putExtra("limit", PokeApi.limitFourthGen);
        } else if (v.getId() == btFifth.getId()) {
            i.putExtra("offset", PokeApi.offsetFifthGen);
            i.putExtra("limit", PokeApi.limitFifthGen);
        } else if (v.getId() == btSixth.getId()) {
            i.putExtra("offset", PokeApi.offsetSixthGen);
            i.putExtra("limit", PokeApi.limitSixthGen);
        } else if (v.getId() == btSeventh.getId()){
            i.putExtra("offset", PokeApi.offsetSeventhGen);
            i.putExtra("limit", PokeApi.limitSeventhGen);
        } else if (v.getId() == btEighth.getId()) {
            i.putExtra("offset", PokeApi.offsetEigthtGen);
            i.putExtra("limit", PokeApi.limitEigthtGen);
        } else {
            i.putExtra("offset", PokeApi.offsetPokedexGen);
            i.putExtra("limit", PokeApi.limitPokedexGen);
        }

        startActivity(i);


    }
}