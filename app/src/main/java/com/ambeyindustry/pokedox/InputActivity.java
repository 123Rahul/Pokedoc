package com.ambeyindustry.pokedox;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
    }

    public void searchPokemon(View view) {
        String pokemonName = ((EditText) findViewById(R.id.searchName)).getText().toString();
        Intent phrasesIntent = new Intent(this, InfoActivity.class);
        phrasesIntent.putExtra("pokemonName", pokemonName);
        startActivity(phrasesIntent);
    }

    public void listAllPokemons(View view) {
        Intent phrasesIntent = new Intent(this, ListActivity.class);
        startActivity(phrasesIntent);
    }
}
