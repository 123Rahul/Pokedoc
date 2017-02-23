package com.ambeyindustry.pokedox;

import android.content.Intent;
import android.net.Uri;
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

import java.net.URL;

public class InfoActivity extends AppCompatActivity implements LoaderCallbacks<Pokemon> {

    private String pokemonName = null;
    private Picasso.Builder builder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pokemonName = getIntent().getStringExtra("pokemonName");
        setContentView(R.layout.activity_info);

        //show loading view
        View view = (View) findViewById(R.id.noInternet);
        view.setVisibility(View.GONE);
        view = (View) findViewById(R.id.main);
        view.setVisibility(View.GONE);
        view = (View) findViewById(R.id.wrong);
        view.setVisibility(View.GONE);
        View view1 = (View) findViewById(R.id.loading);
        view1.setVisibility(View.VISIBLE);

        builder = new Picasso.Builder(this);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
                ((ImageView) findViewById(R.id.arrw)).setOnClickListener(null);
            }
        });

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
        if (pokemon != null) {
            if (pokemon.getName().equals("no internet")) {
                View view = (View) findViewById(R.id.loading);
                view.setVisibility(View.GONE);
                view = (View) findViewById(R.id.main);
                view.setVisibility(View.GONE);
                view = (View) findViewById(R.id.wrong);
                view.setVisibility(View.GONE);
                View view1 = (View) findViewById(R.id.noInternet);
                view1.setVisibility(View.VISIBLE);
                return;
            } else {
                View view = (View) findViewById(R.id.loading);
                view.setVisibility(View.GONE);
                view = (View) findViewById(R.id.noInternet);
                view.setVisibility(View.GONE);
                view = (View) findViewById(R.id.wrong);
                view.setVisibility(View.GONE);
                View view1 = (View) findViewById(R.id.main);
                view1.setVisibility(View.VISIBLE);
            }
        } else {
            View view = (View) findViewById(R.id.loading);
            view.setVisibility(View.GONE);
            view = (View) findViewById(R.id.noInternet);
            view.setVisibility(View.GONE);
            view = (View) findViewById(R.id.main);
            view.setVisibility(View.GONE);
            View view1 = (View) findViewById(R.id.wrong);
            view1.setVisibility(View.VISIBLE);
            return;
        }

        ImageView imageView = (ImageView) findViewById(R.id.dp);
        builder.build().load(pokemon.getImageURI()).into(imageView);
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
            ((TextView) findViewById(R.id.from)).setText(pokemon.getName());
            ((TextView) findViewById(R.id.to)).setText(evolution.getName());
            ImageView imageView1 = (ImageView) findViewById(R.id.dp1);
            builder.build().load(pokemon.getImageURI()).into(imageView1);
            ImageView imageViewEv = (ImageView) findViewById(R.id.dp_ev);
            builder.build().load(evolution.getImageURI()).error(R.drawable.ic_not_interested_black_24dp).into(imageViewEv);
            ((View) findViewById(R.id.view_ev)).setVisibility(View.VISIBLE);
        } else {
            ((View) findViewById(R.id.view_ev)).setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Pokemon> loader) {

    }

    public void evolvePokemon(View view) {
        String pokemonName = ((TextView) findViewById(R.id.to)).getText().toString();
        Intent phrasesIntent = new Intent(this, InfoActivity.class);
        phrasesIntent.putExtra("pokemonName", pokemonName);
        startActivity(phrasesIntent);
    }
}
