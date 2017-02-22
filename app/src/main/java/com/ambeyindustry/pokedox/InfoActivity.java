package com.ambeyindustry.pokedox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class InfoActivity extends AppCompatActivity implements LoaderCallbacks<Pokemon> {

    private String pokemonName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pokemonName = getIntent().getStringExtra("pokemonName");
        setContentView(R.layout.activity_info);

        //show loading view
        View view = (View) findViewById(R.id.loading);
        view.setVisibility(View.VISIBLE);
        View view1 = (View) findViewById(R.id.main);
        view1.setVisibility(View.GONE);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, this);
    }

    @Override
    public Loader<Pokemon> onCreateLoader(int i, Bundle bundle) {
        return new PokemonLoader(this, pokemonName);
    }

    @Override
    public void onLoadFinished(Loader<Pokemon> loader, Pokemon pokemon) {
        //remove loading view
        View view = (View) findViewById(R.id.loading);
        view.setVisibility(View.GONE);
        View view1 = (View) findViewById(R.id.main);
        view1.setVisibility(View.VISIBLE);

        ImageView imageView = (ImageView) findViewById(R.id.dp);
        Picasso.with(this).load(pokemon.getImageURI()).into(imageView);
        ImageView imageView1 = (ImageView) findViewById(R.id.dp1);
        Picasso.with(this).load(pokemon.getImageURI()).into(imageView1);
        String value = pokemon.getName();
        ((TextView) findViewById(R.id.pokemon_name)).setText(value);
        value = Integer.toString(pokemon.getHp()) + " hp";
        ((TextView) findViewById(R.id.pokemon_hp)).setText(value);
        value = Integer.toString(pokemon.getHeight()) + " ft";
        ((TextView) findViewById(R.id.pokemon_height)).setText(value);
        value = Integer.toString(pokemon.getWeight()) + " lbs";
        ((TextView) findViewById(R.id.pokemon_weight)).setText(value);
        value = Integer.toString(pokemon.getAttack());
        ((TextView) findViewById(R.id.pokemon_attack)).setText(value);
        value = Integer.toString(pokemon.getDefense());
        ((TextView) findViewById(R.id.pokemon_defense)).setText(value);
        value = Integer.toString(pokemon.getSpeed());
        ((TextView) findViewById(R.id.pokemon_speed)).setText(value);

        String arr[] = pokemon.getType();
        value = "";
        for (int i = 0; i < (arr.length - 1); i++) {
            value += (arr[i] + ", ");
        }
        value += arr[arr.length - 1];
        ((TextView) findViewById(R.id.pokemon_types)).setText(value);

        arr = pokemon.getMoves();
        value = "";
        if (arr.length < 5) {
            for (int i = 0; i < (arr.length - 1); i++) {
                value += (arr[i] + ", ");
            }
            value += arr[(arr.length - 1)];
        } else {
            for (int i = 0; i < 4; i++) {
                value += (arr[i] + ", ");
            }
            value += arr[4];
        }
        ((TextView) findViewById(R.id.pokemon_moves)).setText(value);

        arr = pokemon.getAbilities();
        value = "";
        for (int i = 0; i < (arr.length - 1); i++) {
            value += (arr[i] + ", ");
        }
        value += arr[arr.length - 1];
        ((TextView) findViewById(R.id.pokemon_abilities)).setText(value);

        if (pokemon.getEvolutions() != null) {
            Pokemon evolution = pokemon.getEvolutions()[0];
//            ((TextView) findViewById(R.id.pokemon_name_ev)).setText(evolution.getName());
            ImageView imageViewEv = (ImageView) findViewById(R.id.dp_ev);
            Picasso.with(this).load(evolution.getImageURI()).into(imageViewEv);
            ((View) findViewById(R.id.view_ev)).setVisibility(View.VISIBLE);
        } else {
            ((View) findViewById(R.id.view_ev)).setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Pokemon> loader) {

    }
}
