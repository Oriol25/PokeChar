package org.insbaixcamp.pokechar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class BolsaData extends AppCompatActivity {
    private ImageView imagenI;
    private TextView tvnomI;
    private TextView tvDesI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolsa_data);

        imagenI = findViewById(R.id.imagenI);
        tvnomI = findViewById(R.id.tvnomI);
        tvDesI = findViewById(R.id.tvDesI);



    }
}