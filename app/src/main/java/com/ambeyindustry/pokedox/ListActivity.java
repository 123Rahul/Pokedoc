package com.ambeyindustry.pokedox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements LoaderCallbacks<List<Pokemon>> {

    private PokemonAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView pokemonListView = (ListView) findViewById(R.id.list);
        pAdapter = new PokemonAdapter(this, new ArrayList<Pokemon>());
        pokemonListView.setAdapter(pAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, this);

        pokemonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Pokemon pokemon = (Pokemon) ((ListView) adapterView).getAdapter().getItem(i);
                String pokemonName = pokemon.getName();
                Intent phrasesIntent = new Intent(view.getContext(), InfoActivity.class);
                phrasesIntent.putExtra("pokemonName", pokemonName);
                startActivity(phrasesIntent);
            }
        });
    }

    @Override
    public Loader<List<Pokemon>> onCreateLoader(int i, Bundle bundle) {
        return new PokemonListLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Pokemon>> loader, List<Pokemon> pokemons) {
        pAdapter.clear();
        if (pokemons != null) {
            pAdapter.addAll(pokemons);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Pokemon>> loader) {
        pAdapter.clear();
    }
}
